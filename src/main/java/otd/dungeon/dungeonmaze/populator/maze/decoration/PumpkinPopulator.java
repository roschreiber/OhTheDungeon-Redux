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

public class PumpkinPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .025f;
	private static final int ROOM_ITERATIONS = 7;
	private static final float ROOM_ITERATIONS_CHANCE = .5f;
	private static final int ROOM_ITERATIONS_MAX = 5;

	/** Populator constants. */
	private static final float JACK_O_LANTERN_CHANCE = .33f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
		final int xPumpkin = x + rand.nextInt(6) + 1;
		final int yPumpkin = args.getFloorY() + 1;
		final int zPumpkin = z + rand.nextInt(6) + 1;
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Decide whether to place a pumpkin or jack o lantern
		final boolean illuminated = rand.nextFloat() < JACK_O_LANTERN_CHANCE;

		// Place the pumpkin if there's any place
		Material type = world.getChunkType(xPumpkin, yPumpkin - 1, zPumpkin);
		if (type.isSolid()) {
			if (world.getChunkType(xPumpkin, yPumpkin, zPumpkin) == Material.AIR) {
				if (!illuminated)
					world.setChunkType(xPumpkin, yPumpkin, zPumpkin, Material.PUMPKIN);
				else
					world.setChunkType(xPumpkin, yPumpkin, zPumpkin, Material.JACK_O_LANTERN);

				// Randomly rotate the pumpkin
//                slabBlock.setData((byte) rand.nextInt(4));//TODO
			}
		}
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

	@Override
	public float getRoomChance() {
		return ROOM_CHANCE;
	}

	@Override
	public int getRoomIterations() {
		return ROOM_ITERATIONS;
	}

	@Override
	public float getRoomIterationsChance() {
		return ROOM_ITERATIONS_CHANCE;
	}

	@Override
	public int getRoomIterationsMax() {
		return ROOM_ITERATIONS_MAX;
	}
}