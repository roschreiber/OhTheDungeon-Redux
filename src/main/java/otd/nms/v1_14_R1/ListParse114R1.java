package otd.nms.v1_14_R1;

import otd.nms.ListParse;
import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.List;
import otd.util.nbt.JsonToNBT.NBTException;

public class ListParse114R1 implements ListParse {
	public Object parse(List l) throws NBTException {
		net.minecraft.server.v1_14_R1.NBTTagList nbttaglist = new net.minecraft.server.v1_14_R1.NBTTagList();

		for (JsonToNBT.Any jsontonbt$any : l.tagList) {
			nbttaglist.add((net.minecraft.server.v1_14_R1.NBTBase) jsontonbt$any.parse());
		}

		return nbttaglist;
	}
}
