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

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

/**
 *
 * @author
 */
public class EmptyRoom extends MazeRoomBlockPopulator {

	// TODO: Use material enums instead of ID's due to ID deprecation by Mojang

	/** General populator constants. */
//	private static final int LAYER_MIN = 1;
//	private static final int LAYER_MAX = 7;
//	private static final float ROOM_CHANCE = .001f;

	public boolean const_room = true;

	@Override
	public boolean getConstRoom() {
		return const_room;
	}

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {

	}
}
