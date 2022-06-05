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

public class AlmostFlatlands extends BaseGenerator {

	private final static int WORLD_HEIGHT = 64;

	private void setBlockAt(AsyncChunk chunk, int x, int y, int z, Material type) {
		chunk.setBlock(x, y, z, type);
	}

	@Override
	public AsyncChunk asyncGenerateChunkData(long seed, Random random, int chunkX, int chunkZ) {
		AsyncChunk chunk = new AsyncChunk();

		SimplexOctaveGenerator gen = new SimplexOctaveGenerator(seed, 8);

		gen.setScale(1 / 64.0);

		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				this.setBlockAt(chunk, x, 0, z, Material.BEDROCK);

				int height = (int) ((gen.noise(x + chunkX * 16, z + chunkZ * 16, 0.5, 0.5) / 0.75) + WORLD_HEIGHT);

				for (int y = 1; y < height - 4; ++y) {
					this.setBlockAt(chunk, x, y, z, Material.STONE);
				}

				for (int y = height - 4; y < height; ++y) {
					this.setBlockAt(chunk, x, y, z, Material.DIRT);
				}

				this.setBlockAt(chunk, x, height, z, Material.GRASS_BLOCK);
			}
		}

		return chunk;
	}

}