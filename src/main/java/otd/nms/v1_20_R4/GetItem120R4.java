package otd.nms.v1_20_R4;

import otd.nms.GetItem;

public class GetItem120R4 implements GetItem {

	public Object get(String itemName) {
		Object obj = null;
		obj = getInner(itemName);
		return obj;
	}

	private Object getInner(String itemName) {
		net.minecraft.nbt.CompoundTag item = new net.minecraft.nbt.CompoundTag();
		if (itemName == null)
			return item;
		item.putString("id", itemName);
		item.putInt("Count", 1);
		return item;
	}
}
