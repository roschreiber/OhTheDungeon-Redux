package otd.nms;

import otd.util.nbt.JsonToNBT.NBTException;
import otd.util.nbt.JsonToNBT.Primitive;

public interface PrimitiveParse {
	public Object parseDouble(Primitive p) throws NBTException;

	public Object parseFloat(Primitive p) throws NBTException;

	public Object parseByte(Primitive p) throws NBTException;

	public Object parseLong(Primitive p) throws NBTException;

	public Object parseShort(Primitive p) throws NBTException;

	public Object parseInteger(Primitive p) throws NBTException;

	public Object parseDoubleUntyped(Primitive p) throws NBTException;

	public Object parseBoolean(Primitive p) throws NBTException;

	public Object parseString(String str) throws NBTException;

	public Object parseIntArray(Primitive p, int[] aint) throws NBTException;
}