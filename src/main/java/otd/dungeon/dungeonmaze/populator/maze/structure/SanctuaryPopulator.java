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

import org.bukkit.Material;

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;

public class SanctuaryPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 1;
	private static final float ROOM_CHANCE = .003f;

	public boolean const_room = true;

	@Override
	public boolean getConstRoom() {
		return const_room;
	}

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final int x = args.getRoomChunkX();
		final int yFloor = args.getFloorY();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

//        // Register the current room as constant room
//        //DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, 30, z);

		for (int x2 = x; x2 < x + 8; x2 += 1)
			for (int z2 = z; z2 < z + 8; z2 += 1)
				world.setChunkType(x2, yFloor, z2, Material.OBSIDIAN);

		// Outline altar right
		world.setChunkType(x + 2, yFloor + 1, z + 2, Material.GOLD_BLOCK);
		world.setChunkType(x + 3, yFloor + 1, z + 2, Material.NETHERRACK);
		world.setChunkType(x + 4, yFloor + 1, z + 2, Material.NETHERRACK);
		world.setChunkType(x + 5, yFloor + 1, z + 2, Material.GOLD_BLOCK);

		// Center altar
		world.setChunkType(x + 2, yFloor + 1, z + 3, Material.NETHERRACK);
		world.setChunkType(x + 3, yFloor + 1, z + 3, Material.SOUL_SAND);
		world.setChunkType(x + 4, yFloor + 1, z + 3, Material.SOUL_SAND);
		world.setChunkType(x + 5, yFloor + 1, z + 3, Material.NETHERRACK);

		// Outline altar left
		world.setChunkType(x + 2, yFloor + 1, z + 4, Material.GOLD_BLOCK);
		world.setChunkType(x + 3, yFloor + 1, z + 4, Material.NETHERRACK);
		world.setChunkType(x + 4, yFloor + 1, z + 4, Material.NETHERRACK);
		world.setChunkType(x + 5, yFloor + 1, z + 4, Material.GOLD_BLOCK);

		// Torches
		world.setChunkType(x + 2, yFloor + 2, z + 2, Material.TORCH);
		world.setChunkType(x + 5, yFloor + 2, z + 2, Material.TORCH);
		world.setChunkType(x + 2, yFloor + 2, z + 4, Material.TORCH);
		world.setChunkType(x + 5, yFloor + 2, z + 4, Material.TORCH);
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