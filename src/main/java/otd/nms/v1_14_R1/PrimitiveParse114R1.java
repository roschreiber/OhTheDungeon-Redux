package otd.nms.v1_14_R1;

import otd.nms.PrimitiveParse;
import otd.util.nbt.JsonToNBT.NBTException;
import otd.util.nbt.JsonToNBT.Primitive;

public class PrimitiveParse114R1 implements PrimitiveParse {
	public Object parseDouble(Primitive p) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagDouble(
				Double.parseDouble(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseFloat(Primitive p) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagFloat(
				Float.parseFloat(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseByte(Primitive p) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagByte(
				Byte.parseByte(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseLong(Primitive p) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagLong(
				Long.parseLong(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseShort(Primitive p) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagShort(
				Short.parseShort(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseInteger(Primitive p) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagInt(Integer.parseInt(p.jsonValue));
	}

	public Object parseDoubleUntyped(Primitive p) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagDouble(Double.parseDouble(p.jsonValue));
	}

	public Object parseBoolean(Primitive p) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagByte((byte) (Boolean.parseBoolean(p.jsonValue) ? 1 : 0));
	}

	public Object parseString(String str) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagString(str);
	}

	public Object parseIntArray(Primitive p, int[] aint) throws NBTException {
		return new net.minecraft.server.v1_14_R1.NBTTagIntArray(aint);
	}
}
