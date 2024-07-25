package otd.nms;

import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.Compound;
import otd.util.nbt.JsonToNBT.NBTException;

public class CompoundParse {
	public Object parse(Compound c) throws NBTException {
		net.minecraft.nbt.CompoundTag nbttagcompound = new net.minecraft.nbt.CompoundTag();
		for (JsonToNBT.Any jsontonbt$any : c.tagList) {
			nbttagcompound.put(jsontonbt$any.json, (net.minecraft.nbt.Tag) jsontonbt$any.parse());
		}

		return nbttagcompound;
	}
}
