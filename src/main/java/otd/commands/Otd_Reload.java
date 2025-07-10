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

import com.google.gson.Gson;

import otd.Main;
import otd.config.WorldConfig;
import otd.redux.util.ChatManager;
import otd.redux.util.ChatManager.MessageType;

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
		
		if (args.length == 2 && args[0].equalsIgnoreCase("chat")) {
			List<String> options = new ArrayList<>();
			options.add("prefix");
			
			if (args[1].isEmpty()) {
				return options;
			} else {
				List<String> filtered = new ArrayList<>();
				for (String option : options) {
					if (option.startsWith(args[1].toLowerCase())) {
						filtered.add(option);
					}
				}
				return filtered;
			}
		}
		
		if (args.length == 3 && args[0].equalsIgnoreCase("chat") && args[1].equalsIgnoreCase("prefix")) {
			List<String> options = new ArrayList<>();
			options.add(ChatManager.getInstance().getRawPrefix());
			return options;
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

		// Handle chat prefix update
		if (args.length >= 1 && args[0].equalsIgnoreCase("chat")) {
			if (args.length >= 2 && args[1].equalsIgnoreCase("prefix")) {
				if (args.length >= 3) {
					// Update the prefix
					StringBuilder newPrefix = new StringBuilder();
					for (int i = 2; i < args.length; i++) {
						if (i > 2) newPrefix.append(" ");
						newPrefix.append(args[i]);
					}
					
					ChatManager.getInstance().updatePrefix(newPrefix.toString());
					sender.sendMessage(ChatManager.getInstance().formatMessage("Chat prefix updated successfully", MessageType.SUCCESS));
					return true;
				} else {
					// Show the current prefix
					String currentPrefix = ChatManager.getInstance().getRawPrefix();
					sender.sendMessage(ChatManager.getInstance().formatMessage("Current chat prefix: " + currentPrefix, MessageType.INFO));
					return true;
				}
			} else {
				// Reload the chat configuration
				ChatManager.getInstance().loadConfig();
				sender.sendMessage(ChatManager.getInstance().formatMessage("Chat configuration reloaded", MessageType.SUCCESS));
				return true;
			}
		}
		
		// If not handling chat, or if "all" or "config" is specified, reload the world config
		if (args.length == 0 || args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("config")) {
			Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
				String value = WorldConfig.db.getValue("config");
				if (value != null && !value.isEmpty()) {
					WorldConfig wc = (new Gson()).fromJson(value, WorldConfig.class);
					Bukkit.getScheduler().runTask(Main.instance, () -> {
						WorldConfig.wc = wc;
						sender.sendMessage(ChatManager.getInstance().formatMessage("World configuration reloaded", MessageType.SUCCESS));
					});
				}
			});
			
			// If "all" is specified, also reload chat config
			if (args.length > 0 && args[0].equalsIgnoreCase("all")) {
				ChatManager.getInstance().loadConfig();
				sender.sendMessage(ChatManager.getInstance().formatMessage("Chat configuration reloaded", MessageType.SUCCESS));
			}
			
			return true;
		}
		
		// If we get here, the command syntax was incorrect
		sender.sendMessage(ChatManager.getInstance().formatMessage("Usage: /otd_reload [all|config|chat] [prefix] [new_prefix]", MessageType.INFO));
		return true;
	}
}
