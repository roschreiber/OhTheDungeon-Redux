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
package otd.dungeon.dungeonmaze.populator;

import java.util.HashSet;
import java.util.Random;

import otd.lib.async.AsyncWorldEditor;

public abstract class ChunkBlockPopulator {

	private boolean const_room = false;

	public boolean getConstRoom() {
		return const_room;
	}

	public void populate(AsyncWorldEditor world, Random rand, int chunkx, int chunkz, boolean force) {
		// TODO: Re-enable queued populators, and properly implement them for the next
		// release
//        // Queue populators when not force
//        if(!force && !(chunk.getX() == 0 && chunk.getZ() == 0)) {
//            DungeonMaze.instance.queuedPopulators.add(new DelayedPopulator(this, world, rand, chunk));
//            return;
//        }

		// Check whether this this chunk should be populated based on it's chance
		if (rand.nextFloat() >= getChunkChance())
			return;

		// Iterate through this chunk
		final int iterations = getChunkIterations();
		final int iterationsMax = getChunkIterationsMax();
		int iterationCount = 0;
		for (int i = 0; i < iterations; i++) {
			// Make sure we didn't iterate too many times
			if (iterationCount >= iterationsMax && iterationsMax >= 0)
				break;

			// Check whether this this chunk should be populated in the current iteration
			// based on it's iteration chance
			if (rand.nextFloat() >= getChunkIterationsChance())
				continue;

			// Increase the iterations counter
			iterationCount++;

			// Construct the MazeBlockPopulatorArgs to use the the populateMaze method
			ChunkBlockPopulatorArgs args = new ChunkBlockPopulatorArgs(world, rand, new HashSet<>(), chunkx, chunkz);

			// Populate the maze
			populateChunk(args);
		}
	}

	/**
	 * Population method.
	 *
	 * @param args Populator arguments.
	 */
	public abstract void populateChunk(ChunkBlockPopulatorArgs args);

	/**
	 * Get the chunk population chance. This value is between 0.0 and 1.0.
	 *
	 * @return The population chance of the chunk.
	 */
	public float getChunkChance() {
		return 1.0f;
	}

	/**
	 * Get the number of times to iterate through each chunk.
	 *
	 * @return The number of iterations.
	 */
	public int getChunkIterations() {
		return 1;
	}

	/**
	 * Get the chunk population chance for each iteration. This value is between 0.0
	 * and 1.0.
	 *
	 * @return The population chance of the chunk.
	 */
	public float getChunkIterationsChance() {
		return 1.0f;
	}

	/**
	 * Get the maximum number of times to iterate based on the chance and iteration
	 * count.
	 *
	 * @return The maximum number of times to iterate. Return a negative number of
	 *         ignore the maximum.
	 */
	public int getChunkIterationsMax() {
		return -1;
	}
}
