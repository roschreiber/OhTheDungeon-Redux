/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.util;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

/**
 *
 * @author
 */
public class RotationMirror {
	public static enum Rotation {
		NONE("rotate_0"), CLOCKWISE_90("rotate_90"), CLOCKWISE_180("rotate_180"), COUNTERCLOCKWISE_90("rotate_270");

		private final String name;
		private static final String[] rotationNames = new String[values().length];

		private Rotation(String nameIn) {
			this.name = nameIn;
		}

		public Rotation add(Rotation rotation) {
			switch (rotation) {
			case CLOCKWISE_180:

				switch (this) {
				case NONE:
					return CLOCKWISE_180;
				case CLOCKWISE_90:
					return COUNTERCLOCKWISE_90;
				case CLOCKWISE_180:
					return NONE;
				case COUNTERCLOCKWISE_90:
					return CLOCKWISE_90;
				}

			case COUNTERCLOCKWISE_90:

				switch (this) {
				case NONE:
					return COUNTERCLOCKWISE_90;
				case CLOCKWISE_90:
					return NONE;
				case CLOCKWISE_180:
					return CLOCKWISE_90;
				case COUNTERCLOCKWISE_90:
					return CLOCKWISE_180;
				}

			case CLOCKWISE_90:

				switch (this) {
				case NONE:
					return CLOCKWISE_90;
				case CLOCKWISE_90:
					return CLOCKWISE_180;
				case CLOCKWISE_180:
					return COUNTERCLOCKWISE_90;
				case COUNTERCLOCKWISE_90:
					return NONE;
				}

			default:
				return this;
			}
		}

		public BlockFace rotate(BlockFace facing) {
			if (facing == BlockFace.UP || facing == BlockFace.DOWN) {
				return facing;
			}

			if (facing == BlockFace.EAST || facing == BlockFace.WEST || facing == BlockFace.SOUTH
					|| facing == BlockFace.NORTH) {
				if (this == CLOCKWISE_90) {
					if (facing == BlockFace.EAST)
						return BlockFace.SOUTH;
					if (facing == BlockFace.WEST)
						return BlockFace.NORTH;
					if (facing == BlockFace.NORTH)
						return BlockFace.EAST;
					if (facing == BlockFace.SOUTH)
						return BlockFace.WEST;

					return facing;
				}
				if (this == CLOCKWISE_180) {
					if (facing == BlockFace.EAST)
						return BlockFace.WEST;
					if (facing == BlockFace.WEST)
						return BlockFace.EAST;
					if (facing == BlockFace.NORTH)
						return BlockFace.SOUTH;
					if (facing == BlockFace.SOUTH)
						return BlockFace.NORTH;

					return facing;
				}
				if (this == COUNTERCLOCKWISE_90) {
					if (facing == BlockFace.EAST)
						return BlockFace.NORTH;
					if (facing == BlockFace.WEST)
						return BlockFace.SOUTH;
					if (facing == BlockFace.NORTH)
						return BlockFace.WEST;
					if (facing == BlockFace.SOUTH)
						return BlockFace.EAST;

					return facing;
				}
			}

			return facing;
		}

		public int rotate(int p_185833_1_, int p_185833_2_) {
			switch (this) {
			case CLOCKWISE_90:
				return (p_185833_1_ + p_185833_2_ / 4) % p_185833_2_;
			case CLOCKWISE_180:
				return (p_185833_1_ + p_185833_2_ / 2) % p_185833_2_;
			case COUNTERCLOCKWISE_90:
				return (p_185833_1_ + p_185833_2_ * 3 / 4) % p_185833_2_;
			default:
				return p_185833_1_;
			}
		}

		static {
			int i = 0;

			for (Rotation rotation : values()) {
				rotationNames[i++] = rotation.name;
			}
		}
	}

	public static enum Mirror {
		NONE("no_mirror"), LEFT_RIGHT("mirror_left_right"), FRONT_BACK("mirror_front_back");

		private final String name;
		private static final String[] mirrorNames = new String[values().length];

		private Mirror(String nameIn) {
			this.name = nameIn;
		}

		public int mirrorRotation(int rotationIn, int rotationCount) {
			int i = rotationCount / 2;
			int j = rotationIn > i ? rotationIn - rotationCount : rotationIn;

			switch (this) {
			case FRONT_BACK:
				return (rotationCount - j) % rotationCount;
			case LEFT_RIGHT:
				return (i - j + rotationCount) % rotationCount;
			default:
				return rotationIn;
			}
		}

		public Rotation toRotation(BlockFace facing) {
			char r;
			if (facing == BlockFace.EAST || facing == BlockFace.WEST)
				r = 'X';
			else if (facing == BlockFace.UP || facing == BlockFace.DOWN)
				r = 'Y';
			else if (facing == BlockFace.NORTH || facing == BlockFace.SOUTH)
				r = 'Z';
			else
				r = 'C';

			return (this != LEFT_RIGHT || r != 'Z') && (this != FRONT_BACK || r != 'X') ? Rotation.NONE
					: Rotation.CLOCKWISE_180;
		}

		public BlockFace mirror(BlockFace facing) {
			switch (this) {
			case FRONT_BACK:

				if (facing == BlockFace.WEST) {
					return BlockFace.EAST;
				} else {
					if (facing == BlockFace.EAST) {
						return BlockFace.WEST;
					}

					return facing;
				}

			case LEFT_RIGHT:

				if (facing == BlockFace.NORTH) {
					return BlockFace.SOUTH;
				} else {
					if (facing == BlockFace.SOUTH) {
						return BlockFace.NORTH;
					}

					return facing;
				}

			default:
				return facing;
			}
		}

		static {
			int i = 0;

			for (Mirror mirror : values()) {
				mirrorNames[i++] = mirror.name;
			}
		}
	}

	public static BlockData withMirror(BlockData data, Mirror mirror) {
		if (data instanceof Directional) {
			Directional dir = (Directional) data;
			BlockFace facing = dir.getFacing();
			facing = mirror.mirror(facing);
			dir.setFacing(facing);

			return dir;
		}
		return data;
	}

	public static BlockData withRotation(BlockData data, Rotation rotation) {
		if (data instanceof Directional) {
			Directional dir = (Directional) data;
			BlockFace facing = dir.getFacing();
			facing = rotation.rotate(facing);
			dir.setFacing(facing);

			return dir;
		}
		return data;
	}
}
