// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import org.bukkit.util.noise.NoiseGenerator;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.Material;

public class ForestGenerator extends PlatGenerator {
	private Material dirt;
//    private Material grass;
	private Material byteDirt;
	private Material byteGrass;
	int groundLevel;
	int maximumLevel;
	double groundRange;
	public static final double xGroundFactor = 50.0;
	public static final double zGroundFactor = 50.0;
	public static final double scaleGround = 5.0;
	public static final double rangeRural = 0.1;
//    private static final double oddsOfGrass = 0.3;
//    private static final double oddsOfFlower = 0.05;
//    private static final double oddsOfTree = 0.02;
	private SimplexNoiseGenerator noiseGround;

	public ForestGenerator(final Generator noise) {
		super(noise);
		this.dirt = Material.DIRT;
//        this.grass = Material.GRASS_BLOCK;
		this.byteDirt = Material.DIRT;
		this.byteGrass = Material.GRASS_BLOCK;
		this.noiseGround = new SimplexNoiseGenerator(noise.getNextSeed());
		this.groundLevel = noise.getStreetLevel();
		this.maximumLevel = noise.getMaximumLevel();
		this.groundRange = (this.maximumLevel - this.groundLevel) * 0.1;
	}

	@Override
	public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		final boolean roadToNorth = this.noise.isRoad(chunkX, chunkZ - 1);
		final boolean roadToSouth = this.noise.isRoad(chunkX, chunkZ + 1);
		final boolean roadToWest = this.noise.isRoad(chunkX - 1, chunkZ);
		final boolean roadToEast = this.noise.isRoad(chunkX + 1, chunkZ);
		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				final int y = this.getGroundSurfaceY(chunkX, chunkZ, x, z);
				if ((roadToNorth && z == 0) || (roadToSouth && z == 15) || (roadToWest && x == 0)
						|| (roadToEast && x == 15)) {
					chunk.setBlocks(x, this.groundLevel, y + 1, z, ForestGenerator.byteStoneWall);
				} else {
					chunk.setBlocks(x, this.groundLevel, y, z, this.byteDirt);
					chunk.setBlock(x, y, z, this.byteGrass);
				}
			}
		}
	}

	@Override
	public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX,
			final int blockZ) {
		final int blockY = this.getGroundSurfaceY(chunkX, chunkZ, blockX, blockZ);
		chunk.setBlocks(blockX, this.groundLevel, blockY, blockZ, this.byteDirt);
		chunk.setBlock(blockX, blockY, blockZ, this.byteGrass);
		return blockY;
	}

	@Override
	public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		final boolean roadToNorth = this.noise.isRoad(chunkX, chunkZ - 1);
		final boolean roadToSouth = this.noise.isRoad(chunkX, chunkZ + 1);
		final boolean roadToWest = this.noise.isRoad(chunkX - 1, chunkZ);
		final boolean roadToEast = this.noise.isRoad(chunkX + 1, chunkZ);
		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				final int y = this.getGroundSurfaceY(chunkX, chunkZ, x, z) + 1;
				if ((!roadToNorth || z != 0) && (!roadToSouth || z != 15) && (!roadToWest || x != 0)
						&& (!roadToEast || x != 15)) {
					final double plantNoise = random.nextDouble();
					if (plantNoise < 0.02) {
					} else if (plantNoise < 0.05) {
						chunk.setBlock(x, y, z, this.getRandomFlowerType(random));
					} else if (plantNoise < 0.3) {
						chunk.setBlock(x, y, z, ForestGenerator.intGrassBlades);
					}
				}
			}
		}
	}

	@Override
	public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
		final double x = chunkX * 16 + blockX;
		final double z = chunkZ * 16 + blockZ;
		final double noiseLevel = this.noiseGround.noise(x / 50.0, z / 50.0);
		return this.groundLevel + Math.max(1, Math.abs(NoiseGenerator.floor(noiseLevel * 5.0)));
	}

	@Override
	public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
		return this.dirt;
	}

	@Override
	public boolean isChunk(final int chunkX, final int chunkZ) {
		return this.noise.isRural(chunkX, chunkZ) && this.noise.isGreenBelt(chunkX, chunkZ);
	}
}
