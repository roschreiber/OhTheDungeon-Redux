/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.style;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import forge_sandbox.com.someguyssoftware.dungeons2.generator.blockprovider.IDungeonsBlockProvider;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Dungeon;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.spawner.SpawnSheet;
import forge_sandbox.com.someguyssoftware.dungeonsengine.chest.ILootLoader;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.ILevelConfig;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.BlockPos;
import forge_sandbox.GroupHelper;
import org.bukkit.Material;

/**
 * @author Mark Gottschling on Feb 15, 2017
 *
 */
public class LibraryRoomDecorator extends RoomDecorator {
    private static final int CARPET_PERCENT_CHANCE = 85;
    
    /**
     * 
     * @param loader
     * @param spawnSheet
     */
    public LibraryRoomDecorator(ILootLoader loader, SpawnSheet spawnSheet) {
        super(loader, spawnSheet);
    }

    @Override
    public void decorate(AsyncWorldEditor world, Random random, Dungeon dungeon, IDungeonsBlockProvider provider, Room room, ILevelConfig config) {
        /*
         * NOTE these streams aren't needed for the multimap - just access to get the collection
         */
        List<Entry<DesignElement, ICoords>> surfaceAirZone = room.getFloorMap().entries().stream().filter(x -> x.getKey().getFamily() == DesignElement.SURFACE_AIR)
                .collect(Collectors.toList());        

        if (surfaceAirZone == null || surfaceAirZone.isEmpty()) return;

        List<ICoords> wallZone;
        List<ICoords> floorZone;

        wallZone = (List<ICoords>) room.getFloorMap().get(DesignElement.WALL_AIR);
        floorZone = (List<ICoords>) room.getFloorMap().get(DesignElement.FLOOR_AIR);
        
        List<ICoords> removeFloorZones = new ArrayList<>();
        List<ICoords> removeWallZones = new ArrayList<>();
        
        // select a color for the carpet
                Material carpet = GroupHelper.CARPETS.get(random.nextInt(GroupHelper.CARPETS.size()));

        for (ICoords coords : floorZone) {
            BlockPos floorPos = coords.toPos().down();

            // get the x,z indexes
            int xIndex = coords.getX() - room.getCoords().getX();
            int zIndex = coords.getZ() - room.getCoords().getZ();

            // check if against a wall
            if (coords.getX() == room.getMinX() + 1 || coords.getX() == room.getMaxX() -1
                    || coords.getZ() == room.getMinZ() +1 || coords.getZ() == room.getMaxZ() -1) {
                // check if the block has support
                if (hasSupport(world, coords, DesignElement.FLOOR_AIR, provider.getLocation(coords, room, room.getLayout()))) {    
                    // check if wall AND the 4th block
                    if (((coords.getX() == room.getMinX() + 1 || coords.getX() == room.getMaxX() -1) && Math.abs(zIndex) % 4 == 0)
                            || ((coords.getZ() == room.getMinZ() + 1 || coords.getZ() == room.getMaxZ() -1) && Math.abs(xIndex) % 4 == 0)) {
                        world.setBlockState(coords.toPos(), GroupHelper.OAK_LOG_Y.clone(), 3);
                    }
                    else {
                        world.setBlockState(coords.toPos(), Material.BOOKSHELF, 3);
                    }
                    removeFloorZones.add(coords);    
                }
            }
            else {
                // add carpet
                if (random.nextInt(100) < CARPET_PERCENT_CHANCE) {
                    if (world.getBlockState(floorPos).isSolid()) {
                        // update the world
                        world.setBlockState(coords.toPos(), carpet, 3);    
                    }
                }
            }

            // replace the floor block with planks
            if (world.getBlockState(floorPos).isSolid()) {
                world.setBlockState(floorPos, Material.OAK_PLANKS, 3);
            }
        }

        for (ICoords coords : wallZone) {
            DesignElement elem = DesignElement.WALL_AIR;
            if (hasSupport(world, coords, elem, provider.getLocation(coords, room, room.getLayout()))) {
                // get the x,z indexes
                int xIndex = coords.getX() - room.getCoords().getX();
                int zIndex = coords.getZ() - room.getCoords().getZ();
                
                if (((coords.getX() == room.getMinX() + 1 || coords.getX() == room.getMaxX() -1) && Math.abs(zIndex) % 4 == 0)
                        || ((coords.getZ() == room.getMinZ() + 1 || coords.getZ() == room.getMaxZ() -1) && Math.abs(xIndex) % 4 == 0)) {
                    world.setBlockState(coords.toPos(), GroupHelper.OAK_LOG_Y.clone(), 3);
                }
                else {
                    // update the world
                    world.setBlockState(coords.toPos(), Material.BOOKSHELF, 3);
                }
                // add the zone to the remove list
                removeWallZones.add(coords);
            }
        }

        for(ICoords c : removeFloorZones) {
            room.getFloorMap().remove(DesignElement.FLOOR_AIR, c);
        }

        for (ICoords c : removeWallZones) {
            room.getFloorMap().remove(DesignElement.WALL_AIR, c);
        }
        
        floorZone.removeAll(removeFloorZones);
        removeFloorZones.clear();
        // remove location from wallZone
        wallZone.removeAll(removeWallZones); // <--- this doesn't really matter
        removeWallZones.clear();

        // add shelves that extend from the wall

        // decorate as normal
        super.decorate(world, random, dungeon, provider, room, config);
    }
    
