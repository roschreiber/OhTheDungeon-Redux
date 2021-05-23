/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.style;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import forge_sandbox.com.someguyssoftware.dungeons2.generator.Location;
import forge_sandbox.com.someguyssoftware.dungeons2.generator.blockprovider.IDungeonsBlockProvider;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Dungeon;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.ILevelConfig;
import forge_sandbox.com.someguyssoftware.gottschcore.Quantity;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import forge_sandbox.com.someguyssoftware.gottschcore.random.RandomHelper;
import forge_sandbox.BlockPos;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Jan 11, 2017
 *
 */
public interface IRoomDecorator {

    /**
     * 
     * @param world
     * @param random
     * @param provider
     * @param room
     * @param config
     */
    // keep this version for legecy purposes
    void decorate(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room, LevelConfig config);
    void decorate(AsyncWorldEditor world, Random random, Dungeon dungeon, IDungeonsBlockProvider provider, Room room, ILevelConfig config);
    
    default public void addBlock(final AsyncWorldEditor world, Random random, final IDungeonsBlockProvider provider,
            final Room room, final List<Entry<DesignElement, ICoords>> zone, final BlockData[] states, 
            final Quantity frequency, final Quantity number, final ILevelConfig config) {
        
        BlockData state;
        double freq = RandomHelper.randomDouble(random, frequency.getMin(), frequency.getMax());
        int scaledNum = scaleNumForSizeOfRoom(room, RandomHelper.randomInt(random, number.getMinInt(), number.getMaxInt()), config);
        
        for (int i = 0; i < scaledNum; i++) {
            double n = random.nextDouble() * 100;
            if (n < freq && zone.size() > 0) {
                // select ANY zone
                int zoneIndex = random.nextInt(zone.size());
                Entry<DesignElement, ICoords> entry = zone.get(zoneIndex);
                DesignElement elem = zone.get(zoneIndex).getKey();
                ICoords coords = entry.getValue();
                // check if the adjoining block exists
                if (hasSupport(world, coords, elem, provider.getLocation(coords, room, room.getLayout()))) {
                    // select a block
                    if (states.length==1) state = states[0];
                    else state = states[random.nextInt(states.length)];                    
                    // update the world
                                        BlockPos pos = coords.toPos();
                                        world.setBlockData(pos.x, pos.y, pos.z, state);
                    // remove location from airZone
                    zone.remove(entry);
                }
            }
        }        
    }
    
    /**
     * NOTE This is a STATELESS method ie blocks that don't have a specific state to be in, like WEB.
     * This won't work with Blocks that use FACING etc.
     * Adds a random number of specified blocks as decorations to the room.
     * @param world
     * @param random
     * @param provider
     * @param room
     * @param zone
     * @param block
     * @param frequency
     * @param number
     * @param config
     */
    @Deprecated
    default public void addBlock(final AsyncWorldEditor world, Random random, final IDungeonsBlockProvider provider,
            final Room room, final List<Entry<DesignElement, ICoords>> zone, final BlockData[] states, 
            final Quantity frequency, final Quantity number, final LevelConfig config) {
        
        BlockData state;
        double freq = RandomHelper.randomDouble(random, frequency.getMin(), frequency.getMax());
        int scaledNum = scaleNumForSizeOfRoom(room, RandomHelper.randomInt(random, number.getMinInt(), number.getMaxInt()), config);
        
        for (int i = 0; i < scaledNum; i++) {
            double n = random.nextDouble() * 100;
            if (n < freq && zone.size() > 0) {
                // select ANY zone
                int zoneIndex = random.nextInt(zone.size());
                Entry<DesignElement, ICoords> entry = zone.get(zoneIndex);
                DesignElement elem = zone.get(zoneIndex).getKey();
                ICoords coords = entry.getValue();
                // check if the adjoining block exists
                if (hasSupport(world, coords, elem, provider.getLocation(coords, room, room.getLayout()))) {
                    // select a block
                    if (states.length==1) state = states[0];
                    else state = states[random.nextInt(states.length)];                    
                    // update the world
                                        BlockPos pos = coords.toPos();
                                        world.setBlockData(pos.x, pos.y, pos.z, state);
                    // remove location from airZone
                    zone.remove(entry);
                }
            }
        }        
    }
    
    /**
     * 
     * @param world
     * @param coords
     * @param elem
     * @param location
     * @return
     */
    default public boolean hasSupport(AsyncWorldEditor world, ICoords coords, DesignElement elem, Location location) {
        BlockPos pos = coords.toPos();
        Material blockState = null;
        Material block;
        if (elem == DesignElement.FLOOR_AIR) {
            // check below
            blockState = world.getBlockState(pos.add(0, -1, 0));
        }
        else if (elem == DesignElement.CEILING_AIR) {
            // check above
            blockState = world.getBlockState(pos.add(0, 1, 0));
        }
        else if (elem == DesignElement.WALL_AIR) {
            switch (location) {
            case NORTH_SIDE:
                blockState = world.getBlockState(pos.add(0, 0, -1));
                break;
            case EAST_SIDE:
                blockState = world.getBlockState(pos.add(1, 0, 0));
                break;
            case SOUTH_SIDE:
                blockState = world.getBlockState(pos.add(0, 0, 1));
                break;
            case WEST_SIDE:
                blockState = world.getBlockState(pos.add(-1, 0, 0));
                break;
            default:
                break;
            }            
        }        

        if (blockState != null) {
            block = blockState;
            if (block.isSolid()) {
                return true;
            }    
        }
        return false;
    }
    
    /**
     * 
     * @param room
     * @param numDecorations
     * @param config
     * @return
     */
    default public int scaleNumForSizeOfRoom(Room room, int numDecorations, ILevelConfig config) {
        int size = (room.getWidth()-2) * (room.getDepth()-2) * (room.getHeight()-2);
        int halfOfMax = ((config.getWidth().getMaxInt()-2) * (config.getDepth().getMaxInt()-2) * (config.getHeight().getMaxInt()-2))/2;
        float factor = 1F;
        
        if (size <= 27) factor = 0.25F;
        else if (size < halfOfMax) factor = 0.5F;        
        
        int num = (int) (numDecorations * factor);

        return num;
    }
    
    /**
     * Depending on the size of the room, scale the number of decorations.
     * Anything bigger than half the room size gets full amount, under half scales downward.
     * @param room
     *     @param numDecorations
     * @param config
     * @return
     */
    @Deprecated
    default public int scaleNumForSizeOfRoom(Room room, int numDecorations, LevelConfig config) {
        int size = (room.getWidth()-2) * (room.getDepth()-2) * (room.getHeight()-2);
        int halfOfMax = ((config.getWidth().getMaxInt()-2) * (config.getDepth().getMaxInt()-2) * (config.getHeight().getMaxInt()-2))/2;
        float factor = 1F;
        
        if (size <= 27) factor = 0.25F;
        else if (size < halfOfMax) factor = 0.5F;        
        
        int num = (int) (numDecorations * factor);

        return num;
    }
    
    /**
     * 
     * @param location
     * @return
     */
    default public BlockFace orientChest(Location location) {
        BlockFace facing;
        switch(location) {
        case NORTH_SIDE:
            facing = BlockFace.SOUTH;
            break;
        case SOUTH_SIDE:
            facing = BlockFace.NORTH;
            break;
        case EAST_SIDE:
            facing = BlockFace.WEST;
            break;
        case WEST_SIDE:
            facing = BlockFace.EAST;
            break;
        default:
            facing = BlockFace.NORTH;
        }
        return facing;
    }
}