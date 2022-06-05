package otd.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.bukkit.Bukkit;

import otd.Main;
import otd.util.ExceptionReporter;
import otd.util.FileUtils;

public class JSLoader {

	public static ScriptEngine engine;

	public static class Script {
		public CompiledScript script;
		public ScriptContext context;

		public Script(CompiledScript script, ScriptContext context) {
			this.script = script;
			this.context = context;
		}
	}

	private static Map<String, Map<String, Script>> scripts;

	public static Script getScript(String item, String name) {
		Map<String, Script> s = scripts.get(item);
		if (s == null)
			return null;
		return s.get(name);
	}

	public static Map<String, Script> getScripts(String item) {
		return scripts.get(item);
	}

	public static void init() {
		scripts = new HashMap<>();
		engine = JSEngine.getEngine();

		File js = new File(Main.instance.getDataFolder(), "js");
		if (!js.exists())
			js.mkdirs();

		File on_dungeon_placed = new File(js, "on_dungeon_placed");
		if (!on_dungeon_placed.exists())
			on_dungeon_placed.mkdir();
		loadInDirectory("on_dungeon_placed", on_dungeon_placed);

		File scripts = new File(js, "spawner_scripts");
		if (!scripts.exists())
			scripts.mkdir();
		loadInDirectory("spawner_scripts", scripts);

		File on_dungeon_mob_killed = new File(js, "on_dungeon_mob_killed");
		if (!on_dungeon_mob_killed.exists())
			on_dungeon_mob_killed.mkdir();
		loadInDirectory("on_dungeon_mob_killed", on_dungeon_mob_killed);
	}

	private static void loadInDirectory(String item, File path) {
		File[] listOfFiles = path.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				if (FileUtils.getExtension(file).equals("js")) {
					load(item, file);
				}
			}
		}
	}

	private static void load(String item, File f) {
		Bukkit.getLogger().info("Loading " + f.getAbsoluteFile().toString());
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}
			reader.close();

			ScriptContext scriptCtxt = new SimpleScriptContext();
			Bindings engineScope = scriptCtxt.getBindings(ScriptContext.ENGINE_SCOPE);
			CompiledScript compiledScript = ((Compilable) engine).compile(sb.toString());
			compiledScript.eval(engineScope);

			if (!scripts.containsKey(item))
				scripts.put(item, new HashMap<>());

			scripts.get(item).put(f.getName(), new Script(compiledScript, scriptCtxt));

		} catch (IOException | ScriptException ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Found errors while loading js script : " + f.getAbsolutePath());
			Bukkit.getLogger().log(Level.SEVERE, ExceptionReporter.exceptionToString(ex));
		}
	}
}
