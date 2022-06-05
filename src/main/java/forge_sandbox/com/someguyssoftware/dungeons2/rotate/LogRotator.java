/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.rotate;

import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Rotate;
import org.bukkit.Axis;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;

/**
 * @author Mark Gottschling on Sep 10, 2015
 *
 */
public class LogRotator implements IRotator {

	/**
	 * 
	 * @param blockState
	 * @param direction  the direction the blockState should face.
	 * @return
	 */
	@Override
	public BlockData rotate(BlockData blockState, Direction direction) {
		Orientable rotatedState = (Orientable) blockState.clone();
		switch (direction) {
		case EAST:
			rotatedState.setAxis(Axis.Z);
			break;
		case WEST:
			rotatedState.setAxis(Axis.Z);
			break;
		default:
			break;
		}
		return rotatedState;
	}

	@Override
	public BlockData rotate(BlockData blockState, Rotate rotate) {
		Orientable rotatedMeta = (Orientable) blockState.clone();

		Axis axis = rotatedMeta.getAxis();

		// switch on the rotation
		switch (rotate) {
		case ROTATE_90:
			// switch on the current facing direction
			switch (axis) {
			case X:
				// update the block blockState
				rotatedMeta.setAxis(Axis.Z);
				break;
			case Z:
				rotatedMeta.setAxis(Axis.X);
				break;
			default:
				break;
			}
			break;
		case ROTATE_270:
			switch (axis) {
			case X:
				rotatedMeta.setAxis(Axis.Z);
				break;
			case Z:
				rotatedMeta.setAxis(Axis.X);
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
