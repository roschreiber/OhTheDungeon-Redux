package otd.nms.v1_19_R3;

import org.bukkit.inventory.ItemStack;

import otd.nms.Get;

public class Get119R3 implements Get {
	public ItemStack get(ItemStack item, Object nbt) {
		net.minecraft.world.item.ItemStack tmp = org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack
				.asNMSCopy(item);
		tmp.c((net.minecraft.nbt.NBTTagCompound) nbt);

		item = org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack.asBukkitCopy(tmp);
		return item;
	}
}
