/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import otd.Main;

/**
 *
 * @author shadow_wind
 */
public class I18n {
	private static I18n instance = new I18n();
	public static final transient String configFileName = "lang.json";

	private I18n() {
		dict.put("Private_Dungeon_Menu", "Private Dungeon Menu");
		dict.put("Nordic", "Terrain: Nordic");
		dict.put("Empty", "Terrain: Empty");
		dict.put("AlmostFlatlands", "Terrain: Almost Flat lands");
		dict.put("Vanilla", "Terrain: Vanilla");
		dict.put("Fjord", "Terrain: Fjord");
		dict.put("Hoth", "Terrain: Hoth");
		dict.put("Tatooine", "Terrain: Tatooine");
		dict.put("Slient", "Terrain: Slient");
		dict.put("NorthLand", "Terrain: NorthLand");
		dict.put("Rocky", "Terrain: Rocky");
		dict.put("City", "Terrain: City");
		dict.put("Island", "Terrain: Island");
		dict.put("Valley", "Terrain: Valley");
		dict.put("Tropic", "Terrain: Tropic");
		dict.put("Plot", "Terrain: Plot");
		dict.put("Candy", "Terrain: Candy");
		dict.put("Cake", "Terrain: Cake");
//        dict.put("Town", "Terrain: Town");

		dict.put("Void", "Decoration: None");
		dict.put("Oak", "Decoration: Oak Tree");
		dict.put("PromisedTree", "Decoration: Promised Tree");
		dict.put("GiantFlower", "Decoration: Giant Flower");
		dict.put("Sakura", "Decoration: Sakura");

		dict.put("Desert", "Tower: Desert");
		dict.put("Forest", "Tower: Forest");
		dict.put("Grassland", "Tower: Grassland");
		dict.put("Hole", "Tower: Hole");
		dict.put("Ice", "Tower: Ice");
		dict.put("Jungle", "Tower: Jungle");
		dict.put("Mountain", "Tower: Mountain");
		dict.put("Mushroom", "Tower: Mushroom");
		dict.put("Rare", "Tower: Rare");
		dict.put("Ruin", "Tower: Ruin");
		dict.put("Swamp", "Tower: Swamp");
		dict.put("Tree", "Tower: Tree");
		dict.put("Mesa", "Tower: Mesa");
		dict.put("BirthDay", "Tower: BirthDay");

		dict.put("MONEY", "Pay with money");
		dict.put("LEVEL", "Pay with level");
		dict.put("DISABLE_ADMIN", "Disabled by admin");
		dict.put("PAY_MONEY", "Pay with money ");
		dict.put("PAY_LEVEL", "Pay with level ");

		dict.put("CLICK", "Click to create your own world & dungeon");
		dict.put("NotEnoughMoney", "You don't have enough money to do that");
		dict.put("NotEnoughLevel", "You don't have enough level to do that");

		dict.put("Tip1", "Put this book on a Lectern and right click to check status or enter a dungeon");
		dict.put("Tip2",
				"If you found out that this dungeon cannot work properly, please give this to your server admin to get back you EXP or MONEY");
		dict.put("Tip3",
				"If you are admin, please submit the plugins/PerPlayerDungeonInstance/logs/log_dx_dz.txt to author's discord. Thank you");

		dict.put("DungeonCreated", "One of your dungeon has been created. Check your book~");
		dict.put("PendingDungeon", "Dungeon creation has not been finished yet");
		dict.put("InvalidDungeon", "This dungeon book is no longer valid");
		dict.put("NotOwner", "You are not the owner of this dungeon book");
		dict.put("BreakDungeon",
				"Looks like your dungeon creation is interrupted by a server restarting. It is committed now. Check your dungeon book to get the status");

		dict.put("NoPermission", "You do not have permission to do that");

		dict.put("Creating", "Your dungeon task is committed. Check your dungeon book to get the status");

		dict.put("DungeonBook", "Dungeon Book");

		dict.put("DungeonTitle", "Roguelike Dungeon");
		dict.put("DungeonTitle2", "Find the underground dungeon");

		dict.put("Terrain", "Terrain type");
		dict.put("TerrainLore", "Choose one for terrain generation");
		dict.put("Decoration", "Decoration type");
		dict.put("DecorationLore", "Choose one for environment decoration");
		dict.put("Tower", "Tower type");
		dict.put("TowerLore", "Choose one for dungeon tower");

		dict.put("NoEmpty", "You need at least empty inventory slot");

		dict.put("Back", "Use /otd_pi_back to go back to normal world");
	}

	public static String get(String key) {
		String res = instance.dict.get(key);
		if (res == null)
			return "MissingLang";
		return res;
	}

	private static void check_update1() {
		if (!instance.dict.containsKey("Plot")) {
			instance.dict.put("Plot", "Terrain: Plot");
		}
		if (!instance.dict.containsKey("Candy")) {
			instance.dict.put("Candy", "Terrain: Candy Land");
		}
		if (!instance.dict.containsKey("Candy")) {
			instance.dict.put("Candy", "Terrain: Candy Land");
		}
		if (!instance.dict.containsKey("Mesa")) {
			instance.dict.put("Mesa", "Tower: Mesa");
		}
		if (!instance.dict.containsKey("BirthDay")) {
			instance.dict.put("BirthDay", "Tower: BirthDay");
		}
		if (!instance.dict.containsKey("Cake")) {
			instance.dict.put("Cake", "Terrain: Cake");
		}
	}

	private Map<String, String> dict = new HashMap<>();

	public static void init() {
		String configDirName = Main.instance.getDataFolder().getParentFile().toString() + File.separator
				+ "PerPlayerDungeonInstance";
		File directory = new File(configDirName);
		if (!directory.exists()) {
			directory.mkdir();
		}
		String file_path = configDirName + File.separator + configFileName;
		File file = new File(file_path);
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(file_path), "UTF8"))) {
				StringBuilder sb = new StringBuilder();
				String line = reader.readLine();
				while (line != null) {
					sb.append(line);
					line = reader.readLine();
				}
				I18n lang = (new Gson()).fromJson(sb.toString(), I18n.class);
				instance = lang;
			} catch (Exception ex) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				Bukkit.getLogger().log(Level.SEVERE, sw.toString());
			}
		}
		check_update1();
		save();
	}

	public static void save() {
		String configDirName = Main.instance.getDataFolder().getParentFile().toString() + File.separator
				+ "PerPlayerDungeonInstance";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(instance);
		String file_path = configDirName + File.separator + configFileName;
		File file = new File(file_path);
		try (OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
			oStreamWriter.append(json);
			oStreamWriter.close();
		} catch (IOException ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Error while saving lang file.");
		}
	}
}
