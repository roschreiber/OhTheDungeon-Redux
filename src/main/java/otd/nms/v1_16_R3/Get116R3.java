package otd.nms.v1_16_R3;

import org.bukkit.inventory.ItemStack;

import otd.nms.Get;

public class Get116R3 implements Get {
	public ItemStack get(ItemStack item, Object nbt) {
		net.minecraft.server.v1_16_R3.ItemStack tmp = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
				.asNMSCopy(item);
		tmp.setTag((net.minecraft.server.v1_16_R3.NBTTagCompound) nbt);
		item = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asBukkitCopy(tmp);
		return item;
	}
}
