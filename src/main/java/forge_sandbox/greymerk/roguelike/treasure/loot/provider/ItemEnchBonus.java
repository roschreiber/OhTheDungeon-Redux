package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;

public class ItemEnchBonus extends ItemBase {

	public ItemEnchBonus(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if (rand.nextBoolean())
			return new ItemStack(Material.EXPERIENCE_BOTTLE, 1 + rand.nextInt(5));
		return new ItemStack(Material.ENDER_PEARL, 1 + rand.nextInt(2));
	}

}
