package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//import net.minecraft.init.Blocks;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;

public class ItemSupply extends ItemBase {

	public ItemSupply(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {

		if (rand.nextInt(20) == 0)
			return new ItemStack(Material.CARROT, 1);
		if (rand.nextInt(20) == 0)
			return new ItemStack(Material.POTATO, 1);

		switch (rand.nextInt(8)) {
		case 0:
			return new ItemStack(Material.WHEAT_SEEDS, rand.nextInt(8) + 1);
		case 1:
			return new ItemStack(Material.PUMPKIN_SEEDS, rand.nextInt(8) + 1);
		case 2:
			return new ItemStack(Material.MELON_SEEDS, rand.nextInt(8) + 1);
		case 3:
			return new ItemStack(Material.WHEAT, rand.nextInt(8) + 1);
		case 4:
			return new ItemStack(Material.TORCH, 10 + rand.nextInt(10));
		case 5:
			return new ItemStack(Material.PAPER, rand.nextInt(8) + 1);
		case 6:
			return new ItemStack(Material.BOOK, rand.nextInt(4) + 1);
		case 7:
			return new ItemStack(Material.OAK_SAPLING, rand.nextInt(4) + 1);
		default:
			return new ItemStack(Material.STICK, 1);
		}
	}
}
