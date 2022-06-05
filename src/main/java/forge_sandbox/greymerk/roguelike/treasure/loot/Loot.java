package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.Random;

import com.google.gson.JsonObject;
import forge_sandbox.Sandbox;

import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemBlock;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemBrewing;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemEnchBonus;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemFood;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemJunk;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemMixture;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemOre;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemPotion;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemRecord;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemSmithy;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemSupply;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemTool;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import forge_sandbox.greymerk.roguelike.util.IWeighted;
import forge_sandbox.greymerk.roguelike.util.TextFormat;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Loot {

	WEAPON, ARMOUR, BLOCK, JUNK, ORE, TOOL, POTION, FOOD, ENCHANTBOOK, ENCHANTBONUS, SUPPLY, MUSIC, SMITHY, SPECIAL,
	REWARD, BREWING;

	public static ILoot getLoot() {

		LootProvider loot = new LootProvider();
		for (int i = 0; i < 5; ++i) {
			loot.put(i, new LootSettings(i));
		}

		return loot;
	}

	public static IWeighted<ItemStack> get(JsonObject data, int weight) throws Exception {

		if (!data.has("type"))
			return new WeightedRandomLoot(data, weight);

		String type = data.get("type").getAsString().toLowerCase();

		switch (type) {
		case "potion":
			return Potion.get(data, weight);
		case "mixture":
			return new ItemMixture(data, weight);
		case "weapon":
			return new ItemWeapon(data, weight);
		case "specialty":
			return new ItemSpecialty(data, weight);
		case "novelty":
			return ItemNovelty.get(data, weight);
		case "tool":
			return new ItemTool(data, weight);
		case "armour":
			return new ItemArmour(data, weight);
		case "enchanted_book":
			return new ItemEnchBook(data, weight);
		default:
			throw new Exception("No such loot type as: " + type);
		}
	}

	public static IWeighted<ItemStack> getProvider(Loot type, int level) {
		if (!Sandbox.set.loots.contains(type))
			return new WeightedRandomLoot(Material.STICK, 0, 1);
		switch (type) {
		case WEAPON:
			return new ItemWeapon(0, level);
		case ARMOUR:
			return new ItemArmour(0, level);
		case BLOCK:
			return new ItemBlock(0, level);
		case JUNK:
			return new ItemJunk(0, level);
		case ORE:
			return new ItemOre(0, level);
		case TOOL:
			return new ItemTool(0, level);
		case POTION:
			return new ItemPotion(0, level);
		case BREWING:
			return new ItemBrewing(0, level);
		case FOOD:
			return new ItemFood(0, level);
		case ENCHANTBOOK:
			return new ItemEnchBook(0, level);
		case ENCHANTBONUS:
			return new ItemEnchBonus(0, level);
		case SUPPLY:
			return new ItemSupply(0, level);
		case MUSIC:
			return new ItemRecord(0, level);
		case SMITHY:
			return new ItemSmithy(0, level);
		case SPECIAL:
			return new ItemSpecialty(0, level);
		case REWARD:
		}

		return new WeightedRandomLoot(Material.STICK, 0, 1);
	}

	public static ItemStack getEquipmentBySlot(Random rand, EquipmentSlot slot, int level, boolean enchant) {
		if (slot == EquipmentSlot.HAND) {
			return ItemWeapon.getRandom(rand, level, enchant);
		}

		return ItemArmour.getRandom(rand, level, Slot.getSlot(slot), enchant);
	}

	public static ItemStack getEquipmentBySlot(Random rand, Slot slot, int level, boolean enchant) {

		if (slot == Slot.WEAPON) {
			return ItemWeapon.getRandom(rand, level, enchant);
		}

		return ItemArmour.getRandom(rand, level, slot, enchant);
	}

	@SuppressWarnings("deprecation")
	public static ItemStack setItemLoreNew(ItemStack item, String loreText) {
		ItemMeta im = item.getItemMeta();
		List<String> lore = new ArrayList<>();
		if (im.hasLore())
			lore = im.getLore();
		lore.add(loreText);
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}

	public static ItemStack setItemLoreNew(ItemStack item, String loreText, TextFormat option) {
		return setItemLoreNew(item, TextFormat.apply(loreText, option));
	}

	@SuppressWarnings("deprecation")
	public static ItemStack setItemNameNew(ItemStack item, String name, TextFormat option) {
		if (option == null) {
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(name);
			item.setItemMeta(im);
			return item;
		}
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(TextFormat.apply(name, option));
		item.setItemMeta(im);
		return item;
	}

	public static ItemStack setItemNameNew(ItemStack item, String name) {
		return setItemNameNew(item, name, null);
	}
}
