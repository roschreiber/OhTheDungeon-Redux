/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest.structures;

import java.util.Random;
import org.bukkit.block.BlockFace;
import otd.util.RotationMirror.Rotation;
import static otd.util.RotationMirror.Rotation.CLOCKWISE_180;
import static otd.util.RotationMirror.Rotation.CLOCKWISE_90;
import static otd.util.RotationMirror.Rotation.COUNTERCLOCKWISE_90;
import static otd.util.RotationMirror.Rotation.NONE;

public final class RotationUtil {
	public static final Rotation[] ROTATIONS = Rotation.values();
	public static final BlockFace[] CARDINALS = { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };

	private RotationUtil() {
	}

	public static Rotation getRandomRotation(Random random) {
		return ROTATIONS[random.nextInt(ROTATIONS.length)];
	}

	public static Rotation add(Rotation original, int rotations) {
		return original.add(ROTATIONS[(rotations + 4) & 3]);
	}

	public static Rotation subtract(Rotation original, Rotation rotation) {
		switch (rotation) {
		case CLOCKWISE_180:

			switch (original) {
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

			switch (original) {
			case NONE:
				return CLOCKWISE_90;
			case CLOCKWISE_90:
				return CLOCKWISE_180;
			case CLOCKWISE_180:
				return COUNTERCLOCKWISE_90;
			case COUNTERCLOCKWISE_90:
				return NONE;
			}

		case CLOCKWISE_90:

			switch (original) {
			case NONE:
				return COUNTERCLOCKWISE_90;
			case CLOCKWISE_90:
				return NONE;
			case CLOCKWISE_180:
				return CLOCKWISE_90;
			case COUNTERCLOCKWISE_90:
				return CLOCKWISE_180;
			}

		default:
			return original;
		}
	}

	public static Rotation getRelativeRotation(BlockFace original, BlockFace destination) {
		switch (original) {
		case NORTH:
		default:
			switch (destination) {
			case NORTH:
			default:
				return Rotation.NONE;
			case SOUTH:
				return Rotation.CLOCKWISE_180;
			case EAST:
				return Rotation.CLOCKWISE_90;
			case WEST:
				return Rotation.COUNTERCLOCKWISE_90;
			}
		case SOUTH:
			switch (destination) {
			case SOUTH:
			default:
				return Rotation.NONE;
			case NORTH:
				return Rotation.CLOCKWISE_180;
			case WEST:
				return Rotation.CLOCKWISE_90;
			case EAST:
				return Rotation.COUNTERCLOCKWISE_90;
			}
		case EAST:
			switch (destination) {
			case EAST:
			default:
				return Rotation.NONE;
			case WEST:
				return Rotation.CLOCKWISE_180;
			case SOUTH:
				return Rotation.CLOCKWISE_90;
			case NORTH:
				return Rotation.COUNTERCLOCKWISE_90;
			}
		case WEST:
			switch (destination) {
			case WEST:
			default:
				return Rotation.NONE;
			case EAST:
				return Rotation.CLOCKWISE_180;
			case NORTH:
				return Rotation.CLOCKWISE_90;
			case SOUTH:
				return Rotation.COUNTERCLOCKWISE_90;
			}
		}
	}

	public static BlockFace getRandomFacing(Random random) {
		return CARDINALS[random.nextInt(CARDINALS.length)];
	}
}
