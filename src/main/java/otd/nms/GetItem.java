package otd.nms;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;

public class GetItem {

	public Object get(String itemName) {
		return getInner(itemName);
	}

	private Object getInner(String itemName) {
		ReadWriteNBT item = NBT.createNBTObject();
		if (itemName == null)
			return item;
		item.setString("id", itemName);
		item.setInteger("count", 1);
		return item;
	}
}
