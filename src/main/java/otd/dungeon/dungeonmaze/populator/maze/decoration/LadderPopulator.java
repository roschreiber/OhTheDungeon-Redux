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

public class LadderPopulator extends MazeRoomBlockPopulator {

	private static final BlockData LADDER0 = Bukkit.createBlockData(Material.LADDER);
	private static final BlockData LADDER2 = Bukkit.createBlockData("minecraft:ladder[facing=north]");
	private static final BlockData LADDER3 = Bukkit.createBlockData("minecraft:ladder[facing=south]");
	private static final BlockData LADDER4 = Bukkit.createBlockData("minecraft:ladder[facing=west]");
	private static final BlockData LADDER5 = Bukkit.createBlockData("minecraft:ladder[facing=east]");

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
	private static final float ROOM_CHANCE = .05f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
		final int startX;
		final int startY = args.getFloorY() + 1;
		final int startZ;

		BlockData ladderData = LADDER0;
		switch (rand.nextInt(2)) {
		case 0:
			int r = rand.nextInt(2);
			startX = x + 1 + (r * 5);
			startZ = z + rand.nextInt(2) * 7;
			if (r == 0)
				ladderData = LADDER5;
			else
				ladderData = LADDER4;
			break;

		case 1:
			int r2 = rand.nextInt(2);
			startX = x + rand.nextInt(2) * 7;
			startZ = z + 1 + (r2 * 5);
			if (r2 == 0)
				ladderData = LADDER3;
			else
				ladderData = LADDER2;
			break;

		default:
			startX = x + 1 + (rand.nextInt(2) * 5);
			startZ = z + rand.nextInt(2) * 7;
		}

		// Make sure there's no wall or anything else
		if (world.getChunkType(startX, startY, startZ) == Material.AIR) {
			for (int ladderY = startY; ladderY <= startY + 8; ladderY++) {
				world.setChunkData(startX, ladderY, startZ, ladderData);
				if (ladderData == LADDER2) {
					Material b = world.getChunkType(startX, ladderY, startZ + 1);
					if (b == Material.AIR)
						world.setChunkType(startX, ladderY, startZ + 1, Material.COBBLESTONE);
				} else if (ladderData == LADDER3) {
					Material b = world.getChunkType(startX, ladderY, startZ - 1);
					if (b == Material.AIR)
						world.setChunkType(startX, ladderY, startZ - 1, Material.COBBLESTONE);
				} else if (ladderData == LADDER4) {
					Material b = world.getChunkType(startX + 1, ladderY, startZ);
					if (b == Material.AIR)
						world.setChunkType(startX + 1, ladderY, startZ, Material.COBBLESTONE);
				} else if (ladderData == LADDER5) {
					Material b = world.getChunkType(startX - 1, ladderY, startZ);
					if (b == Material.AIR)
						world.setChunkType(startX - 1, ladderY, startZ, Material.COBBLESTONE);
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