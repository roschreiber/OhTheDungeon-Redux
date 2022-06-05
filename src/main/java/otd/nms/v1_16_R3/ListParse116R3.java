package otd.nms.v1_16_R3;

import otd.nms.ListParse;
import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.List;
import otd.util.nbt.JsonToNBT.NBTException;

public class ListParse116R3 implements ListParse {
	public Object parse(List l) throws NBTException {
		net.minecraft.server.v1_16_R3.NBTTagList nbttaglist = new net.minecraft.server.v1_16_R3.NBTTagList();

		for (JsonToNBT.Any jsontonbt$any : l.tagList) {
			nbttaglist.add((net.minecraft.server.v1_16_R3.NBTBase) jsontonbt$any.parse());
		}

		return nbttaglist;
	}
}
