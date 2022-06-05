package otd.nms.v1_16_R2;

import otd.nms.GetItem;

public class GetItem116R2 implements GetItem {
	public Object get(String itemName) {
		net.minecraft.server.v1_16_R2.NBTTagCompound item = new net.minecraft.server.v1_16_R2.NBTTagCompound();
		if (itemName == null)
			return item;
		item.setString("id", itemName);
		item.setInt("Count", 1);
		return item;
	}
}
