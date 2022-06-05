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
package forge_sandbox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forge_sandbox.greymerk.roguelike.treasure.loot.Loot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import otd.Main;
import otd.util.I18n;

/**
 *
 * @author
 */
public class Sandbox {
	public final static List<String> mods;
	static {
		mods = new ArrayList<>();
		mods.add("roguelike");
		mods.add("doomlike");
		mods.add("dungeons2");
		mods.add("twilightforest");
	}

	public static void mkdir() {
		File config = Main.instance.getDataFolder();
		if (!config.exists())
			config.mkdir();

		File sandbox = new File(config.toString() + File.separator + "forge_sandbox");
		if (!sandbox.exists())
			sandbox.mkdir();

		int len = mods.size();
		for (int i = 0; i < len; i++) {
			File sub = new File(config.toString() + File.separator + "forge_sandbox" + File.separator + mods.get(i));
			if (!sub.exists())
				sub.mkdir();
		}

		initRoguelikeLootSet();
	}

	public static class RoguelikeLootSet {
		public Set<Loot> loots = new HashSet<>();
		public boolean extra_ItemSpecialty = true;
		public boolean extra_ItemEnchBook = true;
	}

	public static RoguelikeLootSet set;

	public static void initRoguelikeLootSet() {
		File config = Main.instance.getDataFolder();
		File file = new File(config.toString() + File.separator + "forge_sandbox" + File.separator + "roguelike"
				+ File.separator + "loot.json");
		if (!file.exists()) {
			try {
				file.createNewFile();
				set = new RoguelikeLootSet();
				for (Loot loot : Loot.values())
					set.loots.add(loot);
				set.extra_ItemEnchBook = true;
				set.extra_ItemSpecialty = true;
				saveRoguelikeLootSet();
			} catch (IOException ex) {
				Bukkit.getLogger().log(Level.SEVERE, I18n.instance.Load_Config_Err);
			}
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}
			set = (new Gson()).fromJson(sb.toString(), RoguelikeLootSet.class);
		} catch (IOException ex) {
			set = new RoguelikeLootSet();
			for (Loot loot : Loot.values())
				set.loots.add(loot);
			set.extra_ItemEnchBook = true;
			set.extra_ItemSpecialty = true;
		}

		Bukkit.getLogger().log(Level.INFO, (new Gson()).toJson(set));
	}

	private static void saveRoguelikeLootSet() {
		File config = Main.instance.getDataFolder();
		File file = new File(config.toString() + File.separator + "forge_sandbox" + File.separator + "roguelike"
				+ File.separator + "loot.json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(set);
		try (OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
			oStreamWriter.append(json);
			oStreamWriter.close();
		} catch (IOException ex) {
			Bukkit.getLogger().log(Level.SEVERE, I18n.instance.Load_Config_Err);
		}
	}
}
