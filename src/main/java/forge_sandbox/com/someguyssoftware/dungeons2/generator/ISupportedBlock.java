package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import org.bukkit.block.data.BlockData;

public interface ISupportedBlock {

	/**
	 * @return the block
	 */
	BlockData getBlockState();

	/**
	 * @param block the block to set
	 */
	void setBlockState(BlockData state);

	/**
	 * @return the amount
	 */
	int getAmount();

	/**
	 * @param amount the amount to set
	 */
	void setAmount(int amount);

}