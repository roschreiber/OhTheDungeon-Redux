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

public class CrackedStoneBrickPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final int ROOM_ITERATIONS = 80;
	private static final float ROOM_ITERATIONS_CHANCE = .7f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int z = args.getRoomChunkZ();

		// Specify the block
		int bx = x + rand.nextInt(8);
		int by = rand.nextInt((y + 6) - y + 1) + y;
		int bz = z + rand.nextInt(8);
		Material crackedStoneBlock = world.getChunkType(bx, by, bz);

		// Change the block if it's currently a smooth stone brick block
		if (crackedStoneBlock == Material.STONE_BRICKS)
			world.setChunkType(bx, by, bz, Material.CRACKED_STONE_BRICKS);
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
