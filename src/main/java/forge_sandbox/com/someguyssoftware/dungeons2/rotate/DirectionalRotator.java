/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.rotate;

import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Rotate;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

/**
 * 
 * @author Mark Gottschling on Aug 4, 2016
 *
 */
public class DirectionalRotator implements IRotator {

	/**
	 * 
	 * @param blockState
	 * @param direction  the direction the blockState should face.
	 * @return
	 */
	@Override
	public BlockData rotate(BlockData blockState, Direction direction) {
		Directional rotatedState = (Directional) blockState.clone();
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

	/**
	 * @return the rotate meta value
	 */
	@Override
	public BlockData rotate(BlockData blockState, Rotate rotate) {
		Directional rotatedMeta = (Directional) blockState.clone();

		BlockFace facing = ((Directional) blockState).getFacing();

		// switch on the rotation
		switch (rotate) {
		case ROTATE_90:
			// switch on the current facing direction
			switch (facing) {
			case NORTH:
				// update the block state
				rotatedMeta.setFacing(BlockFace.EAST);
				break;
			case EAST:
				rotatedMeta.setFacing(BlockFace.SOUTH);
				break;
			case SOUTH:
				rotatedMeta.setFacing(BlockFace.WEST);
				break;
			case WEST:
				rotatedMeta.setFacing(BlockFace.NORTH);
				break;
			default:
				break;
			}
			break;
		case ROTATE_180:
			switch (facing) {
			case NORTH:
				rotatedMeta.setFacing(BlockFace.SOUTH);
				break;
			case EAST:
				rotatedMeta.setFacing(BlockFace.WEST);
				break;
			case SOUTH:
				rotatedMeta.setFacing(BlockFace.NORTH);
				break;
			case WEST:
				rotatedMeta.setFacing(BlockFace.EAST);
				break;
			default:
				break;
			}
			break;
		case ROTATE_270:
			switch (facing) {
			case NORTH:
				rotatedMeta.setFacing(BlockFace.WEST);
				break;
			case EAST:
				rotatedMeta.setFacing(BlockFace.NORTH);
				break;
			case SOUTH:
				rotatedMeta.setFacing(BlockFace.EAST);
				break;
			case WEST:
				rotatedMeta.setFacing(BlockFace.SOUTH);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

		return rotatedMeta;
	}
}
