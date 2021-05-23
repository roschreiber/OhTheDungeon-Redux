/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.commands;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.addon.com.ohthedungeon.storydungeon.gui.GUI_Util;
import otd.addon.com.ohthedungeon.storydungeon.util.I18n;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author shadow_wind
 */
public class CommandMenu implements CommandExecutor {
    
    private PerPlayerDungeonInstance plugin;
    public CommandMenu(PerPlayerDungeonInstance plugin) {
        this.plugin = plugin;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("perplayerdungeoninstance.menu")) {
                p.sendMessage(ChatColor.RED + I18n.get("NoPermission"));
                return true;
            }
            GUI_Util.showMenu(p);
        } else {
            sender.sendMessage("Player only command");
        }
//        if(sender instanceof Player) {
//            Player p = (Player) sender;
//            p.teleport(new Location(Bukkit.getWorld("otd_dungeon"), 0, 90, 0));
//            p.getInventory().addItem(Book.get("test", 0, 0, p.getName(), "1", "2", "3"));
//        } else {
//            WorldManager.startTest(plugin.getFakeGenerator());
//        }
        return true;
    }
}
