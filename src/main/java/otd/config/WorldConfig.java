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
package otd.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.md_5.bungee.api.ChatColor;
import otd.Main;
import otd.config.EnumType.ChestType;
import otd.config.storage.ConfigImpl;
import otd.config.storage.MySqlConfig;
import otd.config.storage.SQLiteConfig;
import otd.util.ExceptionReporter;
import otd.util.I18n;
//import static otd.config.WorldConfig.wc;
import otd.world.WorldDefine;

/**
 *
 * @author
 */
public class WorldConfig {
	public Map<String, SimpleWorldConfig> dict = new HashMap<>();

	public boolean furniture = true;
	public boolean preciousBlocks = true;
	public boolean rogueSpawners = true;
	public boolean disableAPI = false;
	public boolean noMobChanges = false;
	public int version = 14;

	public int map_house = -1;
	public int map_desert = -1;
	public int map_bunker = -1;
	public int map_jungle = -1;
	public int map_swamp = -1;
	public int map_mountain = -1;
	public int map_forest = -1;
	public int map_mesa = -1;
	public int map_ice = -1;
	public int map_ruin = -1;
	public int map_cactus = -1;

	public int rollCoolDownInSecond = 11;
	public int rollRange = 15;

	public String diceUUID = "afbe4c67-a6a5-4559-ad06-78a6ed2ab4e9";
	public String diceTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTE1ZjdjMzEzYmNhOWMyZjk1OGU2OGFiMTRhYjM5Mzg2N2Q2NzUwM2FmZmZmOGYyMGNiMTNmYmU5MTdmZDMxIn19fQ==";

	public DungeonWorldConfig dungeon_world = new DungeonWorldConfig();

	public static transient ConfigImpl db;

	public static class CustomDungeon {
		public UUID id = UUID.randomUUID();
		public String file = "";

		public List<LootNode> loots = new ArrayList<>();
		public Set<String> biomeExclusions = new HashSet<>();
		public Set<String> mobs = new HashSet<>();

		public CustomDungeonType type = CustomDungeonType.ground;
		public int sky_spawn_height = 180;
		public int[] offset = { 0, 0, 0 };
		public int weight = 10;

		public CustomDungeon() {
			mobs.add(EntityType.ZOMBIE.toString());
		}

		public List<String> getLores() {
			List<String> lores = new ArrayList<>();
			lores.add(ChatColor.BLUE + file);
			lores.add(type.toString());
			lores.add(I18n.instance.Weight + " : " + Integer.toString(weight));
			return lores;
		}

