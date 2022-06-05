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

public class VinePopulator extends MazeRoomBlockPopulator {

	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;

	private static final int CHANCE_VINE = 30;
	private static final double CHANCE_VINE_ADDITION_EACH_LEVEL = -2.5; /* to 15 */
	private static final int ITERATIONS = 5;
	private static final int CHANCE_CEILING_VINE = 5;
	private static final int ITERATIONS_CEILING_VINE = 5;

	private static final BlockData VINE0 = Bukkit
			.createBlockData("minecraft:vine[east=false,south=false,north=false,west=false,up=false]");
	private static final BlockData VINE1 = Bukkit
			.createBlockData("minecraft:vine[east=false,south=true,north=false,west=false,up=false]");
	private static final BlockData VINE2 = Bukkit
			.createBlockData("minecraft:vine[east=false,south=false,north=false,west=true,up=false]");
	private static final BlockData VINE4 = Bukkit
			.createBlockData("minecraft:vine[east=false,south=false,north=true,west=false,up=false]");
	private static final BlockData VINE8 = Bukkit
			.createBlockData("minecraft:vine[east=true,south=false,north=false,west=false,up=false]");

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Iterate
		for (int i = 0; i < ITERATIONS; i++) {
			if (rand.nextInt(100) < CHANCE_VINE + (CHANCE_VINE_ADDITION_EACH_LEVEL * (y - 30) / 6)) {

				int vineX;
				int vineY;
				int vineZ;

				switch (rand.nextInt(4)) {
				case 0:
					vineX = 0;
					vineY = rand.nextInt(4) + 2;
					vineZ = rand.nextInt(6) + 1;

					if (world.getChunkType(x + vineX, y + vineY, z + vineZ) == Material.STONE_BRICKS) {
						world.setChunkData(x + vineX + 1, y + vineY, z + vineZ, VINE2);
					}

					break;
				case 1:
					vineX = 7;
					vineY = rand.nextInt(3) + 3;
					vineZ = rand.nextInt(6) + 1;

					if (world.getChunkType(x + vineX, y + vineY, z + vineZ) == Material.STONE_BRICKS) {
						world.setChunkData(x + vineX - 1, y + vineY, z + vineZ, VINE8);
					}

					break;
				case 2:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 0;

					if (world.getChunkType(x + vineX, y + vineY, z + vineZ) == Material.STONE_BRICKS) {
						world.setChunkData(x + vineX, y + vineY, z + vineZ + 1, VINE4);
					}

					break;
				case 3:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 7;

					if (world.getChunkType(x + vineX, y + vineY, z + vineZ) == Material.STONE_BRICKS) {
						world.setChunkData(x + vineX, y + vineY, z + vineZ - 1, VINE1);
					}

					break;
				default:
				}
			}
		}

		// Iterate
		for (int i = 0; i < ITERATIONS_CEILING_VINE; i++) {
			if (rand.nextInt(100) < CHANCE_CEILING_VINE) {

				int vineX = rand.nextInt(6) + 1;
				int vineY = args.getCeilingY() - 1;
				int vineZ = rand.nextInt(6) + 1;

				world.setChunkData(x + vineX, vineY, z + vineZ, VINE0);
			}
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