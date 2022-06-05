/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
//import java.util.logging.Level;
import org.bukkit.scheduler.BukkitRunnable;

import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.ChunkCallback;
import otd.addon.com.ohthedungeon.storydungeon.populator.GiantFlowerPopulator;
import otd.addon.com.ohthedungeon.storydungeon.populator.PromisedTreePopulator;
import otd.addon.com.ohthedungeon.storydungeon.populator.SakuraPopulator;
import otd.addon.com.ohthedungeon.storydungeon.populator.SmallOakPopulator;
import otd.addon.com.ohthedungeon.storydungeon.populator.VoidPopulator;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsBirthdayTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsDesertTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsForestTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsGrasslandTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsHoleTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsIceTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsJungleTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsMesaTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsMountainTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsMushroomTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsRareTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsRuinTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsSwampTheme;
import otd.addon.com.ohthedungeon.storydungeon.themes.SettingsTreeTheme;
import otd.addon.com.ohthedungeon.storydungeon.util.AsyncErrorLogger;
import otd.addon.com.ohthedungeon.storydungeon.util.DungeonSyncTask;
import otd.addon.com.ohthedungeon.storydungeon.world.ZoneConfig;

/**
 *
 * @author shadow_wind
 */
public final class FakeGenerator extends ChunkGenerator {
	private final static Map<String, BaseGenerator> GENERATORS = new LinkedHashMap<>();
	private final static Map<String, BlockPopulator> POPULATORS = new LinkedHashMap<>();
	private final static Map<String, DungeonSettings> DUNGEONS = new LinkedHashMap<>();

	private final static Random RANDOM = new Random();
	static {
		GENERATORS.put("Nordic", new Nordic());
//        GENERATORS.put("Empty", new EmptyWorld());
		GENERATORS.put("AlmostFlatlands", new AlmostFlatlands());
		GENERATORS.put("Vanilla", new Vanilla());
		GENERATORS.put("Fjord", new Fjord());
		GENERATORS.put("Hoth", new Hoth());
		GENERATORS.put("Tatooine", new Tatooine());
		GENERATORS.put("Slient", new Slient());
		GENERATORS.put("NorthLand", new NorthLand());
		GENERATORS.put("Rocky", new Rocky());
		GENERATORS.put("City", new ChunkCallback());
		GENERATORS.put("Island", new Island());
		GENERATORS.put("Tropic", new Tropic());
//        GENERATORS.put("Town", new Town());
		GENERATORS.put("Valley", new Valley());
		GENERATORS.put("Plot", new Plot());
		GENERATORS.put("Candy", new CandyWorld(17L));
		GENERATORS.put("Cake", new Cake());

//        GENERATORS.put("Cyber", new CyberWorldChunkGenerator());
//        GENERATORS.put("GlowsLike", new GlowsLike());
//        GENERATORS.put("FarLand0", new FarLand0());
//        GENERATORS.put("FarLand1", new FarLand1());
//        GENERATORS.put("FarLand2", new FarLand2());

		POPULATORS.put("Void", new VoidPopulator());
		POPULATORS.put("Oak", new SmallOakPopulator());
		POPULATORS.put("PromisedTree", new PromisedTreePopulator());
		POPULATORS.put("GiantFlower", new GiantFlowerPopulator());
		POPULATORS.put("Sakura", new SakuraPopulator());

		DUNGEONS.put("Desert", new SettingsDesertTheme());
		DUNGEONS.put("Forest", new SettingsForestTheme());
		DUNGEONS.put("Grassland", new SettingsGrasslandTheme());
		DUNGEONS.put("Hole", new SettingsHoleTheme());
		DUNGEONS.put("Ice", new SettingsIceTheme());
		DUNGEONS.put("Jungle", new SettingsJungleTheme());
		DUNGEONS.put("Mountain", new SettingsMountainTheme());
		DUNGEONS.put("Mushroom", new SettingsMushroomTheme());
		DUNGEONS.put("Rare", new SettingsRareTheme());
		DUNGEONS.put("Ruin", new SettingsRuinTheme());
		DUNGEONS.put("Swamp", new SettingsSwampTheme());
		DUNGEONS.put("Tree", new SettingsTreeTheme());
		DUNGEONS.put("Mesa", new SettingsMesaTheme());
		DUNGEONS.put("BirthDay", new SettingsBirthdayTheme());
	}

