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
package otd;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Random;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import forge_sandbox.Sandbox;
import forge_sandbox.com.someguyssoftware.dungeons2.config.ModConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.spawner.SpawnSheetLoader;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheetLoader;
import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.jaredbgreat.dldungeons.themes.ThemeReader;
import forge_sandbox.jaredbgreat.dldungeons.themes.ThemeType;
import forge_sandbox.team.cqr.cqrepoured.boss.CastleKing;
import forge_sandbox.twilightforest.structures.lichtower.boss.Lich;
import io.papermc.lib.PaperLib;
import net.md_5.bungee.api.ChatColor;
import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.commands.Otd;
import otd.commands.Otd_Cp;
import otd.commands.Otd_Debug;
import otd.commands.Otd_Place;
import otd.commands.Otd_Reload;
import otd.commands.Otd_Reload_Scripts;
import otd.commands.Otd_Tp;
import otd.config.WorldConfig;
import otd.config.YamlPluginConfig;
import otd.dungeon.draylar.BattleTowerSchematics;
import otd.gui.AetherDungeonConfig;
import otd.gui.AntManDungeonConfig;
import otd.gui.BackupGUI;
import otd.gui.BattleTowerConfig;
import otd.gui.BiomeSetting;
import otd.gui.BossConfig;
import otd.gui.CastleDungeonConfig;
import otd.gui.DoomlikeConfig;
import otd.gui.DraylarBattleTowerConfig;
import otd.gui.DungeonSpawnSetting;
import otd.gui.LichTowerConfig;
import otd.gui.LootItem;
import otd.gui.LootManager;
import otd.gui.MainMenu;
import otd.gui.RogueLikeDungeonTower;
import otd.gui.RoguelikeConfig;
import otd.gui.RoguelikeLootItem;
import otd.gui.RoguelikeLootManager;
import otd.gui.SmoofyConfig;
import otd.gui.UtilMenu;
import otd.gui.WorldEditor;
import otd.gui.WorldManager;
import otd.gui.WorldParameter;
import otd.gui.WorldSpawnerManager;
import otd.gui.customstruct.CustomDungeonEditor;
import otd.gui.customstruct.CustomDungeonList;
import otd.gui.customstruct.CustomDungeonPlaceSelect;
import otd.gui.customstruct.MobSelect;
import otd.gui.customstruct.SchematicSelect;
import otd.gui.customstruct.WorldCustomDungeon;
import otd.gui.dungeon_plot.CreateDungeonWorld;
import otd.gui.dungeon_plot.RemoveDungeonWorld;
import otd.gui.dungeon_plot.UserTeleport;
import otd.gui.storydungeon.PPDI_Config;
//import otd.integration.BossImpl;
import otd.integration.EcoBossesImpl;
import otd.integration.MythicMobsImpl;
import otd.integration.PlaceholderAPI;
import otd.integration.WorldEdit;
import otd.lib.async.AsyncRoguelikeDungeon;
import otd.listener.MobListener;
import otd.listener.SpawnerListener;
import otd.populator.DungeonPopulator;
import otd.script.JSExample;
import otd.script.JSLoader;
import otd.script.listener.DungeonEventListener;
import otd.struct.SchematicLoader;
import otd.update.UpdateChecker;
import otd.util.Diagnostic;
import otd.util.ExceptionReporter;
import otd.util.I18n;
import otd.util.LanguageUtil;
import otd.world.ChunkList;
import otd.world.DungeonWorld;
import otd.world.WorldGenOptimization;

/**
 *
 * @author
 */
public class Main extends JavaPlugin {
	public static JavaPlugin instance;
	private static Main main;
	public static Main mainInstance;
	public static boolean disabled = false;
	@SuppressWarnings("unused")
	private static Integer api_version = 7;
	public static MultiVersion.Version version = MultiVersion.Version.UNKNOWN;
	@SuppressWarnings("unused")
	private Metrics metrics;
	private final static int metric_pluginId = 9213;
	@SuppressWarnings("unused")
	private static PerPlayerDungeonInstance ppdi;
	public final static boolean DEBUG = true;

	public static Main Instance() {
		return main;
	}

