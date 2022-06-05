package otd.nms.v1_16_R2;

import otd.nms.ListParse;
import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.List;
import otd.util.nbt.JsonToNBT.NBTException;

public class ListParse116R2 implements ListParse {
	public Object parse(List l) throws NBTException {
		net.minecraft.server.v1_16_R2.NBTTagList nbttaglist = new net.minecraft.server.v1_16_R2.NBTTagList();

		for (JsonToNBT.Any jsontonbt$any : l.tagList) {
			nbttaglist.add((net.minecraft.server.v1_16_R2.NBTBase) jsontonbt$any.parse());
		}

		return nbttaglist;
	}
}
