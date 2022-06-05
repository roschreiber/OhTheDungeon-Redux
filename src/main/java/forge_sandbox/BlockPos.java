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
package forge_sandbox;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.BlockFace;

/**
 *
 * @author
 */
public class BlockPos extends Vec3i {
	public int x, y, z;

	public BlockPos(double x, double y, double z) {
		super(x, y, z);
		this.x = (int) x;
		this.y = (int) y;
		this.z = (int) z;
	}

	public BlockPos(BlockPos bp) {
		super(bp.x, bp.y, bp.z);
		this.x = bp.x;
		this.y = bp.y;
		this.z = bp.z;
	}

	public BlockPos(int x, int y, int z) {
		super(x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockPos(Vec3d vec) {
		super((int) vec.x, (int) vec.y, (int) vec.z);
		this.x = (int) vec.x;
		this.y = (int) vec.y;
		this.z = (int) vec.z;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getZ() {
		return this.z;
	}

	public BlockPos offset(BlockFace facing) {
		return this.offset(facing, 1);
	}

	public BlockPos offset(BlockFace facing, int n) {
		return n == 0 ? this
				: new BlockPos(this.getX() + facing.getModX() * n, this.getY() + facing.getModY() * n,
						this.getZ() + facing.getModZ() * n);
	}

	public BlockPos add(double x, double y, double z) {
		return new BlockPos(this.x + x, this.y + y, this.z + z);
	}

	public BlockPos add(Vec3i vec) {
		return new BlockPos(this.x + vec.getX(), this.y + vec.getY(), this.z + vec.getZ());
	}

	public static List<BlockPos> getAllInBox(int x0, int y0, int z0, int x1, int y1, int z1) {
		List<BlockPos> list = new ArrayList<>();
		for (int x = x0; x < x1; x++)
			for (int y = y0; y < y1; y++)
				for (int z = z0; z < z1; z++)
					list.add(new BlockPos(x, y, z));
		return list;
	}

	public BlockPos north() {
		return offset(BlockFace.NORTH, 1);
	}

	public BlockPos north(int steps) {
		return new BlockPos(x, y, z - steps);
	}

	public BlockPos south() {
		return offset(BlockFace.SOUTH, 1);
	}

	public BlockPos east() {
		return offset(BlockFace.EAST, 1);
	}

	public BlockPos west() {
		return offset(BlockFace.WEST, 1);
	}

	public BlockPos west(int steps) {
		return new BlockPos(x - steps, y, z);
	}

	public BlockPos down() {
		return offset(BlockFace.DOWN, 1);
	}

	public BlockPos up() {
		return offset(BlockFace.UP, 1);
	}

	public BlockPos toImmutable() {
		return this;
	}

	public static class MutableBlockPos extends BlockPos {
		protected int x;
		protected int y;
		protected int z;

		public MutableBlockPos() {
			this(0, 0, 0);
		}

		public MutableBlockPos(BlockPos pos) {
			this(pos.getX(), pos.getY(), pos.getZ());
		}

		public MutableBlockPos(int x_, int y_, int z_) {
			super(0, 0, 0);
			this.x = x_;
			this.y = y_;
			this.z = z_;
		}

		public BlockPos add(double x, double y, double z) {
			return super.add(x, y, z).toImmutable();
		}

		public BlockPos add(int x, int y, int z) {
			return super.add(x, y, z).toImmutable();
		}

		public BlockPos offset(BlockFace facing, int n) {
			return super.offset(facing, n).toImmutable();
		}

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}

		public int getZ() {
			return this.z;
		}

		public BlockPos.MutableBlockPos setPos(int xIn, int yIn, int zIn) {
			this.x = xIn;
			this.y = yIn;
			this.z = zIn;
			return this;
		}

		public BlockPos toImmutable() {
			return new BlockPos(this);
		}

		public BlockPos.MutableBlockPos move(BlockFace facing) {
			return this.move(facing, 1);
		}

		public BlockPos.MutableBlockPos move(BlockFace facing, int n) {
			return this.setPos(this.x + facing.getModX() * n, this.y + facing.getModY() * n,
					this.z + facing.getModZ() * n);
		}
	}
}
