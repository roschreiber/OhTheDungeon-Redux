// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import org.bukkit.Material;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;

public class UnknownGenerator extends PlatGenerator {
	private int skyLevel;

	public UnknownGenerator(final Generator noise) {
		super(noise);
		this.skyLevel = noise.getMaximumLevel();
	}

	@Override
	public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		chunk.setBlocks(0, 16, this.skyLevel, this.skyLevel + 1, 0, 16, UnknownGenerator.byteGlass);
	}

	@Override
	public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX,
			final int blockZ) {
		chunk.setBlock(blockX, this.skyLevel, blockZ, UnknownGenerator.byteGlass);
		return this.skyLevel;
	}

	@Override
	public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
	}

	@Override
	public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
		return this.skyLevel;
	}

	@Override
	public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
		return Material.GLASS;
	}

	@Override
	public boolean isChunk(final int chunkX, final int chunkZ) {
		return false;
	}
}
