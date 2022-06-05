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

public class PoolPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 1;
	private static final float ROOM_CHANCE = .05f; // Includes lava pools

	/** Populator constants. */
	private static final int NO_LAVA_NEAR_SPAWN_RADIUS = 2; // In chunks
	private static final int LAVA_CHANCE = 35; // Rest is water

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Determine whether the lava liquid type is allowed
		boolean allowLava = true;
		if (Math.abs(chunkx) < NO_LAVA_NEAR_SPAWN_RADIUS || Math.abs(chunkz) < NO_LAVA_NEAR_SPAWN_RADIUS)
			allowLava = false;

		// Determine the liquid type of the pool
		LiquidType liquidType = LiquidType.WATER;
		if (allowLava && rand.nextInt(100) < LAVA_CHANCE)
			liquidType = LiquidType.LAVA;

		// Specify the pool location and size
		final int xPool = x + rand.nextInt(6) + 1;
		final int yPool = args.getFloorY();
		final int zPool = z + rand.nextInt(6) + 1;
		final int poolWidth = rand.nextInt(5);
		final int poolLength = rand.nextInt(5);

		// Create/spawn the pool with the specified liquid
		for (int i = Math.max(xPool - poolWidth / 2, 1); i < Math.min(xPool - poolWidth / 2 + poolWidth, 6); i++) {
			for (int j = Math.max(zPool - poolLength / 2, 1); j < Math.min(zPool - poolLength / 2 + poolLength,
					6); j++) {
				world.setChunkType(i, yPool, j, liquidType.getMaterial());
				world.setChunkType(i, yPool - 1, j, Material.MOSSY_COBBLESTONE);
			}
		}
	}

	public enum LiquidType {
		WATER(Material.WATER), LAVA(Material.LAVA);

		/** The liquid type as a Bukkit material. */
		private final Material mat;

		/**
		 * Constructor.
		 *
		 * @param mat The material type of the liquid.
		 */
		LiquidType(Material mat) {
			this.mat = mat;
		}

		/**
		 * Get the Bukkit material type of the liquid.
		 *
		 * @return The material type of the liquid.
		 */
		public Material getMaterial() {
			return mat;
		}
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
