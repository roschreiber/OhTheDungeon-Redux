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
package otd.api.event;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import forge_sandbox.ChunkPos;
import otd.world.DungeonType;

/**
 *
 * @author shadow
 */
public class DungeonGeneratedEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final Set<ChunkPos> ichunks;
	private final DungeonType itype;
	private final int centerX, centerY, centerZ;
	private final String extra;
	private final World world;

	public DungeonGeneratedEvent(World world, Set<int[]> chunks, DungeonType type, int centerX, int centerY,
			int centerZ) {
		this.ichunks = new HashSet<>();
		for (int[] chunk : chunks) {
			this.ichunks.add(new ChunkPos(chunk[0], chunk[1]));
		}
		this.itype = type;
		this.centerX = centerX;
		this.centerY = centerY;
		this.centerZ = centerZ;
		this.extra = "";
		this.world = world;
	}

	public DungeonGeneratedEvent(World world, Set<int[]> chunks, DungeonType type, int centerX, int centerY,
			int centerZ, String extra) {
		this.ichunks = new HashSet<>();
		for (int[] chunk : chunks) {
			this.ichunks.add(new ChunkPos(chunk[0], chunk[1]));
		}
		this.itype = type;
		this.centerX = centerX;
		this.centerY = centerY;
		this.centerZ = centerZ;
		this.extra = extra;
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	public String getExtra() {
		return getCustomStructureName();
	}

	public String getCustomStructureName() {
		return this.extra;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getCenterZ() {
		return centerZ;
	}

	public DungeonType getType() {
		return itype;
	}

	public Set<ChunkPos> getChunks() {
		return Collections.unmodifiableSet(ichunks);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
