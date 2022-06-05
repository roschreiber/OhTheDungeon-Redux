/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.rotate;

import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Rotate;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;

/**
 * @author Mark Gottschling on Aug 4, 2016
 *
 */
public class RotatorHelper {

	// rotators
	private static IRotator facingRotator = new DirectionalRotator();
	@SuppressWarnings("unused")
	private static IRotator logRotator = new LogRotator();
	@SuppressWarnings("unused")
	private static IRotator leverRotator = new LeverRotator();

	/*
	 * on classload, register all blocks with rotators
	 */
	/**
	 * 
	 */
	private RotatorHelper() {
	}

	/**
	 * 
	 * @param blockState
	 * @param direction
	 * @return
	 */
	public static BlockData rotateBlock(BlockData blockState, Direction direction) {
		Material block = blockState.getMaterial();

		// check against the list of blocks to ignore
		if (block == Material.AIR || blockState instanceof Slab)
			return blockState;

		// determine which rotator implementation to use
		IRotator rotator = null;
		if (blockState instanceof Directional) {
//            Dungeons2.log.debug(blockState.getBlock().getUnlocalizedName() + ": Using default FACING rotator");
			rotator = facingRotator;
		} else {
//            Dungeons2.log.debug("Can not locate rotator for block: " + block.toString());
		}

		if (rotator == null) {
//            Dungeons2.log.debug("Rotator is null.");
			return blockState;
		}

		return rotator.rotate(blockState, direction);
	}

	/**
	 * 
	 * @param blockState
	 * @param rotate
	 * @return
	 */
	public static BlockData rotateBlock(BlockData blockState, Rotate rotate) {

		// determine which rotator implementation to use
		IRotator rotator = null;

		// most common property/rotator
		if (blockState instanceof Directional) {
			rotator = facingRotator;
		}

		if (rotator == null) {
			return blockState;
		}

		return rotator.rotate(blockState, rotate);
	}
}
