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

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.papermc.lib.PaperLib;
import net.md_5.bungee.api.ChatColor;
import otd.Main;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.integration.MythicMobsImpl;

/**
 *
 * @author
 */
public class Diagnostic {

	private static boolean isWorldHookReady() {
//		List<World> worlds = Bukkit.getWorlds();
//		for (World world : worlds) {
//			if (world.getName().equalsIgnoreCase(DungeonWorldManager.WORLD_NAME))
//				continue;
//			if (world.getName().equalsIgnoreCase(WorldDefine.WORLD_NAME))
//				continue;
//			List<BlockPopulator> populators = world.getPopulators();
//			boolean flag = false;
//			for (BlockPopulator populator : populators) {
//				if (populator instanceof DungeonPopulator) {
//					flag = true;
//					break;
//				}
//			}
//			if (!flag)
//				return false;
//		}
		return true;
	}

	public static void diagnostic() {
		if (!isWorldHookReady()) {
			Bukkit.getLogger().log(Level.INFO,
					"{0}[OTD] Looks like you use /reload to load this plugin.,.. You need to restart your server, otherwise it won''t work",
					ChatColor.RED);
		}
	}

	private static boolean isJre32() {
		return System.getProperty("sun.arch.data.model").equals("32");
	}

//	public static void main(String[] args) {
//		System.out.println(System.getProperty("sun.arch.data.model"));
//	}

	private static boolean getBoolean(String path, boolean def, String worldName) {
		File spigot = new File(Main.instance.getServer().getWorldContainer() + File.separator + "spigot.yml");
//		Bukkit.getLogger().info(spigot.getAbsolutePath());
		FileConfiguration spigot_yml = YamlConfiguration.loadConfiguration(spigot);
//		Bukkit.getLogger().info(spigot_yml.saveToString());
		return spigot_yml.getBoolean("world-settings." + worldName + "." + path,
				spigot_yml.getBoolean("world-settings.default." + path));
	}

	public static boolean isSpawnerNotReady() {
		boolean b = false;
		for (World world : Bukkit.getWorlds()) {
			b = b | getBoolean("nerf-spawner-mobs", false, world.getName());
		}
		return b;
	}

	public static void check(Player p) {
		int count = 0;
		if (isSpawnerNotReady()) {
			count++;
			p.sendMessage(ChatColor.BLUE
					+ "https://github.com/OhTheDungeon/OhTheDungeon/blob/main/docs/Spawner_Not_Working.md");
		}
		if (isJre32()) {
			count++;
			p.sendMessage(ChatColor.BLUE + "https://github.com/OhTheDungeon/OhTheDungeon/blob/main/docs/Use_Jre64.md");
		}
		if (!isWorldHookReady()) {
			count++;
			p.sendMessage(ChatColor.BLUE
					+ "https://github.com/OhTheDungeon/OhTheDungeon/blob/main/docs/Fail_to_Hook_Minecraft_World_Object.md");
		}
		if (!PaperLib.isPaper()) {
			count++;
			p.sendMessage(
					ChatColor.BLUE + "https://github.com/OhTheDungeon/OhTheDungeon/blob/main/docs/Using_Paper.md");
		}
		{
			boolean found = false;
			for (World w : Bukkit.getWorlds()) {
				String name = w.getName();
				if (WorldConfig.wc.dict.containsKey(name)) {
					SimpleWorldConfig swc = WorldConfig.wc.dict.get(name);
					if (swc.boss == SimpleWorldConfig.BossType.MythicMobs)
						found = true;
				}
			}
			if (found) {
				if (!MythicMobsImpl.lichBossReady()) {
					p.sendMessage(
							"You open the MythicMobs support but no otd_lich_boss is found in your MythicMobs config");
				}
				if (!MythicMobsImpl.kingCastleBossReady()) {
					p.sendMessage(
							"You open the MythicMobs support but no otd_castle_king is found in your MythicMobs config");
				}
			}
		}

		if (count > 0) {
			p.sendMessage("OTD found " + count + " issue(s) on your server. Check the above link for help");
		} else {
			p.sendMessage("OTD found 0 issues on your server.");
		}
	}
}
