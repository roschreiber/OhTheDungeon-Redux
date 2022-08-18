package otd.nms.v1_17_R1;

import otd.nms.GetItem;

public class GetItem117R1 implements GetItem {

	public Object get(String itemName) {
		Object obj = null;
		obj = getInner(itemName);
//		try {
//			obj = getInner(itemName);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			if (Main.DEBUG) {
//				Bukkit.getLogger().log(Level.SEVERE, ExceptionRepoter.exceptionToString(e));
//				;
//			}
//		}
		return obj;
	}

	private Object getInner(String itemName) {
//			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		net.minecraft.nbt.NBTTagCompound item = new net.minecraft.nbt.NBTTagCompound();
		if (itemName == null)
			return item;
		item.a("id", itemName);
		item.a("Count", 1);
//		MultiVersion.NMS_1_17.NBTTagCompound_SetString.invoke(item, "id", itemName);
//		MultiVersion.NMS_1_17.NBTTagCompound_SetInt.invoke(item, "Count", 1);
		// item.setString("id", itemName);
		// item.setInt("Count", 1);
		return item;
	}
}
