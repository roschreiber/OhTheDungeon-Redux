package forge_sandbox.team.cqr.cqrepoured.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import forge_sandbox.BlockPos;
import forge_sandbox.EnumFacingConstant;

/**
 * Copyright (c) 15 Feb 2019 Developed by KalgogSmash GitHub:
 * https://github.com/KalgogSmash
 */
public class SpiralStaircaseBuilder {

	private static final int STAIR_WIDTH = 2;

	private BlockPos start;
	private BlockFace firstSide;
	private BlockData platformBlockState;
	private BlockData stairBlockState;

	public SpiralStaircaseBuilder(BlockPos pillarStart, BlockFace firstStairSide, BlockData platformBlockState,
			BlockData stairBlockState) {
		this.start = pillarStart;
		this.firstSide = firstStairSide;
		this.platformBlockState = platformBlockState;
		this.stairBlockState = stairBlockState;
	}

	public SpiralStaircaseBuilder(BlockPos pillarStart, BlockFace firstStairSide, Material platformBlockState,
			Material stairBlockState) {
		this.start = pillarStart;
		this.firstSide = firstStairSide;
		this.platformBlockState = Bukkit.createBlockData(platformBlockState);
		this.stairBlockState = Bukkit.createBlockData(stairBlockState);
	}

	// returns true if a position is within this staircase, meaning that it is
	// within
	// the 3x3 grid of the spiral and at or above the starting Y
	public boolean isPartOfStairs(BlockPos position) {
		return ((Math.abs(position.getX() - this.start.getX()) <= STAIR_WIDTH)
				&& (Math.abs(position.getZ() - this.start.getZ()) <= STAIR_WIDTH)
				&& (position.getY() >= this.start.getY()));
	}

	public BlockData getBlock(BlockPos position) {
		BlockFace stairSide;
		int startX = this.start.getX();
		int startZ = this.start.getZ();
		int posX = position.getX();
		int posZ = position.getZ();

		if (position.getX() == this.start.getX() && position.getZ() == this.start.getZ()) {
			return this.platformBlockState;
		}

		// The side of the stairs rotates each level from the bottom
		stairSide = this.rotateFacingNTimesCW(this.firstSide, Math.abs(position.getY() - this.start.getY()));
		BlockFace stairFacing = this.rotateFacingNTimesCW(stairSide, 1);

		switch (stairSide) {
		case NORTH:
			if (posX == startX && this.inBoundsNoZero(posZ, startZ, -STAIR_WIDTH)) {
				BlockData res = this.stairBlockState.clone();
				Directional dir = (Directional) res;
				dir.setFacing(stairFacing);
				return dir;
			} else if (this.inBoundsNoZero(posX, startX, STAIR_WIDTH)
					&& this.inBoundsWithZero(posZ, startZ, -STAIR_WIDTH)) {
				return this.platformBlockState;
			}
			break;

		case SOUTH:
			if (posX == startX && this.inBoundsNoZero(posZ, startZ, STAIR_WIDTH)) {
				BlockData res = this.stairBlockState.clone();
				Directional dir = (Directional) res;
				dir.setFacing(stairFacing);
				return dir;
			} else if (this.inBoundsNoZero(posX, startX, -STAIR_WIDTH)
					&& this.inBoundsWithZero(posZ, startZ, STAIR_WIDTH)) {
				return this.platformBlockState;
			}
			break;

		case WEST:
			if (this.inBoundsNoZero(posX, startX, -STAIR_WIDTH) && posZ == startZ) {
				BlockData res = this.stairBlockState.clone();
				Directional dir = (Directional) res;
				dir.setFacing(stairFacing);
				return dir;
			} else if (this.inBoundsWithZero(posX, startX, -STAIR_WIDTH)
					&& this.inBoundsNoZero(posZ, startZ, -STAIR_WIDTH)) {
				return this.platformBlockState;
			}
			break;

		case EAST:
			if (this.inBoundsNoZero(posX, startX, STAIR_WIDTH) && posZ == startZ) {
				BlockData res = this.stairBlockState.clone();
				Directional dir = (Directional) res;
				dir.setFacing(stairFacing);
				return dir;
			} else if (this.inBoundsWithZero(posX, startX, STAIR_WIDTH)
					&& this.inBoundsNoZero(posZ, startZ, STAIR_WIDTH)) {
				return this.platformBlockState;
			}
			break;
		default:
			break;
		}
		return Bukkit.createBlockData(Material.AIR);
	}

	private BlockFace rotateFacingNTimesCW(BlockFace facing, int n) {
		n = n % 4; // cap at 0-3 rotations, any more is redundant
		while (n != 0) {
			facing = EnumFacingConstant.rotateY(facing);
			n--;
		}

		return facing;
	}

	private boolean inBoundsNoZero(int pos, int start, int distance) {
		int diff = pos - start;
		if (distance > 0) {
			return (diff > 0 && diff <= distance);
		} else {
			return (diff < 0 && diff >= distance);
		}
	}

	private boolean inBoundsWithZero(int pos, int start, int distance) {
		int diff = pos - start;
		if (distance > 0) {
			return (diff >= 0 && diff <= distance);
		} else {
			return (diff <= 0 && diff >= distance);
		}
	}

}