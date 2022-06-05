/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator.blockprovider;

import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.DesignElement;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Layout;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;

/**
 * @author Mark Gottschling on Aug 28, 2016
 *
 */
public class HallwayBlockProvider implements IDungeonsBlockProvider {

	/**
	 * 
	 */
	@Override
	public DesignElement getDesignElement(ICoords coords, Room room, Layout layout) {

		// check for floor
		if (isFloorElement(coords, room, layout))
			return DesignElement.FLOOR;

		// check for wall
		if (isWallElement(coords, room, layout))
			return DesignElement.WALL;

		// check for ceiling
		if (isCeilingElement(coords, room, layout))
			return DesignElement.CEILING;

		return DesignElement.AIR;
	}
}
