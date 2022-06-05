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

public class Rocky extends BaseGenerator {
	private SimplexOctaveGenerator overhangs;
	private SimplexOctaveGenerator bottoms;
	private SimplexOctaveGenerator bottoms2;
	private long seed;

	private void initNoise(long s) {
		if (s == seed)
			return;
		this.seed = s;
		overhangs = new SimplexOctaveGenerator(seed, 8);
		bottoms = new SimplexOctaveGenerator(seed, 8);
		bottoms2 = new SimplexOctaveGenerator(seed, 8);

		overhangs.setScale(1 / 64.0); // little note: the .0 is VERY important
		bottoms.setScale(1 / 128.0);
		bottoms2.setScale(1 / 64.0);
	}

	public Rocky() {
		this.seed = -1;
		initNoise(seed + 1);
	}

	@Override
	public AsyncChunk asyncGenerateChunkData(long seed, Random random, int ChunkX, int ChunkZ) {
		AsyncChunk chunk = new AsyncChunk();

		int overhangsMagnitude = 16; // used when we generate the noise for the tops of the overhangs

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				chunk.setBlock(x, 0, z, Material.BEDROCK);
				chunk.setBlock(x, 1, z, Material.BEDROCK);
				int realX = x + ChunkX * 16;
				int realZ = z + ChunkZ * 16;

				int bottomHeight = (int) ((bottoms.noise(realX, realZ, 0.5, 0.5)
						+ bottoms2.noise(realX, realZ, 0.5, 0.5)) * 30 + 64);
				int maxHeight = (int) overhangs.noise(realX, realZ, 0.5, 0.5) * overhangsMagnitude + bottomHeight + 32;
				double threshold = 0.3;

				// make the terrain
				for (int y = 0; y < maxHeight && y < 256; y++) {
					if (y > bottomHeight) { // part where we do the overhangs
						double density = overhangs.noise(realX, y, realZ, 0.5, 0.5);

						if (density > threshold)
							chunk.setBlock(x, y, z, Material.STONE);

					} else {
						chunk.setBlock(x, y, z, Material.STONE);
					}
				}

				// turn the tops into grass
				chunk.setBlock(x, bottomHeight, z, Material.GRASS_BLOCK); // the top of the base hills
				chunk.setBlock(x, bottomHeight - 1, z, Material.DIRT);
				chunk.setBlock(x, bottomHeight - 2, z, Material.DIRT);

				for (int y = bottomHeight + 1; y > bottomHeight && y < maxHeight; y++) { // the overhang
					Material thisblock = chunk.getType(x, y, z);
					Material blockabove = chunk.getType(x, y + 1, z);

					if (thisblock != Material.AIR && blockabove == Material.AIR) {
						chunk.setBlock(x, y, z, Material.GRASS_BLOCK);
						if (chunk.getType(x, y - 1, z) != Material.AIR)
							chunk.setBlock(x, y - 1, z, Material.DIRT);
						if (chunk.getType(x, y - 2, z) != Material.AIR)
							chunk.setBlock(x, y - 2, z, Material.DIRT);
					}
				}

			}
		}
		return chunk;
	}
}