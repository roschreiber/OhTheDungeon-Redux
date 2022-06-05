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
package otd.dungeon.dungeonmaze.populator.maze.spawner;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

//import zhehe.com.timvisee.dungeonmaze.Config;
//import java.util.Random;

import otd.dungeon.dungeonmaze.util.NumberUtils;
import otd.dungeon.dungeonmaze.util.SpawnerUtils;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.smoofy.Spawner_Later;

public class CreeperSpawnerRoomPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 5;
	private static final float ROOM_CHANCE = .003f;

	public boolean const_room = true;

	@Override
	public boolean getConstRoom() {
		return const_room;
	}

	/** Populator constants. */
	private static final double SPAWN_DISTANCE_MIN = 5; // Chunks

	// TODO: Implement this!
	public static final double CHANCE_SPAWNER_ADDITION_EACH_LEVEL = -0.333; /* to 1 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		AsyncWorldEditor world = args.getWorld();
//		Random rand = args.getRandom();
		int x = args.getRoomChunkX();
//		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Make sure the distance between the spawn and the current chunk is allowed
		if (NumberUtils.distanceFromZero(chunkx, chunkz) < SPAWN_DISTANCE_MIN)
			return;

		// Register the current room as constant room
		// DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(),
		// chunk.getZ(), x, y, z);

		// Create the core
		world.setChunkType(x + 3, yFloor + 1, z + 4, Material.NETHER_BRICKS);
		world.setChunkType(x + 4, yFloor + 1, z + 3, Material.NETHER_BRICKS);
		world.setChunkType(x + 3, yFloor + 1, z + 2, Material.NETHER_BRICKS);
		world.setChunkType(x + 2, yFloor + 1, z + 3, Material.NETHER_BRICKS);
		world.setChunkType(x + 3, yFloor + 2, z + 3, Material.NETHER_BRICKS);

		// Create the spawner
		if (SpawnerUtils.isCreeperAllowed(world.getWorld())) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 3, yFloor + 1, chunkz * 16 + z + 3,
					EntityType.CREEPER);
			world.addLater(later);
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