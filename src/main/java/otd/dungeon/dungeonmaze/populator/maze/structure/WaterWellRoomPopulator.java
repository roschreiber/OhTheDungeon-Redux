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

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;

public class WaterWellRoomPopulator extends MazeRoomBlockPopulator {

	/**
	 * General populator constants.
	 */
	private static final int LAYER_MIN = 3;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .002f;

	public boolean const_room = true;

	@Override
	public boolean getConstRoom() {
		return const_room;
	}

	private final static BlockData STEP2 = Bukkit.createBlockData("minecraft:petrified_oak_slab[type=bottom]");
	private final static BlockData STAIRS0 = Bukkit
			.createBlockData("minecraft:oak_stairs[half=bottom,shape=outer_right,facing=east]");
	private final static BlockData STAIRS1 = Bukkit
			.createBlockData("minecraft:oak_stairs[half=bottom,shape=outer_right,facing=west]");
	private final static BlockData STAIRS2 = Bukkit
			.createBlockData("minecraft:oak_stairs[half=bottom,shape=outer_right,facing=south]");
	private final static BlockData STAIRS3 = Bukkit
			.createBlockData("minecraft:oak_stairs[half=bottom,shape=outer_right,facing=north]");

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);
		final int x = args.getRoomChunkX();
//        final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int z = args.getRoomChunkZ();

//        // Register the current room as constant room
//        //DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);

		// Floor
		for (int x2 = x; x2 <= x + 7; x2 += 1)
			for (int z2 = z; z2 <= z + 7; z2 += 1)
				world.setBlockType(chunkx * 16 + x2, yFloor, chunkz * 16 + z2, Material.STONE);

		// Floor (cobblestone underneath the stone floor)
		for (int x2 = x; x2 <= x + 7; x2 += 1)
			for (int z2 = z; z2 <= z + 7; z2 += 1)
				world.setBlockType(chunkx * 16 + x2, yFloor - 1, chunkz * 16 + z2, Material.COBBLESTONE);

		// Well
		for (int x2 = x + 2; x2 <= x + 4; x2 += 1)
			for (int z2 = z + 2; z2 <= z + 4; z2 += 1)
				world.setBlockType(chunkx * 16 + x2, yFloor + 1, chunkz * 16 + z2, Material.STONE_BRICKS);

		world.setBlockType(chunkx * 16 + x + 3, yFloor + 1, chunkz * 16 + z + 3, Material.WATER);

		// Poles
		world.setBlockType(chunkx * 16 + x + 2, yFloor + 2, chunkz * 16 + z + 2, Material.OAK_FENCE);
		world.setBlockType(chunkx * 16 + x + 2, yFloor + 2, chunkz * 16 + z + 4, Material.OAK_FENCE);
		world.setBlockType(chunkx * 16 + x + 4, yFloor + 2, chunkz * 16 + z + 2, Material.OAK_FENCE);
		world.setBlockType(chunkx * 16 + x + 4, yFloor + 2, chunkz * 16 + z + 4, Material.OAK_FENCE);

		// Roof
		world.setBlockState(chunkx * 16 + x + 2, yFloor + 3, chunkz * 16 + z + 2, STEP2);
		world.setBlockState(chunkx * 16 + x + 2, yFloor + 3, chunkz * 16 + z + 3, STAIRS0);
		world.setBlockState(chunkx * 16 + x + 2, yFloor + 3, chunkz * 16 + z + 4, STEP2);
		world.setBlockState(chunkx * 16 + x + 3, yFloor + 3, chunkz * 16 + z + 2, STAIRS2);
		world.setBlockType(chunkx * 16 + x + 3, yFloor + 3, chunkz * 16 + z + 3, Material.GLOWSTONE);
		world.setBlockState(chunkx * 16 + x + 3, yFloor + 3, chunkz * 16 + z + 4, STAIRS3);
		world.setBlockState(chunkx * 16 + x + 4, yFloor + 3, chunkz * 16 + z + 2, STEP2);
		world.setBlockState(chunkx * 16 + x + 4, yFloor + 3, chunkz * 16 + z + 3, STAIRS1);
		world.setBlockState(chunkx * 16 + x + 4, yFloor + 3, chunkz * 16 + z + 4, STEP2);
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