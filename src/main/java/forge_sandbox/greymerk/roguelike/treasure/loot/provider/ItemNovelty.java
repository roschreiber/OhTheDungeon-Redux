package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import forge_sandbox.greymerk.roguelike.treasure.loot.Enchant;
import forge_sandbox.greymerk.roguelike.treasure.loot.Loot;
import forge_sandbox.greymerk.roguelike.util.IWeighted;
import forge_sandbox.greymerk.roguelike.util.TextFormat;
import forge_sandbox.greymerk.roguelike.util.WeightedChoice;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import otd.util.I18n;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;

public enum ItemNovelty {

	GREYMERK, NEBRISCROWN, NULL, MANPANTS, ZISTEAUSIGN, AVIDYA, ASHLEA, KURT, AMLP, CLEO, ENIKOSWORD, ENIKOBOW,
	BDOUBLEO, GUUDE, RLEAHY, ETHO, BAJ, DOCM, GINGER, VECHS, NOTCH, QUANTUMLEAP, GENERIKB, FOURLES, DINNERBONE, GRIM,
	MMILLSS, VALANDRAH;

	public static final Map<String, ItemNovelty> names;
	static {
		names = new HashMap<>();
		names.put("greymerk", ItemNovelty.GREYMERK);
		names.put("nebriscrown", ItemNovelty.NEBRISCROWN);
		names.put("nebrissword", ItemNovelty.NULL);
		names.put("zisteaupants", ItemNovelty.MANPANTS);
		names.put("zisteausign", ItemNovelty.ZISTEAUSIGN);
		names.put("avidya", ItemNovelty.AVIDYA);
		names.put("ashlea", ItemNovelty.ASHLEA);
		names.put("kurt", ItemNovelty.KURT);
		names.put("amlp", ItemNovelty.AMLP);
		names.put("cleo", ItemNovelty.CLEO);
		names.put("enikosword", ItemNovelty.ENIKOSWORD);
		names.put("enikobow", ItemNovelty.ENIKOBOW);
		names.put("bdoubleo", ItemNovelty.BDOUBLEO);
		names.put("guude", ItemNovelty.GUUDE);
		names.put("rleahy", ItemNovelty.RLEAHY);
		names.put("etho", ItemNovelty.ETHO);
		names.put("baj", ItemNovelty.BAJ);
		names.put("docm", ItemNovelty.DOCM);
		names.put("ginger", ItemNovelty.GINGER);
		names.put("vechs", ItemNovelty.VECHS);
		names.put("notch", ItemNovelty.NOTCH);
		names.put("quantumleap", ItemNovelty.QUANTUMLEAP);
		names.put("generikb", ItemNovelty.GENERIKB);
		names.put("fourles", ItemNovelty.FOURLES);
		names.put("dinnerbone", ItemNovelty.DINNERBONE);
		names.put("grim", ItemNovelty.GRIM);
		names.put("mmillss", ItemNovelty.MMILLSS);
		names.put("valandrah", ItemNovelty.VALANDRAH);
	};

	public static ItemStack getItemByName(String name) {
		if (!names.containsKey(name))
			return null;
		return getItem(names.get(name));
	}

	public static IWeighted<ItemStack> get(JsonObject data, int weight) throws Exception {
		if (!data.has("name"))
			throw new Exception("Novelty item requires a name");
		String name = data.get("name").getAsString();
		ItemStack item = getItemByName(name);
		if (item == null)
			throw new Exception("No such novelty name: " + name);
		return new WeightedChoice<>(item, weight);
	}

	public static IWeighted<ItemStack> get(ItemNovelty choice, int weight) {
		return new WeightedChoice<>(getItem(choice), weight);
	}

