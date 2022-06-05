/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.world;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

/**
 *
 * @author shadow
 */
public class DungeonWorldChunkGenerator extends ChunkGenerator {
	@SuppressWarnings("deprecation")
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		ChunkData chunk = Bukkit.createChunkData(world);

		String chunkPos = chunkX + "," + chunkZ;
		if (ChunkList.chunks.containsKey(chunkPos)) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					biome.setBiome(i, j, Biome.PLAINS);
					chunk.setBlock(i, 0, j, Material.BEDROCK);
					for (int k = 1; k <= 60; k++) {
						chunk.setBlock(i, k, j, Material.STONE);
					}
					chunk.setBlock(i, 61, j, Material.DIRT);
					chunk.setBlock(i, 62, j, Material.DIRT);
					chunk.setBlock(i, 63, j, Material.DIRT);
					chunk.setBlock(i, 64, j, Material.GRASS_BLOCK);
				}
			}
		} else {
			for (int i = 0; i < 16; i++)
				for (int j = 0; j < 16; j++)
					biome.setBiome(i, j, Biome.PLAINS);
		}

		return chunk;
	}
}