    @Deprecated
    @Override
    public void decorate(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room, LevelConfig config) {
        /*
         * NOTE these streams aren't needed for the multimap - just access to get the collection
         */
        List<Entry<DesignElement, ICoords>> surfaceAirZone = room.getFloorMap().entries().stream().filter(x -> x.getKey().getFamily() == DesignElement.SURFACE_AIR)
                .collect(Collectors.toList());        

        if (surfaceAirZone == null || surfaceAirZone.isEmpty()) return;

        List<ICoords> wallZone;
        List<ICoords> floorZone;

        wallZone = (List<ICoords>) room.getFloorMap().get(DesignElement.WALL_AIR);
        floorZone = (List<ICoords>) room.getFloorMap().get(DesignElement.FLOOR_AIR);
        
        List<ICoords> removeFloorZones = new ArrayList<>();
        List<ICoords> removeWallZones = new ArrayList<>();
        
        // select a color for the carpet
        Material carpet = GroupHelper.CARPETS.get(random.nextInt(GroupHelper.CARPETS.size()));

        for (ICoords coords : floorZone) {
            BlockPos floorPos = coords.toPos().down();

            // get the x,z indexes
            int xIndex = coords.getX() - room.getCoords().getX();
            int zIndex = coords.getZ() - room.getCoords().getZ();

            // check if against a wall
            if (coords.getX() == room.getMinX() + 1 || coords.getX() == room.getMaxX() -1
                    || coords.getZ() == room.getMinZ() +1 || coords.getZ() == room.getMaxZ() -1) {
                // check if the block has support
                if (hasSupport(world, coords, DesignElement.FLOOR_AIR, provider.getLocation(coords, room, room.getLayout()))) {    
                    // check if wall AND the 4th block
                    if (((coords.getX() == room.getMinX() + 1 || coords.getX() == room.getMaxX() -1) && Math.abs(zIndex) % 4 == 0)
                            || ((coords.getZ() == room.getMinZ() + 1 || coords.getZ() == room.getMaxZ() -1) && Math.abs(xIndex) % 4 == 0)) {
                        world.setBlockState(coords.toPos(), GroupHelper.OAK_LOG_Y.clone(), 3);
                    }
                    else {
                        world.setBlockState(coords.toPos(), Material.BOOKSHELF, 3);
                    }
                    removeFloorZones.add(coords);    
                }
            }
            else {
                // add carpet
                if (random.nextInt(100) < CARPET_PERCENT_CHANCE) {
                    if (world.getBlockState(floorPos).isSolid()) {
                        // update the world
                        world.setBlockState(coords.toPos(), carpet, 3);    
                    }
                }
            }

            // replace the floor block with planks
            if (world.getBlockState(floorPos).isSolid()) {
                world.setBlockState(floorPos, Material.OAK_PLANKS, 3);
            }
        }

        for (ICoords coords : wallZone) {
            DesignElement elem = DesignElement.WALL_AIR;
            if (hasSupport(world, coords, elem, provider.getLocation(coords, room, room.getLayout()))) {
                // get the x,z indexes
                int xIndex = coords.getX() - room.getCoords().getX();
                int zIndex = coords.getZ() - room.getCoords().getZ();
                
                if (((coords.getX() == room.getMinX() + 1 || coords.getX() == room.getMaxX() -1) && Math.abs(zIndex) % 4 == 0)
                        || ((coords.getZ() == room.getMinZ() + 1 || coords.getZ() == room.getMaxZ() -1) && Math.abs(xIndex) % 4 == 0)) {
                    world.setBlockState(coords.toPos(), GroupHelper.OAK_LOG_Y.clone(), 3);            
                }
                else {
                    // update the world
                    world.setBlockState(coords.toPos(), Material.BOOKSHELF, 3);
                }
                // add the zone to the remove list
                removeWallZones.add(coords);
            }
        }

        for(ICoords c : removeFloorZones) {
            room.getFloorMap().remove(DesignElement.FLOOR_AIR, c);
        }

        for (ICoords c : removeWallZones) {
            room.getFloorMap().remove(DesignElement.WALL_AIR, c);
        }
        
        floorZone.removeAll(removeFloorZones);
        removeFloorZones.clear();
        // remove location from wallZone
        wallZone.removeAll(removeWallZones); // <--- this doesn't really matter
        removeWallZones.clear();

        // add shelves that extend from the wall

        // decorate as normal
        super.decorate(world, random, provider, room, config);
    }

}
