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
package otd.world.task;

import otd.world.DungeonType;

/**
 *
 * @author shadow
 */
public class DungeonPlaceTask extends DungeonWorldTask {

	public final DungeonType dungeon;

	public DungeonPlaceTask(int x, int z, DungeonType dungeon) {
		super(x, z);
		this.dungeon = dungeon;
	}

	@Override
	public int getDelay() {
		if (dungeon == DungeonType.Roguelike)
			return 200;
		return 100;
	}
}
