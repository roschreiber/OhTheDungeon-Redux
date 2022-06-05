package otd.nms.v1_16_R3;

import otd.nms.CompoundParse;
import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.Compound;
import otd.util.nbt.JsonToNBT.NBTException;

public class CompoundParse116R3 implements CompoundParse {
	public Object parse(Compound c) throws NBTException {
		net.minecraft.server.v1_16_R3.NBTTagCompound nbttagcompound = new net.minecraft.server.v1_16_R3.NBTTagCompound();

		for (JsonToNBT.Any jsontonbt$any : c.tagList) {
			nbttagcompound.set(jsontonbt$any.json, (net.minecraft.server.v1_16_R3.NBTBase) jsontonbt$any.parse());
		}

		return nbttagcompound;
	}
}
