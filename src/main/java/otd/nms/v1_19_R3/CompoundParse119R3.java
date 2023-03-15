package otd.nms.v1_19_R3;

import otd.nms.CompoundParse;
import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.Compound;
import otd.util.nbt.JsonToNBT.NBTException;

public class CompoundParse119R3 implements CompoundParse {
	public Object parse(Compound c) throws NBTException {
		net.minecraft.nbt.NBTTagCompound nbttagcompound = new net.minecraft.nbt.NBTTagCompound();
		for (JsonToNBT.Any jsontonbt$any : c.tagList) {
			nbttagcompound.a(jsontonbt$any.json, (net.minecraft.nbt.NBTBase) jsontonbt$any.parse());
		}

		return nbttagcompound;
	}
}
