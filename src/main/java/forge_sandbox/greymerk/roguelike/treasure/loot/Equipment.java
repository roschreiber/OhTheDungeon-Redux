package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;

public enum Equipment {

	SWORD, BOW, HELMET, CHEST, LEGS, FEET, PICK, AXE, SHOVEL;

	public static Equipment[] armour = new Equipment[] { HELMET, CHEST, LEGS, FEET };

	public static String getName(Equipment type, Quality quality) {

		String qualityName;
		String itemName;

		switch (type) {
		case SWORD:
			itemName = "sword";
			break;
		case BOW:
			return "minecraft:bow";
		case HELMET:
			itemName = "helmet";
			break;
		case CHEST:
			itemName = "chestplate";
			break;
		case LEGS:
			itemName = "leggings";
			break;
		case FEET:
			itemName = "boots";
			break;
		case PICK:
			itemName = "pickaxe";
			break;
		case AXE:
			itemName = "axe";
			break;
		case SHOVEL:
			itemName = "shovel";
			break;
		default:
			return "minecraft:stick";
		}

		if (Arrays.asList(Equipment.armour).contains(type)) {
			switch (quality) {
			case WOOD:
				qualityName = "leather";
				break;
			case STONE:
				qualityName = "chainmail";
				break;
			case IRON:
				qualityName = "iron";
				break;
			case GOLD:
				qualityName = "golden";
				break;
			case DIAMOND:
				qualityName = "diamond";
				break;
			default:
				return "minecraft:stick";
			}
		} else {
			switch (quality) {
			case WOOD:
				qualityName = "wooden";
				break;
			case STONE:
				qualityName = "stone";
				break;
			case IRON:
				qualityName = "iron";
				break;
			case GOLD:
				qualityName = "golden";
				break;
			case DIAMOND:
				qualityName = "diamond";
				break;
			default:
				return "minecraft:stick";
			}
		}

		return "minecraft:" + qualityName + "_" + itemName;
	}

	public static ItemStack get(Equipment type, Quality quality) {
		switch (type) {
		case SWORD:
			switch (quality) {
			case WOOD:
				return new ItemStack(Material.WOODEN_SWORD);
			case STONE:
				return new ItemStack(Material.STONE_SWORD);
			case IRON:
				return new ItemStack(Material.IRON_SWORD);
			case GOLD:
				return new ItemStack(Material.GOLDEN_SWORD);
			case DIAMOND:
				return new ItemStack(Material.DIAMOND_SWORD);
			default:
			}
		case BOW:
			return new ItemStack(Material.BOW);
		case HELMET:
			switch (quality) {
			case WOOD:
				return new ItemStack(Material.LEATHER_HELMET);
			case STONE:
				return new ItemStack(Material.CHAINMAIL_HELMET);
			case IRON:
				return new ItemStack(Material.IRON_HELMET);
			case GOLD:
				return new ItemStack(Material.GOLDEN_HELMET);
			case DIAMOND:
				return new ItemStack(Material.DIAMOND_HELMET);
			default:
			}
		case CHEST:
			switch (quality) {
			case WOOD:
				return new ItemStack(Material.LEATHER_CHESTPLATE);
			case STONE:
				return new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			case IRON:
				return new ItemStack(Material.IRON_CHESTPLATE);
			case GOLD:
				return new ItemStack(Material.GOLDEN_CHESTPLATE);
			case DIAMOND:
				return new ItemStack(Material.DIAMOND_CHESTPLATE);
			default:
			}
		case LEGS:
			switch (quality) {
			case WOOD:
				return new ItemStack(Material.LEATHER_LEGGINGS);
			case STONE:
				return new ItemStack(Material.CHAINMAIL_LEGGINGS);
			case IRON:
				return new ItemStack(Material.IRON_LEGGINGS);
			case GOLD:
				return new ItemStack(Material.GOLDEN_LEGGINGS);
			case DIAMOND:
				return new ItemStack(Material.DIAMOND_LEGGINGS);
			default:
			}
		case FEET:
			switch (quality) {
			case WOOD:
				return new ItemStack(Material.LEATHER_BOOTS);
			case STONE:
				return new ItemStack(Material.CHAINMAIL_BOOTS);
			case IRON:
				return new ItemStack(Material.IRON_BOOTS);
			case GOLD:
				return new ItemStack(Material.GOLDEN_BOOTS);
			case DIAMOND:
				return new ItemStack(Material.DIAMOND_BOOTS);
			default:
			}
		case PICK:
			switch (quality) {
			case WOOD:
				return new ItemStack(Material.WOODEN_PICKAXE);
			case STONE:
				return new ItemStack(Material.STONE_PICKAXE);
			case IRON:
				return new ItemStack(Material.IRON_PICKAXE);
			case GOLD:
				return new ItemStack(Material.GOLDEN_PICKAXE);
			case DIAMOND:
				return new ItemStack(Material.DIAMOND_PICKAXE);
			default:
			}
		case AXE:
			switch (quality) {
			case WOOD:
				return new ItemStack(Material.WOODEN_AXE);
			case STONE:
				return new ItemStack(Material.STONE_AXE);
			case IRON:
				return new ItemStack(Material.IRON_AXE);
			case GOLD:
				return new ItemStack(Material.GOLDEN_AXE);
			case DIAMOND:
				return new ItemStack(Material.DIAMOND_AXE);
			default:
			}
		case SHOVEL:
			switch (quality) {
			case WOOD:
				return new ItemStack(Material.WOODEN_SHOVEL);
			case STONE:
				return new ItemStack(Material.STONE_SHOVEL);
			case IRON:
				return new ItemStack(Material.IRON_SHOVEL);
			case GOLD:
				return new ItemStack(Material.GOLDEN_SHOVEL);
			case DIAMOND:
				return new ItemStack(Material.DIAMOND_SHOVEL);
			default:
			}
		default:
			return new ItemStack(Material.STICK);
		}
	}
}
