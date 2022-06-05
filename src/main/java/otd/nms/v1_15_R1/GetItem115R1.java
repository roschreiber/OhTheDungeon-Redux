package otd.nms.v1_15_R1;

import otd.nms.GetItem;

public class GetItem115R1 implements GetItem {
	public Object get(String itemName) {
		net.minecraft.server.v1_15_R1.NBTTagCompound item = new net.minecraft.server.v1_15_R1.NBTTagCompound();
		if (itemName == null)
			return item;
		item.setString("id", itemName);
		item.setInt("Count", 1);
		return item;
	}
}
