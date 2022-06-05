// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import org.bukkit.Material;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;

public class GroundGenerator extends PlatGenerator {
	private int streetLevel;

	public GroundGenerator(final Generator noise) {
		super(noise);
		this.streetLevel = noise.getStreetLevel();
	}

	@Override
	public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		chunk.setLayer(0, GroundGenerator.byteBedrock);
		chunk.setBlocks(0, 16, 1, this.streetLevel - 4, 0, 16, GroundGenerator.byteStone);
		chunk.setBlocks(0, 16, this.streetLevel - 4, this.streetLevel, 0, 16, GroundGenerator.byteDirt);
	}

	@Override
	public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX,
			final int blockZ) {
		chunk.setBlocks(blockX, 1, this.streetLevel - 4, blockZ, GroundGenerator.byteStone);
		chunk.setBlocks(blockX, this.streetLevel - 4, this.streetLevel, blockZ, GroundGenerator.byteDirt);
		return this.streetLevel - 1;
	}

	@Override
	public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
	}

	@Override
	public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
		return 0;
	}

	@Override
	public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
		return Material.DIRT;
	}

	@Override
	public boolean isChunk(final int chunkX, final int chunkZ) {
		return false;
	}
}
