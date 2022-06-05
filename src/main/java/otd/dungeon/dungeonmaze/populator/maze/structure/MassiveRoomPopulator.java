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

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;

public class MassiveRoomPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .005f;

	public boolean const_room = true;

	@Override
	public boolean getConstRoom() {
		return const_room;
	}

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
//		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

//        // Register the current room as constant room
//        ////DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

		// Walls
		for (int x2 = x; x2 <= x + 7; x2 += 1) {
			for (int y2 = yFloor + 1; y2 <= yCeiling - 1; y2 += 1) {
				world.setChunkType(x2, y2, z, Material.STONE_BRICKS);
				world.setChunkType(x2, y2, z + 7, Material.STONE_BRICKS);
			}
		}
		for (int z2 = z; z2 <= z + 7; z2 += 1) {
			for (int y2 = yFloor + 1; y2 <= yCeiling - 1; y2 += 1) {
				world.setChunkType(x, y2, z2, Material.STONE_BRICKS);
				world.setChunkType(x + 7, y2, z2, Material.STONE_BRICKS);
			}
		}

		// Make the room massive with stone
		for (int x2 = x + 1; x2 <= x + 6; x2 += 1)
			for (int y2 = yFloor + 1; y2 <= yCeiling - 1; y2 += 1)
				for (int z2 = z + 1; z2 <= z + 6; z2 += 1)
					world.setChunkType(x2, y2, z2, Material.STONE);

		// Fill the massive room with some ores!
		for (int x2 = x + 1; x2 <= x + 6; x2 += 1) {
			for (int y2 = yFloor + 1; y2 <= yCeiling - 1; y2 += 1) {
				for (int z2 = z + 1; z2 <= z + 6; z2 += 1) {
					if (rand.nextInt(100) < 2) {
						switch (rand.nextInt(8)) {
						case 0:
							world.setChunkType(x2, y2, z2, Material.GOLD_ORE);
							break;
						case 1:
							world.setChunkType(x2, y2, z2, Material.IRON_ORE);
							break;
						case 2:
							world.setChunkType(x2, y2, z2, Material.COAL_ORE);
							break;
						case 3:
							world.setChunkType(x2, y2, z2, Material.LAPIS_ORE);
							break;
						case 4:
							world.setChunkType(x2, y2, z2, Material.DIAMOND_ORE);
							break;
						case 5:
							world.setChunkType(x2, y2, z2, Material.REDSTONE_ORE);
							break;
						case 6:
							world.setChunkType(x2, y2, z2, Material.EMERALD_ORE);
							break;
						case 7:
							world.setChunkType(x2, y2, z2, Material.CLAY);
							break;
						case 8:
							world.setChunkType(x2, y2, z2, Material.COAL_ORE);
							break;
						default:
							world.setChunkType(x2, y2, z2, Material.COAL_ORE);
						}
					}
				}
			}
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