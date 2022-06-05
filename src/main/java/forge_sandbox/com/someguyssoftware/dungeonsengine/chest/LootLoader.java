/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeonsengine.chest;

import java.util.Random;

import forge_sandbox.com.someguyssoftware.dungeonsengine.config.IChestConfig;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import org.bukkit.block.Block;
import org.bukkit.inventory.InventoryHolder;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Jan 8, 2019
 *
 */
public class LootLoader implements ILootLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.someguyssoftware.dungeonsengine.chest.ILootLoader#populate(net.minecraft.
	 * inventory.IInventory,
	 * com.someguyssoftware.dungeonsengine.config.IChestConfig)
	 */
	@Override
	public void fill(AsyncWorldEditor world, InventoryHolder inventory, IChestConfig config, Random random) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.someguyssoftware.dungeonsengine.chest.ILootLoader#populate(net.minecraft.
	 * tileentity.TileEntityChest,
	 * com.someguyssoftware.dungeonsengine.config.IChestConfig)
	 */
	@Override
	public void fill(AsyncWorldEditor world, Random random, Block entity, IChestConfig config) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.someguyssoftware.dungeonsengine.chest.ILootLoader#populate(net.minecraft.
	 * world.World, java.util.Random,
	 * com.someguyssoftware.gottschcore.positional.ICoords,
	 * com.someguyssoftware.dungeonsengine.config.IChestConfig)
	 */
	@Override
	public void fill(AsyncWorldEditor world, Random random, ICoords coords, IChestConfig config) {
		// TODO Auto-generated method stub
	}

}
