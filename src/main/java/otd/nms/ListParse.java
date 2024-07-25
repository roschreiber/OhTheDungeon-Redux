package otd.nms;

import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.List;
import otd.util.nbt.JsonToNBT.NBTException;

public class ListParse {
	public Object parse(List l) throws NBTException {
		net.minecraft.nbt.ListTag nbttaglist = new net.minecraft.nbt.ListTag();

		for (JsonToNBT.Any jsontonbt$any : l.tagList) {
			nbttaglist.add((net.minecraft.nbt.Tag) jsontonbt$any.parse());
		}

		return nbttaglist;
	}
}
