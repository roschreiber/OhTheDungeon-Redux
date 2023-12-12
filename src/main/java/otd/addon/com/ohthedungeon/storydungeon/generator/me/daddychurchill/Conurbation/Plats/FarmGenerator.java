// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import org.bukkit.util.noise.NoiseGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.HouseFactory;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.Material;

public class FarmGenerator extends PlatGenerator {
	public static final Material matGround;
	public static final Material byteGround;
	public static final Material byteWall;
	public static final Material byteRoof;
	public static final Material byteFloor;
	public static final Material matWater;
	public static final Material matSoil;
	public static final Material matSand;
	public static final Material matDirt;
	public static final Material matAir;
	public static final Material matTrellis;
	public static final Material deadOnDirt;
	public static final Material deadOnSand;
	public static final Material cropNone;
	public static final Material cropFallow;
	public static final Material cropYellowFlower;
	public static final Material cropRedFlower;
	public static final Material cropWheat;
	public static final Material cropPumpkin;
	public static final Material cropMelon;
	public static final Material cropVine;
	public static final Material cropCactus;
	public static final Material cropSugarCane;
//    private static final double xCropFactor = 15.0;
//    private static final double zCropFactor = 15.0;
	private SimplexNoiseGenerator noiseCrop;
//    private static final double oddsOfNorthSouth = 0.5;
//    private static final double oddsOfFarmHouse = 0.2;
//    private static final int slotDirection = 0;
//    private static final int slotFarmHouse = 1;
	int groundLevel;
	int cropLevel;

	static {
		matGround = Material.DIRT;
		byteGround = FarmGenerator.matGround;
		byteWall = Material.OAK_LOG;
		byteRoof = Material.COBBLESTONE;
		byteFloor = Material.WHITE_WOOL;
		matWater = Material.WATER;
		matSoil = Material.FARMLAND;
		matSand = Material.SAND;
		matDirt = Material.DIRT;
		matAir = Material.AIR;
		matTrellis = Material.OAK_LOG;
		deadOnDirt = Material.SHORT_GRASS;
		deadOnSand = Material.DEAD_BUSH;
		cropNone = Material.AIR;
		cropFallow = Material.DIRT;
		cropYellowFlower = Material.DANDELION;
		cropRedFlower = Material.POPPY;
		cropWheat = Material.WHEAT;
		cropPumpkin = Material.PUMPKIN_STEM;
		cropMelon = Material.MELON_STEM;
		cropVine = Material.VINE;
		cropCactus = Material.CACTUS;
		cropSugarCane = Material.SUGAR_CANE;
	}

	public FarmGenerator(final Generator noise) {
		super(noise);
		this.noiseCrop = new SimplexNoiseGenerator(noise.getNextSeed());
		this.groundLevel = noise.getStreetLevel() + 1;
		this.cropLevel = this.groundLevel + 1;
	}

	@Override
	public boolean isChunk(final int chunkX, final int chunkZ) {
		return this.noise.isRural(chunkX, chunkZ) && !this.noise.isGreenBelt(chunkX, chunkZ);
	}

