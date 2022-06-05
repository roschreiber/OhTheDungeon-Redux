/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.noise.NoiseGenerator;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author shadow_wind
 */
public class Hoth extends BaseGenerator {

	private NoiseGenerator noiseGenerator = null;

	private static class HothUtils {
		public static void setPos(AsyncChunk chunk, int x, int y, int z, Material material) {
			chunk.setBlock(x, y, z, material);
		}
	}

	private static Material getBedrockMaterial(Random localRand, int limit) {
		return getBedrockMaterial(localRand, limit, Material.STONE);
	}

	private static Material getBedrockMaterial(Random localRand, int limit, Material material) {
		int ran = localRand.nextInt() & 0xff;
		if (ran > limit)
			return material;
		return Material.BEDROCK;
	}

	@Override
	public AsyncChunk asyncGenerateChunkData(long seed, Random random, int chunkx, int chunkz) {
		if (this.noiseGenerator == null) {
			this.noiseGenerator = new NoiseGenerator(seed);
		}
//        boolean smooth_snow = true;

		int surfaceOffset = 0;

		Random localRand = new Random(chunkx * chunkz);
//        byte[][] snowcover = new byte[16][16];

//        int vsegs = 256 / 16;
		AsyncChunk chunk = new AsyncChunk();

		for (int z = 0; z < 16; z++) {
			for (int x = 0; x < 16; x++) {
				int rx = chunkx * 16 + x;
				int rz = chunkz * 16 + z;

				float factor = 1.0f;
				// BEDROCK Layer
				int y = 0;
				HothUtils.setPos(chunk, x, y, z, Material.BEDROCK);
				HothUtils.setPos(chunk, x, y + 1, z, getBedrockMaterial(localRand, (int) (256 * 0.9f))); // 90%
				HothUtils.setPos(chunk, x, y + 2, z, getBedrockMaterial(localRand, (int) (256 * 0.7f))); // 70%
				HothUtils.setPos(chunk, x, y + 3, z, getBedrockMaterial(localRand, (int) (256 * 0.5f))); // 50%
				HothUtils.setPos(chunk, x, y + 4, z, getBedrockMaterial(localRand, (int) (256 * 0.3f))); // 30%
				HothUtils.setPos(chunk, x, y + 5, z, getBedrockMaterial(localRand, (int) (256 * 0.2f))); // 20%

				// STONE Layer, solid
				for (y = 6; y < 27 + surfaceOffset; y++) {
					HothUtils.setPos(chunk, x, y, z, Material.STONE);
				}

				// STONE Layer
				double stone = this.noiseGenerator.noise(rx, rz, 8, 16) * 3;
				for (int i = 0; i < (int) (stone); i++) {
					HothUtils.setPos(chunk, x, y, z, Material.STONE);
					y++;
				}

				// DIRT Layer
				double dirt = this.noiseGenerator.noise(rx, rz, 8, 11) * 5;
				for (int i = 2; i < (int) (dirt); i++) {
					HothUtils.setPos(chunk, x, y, z, Material.DIRT);
					y++;
				}

				// GRAVEL Layer
				double gravel = this.noiseGenerator.noise(rx, rz, 7, 16) * 5;
				for (int i = 2; i < (int) (gravel); i++) {
					HothUtils.setPos(chunk, x, y, z, Material.GRAVEL);
					y++;
				}

				// SANDSTONE Layer
				double sandstone = this.noiseGenerator.noise(rx, rz, 8, 23) * 4;
				for (int i = 1; i < (int) (sandstone); i++) {
					HothUtils.setPos(chunk, x, y, z, Material.SANDSTONE);
					y++;
				}

				// SAND Layer
				double sand = 1 + this.noiseGenerator.noise(rx, rz, 8, 43) * 4;
				for (int i = 0; i < (int) (sand); i++) {
					HothUtils.setPos(chunk, x, y, z, Material.SAND);
					y++;
				}

				// CLAY Layer
				double clay = 1 + this.noiseGenerator.noise(rx, rz, 3, 9) * 5;
				for (int i = 3; i < (int) (clay); i++) {
					if (i == 3) {
						HothUtils.setPos(chunk, x, y, z, Material.TERRACOTTA);
					} else {
						HothUtils.setPos(chunk, x, y, z, Material.CLAY);
					}
					y++;
				}

				// ice Layer
				while (y < 34 + surfaceOffset) {
					HothUtils.setPos(chunk, x, y, z, Material.ICE);
					y++;
				}

				double icel = this.noiseGenerator.noise(rx, rz, 3, 68) * 8;
				for (int i = 3; i < (int) (icel); i++) {
					HothUtils.setPos(chunk, x, y, z, Material.ICE);
					y++;
				}

				double iceh = this.noiseGenerator.noise(rx, rz, 3, 7) * 15;

				// ICE mountains and hills
				double ice = factor
						* (this.noiseGenerator.noise(rx, rz, 4, 63) * 2 + this.noiseGenerator.noise(rx, rz, 10, 12))
						* 2.5;

				int icey = surfaceOffset + 64 + (int) (ice);
				double dicey = surfaceOffset + 64 + ice;
				for (; y < (icey - iceh); y++) {
					HothUtils.setPos(chunk, x, y, z, Material.PACKED_ICE); // Replace with packed ice
				}

				for (; y < (icey); y++) {
					HothUtils.setPos(chunk, x, y, z, Material.ICE);
				}

				// Inject stone mountains
				double domountain = this.noiseGenerator.noise(rx, rz, 4, 236) * 20;
				double mfactor = 0.0f;
				if (domountain > 18) {
					mfactor = 1.0f;
				} else if (domountain > 16) {
					mfactor = (domountain - 16) * 0.5;
				}

				if (mfactor > 0) {
					double mountain = this.noiseGenerator.noise(rx, rz, 4, 27) * 84; // bulk of the mountain
					mountain = mountain + this.noiseGenerator.noise(rx, rz, 8, 3) * 5; // Add a bit more noise
					for (int i = 0; i < (int) (mountain * mfactor); i++) {
						HothUtils.setPos(chunk, x, i + 26 + surfaceOffset, z, Material.STONE);

						if (i + 26 + surfaceOffset > y) {
							y = i + 26 + surfaceOffset;
						}
					}
				}

				// snowblock cover
				double snowblocks = 1 + this.noiseGenerator.noise(rx, rz, 8, 76) * 2;

				for (int i = 0; i < (int) (snowblocks + (dicey - (int) dicey)); i++) {
					HothUtils.setPos(chunk, x, y, z, Material.SNOW_BLOCK);
					y++;
				}

				// snow cover
//                double dval = snowblocks+dicey;
//                snowcover[z][x] = (byte) (8.0*(dval-(int)(dval)));
				HothUtils.setPos(chunk, x, y, z, Material.SNOW);

//                if(smooth_snow) {
//                    byte data = snowcover[z][x];
//                    BlockData sdata = MagicIdHandler.fromId(78, data);
//                    chunk.setBlock(x, y, z, sdata);
//                }

				// LAVA Layer
				double dolava = this.noiseGenerator.noise(rx, rz, 4, 71) * 10;
				if (dolava > 7) {
					double lava = this.noiseGenerator.noise(rx, rz, 4, 7) * 21;
					int lavay = (int) lava - 18;
					if (lavay > -2) {
						int start = 8 - (2 + lavay) / 2;

						for (int i = -1; i < lavay; i++) {
							if (start + i > 6) {
								HothUtils.setPos(chunk, x, start + i, z, Material.AIR);
							} else {
								HothUtils.setPos(chunk, x, start + i, z, Material.LAVA);
							}
						}
					}
				}

				// WATER Layer
				double dowater = this.noiseGenerator.noise(rx, rz, 4, 91) * 10;
				if (dowater > 7) {
					double water = this.noiseGenerator.noise(rx, rz, 4, 8) * 21;
					int watery = (int) water - 18;
					if (watery > -2) {
						int start = 23 - (2 + watery) / 2;

						for (int i = -1; i < watery; i++) {
							if (start + i > 21) {
								HothUtils.setPos(chunk, x, start + i, z, Material.AIR);
							} else {
								HothUtils.setPos(chunk, x, start + i, z, Material.WATER);
							}

						}
					}
				}
			}
		}

		return chunk;
	}

}
