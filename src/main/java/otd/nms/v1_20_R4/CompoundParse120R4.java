package otd.nms.v1_20_R4;

import otd.nms.CompoundParse;
import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.Compound;
import otd.util.nbt.JsonToNBT.NBTException;

public class CompoundParse120R4 implements CompoundParse {
	public Object parse(Compound c) throws NBTException {
		net.minecraft.nbt.CompoundTag nbttagcompound = new net.minecraft.nbt.CompoundTag();
		for (JsonToNBT.Any jsontonbt$any : c.tagList) {
			nbttagcompound.put(jsontonbt$any.json, (net.minecraft.nbt.Tag) jsontonbt$any.parse());
		}

		return nbttagcompound;
	}
}
