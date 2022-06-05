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

public class WaterOutOfWallPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 5;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .04f;

	// TODO: Implement this!
	public static final double CHANCE_WATER_ADDITION_EACH_LEVEL = -0.833; /* to 0 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int z = args.getRoomChunkZ();
		final int floorOffset = args.getFloorOffset();
		final int lanternX = x + rand.nextInt(8);
		final int lanternY = y + rand.nextInt(4 - floorOffset) + 2 + floorOffset;
		final int lanternZ = z + rand.nextInt(8);
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Specify the water block
		Material waterBlock = world.getChunkType(lanternX, lanternY, lanternZ);

		// Set the block to water only if it will be replacing a valid wall block
		if (waterBlock == Material.COBBLESTONE || waterBlock == Material.MOSSY_COBBLESTONE
				|| waterBlock == Material.STONE_BRICKS)
			world.setChunkType(lanternX, lanternY, lanternZ, Material.WATER);
	}

	@Override
	public float getRoomChance() {
		return ROOM_CHANCE;
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
