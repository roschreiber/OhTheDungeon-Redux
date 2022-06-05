// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import java.util.HashSet;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.Material;

public class RoadGenerator extends PlatGenerator {
	public static final int sidewalkWidth = 3;
	public static final int roadThickness = 2;
	public static final int lightpostHeight = 3;
	public static final Material bytePavement = Material.STONE;
	public static final Material byteSidewalk = Material.WHITE_CONCRETE;
	@SuppressWarnings("deprecation")
	public static final Material bytePlatwalk = Material.LEGACY_DOUBLE_STEP;
	public static final Material byteBridge = Material.STONE_BRICKS;
	public static final Material byteRailing = Material.OAK_FENCE;
	public static final Material byteRailingBase = RoadGenerator.bytePlatwalk;
	public static final Material lightpostbaseMaterial = Material.BRICKS;
	public static final Material lightpostMaterial = Material.OAK_FENCE;
	public static final Material workingLampMaterial = Material.GLOWSTONE;
	public static final Material workingTorchMaterial = Material.TORCH;
	public static final Material brokeLampMaterial = Material.GLASS;
	@SuppressWarnings("deprecation")
	public static final Material brokeTorchMaterial = Material.LEGACY_REDSTONE_TORCH_OFF;
	public static final int roadCellSize = 4;
//    private static final double xIntersectionFactor = 6.0;
//    private static final double zIntersectionFactor = 6.0;
//    private static final double threshholdRoad = 0.65;
//    private static final double threshholdBridge = 1.0;
//    private static final double threshholdBridgeLength = 0.1;
	int streetLevel;
	int seabedLevel;
	int sidewalkLevel;
	private SimplexNoiseGenerator noiseIntersection;
	private HashSet<Long> knownRoads;

	public RoadGenerator(final Generator noise) {
		super(noise);
		this.noiseIntersection = new SimplexNoiseGenerator(noise.getNextSeed());
		this.knownRoads = new HashSet<Long>();
		this.streetLevel = noise.getStreetLevel();
		this.seabedLevel = noise.getSeabedLevel();
		this.sidewalkLevel = this.streetLevel + 1;
	}

