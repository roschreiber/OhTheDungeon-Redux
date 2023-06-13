package otd.nms.v1_20_R1;

import otd.nms.ListParse;
import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.List;
import otd.util.nbt.JsonToNBT.NBTException;

public class ListParse120R1 implements ListParse {
	public Object parse(List l) throws NBTException {
		net.minecraft.nbt.NBTTagList nbttaglist = new net.minecraft.nbt.NBTTagList();

		for (JsonToNBT.Any jsontonbt$any : l.tagList) {
			nbttaglist.add((net.minecraft.nbt.NBTBase) jsontonbt$any.parse());
		}

		return nbttaglist;
	}
}
