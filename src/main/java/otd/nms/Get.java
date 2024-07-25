package otd.nms;

import org.bukkit.inventory.ItemStack;

public class Get {
	public ItemStack get(ItemStack item, Object nbt) {
		//net.minecraft.world.item.ItemStack tmp = org.bukkit.craftbukkit.v1_20_R4.inventory.CraftItemStack.asNMSCopy(item);
		//tmp.c((net.minecraft.nbt.NBTTagCompound) nbt);
		//item = org.bukkit.craftbukkit.v1_20_R4.inventory.CraftItemStack.asBukkitCopy(tmp);
		return item;
	}
}
