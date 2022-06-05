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
package otd.populator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;
import otd.config.SimpleWorldConfig;
import otd.config.SimpleWorldConfig.Roguelike.Themes;
import otd.config.WorldConfig;
import otd.lib.async.AsyncRoguelikeDungeon;
import otd.lib.async.AsyncWorldEditor;
import otd.util.ActualHeight;
import otd.util.AsyncLog;
import otd.world.ChunkList;

/**
 *
 * @author
 */
public class RoguelikePopulator extends IPopulator {

//    public final static List<DungeonSettings> dungeons;
//    static {
//        dungeons = new ArrayList<>();
//        dungeons.add(SettingsContainer.house);
//        dungeons.add(SettingsContainer.desert);
//        dungeons.add(SettingsContainer.bunker);
//        dungeons.add(SettingsContainer.jungle);
//        dungeons.add(SettingsContainer.swamp);
//        dungeons.add(SettingsContainer.mountain);
//        dungeons.add(SettingsContainer.forest);
//        dungeons.add(SettingsContainer.mesa);
//        dungeons.add(SettingsContainer.ice);
//        dungeons.add(SettingsContainer.ruin);
//        dungeons.add(SettingsContainer.rare);
//    }

	@Override
	public Set<String> getBiomeExclusions(World world) {
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
		return swc.roguelike.biomeExclusions;
	}

	@Override
	public int getDungeonConstant() {
		return 3;
	}

	@Override
	public boolean generateDungeon(World world, Random random, Chunk chunk) {
		AsyncWorldEditor editor = new AsyncWorldEditor(world);

		int x = chunk.getX() * 16 + 4;
		int z = chunk.getZ() * 16 + 4;
		int avg_y = 0;

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				Location loc = world.getHighestBlockAt(x + i, z + j).getLocation();
				loc = ActualHeight.getHeight(loc);
				avg_y += loc.getBlockY();
			}
		}
		avg_y = avg_y / 9;

		editor.setVirtualGround(true);
		editor.setVirtualGroundHeight(avg_y);

		boolean res = AsyncRoguelikeDungeon.generateAsync(random, editor, x, z);
		if (res)
			AsyncLog.logMessage("[Roguelike Dungeon @ " + world.getName() + "] x=" + chunk.getX() * 16 + ", z="
					+ chunk.getZ() * 16);
		return res;
	}

	public boolean generateDungeonWithRandomTheme(World world, Random random, Chunk chunk) {
		List<DungeonSettings> adungeons = new ArrayList<>();
		String world_name = world.getName();
		if (WorldConfig.wc.dict.containsKey(world_name)) {
			Themes theme = WorldConfig.wc.dict.get(world_name).roguelike.themes;
			if (theme.house)
				adungeons.add(SettingsContainer.house);
			if (theme.desert)
				adungeons.add(SettingsContainer.desert);
			if (theme.bunker)
				adungeons.add(SettingsContainer.bunker);
			if (theme.jungle)
				adungeons.add(SettingsContainer.jungle);
			if (theme.swamp)
				adungeons.add(SettingsContainer.swamp);
			if (theme.mountain)
				adungeons.add(SettingsContainer.mountain);
			if (theme.forest)
				adungeons.add(SettingsContainer.forest);
			if (theme.mesa)
				adungeons.add(SettingsContainer.mesa);
			if (theme.ice)
				adungeons.add(SettingsContainer.ice);
			if (theme.ruin)
				adungeons.add(SettingsContainer.ruin);
			if (theme.rare)
				adungeons.add(SettingsContainer.rare);
		} else {
			adungeons.add(SettingsContainer.house);
			adungeons.add(SettingsContainer.desert);
			adungeons.add(SettingsContainer.bunker);
			adungeons.add(SettingsContainer.jungle);
			adungeons.add(SettingsContainer.swamp);
			adungeons.add(SettingsContainer.mountain);
			adungeons.add(SettingsContainer.forest);
			adungeons.add(SettingsContainer.mesa);
			adungeons.add(SettingsContainer.ice);
			adungeons.add(SettingsContainer.ruin);
			adungeons.add(SettingsContainer.rare);
		}

		AsyncWorldEditor editor = new AsyncWorldEditor(world);
		boolean res = AsyncRoguelikeDungeon.generateAsync(random, editor, chunk.getX() * 16 + 4, chunk.getZ() * 16 + 4,
				ChunkList.chunk_set, adungeons.get(random.nextInt(adungeons.size())), 0);
		if (res)
			AsyncLog.logMessage("[Roguelike Dungeon @ " + world.getName() + "] x=" + chunk.getX() * 16 + ", z="
					+ chunk.getZ() * 16);
		return res;
	}
}
