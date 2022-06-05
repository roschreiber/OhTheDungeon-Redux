/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;

public class Cake extends BaseGenerator {

	private final static int WORLD_HEIGHT = 64;
	private final static int BASE_HEIGHT = 48;

	private final static Material[] EXTRA = { Material.RED_CONCRETE, Material.BLUE_CONCRETE, Material.YELLOW_CONCRETE,
			Material.GREEN_CONCRETE, };

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

				int height = (int) ((gen.noise(x + chunkX * 16, z + chunkZ * 16, 0.5, 0.5) / 0.75) + BASE_HEIGHT);

				for (int y = 1; y < height; ++y) {
					this.setBlockAt(chunk, x, y, z, Material.BROWN_CONCRETE);
				}

				for (int y = height; y < WORLD_HEIGHT; ++y) {
					this.setBlockAt(chunk, x, y, z, Material.WHITE_CONCRETE);
				}

				Material material = EXTRA[random.nextInt(EXTRA.length)];
				for (int bx = 0; bx < 16; bx++) {
					for (int bz = 0; bz < 16; bz++) {
						this.setBlockAt(chunk, bx, WORLD_HEIGHT, bz, Material.WHITE_CONCRETE);
					}
				}
				int rand = random.nextInt(4);
				if (rand == 0) {
					for (int bx = 0; bx < 8; bx++) {
						for (int bz = 0; bz < 8; bz++) {
							this.setBlockAt(chunk, bx, WORLD_HEIGHT, bz, material);
						}
					}
				}
				if (rand == 1) {
					for (int bx = 0; bx < 8; bx++) {
						for (int bz = 8; bz < 16; bz++) {
							this.setBlockAt(chunk, bx, WORLD_HEIGHT, bz, material);
						}
					}
				}
				if (rand == 2) {
					for (int bx = 8; bx < 16; bx++) {
						for (int bz = 0; bz < 8; bz++) {
							this.setBlockAt(chunk, bx, WORLD_HEIGHT, bz, material);
						}
					}
				}
				if (rand == 3) {
					for (int bx = 8; bx < 16; bx++) {
						for (int bz = 8; bz < 16; bz++) {
							this.setBlockAt(chunk, bx, WORLD_HEIGHT, bz, material);
						}
					}
				}
			}
		}

		return chunk;
	}

}