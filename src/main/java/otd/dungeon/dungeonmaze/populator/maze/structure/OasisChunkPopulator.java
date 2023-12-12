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
package otd.dungeon.dungeonmaze.populator.maze.structure;

import java.util.Random;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;

import forge_sandbox.BlockPos;
import otd.dungeon.dungeonmaze.populator.ChunkBlockPopulator;
import otd.dungeon.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import otd.lib.BiomeDictionary;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.smoofy.Tree_Later;

public class OasisChunkPopulator extends ChunkBlockPopulator {

	/** General populator constants. */
	private static final float CHUNK_CHANCE = .003f;

	/** Populator constants. */
	private static final int CHANCE_CLAYINDIRT = 10;
//	private static final double SPAWN_DISTANCE_MIN = 7; // Chunks

	public void apply_glass(int ymax, AsyncWorldEditor world, int x, int z, Biome b) {
		Set<BiomeDictionary.Type> set = BiomeDictionary.getTypes(b);
		if (set.contains(BiomeDictionary.Type.BEACH) || set.contains(BiomeDictionary.Type.OCEAN)) {
//			Chunk c = world.getBlockAt(x, 0, z).getChunk();
			int chunkx = x / 16, chunkz = z / 16;
			for (int i = 0; i < 16; i++)
				for (int j = 0; j < 16; j++)
					world.setBlockType(chunkx * 16 + i, ymax, chunkz * 16 + j, Material.GLASS);
		}
	}

	@Override
	public void populateChunk(ChunkBlockPopulatorArgs args) {
//		Bukkit.getLogger().info("oasis");
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);
//        final DungeonChunk dungeonChunk = args.getDungeonChunk();

		// Set this chunk as custom
		args.custom.add(Integer.toString(chunkx) + "," + Integer.toString(chunkz));

		// Generate a dirt layer
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++)
				world.setBlockType(chunkx * 16 + x, 29, chunkz * 16 + z, Material.DIRT);

		// Generate some clay inside the dirt layer
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++)
				if (rand.nextInt(100) < CHANCE_CLAYINDIRT)
					world.setBlockType(chunkx * 16 + x, 29, chunkz * 16 + z, Material.CLAY);

		// Generate the grass layer
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++)
				world.setBlockType(chunkx * 16 + x, 30, chunkz * 16 + z, Material.GRASS_BLOCK);

		// Remove all the stone above the grass layer!
		for (int y = 31; y <= 100; y++)
			for (int x = 0; x < 16; x++)
				for (int z = 0; z < 16; z++)
					world.setBlockType(chunkx * 16 + x, y, chunkz * 16 + z, Material.AIR);

		// Generate some tall grass on the oasis
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				if (rand.nextInt(100) < CHANCE_CLAYINDIRT) {
					world.setBlockType(chunkx * 16 + x, 31, chunkz * 16 + z, Material.SHORT_GRASS);
				}
			}
		}

		// Random tree offset (0 or 1)
		int treeOffsetX = rand.nextInt(2);
		int treeOffsetZ = rand.nextInt(2);

		// Generate the water around the tree
		for (int x = 5; x <= 11; x++)
			world.setBlockType(chunkx * 16 + x + treeOffsetX, 30, chunkz * 16 + 5 + treeOffsetZ, Material.WATER);
		for (int z = 5; z <= 11; z++)
			world.setBlockType(chunkx * 16 + 5 + treeOffsetX, 30, chunkz * 16 + z + treeOffsetZ, Material.WATER);
		for (int x = 5; x <= 11; x++)
			world.setBlockType(chunkx * 16 + x + treeOffsetX, 30, chunkz * 16 + 11 + treeOffsetZ, Material.WATER);
		for (int z = 5; z <= 11; z++)
			world.setBlockType(chunkx * 16 + 11 + treeOffsetX, 30, chunkz * 16 + z + treeOffsetZ, Material.WATER);

		// Generate some sugar canes
		world.setBlockType(chunkx * 16 + 6 + treeOffsetX, 31, chunkz * 16 + 6 + treeOffsetZ, Material.SUGAR_CANE);
		world.setBlockType(chunkx * 16 + 6 + treeOffsetX, 31, chunkz * 16 + 10 + treeOffsetZ, Material.SUGAR_CANE);
		world.setBlockType(chunkx * 16 + 10 + treeOffsetX, 31, chunkz * 16 + 6 + treeOffsetZ, Material.SUGAR_CANE);
		world.setBlockType(chunkx * 16 + 10 + treeOffsetX, 31, chunkz * 16 + 10 + treeOffsetZ, Material.SUGAR_CANE);

		// Random tree type and generate the tree
		TreeType treeType;
		switch (rand.nextInt(5)) {
		case 0:
			treeType = TreeType.BIG_TREE;
			break;
		case 1:
			treeType = TreeType.BIRCH;
			break;
		case 2:
			treeType = TreeType.REDWOOD;
			break;
		case 3:
			treeType = TreeType.TALL_REDWOOD;
			break;
		case 4:
			treeType = TreeType.TREE;
			break;
		default:
			treeType = TreeType.TREE;
		}

		BlockPos treeLocation = new BlockPos(chunkx * 16 + 8 + treeOffsetX, 31, chunkz * 16 + 8 + treeOffsetZ);
		world.addLater(new Tree_Later(treeLocation, treeType));
	}

	@Override
	public float getChunkIterationsChance() {
		return CHUNK_CHANCE;
	}
}