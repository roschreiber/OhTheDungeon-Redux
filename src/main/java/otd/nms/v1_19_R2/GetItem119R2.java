package otd.nms.v1_19_R2;

import otd.nms.GetItem;

public class GetItem119R2 implements GetItem {

	public Object get(String itemName) {
		Object obj = null;
		obj = getInner(itemName);
		return obj;
	}

	private Object getInner(String itemName) {
		net.minecraft.nbt.NBTTagCompound item = new net.minecraft.nbt.NBTTagCompound();
		if (itemName == null)
			return item;
		item.a("id", itemName);
		item.a("Count", 1);
		return item;
	}
}
