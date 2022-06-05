/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import org.bukkit.block.BlockFace;

/**
 * @author Mark Gottschling on Aug 4, 2016
 *
 */
public enum Location {
	NORTH_SIDE, EAST_SIDE, SOUTH_SIDE, WEST_SIDE, MIDDLE;

	/**
	 * 
	 * @return
	 */
	public BlockFace getFacing() {
		if (this == NORTH_SIDE)
			return BlockFace.SOUTH;
		if (this == EAST_SIDE)
			return BlockFace.WEST;
		if (this == SOUTH_SIDE)
			return BlockFace.NORTH;
		if (this == WEST_SIDE)
			return BlockFace.EAST;
		return BlockFace.NORTH;
	}
}