	public static Map<String, BaseGenerator> getGenerators() {
		return Collections.unmodifiableMap(GENERATORS);
	}

	public static Map<String, BlockPopulator> getPopulators() {
		return Collections.unmodifiableMap(POPULATORS);
	}

	public static Map<String, DungeonSettings> getTowers() {
		return Collections.unmodifiableMap(DUNGEONS);
	}

	private final List<DungeonSyncTask> task_pool = new ArrayList<>();
	private final JavaPlugin plugin;

	public FakeGenerator(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public World fake_world;

	public void setDefaultWorld(World w) {
		fake_world = w;
	}

	public static DungeonSettings getDungeonTheme(String name) {
//        Bukkit.getLogger().log(Level.SEVERE, name);
		return DUNGEONS.get(name);
	}

	public World getDungeonWorld() {
		return this.fake_world;
	}

	public int getPoolSize() {
		int res;
		synchronized (this.task_pool) {
			res = this.task_pool.size();
		}
		return res;
	}

	public FakeGenerator addTaskToPool(DungeonSyncTask... tasks) {
		synchronized (this.task_pool) {
			for (DungeonSyncTask task : tasks) {
				task_pool.add(task);
			}
		}
		return this;
	}

	public static Random getRandom() {
		return RANDOM;
	}

	public static BaseGenerator getGenerator(String generator) {
		return GENERATORS.get(generator);
	}

	public static BlockPopulator getBlockPopulator(String populator) {
		return POPULATORS.get(populator);
	}

	public void generateZone(World world, int chunkX, int chunkZ, ZoneConfig zc) {
//        TaskHolder t = new TaskHolder(this, world, chunkX, chunkZ, zc);
		PerPlayerDungeonInstance.getInstance().getAsyncTaskPool().addTask(this, world, chunkX, chunkZ, zc);
//        BukkitRunnable t_run = new BukkitRunnable() {
//            @Override
//            public void run() {
//                try {
//                    while(!t.run()) {
//                        try {
//                            Thread.sleep(1000L);
//                        } catch(InterruptedException ex) {
//
//                        }
//                    }
//                } catch(Exception ex) {
//                    AsyncErrorLogger.normalLog(chunkX, chunkZ, AsyncErrorLogger.exceptionToString(ex));
//                }
//            }
//        };
//        t_run.runTaskLaterAsynchronously(this.plugin, 1L);
	}

	ChunkData empty = null;

	@SuppressWarnings("deprecation")
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		if (empty == null)
			empty = Bukkit.createChunkData(world);
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				biome.setBiome(i, j, Biome.PLAINS);
		return empty;
	}

	private static BukkitRunnable run = null;

	public void registerSyncChunkTask() {
		if (run != null)
			return;
		run = new BukkitRunnable() {
			@Override
			public void run() {
				DungeonSyncTask task = null;
				synchronized (task_pool) {
					if (task_pool.isEmpty())
						return;
					for (DungeonSyncTask t : task_pool) {
						task = t;
						break;
					}
				}
				if (task == null)
					return;
				boolean r;
				try {
					r = task.getTask().doTask(fake_world, RANDOM);
				} catch (Exception ex) {
					r = true;
					AsyncErrorLogger.normalLog(task.getX(), task.getZ(), AsyncErrorLogger.exceptionToString(ex));
				}
				if (r) {
					synchronized (task_pool) {
						task_pool.remove(task);
					}
				}
			}
		};
		run.runTaskTimer(this.plugin, 1L, 5L);
	}
}
