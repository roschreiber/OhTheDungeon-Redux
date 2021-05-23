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

import otd.gui.*;
import otd.gui.dungeon_plot.*;
import forge_sandbox.com.someguyssoftware.dungeons2.config.ModConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.spawner.SpawnSheetLoader;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheetLoader;
import otd.commands.Otd_Place;
import otd.commands.Otd_Cp;
import otd.commands.Otd;
import forge_sandbox.Sandbox;
import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.jaredbgreat.dldungeons.themes.ThemeReader;
import forge_sandbox.jaredbgreat.dldungeons.themes.ThemeType;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import otd.dungeon.draylar.BattleTowerSchematics;
import otd.listener.MobListener;
import otd.listener.SpawnerListener;
import otd.populator.DungeonPopulator;
import otd.update.UpdateChecker;
import otd.util.Diagnostic;
import otd.lib.async.io.papermc.lib.PaperLib;
import otd.lib.bstats.Metrics;
import otd.lib.DungeonWorldManager;
import otd.util.I18n;
import otd.util.Logging;
import otd.config.PluginConfig;
import otd.config.WorldConfig;
import otd.util.LanguageUtil;
import forge_sandbox.twilightforest.structures.lichtower.boss.Lich;
import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.commands.Otd_Tp;
import otd.gui.customstruct.CustomDungeonEditor;
import otd.gui.customstruct.CustomDungeonList;
import otd.gui.customstruct.CustomDungeonPlaceSelect;
import otd.gui.customstruct.CustomDungeonSelect;
import otd.gui.customstruct.MobSelect;
import otd.gui.customstruct.SchematicSelect;
import otd.gui.customstruct.WorldCustomDungeon;
import otd.integration.WorldEdit;
import otd.struct.SchematicLoader;
import otd.world.ChunkList;
import otd.world.DungeonWorld;
import otd.world.WorldDefine;
import otd.world.WorldGenOptimization;

/**
 *
 * @author
 */
public class Main extends JavaPlugin {
    public static JavaPlugin instance;
    public static boolean disabled = false;
    private static Integer api_version = 6;
    public static MultiVersion.Version version = MultiVersion.Version.UNKNOWN;
    private Metrics metrics;
    private final static int metric_pluginId = 9213;
    private static PerPlayerDungeonInstance ppdi;
    
    public Main() {
        instance = this;
        if(MultiVersion.is114()) {
            version = MultiVersion.Version.V1_14_R1;
            Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You''ll Go] MC Version: 1.14.x", ChatColor.GREEN);
        }
        else if(MultiVersion.is115()) {
            version = MultiVersion.Version.V1_15_R1;
            Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You''ll Go] MC Version: 1.15.x", ChatColor.GREEN);
        }
        else if(MultiVersion.is116R1()) {
            version = MultiVersion.Version.V1_16_R1;
            Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You''ll Go] MC Version: 1.16.[0-1]", ChatColor.GREEN);
        }
        else if(MultiVersion.is116R2()) {
            version = MultiVersion.Version.V1_16_R2;
            Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You''ll Go] MC Version: 1.16.[2-3]", ChatColor.GREEN);
        }
        else if(MultiVersion.is116R3()) {
            version = MultiVersion.Version.V1_16_R3;
            Bukkit.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You''ll Go] MC Version: 1.16.4", ChatColor.GREEN);
        }
        else version = MultiVersion.Version.UNKNOWN;
    }
    
    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.WARNING, "[Oh The Dungeons You'll Go] Plugin is disabled");
        disabled = true;
    }
    
    @Override
    public void onEnable() {
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException ex) {
            getLogger().severe("[Oh The Dungeons You'll Go] requires Spigot (or a fork such as Paper) in order to run.");
            throw new UnsupportedOperationException("Unsupported Server Type");
        }
        
        if(version == MultiVersion.Version.UNKNOWN) {
            Bukkit.getLogger().log(Level.SEVERE, "[Oh The Dungeons You'll Go] Unsupported MC Version");
            throw new UnsupportedOperationException("Unsupported MC Version");
        }
        
        //PaperLib.suggestPaper(this);
        disabled = false;
        
        Sandbox.mkdir();
        BackupGUI.initBackupFolder();
        
        I18n.init();
        {
            (new File(this.getDataFolder(), "schematics")).mkdirs();
            if(WorldEdit.isReady()) SchematicLoader.initDir(this);
        }
        
        WorldConfig.loadWorldConfig();
        
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
        getServer().getPluginManager().registerEvents(CustomDungeonSelect.instance, this);
        getServer().getPluginManager().registerEvents(MobSelect.instance, this);
        getServer().getPluginManager().registerEvents(SchematicSelect.instance, this);
        getServer().getPluginManager().registerEvents(WorldCustomDungeon.instance, this);
        getServer().getPluginManager().registerEvents(CustomDungeonPlaceSelect.instance, this);
        
        getServer().getPluginManager().registerEvents(new MobListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
        getServer().getPluginManager().registerEvents(new Lich(), this);
        getServer().getPluginManager().registerEvents(new WorldGenOptimization(), this);

        PluginConfig.instance.init();
        PluginConfig.instance.update();
        
        ChunkList.rebuildChunkMap();
        
        String update = PluginConfig.instance.config.get("updater");
        if(update != null && update.equalsIgnoreCase("TRUE")) {
            Bukkit.getLogger().log(Level.INFO, "[Oh The Dungeons You'll Go] Update checking...");
            asyncUpdateChecker();
        }
        
        String bstats = PluginConfig.instance.config.get("bstats");
        if(bstats != null && bstats.equalsIgnoreCase("TRUE")) {
            metrics = new Metrics(this, metric_pluginId);
        }
        
        registerCommand();
        BattleTowerSchematics.init(this);
        
        LanguageUtil.init();
        Lich.init();
        
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            Diagnostic.diagnostic();
            {
                try {
                    InputStream stream = this.getResource("logo.txt");
                    InputStreamReader isr = new InputStreamReader(stream);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;

                    while( (line=reader.readLine())!=null) {
                        Bukkit.getLogger().log(Level.INFO, "{0}{1}", new Object[]{ChatColor.BLUE, line});
                    }
                } catch (IOException ex) {

                }
            }
        }, 2L);
        
        Bukkit.getScheduler().runTaskLater(this, () -> {
            PaperLib.suggestPaper(Main.instance);
        }, 3L);
        
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if(WorldConfig.wc.dungeon_world.finished) {
                Bukkit.getLogger().log(Level.INFO, "Loading dungeon plot world...");
                DungeonWorld.loadDungeonWorld();
            }
        }, 1L);
        
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.getLogger().log(Level.INFO, "Loading PerPlayerDungeonInstance...");
            ppdi = new PerPlayerDungeonInstance();
        }, 1L);
        
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.getLogger().log(Level.INFO, "Loading User Manual...");
            loadPDF();
        }, 1L);
        
