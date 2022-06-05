package otd.nms;

import otd.util.nbt.JsonToNBT.List;
import otd.util.nbt.JsonToNBT.NBTException;

public interface ListParse {
	public Object parse(List l) throws NBTException;
}