	public static ItemStack getItem(ItemNovelty choice) {

		ItemStack item;

		switch (choice) {

		case GREYMERK:
			item = new ItemStack(Material.IRON_AXE);
			item = Loot.setItemNameNew(item, I18n.instance.GREYMERK.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.GREYMERK.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 2);
			return item;
		case NEBRISCROWN:
			item = new ItemStack(Material.GOLDEN_HELMET);
			item = Loot.setItemNameNew(item, I18n.instance.NEBRISCROWN.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.NEBRISCROWN.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROTECTION), 4);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			return item;
		case NULL:
			item = new ItemStack(Material.DIAMOND_SWORD);
			item = Loot.setItemNameNew(item, I18n.instance.NULL.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.NULL.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			return item;
		case MANPANTS:
			item = new ItemStack(Material.LEATHER_LEGGINGS);
			item = Loot.setItemNameNew(item, I18n.instance.MANPANTS.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.MANPANTS.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), 4);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			ItemArmour.dyeArmor(item, 250, 128, 114);
			return item;
		case ZISTEAUSIGN:
			item = new ItemStack(Material.OAK_SIGN);
			item = Loot.setItemNameNew(item, I18n.instance.ZISTEAUSIGN.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.ZISTEAUSIGN.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
			return item;
		case AVIDYA:
			item = new ItemStack(Material.MILK_BUCKET);
			item = Loot.setItemNameNew(item, I18n.instance.AVIDYA.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.AVIDYA.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.ARTHOPODS), 4);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
			return item;
		case ASHLEA:
			item = new ItemStack(Material.COOKIE);
			item = Loot.setItemNameNew(item, I18n.instance.ASHLEA.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.ASHLEA.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			return item;
		case KURT:
			item = new ItemStack(Material.LEATHER_BOOTS);
			item = Loot.setItemNameNew(item, I18n.instance.KURT.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.KURT.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROTECTION), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FEATHERFALLING), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			ItemArmour.dyeArmor(item, 165, 42, 42);
			return item;
		case AMLP:
			item = new ItemStack(Material.SHEARS);
			item = Loot.setItemNameNew(item, I18n.instance.AMLP.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.AMLP.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
			return item;
		case CLEO:
			item = new ItemStack(Material.COD);
			item = Loot.setItemNameNew(item, I18n.instance.CLEO.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.CLEO.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 10);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 5);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FORTUNE), 5);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 10);
			return item;
		case BDOUBLEO:
			item = new ItemStack(Material.DIAMOND_SHOVEL);
			item = Loot.setItemNameNew(item, I18n.instance.BDOUBLEO.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.BDOUBLEO.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 5);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			return item;
		case GUUDE:
			item = new ItemStack(Material.MUSIC_DISC_13);
			item = Loot.setItemNameNew(item, I18n.instance.GUUDE.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.GUUDE.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), 3);
			return item;
		case RLEAHY:
			item = new ItemStack(Material.BREAD);
			item = Loot.setItemNameNew(item, I18n.instance.RLEAHY.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.RLEAHY.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 2);
			return item;
		case ETHO:
			item = new ItemStack(Material.WOODEN_PICKAXE);
			item = Loot.setItemNameNew(item, I18n.instance.ETHO.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.ETHO.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 5);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			return item;
		case ENIKOBOW:
			item = new ItemStack(Material.BOW);
			item = Loot.setItemNameNew(item, I18n.instance.ENIKOBOW.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.ENIKOBOW.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.POWER), 5);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.INFINITY), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			return item;
		case ENIKOSWORD:
			item = new ItemStack(Material.DIAMOND_SWORD);
			item = Loot.setItemNameNew(item, I18n.instance.ENIKOSWORD.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.ENIKOSWORD.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.LOOTING), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			return item;
		case BAJ:
			item = new ItemStack(Material.GOLDEN_HOE);
			item = Loot.setItemNameNew(item, I18n.instance.BAJ.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.BAJ.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FORTUNE), 5);
			return item;
		case DOCM:
			item = new ItemStack(Material.FISHING_ROD);
			item = Loot.setItemNameNew(item, I18n.instance.DOCM.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.DOCM.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			return item;
		case GINGER:
			item = new ItemStack(Material.COOKED_CHICKEN);
			item = Loot.setItemNameNew(item, I18n.instance.GINGER.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.GINGER.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 1);
			return item;
		case VECHS:
			item = new ItemStack(Material.STICK);
			item = Loot.setItemNameNew(item, I18n.instance.VECHS.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.VECHS.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 1);
			return item;
		case NOTCH:
			item = new ItemStack(Material.APPLE);
			item = Loot.setItemNameNew(item, I18n.instance.NOTCH.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.NOTCH.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 10);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 10);
			return item;
		case QUANTUMLEAP:
			item = new ItemStack(Material.SPONGE);
			item = Loot.setItemNameNew(item, I18n.instance.QUANTUMLEAP.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.QUANTUMLEAP.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 4);
			return item;
		case GENERIKB:
			item = new ItemStack(Material.BAKED_POTATO);
			item = Loot.setItemNameNew(item, I18n.instance.GENERIKB.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.GENERIKB.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			return item;
		case FOURLES:
			item = new ItemStack(Material.COCOA_BEANS, 1);
			item = Loot.setItemNameNew(item, I18n.instance.FOURLES.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.FOURLES.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
			return item;
		case DINNERBONE:
			item = new ItemStack(Material.BONE, 1);
			item = Loot.setItemNameNew(item, I18n.instance.DINNERBONE.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.DINNERBONE.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 2);
			return item;
		case GRIM:
			item = new ItemStack(Material.ROTTEN_FLESH);
			item = Loot.setItemNameNew(item, I18n.instance.GRIM.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.GRIM.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SMITE), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.LOOTING), 1);
			return item;
		case MMILLSS:
			item = new ItemStack(Material.CACTUS);
			item = Loot.setItemNameNew(item, I18n.instance.MMILLSS.Name, TextFormat.DARKPURPLE);
			item = Loot.setItemLoreNew(item, I18n.instance.MMILLSS.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.ARTHOPODS), 4);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.THORNS), 2);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.LOOTING), 1);
			return item;
		case VALANDRAH:
			item = new ItemStack(Material.IRON_SWORD);
			item = Loot.setItemNameNew(item, I18n.instance.VALANDRAH.Name, null);
			item = Loot.setItemLoreNew(item, I18n.instance.VALANDRAH.Lore, TextFormat.DARKGREEN);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 4);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 2);
			return item;
		default:
			return null;

		}
	}

}
