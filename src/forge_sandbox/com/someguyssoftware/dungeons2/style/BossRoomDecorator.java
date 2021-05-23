/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.style;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import forge_sandbox.com.someguyssoftware.dungeons2.Dungeons2;
import forge_sandbox.com.someguyssoftware.dungeons2.config.ModConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.generator.Location;
import forge_sandbox.com.someguyssoftware.dungeons2.generator.blockprovider.IDungeonsBlockProvider;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Dungeon;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Level;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeonsengine.chest.ILootLoader;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.ILevelConfig;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Rarity;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import forge_sandbox.com.someguyssoftware.gottschcore.random.RandomHelper;
import forge_sandbox.GroupHelper;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import otd.lib.async.later.dungeons2.Chest_Later;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Jan 11, 2017
 *
 */
public class BossRoomDecorator extends RoomDecorator {
    
    private static final int CARPET_PERCENT_CHANCE = 75;
    private ILootLoader lootLoader;
    
    /**
     * @param chestSheet
     */
    public BossRoomDecorator() {
//        this.chestPopulator = new ChestPopulator(chestSheet);
    }

    /**
     * 
     * @param loader
     */
    public BossRoomDecorator(ILootLoader loader) {
        setLootLoader(loader);
    }
    
    /**
     * 
     */
    // TODO this method needs to use the Template pattern. Needs to take in the Dungeon and return the dungeon
    // or has to return something that returns all important things added like, chests, spawners and any other specials. (like StructureGen in Treasure)
    @Override
    public void decorate(AsyncWorldEditor world, Random random, Dungeon dungeon, IDungeonsBlockProvider provider, Room room, ILevelConfig config) {
        Dungeons2.log.debug("In Boos Room Decorator.");
        List<Entry<DesignElement, ICoords>> surfaceAirZone = room.getFloorMap().entries().stream().filter(x -> x.getKey().getFamily() == DesignElement.SURFACE_AIR)
                .collect(Collectors.toList());            
        if (surfaceAirZone == null || surfaceAirZone.isEmpty()) return;

        List<Entry<DesignElement, ICoords>> wallZone;
        List<Entry<DesignElement, ICoords>> floorZone;
        
        // get the floor only (from the air zone)
        floorZone = surfaceAirZone.stream().filter(f -> f.getKey() == DesignElement.FLOOR_AIR).collect(Collectors.toList());
        
        Material carpet = GroupHelper.CARPETS.get(random.nextInt(GroupHelper.CARPETS.size()));
        for (Entry<DesignElement, ICoords> entry : floorZone) {
            if (random.nextInt(100) < CARPET_PERCENT_CHANCE) {
                DesignElement elem = entry.getKey();
                ICoords coords = entry.getValue();
                // check if the adjoining block exists
                if (hasSupport(world, coords, elem, provider.getLocation(coords, room, room.getLayout()))) {
                    // update the world
                    world.setBlockState(coords.toPos(), carpet, 3);    
                }
            }
        }        

        // get the walls only (from the air zone)
        wallZone = surfaceAirZone.stream().filter(f -> f.getKey() == DesignElement.WALL_AIR).collect(Collectors.toList());
                
//        // add paintings
                // TODO Do Second
//        for (int i = 0; i < 4; i++) {
//            Entry<DesignElement, ICoords> entry = wallZone.get(random.nextInt(wallZone.size()));
//            ICoords coords = entry.getValue();
//            Location location = provider.getLocation(coords, room, room.getLayout());
//            EnumFacing facing = location.getFacing();
//            if (location != null) {
//                EntityHanging entityhanging = new EntityPainting(world, coords.toPos(), facing);
//                            if (entityhanging != null && entityhanging.onValidSurface()) {
//                                if (WorldInfo.isServerSide(world)/*!world.isRemote*/) {
//                                    entityhanging.playPlaceSound();
//                                    world.spawnEntity(entityhanging);
//                                }
//                            }
//            }
//            wallZone.remove(entry);
//        }

        // TODO add pedestal/alter
        
        /*
         * add chest
         * NOTE don't need to handle the chest coords as the chest if filled within the method
         */
        addChest(world, random, dungeon, provider, room, floorZone, config);
    }
    
