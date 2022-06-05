package forge_sandbox;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

public enum Rotation {
	CLOCKWISE_180, CLOCKWISE_90, COUNTERCLOCKWISE_90, NONE;

	public static BlockPos rotate(Rotation r, BlockPos pos) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		switch (r) {
		case COUNTERCLOCKWISE_90:
			return new BlockPos(k, j, -i);
		case CLOCKWISE_90:
			return new BlockPos(-k, j, i);
		case CLOCKWISE_180:
			return new BlockPos(-i, j, -k);
		default:
			return new BlockPos(i, j, k);
		}
	}

	public static BlockFace rotate(Rotation r, BlockFace facing) {
		if (r == CLOCKWISE_180) {
			switch (facing) {
			case EAST:
				return BlockFace.WEST;
			case WEST:
				return BlockFace.EAST;
			case NORTH:
				return BlockFace.SOUTH;
			case SOUTH:
				return BlockFace.NORTH;
			default:
				return facing;
			}
		}
		if (r == CLOCKWISE_90) {
			switch (facing) {
			case EAST:
				return BlockFace.SOUTH;
			case WEST:
				return BlockFace.NORTH;
			case NORTH:
				return BlockFace.EAST;
			case SOUTH:
				return BlockFace.WEST;
			default:
				return facing;
			}
		}
		if (r == COUNTERCLOCKWISE_90) {
			switch (facing) {
			case EAST:
				return BlockFace.NORTH;
			case WEST:
				return BlockFace.SOUTH;
			case NORTH:
				return BlockFace.WEST;
			case SOUTH:
				return BlockFace.EAST;
			default:
				return facing;
			}
		}
		return facing;
	}

	public static void rotate(Rotation r, BlockPos[] pos, BlockData[] data) {
		BlockData bd = data[0];
		if (bd instanceof Directional) {
			BlockFace facing = ((Directional) bd).getFacing();
			facing = rotate(r, facing);
			((Directional) bd).setFacing(facing);
			data[0] = bd;
		}
		pos[0] = rotate(r, pos[0]);
	}
}
