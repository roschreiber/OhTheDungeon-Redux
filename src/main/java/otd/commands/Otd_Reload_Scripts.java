package otd.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import otd.redux.util.ChatManager;
import otd.redux.util.ChatManager.MessageType;

import otd.script.JSLoader;

public class Otd_Reload_Scripts implements TabExecutor {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		boolean flag = false;
		if ((sender instanceof ConsoleCommandSender)) {
			flag = true;
		} else if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("oh_the_dungeons.admin")) {
				sender.sendMessage(ChatManager.getInstance().formatMessage("You don't have permission to do that", MessageType.ERROR));
				return true;
			}
			flag = true;
		}
		if (flag) {
			JSLoader.init();
			sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));
		}
		return true;
	}
}
