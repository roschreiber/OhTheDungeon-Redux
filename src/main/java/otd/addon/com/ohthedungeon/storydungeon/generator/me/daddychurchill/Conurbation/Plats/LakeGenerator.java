// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import org.bukkit.util.noise.NoiseGenerator;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class LakeGenerator extends WaterGenerator {
	public static final double xLakeFactor = 40.0;
	public static final double zLakeFactor = 40.0;
	public static final double threshholdLake = 0.3;
	public static final double xLakebedNoiseFactor = 2.0;
	public static final double zLakebedNoiseFactor = 2.0;
	public static final double scaleLakebedNoise = 4.0;
	public int lakeDepth;
	private SimplexNoiseGenerator noiseLake;
	private SimplexNoiseGenerator noiseLakebedDeviance;

	public LakeGenerator(final Generator noise) {
		super(noise);
		this.noiseLake = new SimplexNoiseGenerator(noise.getNextSeed());
		this.noiseLakebedDeviance = new SimplexNoiseGenerator(noise.getNextSeed());
		this.lakeDepth = this.waterLevel - noise.getSeabedLevel();
	}

	@Override
	public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		chunk.setLayer(0, LakeGenerator.byteBedrock);
		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				this.generateChunkColumn(chunk, chunkX, chunkZ, x, z, this.getGroundSurfaceY(chunkX, chunkZ, x, z));
			}
		}
		this.generateSeawalls(chunk, random, chunkX, chunkZ);
	}

	@Override
	public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX,
			final int blockZ) {
		return this.generateChunkColumn(chunk, chunkX, chunkZ, blockX, blockZ,
				this.getGroundSurfaceY(chunkX, chunkZ, blockX, blockZ));
	}

	@Override
	public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
	}

	@Override
	public boolean isChunk(final int chunkX, final int chunkZ) {
		final double noiseLevel = (this.noiseLake.noise(chunkX / 40.0, chunkZ / 40.0) + 1.0) / 2.0;
		return noiseLevel < 0.3;
	}

	@Override
	public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
		final double x = this.calcBlock(chunkX, blockX);
		final double z = this.calcBlock(chunkZ, blockZ);
		final double lakebedShape = (this.noiseLake.noise(x / 40.0, z / 40.0) + 1.0) / 2.0 / 0.3;
		final double lakebedNoise = this.noiseLakebedDeviance.noise(x / 2.0, z / 2.0) * 4.0;
		final int lakebedLevel = NoiseGenerator.floor(lakebedShape * this.lakeDepth + lakebedNoise - 4.0);
		return Math.max(0, Math.min(lakebedLevel, this.lakeDepth - 1)) + this.waterLevel - this.lakeDepth;
	}
}
