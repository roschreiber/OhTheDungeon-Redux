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

import forge_sandbox.com.someguyssoftware.dungeons2.BukkitDungeonGenerator;
import static forge_sandbox.jaredbgreat.dldungeons.builder.Builder.commandPlaceDungeon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import static otd.Main.instance;
import otd.dungeon.aetherlegacy.AetherBukkitGenerator;
import otd.dungeon.draylar.BattleTowerSchematics;
import otd.populator.BattleTowerPopulator;
import otd.populator.SmoofyPopulator;
import otd.util.ActualHeight;
import otd.lib.ExceptionRepoter;
import otd.lib.async.AsyncRoguelikeDungeon;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.TFBukkitGenerator;
import java.util.Map;
import java.util.UUID;
import otd.config.WorldConfig;
import otd.config.WorldConfig.CustomDungeon;
import otd.gui.customstruct.CustomDungeonPlaceSelect;
import otd.struct.SchematicLoader;
import otd.util.I18n;

public class Otd_Place implements TabExecutor {
    
    private final Set<Player> players = new HashSet<>();
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> res = new ArrayList<>();
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission("oh_the_dungeons.admin")) return res;
            if(args.length == 1) {
                res.add("roguelike");
                res.add("doomlike");
                res.add("battletower");
                res.add("smoofy");
                res.add("draylar");
                res.add("antman");
                res.add("aether");
                res.add("lich");
                res.add("custom");
            }
        }
        return res;
    }
    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(sender == null) return false;
        if(!(sender instanceof Player)) {
            sender.sendMessage("Player only command");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("oh_the_dungeons.admin")) {
            sender.sendMessage(I18n.instance.No_Permission);
            return true;
        }
        String type;
        if(args.length == 0) {
            sender.sendMessage("Dungeon type is needed here");
            return true;
        }
        
        if(!players.contains(p)) {
            sender.sendMessage("Make sure you want to do that");
            sender.sendMessage("Type command again in 20s to confirm");
            players.add(p);
            
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                players.remove(p);
            }, 20 * 20);
            
            return true;
        }
        
        type = args[0];
        Location loc = p.getLocation();
        Chunk chunk = loc.getChunk();
        World world = loc.getWorld();
        if(type.equals("doomlike")) {
            try {
                boolean res = commandPlaceDungeon(new Random(), chunk.getX(), chunk.getZ(), world);
                if(!res) {
                    sender.sendMessage("Fail: No theme available for this chunk...");
                } else {
                    sender.sendMessage("Done");
                }
            } catch (Throwable ex) {
                sender.sendMessage("Internal Error when placing a dungeon in this chunk...");
            }
        } else if(type.equals("roguelike")) {
            Random rand = new Random();
            //IWorldEditor editor = new forge_sandbox.greymerk.roguelike.worldgen.WorldEditor(world);
            AsyncWorldEditor editor = new AsyncWorldEditor(world);
            boolean flag = AsyncRoguelikeDungeon.generateAsync(rand, editor, loc.getBlockX(), loc.getBlockZ());
            
            if(!flag) sender.sendMessage("Fail: No theme available for this chunk...");
            sender.sendMessage("Done");
        } else if(type.equals("battletower")) {
            BattleTowerPopulator g = new BattleTowerPopulator();
            g.generateDungeon(world, new Random(), chunk);
            sender.sendMessage("Done");
        } else if(type.equals("smoofy")) {
            SmoofyPopulator.halfAsyncGenerate(world, chunk, new Random());
            sender.sendMessage("Done");
        } else if(type.equals("draylar")) {
            Location location = p.getLocation();
            BattleTowerSchematics.place(world, new Random(), location.getBlockX(), location.getBlockZ());
            sender.sendMessage("Done");
        } else if(type.equals("antman")) {
            Location location = p.getLocation();
            location = location.getWorld().getHighestBlockAt(location).getLocation();
            location = ActualHeight.getHeight(location);
            
            try {
                BukkitDungeonGenerator.generate(world, location, new Random());
                sender.sendMessage("Done");
            } catch(Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, ExceptionRepoter.exceptionToString(ex));
            }
        } else if(type.equals("aether")) {
            Location location = p.getLocation();
            AetherBukkitGenerator.generate(world, new Random(), location.getBlockX(), location.getBlockZ());
            sender.sendMessage("Done");
        } else if(type.equals("lich")) {
            Location location = p.getLocation();
            location = location.getWorld().getHighestBlockAt(location).getLocation();
            location = ActualHeight.getHeight(location);
            TFBukkitGenerator.generateLichTower(world, location, new Random());
        } else if(type.equals("custom")) {
            CustomDungeonPlaceSelect gui = new CustomDungeonPlaceSelect();
            gui.openInventory(p);
        } else if(type.equals("test")) {
            CustomDungeon dungeon = null;
            for(Map.Entry<UUID, CustomDungeon> entry : WorldConfig.wc.custom_dungeon.entrySet()) {
                dungeon = entry.getValue();
                break;
            }
            if(dungeon != null) {
                Location location = world.getHighestBlockAt(p.getLocation()).getLocation();
                SchematicLoader.createInWorldAsync(dungeon, location, new Random());
            }
        } else return false;
        return true;
    }
}
