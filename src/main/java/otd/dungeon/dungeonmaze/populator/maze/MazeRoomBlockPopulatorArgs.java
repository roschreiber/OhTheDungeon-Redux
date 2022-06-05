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

import otd.lib.async.AsyncWorldEditor;

public class MazeRoomBlockPopulatorArgs extends MazeLayerBlockPopulatorArgs {

	/** Defines the X coordinate of the chunk. */
	private int x = 0;
	/** Defines the Z coordinate of the chunk. */
	private int z = 0;
	/** Defines the floor offset. */
	private int floorOffset = 0;
	/** Defines the ceiling offset. */
	private int ceilingOffset = 0;

	/**
	 * Constructor.
	 *
	 * @param world         World.
	 * @param rand          Random instance.
	 * @param chunk         Source chunk.
	 * @param layer         Layer.
	 * @param x             X coordinate.
	 * @param y             Y coordinate.
	 * @param z             Z coordinate.
	 * @param floorOffset   Floor offset.
	 * @param ceilingOffset Ceiling offset.
	 */
	public MazeRoomBlockPopulatorArgs(AsyncWorldEditor world, Random rand, int chunkX, int chunkZ, Set<String> custom,
			int layer, int x, int y, int z, int floorOffset, int ceilingOffset) {
		super(world, rand, chunkX, chunkZ, custom, layer, y);
		this.x = x;
		this.z = z;
		this.floorOffset = floorOffset;
		this.ceilingOffset = ceilingOffset;
	}

	/**
	 * Get the X coordinate.
	 *
	 * @return X coordinate.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Get the X coordinate inside the current chunk.
	 *
	 * @return X coordinate inside the current chunk.
	 */
	public int getRoomChunkX() {
		return ((this.x % 16) + 16) % 16;
	}

	/**
	 * Set the X coordinate.
	 *
	 * @param x X coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get the Z coordinate.
	 *
	 * @return Z coordinate.
	 */
	public int getZ() {
		return this.z;
	}

	/**
	 * Get the Z coordinate inside the current chunk.
	 *
	 * @return Z coordinate inside the current chunk.
	 */
	public int getRoomChunkZ() {
		return ((this.z % 16) + 16) % 16;
	}

	/**
	 * Set the Z coordinate.
	 *
	 * @param z Z coordinate.
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * Get the floor offset.
	 *
	 * @return Floor offset.
	 */
	public int getFloorOffset() {
		return this.floorOffset;
	}

	/**
	 * Set the floor offset.
	 *
	 * @param floorOffset Floor offset.
	 */
	public void setFloorOffset(int floorOffset) {
		this.floorOffset = floorOffset;
	}

	/**
	 * Get the floor Y coordinate (Y coordinate + floor offset).
	 *
	 * @return Floor Y coordinate.
	 */
	public int getFloorY() {
		return (this.y + this.floorOffset);
	}

	/**
	 * Get the ceiling offset.
	 *
	 * @return Ceiling offset.
	 */
	public int getCeilingOffset() {
		return this.ceilingOffset;
	}

	/**
	 * Set the ceiling offset.
	 *
	 * @param ceilingOffset Ceiling offset.
	 */
	public void setCeilingOffset(int ceilingOffset) {
		this.ceilingOffset = ceilingOffset;
	}

	/**
	 * Get the ceiling Y coordinate.
	 *
	 * @return Ceiling Y coordinate.
	 */
	public int getCeilingY() {
		return (this.y + 6 + this.ceilingOffset);
	}
}
