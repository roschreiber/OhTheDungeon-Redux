// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.TreeType;
import org.bukkit.util.noise.NoiseGenerator;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import org.bukkit.Material;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public abstract class PlatGenerator {
//    private static final double xFeatureFactor = 5.0;
//    private static final double zFeatureFactor = 5.0;
	private SimplexNoiseGenerator noiseFeature;
//    private static final int featureWall = -1;
//    private static final int featureCeiling = -2;
//    private static final int featureFloor = -3;
//    private static final int featureRoof = -4;
	public Generator noise;
	protected static final Material byteBedrock = Material.BEDROCK;
	protected static final Material byteWater = Material.WATER;
	protected static final Material byteDirt = Material.DIRT;
	protected static final Material byteGrass = Material.GRASS_BLOCK;
	protected static final Material byteIron = Material.IRON_BLOCK;
	protected static final Material byteGlass = Material.GLASS;
	protected static final Material byteWindow = Material.GLASS;
	protected static final Material byteCobblestone = Material.COBBLESTONE;
	protected static final Material byteMossyCobblestone = Material.MOSSY_COBBLESTONE;
	protected static final Material byteStone = Material.STONE;
	protected static final Material byteSmoothBrick = Material.STONE_BRICKS;
	protected static final Material byteSandstone = Material.SANDSTONE;
	protected static final Material byteSand = Material.SAND;
	protected static final Material byteBrick = Material.BRICKS;
	protected static final Material byteClay = Material.CLAY;
	protected static final Material byteWood = Material.OAK_LOG;
	protected static final Material byteWool = Material.WHITE_WOOL;
	protected static final Material byteStoneWall = Material.COBBLESTONE_WALL;
	protected static final Material byteFence = Material.OAK_FENCE;
	protected static final Material byteFenceBase = Material.STONE_BRICKS;
	@SuppressWarnings("deprecation")
	protected static final Material byteSidewalk = Material.LEGACY_DOUBLE_STEP;
	@SuppressWarnings("deprecation")
	protected static final Material byteParkwalk = Material.LEGACY_DOUBLE_STEP;
	protected static final Material intGrassBlades = Material.SHORT_GRASS;
	protected static final Material intFlowerRed = Material.POPPY;
	protected static final Material intFlowerYellow = Material.DANDELION;
	protected static final double oddsOfAFenceOpening = 0.7;

	public PlatGenerator(final Generator noise) {
		this.noise = noise;
		this.noiseFeature = new SimplexNoiseGenerator(noise.getNextSeed());
	}

	public abstract boolean isChunk(final int p0, final int p1);

	public abstract void generateChunk(final ByteChunk p0, final Random p1, final int p2, final int p3);

	public abstract void populateChunk(final ByteChunk p0, final Random p1, final int p2, final int p3);

	public abstract int generateChunkColumn(final ByteChunk p0, final int p1, final int p2, final int p3, final int p4);

	public abstract int getGroundSurfaceY(final int p0, final int p1, final int p2, final int p3);

	public abstract Material getGroundSurfaceMaterial(final int p0, final int p1);

	public boolean isCompatibleEdgeChunk(final PlatGenerator generator) {
		return generator == this;
	}

	protected double calcBlock(final double chunkI, final double i) {
		return chunkI + i / 16.0;
	}

	protected Long getChunkKey(final int chunkX, final int chunkZ) {
		return chunkX * 2147483647L + chunkZ;
	}

	public int randomFeatureAt(final int chunkX, final int chunkZ, final int slot, final int range) {
		return NoiseGenerator.floor(this.randomFeatureAt(chunkX, chunkZ, slot) * range);
	}

	public double randomFeatureAt(final int chunkX, final int chunkZ, final int slot) {
		return (this.noiseFeature.noise(chunkX / 5.0, (double) slot, chunkZ / 5.0) + 1.0) / 2.0;
	}

	public boolean ifFeatureAt(final int chunkX, final int chunkZ, final int slot, final double odds) {
		return this.randomFeatureAt(chunkX, chunkZ, slot) < odds;
	}

	protected boolean generateFence(final ByteChunk chunk, final Random random, final int x1, final int x2, final int y,
			final int z1, final int z2) {
		final boolean hasOpening = this.isThereAFenceOpening(random);
		for (int x3 = x1; x3 < x2; ++x3) {
			for (int z3 = z1; z3 < z2; ++z3) {
				if (!hasOpening || (x3 != 7 && x3 != 8 && z3 != 7 && z3 != 8)) {
					chunk.setBlock(x3, y - 1, z3, PlatGenerator.byteFenceBase);
					chunk.setBlock(x3, y, z3, PlatGenerator.byteFence);
				}
			}
		}
		return hasOpening;
	}

	protected void generateSidewalk(final ByteChunk chunk, final int x1, final int x2, final int y, final int z1,
			final int z2) {
		chunk.setBlocks(x1, x2, y, y + 1, z1, z2, PlatGenerator.byteSidewalk);
	}

	protected void generateParkwalk(final ByteChunk chunk, final int x1, final int x2, final int y, final int z1,
			final int z2) {
		chunk.setBlocks(x1, x2, y, y + 1, z1, z2, PlatGenerator.byteParkwalk);
	}

	protected boolean isThereAFenceOpening(final Random random) {
		return random.nextDouble() < 0.7;
	}

	protected Material getRandomFlowerType(final Random random) {
		switch (random.nextInt(2)) {
		case 1: {
			return PlatGenerator.intFlowerRed;
		}
		default: {
			return PlatGenerator.intFlowerYellow;
		}
		}
	}

	protected TreeType getRandomTreeType(final Random random) {
		switch (random.nextInt(3)) {
		case 1: {
			return TreeType.BIRCH;
		}
		case 2: {
			return TreeType.REDWOOD;
		}
		default: {
			return TreeType.TREE;
		}
		}
	}

	private final static Material[] CONCRETES;
	private final static Material[] TERRACOTTA;
	static {
		List<Material> tmp = new ArrayList<>();
		for (Material material : Material.values()) {
			if (material.toString().toUpperCase().contains("CONCRETE")) {
				if (material.toString().toUpperCase().contains("POWDER"))
					continue;
				tmp.add(material);
			}
		}
		CONCRETES = new Material[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			CONCRETES[i] = tmp.get(i);
		}
		tmp.clear();
		for (Material material : Material.values()) {
			if (material.toString().toUpperCase().contains("TERRACOTTA")) {
				tmp.add(material);
			}
		}
		TERRACOTTA = new Material[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			TERRACOTTA[i] = tmp.get(i);
		}
	}

	public Material pickWallMaterial(final int chunkX, final int chunkZ) {
		switch (NoiseGenerator.floor(this.randomFeatureAt(chunkX, chunkZ, -1) * 9.0)) {
		case 1: {
			return PlatGenerator.byteCobblestone;
		}
		case 2: {
			return PlatGenerator.byteMossyCobblestone;
		}
		case 3: {
			return PlatGenerator.byteStone;
		}
		case 4: {
			return PlatGenerator.byteSmoothBrick;
		}
		case 5: {
			return PlatGenerator.byteSandstone;
		}
		case 6: {
			int index = NoiseGenerator.floor(this.randomFeatureAt(chunkX, chunkZ, -1) * 9.0);
			index = index % TERRACOTTA.length;
			return TERRACOTTA[index];
//                return PlatGenerator.byteSand;
		}
		case 7: {
			return PlatGenerator.byteBrick;
		}
		case 8: {
			int index = NoiseGenerator.floor(this.randomFeatureAt(chunkX, chunkZ, -1) * 9.0);
			index = index % CONCRETES.length;
			return CONCRETES[index];
//                return PlatGenerator.byteClay;
		}
		default: {
			return PlatGenerator.byteWood;
		}
		}
	}

	public Material pickCeilingMaterial(final int chunkX, final int chunkZ) {
		switch (NoiseGenerator.floor(this.randomFeatureAt(chunkX, chunkZ, -2) * 5.0)) {
		case 1: {
			return PlatGenerator.byteCobblestone;
		}
		case 2: {
			return PlatGenerator.byteStone;
		}
		case 3: {
			return PlatGenerator.byteSmoothBrick;
		}
		case 4: {
			return PlatGenerator.byteSandstone;
		}
		default: {
			return PlatGenerator.byteWood;
		}
		}
	}

	public Material pickFloorMaterial(final int chunkX, final int chunkZ) {
		switch (NoiseGenerator.floor(this.randomFeatureAt(chunkX, chunkZ, -3) * 4.0)) {
		case 1: {
			return PlatGenerator.byteCobblestone;
		}
		case 2: {
			return PlatGenerator.byteStone;
		}
		case 3: {
			return PlatGenerator.byteWool;
		}
		default: {
			return PlatGenerator.byteWood;
		}
		}
	}

	public Material pickRoofMaterial(final int chunkX, final int chunkZ) {
		switch (NoiseGenerator.floor(this.randomFeatureAt(chunkX, chunkZ, -4) * 6.0)) {
		case 1: {
			return PlatGenerator.byteCobblestone;
		}
		case 2: {
			return PlatGenerator.byteMossyCobblestone;
		}
		case 3: {
			return PlatGenerator.byteStone;
		}
		case 4: {
			return PlatGenerator.byteSmoothBrick;
		}
		case 5: {
			return PlatGenerator.byteSandstone;
		}
		default: {
			return PlatGenerator.byteWood;
		}
		}
	}
}
