package forge_sandbox.team.cqr.cqrepoured.util;

import java.util.Random;

import org.bukkit.block.BlockFace;

import forge_sandbox.EnumFacingConstant;
import forge_sandbox.Vec3i;
import otd.addon.com.ohthedungeon.storydungeon.generator.b173gen.oldgen.MathHelper;
import otd.lib.async.AsyncWorldEditor;

/**
 * Copyright (c) 29.04.2019<br>
 * Developed by DerToaster98<br>
 * GitHub: https://github.com/DerToaster98
 */
public class DungeonGenUtils {
	private static final Random RAND = new Random();

	public static int randomBetweenGaussian(int min, int max) {
		return randomBetweenGaussian(min, max, RAND);
	}

	/*
	 * Rotate a vec3i to align with the given side. Assumes that the vec3i is
	 * default +x right, +z down coordinate system
	 */
	public static Vec3i rotateVec3i(Vec3i vec, BlockFace side) {
		if (side == BlockFace.SOUTH) {
			return new Vec3i(-vec.getX(), vec.getY(), -vec.getZ());
		} else if (side == BlockFace.WEST) {
			return new Vec3i(vec.getZ(), vec.getY(), -vec.getX());
		} else if (side == BlockFace.EAST) {
			return new Vec3i(-vec.getZ(), vec.getY(), vec.getX());
		} else {
			// North side, or some other invalid side
			return vec;
		}
	}

	public static int randomBetweenGaussian(int min, int max, Random rand) {
		if (min >= max) {
			return min;
		}
		double avg = min + ((max - min) / 2.0D);
		double stdDev = (max - avg) / 3.0D; // guarantees that MOST (99.7%) results will be between low & high
		double gaussian = rand.nextGaussian();
		int result = (int) (avg + (gaussian * stdDev) + 0.5D); // 0.5 is added for rounding to nearest whole number
		return MathHelper.clamp(result, min, max);
	}

	public static int getCWRotationsBetween(BlockFace start, BlockFace end) {
		int rotations = 0;
		if (EnumFacingConstant.isHorizontal(start) && EnumFacingConstant.isHorizontal(end)) {
			while (start != end) {
				start = EnumFacingConstant.rotateY(start);
				rotations++;
			}
		}
		return rotations;
	}

	public static int getSpawnX(AsyncWorldEditor world) {
		return world.getSpawnX();
	}

	public static int getSpawnZ(AsyncWorldEditor world) {
		return world.getSpawnZ();
	}

	public static BlockFace rotateFacingNTimesAboutY(BlockFace facing, int n) {
		for (int i = 0; i < n; i++) {
			facing = EnumFacingConstant.rotateY(facing);
		}
		return facing;
	}

	public static int randomBetween(int min, int max) {
		return randomBetween(min, max, RAND);
	}

	public static int randomBetween(int min, int max, Random rand) {
		if (min >= max) {
			return min;
		}
		return min + rand.nextInt(max - min + 1);
	}

	public static boolean percentageRandom(int chance) {
		return percentageRandom(chance, RAND);
	}

	public static boolean percentageRandom(int chance, Random rand) {
		if (chance <= 0) {
			return false;
		}
		if (chance >= 100) {
			return true;
		}
		return rand.nextInt(100) < chance;
	}

	public static boolean percentageRandom(double chance) {
		return percentageRandom(chance, RAND);
	}

	public static boolean percentageRandom(double chance, Random rand) {
		if (chance <= 0.0D) {
			return false;
		}
		if (chance >= 1.0D) {
			return true;
		}
		return rand.nextDouble() < chance;
	}

	public static Vec3i rotateMatrixOffsetCW(Vec3i offset, int sizeX, int sizeZ, int numRotations) {
		final int maxXIndex = sizeX - 1;
		final int maxZIndex = sizeZ - 1;

		if (numRotations % 4 == 0) {
			return new Vec3i(offset.getX(), offset.getY(), offset.getZ());
		} else if (numRotations % 4 == 1) {
			return new Vec3i(maxZIndex - offset.getZ(), offset.getY(), offset.getX());
		} else if (numRotations % 4 == 2) {
			return new Vec3i(maxXIndex - offset.getX(), offset.getY(), maxZIndex - offset.getZ());
		} else {
			return new Vec3i(offset.getZ(), offset.getY(), maxXIndex - offset.getX());
		}
	}

}
