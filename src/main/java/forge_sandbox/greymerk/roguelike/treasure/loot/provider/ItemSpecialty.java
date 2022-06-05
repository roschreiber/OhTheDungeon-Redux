package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import com.google.gson.JsonObject;

import forge_sandbox.greymerk.roguelike.treasure.loot.Enchant;
import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Loot;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.util.TextFormat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import otd.util.I18n;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;

public class ItemSpecialty extends ItemBase {

	private Equipment type;
	private Quality quality;

	public ItemSpecialty(int weight, int level) {
		super(weight, level);
	}

	public ItemSpecialty(JsonObject data, int weight) throws Exception {
		super(weight);
		if (!data.has("level"))
			throw new Exception("Item requires a level");
		this.level = data.get("level").getAsInt();

		if (data.has("quality")) {
			try {
				this.quality = Quality.valueOf(data.get("quality").getAsString().toUpperCase());
			} catch (Exception e) {
				throw new Exception("No such Quality as: " + data.get("quality").getAsString());
			}
		}

		if (data.has("equipment")) {
			try {
				this.type = Equipment.valueOf(data.get("equipment").getAsString().toUpperCase());
			} catch (Exception e) {
				throw new Exception("No such Equipment as: " + data.get("equipment").getAsString());
			}
		}
	}

	public ItemSpecialty(int weight, int level, Equipment type, Quality q) {
		super(weight, level);
		this.type = type;
		this.quality = q;
	}

	public ItemSpecialty(int weight, int level, Quality q) {
		super(weight, level);
		this.quality = q;
	}

	@Override
	public ItemStack get(Random rand) {
		Equipment t = this.type == null ? Equipment.values()[rand.nextInt(Equipment.values().length)] : this.type;
		Quality q = this.quality == null ? Quality.get(rand, level, t) : this.quality;
		return getRandomItem(t, rand, q);
	}

