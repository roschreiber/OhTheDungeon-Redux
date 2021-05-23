/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.commands;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
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
public class CommandDebug implements CommandExecutor {
    
    private PerPlayerDungeonInstance plugin;
    public CommandDebug(PerPlayerDungeonInstance plugin) {
        this.plugin = plugin;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("perplayerdungeoninstance.admin")) {
                p.sendMessage(ChatColor.RED + I18n.get("NoPermission"));
                return true;
            }
        }
        
        sender.sendMessage(ChatColor.BLUE + "Pending: ");
        sender.sendMessage(ChatColor.BLUE + plugin.getAsyncTaskPool().getPending().toString());
        sender.sendMessage(ChatColor.BLUE + "Running: ");
        sender.sendMessage(ChatColor.BLUE + plugin.getAsyncTaskPool().getRunning().toString());
        
        return true;
    }
}
