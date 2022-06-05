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
package otd.dungeon.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Material;

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;

public class IronBarPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final int ROOM_ITERATIONS = 4;
	private static final float ROOM_ITERATIONS_CHANCE = .20f;

	/** Populator constants. */
	private static final float CHANCE_DOUBLE_HEIGHT = .66f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int z = args.getRoomChunkZ();
		final int floorOffset = args.getFloorOffset();

		// Define the position variables
		int blockX, blockY, blockZ;

		// Determine the y position of the gap
		blockY = y + rand.nextInt(4 - floorOffset) + 1 + floorOffset;

		// Define the x and z position of the broken wall
		if (rand.nextBoolean()) {
			blockX = x + (rand.nextBoolean() ? 0 : 7);
			blockZ = z + rand.nextInt(6) + 1;

		} else {
			blockX = z + rand.nextInt(6) + 1;
			blockZ = x + (rand.nextBoolean() ? 0 : 7);
		}

		// Specify the bars base block
		Material barsBase = world.getChunkType(blockX, blockY, blockZ);
		if (barsBase == Material.COBBLESTONE || barsBase == Material.MOSSY_COBBLESTONE
				|| barsBase == Material.STONE_BRICKS) {
			// Set the block type to the iron bars
			world.setChunkType(blockX, blockY, blockZ, Material.IRON_BARS);

			// Check whether bars of two blocks height should be spawned
			if (rand.nextFloat() < CHANCE_DOUBLE_HEIGHT) {
				world.setChunkType(blockX, blockY + 1, blockZ, Material.IRON_BARS);
			}
		}
	}

	@Override
	public int getRoomIterations() {
		return ROOM_ITERATIONS;
	}

	@Override
	public float getRoomIterationsChance() {
		return ROOM_ITERATIONS_CHANCE;
	}

	/**
	 * Get the minimum layer
	 * 
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}

	/**
	 * Get the maximum layer
	 * 
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
}
