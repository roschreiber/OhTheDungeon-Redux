/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;


import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import forge_sandbox.BlockPos;
import org.bukkit.Material;

import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Aug 28, 2016
 *
 */
public abstract class AbstractRoomGenerator implements IRoomGenerator {

    /**
     *  Beginning at point, and move towards the room (at least 2 blocks - the point, 
     *  and the room wall) and clear blocks for doorway.
     *  Must continue into room until there is an air block either to the front or to the sides.
     *  This check will remove any crown/trim/pillar blocks that might be in the way.
     * @param world
     * @param coords
     * @param direction
     */
    protected void buildDoorway(AsyncWorldEditor world, ICoords coords, Direction direction) {
        int touching = 0;
        int x =0;
        int z = 0;
        int failSafe = 0;
        
        do {
            touching = 0;
            world.setBlockState(coords.add(x, 1, z).toPos(), Material.AIR, 3);
            world.setBlockState(coords.add(x, 2, z).toPos(), Material.AIR, 3);

            // check in all four directions and add 1 to value
            BlockPos pos = coords.add(x, 1, z).toPos();
            if (world.getBlockState(pos.north()) == Material.AIR) touching++;
            if (world.getBlockState(pos.south()) == Material.AIR) touching++;
            if (world.getBlockState(pos.east()) == Material.AIR) touching++;
            if (world.getBlockState(pos.west()) == Material.AIR) touching++;

            // move to next block
            switch(direction) {
            case NORTH:
                z--;
                break;
            case EAST:
                x++;
                break;
            case SOUTH:
                z++;
                break;
            case WEST:
                x--;
                break;
                default:
            }
            failSafe++;
        } while (touching < 2 && failSafe < 5);
    }
}