	public Main() {
		instance = this;
		main = this;
		mainInstance = this;
		if (MultiVersion.is114()) {
			version = MultiVersion.Version.V1_14_R1;
			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] MC Version: 1.14.x", ChatColor.GREEN);

		} else if (MultiVersion.is115()) {
			version = MultiVersion.Version.V1_15_R1;
			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] MC Version: 1.15.x", ChatColor.GREEN);

		} else if (MultiVersion.is116R3()) {
			version = MultiVersion.Version.V1_16_R3;
			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] MC Version: 1.16.[4-5]", ChatColor.GREEN);

		} else if (MultiVersion.is117R1()) {
			version = MultiVersion.Version.V1_17_R1;
			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] MC Version: 1.17", ChatColor.GREEN);

		} else if (MultiVersion.is118R2()) {
			version = MultiVersion.Version.V1_18_R2;
			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] MC Version: 1.18.2", ChatColor.GREEN);

		} else if (MultiVersion.is119R1()) {
			version = MultiVersion.Version.V1_19_R1;
			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] MC Version: 1.19", ChatColor.GREEN);
		} else {
			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] Unknown Version...", ChatColor.RED);
			version = MultiVersion.Version.UNKNOWN;
		}
		MultiVersion.has3DBiome();
		if (version == MultiVersion.Version.UNKNOWN) {
			// MultiVersion.checkForUnknownVersion();
			throw new UnsupportedOperationException("Unknown server version...");
		}
		MultiVersion.init();

		Sandbox.mkdir();
		BackupGUI.initBackupFolder();
	}

	@Override
	public void onDisable() {
		WorldConfig.AsyncSaver.cancelAsyncSaver();
		WorldConfig.close();
		Bukkit.getLogger().log(Level.WARNING, "[Oh The Dungeons You'll Go] Plugin is disabled");

		disabled = true;
	}

	@Override
	public void onEnable() {
		try {
			Class.forName("org.spigotmc.SpigotConfig");
		} catch (ClassNotFoundException ex) {
			getLogger()
					.severe("[Oh The Dungeons You'll Go] requires Spigot (or a fork such as Paper) in order to run.");
			throw new UnsupportedOperationException("Unsupported Server Type");
		}

		try {
			YamlPluginConfig.init();
		} catch (IOException ex) {
			Bukkit.getLogger().log(Level.SEVERE, ExceptionReporter.exceptionToString(ex));
		}

		// PaperLib.suggestPaper(this);
		disabled = false;

		I18n.init();
		{
			(new File(this.getDataFolder(), "schematics")).mkdirs();
			if (WorldEdit.isReady())
				SchematicLoader.initDir(this);
		}

		WorldConfig.loadWorldConfig();
		WorldConfig.AsyncSaver.setupAsyncSaver();

		ThemeReader.setConfigDir();
		ThemeReader.setThemesDir();
		ThemeReader.readSpecialChest();
		ThemeReader.readThemes();
		ThemeType.SyncMobLists();

		StyleSheetLoader.exposeStyleSheet(ModConfig.styleSheetFile);
		SpawnSheetLoader.exposeSpawnSheet(ModConfig.spawnSheetFile);

		Dungeon.init = true;

		getServer().getPluginManager().registerEvents(new DLDWorldListener(), this);

		getServer().getPluginManager().registerEvents(WorldEditor.instance, this);
		getServer().getPluginManager().registerEvents(WorldManager.instance, this);
		getServer().getPluginManager().registerEvents(RoguelikeConfig.instance, this);
		getServer().getPluginManager().registerEvents(LootManager.instance, this);
		getServer().getPluginManager().registerEvents(LootItem.instance, this);
		getServer().getPluginManager().registerEvents(RoguelikeLootManager.instance, this);
		getServer().getPluginManager().registerEvents(RoguelikeLootItem.instance, this);
		getServer().getPluginManager().registerEvents(BiomeSetting.instance, this);
		getServer().getPluginManager().registerEvents(DoomlikeConfig.instance, this);
		getServer().getPluginManager().registerEvents(BattleTowerConfig.instance, this);
		getServer().getPluginManager().registerEvents(DungeonSpawnSetting.instance, this);
		getServer().getPluginManager().registerEvents(SmoofyConfig.instance, this);
		getServer().getPluginManager().registerEvents(DraylarBattleTowerConfig.instance, this);
		getServer().getPluginManager().registerEvents(WorldSpawnerManager.instance, this);
		getServer().getPluginManager().registerEvents(AntManDungeonConfig.instance, this);
		getServer().getPluginManager().registerEvents(AetherDungeonConfig.instance, this);
		getServer().getPluginManager().registerEvents(LichTowerConfig.instance, this);
		getServer().getPluginManager().registerEvents(MainMenu.instance, this);
		getServer().getPluginManager().registerEvents(RemoveDungeonWorld.instance, this);
		getServer().getPluginManager().registerEvents(CreateDungeonWorld.instance, this);
		getServer().getPluginManager().registerEvents(UserTeleport.instance, this);
		getServer().getPluginManager().registerEvents(BackupGUI.instance, this);
		getServer().getPluginManager().registerEvents(RogueLikeDungeonTower.instance, this);
		getServer().getPluginManager().registerEvents(CustomDungeonEditor.instance, this);
		getServer().getPluginManager().registerEvents(CustomDungeonList.instance, this);
//        getServer().getPluginManager().registerEvents(CustomDungeonSelect.instance, this);
		getServer().getPluginManager().registerEvents(MobSelect.instance, this);
		getServer().getPluginManager().registerEvents(SchematicSelect.instance, this);
		getServer().getPluginManager().registerEvents(WorldCustomDungeon.instance, this);
		getServer().getPluginManager().registerEvents(CustomDungeonPlaceSelect.instance, this);
		getServer().getPluginManager().registerEvents(PPDI_Config.instance, this);
		getServer().getPluginManager().registerEvents(CastleDungeonConfig.instance, this);
		getServer().getPluginManager().registerEvents(BossConfig.instance, this);
		getServer().getPluginManager().registerEvents(WorldParameter.instance, this);
		getServer().getPluginManager().registerEvents(UtilMenu.instance, this);

		getServer().getPluginManager().registerEvents(new MobListener(), this);
		getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
		getServer().getPluginManager().registerEvents(new Lich(), this);
		getServer().getPluginManager().registerEvents(new CastleKing(), this);
		getServer().getPluginManager().registerEvents(new WorldGenOptimization(), this);

		// getServer().getPluginManager().registerEvents(new ChestEventTest(), this);

//		PluginConfig.instance.init();
//		PluginConfig.instance.update();
//
		ChunkList.rebuildChunkMap();

		if (YamlPluginConfig.update) {
			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You''ll Go] Update checking...", ChatColor.GREEN);
			asyncUpdateChecker();
		}
