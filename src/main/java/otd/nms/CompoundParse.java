package otd.nms;

import otd.util.nbt.JsonToNBT.Compound;
import otd.util.nbt.JsonToNBT.NBTException;

public interface CompoundParse {
	public Object parse(Compound c) throws NBTException;
}