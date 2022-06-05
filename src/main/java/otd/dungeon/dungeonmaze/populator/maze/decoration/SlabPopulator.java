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

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;

public class SlabPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final int ROOM_ITERATIONS = 14;
	private static final float ROOM_ITERATIONS_CHANCE = .6f;
	private static final int ROOM_ITERATIONS_MAX = 12;

	private static final BlockData STEP3 = Bukkit.createBlockData("minecraft:cobblestone_slab[type=bottom]");
	private static final BlockData STEP11 = Bukkit.createBlockData("minecraft:cobblestone_slab[type=top]");

	/** Populator constants. */
	private static final float CEILING_CHANCE = .5f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
		final int slabX = x + rand.nextInt(6) + 1;
		int slabY = args.getFloorY() + 1;
		final int slabZ = z + rand.nextInt(6) + 1;
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Determine whether the slab should be on the ceiling
		final boolean ceiling = rand.nextFloat() >= CEILING_CHANCE;

		// Set the slab coordinate if it should be on the ceiling
		if (ceiling)
			slabY = args.getCeilingY() - 1;

		// Get the slab blocks
		Material slabBlock = world.getChunkType(slabX, slabY, slabZ);
		Material baseBlock = world.getChunkType(slabX, slabY - 1, slabZ);
		if (ceiling)
			baseBlock = world.getChunkType(slabX, slabY + 1, slabZ);

		// Make sure the slab could be placed on the specified position
		if (baseBlock != Material.AIR && slabBlock == Material.AIR) {
			// Set the material type to a slab
			// Set the proper data value
			if (!ceiling)
				world.setChunkData(slabX, slabY, slabZ, STEP3);
			else
				world.setChunkData(slabX, slabY, slabZ, STEP11);
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