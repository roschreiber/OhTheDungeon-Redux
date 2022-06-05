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

import org.bukkit.Material;

import otd.lib.async.AsyncWorldEditor;

public abstract class MazeRoomBlockPopulator extends MazeLayerBlockPopulator {

	/**
	 * Population method.
	 *
	 * @param args Populator arguments.
	 */
	@Override
	public void populateLayer(MazeLayerBlockPopulatorArgs args) {
		AsyncWorldEditor world = args.getWorld();
		Random rand = args.getRandom();
		int layer = args.getLayer();
		int y = args.getY();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// The 4 rooms on each layer
		for (int chunkX = 0; chunkX < 16; chunkX += 8) {
			for (int chunkZ = 0; chunkZ < 16; chunkZ += 8) {
				// Check whether this this room should be populated based on it's chance
				if (rand.nextFloat() >= getRoomChance())
					continue;

				// Iterate through this room
				final int iterations = getRoomIterations();
				final int iterationsMax = getRoomIterationsMax();
				int iterationCount = 0;
				for (int i = 0; i < iterations; i++) {
					// Make sure we didn't iterate too many times
					if (iterationCount >= iterationsMax && iterationsMax >= 0)
						break;

					// Check whether this this room should be populated in the current iteration
					// based on it's iteration chance
					if (rand.nextFloat() >= getRoomIterationsChance())
						continue;

					// Increase the iterations counter
					iterationCount++;

					// Make sure this room isn't constant
//                    if(DungeonMaze.instance.isConstantRoom(world.getName(), chunk, chunkX, y, chunkZ))
//                        continue;

					// Calculate the global X and Y coordinates
					int x = (chunkx * 16) + chunkX;
					int z = (chunkz * 16) + chunkZ;

					// Get the floor and ceiling offset
					int floorOffset = getFloorOffset(chunkX, y, chunkZ, world);
					int ceilingOffset = getCeilingOffset(chunkX, y, chunkZ, world);

					// Construct the DMMazePopulatorArgs to use the the populateMaze method
					MazeRoomBlockPopulatorArgs newArgs = new MazeRoomBlockPopulatorArgs(world, rand, chunkx, chunkz,
							args.custom, layer, x, y, z, floorOffset, ceilingOffset);

					// Populate the maze
					populateRoom(newArgs);
				}
			}
		}
	}

	/**
	 * Population method.
	 *
	 * @param args Populator arguments.
	 */
	public abstract void populateRoom(MazeRoomBlockPopulatorArgs args);

	/**
	 * Get the floor offset in a specific room.
	 *
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @param z Z coordinate.
	 * @param c Chunk.
	 *
	 * @return Floor offset.
	 */
	public int getFloorOffset(int x, int y, int z, AsyncWorldEditor world) {
		Material typeId = world.getChunkType(x + 3, y, z + 3);

		// x and z +2 so that you aren't inside a wall!
		if (!(typeId == Material.COBBLESTONE || typeId == Material.MOSSY_COBBLESTONE || typeId == Material.NETHERRACK
				|| typeId == Material.SOUL_SAND))
			return 1;

		return 0;
	}

	/**
	 * Get the ceiling offset in a specific room.
	 *
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @param z Z coordinate.
	 * @param c The chunk.
	 *
	 * @return Ceiling offset.
	 */
	public int getCeilingOffset(int x, int y, int z, AsyncWorldEditor world) {
		Material typeId = world.getChunkType(x + 3, y + 6, z + 3);

		// x and z +2 so that you aren't inside a wall!
		if (!(typeId == Material.COBBLESTONE || typeId == Material.MOSSY_COBBLESTONE || typeId == Material.NETHERRACK
				|| typeId == Material.SOUL_SAND))
			return 1;

		return 0;
	}

	/**
	 * Get the room population chance. This value is between 0.0 and 1.0.
	 *
	 * @return The population chance of the room.
	 */
	public float getRoomChance() {
		return 1.0f;
	}

	/**
	 * Get the number of times to iterate through each room.
	 *
	 * @return The number of iterations.
	 */
	public int getRoomIterations() {
		return 1;
	}

	/**
	 * Get the room population chance for each iteration. This value is between 0.0
	 * and 1.0.
	 *
	 * @return The population chance of the room.
	 */
	public float getRoomIterationsChance() {
		return 1.0f;
	}

	/**
	 * Get the maximum number of times to iterate based on the chance and iteration
	 * count.
	 *
	 * @return The maximum number of times to iterate. Return a negative number of
	 *         ignore the maximum.
	 */
	public int getRoomIterationsMax() {
		return -1;
	}
}
