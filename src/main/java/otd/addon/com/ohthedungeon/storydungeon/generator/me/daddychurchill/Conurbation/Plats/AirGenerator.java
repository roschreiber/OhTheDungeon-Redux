// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import org.bukkit.Material;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;

public class AirGenerator extends PlatGenerator {
	private int streetLevel;

	public AirGenerator(final Generator noise) {
		super(noise);
		this.streetLevel = noise.getStreetLevel();
	}

	@Override
	public boolean isChunk(final int chunkX, final int chunkZ) {
		return true;
	}

	@Override
	public boolean isCompatibleEdgeChunk(final PlatGenerator generator) {
		return true;
	}

	@Override
	public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
	}

	@Override
	public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
	}

	@Override
	public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX,
			final int blockZ) {
		return this.streetLevel;
	}

	@Override
	public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
		return this.streetLevel;
	}

	@Override
	public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
		return Material.AIR;
	}
}
