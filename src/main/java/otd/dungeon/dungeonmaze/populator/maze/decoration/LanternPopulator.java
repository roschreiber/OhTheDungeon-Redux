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

public class LanternPopulator extends MazeRoomBlockPopulator {

	private static final int LAYER_MIN = 3;
	private static final int LAYER_MAX = 7;
	private static final int ROOM_ITERATIONS = 3;
	private static final float ROOM_ITERATIONS_CHANCE = .3f;
	private static final int ROOM_ITERATIONS_MAX = 3;

	private static final float BROKEN_CHANCE = .33f;

	// TODO: Implement this feature!
	public static final double CHANCE_SINGLE_ADDITION_EACH_LEVEL = 7.5; /* to 75 */
	public static final double CHANCE_DOUBLE_ADDITION_EACH_LEVEL = 4.167; /* to 35 */

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

		// Decide whether the lantern is broken or not
		final boolean broken = rand.nextFloat() < BROKEN_CHANCE;

		// Choose the lantern position
		final int lanternX = x + rand.nextInt(8);
		final int lanternY = y + rand.nextInt(4 - floorOffset) + 2 + floorOffset;
		final int lanternZ = z + rand.nextInt(8);
		final Material b = world.getChunkType(lanternX, lanternY, lanternZ);

		// Make sure the lantern can be placed
		if (b != Material.COBBLESTONE && b != Material.MOSSY_COBBLESTONE && b != Material.STONE_BRICKS)
			return;

//        Bukkit.getLogger().log(Level.SEVERE, "???");
		// Place the actual lantern
		if (!broken) {
			world.setChunkType(lanternX, lanternY, lanternZ, Material.SEA_LANTERN);
		} else
			world.setChunkType(lanternX, lanternY, lanternZ, Material.REDSTONE_LAMP);
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

	@Override
	public int getRoomIterationsMax() {
		return ROOM_ITERATIONS_MAX;
	}
}
