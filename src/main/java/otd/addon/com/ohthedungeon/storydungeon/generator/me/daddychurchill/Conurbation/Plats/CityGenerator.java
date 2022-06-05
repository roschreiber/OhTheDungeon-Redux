// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.Material;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Neighbors.CityNeighbors;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class CityGenerator extends PlatGenerator {
//    private static final double xHeightFactor = 2.0;
//    private static final double zHeightFactor = 2.0;
	public static final double floorDeviance = 4.0;
	private SimplexNoiseGenerator noiseHeightDeviance;
	private int firstFloorY;
//    private static final int featureWallMaterial = 0;
//    private static final int featureFloorMaterial = 1;
//    private static final int featureRoofMaterial = 2;
//    private static final int featureGlassStyle = 3;

	public CityGenerator(final Generator noise) {
		super(noise);
		this.noiseHeightDeviance = new SimplexNoiseGenerator(noise.getNextSeed());
		this.firstFloorY = noise.getStreetLevel() + 1;
	}

	@Override
	public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
		chunk.setLayer(this.firstFloorY - 1, CityGenerator.byteStone);
		chunk.setLayer(this.firstFloorY, RoadGenerator.byteSidewalk);
		final CityNeighbors neighbors = new CityNeighbors(this, chunkX, chunkZ);
		final int glassSkip = this.randomFeatureAt(chunkX, chunkZ, 3, 4) + 1;
		for (int i = 0; i < neighbors.floors; ++i) {
			final int y1 = this.firstFloorY + i * 4;
			final int y2 = y1 + 4;
			final int insetNorth = neighbors.insetToNorth();
			final int insetSouth = neighbors.insetToSouth();
			final int insetWest = neighbors.insetToWest();
			final int insetEast = neighbors.insetToEast();
			if (i == 0) {
				this.generateFloor(chunk, neighbors, insetNorth, insetSouth, insetWest, insetEast, y1,
						neighbors.floorMaterial);
			}
			if (insetNorth > 0) {
				this.generateNSWall(chunk, random, insetWest, chunk.width - insetEast, y1, y2, insetNorth,
						insetNorth + 1, neighbors.wallMaterial, glassSkip);
			}
			if (insetSouth > 0) {
				this.generateNSWall(chunk, random, insetWest, chunk.width - insetEast, y1, y2,
						chunk.width - insetSouth - 1, chunk.width - insetSouth, neighbors.wallMaterial, glassSkip);
			}
			if (insetWest > 0) {
				this.generateEWWall(chunk, random, insetWest, insetWest + 1, y1, y2, insetNorth,
						chunk.width - insetSouth, neighbors.wallMaterial, glassSkip);
			}
			if (insetEast > 0) {
				this.generateEWWall(chunk, random, chunk.width - insetEast - 1, chunk.width - insetEast, y1, y2,
						insetNorth, chunk.width - insetSouth, neighbors.wallMaterial, glassSkip);
			}
			if (i == neighbors.floors - 1) {
				this.generateFloor(chunk, neighbors, insetNorth, insetSouth, insetWest, insetEast, y2,
						neighbors.roofMaterial);
			} else {
				this.generateFloor(chunk, neighbors, insetNorth, insetSouth, insetWest, insetEast, y2,
						neighbors.floorMaterial);
			}
			neighbors.decrement();
		}
	}

	private void generateFloor(final ByteChunk chunk, final CityNeighbors neighbors, final int insetNorth,
			final int insetSouth, final int insetWest, final int insetEast, final int y1, final Material material) {
		final int y2 = y1 + 1;
		chunk.setBlocks(insetWest, chunk.width - insetEast, y1, y2, insetNorth, chunk.width - insetSouth, material);
		if (insetNorth > 0 || insetSouth > 0 || insetWest > 0 || insetEast > 0) {
			if (neighbors.toNorth()) {
				chunk.setBlocks(insetEast, chunk.width - insetWest, y1, y2, 0, insetNorth, material);
			}
			if (neighbors.toSouth()) {
				chunk.setBlocks(insetEast, chunk.width - insetWest, y1, y2, chunk.width - insetSouth, chunk.width,
						material);
			}
			if (neighbors.toWest()) {
				chunk.setBlocks(0, insetWest, y1, y2, insetNorth, chunk.width - insetSouth, material);
			}
			if (neighbors.toEast()) {
				chunk.setBlocks(chunk.width - insetEast, chunk.width, y1, y2, insetNorth, chunk.width - insetSouth,
						material);
			}
			if (neighbors.toNorthWest()) {
				chunk.setBlocks(0, insetWest, y1, y2, 0, insetNorth, material);
			}
			if (neighbors.toNorthEast()) {
				chunk.setBlocks(chunk.width - insetEast, chunk.width, y1, y2, 0, insetNorth, material);
			}
			if (neighbors.toSouthWest()) {
				chunk.setBlocks(0, insetWest, y1, y2, chunk.width - insetSouth, chunk.width, material);
			}
			if (neighbors.toSouthEast()) {
				chunk.setBlocks(chunk.width - insetEast, chunk.width, y1, y2, chunk.width - insetSouth, chunk.width,
						material);
			}
		}
	}

	private void generateNSWall(final ByteChunk chunk, final Random random, final int x1, final int x2, final int y1,
			final int y2, final int z1, final int z2, final Material wallMaterial, final int glassSkip) {
		chunk.setBlocks(x1, x2, y1 + 1, y1 + 2, z1, z2, wallMaterial);
		for (int x3 = x1; x3 < x2; ++x3) {
			Material material = CityGenerator.byteWindow;
			if (x3 == x1 || x3 == x2 - 1 || (glassSkip == 1 && random.nextBoolean())
					|| (glassSkip > 1 && x3 % glassSkip == 0)) {
				material = wallMaterial;
			}
			chunk.setBlocks(x3, x3 + 1, y1 + 2, y2, z1, z2, material);
		}
	}

	private void generateEWWall(final ByteChunk chunk, final Random random, final int x1, final int x2, final int y1,
			final int y2, final int z1, final int z2, final Material wallMaterial, final int glassSkip) {
		chunk.setBlocks(x1, x2, y1 + 1, y1 + 2, z1, z2, wallMaterial);
		for (int z3 = z1; z3 < z2; ++z3) {
			Material material = CityGenerator.byteWindow;
			if (z3 == z1 || z3 == z2 - 1 || (glassSkip == 1 && random.nextBoolean())
					|| (glassSkip > 1 && z3 % glassSkip == 0)) {
				material = wallMaterial;
			}
			chunk.setBlocks(x1, x2, y1 + 2, y2, z3, z3 + 1, material);
		}
	}

	@Override
	public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX,
			final int blockZ) {
		chunk.setBlock(blockX, this.firstFloorY, blockZ, RoadGenerator.byteSidewalk);
		return this.firstFloorY;
	}

	@Override
	public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
	}

	@Override
	public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
		return this.firstFloorY;
	}

	@Override
	public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
		return Material.STONE;
	}

	@Override
	public boolean isChunk(final int chunkX, final int chunkZ) {
		return this.noise.isUrban(chunkX, chunkZ) && !this.noise.isGreenBelt(chunkX, chunkZ);
	}

	public int getUrbanHeight(final int x, final int z) {
		final double devianceAmount = (this.noiseHeightDeviance.noise(x / 2.0, z / 2.0) + 1.0) / 2.0 * 4.0;
		return Math.max(1,
				NoiseGenerator.floor(this.noise.getUrbanLevel(x, z) * this.noise.getMaximumFloors() - devianceAmount));
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

	public Material getWallMaterial(final int chunkX, final int chunkZ) {
		switch (this.randomFeatureAt(chunkX, chunkZ, 0, 4)) {
		case 1: {
			return Material.BRICK;
		}
		case 2: {
			int index = this.randomFeatureAt(chunkX, chunkZ, 0, CONCRETES.length);
			index = index % CONCRETES.length;
			return CONCRETES[index];
//                return Material.SAND;
		}
		case 3: {
			int index = this.randomFeatureAt(chunkX, chunkZ, 0, TERRACOTTA.length);
			index = index % TERRACOTTA.length;
			return TERRACOTTA[index];
//                return Material.CLAY;
		}
		default: {
			return Material.STONE_BRICKS;
		}
		}
	}

	public Material getFloorMaterial(final int chunkX, final int chunkZ) {
		switch (this.randomFeatureAt(chunkX, chunkZ, 1, 3)) {
		case 1: {
			return Material.OAK_LOG;
		}
		case 2: {
			return Material.WHITE_WOOL;
		}
		default: {
			return Material.STONE_BRICKS;
		}
		}
	}

	public Material getRoofMaterial(final int chunkX, final int chunkZ) {
		switch (this.randomFeatureAt(chunkX, chunkZ, 2, 4)) {
		case 1: {
			return Material.OAK_LOG;
		}
		case 2: {
			return Material.COBBLESTONE;
		}
		case 3: {
			return Material.SANDSTONE;
		}
		default: {
			return Material.STONE_BRICKS;
		}
		}
	}
}