//        Bukkit.getScheduler().runTaskLater(this, () -> {
//            MapManager.init();
//        }, 1L);
    }
    private void loadPDF() {
        File out = new File(Main.instance.getDataFolder(), "OTD.pdf");
        try(InputStream in = Main.instance.getResource("OTD.pdf");
           OutputStream writer = new BufferedOutputStream(
               new FileOutputStream(out, false))) {
            // Step 3
            byte[] buffer = new byte[1024 * 4];
            int length;
            while((length = in.read(buffer)) >= 0) {
                writer.write(buffer, 0, length);
            }
        } catch(Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Load OTD.pdf error...");
            return;
        }
        try {
            getServer().getPluginManager().loadPlugin(out);
        } catch(InvalidDescriptionException | InvalidPluginException | UnknownDependencyException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Load OTD.pdf error...");
        }
    }

    private void loadAdvancement() {
        File out = new File(Main.instance.getDataFolder(), "OhTheDungeonAdvancement.jar");
        try(InputStream in = Main.instance.getResource("OhTheDungeonAdvancement.jar");
           OutputStream writer = new BufferedOutputStream(
               new FileOutputStream(out, false))) {
            // Step 3
            byte[] buffer = new byte[1024 * 4];
            int length;
            while((length = in.read(buffer)) >= 0) {
                writer.write(buffer, 0, length);
            }
        } catch(Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Load Advancements error...");
            return;
        }
        try {
            getServer().getPluginManager().loadPlugin(out);
        } catch(InvalidDescriptionException | InvalidPluginException | UnknownDependencyException ex) {
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
        @EventHandler(priority = EventPriority.LOW)
        public void onWorldInit(WorldInitEvent event) {
            String world_name = event.getWorld().getName();
            if(world_name.equals(DungeonWorldManager.WORLD_NAME)) return;
            if(world_name.equalsIgnoreCase(WorldDefine.WORLD_NAME)) return;
            Logging.logInfo(ChatColor.BLUE + "[Oh The Dungeons You'll Go] Found world: " + world_name);
            event.getWorld().getPopulators().add(new DungeonPopulator());
        }
    }
    
    private void registerCommand() {
        Otd otd = new Otd();
        Otd_Place otd_place = new Otd_Place();
        Otd_Cp otd_cp = new Otd_Cp();
        Otd_Tp otd_tp = new Otd_Tp();
        
        PluginCommand command;
        command = this.getCommand("oh_the_dungeons");
        if(command != null) {
            command.setExecutor(otd);
            command.setTabCompleter(otd);
        }
        command = this.getCommand("oh_the_dungeons_place");
        if(command != null) {
            command.setExecutor(otd_place);
            command.setTabCompleter(otd_place);
        }
        command = this.getCommand("oh_the_dungeons_cp");
        if(command != null) {
            command.setExecutor(otd_cp);
            command.setTabCompleter(otd_cp);
        }
        command = this.getCommand("oh_the_dungeons_tp");
        if(command != null) {
            command.setExecutor(otd_tp);
            command.setTabCompleter(otd_tp);
        }
    }
}
