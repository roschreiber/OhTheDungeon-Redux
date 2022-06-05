// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.BaseGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import java.util.Random;

public class ChunkCallback extends BaseGenerator {
	private WorldConfig config;
	// private BlockCallback blockCallback = new BlockCallback(this);;
	private Generator generators;

	public ChunkCallback() {
		this.config = new WorldConfig();
	}

	@Override
	public AsyncChunk asyncGenerateChunkData(long seed, Random random, int chunkX, int chunkZ) {
		if (this.generators == null) {
			this.generators = new Generator(this.config);
		}
		final ByteChunk byteChunk = new ByteChunk(chunkX, chunkZ);
		this.generators.generate(byteChunk, random, chunkX, chunkZ);
		populate(byteChunk, random, chunkX, chunkZ);
		return byteChunk.blocks;
	}

	public void populate(final ByteChunk realChunk, final Random random, final int chunkX, int chunkZ) {
		this.generators.populate(realChunk, random, chunkX, chunkZ);
	}
}
