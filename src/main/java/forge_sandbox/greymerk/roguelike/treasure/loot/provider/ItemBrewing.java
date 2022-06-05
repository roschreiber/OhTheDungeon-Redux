package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import forge_sandbox.greymerk.roguelike.util.WeightedRandomizer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;

public class ItemBrewing extends ItemBase {

	private WeightedRandomizer<ItemStack> items;

	public ItemBrewing(int weight, int level) {
		super(weight, level);
		this.items = new WeightedRandomizer<>();
		this.items.add(new WeightedRandomLoot(Material.SPIDER_EYE, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.BLAZE_POWDER, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.MAGMA_CREAM, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.GHAST_TEAR, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.NETHER_WART, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.REDSTONE, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.GLOWSTONE_DUST, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.SUGAR, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.GLISTERING_MELON_SLICE, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.FERMENTED_SPIDER_EYE, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.BROWN_MUSHROOM, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.RED_MUSHROOM, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.RABBIT_FOOT, 0, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.PUFFERFISH, 3, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Material.GLASS_BOTTLE, 0, 3, 12, 1));
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return this.items.get(rand);
	}
}
