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

import static forge_sandbox.jaredbgreat.dldungeons.builder.Builder.commandPlaceDungeon;
import static otd.Main.instance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import forge_sandbox.ChunkPos;
import forge_sandbox.com.someguyssoftware.dungeons2.BukkitDungeonGenerator;
import forge_sandbox.team.cqr.cqrepoured.BukkitCastleGenerator;
import forge_sandbox.twilightforest.TFBukkitGenerator;
import otd.dungeon.aetherlegacy.AetherBukkitGenerator;
import otd.dungeon.draylar.BattleTowerSchematics;
import otd.gui.customstruct.CustomDungeonPlaceSelect;
import otd.lib.async.AsyncRoguelikeDungeon;
import otd.lib.async.AsyncWorldEditor;
import otd.populator.BattleTowerPopulator;
import otd.populator.SmoofyPopulator;
import otd.script.JSEngine;
import otd.script.JSLoader;
import otd.script.JSLoader.Script;
import otd.util.ActualHeight;
import otd.util.ExceptionReporter;
import otd.util.I18n;
import otd.world.DungeonType;
import otd.redux.util.ChatManager;
import otd.redux.util.ChatManager.MessageType;

public class Otd_Place implements TabExecutor {

	private final Set<Player> players = new HashSet<>();

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> res = new ArrayList<>();
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("oh_the_dungeons.admin"))
				return res;
			if (args.length == 1) {
				res.add("roguelike");
				res.add("doomlike");
				res.add("battletower");
				res.add("smoofy");
				res.add("draylar");
				res.add("antman");
				res.add("aether");
				res.add("lich");
				res.add("castle");
				res.add("custom");
			}
		}
		return res;
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
		if (!p.hasPermission("oh_the_dungeons.admin")) {
			sender.sendMessage(ChatManager.getInstance().formatMessage("You do not have permission to use this command", MessageType.ERROR));
			return true;
		}
		String type;
		if (args.length == 0) {
			sender.sendMessage(ChatManager.getInstance().formatMessage("Dungeon type is needed here", MessageType.ERROR));
			return true;
		}

		if (!players.contains(p)) {
			sender.sendMessage(ChatManager.getInstance().formatMessage("Retype that command in the next 20 seconds to confirm", MessageType.WARNING));
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
		if (type.equals("doomlike")) {
			try {
				boolean res = commandPlaceDungeon(new Random(), chunk.getX(), chunk.getZ(), world);
				if (!res) {
					sender.sendMessage(ChatManager.getInstance().formatMessage("Fail: No theme available for this chunk", MessageType.ERROR));
				} else {
					sender.sendMessage(ChatManager.getInstance().formatMessage("Done, Dungeon should be placed in this chunk", MessageType.SUCCESS));
				}
			} catch (Throwable ex) {
				sender.sendMessage(ChatManager.getInstance().formatMessage("Internal Error occured when trying to place a dungeon in this chunk", MessageType.ERROR));
				sender.sendMessage(ExceptionReporter.exceptionToString(ex));
			}
			players.remove(p);
		} else if (type.equals("roguelike")) {
			Random rand = new Random();
			// IWorldEditor editor = new
			// forge_sandbox.greymerk.roguelike.worldgen.WorldEditor(world);
			AsyncWorldEditor editor = new AsyncWorldEditor(world);
			editor.setVirtualGround(true);
			int avg_y = 0;
			int x = loc.getBlockX();
			int z = loc.getBlockZ();
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					Location aloc = world.getHighestBlockAt(x + i, z + j).getLocation();
					aloc = ActualHeight.getHeight(aloc);
					avg_y += aloc.getBlockY();
				}
			}
			avg_y /= 9;
			editor.setVirtualGroundHeight(avg_y);
			boolean flag = AsyncRoguelikeDungeon.generateAsync(rand, editor, x, z);

			if (!flag)
				sender.sendMessage(ChatManager.getInstance().formatMessage("Fail: No theme available for this chunk...", MessageType.ERROR));
			sender.sendMessage(ChatManager.getInstance().formatMessage("Done, Dungeon should be placed in this chunk", MessageType.SUCCESS));
			players.remove(p);
		} else if (type.equals("battletower")) {
			BattleTowerPopulator g = new BattleTowerPopulator();
			g.generateDungeon(world, new Random(), chunk);
			sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));
		} else if (type.equals("smoofy")) {
			SmoofyPopulator.halfAsyncGenerate(world, chunk, new Random());
			sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));
		} else if (type.equals("draylar")) {
			Location location = p.getLocation();
			BattleTowerSchematics.place(world, new Random(), location.getBlockX(), location.getBlockZ());
			sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));
		} else if (type.equals("antman")) {
			Location location = p.getLocation();
			location = location.getWorld().getHighestBlockAt(location).getLocation();
			location = ActualHeight.getHeight(location);

			try {
				BukkitDungeonGenerator.generate(world, location, new Random());
				sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));
			} catch (Exception ex) {
				Bukkit.getLogger().log(Level.SEVERE, ExceptionReporter.exceptionToString(ex));
			}
			players.remove(p);
		} else if (type.equals("aether")) {
			Location location = p.getLocation();
			AetherBukkitGenerator.generate(world, new Random(), location.getBlockX(), location.getBlockZ());
			sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));
			players.remove(p);
		} else if (type.equals("lich")) {
			Location location = p.getLocation();
			location = location.getWorld().getHighestBlockAt(location).getLocation();
			location = ActualHeight.getHeight(location);
			TFBukkitGenerator.generateLichTower(world, location, new Random());
			sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));
			players.remove(p);
		} else if (type.equals("custom")) {
			CustomDungeonPlaceSelect gui = new CustomDungeonPlaceSelect();
			gui.openInventory(p);
			sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));
			players.remove(p);
		} else if (type.equals("castle")) {
			Location location = p.getLocation();
			location = location.getWorld().getHighestBlockAt(location).getLocation();
			location = ActualHeight.getHeight(location);
			BukkitCastleGenerator.generate(world, location, new Random());
			sender.sendMessage(ChatManager.getInstance().formatMessage("Done", MessageType.SUCCESS));
			players.remove(p);
		} else if (type.equals("text")) {
			Map<String, Script> scripts = JSLoader.getScripts("on_dungeon_placed");
			for (Map.Entry<String, Script> entry : scripts.entrySet()) {
				try {
					ScriptEngine engine = JSEngine.getEngine();
					Invocable invocable = (Invocable) engine;
					engine.setContext(entry.getValue().context);

					Set<ChunkPos> tmp = new HashSet<>();
					tmp.add(new ChunkPos(-1, -2));
					invocable.invokeFunction("on_dungeon_placed", 3, 4, tmp, DungeonType.CustomDungeon, "ac");
				} catch (NoSuchMethodException | ScriptException ex) {
					Bukkit.getLogger().log(Level.SEVERE, "Found errors while executing js script : " + entry.getKey());
					Bukkit.getLogger().log(Level.SEVERE, ExceptionReporter.exceptionToString(ex));
				}
			}
			return true;
		} else
			return false;
		return true;
	}
}
