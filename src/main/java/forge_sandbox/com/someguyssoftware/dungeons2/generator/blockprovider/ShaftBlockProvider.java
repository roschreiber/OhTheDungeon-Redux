/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator.blockprovider;

import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.DesignElement;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Layout;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;

/**
 * @author Mark Gottschling on Aug 27, 2016
 *
 */
public class ShaftBlockProvider implements IExteriorDungeonsBlockProvider {

	/**
	 * 
	 */
	@Override
	public DesignElement getDesignElement(ICoords coords, Room room, Layout layout) {
		if (isLadderElement(coords, room, layout))
			return DesignElement.LADDER;
		return DesignElement.WALL;
	}

	/**
	 * Determines whether the position at (x, y, z) is a ladder
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param room
	 * @param layout
	 * @return
	 */
	@Override
	public boolean isLadderElement(ICoords coords, Room room, Layout layout) {
		ICoords center = room.getCenter();
		return coords.getX() == center.getX() && coords.getZ() == (center.getZ());
	}

}
