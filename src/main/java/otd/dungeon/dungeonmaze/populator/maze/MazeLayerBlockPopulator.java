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
package otd.dungeon.dungeonmaze.populator.maze;

import java.util.Random;
import java.util.Set;

import org.bukkit.Material;

import otd.dungeon.dungeonmaze.populator.ChunkBlockPopulator;
import otd.dungeon.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;

public abstract class MazeLayerBlockPopulator extends ChunkBlockPopulator {

	private static final int MIN_LAYER = 1;
	private static final int MAX_LAYER = 7;
	private static final int LAYER_COUNT = 7;

	@Override
	public void populateChunk(ChunkBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);
		final Set<String> custom = args.custom;
		String tag = world.getWorldName() + "," + Integer.toString(chunkx) + "#" + Integer.toString(chunkz);

		// Make sure the dungeon chunk isn't custom
		if (custom.contains(tag))
			return;

		// Get the minimum and maximum layer count
		final int layerMin = Math.max(getMinimumLayer(), 1);
		final int layerMax = Math.min(getMaximumLayer(), LAYER_COUNT);

		// The layers
		for (int l = layerMin; l <= layerMax; l++) {
			// Check whether this this layer should be populated based on it's chance
			if (rand.nextFloat() >= getLayerChance())
				continue;

			// Iterate through this layer
			final int iterations = getLayerIterations();
			final int iterationsMax = getLayerIterationsMax();
			int iterationCount = 0;
			for (int i = 0; i < iterations; i++) {
				// Make sure we didn't iterate too many times
				if (iterationCount >= iterationsMax && iterationsMax >= 0)
					break;

				// Check whether this this layer should be populated in this iteration based on
				// it's iteration chance
				if (rand.nextFloat() >= getLayerIterationsChance())
					continue;

				// Increase the iterations counter
				iterationCount++;

				// Make sure the dungeon chunk isn't custom
				if (custom.contains(tag))
					return;

				// Calculate the Y coordinate based on the current layer
				int y = 18 + ((l - 1) * 6);

				// Construct the MazePopulatorArgs to use the the populateMaze method
				MazeLayerBlockPopulatorArgs newArgs = new MazeLayerBlockPopulatorArgs(world, rand, chunkx, chunkz,
						custom, l, y);

//                Bukkit.getLogger().log(Level.SEVERE, "" + y);
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						for (int h = y; h < y + 6; h++) {
							world.setChunkType(x, h, z, Material.AIR);
						}
					}
				}

				// Populate the maze
				populateLayer(newArgs);
			}
		}
	}

	/**
	 * Population method.
	 *
	 * @param args Populator arguments.
	 */
	public abstract void populateLayer(MazeLayerBlockPopulatorArgs args);

	/**
	 * Get the minimum layer.
	 *
	 * @return Minimum layer.
	 */
	public int getMinimumLayer() {
		return MIN_LAYER;
	}

	/**
	 * Get the maximum layer.
	 *
	 * @return Maximum layer.
	 */
	public int getMaximumLayer() {
		return MAX_LAYER;
	}

	/**
	 * Get the layer population chance. This value is between 0.0 and 1.0.
	 *
	 * @return The population chance of the room.
	 */
	public float getLayerChance() {
		return 1.0f;
	}

	/**
	 * Get the number of times to iterate through each layer.
	 *
	 * @return The number of iterations.
	 */
	public int getLayerIterations() {
		return 1;
	}

	/**
	 * Get the layer population chance for each iteration. This value is between 0.0
	 * and 1.0.
	 *
	 * @return The population chance of the room.
	 */
	public float getLayerIterationsChance() {
		return 1.0f;
	}

	/**
	 * Get the maximum number of times to iterate based on the chance and iteration
	 * count.
	 *
	 * @return The maximum number of times to iterate. Return a negative number of
	 *         ignore the maximum.
	 */
	public int getLayerIterationsMax() {
		return -1;
	}
}
