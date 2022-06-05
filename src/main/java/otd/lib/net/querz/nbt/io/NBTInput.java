package otd.lib.net.querz.nbt.io;

import java.io.IOException;

import otd.lib.net.querz.nbt.tag.Tag;

public interface NBTInput {

	NamedTag readTag(int maxDepth) throws IOException;

	Tag<?> readRawTag(int maxDepth) throws IOException;
}