    // TODO add method to interface
    protected ICoords addChest(AsyncWorldEditor world, Random random, Dungeon dungeon, IDungeonsBlockProvider provider, Room room,
            List<Entry<DesignElement, ICoords>> floorZone, ILevelConfig config) {
        // select a random position on the floor
        Entry<DesignElement, ICoords> floorEntry = floorZone.get(random.nextInt(floorZone.size()));
        DesignElement elem = floorEntry.getKey();
        ICoords chestCoords = floorEntry.getValue();
        // determine location in room
        Location location = provider.getLocation(chestCoords, room, room.getLayout());
        if (hasSupport(world, chestCoords, elem, location)) {
            BlockFace facing = orientChest(location);
                        BlockData chestState = GroupHelper.CHEST.get(facing);
            
            boolean isChestPlaced = false;
            // place a chest
            if (ModConfig.enableTreasure2Integration
                    && RandomHelper.checkProbability(random, ModConfig.treasure2ChestProbability)) {
                Dungeons2.log.debug("boss room adding Treasure2 chest @ {}", new Object[] {chestCoords.toShortString()});
                // determine rarity based on dungeon size, # of levels
                int rooms = 0;
                for (Level level : dungeon.getLevels()) {
                    rooms += level.getRooms().size();  
                }
                int levels = dungeon.getLevels().size();
                
                // run thru all level maxing the # of rooms.
                Rarity rarity = Rarity.UNCOMMON;
                if (levels > 8 || rooms > 260) {
                    rarity = Rarity.EPIC;
                }
                else if (levels > 5 || rooms > 180) {
                    rarity = Rarity.RARE;
                }
                else if (levels > 2 || rooms > 100) {
                    rarity = Rarity.SCARCE;
                }

                Dungeons2.log.debug("boss room using rarity -> {}", new Object[] {rarity});
                
                Chest_Later.generate_later(world, random, chestCoords, rarity, chestState);
                isChestPlaced = true;
            }
 
            // default action
            if (!isChestPlaced) {
                Dungeons2.log.debug("boss room, treasure2 chest was NOT generated, using default.");
                Chest_Later.generate_later(world, random, chestCoords, Rarity.EPIC, chestState);
                /*
                 * NOTE this is duplicated from RoomDecorator - change into method
                 */
                Dungeons2.log.debug("Adding boss chest @ " + chestCoords.toShortString());            
            }

            // remove from list
            floorZone.remove(floorEntry);
        }
        else {
            Dungeons2.log.debug("Boss Chest has no floor support");
            chestCoords = null;
        }
        return chestCoords;
    }
    
    /**
     * 
     */
    @Deprecated
    @Override
    public void decorate(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room, LevelConfig config) {
        Dungeons2.log.debug("In Boos Room Decorator.");
        List<Entry<DesignElement, ICoords>> surfaceAirZone = room.getFloorMap().entries().stream().filter(x -> x.getKey().getFamily() == DesignElement.SURFACE_AIR)
                .collect(Collectors.toList());            
        if (surfaceAirZone == null || surfaceAirZone.isEmpty()) return;

        List<Entry<DesignElement, ICoords>> wallZone;
        List<Entry<DesignElement, ICoords>> floorZone;
        
        // get the floor only (from the air zone)
        floorZone = surfaceAirZone.stream().filter(f -> f.getKey() == DesignElement.FLOOR_AIR).collect(Collectors.toList());
        
        // decorate with carpet        
        DyeColor dye = DyeColor.values()[random.nextInt(DyeColor.values().length)];
 
        // cover floor with carpet
        Material carpet = GroupHelper.CARPETS.get(random.nextInt(GroupHelper.CARPETS.size()));
        for (Entry<DesignElement, ICoords> entry : floorZone) {
            if (random.nextInt(100) < CARPET_PERCENT_CHANCE) {
                DesignElement elem = entry.getKey();
                ICoords coords = entry.getValue();
                // check if the adjoining block exists
                if (hasSupport(world, coords, elem, provider.getLocation(coords, room, room.getLayout()))) {
                    // update the world
                    world.setBlockState(coords.toPos(), carpet, 3);    
                }
            }
        }        

        // get the walls only (from the air zone)
        wallZone = surfaceAirZone.stream().filter(f -> f.getKey() == DesignElement.WALL_AIR).collect(Collectors.toList());
                
        // add paintings
                //TODO Do Second
        for (int i = 0; i < 4; i++) {
            Entry<DesignElement, ICoords> entry = wallZone.get(random.nextInt(wallZone.size()));
//            ICoords coords = entry.getValue();
//            Location location = provider.getLocation(coords, room, room.getLayout());
//            EnumFacing facing = location.getFacing();
//            if (location != null) {
//                EntityHanging entityhanging = new EntityPainting(world, coords.toPos(), facing);
//                if (entityhanging != null && entityhanging.onValidSurface()) {
//                    if (WorldInfo.isServerSide(world)/*!world.isRemote*/) {
//                        entityhanging.playPlaceSound();
//                        world.spawnEntity(entityhanging);
//                    }
//                }
//            }
            wallZone.remove(entry);
        }

        // TODO add pedestal/alter
        
        /*
         * add chest
         */
        // select a random position on the floor
        Entry<DesignElement, ICoords> floorEntry = floorZone.get(random.nextInt(floorZone.size()));
        DesignElement elem = floorEntry.getKey();
        ICoords chestCoords = floorEntry.getValue();
        // determine location in room
        Location location = provider.getLocation(chestCoords, room, room.getLayout());
        if (hasSupport(world, chestCoords, elem, location)) {    
            BlockFace facing = orientChest(location);            
            // place a chest
            Chest_Later.generate_later(world, random, chestCoords, Rarity.EPIC, GroupHelper.CHEST.get(facing));
            // remove from list
            floorZone.remove(floorEntry);
        }
        else {
            Dungeons2.log.debug("Boss Chest has no floor support");
        }        
    }

    /**
     * @return the lootLoader
     */
    @Override
    public ILootLoader getLootLoader() {
        return lootLoader;
    }

    /**
     * @param lootLoader the lootLoader to set
     */
    @Override
    public final void setLootLoader(ILootLoader loader) {
        this.lootLoader = loader;
    }
}