//
//		String update = PluginConfig.instance.config.get("updater");
//		if (update != null && update.equalsIgnoreCase("TRUE")) {
//			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You''ll Go] Update checking...", ChatColor.GREEN);
//			asyncUpdateChecker();
//		}

		// String bstats = PluginConfig.instance.config.get("bstats");
		// if(bstats != null && bstats.equalsIgnoreCase("TRUE")) {
		metrics = new Metrics(this, metric_pluginId);
		// }

//        String fps_opt = PluginConfig.instance.config.get("fps_opt");
//        if(fps_opt != null && fps_opt.equalsIgnoreCase("TRUE")) {
//            if(version == MultiVersion.Version.V1_16_R1 || version == MultiVersion.Version.V1_16_R2 || version == MultiVersion.Version.V1_16_R3) {
//                
//            }
//        }

		registerCommand();
		BattleTowerSchematics.init(this);

		LanguageUtil.init();
		Lich.init();
		CastleKing.init();

		PlaceholderAPI.enable();
		MythicMobsImpl.enable();
		//BossImpl.enable();

		AsyncRoguelikeDungeon.init();

		Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
			Diagnostic.diagnostic();
			{
				try {
					InputStream stream = this.getResource("logo.txt");
					InputStreamReader isr = new InputStreamReader(stream);
					BufferedReader reader = new BufferedReader(isr);
					String line;

					while ((line = reader.readLine()) != null) {
						Bukkit.getLogger().log(Level.INFO, "{0}{1}", new Object[] { ChatColor.BLUE, line });
					}
				} catch (IOException ex) {

				}
			}
		}, 2L);

		Bukkit.getScheduler().runTaskLater(this, () -> {
			// PaperLib.suggestPaper(Main.instance);
			if (!PaperLib.isPaper()) {
				Bukkit.getLogger().log(Level.INFO,
						"{0}[Oh The Dungeons You'll Go] You are not using Paper, async chunk generator is disabled. Dungeon generation may cause tps loss",
						ChatColor.RED);
			}
			if (!WorldEdit.isReady()) {
				Bukkit.getLogger().log(Level.INFO,
						"{0}[Oh The Dungeons You'll Go] WorldEdit not installed, custom dungeon function is disabled. Don't worry, you could still use the built-in dungeons",
						ChatColor.RED);
			}
			if (!PlaceholderAPI.isReady()) {
				Bukkit.getLogger().log(Level.INFO,
						"{0}[Oh The Dungeons You'll Go] PlaceHolderAPI not installed, all placeholders are disabled",
						ChatColor.RED);
			}
			if (!MythicMobsImpl.isMythicMobsReady()) {
				Bukkit.getLogger().log(Level.INFO,
						"{0}[Oh The Dungeons You'll Go] MythicMobs not installed, will disable MythicMobs related features",
						ChatColor.RED);
			} else {
				MythicMobsImpl.report();
			}
			/*if (!BossImpl.isBossReady()) {
				Bukkit.getLogger().log(Level.INFO,
						"{0}[Oh The Dungeons You'll Go] Boss not installed, will disable BossPlugin related features",
						ChatColor.RED);
			}*/
			if (!EcoBossesImpl.isEcoBossesReady()) {
				Bukkit.getLogger().log(Level.INFO,
						"{0}[Oh The Dungeons You'll Go] EcoBosses not installed, will disable BossPlugin EcoBosses features",
						ChatColor.RED);
			}

			JSLoader.init();
			JSExample.init();
			getServer().getPluginManager().registerEvents(new DungeonEventListener(), this);

		}, 3L);

		Bukkit.getScheduler().runTaskLater(this, () -> {
			if (WorldConfig.wc.dungeon_world.finished) {
				Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] Loading dungeon plot world...",
						ChatColor.GREEN);
				DungeonWorld.loadDungeonWorld();
			}
		}, 1L);

		Bukkit.getScheduler().runTaskLater(this, () -> {
			Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You'll Go] Loading PerPlayerDungeonInstance...",
					ChatColor.GREEN);
			ppdi = new PerPlayerDungeonInstance();
		}, 1L);

	}

	@SuppressWarnings("unused")
	private void loadPDF() {
		File out = new File(Main.instance.getDataFolder(), "OTD.pdf");
		try (InputStream in = Main.instance.getResource("OTD.pdf");
				OutputStream writer = new BufferedOutputStream(new FileOutputStream(out, false))) {
			// Step 3
			byte[] buffer = new byte[1024 * 4];
			int length;
			while ((length = in.read(buffer)) >= 0) {
				writer.write(buffer, 0, length);
			}
		} catch (Exception ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Load OTD.pdf error...");
			return;
		}
		try {
			getServer().getPluginManager().loadPlugin(out);
		} catch (InvalidDescriptionException | InvalidPluginException | UnknownDependencyException ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Load OTD.pdf error...");
		}
	}

	@SuppressWarnings("unused")
	private void loadAdvancement() {
		File out = new File(Main.instance.getDataFolder(), "OhTheDungeonAdvancement.jar");
		try (InputStream in = Main.instance.getResource("OhTheDungeonAdvancement.jar");
				OutputStream writer = new BufferedOutputStream(new FileOutputStream(out, false))) {
			// Step 3
			byte[] buffer = new byte[1024 * 4];
			int length;
			while ((length = in.read(buffer)) >= 0) {
				writer.write(buffer, 0, length);
			}
		} catch (Exception ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Load Advancements error...");
			return;
		}
		try {
			getServer().getPluginManager().loadPlugin(out);
		} catch (InvalidDescriptionException | InvalidPluginException | UnknownDependencyException ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Load Advancements error...");
		}
	}

	private BukkitRunnable update_check_task_id;
	private final int RESOURCE_ID = 76437;

	private void asyncUpdateChecker() {
		update_check_task_id = new BukkitRunnable() {
			@Override
			public void run() {
				UpdateChecker.CheckUpdate(instance, RESOURCE_ID);
			}
		};
		update_check_task_id.runTaskTimerAsynchronously(this, 200, 20 * 3600 * 1);
	}

	private class DLDWorldListener implements Listener {
		DungeonPopulator d = new DungeonPopulator();
		Random rand = new Random();

		@EventHandler(priority = EventPriority.LOW)
		public void onChunk(ChunkPopulateEvent e) {
			d.populate(e.getWorld(), rand, e.getChunk());
		}
	}

	private void registerCommand() {
		Otd otd = new Otd();
		Otd_Place otd_place = new Otd_Place();
		Otd_Cp otd_cp = new Otd_Cp();
		Otd_Tp otd_tp = new Otd_Tp();
		Otd_Reload otr = new Otd_Reload();

		PluginCommand command;
		command = this.getCommand("oh_the_dungeons");
		if (command != null) {
			command.setExecutor(otd);
			command.setTabCompleter(otd);
		}
		command = this.getCommand("oh_the_dungeons_place");
		if (command != null) {
			command.setExecutor(otd_place);
			command.setTabCompleter(otd_place);
		}
		command = this.getCommand("oh_the_dungeons_cp");
		if (command != null) {
			command.setExecutor(otd_cp);
			command.setTabCompleter(otd_cp);
		}
		command = this.getCommand("oh_the_dungeons_tp");
		if (command != null) {
			command.setExecutor(otd_tp);
			command.setTabCompleter(otd_tp);
		}
		command = this.getCommand("otd_reload");
		if (command != null) {
			command.setExecutor(otr);
			command.setTabCompleter(otr);
		}

		command = this.getCommand("otd_debug");
		if (command != null) {
			Otd_Debug d = new Otd_Debug();
			command.setExecutor(d);
			command.setTabCompleter(d);
		}

		command = this.getCommand("otd_reload_scripts");
		if (command != null) {
			Otd_Reload_Scripts d = new Otd_Reload_Scripts();
			command.setExecutor(d);
			command.setTabCompleter(d);
		}
	}
}
