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
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import otd.config.WorldConfig;
import otd.util.I18n;

public class Otd_Cp implements TabExecutor {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> res = new ArrayList<>();
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("oh_the_dungeons.admin"))
				return res;
		}
		if (args.length == 1 || args.length == 2) {
			for (World w : Bukkit.getWorlds()) {
				res.add(w.getName());
			}
		}
		return res;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		if (sender == null)
			return false;
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("oh_the_dungeons.admin")) {
				sender.sendMessage(I18n.instance.No_Permission);
				return true;
			}
		}

		if (args.length != 2)
			return false;
		if (!WorldConfig.wc.dict.containsKey(args[0])) {
			sender.sendMessage("Invalid source world");
			return true;
		}

		WorldConfig.wc.dict.put(args[1], WorldConfig.wc.dict.get(args[0]));
		WorldConfig.save();
		sender.sendMessage("Done");

		return true;
	}
}
