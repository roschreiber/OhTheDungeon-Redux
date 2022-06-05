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

public class CobblestonePopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .2f;

	/** Populator constants. */
	private static final int CHANCE_CORNER = 75;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
		final int webX = x + rand.nextInt(6) + 1;
		final int webY = args.getFloorY();
		final int webCeilingY = args.getCeilingY();
		final int webZ = z + rand.nextInt(6) + 1;

		if (rand.nextInt(100) < CHANCE_CORNER) {
			int bx = x + (rand.nextInt(2) * 5);
			int by = webCeilingY;
			int bz = z + (rand.nextInt(2) * 5);
			if (world.getChunkType(bx, by, bz) == Material.AIR)
				world.setChunkType(bx, by, bz, Material.COBWEB);
			else if (!(world.getChunkType(webX, webY - 1, webZ) == Material.AIR))
				if (world.getChunkType(webX, webY, webZ) == Material.AIR)
					world.setChunkType(webX, webY, webZ, Material.COBBLESTONE);
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