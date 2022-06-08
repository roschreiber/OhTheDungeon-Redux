/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;

import net.md_5.bungee.api.ChatColor;
import otd.Main;
import otd.addon.com.ohthedungeon.storydungeon.async.Async_Task_Pool;
import otd.addon.com.ohthedungeon.storydungeon.commands.CommandBack;
import otd.addon.com.ohthedungeon.storydungeon.commands.CommandCreate;
import otd.addon.com.ohthedungeon.storydungeon.commands.CommandMenu;
import otd.addon.com.ohthedungeon.storydungeon.config.DungeonConfig;
import otd.addon.com.ohthedungeon.storydungeon.database.Database;
import otd.addon.com.ohthedungeon.storydungeon.database.SQLite;
import otd.addon.com.ohthedungeon.storydungeon.generator.FakeGenerator;
import otd.addon.com.ohthedungeon.storydungeon.gui.Menu;
import otd.addon.com.ohthedungeon.storydungeon.listener.EventManager;
import otd.addon.com.ohthedungeon.storydungeon.util.I18n;
import otd.addon.com.ohthedungeon.storydungeon.util.ZoneUtils;
import otd.addon.com.ohthedungeon.storydungeon.vault.VaultManager;
import otd.addon.com.ohthedungeon.storydungeon.world.WorldManager;

/**
 *
 * @author shadow_wind
 */
public class PerPlayerDungeonInstance {
	private FakeGenerator fakeGenerator = null;
	private World world = null;
	private VaultManager vault;
	private Database db;
	private Async_Task_Pool async_task_pool;
	// private boolean api_ready;
	private EventManager eventManager;

	private static PerPlayerDungeonInstance instance;

	public PerPlayerDungeonInstance() {
		instance = this;
		async_task_pool = new Async_Task_Pool(Main.instance);
		vault = new VaultManager();
		String path = Main.instance.getDataFolder().getParentFile().toString() + File.separator
				+ "PerPlayerDungeonInstance";
		(new File(path)).mkdirs();
		DungeonConfig.load();
		if (!vault.setupEconomy()) {
			Bukkit.getLogger().log(Level.INFO,
					"{0}[Oh The Dungeons You''ll Go] Vault is not installed. Economy function is disabled",
					ChatColor.RED);
			DungeonConfig.setEnableMoneyPayment(false);
		}
		Main.instance.getCommand("otd_pi").setExecutor(new CommandMenu(this));
		Main.instance.getCommand("otd_pi_back").setExecutor(new CommandBack(this));
//		Main.instance.getCommand("otd_pi_debug").setExecutor(new CommandDebug(this));
		Main.instance.getCommand("otd_pi_create").setExecutor(new CommandCreate(this));
		initFakeGenerator();

		this.db = new SQLite(Main.instance);
		this.db.load();

		I18n.init();
		ZoneUtils.loadZonePos();

		eventManager = new EventManager();
		Bukkit.getServer().getPluginManager().registerEvents(eventManager, Main.instance);
		Bukkit.getServer().getPluginManager().registerEvents(new Menu(), Main.instance);
	}

	public Async_Task_Pool getAsyncTaskPool() {
		return this.async_task_pool;
	}

	public Database getDatabase() {
		return this.db;
	}

	public static PerPlayerDungeonInstance getInstance() {
		return instance;
	}

	public VaultManager getVault() {
		return this.vault;
	}

	private void initFakeGenerator() {
		fakeGenerator = new FakeGenerator(Main.instance);
		world = WorldManager.createDungeonWorld(fakeGenerator);
		fakeGenerator.setDefaultWorld(world);
		fakeGenerator.registerSyncChunkTask();
	}

	public FakeGenerator getFakeGenerator() {
		return this.fakeGenerator;
	}

	public World getWorld() {
		return this.world;
	}
}
