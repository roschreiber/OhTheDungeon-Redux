/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import forge_sandbox.com.someguyssoftware.dungeons2.Dungeons2;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Rotate;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.Coords;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Aug 28, 2016
 *
 */
public abstract class AbstractExteriorRoomGenerator extends AbstractRoomGenerator {
    
    /**
     * 
     * @param world
     * @param room
     */
    protected void buildDoorway(AsyncWorldEditor world, Room room) {
        ICoords doorCoords;
        Direction moveDirection;
        boolean isInsetWall = (room.hasPlinth() || room.hasColumn() || room.hasCornice()) && room.getWidth() >= 7;
        Dungeons2.log.debug("Has Inset Wall:" + isInsetWall);
        int offset;
        switch(room.getDirection()) {
        case NORTH:
            offset = isInsetWall ? 1 : 0;
            doorCoords = new Coords(room.getCenter().getX(), room.getCoords().getY(), room.getMinZ() + offset);
            moveDirection = Direction.SOUTH;
            break;
        case EAST:
            offset = isInsetWall ? -1 : 0;
            doorCoords = new Coords(room.getMaxX() + offset, room.getCoords().getY(), room.getCenter().getZ());
            moveDirection = Direction.WEST;
            break;
        default: // default is SOUTH
        case SOUTH:
            offset = isInsetWall ? -1 : 0;
            doorCoords = new Coords(room.getCenter().getX(), room.getCoords().getY(), room.getMaxZ() + offset);
            moveDirection = Direction.NORTH;
            break;
        case WEST:
            offset = isInsetWall ? 1 : 0;
            doorCoords = new Coords(room.getMinX() + offset, room.getCoords().getY(), room.getCenter().getZ());
            moveDirection = Direction.EAST;
            break;
        }        
        // carve the doorway in both directions
        super.buildDoorway(world, doorCoords, moveDirection);
        super.buildDoorway(world, doorCoords, moveDirection.rotate(Rotate.ROTATE_180));
    }
}
