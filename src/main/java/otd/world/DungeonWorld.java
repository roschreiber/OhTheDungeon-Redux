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
package otd.world;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import otd.Main;
import otd.config.WorldConfig;
import otd.gui.dungeon_plot.UserTeleport;
import otd.util.FileUtils;

/**
 *
 * @author shadow
 */
public class DungeonWorld {
	public static World world = null;
	public static DungeonWorldChunkGenerator generator = new DungeonWorldChunkGenerator();

	public static void loadDungeonWorld() {
		WorldCreator wc = new WorldCreator(WorldDefine.WORLD_NAME);
		wc.environment(World.Environment.NORMAL);
		wc.generator(generator);

		wc.generateStructures(false);
		wc.type(WorldType.NORMAL);

		world = wc.createWorld();
	}

	public static boolean generateDungeonWorld() {

		File container = Bukkit.getWorldContainer();
		File world_folder = new File(container, WorldDefine.WORLD_NAME);
		if (world_folder.exists()) {
			if (!FileUtils.deleteDirectory(world_folder)) {
				return false;
			}
		}
		loadDungeonWorld();
		return true;
	}

	public static void removeDungeonWorld() {
		if (world == null)
			return;

		List<Player> ps = world.getPlayers();
		for (Player p : ps) {
			UserTeleport.teleportBed(p);
		}

		File fw = world.getWorldFolder();

		Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
			if (!Bukkit.unloadWorld(world, false))
				return;
			world = null;

			FileUtils.deleteDirectory(fw);
			WorldConfig.wc.dungeon_world.finished = false;
			WorldConfig.save();
		}, 1L);
	}
}
