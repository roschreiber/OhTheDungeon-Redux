/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import org.bukkit.block.data.BlockData;

/**
 * @author Mark Gottschling on May 19, 2015
 *
 */

public class SupportedBlock implements ISupportedBlock {
	private BlockData blockState;
	private int amount;

	public SupportedBlock() {

	}

	public SupportedBlock(BlockData blockState, int amount) {
		setBlockState(blockState);
		setAmount(amount);
	}

	/**
	 * Copy constructor
	 * 
	 * @param supported
	 */
	public SupportedBlock(ISupportedBlock supported) {
		setBlockState(supported.getBlockState());
		setAmount(supported.getAmount());
	}

	/**
	 * @return the block
	 */
	@Override
	public BlockData getBlockState() {
		return blockState;
	}

	/**
	 * @param block the block to set
	 */
	@Override
	public final void setBlockState(BlockData blockState) {
		this.blockState = blockState;
	}

	/**
	 * @return the amount
	 */
	@Override
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	@Override
	public final void setAmount(int amount) {
		this.amount = amount;
	}
}