	@Override
	public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		chunk.setLayer(this.groundLevel - 1, 2, FarmGenerator.byteGround);
		if (this.isFarmHouse(chunkX, chunkZ)) {
			HouseFactory.generateColonial(chunk, random, chunkX, chunkZ, this.cropLevel, FarmGenerator.byteFloor,
					FarmGenerator.byteWall, FarmGenerator.byteRoof);
		} else {
			final boolean roadToNorth = this.noise.isRoad(chunkX, chunkZ - 1);
			final boolean roadToSouth = this.noise.isRoad(chunkX, chunkZ + 1);
			final boolean roadToWest = this.noise.isRoad(chunkX - 1, chunkZ);
			final boolean roadToEast = this.noise.isRoad(chunkX + 1, chunkZ);
			if (roadToNorth) {
				this.generateFence(chunk, random, 0, 16, this.cropLevel, 0, 1);
			}
			if (roadToSouth) {
				this.generateFence(chunk, random, 0, 16, this.cropLevel, 15, 16);
			}
			if (roadToWest) {
				this.generateFence(chunk, random, 0, 1, this.cropLevel, 0, 16);
			}
			if (roadToEast) {
				this.generateFence(chunk, random, 15, 16, this.cropLevel, 0, 16);
			}
		}
	}

	@Override
	public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		if (!this.isFarmHouse(chunkX, chunkZ)) {
			final boolean directionNorthSouth = this.ifFeatureAt(chunkX, chunkZ, 0, 0.5);
			final Material cropType = this.getCrop(chunkX, chunkZ);
			if (cropType == FarmGenerator.cropYellowFlower || cropType == FarmGenerator.cropRedFlower) {
				this.plowField(chunk, random, directionNorthSouth, FarmGenerator.matDirt, 0, FarmGenerator.matWater,
						cropType, 0, FarmGenerator.deadOnDirt, 1, 2, 1);
			} else if (cropType == FarmGenerator.cropWheat) {
				this.plowField(chunk, random, directionNorthSouth, FarmGenerator.matSoil, 8, FarmGenerator.matWater,
						cropType, 4, FarmGenerator.deadOnDirt, 1, 2, 1);
			} else if (cropType == FarmGenerator.cropPumpkin || cropType == FarmGenerator.cropMelon) {
				this.plowField(chunk, random, directionNorthSouth, FarmGenerator.matSoil, 8, FarmGenerator.matWater,
						cropType, 4, FarmGenerator.deadOnDirt, 1, 3, 1);
			} else if (cropType == FarmGenerator.cropCactus) {
				this.plowField(chunk, random, directionNorthSouth, FarmGenerator.matSand, 0, FarmGenerator.matSand,
						cropType, 0, FarmGenerator.deadOnSand, 2, 2, 3);
			} else if (cropType == FarmGenerator.cropSugarCane) {
				this.plowField(chunk, random, directionNorthSouth, FarmGenerator.matSand, 0, FarmGenerator.matWater,
						cropType, 0, FarmGenerator.deadOnSand, 1, 2, 3);
			} else if (cropType == FarmGenerator.cropVine) {
				this.buildVineyard(chunk, random, directionNorthSouth);
			} else if (cropType == FarmGenerator.cropNone) {
				this.plowField(chunk, random, directionNorthSouth, FarmGenerator.matDirt, 0, FarmGenerator.matAir,
						cropType, 0, FarmGenerator.cropNone, 1, 2, 1);
			}
		}
	}

	@Override
	public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX,
			final int blockZ) {
		chunk.setBlocks(blockX, this.groundLevel, this.groundLevel + 2, blockZ, FarmGenerator.byteGround);
		return this.groundLevel + 1;
	}

	@Override
	public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
		return this.groundLevel + 1;
	}

	@Override
	public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
		return FarmGenerator.matGround;
	}

	private boolean isFarmHouse(final int chunkX, final int chunkZ) {
		boolean possibleFarmHouse = this.ifFeatureAt(chunkX, chunkZ, 1, 0.2);
		if (possibleFarmHouse) {
			final boolean roadToNorth = this.noise.isRoad(chunkX, chunkZ - 1);
			final boolean roadToSouth = this.noise.isRoad(chunkX, chunkZ + 1);
			final boolean roadToWest = this.noise.isRoad(chunkX - 1, chunkZ);
			final boolean roadToEast = this.noise.isRoad(chunkX + 1, chunkZ);
			possibleFarmHouse = (roadToNorth || roadToSouth || roadToWest || roadToEast);
		}
		return possibleFarmHouse;
	}

	private void buildVineyard(final ByteChunk chunk, final Random random, final boolean directionNorthSouth) {
		final int stepCol = 3;
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				chunk.setBlocks(x, this.cropLevel, this.cropLevel + 3, 1, FarmGenerator.matTrellis);
				chunk.setBlocks(x, this.cropLevel, this.cropLevel + 3, 14, FarmGenerator.matTrellis);
				chunk.setBlocks(x, x + 1, this.cropLevel + 3, this.cropLevel + 4, 1, 15, FarmGenerator.matTrellis);
				if (!this.noise.isDecrepit(random)) {
					for (int z = 2; z < 14; ++z) {
						if (!this.noise.isDecrepit(random)) {
							chunk.setBlocks(x - 1, x, this.cropLevel + 1 + random.nextInt(3), this.cropLevel + 4, z,
									z + 1, FarmGenerator.cropVine);
							chunk.setBlocks(x + 1, x + 2, this.cropLevel + 1 + random.nextInt(3), this.cropLevel + 4, z,
									z + 1, FarmGenerator.cropVine);
						}
					}
				}
			}
		} else {
			for (int z2 = 1; z2 < 15; z2 += stepCol) {
				chunk.setBlocks(1, this.cropLevel, this.cropLevel + 3, z2, FarmGenerator.matTrellis);
				chunk.setBlocks(14, this.cropLevel, this.cropLevel + 3, z2, FarmGenerator.matTrellis);
				chunk.setBlocks(1, 15, this.cropLevel + 3, this.cropLevel + 4, z2, z2 + 1, FarmGenerator.matTrellis);
				if (!this.noise.isDecrepit(random)) {
					for (int x2 = 2; x2 < 14; ++x2) {
						if (!this.noise.isDecrepit(random)) {
							chunk.setBlocks(x2, x2 + 1, this.cropLevel + 1 + random.nextInt(3), this.cropLevel + 4,
									z2 - 1, z2, FarmGenerator.cropVine);
							chunk.setBlocks(x2, x2 + 1, this.cropLevel + 1 + random.nextInt(3), this.cropLevel + 4,
									z2 + 1, z2 + 2, FarmGenerator.cropVine);
						}
					}
				}
			}
		}
	}

	private void plowField(final ByteChunk chunk, final Random random, final boolean directionNorthSouth,
			final Material matRidge, final int datRidge, final Material matFurrow, final Material matCrop,
			final int datCrop, final Material matDead, final int stepRow, final int stepCol, final int maxHeight) {
//        final byte byteRidge = datRidge;
//        final byte byteFurrow = 0;
//        final byte byteCrop = datCrop;
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				chunk.setBlocks(x, x + 1, this.groundLevel, this.groundLevel + 1, 1, 15, matRidge);
				if (stepCol > 1) {
					chunk.setBlocks(x + 1, x + 2, this.groundLevel, this.groundLevel + 1, 1, 15, matFurrow);
				}
				for (int z = 1; z < 15; z += stepRow) {
					if (this.noise.isDecrepit(random)) {
						chunk.setBlock(x, this.cropLevel, z, matDead);
					} else {
						chunk.setBlocks(x, this.cropLevel, this.cropLevel + random.nextInt(maxHeight) + 1, z, matCrop);
					}
				}
			}
		} else {
			for (int z2 = 1; z2 < 15; z2 += stepCol) {
				chunk.setBlocks(1, 15, this.groundLevel, this.groundLevel + 1, z2, z2 + 1, matRidge);
				if (stepCol > 1) {
					chunk.setBlocks(1, 15, this.groundLevel, this.groundLevel + 1, z2 + 1, z2 + 2, matFurrow);
				}
				for (int x2 = 1; x2 < 15; x2 += stepRow) {
					if (this.noise.isDecrepit(random)) {
						chunk.setBlock(x2, this.cropLevel, z2, matDead);
					} else {
						chunk.setBlocks(x2, this.cropLevel, this.cropLevel + random.nextInt(maxHeight) + 1, z2,
								matCrop);
					}
				}
			}
		}
	}

	private Material getCrop(final int chunkX, final int chunkZ) {
		final double noise = (this.noiseCrop.noise(chunkX / 15.0, chunkZ / 15.0) + 1.0) / 2.0;
		switch (NoiseGenerator.floor(noise * 14.0)) {
		case 1: {
			return FarmGenerator.cropYellowFlower;
		}
		case 2: {
			return FarmGenerator.cropRedFlower;
		}
		case 3: {
			return FarmGenerator.cropPumpkin;
		}
		case 4: {
			return FarmGenerator.cropMelon;
		}
		case 5: {
			return FarmGenerator.cropVine;
		}
		case 6: {
			return FarmGenerator.cropCactus;
		}
		case 7: {
			return FarmGenerator.cropSugarCane;
		}
		case 8: {
			return FarmGenerator.cropFallow;
		}
		case 9: {
			return FarmGenerator.cropNone;
		}
		default: {
			return FarmGenerator.cropWheat;
		}
		}
	}
}
