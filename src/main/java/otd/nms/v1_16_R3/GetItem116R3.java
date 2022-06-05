package otd.nms.v1_16_R3;

import otd.nms.GetItem;

public class GetItem116R3 implements GetItem {
	public Object get(String itemName) {
		net.minecraft.server.v1_16_R3.NBTTagCompound item = new net.minecraft.server.v1_16_R3.NBTTagCompound();
		if (itemName == null)
			return item;
		item.setString("id", itemName);
		item.setInt("Count", 1);
		return item;
	}
}
