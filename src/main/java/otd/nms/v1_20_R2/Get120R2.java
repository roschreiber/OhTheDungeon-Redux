package otd.nms.v1_20_R1;

import org.bukkit.inventory.ItemStack;

import otd.nms.Get;

public class Get120R1 implements Get {
	public ItemStack get(ItemStack item, Object nbt) {
		net.minecraft.world.item.ItemStack tmp = org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack
				.asNMSCopy(item);
		tmp.c((net.minecraft.nbt.NBTTagCompound) nbt);

		item = org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack.asBukkitCopy(tmp);
		return item;
	}
}
