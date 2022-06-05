/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.noise.FMB_RMF;
import otd.addon.com.ohthedungeon.storydungeon.generator.noise.Voronoi;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class Tropic extends BaseGenerator {
	private long usedSeed = -1;

	private FMB_RMF n_p;
	private SimplexNoiseGenerator ground_nouise;
	private Voronoi cliffs;

	public Tropic() {
		this.usedSeed = 1337L;

		changeSeed(usedSeed + 1);
	}

	@Override
	public AsyncChunk asyncGenerateChunkData(long seed, Random random, int xchunk, int zchunk) {
		checkSeed(seed);
		final AsyncChunk result = new AsyncChunk();

		final int SEELEVEL = 60;
		final int TIMBERLIMIT = 110;

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int currheight = 50;
				currheight = Math.max(currheight, genGroundNoise(x, z, xchunk, zchunk) + 60);

				final int DIRTHEIGHT = random.nextInt(5);
				final boolean DO_GRASS = random.nextBoolean();

				for (int y = 0; y <= currheight; y++) {
					if (y == currheight) {
						if (currheight < SEELEVEL - 1) {
							result.setBlock(x, y, z, Material.DIRT);
						} else {
							if (y == SEELEVEL - 1 || y == SEELEVEL || y == SEELEVEL + 1) {
								result.setBlock(x, y, z, Material.SAND);
							} else {
								if (y >= TIMBERLIMIT) {
									if (DO_GRASS) {
										result.setBlock(x, y, z, Material.GRASS_BLOCK);
									} else {
										result.setBlock(x, y, z, Material.STONE);
									}
								} else {
									result.setBlock(x, y, z, Material.GRASS_BLOCK);
								}
							}
						}
					} else if (y >= TIMBERLIMIT) {
						result.setBlock(x, y, z, Material.STONE);
					} else if (currheight - y <= DIRTHEIGHT) {
						result.setBlock(x, y, z, Material.DIRT);
					} else {
						result.setBlock(x, y, z, Material.STONE);
					}
				}
				if (currheight + 1 <= SEELEVEL) {
					for (int y = currheight + 1; y <= SEELEVEL; y++) {
						result.setBlock(x, y, z, Material.WATER);
					}
				}

				result.setBlock(x, 0, z, Material.BEDROCK);
				result.setBlock(x, 1, z, Material.BEDROCK);
				if (random.nextBoolean()) {
					result.setBlock(x, 2, z, Material.BEDROCK);
				}
			}
		}

		return result;
	}

	private int genGroundNoise(final int x, final int z, final int xchunk, final int zchunk) {
		final double x_calc = ((x + xchunk * 16) + Integer.MAX_VALUE / 2) * 0.003d;
		final double z_calc = ((z + zchunk * 16) + Integer.MAX_VALUE / 2) * 0.003d;

		final double temp = n_p.noise_FractionalBrownianMotion(x_calc, z_calc, 0, 6, 0.45f, 1.5f);
		final double ground = ground_nouise.noise(x_calc, z_calc, 4, 0.25, 0.125) * 33;
		final double cliff = cliffs.get((x + xchunk * 16) / 250.0f, (z + zchunk * 16) / 250.0f) * 120;

		double noise = ground
				+ (Math.abs((n_p.noise_RidgedMultiFractal(x_calc, z_calc, 0, 4, 2.85f, 0.45f, 1.0f)) + (.05f * temp))
						* 55);
		noise = noise - cliff;

		return (int) Math.round(noise);
	}

	/**
	 * Sets the Noise generators to use the specified seed
	 */
	private void changeSeed(final long seed) {
		if (this.usedSeed == seed)
			return;
		usedSeed = seed;
		this.n_p = new FMB_RMF(seed);
		this.ground_nouise = new SimplexNoiseGenerator(seed);
		this.cliffs = new Voronoi(64, true, seed, 16, Voronoi.DistanceMetric.Squared, 4);
	}

	/**
	 * Checks if the Seed that is currently used by the Noise generators is the same
	 * as the given seed. If not {@link TropicChunkGenerator#changeSeed(Long)} is
	 * called.
	 */
	private void checkSeed(final Long worldSeed) {
		if (worldSeed != usedSeed) {
			usedSeed = worldSeed;
			changeSeed(worldSeed);
		}
	}
}
