package otd.nms;

public class GetItem {

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
