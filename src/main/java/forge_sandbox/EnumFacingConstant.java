package forge_sandbox;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.block.BlockFace;

public class EnumFacingConstant {
	public final static BlockFace[] HORIZONTALS = { BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST };

	private final static Set<BlockFace> HORIZONTALS_SET;
	static {
		HORIZONTALS_SET = new HashSet<>();
		for (BlockFace face : HORIZONTALS)
			HORIZONTALS_SET.add(face);
	}

	public static enum Axis {
		X, Y, Z
	};

	public static Axis getAxis(BlockFace facing) {
		switch (facing) {
		case NORTH:
			return Axis.Z;
		case SOUTH:
			return Axis.Z;
		case EAST:
			return Axis.X;
		case WEST:
			return Axis.X;
		case UP:
			return Axis.Y;
		case DOWN:
			return Axis.Y;
		default:
			throw new IllegalStateException("Unable to get axis of " + facing.toString());
		}
	}

	public static boolean isHorizontal(BlockFace dir) {
		return HORIZONTALS_SET.contains(dir);
	}

	public static BlockFace rotateY(BlockFace face) {
		switch (face) {
		case NORTH:
			return BlockFace.EAST;
		case EAST:
			return BlockFace.SOUTH;
		case SOUTH:
			return BlockFace.WEST;
		case WEST:
			return BlockFace.NORTH;
		default:
			throw new IllegalStateException("Unable to get Y-rotated facing of " + face.toString());
		}
	}

	public static BlockFace rotateYCCW(BlockFace face) {
		switch (face) {
		case NORTH:
			return BlockFace.WEST;
		case EAST:
			return BlockFace.NORTH;
		case SOUTH:
			return BlockFace.EAST;
		case WEST:
			return BlockFace.SOUTH;
		default:
			throw new IllegalStateException("Unable to get CCW facing of " + face.toString());
		}
	}

	public static float getHorizontalAngle(BlockFace side) {
		int horizontalIndex;
		switch (side) {
		case DOWN:
			horizontalIndex = -1;
			break;
		case UP:
			horizontalIndex = -1;
			break;
		case NORTH:
			horizontalIndex = 2;
			break;
		case SOUTH:
			horizontalIndex = 0;
			break;
		case WEST:
			horizontalIndex = 1;
			break;
		case EAST:
			horizontalIndex = 3;
			break;
		default:
			horizontalIndex = -1;
			break;
		}
		return (float) ((horizontalIndex & 3) * 90);
	}
}
