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
package otd.util;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.IDungeonTask;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import java.util.Random;

/**
 *
 * @author
 */
public class DungeonDelayTask implements IDungeonTask {
	int max_commit_num = 10000;

//        int count = 0;
	@Override
	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {
//            count = count + max_commit_num;
//            Bukkit.getLogger().log(Level.SEVERE, Integer.toString(count));
		return editor.commit(max_commit_num);
//            return true;
	}
}
