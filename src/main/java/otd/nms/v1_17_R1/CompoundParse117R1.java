package otd.nms.v1_17_R1;

import otd.nms.CompoundParse;
import otd.util.nbt.JsonToNBT;
import otd.util.nbt.JsonToNBT.Compound;
import otd.util.nbt.JsonToNBT.NBTException;

public class CompoundParse117R1 implements CompoundParse {
	public Object parse(Compound c) throws NBTException {
		net.minecraft.nbt.NBTTagCompound nbttagcompound = new net.minecraft.nbt.NBTTagCompound();

		for (JsonToNBT.Any jsontonbt$any : c.tagList) {
			nbttagcompound.a(jsontonbt$any.json, (net.minecraft.nbt.NBTBase) jsontonbt$any.parse());
		}
//		try {
//			for (JsonToNBT.Any jsontonbt$any : c.tagList) {
//				MultiVersion.NMS_1_17.NBTTagCompound_Set.invoke(nbttagcompound, jsontonbt$any.json,
//						(net.minecraft.nbt.NBTBase) jsontonbt$any.parse());
//				// nbttagcompound.set(jsontonbt$any.json,
//				// (net.minecraft.nbt.NBTBase) jsontonbt$any.parse());
//			}
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NBTException e) {
//
//		}

		return nbttagcompound;
	}
}