		@SuppressWarnings("deprecation")
		public ItemStack getIcon() {
			ItemStack is = new ItemStack(Material.STRUCTURE_BLOCK);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(id.toString());
			im.setLore(getLores());
			is.setItemMeta(im);

			return is;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof CustomDungeon))
				return false;
			CustomDungeon d = (CustomDungeon) obj;
			return d.id.equals(this.id);
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 67 * hash + Objects.hashCode(this.id);
			return hash;
		}
	}

	public HashMap<UUID, CustomDungeon> custom_dungeon = new HashMap<>();

	public static WorldConfig wc;
	static {
		wc = new WorldConfig();
		addDungeonPlotConfig();

	}

	public WorldConfig() {
	}

	private void initDungeonWorld() {
		addDungeonPlotConfig();
	}

	private static boolean addDungeonPlotConfig() {
		if (!wc.dict.containsKey(WorldDefine.WORLD_NAME)) {
			SimpleWorldConfig swc = new SimpleWorldConfig();
			swc.aether_dungeon.doNaturalSpawn = true;
			swc.ant_man_dungeon.doNaturalSpawn = true;
			swc.battletower.doNaturalSpawn = true;
			swc.doomlike.doNaturalSpawn = true;
			swc.draylar_battletower.doNaturalSpawn = true;
			swc.lich_tower.doNaturalSpawn = true;
			swc.roguelike.doNaturalSpawn = true;
			swc.smoofydungeon.doNaturalSpawn = true;
			swc.castle.doNaturalSpawn = true;

			wc.dict.put(WorldDefine.WORLD_NAME, swc);
			return true;
		}
		return false;
	}

	public static void handleRoguelikePatch() {

	}

	private final static class AsyncSaverLock {
		public boolean save = false;

		public AsyncSaverLock(boolean save) {
			this.save = save;
		}
	}

	public static class AsyncSaver {
		private static AsyncSaverLock lock = new AsyncSaverLock(false);

		public static void save() {
			synchronized (lock) {
				lock.save = true;
			}
		}

		public static BukkitTask task = null;

		public static void cancelAsyncSaver() {
			if (task != null) {
				Bukkit.getScheduler().cancelTask(task.getTaskId());
				actualSave();
			}
		}

		public static void setupAsyncSaver() {
			task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, () -> {
				synchronized (lock) {
					if (!lock.save) {
						return;
					}
					lock.save = false;
				}
				actualSave();
				Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
					Bukkit.getLogger().info("[OTD] New config saved...");
				}, 1L);
			}, 20 * 1, 20 * 1);
		}
	}

	public static void save() {
		AsyncSaver.save();
	}

	public static void actualSave() {
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(wc);
		JavaPlugin plugin = Main.instance;
		// make sure file exists
		File configDir = plugin.getDataFolder();
		if (!configDir.exists()) {
			configDir.mkdir();
		}

		WorldConfig.db.setValue("config", json);
	}

	public static void saveold() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(wc);
		JavaPlugin plugin = Main.instance;
		// make sure file exists
		File configDir = plugin.getDataFolder();
		if (!configDir.exists()) {
			configDir.mkdir();
		}

		String world_config_file = configDir.toString() + File.separator + "world_config.json";
		File file = new File(world_config_file);
		try (OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
			oStreamWriter.append(json);
			oStreamWriter.close();
		} catch (IOException ex) {
			Bukkit.getLogger().log(Level.SEVERE, I18n.instance.Load_Config_Err);
		}
	}

	public static void loadWorldConfig() {
		JavaPlugin plugin = Main.instance;
		File configDir = plugin.getDataFolder();
		if (!configDir.exists()) {
			configDir.mkdir();
		}

		String old = configDir.toString() + File.separator + "old";
		File oldFolder = new File(old);
		if (!oldFolder.exists())
			oldFolder.mkdir();

		String world_config_file = configDir.toString() + File.separator + "world_config.json";
		File cfile = new File(world_config_file);

		String dat = configDir.toString() + File.separator + "config.db";
		File datfile = new File(dat);

		boolean update = false;
		boolean datfile_exist = datfile.exists();

		if (YamlPluginConfig.type == YamlPluginConfig.DATA_TYPE.SQLITE) {
			WorldConfig.db = new SQLiteConfig(Main.instance);
		} else {
			WorldConfig.db = new MySqlConfig(Main.instance);
		}

		WorldConfig.db.load();

		if (cfile.exists()) {
			if (YamlPluginConfig.type == YamlPluginConfig.DATA_TYPE.SQLITE && !datfile_exist) {
				update = true;
				loadWorldConfigUpdate();
			}
			String newf = configDir.toString() + File.separator + "old" + File.separator + UUID.randomUUID().toString();
			try {
				Files.move(cfile, new File(newf));
			} catch (IOException ex) {
				Bukkit.getLogger().log(Level.SEVERE, ExceptionReporter.exceptionToString(ex));
			}
		}

		if (update) {
			actualSave();
		}

		String value = WorldConfig.db.getValue("config");
		if (value == null) {
			wc.dict.put("example_otd", new SimpleWorldConfig());
			actualSave();
		} else {
			wc = (new Gson()).fromJson(value, WorldConfig.class);
		}
	}

	public static void loadWorldConfigUpdate() {
		JavaPlugin plugin = Main.instance;
		// make sure file exists
		File configDir = plugin.getDataFolder();
		if (!configDir.exists()) {
			configDir.mkdir();
		}

		String world_config_file = configDir.toString() + File.separator + "world_config.json";
		File cfile = new File(world_config_file);
		if (!cfile.exists()) {
			try {
				wc.dict.put("example_otd", new SimpleWorldConfig());
				cfile.createNewFile();
				saveold();
			} catch (IOException ex) {
				Bukkit.getLogger().log(Level.SEVERE, I18n.instance.Load_Config_Err);
			}
		}
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(world_config_file), "UTF8"))) {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}
			wc = (new Gson()).fromJson(sb.toString(), WorldConfig.class);
		} catch (IOException ex) {
			wc = new WorldConfig();
		}
		update();
	}

	public static void update() {
		boolean saves = false;
		if (wc.version == 0) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().battletower.chest = ChestType.BOX;
			}
			wc.version = 1;
			saves = true;
		}
		if (wc.version == 1) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().smoofy_weight = 3;
				entry.getValue().initSmoofyDungeon();
			}
			wc.version = 2;
			saves = true;
		}
		if (wc.version == 2) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().egg_change_spawner = true;
			}
			wc.rollCoolDownInSecond = 10;
			wc.rollRange = 15;
			wc.diceUUID = "afbe4c67-a6a5-4559-ad06-78a6ed2ab4e9";
			wc.diceTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTE1ZjdjMzEzYmNhOWMyZjk1OGU2OGFiMTRhYjM5Mzg2N2Q2NzUwM2FmZmZmOGYyMGNiMTNmYmU5MTdmZDMxIn19fQ==";
			wc.version = 3;
			saves = true;
		}
		if (wc.version == 3) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().roguelike.builtinLoot = true;
			}
			wc.version = 4;
			saves = true;
		}
		if (wc.version == 4) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().draylar_weight = 3;
				entry.getValue().initDraylarBattleTower();
			}
			wc.version = 5;
			saves = true;
		}
		if (wc.version == 5) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().silk_vanilla_spawner = false;
				entry.getValue().silk_dungeon_spawner = false;
				entry.getValue().mob_drop_in_vanilla_spawner = true;
				entry.getValue().mob_drop_in_dungeon_spawner = false;
				entry.getValue().disappearance_rate_vanilla = 0;
				entry.getValue().disappearance_rate_dungeon = 0;
			}
			wc.version = 6;
			saves = true;
		}
		if (wc.version == 6) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().antman_weight = 3;
				entry.getValue().initAntManDungeon();
			}
			wc.version = 7;
			saves = true;
		}
		if (wc.version == 7) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().aether_weight = 3;
				entry.getValue().prevent_spawner_dropping = false;
				entry.getValue().prevent_spawner_breaking = false;
				entry.getValue().initAetherDungeon();
			}
			wc.version = 8;
			saves = true;
		}
		if (wc.version == 8) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().lich_weight = 3;
				entry.getValue().initLichTower();
			}
			wc.version = 9;
			saves = true;
		}
		if (wc.version == 9) {
			wc.initDungeonWorld();
			wc.version = 10;
			saves = true;
		}
		if (wc.version == 10) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().roguelike.initThemes();
				entry.getValue().spawner_rejection_rate = 0;
			}
			wc.map_house = -1;
			wc.map_desert = -1;
			wc.map_bunker = -1;
			wc.map_jungle = -1;
			wc.map_swamp = -1;
			wc.map_mountain = -1;
			wc.map_forest = -1;
			wc.map_mesa = -1;
			wc.map_ice = -1;
			wc.map_ruin = -1;
			wc.map_cactus = -1;

			wc.version = 11;
			saves = true;
		}
		if (wc.version == 11) {
			wc.custom_dungeon = new HashMap<>();
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().initCustomDungeon();
				entry.getValue().custom_dungeon_weight = 3;

				wc.version = 12;
				saves = true;
			}
		}
		if (wc.version == 12) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().castle_weight = 3;
				entry.getValue().initCastle();
				entry.getValue().boss = SimpleWorldConfig.BossType.Vanilla;
				entry.getValue().chance = 5;
			}
			wc.version = 13;
			saves = true;
		}
		if (wc.version == 12) {
			for (Map.Entry<String, SimpleWorldConfig> entry : wc.dict.entrySet()) {
				entry.getValue().initWorldParameter();
			}
			wc.version = 13;
			saves = true;
		}
		if (addDungeonPlotConfig()) {
			saves = true;
		}
		if (wc.dungeon_world.dungeon_count > 30) {
			wc.dungeon_world.dungeon_count = 30;
			saves = true;
		}
		if (wc.dungeon_world.dungeon_count < 1) {
			wc.dungeon_world.dungeon_count = 1;
			saves = true;
		}
		if (saves)
			actualSave();
	}

	public static void close() {
		WorldConfig.db.close();
	}
}
