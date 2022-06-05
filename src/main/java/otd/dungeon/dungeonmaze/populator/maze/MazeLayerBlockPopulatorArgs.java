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
package otd.dungeon.dungeonmaze.populator.maze;

import java.util.Random;
import java.util.Set;

import otd.dungeon.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;

public class MazeLayerBlockPopulatorArgs extends ChunkBlockPopulatorArgs {

	/** Defines the current layer number. */
	private int layer = 1;
	/** Defines the current Y coordinate of the room. */
	protected int y = 0;

	/**
	 * Constructor.
	 *
	 * @param world        World.
	 * @param rand         Random instance.
	 * @param chunk        Source chunk.
	 * @param dungeonChunk Dungeon chunk.
	 * @param layer        Layer.
	 */
	public MazeLayerBlockPopulatorArgs(AsyncWorldEditor world, Random rand, int chunkX, int chunkZ, Set<String> custom,
			int layer, int y) {
		super(world, rand, custom, chunkX, chunkZ);
		this.layer = layer;
		this.y = y;
	}

	/**
	 * Get the current layer that should be generated
	 * 
	 * @return Current layer
	 */
	public int getLayer() {
		return this.layer;
	}

	/**
	 * Set the current layer that should be generated
	 * 
	 * @param layer The layer.
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	/**
	 * Get the Y coordinate.
	 *
	 * @return Y coordinate.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Get the Y coordinate in the current chunk.
	 *
	 * @return Y coordinate in the current chunk.
	 */
	public int getChunkY() {
		return this.getY();
	}

	/**
	 * Set the Y coordinate.
	 *
	 * @param y Y coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}
}
