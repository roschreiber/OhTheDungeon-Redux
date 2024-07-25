package otd.nms;

import otd.util.nbt.JsonToNBT.NBTException;
import otd.util.nbt.JsonToNBT.Primitive;

public class PrimitiveParse {
	public Object parseDouble(Primitive p) throws NBTException {
		return net.minecraft.nbt.DoubleTag
				.valueOf(Double.parseDouble(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseFloat(Primitive p) throws NBTException {
		return net.minecraft.nbt.FloatTag
				.valueOf(Float.parseFloat(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseByte(Primitive p) throws NBTException {
		return net.minecraft.nbt.ByteTag.valueOf(Byte.parseByte(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseLong(Primitive p) throws NBTException {
		return net.minecraft.nbt.LongTag.valueOf(Long.parseLong(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseShort(Primitive p) throws NBTException {
		return net.minecraft.nbt.ShortTag
				.valueOf(Short.parseShort(p.jsonValue.substring(0, p.jsonValue.length() - 1)));
	}

	public Object parseInteger(Primitive p) throws NBTException {
		return net.minecraft.nbt.IntTag.valueOf(Integer.parseInt(p.jsonValue));
	}

	public Object parseDoubleUntyped(Primitive p) throws NBTException {
		return net.minecraft.nbt.DoubleTag.valueOf(Double.parseDouble(p.jsonValue));
	}

	public Object parseBoolean(Primitive p) throws NBTException {
		return net.minecraft.nbt.ByteTag.valueOf((byte) (Boolean.parseBoolean(p.jsonValue) ? 1 : 0));
	}

	public Object parseString(String str) throws NBTException {
		return net.minecraft.nbt.StringTag.valueOf(str);
	}

	public Object parseIntArray(Primitive p, int[] aint) throws NBTException {
		return new net.minecraft.nbt.IntArrayTag(aint);
	}
}
