/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author
 */
public class Plot extends BaseGenerator {
	private final static int PLOT_SIZE = 48;
	private final static int PATH_SIZE = 6;
	private final static int ROAD_HEIGHT = 63;
	private final static Material FLOOR_MAIN = Material.OAK_PLANKS;
	private final static Material FLOOR_ALT = Material.BIRCH_PLANKS;
	private final static Material FILLING = Material.STONE;
	private final static Material PLOT_FLOOR = Material.GRASS_BLOCK;
	private final static Material WALL = Material.STONE_SLAB;

	@Override
	public AsyncChunk asyncGenerateChunkData(long seed, Random random, int cx, int cz) {
		double size = PLOT_SIZE + PATH_SIZE;

		double n1;
		double n2;
		double n3;
		int mod2 = 0;

		/*
		 * if (PATH_SIZE % 2 == 1) { n1 = Math.ceil((double) PATH_SIZE / 2) - 2; n2 =
		 * Math.ceil((double) PATH_SIZE / 2) - 1; n3 = Math.ceil((double) PATH_SIZE /
		 * 2); mod2 = -1; } else
		 */ {
			n1 = Math.floor((double) PATH_SIZE / 2) - 2;
			n2 = Math.floor((double) PATH_SIZE / 2) - 1;
			n3 = Math.floor((double) PATH_SIZE / 2);
		}

		int mod1 = 1;
		AsyncChunk result = new AsyncChunk();
		int height = ROAD_HEIGHT + 2;
		for (int x = 0; x < 16; x++) {
			int valx = (cx << 4) + x;

			for (int z = 0; z < 16; z++) {
				int valz = (cz << 4) + z;

				result.setBlock(x, 0, z, Material.BEDROCK);

				for (int y = 1; y < height; y++) {
					if (y == ROAD_HEIGHT) {
						if ((valx - n3 + mod1) % size == 0 || (valx + n3 + mod2) % size == 0) {// middle+3
							boolean found = false;
							for (double i = n2; i >= 0; i--) {
								if ((valz - i + mod1) % size == 0 || (valz + i + mod2) % size == 0) {
									found = true;
									break;
								}
							}

							if (found) {
								result.setBlock(x, y, z, FLOOR_MAIN);
							} else {
								result.setBlock(x, y, z, FILLING);
							}
						} else if ((valx - n2 + mod1) % size == 0 || (valx + n2 + mod2) % size == 0) // middle+2
						{
							if ((valz - n3 + mod1) % size == 0 || (valz + n3 + mod2) % size == 0
									|| (valz - n2 + mod1) % size == 0 || (valz + n2 + mod2) % size == 0) {
								result.setBlock(x, y, z, FLOOR_MAIN);
							} else {
								result.setBlock(x, y, z, FLOOR_ALT);
							}
						} else if ((valx - n1 + mod1) % size == 0 || (valx + n1 + mod2) % size == 0) // middle+2
						{
							if ((valz - n2 + mod1) % size == 0 || (valz + n2 + mod2) % size == 0
									|| (valz - n1 + mod1) % size == 0 || (valz + n1 + mod2) % size == 0) {
								result.setBlock(x, y, z, FLOOR_ALT);
							} else {
								result.setBlock(x, y, z, FLOOR_MAIN);
							}
						} else {
							boolean found = false;
							for (double i = n1; i >= 0; i--) {
								if ((valz - i + mod1) % size == 0 || (valz + i + mod2) % size == 0) {
									found = true;
									break;
								}
							}

							if (found) {
								result.setBlock(x, y, z, FLOOR_MAIN);
							} else if ((valz - n2 + mod1) % size == 0 || (valz + n2 + mod2) % size == 0) {
								result.setBlock(x, y, z, FLOOR_ALT);
							} else {
								boolean found2 = false;
								for (double i = n1; i >= 0; i--) {
									if ((valz - i + mod1) % size == 0 || (valz + i + mod2) % size == 0) {
										found2 = true;
										break;
									}
								}

								if (found2) {
									result.setBlock(x, y, z, FLOOR_MAIN);
								} else {
									boolean found3 = false;
									for (double i = n3; i >= 0; i--) {
										if ((valx - i + mod1) % size == 0 || (valx + i + mod2) % size == 0) {
											found3 = true;
											break;
										}
									}

									if (found3) {
										result.setBlock(x, y, z, FLOOR_MAIN);
									} else {
										result.setBlock(x, y, z, PLOT_FLOOR);
									}
								}
							}
						}
					} else if (y == (ROAD_HEIGHT + 1)) {
						if ((valx - n3 + mod1) % size == 0 || (valx + n3 + mod2) % size == 0) // middle+3
						{
							boolean found = false;
							for (double i = n2; i >= 0; i--) {
								if ((valz - i + mod1) % size == 0 || (valz + i + mod2) % size == 0) {
									found = true;
									break;
								}
							}
							if (!found) {
								result.setBlock(x, y, z, WALL);
							}
						} else {
							boolean found = false;
							for (double i = n2; i >= 0; i--) {
								if ((valx - i + mod1) % size == 0 || (valx + i + mod2) % size == 0) {
									found = true;
									break;
								}
							}

							if (!found && ((valz - n3 + mod1) % size == 0 || (valz + n3 + mod2) % size == 0)) {
								result.setBlock(x, y, z, WALL);
							}
						}
					} else {
						result.setBlock(x, y, z, FILLING);
					}
				}
			}
		}
		return result;
	}

}
