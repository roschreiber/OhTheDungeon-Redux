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
package otd.dungeon.dungeonmaze.populator;

import java.util.Random;
import java.util.Set;

import otd.lib.async.AsyncWorldEditor;

public class ChunkBlockPopulatorArgs {

	/** The world of the chunk. */
	private AsyncWorldEditor w;
	/** The random object used for random generation with the proper seed. */
	private Random rand;
	/** The source chunk. */
	public Set<String> custom;
	public int chunkx, chunkz;

	/** The dungeon chunk data. */

	/**
	 * Constructor.
	 *
	 * @param world        World
	 * @param rand         Random instance
	 * @param chunk        Source chunk
	 * @param dungeonChunk Dungeon chunk instance
	 */
	public ChunkBlockPopulatorArgs(AsyncWorldEditor world, Random rand, Set<String> custom, int chunkx, int chunkz) {
		this.w = world;
		this.rand = rand;
		this.custom = custom;
		this.chunkx = chunkx;
		this.chunkz = chunkz;
	}

	public int getChunkX() {
		return this.chunkx;
	}

	public int getChunkZ() {
		return this.chunkz;
	}

	/**
	 * Get the world
	 * 
	 * @return World
	 */
	public AsyncWorldEditor getWorld() {
		this.w.setChunk(chunkx, chunkz);
		return this.w;
	}

	/**
	 * Get the Random instance
	 * 
	 * @return Random instance
	 */
	public Random getRandom() {
		return this.rand;
	}

	/**
	 * Set the Random instance
	 * 
	 * @param rand Random instance
	 */
	public void setRandom(Random rand) {
		this.rand = rand;
	}
}