	public static ItemStack getRandomItem(Random rand, int level) {
		return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, level);
	}

	public static ItemStack getRandomItem(Equipment type, Random rand, int level) {
		return getRandomItem(type, rand, Quality.get(rand, level, type));
	}

	public static ItemStack getRandomItem(Equipment type, Random rand, Quality quality) {

		switch (type) {

		case SWORD:
			return getSword(rand, quality);
		case BOW:
			return getBow(rand, quality);
		case HELMET:
			return getHelmet(rand, quality);
		case CHEST:
			return getChest(rand, quality);
		case LEGS:
			return getLegs(rand, quality);
		case FEET:
			return getBoots(rand, quality);
		case PICK:
			return getPick(rand, quality);
		case AXE:
			return getAxe(rand, quality);
		case SHOVEL:
			return getShovel(rand, quality);
		default:
			return null;
		}
	}

	public static ItemStack getRandomArmour(Random rand, Quality quality) {
		switch (rand.nextInt(4)) {
		case 0:
			return getRandomItem(Equipment.HELMET, rand, quality);
		case 1:
			return getRandomItem(Equipment.CHEST, rand, quality);
		case 2:
			return getRandomItem(Equipment.LEGS, rand, quality);
		case 3:
			return getRandomItem(Equipment.FEET, rand, quality);
		default:
			return null;
		}
	}

	public static ItemStack getRandomTool(Random rand, Quality quality) {
		switch (rand.nextInt(3)) {
		case 0:
			return getRandomItem(Equipment.PICK, rand, quality);
		case 1:
			return getRandomItem(Equipment.AXE, rand, quality);
		case 2:
			return getRandomItem(Equipment.SHOVEL, rand, quality);
		default:
			return null;
		}
	}

	private static ItemStack getShovel(Random rand, Quality quality) {
		ItemStack item;
		if (quality == Quality.DIAMOND) {
			item = new ItemStack(Material.DIAMOND_SHOVEL);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 3 + rand.nextInt(3));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			item = Loot.setItemNameNew(item, I18n.instance.Soul_Spade);
			return item;
		} else {
			item = new ItemStack(Material.IRON_SHOVEL);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 1 + rand.nextInt(2));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			item = Loot.setItemNameNew(item, I18n.instance.Grave_Spade);
			return item;
		}
	}

	private static ItemStack getAxe(Random rand, Quality quality) {

		ItemStack item;
		if (quality == Quality.DIAMOND) {
			item = new ItemStack(Material.DIAMOND_AXE);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 3 + rand.nextInt(3));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			item = Loot.setItemNameNew(item, I18n.instance.Crystal_Head_Axe);
			return item;
		} else {
			item = new ItemStack(Material.IRON_AXE);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 1 + rand.nextInt(2));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			item = Loot.setItemNameNew(item, I18n.instance.Woodland_Hatchet);
			return item;
		}
	}

	private static ItemStack getPick(Random rand, Quality quality) {

		ItemStack item;

		if (quality == Quality.DIAMOND) {
			item = new ItemStack(Material.DIAMOND_PICKAXE);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 3 + rand.nextInt(3));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			if (rand.nextInt(10) == 0) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SILKTOUCH), 1);
				item = Loot.setItemNameNew(item, I18n.instance.Crystal_Pick_of_Precision);
				return item;
			}
			if (rand.nextInt(10) == 0) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FORTUNE), 2 + rand.nextInt(2));
				item = Loot.setItemNameNew(item, I18n.instance.Crystal_Pick_of_Prospecting);
				return item;
			}

			if (rand.nextInt(5) == 0) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
			}

			item = Loot.setItemNameNew(item, I18n.instance.Crystal_Pick);
			return item;
		} else {
			item = new ItemStack(Material.IRON_PICKAXE);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 1 + rand.nextInt(2));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			if (rand.nextInt(10) == 0) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SILKTOUCH), 1);
				item = Loot.setItemNameNew(item, I18n.instance.Case_Hardened_Pick_of_Precision);
				return item;
			}
			if (rand.nextInt(10) == 0) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FORTUNE), 1 + rand.nextInt(3));
				item = Loot.setItemNameNew(item, I18n.instance.Case_Hardened_Pick_of_Prospecting);
				return item;
			}

			if (rand.nextInt(5) == 0) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
			}

			item = Loot.setItemNameNew(item, I18n.instance.Case_Hardened_Pick);
			return item;
		}

	}

	private static ItemStack getSword(Random rand, Quality quality) {

		ItemStack item;
		if (quality == Quality.DIAMOND) {
			item = new ItemStack(Material.DIAMOND_SWORD);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3 + rand.nextInt(3));
			if (rand.nextInt(10) == 0) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.LOOTING), 2 + rand.nextInt(2));
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
				item = Loot.setItemNameNew(item, I18n.instance.Eldritch_Blade_of_Plundering.Name);
				item = Loot.setItemLoreNew(item, I18n.instance.Eldritch_Blade_of_Plundering.Lore, TextFormat.DARKGREEN);
				return item;
			}
			if (rand.nextInt(10) == 0) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 2 + rand.nextInt(2));
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
				item = Loot.setItemNameNew(item, I18n.instance.Eldritch_Blade_of_the_Inferno.Name);
				item = Loot.setItemLoreNew(item, I18n.instance.Eldritch_Blade_of_the_Inferno.Lore,
						TextFormat.DARKGREEN);
				return item;
			}
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING),
					quality == Quality.DIAMOND ? 3 : 1 + rand.nextInt(2));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
			item = Loot.setItemNameNew(item, I18n.instance.Eldritch_Blade.Name);
			item = Loot.setItemLoreNew(item, I18n.instance.Eldritch_Blade.Lore, TextFormat.DARKGREEN);
			return item;
		} else {
			item = new ItemStack(Material.IRON_SWORD);
			if (rand.nextBoolean()) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 1);
			}
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
			item = Loot.setItemNameNew(item, I18n.instance.Tempered_Blade.Name);
			item = Loot.setItemLoreNew(item, I18n.instance.Tempered_Blade.Lore, TextFormat.DARKGREEN);
			return item;
		}

	}

	private static ItemStack getBow(Random rand, Quality quality) {

		ItemStack item = new ItemStack(Material.BOW);

		switch (quality) {
		case WOOD:
		case STONE:
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.POWER), 1 + rand.nextInt(3));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 1);
			item = Loot.setItemNameNew(item, I18n.instance.Yew_Longbow.Name);
			item = Loot.setItemLoreNew(item, I18n.instance.Yew_Longbow.Lore, TextFormat.DARKGREEN);
			return item;
		case IRON:
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.POWER), 1 + rand.nextInt(3));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 1 + rand.nextInt(3));
			item = Loot.setItemNameNew(item, I18n.instance.Laminated_Bow.Name);
			item = Loot.setItemLoreNew(item, I18n.instance.Laminated_Bow.Lore, TextFormat.DARKGREEN);
			return item;
		case GOLD:
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.POWER), 3 + rand.nextInt(3));
			if (rand.nextBoolean()) {
				item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.INFINITY), 1);
			}
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 1 + rand.nextInt(3));
			item = Loot.setItemNameNew(item, I18n.instance.Recurve_Bow.Name);
			item = Loot.setItemLoreNew(item, I18n.instance.Recurve_Bow.Lore, TextFormat.DARKGREEN);
			return item;
		case DIAMOND:
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.POWER), 3 + rand.nextInt(3));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FLAME), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.INFINITY), 1);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
			item = Loot.setItemNameNew(item, I18n.instance.Eldritch_Bow.Name);
			item = Loot.setItemLoreNew(item, I18n.instance.Eldritch_Bow.Lore, TextFormat.DARKGREEN);
			return item;
		default:
			return null;
		}
	}

	private static ItemStack getHelmet(Random rand, Quality quality) {
		ItemStack item;

		String canonical = "";

		switch (quality) {
		case WOOD:
			item = new ItemStack(Material.LEATHER_HELMET);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = I18n.instance.Bonnet;
			break;
		case STONE:
			item = new ItemStack(Material.CHAINMAIL_HELMET);
			canonical = I18n.instance.Coif;
			break;
		case IRON:
		case GOLD:
			item = new ItemStack(Material.IRON_HELMET);
			canonical = I18n.instance.Sallet;
			break;
		case DIAMOND:
			item = new ItemStack(Material.DIAMOND_HELMET);
			canonical = I18n.instance.Helm;
			break;
		default:
			item = new ItemStack(Material.LEATHER_HELMET);
		}

		String suffix;

		if (rand.nextInt(20) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.RESPIRATION), 3);
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.AQUAAFFINITY), 1);
			suffix = I18n.instance.Of_Diving;
		} else if (rand.nextInt(3) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION),
					getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Deflection;
		} else {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Defense;
		}

		item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));

		if (rand.nextInt(10) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
		}

		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		item = Loot.setItemNameNew(item, name);
		return item;
	}

	private static ItemStack getBoots(Random rand, Quality quality) {
		ItemStack item;

		String canonical;

		switch (quality) {
		case WOOD:
			item = new ItemStack(Material.LEATHER_BOOTS);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = I18n.instance.Shoes;
			break;
		case STONE:
			item = new ItemStack(Material.CHAINMAIL_BOOTS);
			canonical = I18n.instance.Greaves;
			break;
		case IRON:
		case GOLD:
			item = new ItemStack(Material.IRON_BOOTS);
			canonical = I18n.instance.Sabatons;
			break;
		case DIAMOND:
			item = new ItemStack(Material.DIAMOND_BOOTS);
			canonical = I18n.instance.Boots;
			break;
		default:
			item = new ItemStack(Material.LEATHER_BOOTS);
			canonical = I18n.instance.Shoes;
		}

		String suffix;

		if (rand.nextInt(10) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Warding;
		} else if (rand.nextInt(5) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FEATHERFALLING),
					quality == Quality.DIAMOND ? 4 : 1 + rand.nextInt(3));
			suffix = I18n.instance.Of_Lightness;
		} else if (rand.nextInt(3) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION),
					getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Deflection;
		} else {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Defense;
		}

		item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));

		if (rand.nextInt(10) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
		}

		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		item = Loot.setItemNameNew(item, name);
		return item;
	}

	private static ItemStack getLegs(Random rand, Quality quality) {
		ItemStack item;

		String canonical = "";

		switch (quality) {
		case WOOD:
			item = new ItemStack(Material.LEATHER_LEGGINGS);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = I18n.instance.Pantaloons;
			break;
		case STONE:
			item = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			canonical = I18n.instance.Chausses;
			break;
		case IRON:
		case GOLD:
			item = new ItemStack(Material.IRON_LEGGINGS);
			canonical = I18n.instance.Leg_plates;
			break;
		case DIAMOND:
			item = new ItemStack(Material.DIAMOND_LEGGINGS);
			canonical = I18n.instance.Leggings;
			break;
		default:
			item = new ItemStack(Material.LEATHER_LEGGINGS);
		}

		String suffix;

		if (rand.nextInt(10) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Warding;
		} else if (rand.nextInt(10) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Integrity;
		} else if (rand.nextInt(3) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION),
					getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Deflection;
		} else {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Defense;
		}

		item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));

		if (rand.nextInt(10) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
		}

		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		item = Loot.setItemNameNew(item, name);
		return item;
	}

	private static ItemStack getChest(Random rand, Quality quality) {
		ItemStack item;

		String canonical;

		switch (quality) {
		case WOOD:
			item = new ItemStack(Material.LEATHER_CHESTPLATE);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = I18n.instance.Tunic;
			break;
		case STONE:
			item = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			canonical = I18n.instance.Hauberk;
			break;
		case IRON:
		case GOLD:
			item = new ItemStack(Material.IRON_CHESTPLATE);
			canonical = I18n.instance.Cuirass;
			break;
		case DIAMOND:
			item = new ItemStack(Material.DIAMOND_CHESTPLATE);
			canonical = I18n.instance.Plate;
			break;
		default:
			item = new ItemStack(Material.LEATHER_CHESTPLATE);
			canonical = I18n.instance.Tunic;
		}

		String suffix;

		if (rand.nextInt(10) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Flamewarding;
		} else if (rand.nextInt(10) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Integrity;
		} else if (rand.nextInt(3) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION),
					getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Deflection;
		} else {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
			suffix = I18n.instance.Of_Defense;
		}

		item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));

		if (rand.nextInt(10) == 0) {
			item.addUnsafeEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
		}

		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		item = Loot.setItemNameNew(item, name);
		return item;
	}

	private static int getUnbreakingLevel(Quality quality, Random rand) {
		return quality == Quality.DIAMOND ? 3 : 1 + rand.nextInt(2);
	}

	private static int getProtectionLevel(Quality quality, Random rand) {

		int value = 1;

		switch (quality) {
		case WOOD:
			if (rand.nextInt(3) == 0) {
				value++;
			}
			break;
		case STONE:
			if (rand.nextBoolean()) {
				value++;
			}
			break;
		case IRON:
		case GOLD:
			value += rand.nextInt(3);
			break;
		case DIAMOND:
			value += 2 + rand.nextInt(2);
			break;
		}

		return value;
	}

	private static String getArmourPrefix(Quality quality) {

		switch (quality) {
		case WOOD:
			return I18n.instance.armour_prefix.Surplus;
		case STONE:
			return I18n.instance.armour_prefix.Riveted;
		case IRON:
			return I18n.instance.armour_prefix.Gothic;
		case GOLD:
			return I18n.instance.armour_prefix.Jewelled;
		case DIAMOND:
			return I18n.instance.armour_prefix.Crystal;
		default:
			return I18n.instance.armour_prefix.Strange;
		}
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {

		Quality q;
		switch (level) {
		case 0:
			q = Quality.WOOD;
			break;
		case 1:
			q = Quality.STONE;
			break;
		case 2:
			q = Quality.IRON;
			break;
		case 3:
			q = Quality.GOLD;
			break;
		case 4:
			q = Quality.DIAMOND;
			break;
		default:
			q = Quality.WOOD;
			break;
		}

		return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, q);
	}
}
