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

public class BrokenWallsPopulator extends MazeRoomBlockPopulator {

	// TODO: 'Finish' this populator!

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .33f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int roomX = args.getRoomChunkX();
		final int roomZ = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Define the position variables
		int posX, posY, posZ;

		// Determine the y position of the gap
		posY = args.getFloorY() + 1 + rand.nextInt(2);

		// Define the x and z position of the broken wall
		if (rand.nextBoolean()) {
			posX = roomX + (rand.nextBoolean() ? 0 : 7);
			posZ = roomZ + rand.nextInt(6) + 1;

		} else {
			posX = roomZ + rand.nextInt(6) + 1;
			posZ = roomX + (rand.nextBoolean() ? 0 : 7);
		}

		// TODO: Improve this!
		// Make a gap in the wall
		world.setChunkType(posX, posY, posZ, Material.AIR);
		world.setChunkType(posX, posY + 1, posZ, Material.AIR);
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