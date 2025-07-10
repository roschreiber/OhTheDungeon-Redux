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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import otd.config.WorldConfig;
import otd.gui.dungeon_plot.UserTeleport;
import otd.world.DungeonTask;
import otd.world.DungeonWorld;
import otd.util.I18n;
import otd.redux.util.ChatManager;
import otd.redux.util.ChatManager.MessageType;

/**
 *
 * @author shadow
 */
public class Otd_Tp implements TabExecutor {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		if (sender == null)
			return false;
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatManager.getInstance().formatMessage("This is a player only command", MessageType.ERROR));
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("oh_the_dungeons.teleport")) {
			sender.sendMessage(ChatManager.getInstance().formatMessage(I18n.instance.No_Permission, MessageType.ERROR));
			return true;
		}

		if (DungeonWorld.world == null || WorldConfig.wc.dungeon_world.finished == false
				|| DungeonTask.isGenerating()) {
			sender.sendMessage(ChatManager.getInstance().formatMessage(I18n.instance.Dungeon_Plot_Not_Ready, MessageType.ERROR));
			return true;
		}

		if (args.length != 0)
			return false;

		UserTeleport ut = new UserTeleport();
		ut.openInventory(p);
		sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));	

		return true;
	}
}
