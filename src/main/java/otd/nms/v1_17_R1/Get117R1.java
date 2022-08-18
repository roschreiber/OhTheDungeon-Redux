package otd.nms.v1_17_R1;

import org.bukkit.inventory.ItemStack;

import otd.nms.Get;

public class Get117R1 implements Get {
	public ItemStack get(ItemStack item, Object nbt) {
		net.minecraft.world.item.ItemStack tmp = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack
				.asNMSCopy(item);
		tmp.c((net.minecraft.nbt.NBTTagCompound) nbt);
//		try {
//			MultiVersion.NMS_1_17.ItemStack_SetTag.invoke(tmp, nbt);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			if (Main.DEBUG) {
//				Bukkit.getLogger().log(Level.SEVERE, ExceptionRepoter.exceptionToString(e));
//			}
//		}
		// tmp.setTag((net.minecraft.nbt.NBTTagCompound) nbt);
		item = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asBukkitCopy(tmp);
		return item;
	}
}
