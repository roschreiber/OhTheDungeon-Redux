/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.OctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class Slient extends BaseGenerator {
	private OctaveGenerator generator;
	private long seed;

	private OctaveGenerator getGenerator(final long s) {
		if (s == seed)
			return this.generator;
		seed = s;
		this.generator = (OctaveGenerator) new SimplexOctaveGenerator(seed, 64);
		this.generator.setScale(0.00625);

		return this.generator;
	}

	public Slient() {
		seed = -1;
		getGenerator(seed + 1);
	}

	private int getHeight(final long seed, final double x, final double z, final int cx, final int cz,
			final int variance) {
		final OctaveGenerator gen = this.getGenerator(seed);
		double result = gen.noise(x + cx * 16, z + cz * 16, 0.5, 0.5);
		result *= variance;
		return NoiseGenerator.floor(result);
	}

	@Override
	public AsyncChunk asyncGenerateChunkData(long seed, Random random, int cx, int cz) {
		AsyncChunk result = new AsyncChunk();
		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				for (int y = 20; y < 59; ++y) {
					result.setBlock(x, y, z, Material.WATER);
				}
				result.setBlock(x, 59, z, Material.ICE);
				for (int height = this.getHeight(seed, x, z, cx, cz, 9) + 65, y2 = 0; y2 < height; ++y2) {
					result.setBlock(x, y2, z, Material.DIRT);
					if (result.getType(x, height + 1, z) != Material.WATER && height > 52
							&& result.getType(x, height + 1, z) != Material.ICE) {
						result.setBlock(x, height, z, Material.GRASS_BLOCK);
					}
				}
				for (int height = this.getHeight(seed, x, z, cx, cz, 9) + 62, y2 = 0; y2 < height; ++y2) {
					result.setBlock(x, y2, z, Material.STONE);
				}
				for (int y2 = 0; y2 < 3; ++y2) {
					result.setBlock(x, y2, z, Material.BEDROCK);
				}
			}
		}
		return result;
	}
}
