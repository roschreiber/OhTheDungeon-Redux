/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.commands;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.addon.com.ohthedungeon.storydungeon.generator.BaseGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.FakeGenerator;
import otd.addon.com.ohthedungeon.storydungeon.gui.Menu;
import otd.addon.com.ohthedungeon.storydungeon.util.I18n;
import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author shadow_wind
 */
public class CommandCreate implements TabExecutor {
    
    private PerPlayerDungeonInstance plugin;
    public CommandCreate(PerPlayerDungeonInstance plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> res = new ArrayList<>();
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("perplayerdungeoninstance.admin")) return res;
        }
        if(args.length == 1) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                res.add(p.getName());
            }
        }
        if(args.length == 2) {
            Map<String, BaseGenerator> generators = FakeGenerator.getGenerators();
            for(Map.Entry<String, BaseGenerator> generator : generators.entrySet()) {
                res.add(generator.getKey());
            }
        }
        if(args.length == 3) {
            Map<String, BlockPopulator> populators = FakeGenerator.getPopulators();
            for(Map.Entry<String, BlockPopulator> populator : populators.entrySet()) {
                res.add(populator.getKey());
            }
        }
        if(args.length == 4) {
            Map<String, DungeonSettings> towers = FakeGenerator.getTowers();
            for(Map.Entry<String, DungeonSettings> tower : towers.entrySet()) {
                res.add(tower.getKey());
            }
        }
        return res;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender == null) return true;
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("perplayerdungeoninstance.admin")) {
                p.sendMessage(ChatColor.RED + I18n.get("NoPermission"));
                return true;
            }
        }
        
        if(args.length != 4) return false;
        
        String player_name = args[0];
        Player p;
        {
            Map<String, Player> tmp = new HashMap<>();
            for(Player player : Bukkit.getOnlinePlayers()) {
                tmp.put(player.getName(), player);
            }
            p = tmp.get(player_name);
        }
        if(p == null) {
            sender.sendMessage("Could not find player");
            return true;
        }
        
        String generator = args[1];
        {
            Set<String> gs = new HashSet<>();
            Map<String, BaseGenerator> generators = FakeGenerator.getGenerators();
            for(Map.Entry<String, BaseGenerator> g : generators.entrySet()) {
                gs.add(g.getKey());
            }
            if(!gs.contains(generator)) {
                sender.sendMessage("Wrong generator name");
                return true;
            }
        }
        
        String populator = args[2];
        {
            Set<String> ps = new HashSet<>();
            Map<String, BlockPopulator> populators = FakeGenerator.getPopulators();
            for(Map.Entry<String, BlockPopulator> pop : populators.entrySet()) {
                ps.add(pop.getKey());
            }
            if(!ps.contains(populator)) {
                sender.sendMessage("Wrong populator name");
                return true;
            }
        }
        
        String tower = args[3];
        {
            Set<String> ts = new HashSet<>();
            Map<String, DungeonSettings> towers = FakeGenerator.getTowers();
            for(Map.Entry<String, DungeonSettings> tow : towers.entrySet()) {
                ts.add(tow.getKey());
            }
            if(!ts.contains(tower)) {
                sender.sendMessage("Wrong tower name");
                return true;
            }
        }
        
        Location loc = p.getLocation();
        
        ItemStack is = Menu.createNewDungeon_Cmd(p, generator, populator, tower);
        loc.getWorld().dropItem(loc, is);
        
        return true;
    }
}
