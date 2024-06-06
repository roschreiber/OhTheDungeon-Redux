package otd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import otd.config.LootNode;

public enum OTDLoottables {
	CHESTS_TREASURE, CHESTS_EQUIPMENT, CHESTS_FOOD, CHESTS_MATERIAL, CHESTS_CLUTTER, DEFAULT;

	private static OTDLoottables[] LOOTS = { CHESTS_TREASURE, CHESTS_EQUIPMENT, CHESTS_FOOD, CHESTS_MATERIAL,
			CHESTS_CLUTTER };

	public static List<LootNode> getLoots(OTDLoottables loots, Random random) {

		if (loots == DEFAULT) {
			OTDLoottables next = LOOTS[random.nextInt(LOOTS.length)];
			return getLoots(next, random);
		}

		List<LootNode> res = new ArrayList<>();
		if (loots == CHESTS_TREASURE) {
			res.add(new LootNode(new ItemStack(Material.MUSIC_DISC_FAR), 0.7, 1, 1));
			res.add(new LootNode(new ItemStack(Material.MUSIC_DISC_MALL), 0.7, 1, 1));
			{
				ItemStack is = new ItemStack(Material.BOW);
				ItemMeta im = is.getItemMeta();
				im.addEnchant(Enchantment.POWER, 4, false);
				is.setItemMeta(im);
				res.add(new LootNode(is, 0.1, 1, 1));
			}
			{
				ItemStack is = new ItemStack(Material.BOW);
				ItemMeta im = is.getItemMeta();
				im.addEnchant(Enchantment.INFINITY, 1, false);
				is.setItemMeta(im);
				res.add(new LootNode(is, 0.1, 1, 1));
			}
			res.add(new LootNode(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), 0.1, 1, 1));
			res.add(new LootNode(new ItemStack(Material.COMPASS), 0.9, 4, 1));
			res.add(new LootNode(new ItemStack(Material.BOOKSHELF), 0.7, 4, 1));
			res.add(new LootNode(new ItemStack(Material.BOOK), 0.9, 13, 4));
			res.add(new LootNode(new ItemStack(Material.IRON_HORSE_ARMOR), 0.3, 1, 1));
			res.add(new LootNode(new ItemStack(Material.GOLDEN_HORSE_ARMOR), 0.3, 1, 1));
			res.add(new LootNode(new ItemStack(Material.DIAMOND_HORSE_ARMOR), 0.3, 1, 1));
			res.add(new LootNode(new ItemStack(Material.NAME_TAG), 0.7, 1, 1));
		}
		if (loots == CHESTS_EQUIPMENT) {
			res.add(new LootNode(new ItemStack(Material.GOLDEN_BOOTS), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.GOLDEN_LEGGINGS), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.GOLDEN_CHESTPLATE), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.GOLDEN_HELMET), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.GOLD_INGOT), 0.6, 12, 5));
			res.add(new LootNode(new ItemStack(Material.GOLD_NUGGET), 0.8, 29, 14));

			res.add(new LootNode(new ItemStack(Material.IRON_BOOTS), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.IRON_LEGGINGS), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.IRON_CHESTPLATE), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.IRON_HELMET), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.IRON_INGOT), 0.6, 12, 5));
			res.add(new LootNode(new ItemStack(Material.IRON_NUGGET), 0.8, 29, 14));
		}
		if (loots == CHESTS_FOOD) {
			res.add(new LootNode(new ItemStack(Material.BREAD), 0.6, 4, 1));
			res.add(new LootNode(new ItemStack(Material.BAKED_POTATO), 0.6, 4, 1));
			res.add(new LootNode(new ItemStack(Material.GOLDEN_CARROT), 0.6, 4, 1));
			res.add(new LootNode(new ItemStack(Material.APPLE), 0.6, 4, 1));
			res.add(new LootNode(new ItemStack(Material.MUSHROOM_STEW), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.MUSHROOM_STEW), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.MUSHROOM_STEW), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.BEETROOT_SOUP), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.PUMPKIN_PIE), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.MELON_SLICE), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.RABBIT_STEW), 0.6, 1, 1));
			res.add(new LootNode(new ItemStack(Material.DRIED_KELP), 0.6, 40, 20));
		}
		if (loots == CHESTS_MATERIAL) {
			res.add(new LootNode(new ItemStack(Material.MAGMA_CREAM), 0.6, 7, 3));
			res.add(new LootNode(new ItemStack(Material.SLIME_BALL), 0.6, 7, 3));
			res.add(new LootNode(new ItemStack(Material.COBWEB), 0.6, 7, 3));
			res.add(new LootNode(new ItemStack(Material.FIRE_CHARGE), 0.6, 7, 3));
			res.add(new LootNode(new ItemStack(Material.FIRE_CHARGE), 0.6, 7, 3));
			res.add(new LootNode(new ItemStack(Material.LEATHER), 0.6, 20, 5));
			res.add(new LootNode(new ItemStack(Material.RABBIT_HIDE), 0.6, 20, 5));
			res.add(new LootNode(new ItemStack(Material.SEA_PICKLE), 0.6, 10, 5));
		}
		if (loots == CHESTS_CLUTTER) {
			res.add(new LootNode(new ItemStack(Material.IRON_NUGGET), 0.8, 29, 14));
			res.add(new LootNode(new ItemStack(Material.GOLD_NUGGET), 0.8, 29, 14));
			res.add(new LootNode(new ItemStack(Material.GRAVEL), 0.8, 12, 3));
			res.add(new LootNode(new ItemStack(Material.GRAVEL), 0.8, 12, 3));
			res.add(new LootNode(new ItemStack(Material.FLINT), 0.8, 12, 3));
			res.add(new LootNode(new ItemStack(Material.STICK), 0.8, 12, 3));
			res.add(new LootNode(new ItemStack(Material.GRAY_WOOL), 0.8, 12, 3));
			res.add(new LootNode(new ItemStack(Material.WHITE_CARPET), 0.8, 12, 3));
			res.add(new LootNode(new ItemStack(Material.GLASS_BOTTLE), 0.8, 12, 3));
			res.add(new LootNode(new ItemStack(Material.TORCH), 0.8, 12, 3));
		}
		return res;
	}
}
