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

import otd.MultiVersion;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;

public class WebPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final int ROOM_ITERATIONS = 4;
	private static final float ROOM_ITERATIONS_CHANCE = .25f;

	/** Populator constants. */
	private static final float CEILING_CHANCE = .4f;

	// TODO: Implement this!
	public static final double CHANCE_WEB_ADDITION_EACH_LEVEL = -1.667; /* to 30 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Decide whether the web should be on the ground
		final boolean ceiling = rand.nextFloat() < CEILING_CHANCE;

		// Choose a x,z position in the room to spawn the web
		int xWeb;
		int yWeb = args.getFloorY();
		int zWeb;
		while (true) {
			// Choose a random position
			xWeb = x + rand.nextInt(8);
			zWeb = z + rand.nextInt(8);

			// Make sure it isn't in the dungeon maze pillars
			if ((xWeb == 0 || xWeb == 7) || zWeb == 0 || zWeb == 7)
				continue;

			// The position seems to be ok
			break;
		}

		// Check whether the web should be placed on the ground
		if (!ceiling) {
			// Check whether the position is free, if not iterate up
			while (world.getChunkType(xWeb, yWeb, zWeb) != Material.AIR) {
				yWeb++;

				if (yWeb >= args.getCeilingY() - 1 || yWeb + 1 >= MultiVersion.getWorldYRange()[1])
					return;
			}

		} else {
			// Set the y position
			yWeb = args.getCeilingY();

			// Check whether the position is free, if not iterate up
			while (world.getChunkType(xWeb, yWeb, zWeb) != Material.AIR) {
				yWeb--;

				if (yWeb <= args.getFloorY() + 1 || yWeb <= 1)
					return;
			}
		}

		// Place the web
		world.setChunkType(xWeb, yWeb, zWeb, Material.COBWEB);
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
	public int getRoomIterations() {
		return ROOM_ITERATIONS;
	}

	@Override
	public float getRoomIterationsChance() {
		return ROOM_ITERATIONS_CHANCE;
	}
}