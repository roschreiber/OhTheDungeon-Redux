package forge_sandbox.greymerk.roguelike.treasure.loot;

//import net.minecraft.item.ItemStack;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

//import net.minecraft.nbt.NBTTagCompound;

public enum ItemHideFlags {

	ENCHANTMENTS, ATTRIBUTES, UNBREAKABLE, CANDESTROY, CANPLACEON, EFFECTS;

	public static void set(ItemHideFlags[] flags, ItemStack item) {

		int val = 0;

		for (ItemHideFlags flag : flags) {
			val += get(flag);
		}

		ItemMeta im = item.getItemMeta();
		if (val >= 32) {
			val -= 32;
			im.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		}
		if (val >= 16) {
			val -= 16;
			im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		}
		if (val >= 8) {
			val -= 8;
			im.addItemFlags(ItemFlag.HIDE_DESTROYS);
		}
		if (val >= 4) {
			val -= 4;
			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		}
		if (val >= 2) {
			val -= 2;
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		}
		if (val >= 1) {
			val -= 1;
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(im);
	}

	public static void set(ItemHideFlags flag, ItemStack item) {
		set(new ItemHideFlags[] { flag }, item);
	}

	public static int get(ItemHideFlags flag) {
		switch (flag) {
		case ENCHANTMENTS:
			return 1;
		case ATTRIBUTES:
			return 2;
		case UNBREAKABLE:
			return 4;
		case CANDESTROY:
			return 8;
		case CANPLACEON:
			return 16;
		case EFFECTS:
			return 32;
		default:
			return 0;
		}
	}

}
