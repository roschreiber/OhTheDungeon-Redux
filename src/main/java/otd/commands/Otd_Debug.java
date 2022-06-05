package otd.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import forge_sandbox.ChunkPos;
import otd.script.JSLoader;
import otd.script.JSLoader.Script;
import otd.util.ExceptionReporter;
import otd.world.DungeonType;

public class Otd_Debug implements TabExecutor {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		Map<String, Script> scripts = JSLoader.getScripts("on_dungeon_placed");
		for (Map.Entry<String, Script> entry : scripts.entrySet()) {
			try {
				ScriptEngine engine = JSLoader.engine;
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
	}
}
