/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.commands;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.addon.com.ohthedungeon.storydungeon.util.I18n;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author shadow_wind
 */
public class CommandBack implements CommandExecutor {

	// private PerPlayerDungeonInstance plugin;
	public CommandBack(PerPlayerDungeonInstance plugin) {
		// this.plugin = plugin;
	}

	// This method is called, when somebody uses our command
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		World world;
		try {
			world = Bukkit.getServer().getWorlds().get(0);
		} catch (Exception ex) {
			world = null;
		}
		if (world == null)
			return true;

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("perplayerdungeoninstance.back")) {
				p.sendMessage(ChatColor.RED + I18n.get("NoPermission"));
				return true;
			}
			p.teleport(world.getSpawnLocation());
//            p.getInventory().addItem(Book.get("test", 0, 0, p.getName(), "1", "2", "3"));
		} else {
			sender.sendMessage("Player only command");
		}
		return true;
	}
}
