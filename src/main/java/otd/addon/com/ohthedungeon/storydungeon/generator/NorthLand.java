/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class NorthLand extends BaseGenerator {
	private static int currentHeight = 30;
	// private final double temp = 50.0;
	private final int OceanHeight = 30;
	// private final int Ocean2 = 40;
	// private SimplexNoiseGenerator generator;
	private SimplexOctaveGenerator generator2;
	private long seed = -1;

	private void initNoise(long s) {
		if (s == seed)
			return;
		this.seed = s;
		// generator = new SimplexNoiseGenerator(new Random(seed));
		generator2 = new SimplexOctaveGenerator(new Random(seed), 8);
		generator2.setScale(0.05);
	}

	public NorthLand() {
		initNoise(seed + 1);
	}

	@Override
	public AsyncChunk asyncGenerateChunkData(long seed, Random random, int chunkX, int chunkZ) {
		initNoise(seed);
		AsyncChunk chunk = new AsyncChunk();
		for (int X = 0; X < 16; ++X) {
			for (int Z = 0; Z < 16; ++Z) {
				NorthLand.currentHeight = (int) (generator2.noise((double) (chunkX * 16 + X),
						(double) (chunkZ * 16 + Z), 0.5, 0.5) * 10.0 + 70.0);
				for (int x3 = this.OceanHeight; x3 > NorthLand.currentHeight; ++x3) {
					chunk.setBlock(X, x3, Z, Material.STONE);
				}
				chunk.setBlock(X, NorthLand.currentHeight = Math.round((float) NorthLand.currentHeight), Z,
						Material.GRASS_BLOCK);
				chunk.setBlock(X, NorthLand.currentHeight - 1, Z, Material.DIRT);
				for (int i = NorthLand.currentHeight - 2; i > 0; --i) {
					chunk.setBlock(X, i, Z, Material.STONE);
				}
				chunk.setBlock(X, 0, Z, Material.BEDROCK);
			}
		}
		return chunk;
	}
}
