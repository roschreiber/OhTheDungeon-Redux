/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.rotate;

import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Rotate;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Switch;

/**
 * 
 * @author Mark Gottschling on Aug 5, 2016
 *
 */
public class LeverRotator implements IRotator {
	/**
	 * 
	 * @param blockState
	 * @param direction  the direction the blockState should face.
	 * @return
	 */
	@Override
	public BlockData rotate(BlockData blockState, Direction direction) {
		Switch rotatedState = (Switch) blockState;
		switch (direction) {
		case NORTH:
			rotatedState.setFacing(BlockFace.NORTH);
			break;
		case EAST:
			rotatedState.setFacing(BlockFace.EAST);
			break;
		case SOUTH:
			rotatedState.setFacing(BlockFace.SOUTH);
			break;
		case WEST:
			rotatedState.setFacing(BlockFace.WEST);
			break;
		default:
			break;
		}
		return rotatedState;
	}

	@SuppressWarnings("deprecation")
	private static LeverFace get(Switch sw) {
		BlockFace dir = sw.getFacing();
		Switch.Face face = sw.getFace();

		if (face == Switch.Face.FLOOR) {
			if (dir == BlockFace.EAST || dir == BlockFace.WEST)
				return LeverFace.UP_X;
			else
				return LeverFace.UP_Z;
		}
		if (face == Switch.Face.CEILING) {
			if (dir == BlockFace.EAST || dir == BlockFace.WEST)
				return LeverFace.DOWN_X;
			else
				return LeverFace.DOWN_Z;
		}
		if (dir == BlockFace.EAST)
			return LeverFace.EAST;
		if (dir == BlockFace.WEST)
			return LeverFace.WEST;
		if (dir == BlockFace.SOUTH)
			return LeverFace.SOUTH;
		if (dir == BlockFace.NORTH)
			return LeverFace.NORTH;

		return LeverFace.NORTH;
	}

	private static enum LeverFace {
		UP_X, UP_Z, DOWN_X, DOWN_Z, NORTH, EAST, SOUTH, WEST
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockData rotate(BlockData blockState, Rotate rotate) {
		Switch rotatedMeta = (Switch) blockState.clone();

		LeverFace facing = get(rotatedMeta);
		LeverFace facing2 = LeverFace.UP_Z;

		// switch on the rotation
		switch (rotate) {
		case ROTATE_90:
			// switch on the current facing direction
			switch (facing) {
			case UP_X:
				facing2 = LeverFace.UP_Z;
				break;
			case UP_Z:
				facing2 = LeverFace.UP_X;
				break;
			case DOWN_X:
				facing2 = LeverFace.DOWN_Z;
				break;
			case DOWN_Z:
				facing2 = LeverFace.DOWN_X;
				break;
			case NORTH:
				// update the block blockState
				facing2 = LeverFace.EAST;
				break;
			case EAST:
				facing2 = LeverFace.SOUTH;
				break;
			case SOUTH:
				facing2 = LeverFace.WEST;
				break;
			case WEST:
				facing2 = LeverFace.NORTH;
				break;
			default:
				break;
			}
			break;
		case ROTATE_180:
			switch (facing) {
			case NORTH:
				facing2 = LeverFace.SOUTH;
				break;
			case EAST:
				facing2 = LeverFace.WEST;
				break;
			case SOUTH:
				facing2 = LeverFace.NORTH;
				break;
			case WEST:
				facing2 = LeverFace.EAST;
				break;
			default:
				break;
			}
			break;
		case ROTATE_270:
			switch (facing) {
			case UP_X:
				facing2 = LeverFace.UP_Z;
				break;
			case UP_Z:
				facing2 = LeverFace.UP_X;
				break;
			case DOWN_X:
				facing2 = LeverFace.DOWN_Z;
				break;
			case DOWN_Z:
				facing2 = LeverFace.DOWN_X;
				break;
			case NORTH:
				facing2 = LeverFace.WEST;
				break;
			case EAST:
				facing2 = LeverFace.NORTH;
				break;
			case SOUTH:
				facing2 = LeverFace.EAST;
				break;
			case WEST:
				facing2 = LeverFace.SOUTH;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

		if (facing2 == LeverFace.DOWN_X) {
			rotatedMeta.setFace(Switch.Face.CEILING);
			rotatedMeta.setFacing(BlockFace.EAST);
		}
		if (facing2 == LeverFace.DOWN_Z) {
			rotatedMeta.setFace(Switch.Face.CEILING);
			rotatedMeta.setFacing(BlockFace.NORTH);
		}
		if (facing2 == LeverFace.UP_X) {
			rotatedMeta.setFace(Switch.Face.FLOOR);
			rotatedMeta.setFacing(BlockFace.EAST);
		}
		if (facing2 == LeverFace.UP_Z) {
			rotatedMeta.setFace(Switch.Face.FLOOR);
			rotatedMeta.setFacing(BlockFace.NORTH);
		}
		if (facing2 == LeverFace.EAST) {
			rotatedMeta.setFace(Switch.Face.WALL);
			rotatedMeta.setFacing(BlockFace.EAST);
		}
		if (facing2 == LeverFace.WEST) {
			rotatedMeta.setFace(Switch.Face.WALL);
			rotatedMeta.setFacing(BlockFace.WEST);
		}
		if (facing2 == LeverFace.SOUTH) {
			rotatedMeta.setFace(Switch.Face.WALL);
			rotatedMeta.setFacing(BlockFace.SOUTH);
		}
		if (facing2 == LeverFace.NORTH) {
			rotatedMeta.setFace(Switch.Face.WALL);
			rotatedMeta.setFacing(BlockFace.NORTH);
		}

		return rotatedMeta;
	}
}
