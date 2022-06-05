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

public class MushroomPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
	public static final float ROOM_CHANCE = .02f;

	/** Populator constants. */
	private static final float MUSHROOM_RED_CHANCE = .5f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int yFloor = args.getFloorY();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Set the mushroom type
		Material mushroomType = Material.BROWN_MUSHROOM;

		// Apply chances
		if (rand.nextFloat() < MUSHROOM_RED_CHANCE)
			mushroomType = Material.RED_MUSHROOM;

		final int spawnerX = x + rand.nextInt(6) + 1;
		final int spawnerY = yFloor + 1;
		final int spawnerZ = z + rand.nextInt(6) + 1;

		Material base = world.getChunkType(spawnerX, spawnerY - 1, spawnerZ);
		if (base != Material.AIR && base.isSolid()) {
			Material b = world.getChunkType(spawnerX, spawnerY, spawnerZ);
			if (b == Material.AIR)
				world.setChunkType(spawnerX, spawnerY, spawnerZ, mushroomType);
		}
	}

	@Override
	public float getRoomChance() {
		// TODO: Improve this!
		return 1.0f;
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