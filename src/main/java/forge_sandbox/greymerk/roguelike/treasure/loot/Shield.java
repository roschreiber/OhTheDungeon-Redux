package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;

public class Shield {

	public static ItemStack get(Random rand) {

		ItemStack banner = Banner.get(rand);

		ItemStack shield = new ItemStack(Material.SHIELD, 1);

		shield = applyBanner(banner, shield);

		return shield;
	}

	public static ItemStack applyBanner(ItemStack banner, ItemStack shield) {
		// TODO
		return shield;
		/*
		 * int number = 0; Material type = banner.getType(); switch (type) { case
		 * BLACK_BANNER: number = 0; break; case BLUE_BANNER: number = 1; break; case
		 * BROWN_BANNER: number = 2; break; case CYAN_BANNER: number = 3; break; case
		 * GRAY_BANNER: number = 4; break; case GREEN_BANNER: number = 5; break; case
		 * LIGHT_BLUE_BANNER: number = 6; break; case LIGHT_GRAY_BANNER: number = 7;
		 * break; case LIME_BANNER: number = 8; break; case MAGENTA_BANNER: number = 9;
		 * break; case ORANGE_BANNER: number = 10; break; case PINK_BANNER: number = 11;
		 * break; case PURPLE_BANNER: number = 12; break; case RED_BANNER: number = 13;
		 * break; case WHITE_BANNER: number = 14; break; case YELLOW_BANNER: number =
		 * 15; break; default: break; }
		 * 
		 * ItemMeta im = banner.getItemMeta(); shield.setItemMeta(im); return shield;
		 */

//            net.minecraft.server.*.ItemStack nms_item = CraftItemStack.asNMSCopy(banner);
//            NBTTagCompound nbt = nms_item.getOrCreateTag();
//            NBTTagCompound bannerNBT = nbt.getCompound("BlockEntityTag");
//            NBTTagCompound shieldNBT = bannerNBT == null ? new NBTTagCompound() : bannerNBT.clone();
//            shieldNBT.setInt("Base", number);
//            
//            net.minecraft.server.*.ItemStack nms_shield = CraftItemStack.asNMSCopy(shield);
//            nms_shield.setTag(shieldNBT);
//            return CraftItemStack.asBukkitCopy(nms_shield);

		// TODO
//            NBTTagCompound bannerNBT = banner.getSubCompound("BlockEntityTag");
//            NBTTagCompound shieldNBT = bannerNBT == null ? new NBTTagCompound() : bannerNBT.copy();
//            shieldNBT.setInteger("Base", banner.getMetadata() & 15);
//            shield.setTagInfo("BlockEntityTag", shieldNBT);
	}

}
