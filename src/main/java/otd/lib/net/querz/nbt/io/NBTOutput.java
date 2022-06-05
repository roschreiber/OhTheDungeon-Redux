package otd.lib.net.querz.nbt.io;

import java.io.IOException;

import otd.lib.net.querz.nbt.tag.Tag;

public interface NBTOutput {

	void writeTag(NamedTag tag, int maxDepth) throws IOException;

	void writeTag(Tag<?> tag, int maxDepth) throws IOException;

	void flush() throws IOException;
}
