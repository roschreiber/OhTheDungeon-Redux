package forge_sandbox.com.someguyssoftware.dungeons2.rotate;

import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Rotate;
import org.bukkit.block.data.BlockData;

/**
 * This does NOT depend on PlansApi. It is a stand-alone interface for
 * Dungeons2.
 * 
 * @author Mark Gottschling on Aug 4, 2016
 *
 */
public interface IRotator {

	public BlockData rotate(BlockData blockState, Rotate rotate);

	public BlockData rotate(BlockData blockState, Direction direction);

}