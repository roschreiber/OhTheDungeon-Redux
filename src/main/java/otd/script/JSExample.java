package otd.script;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import otd.Main;

public class JSExample {
	private static void loadFile(String jarf, File out) {
		try (InputStream in = Main.instance.getResource(jarf);
				OutputStream writer = new BufferedOutputStream(new FileOutputStream(out, false))) {
			byte[] buffer = new byte[1024 * 4];
			int length;
			while ((length = in.read(buffer)) >= 0) {
				writer.write(buffer, 0, length);
			}
		} catch (Exception ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Load " + jarf + " error...");
			return;
		}
	}

	public static void init() {
		{
			File f = new File(Main.instance.getDataFolder(), File.separator + "js" + File.separator + "examples");
			if (!f.exists())
				f.mkdirs();

			File out = new File(f, "how_to_use.txt");
			loadFile("example/how_to_use.txt", out);
		}
		{
			File f = new File(Main.instance.getDataFolder(),
					File.separator + "js" + File.separator + "examples" + File.separator + "on_dungeon_placed");
			if (!f.exists())
				f.mkdirs();

			File out = new File(f, "custom.js");
			loadFile("example/on_dungeon_placed/custom.js", out);
		}
		{
			File f = new File(Main.instance.getDataFolder(),
					File.separator + "js" + File.separator + "examples" + File.separator + "spawner_scripts");
			if (!f.exists())
				f.mkdirs();

			File out = new File(f, "spawn_boss.js");
			loadFile("example/spawner_scripts/spawn_boss.js", out);
		}
		{
			File f = new File(Main.instance.getDataFolder(),
					File.separator + "js" + File.separator + "examples" + File.separator + "on_dungeon_mob_killed");
			if (!f.exists())
				f.mkdirs();

			File out = new File(f, "test1.js");
			loadFile("example/on_dungeon_mob_killed/test1.js", out);
		}
	}
}