	@Override
	public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		final boolean toNorth = this.noise.isRoad(chunkX, chunkZ - 1);
		final boolean toSouth = this.noise.isRoad(chunkX, chunkZ + 1);
		final boolean toWest = this.noise.isRoad(chunkX - 1, chunkZ);
		final boolean toEast = this.noise.isRoad(chunkX + 1, chunkZ);
		final boolean isBridge = this.noise.isWater(chunkX, chunkZ);
		chunk.setLayer(this.streetLevel, RoadGenerator.bytePavement);
		chunk.setBlocks(0, 3, this.sidewalkLevel, this.sidewalkLevel + 1, 0, 3, RoadGenerator.byteSidewalk);
		chunk.setBlocks(0, 3, this.sidewalkLevel, this.sidewalkLevel + 1, chunk.width - 3, chunk.width,
				RoadGenerator.byteSidewalk);
		chunk.setBlocks(chunk.width - 3, chunk.width, this.sidewalkLevel, this.sidewalkLevel + 1, 0, 3,
				RoadGenerator.byteSidewalk);
		chunk.setBlocks(chunk.width - 3, chunk.width, this.sidewalkLevel, this.sidewalkLevel + 1, chunk.width - 3,
				chunk.width, RoadGenerator.byteSidewalk);
		if (!toWest) {
			chunk.setBlocks(0, 3, this.sidewalkLevel, this.sidewalkLevel + 1, 3, chunk.width - 3,
					RoadGenerator.byteSidewalk);
		}
		if (!toEast) {
			chunk.setBlocks(chunk.width - 3, chunk.width, this.sidewalkLevel, this.sidewalkLevel + 1, 3,
					chunk.width - 3, RoadGenerator.byteSidewalk);
		}
		if (!toNorth) {
			chunk.setBlocks(3, chunk.width - 3, this.sidewalkLevel, this.sidewalkLevel + 1, 0, 3,
					RoadGenerator.byteSidewalk);
		}
		if (!toSouth) {
			chunk.setBlocks(3, chunk.width - 3, this.sidewalkLevel, this.sidewalkLevel + 1, chunk.width - 3,
					chunk.width, RoadGenerator.byteSidewalk);
		}
		if (!toWest && toEast && !toNorth && toSouth) {
			this.generateRoundedOut(chunk, this.sidewalkLevel, 3, 3, false, false);
		}
		if (!toWest && toEast && toNorth && !toSouth) {
			this.generateRoundedOut(chunk, this.sidewalkLevel, 3, chunk.width - 3 - 4, false, true);
		}
		if (toWest && !toEast && !toNorth && toSouth) {
			this.generateRoundedOut(chunk, this.sidewalkLevel, chunk.width - 3 - 4, 3, true, false);
		}
		if (toWest && !toEast && toNorth && !toSouth) {
			this.generateRoundedOut(chunk, this.sidewalkLevel, chunk.width - 3 - 4, chunk.width - 3 - 4, true, true);
		}
		if (isBridge) {
			chunk.setLayer(this.streetLevel - 1, RoadGenerator.byteBridge);
			chunk.setBlocks(0, 2, this.seabedLevel, this.streetLevel - 1, 0, 2, RoadGenerator.byteBridge);
			chunk.setBlocks(14, 16, this.seabedLevel, this.streetLevel - 1, 0, 2, RoadGenerator.byteBridge);
			chunk.setBlocks(0, 2, this.seabedLevel, this.streetLevel - 1, 14, 16, RoadGenerator.byteBridge);
			chunk.setBlocks(14, 16, this.seabedLevel, this.streetLevel - 1, 14, 16, RoadGenerator.byteBridge);
			if (!toNorth) {
				chunk.setBlocks(0, 16, this.sidewalkLevel, this.sidewalkLevel + 1, 0, 1, RoadGenerator.byteRailingBase);
				chunk.setBlocks(0, 16, this.sidewalkLevel + 1, this.sidewalkLevel + 2, 0, 1, RoadGenerator.byteRailing);
			}
			if (!toSouth) {
				chunk.setBlocks(0, 16, this.sidewalkLevel, this.sidewalkLevel + 1, 15, 16,
						RoadGenerator.byteRailingBase);
				chunk.setBlocks(0, 16, this.sidewalkLevel + 1, this.sidewalkLevel + 2, 15, 16,
						RoadGenerator.byteRailing);
			}
			if (!toWest) {
				chunk.setBlocks(0, 1, this.sidewalkLevel, this.sidewalkLevel + 1, 0, 16, RoadGenerator.byteRailingBase);
				chunk.setBlocks(0, 1, this.sidewalkLevel + 1, this.sidewalkLevel + 2, 0, 16, RoadGenerator.byteRailing);
			}
			if (!toEast) {
				chunk.setBlocks(15, 16, this.sidewalkLevel, this.sidewalkLevel + 1, 0, 16,
						RoadGenerator.byteRailingBase);
				chunk.setBlocks(15, 16, this.sidewalkLevel + 1, this.sidewalkLevel + 2, 0, 16,
						RoadGenerator.byteRailing);
			}
		}
	}

	@Override
	public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX,
			final int blockZ) {
		chunk.setBlock(blockX, this.streetLevel, blockZ, RoadGenerator.bytePavement);
		return this.streetLevel - 1;
	}

	protected void generateRoundedOut(final ByteChunk chunk, final int sidewalkLevel, final int x, final int z,
			final boolean toNorth, final boolean toEast) {
		for (int i = 0; i < 4; ++i) {
			chunk.setBlock(toNorth ? (x + 3) : x, sidewalkLevel, z + i, RoadGenerator.byteSidewalk);
			chunk.setBlock(x + i, sidewalkLevel, toEast ? (z + 3) : z, RoadGenerator.byteSidewalk);
		}
		chunk.setBlock(toNorth ? (x + 2) : (x + 1), sidewalkLevel, toEast ? (z + 2) : (z + 1),
				RoadGenerator.byteSidewalk);
	}

	@Override
	public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		final boolean isTorch = this.noise.isRural(chunkX, chunkZ);
		this.generateLightPost(chunk, random, 2, 2, isTorch);
		this.generateLightPost(chunk, random, chunk.width - 3, chunk.width - 3, isTorch);
		this.generateStreetSigns(chunk, random, 2, 2, this.fixRoadCoordinate(chunkX), this.fixRoadCoordinate(chunkZ));
	}

	protected void generateLightPost(final ByteChunk chunk, final Random random, final int x, final int z,
			final boolean isTorch) {
		if (isTorch)
			return;
		final int sidewalkLevel = this.streetLevel + 1;
		chunk.setBlock(x, sidewalkLevel, z, RoadGenerator.lightpostbaseMaterial);
		chunk.setBlocks(x, sidewalkLevel + 1, sidewalkLevel + 3 + 1, z, RoadGenerator.lightpostMaterial);
		if (this.noise.isDecrepit(random)) {
			chunk.setBlock(x, sidewalkLevel + 3 + 1, z, RoadGenerator.brokeLampMaterial);
		} else {
			chunk.setBlock(x, sidewalkLevel + 3 + 1, z, RoadGenerator.workingLampMaterial);
		}
	}

	protected void generateStreetSigns(final ByteChunk chunk, final Random random, final int blockX, final int blockZ,
			final int streetX, final int streetZ) {
	}

	@Override
	public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
		return this.sidewalkLevel;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
		return Material.LEGACY_DOUBLE_STEP;
	}

	@Override
	public boolean isChunk(final int chunkX, final int chunkZ) {
		final boolean checkedX = this.checkRoadCoordinate(chunkX);
		final boolean checkedZ = this.checkRoadCoordinate(chunkZ);
		boolean roadExists = checkedX || checkedZ;
		if (roadExists) {
			final Long roadId = this.getChunkKey(chunkX, chunkZ);
			if (!this.knownRoads.contains(roadId)) {
				if (checkedX && checkedZ) {
					roadExists = (this.isChunk(chunkX - 1, chunkZ) || this.isChunk(chunkX + 1, chunkZ)
							|| this.isChunk(chunkX, chunkZ - 1) || this.isChunk(chunkX, chunkZ + 1));
				} else {
					boolean isBridge = false;
					double lengthNoise = 0.0;
					double previousNoise;
					double nextNoise;
					if (checkedX) {
						int previousX;
						int previousZ;
						for (previousX = chunkX, previousZ = this.fixRoadCoordinate(chunkZ); this.noise
								.isWater(previousX, previousZ); previousZ -= 4, isBridge = true, lengthNoise += 0.1) {
						}
						if (this.noise.isWater(previousX, previousZ + 1)) {
							previousNoise = 0.0;
						} else {
							previousNoise = this.getIntersectionNoise(previousX, previousZ + 1);
						}
						int nextX;
						int nextZ;
						for (nextX = chunkX, nextZ = this.fixRoadCoordinate(chunkZ + 4); this.noise.isWater(nextX,
								nextZ); nextZ += 4, isBridge = true, lengthNoise += 0.1) {
						}
						if (this.noise.isWater(nextX, nextZ - 1)) {
							nextNoise = 0.0;
						} else {
							nextNoise = this.getIntersectionNoise(nextX, nextZ - 1);
						}
					} else {
						int previousX;
						int previousZ;
						for (previousX = this.fixRoadCoordinate(chunkX), previousZ = chunkZ; this.noise
								.isWater(previousX, previousZ); previousX -= 4, isBridge = true, lengthNoise += 0.1) {
						}
						if (this.noise.isWater(previousX + 1, previousZ)) {
							previousNoise = 0.0;
						} else {
							previousNoise = this.getIntersectionNoise(previousX + 1, previousZ);
						}
						int nextX;
						int nextZ;
						for (nextX = this.fixRoadCoordinate(chunkX + 4), nextZ = chunkZ; this.noise.isWater(nextX,
								nextZ); nextX += 4, isBridge = true, lengthNoise += 0.1) {
						}
						if (this.noise.isWater(nextX - 1, nextZ)) {
							nextNoise = 0.0;
						} else {
							nextNoise = this.getIntersectionNoise(nextX - 1, nextZ);
						}
					}
					if (isBridge) {
						roadExists = (previousNoise + nextNoise > 1.0 + lengthNoise);
					} else {
						roadExists = (previousNoise + nextNoise > 0.65);
					}
				}
				if (roadExists) {
					this.knownRoads.add(roadId);
				}
			}
		}
		return roadExists;
	}

	private double getIntersectionNoise(final int x, final int z) {
		return (this.noiseIntersection.noise(x / 6.0, z / 6.0) + 1.0) / 2.0;
	}

	private boolean checkRoadCoordinate(final int i) {
		return i % 4 == 0;
	}

	private int fixRoadCoordinate(final int i) {
		if (i < 0) {
			return -(Math.abs(i + 1) / 4 * 4 + 4);
		}
		return i / 4 * 4;
	}
}
