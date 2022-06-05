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
package otd.dungeon.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;

public class GravelPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 2;
	private static final int LAYER_MAX = 7;
	private static final int ROOM_ITERATIONS = 3;
	private static final float ROOM_ITERATIONS_CHANCE = .05f;
	private static final int ROOM_ITERATIONS_MAX = 2;

	/** Populator constants. */
	private static final BlockFace[] DIRECTIONS = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST,
			BlockFace.WEST };

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int yFloor = args.getFloorY();
		final int z = args.getRoomChunkZ();
		final int startX = x + rand.nextInt(6) + 1;
		final int startY = yFloor + 1;
		final int startZ = z + rand.nextInt(6) + 1;
		final int startHeight = rand.nextInt(2) + 1;
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Choose two random directions
		BlockFace dir1 = DIRECTIONS[rand.nextInt(DIRECTIONS.length)];
		BlockFace dir2 = DIRECTIONS[rand.nextInt(DIRECTIONS.length)];

		int height = startHeight;
		int x2 = startX;
		int z2 = startZ;
		while (height > 0 && 0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
			for (int y2 = startY; y2 < startY + height; y2++)
				if (world.getChunkType(x2, y2, z2) == Material.AIR)
					world.setChunkType(x2, y2, z2, Material.GRAVEL);

			height -= rand.nextInt(1);

			x2 += dir1.getModX();
			z2 += dir1.getModZ();
		}

		if (dir1 != dir2) {
			height = startHeight;
			x2 = startX;
			z2 = startZ;
			while (height > 0 && 0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
				for (int y2 = startY; y2 < startY + height; y2++)
					if (world.getChunkType(x2, y2, z2) == Material.AIR)
						world.setChunkType(x2, y2, z2, Material.GRAVEL);

				height -= rand.nextInt(1);

				x2 += dir2.getModX();
				z2 += dir2.getModZ();
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