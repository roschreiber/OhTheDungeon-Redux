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
package otd.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import otd.Main;
import otd.addon.com.ohthedungeon.storydungeon.util.I18n;
import otd.config.WorldConfig;
import otd.redux.util.ChatManager;
import otd.redux.util.ChatManager.MessageType;
import otd.script.JSLoader;

/**
 *
 * @author shadow
 */
public class Otd_Reload implements TabExecutor {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			List<String> options = new ArrayList<>();
			options.add("all");
			options.add("config");
			options.add("chat");
			options.add("scripts");
			options.add("lang");
			
			if (args[0].isEmpty()) {
				return options;
			} else {
				List<String> filtered = new ArrayList<>();
				for (String option : options) {
					if (option.startsWith(args[0].toLowerCase())) {
						filtered.add(option);
					}
				}
				return filtered;
			}
		}		
		return new ArrayList<>();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		if (sender == null)
			return false;

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("oh_the_dungeons.admin")) {
				sender.sendMessage(ChatManager.getInstance().formatMessage("You don't have permission to do that", MessageType.ERROR));
				return true;
			}
		}

		if (args.length >= 1 && args[0].equalsIgnoreCase("chat")) {
			ChatManager.getInstance().loadConfig();
			sender.sendMessage(ChatManager.getInstance().formatMessage("Chat configuration reloaded", MessageType.SUCCESS));
			return true;
		}

		if (args.length >= 1 && args[0].equalsIgnoreCase("config")) {
			Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
				WorldConfig.reloadFromYaml();
				WorldConfig.loadWorldConfig();
				Bukkit.getScheduler().runTask(Main.instance, () -> {
					otd.locate.DungeonLog.load();
					sender.sendMessage(ChatManager.getInstance().formatMessage("World configuration reloaded", MessageType.SUCCESS));
				});
			});
			return true;
		}

		if (args.length >= 1 && args[0].equalsIgnoreCase("lang")) {
			otd.util.I18n.init();
			I18n.init();
			sender.sendMessage(ChatManager.getInstance().formatMessage("Language config reloaded", MessageType.SUCCESS));
			return true;
		}

		if (args.length >= 1 && args[0].equalsIgnoreCase("scripts")) {
			JSLoader.init();
			sender.sendMessage(ChatManager.getInstance().formatMessage("JS scripts reloaded", MessageType.SUCCESS));
			return true;
		}

		if (args.length == 0 || args[0].equalsIgnoreCase("all")) {
			ChatManager.getInstance().loadConfig();
			sender.sendMessage(ChatManager.getInstance().formatMessage("Chat configuration reloaded", MessageType.SUCCESS));
			Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
				WorldConfig.reloadFromYaml();
				Bukkit.getScheduler().runTask(Main.instance, () -> {
					otd.locate.DungeonLog.load();
					sender.sendMessage(ChatManager.getInstance().formatMessage("World configuration reloaded", MessageType.SUCCESS));
				});
			});
			JSLoader.init();
			sender.sendMessage(ChatManager.getInstance().formatMessage("JS scripts reloaded", MessageType.SUCCESS));
			I18n.init();
			otd.util.I18n.init();
			sender.sendMessage(ChatManager.getInstance().formatMessage("Language config reloaded", MessageType.SUCCESS));
			return true;
		}

		sender.sendMessage(ChatManager.getInstance().formatMessage("Usage: /otd_reload [all|config|chat|lang|scripts]", MessageType.INFO));
		return true;
	}
}
