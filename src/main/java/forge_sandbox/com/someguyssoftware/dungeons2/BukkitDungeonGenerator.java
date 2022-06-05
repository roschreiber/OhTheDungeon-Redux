/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.com.someguyssoftware.dungeons2;

import static forge_sandbox.com.someguyssoftware.dungeons2.builder.IDungeonBuilder.EMPTY_DUNGEON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import forge_sandbox.com.someguyssoftware.dungeons2.builder.DungeonBuilderTopDown;
import forge_sandbox.com.someguyssoftware.dungeons2.builder.IDungeonBuilder;
import forge_sandbox.com.someguyssoftware.dungeons2.builder.LevelBuilder;
import forge_sandbox.com.someguyssoftware.dungeons2.config.BuildDirection;
import forge_sandbox.com.someguyssoftware.dungeons2.config.BuildPattern;
import forge_sandbox.com.someguyssoftware.dungeons2.config.BuildSize;
import forge_sandbox.com.someguyssoftware.dungeons2.config.PRESET_LEVEL_CONFIGS;
import forge_sandbox.com.someguyssoftware.dungeons2.generator.DungeonGenerator;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Dungeon;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.spawner.SpawnSheet;
import forge_sandbox.com.someguyssoftware.dungeons2.spawner.SpawnSheetLoader;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheet;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheetLoader;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Theme;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.DungeonConfigManager;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.Coords;
import forge_sandbox.com.someguyssoftware.gottschcore.random.IRandomProbabilityItem;
import forge_sandbox.com.someguyssoftware.gottschcore.random.RandomProbabilityCollection;
import io.papermc.lib.PaperLib;
import otd.Main;
import otd.api.event.DungeonGeneratedEvent;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.lib.ZoneWorld;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;
import otd.world.DungeonType;

/**
 *
 * @author
 */
@SuppressWarnings("unused")
public class BukkitDungeonGenerator {
	private static DungeonGenerator generator;
	private static StyleSheet styleSheet;
	private static boolean init = false;
	private static DungeonConfigManager configs;
	private static SpawnSheet spawnSheet;

	private static void init() throws Exception {
		if (init)
			return;
		generator = new DungeonGenerator();
		styleSheet = StyleSheetLoader.loadAll();
		configs = new DungeonConfigManager();
		spawnSheet = SpawnSheetLoader.loadAll();
		init = true;
	}

	public static boolean generate(World world, Location location, Random random) throws Exception {
		try {
			init();
		} catch (Exception ex) {
			init = false;
			return false;
		}
		AsyncWorldEditor w = new AsyncWorldEditor(world);
		String sw = world.getName();
		if (WorldConfig.wc.dict.containsKey(sw)) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(sw);
			w.setSeaLevel(swc.worldParameter.sealevel);
			w.setBottom(swc.worldParameter.bottom);
		}
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			asyncGenerate(w, location, random);
		});
		return true;
	}

	private final static Map<UUID, Integer> POOL = new HashMap<>();

	private static boolean asyncGenerate(AsyncWorldEditor w, Location location, Random random) {
		String key = (String) styleSheet.getThemes().keySet().toArray()[random.nextInt(styleSheet.getThemes().size())];
		Theme theme = styleSheet.getThemes().get(key);

		RandomProbabilityCollection<IRandomProbabilityItem> patterns = new RandomProbabilityCollection<>();
		RandomProbabilityCollection<IRandomProbabilityItem> levelSizes = new RandomProbabilityCollection<>();
		RandomProbabilityCollection<IRandomProbabilityItem> dungeonSizes = new RandomProbabilityCollection<>();

		patterns.add(76, new RandomBuildPattern(BuildPattern.SQUARE));
		patterns.add(12, new RandomBuildPattern(BuildPattern.HORZ));
		patterns.add(12, new RandomBuildPattern(BuildPattern.VERT));

		levelSizes.add(50, new RandomBuildSize(BuildSize.SMALL));
		levelSizes.add(25, new RandomBuildSize(BuildSize.MEDIUM));
		levelSizes.add(15, new RandomBuildSize(BuildSize.LARGE));
		levelSizes.add(10, new RandomBuildSize(BuildSize.VAST));

		dungeonSizes.add(30, new RandomBuildSize(BuildSize.SMALL));
		dungeonSizes.add(25, new RandomBuildSize(BuildSize.MEDIUM));
		dungeonSizes.add(25, new RandomBuildSize(BuildSize.LARGE));
		dungeonSizes.add(20, new RandomBuildSize(BuildSize.VAST));

		BuildPattern pattern = ((RandomBuildPattern) patterns.next()).pattern;
		BuildSize levelSize = ((RandomBuildSize) levelSizes.next()).size;
//        BuildSize dungeonSize = ((RandomBuildSize)dungeonSizes.next()).size;
		BuildDirection direction = BuildDirection.values()[random.nextInt(BuildDirection.values().length)];

		LevelConfig levelConfig = PRESET_LEVEL_CONFIGS.getConfig(pattern, levelSize, direction);

		LevelBuilder levelBuilder = new LevelBuilder(levelConfig);
		IDungeonBuilder builder = new DungeonBuilderTopDown(levelBuilder);

		Dungeon dungeon = builder.build(w, null, random,
				new Coords(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
				DungeonConfigManager.DEFAULT_CONFIG);
		if (dungeon == EMPTY_DUNGEON)
			return false;

		levelConfig.setSupportOn(true);
		dungeon.setTheme(theme);

		generator.generate(w, random, dungeon, styleSheet, spawnSheet);

		Set<int[]> chunks0 = w.getAsyncWorld().getCriticalChunks();

		UUID id;
		do {
			id = UUID.randomUUID();
		} while (POOL.containsKey(id));
		UUID uuid = id;
		synchronized (POOL) {
			POOL.put(uuid, chunks0.size());
		}

		int delay = 0;

		for (int[] chunk : chunks0) {
			int chunkX = chunk[0];
			int chunkZ = chunk[1];

			List<ZoneWorld.CriticalNode> cn = w.getAsyncWorld().getCriticalBlock(chunkX, chunkZ);
			List<Later> later = w.getAsyncWorld().getCriticalLater(chunkX, chunkZ);

			delay++;

			Bukkit.getScheduler().runTaskLater(Main.instance, () -> {

				PaperLib.getChunkAtAsync(w.getWorld(), chunkX, chunkZ, true).thenAccept((Chunk c) -> {
					for (ZoneWorld.CriticalNode node : cn) {
						int[] pos = node.pos;
						if (node.bd != null) {
							if (node.bd.getMaterial() != Material.GLASS_PANE)
								c.getBlock(pos[0], pos[1], pos[2]).setBlockData(node.bd, false);
						} else {
							if (node.material != Material.GLASS_PANE)
								c.getBlock(pos[0], pos[1], pos[2]).setType(node.material, false);
						}
					}
					if (later != null) {
						for (Later l : later) {
							l.doSomethingInChunk(c);
						}
					}
					boolean isFinish = false;
					synchronized (POOL) {
						if (POOL.containsKey(uuid)) {
							int count = POOL.get(uuid);
							count--;
							POOL.put(uuid, count);

							if (count == 0) {
								isFinish = true;
								POOL.remove(uuid);
							}
						}
					}

					if (isFinish) {

						int x = (w.zone_world.getMaxX() + w.zone_world.getMinX()) / 2;
						int y = (w.zone_world.getMaxY() + w.zone_world.getMinY()) / 2;
						int z = (w.zone_world.getMaxZ() + w.zone_world.getMinZ()) / 2;

						Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
							DungeonGeneratedEvent event = new DungeonGeneratedEvent(w.getWorld(), chunks0,
									DungeonType.AntMan, x, y, z);
							Bukkit.getServer().getPluginManager().callEvent(event);
						}, 1L);

					}
				});

			}, delay);
		}

		return true;
	}

	private static class RandomBuildPattern implements IRandomProbabilityItem {
		public BuildPattern pattern;

		public int prob = 0;

		public RandomBuildPattern(BuildPattern bp) {
			this.pattern = bp;
		}

		@Override
		public int getProbability() {
			return this.prob;
		}
	}

	private static class RandomBuildSize implements IRandomProbabilityItem {
		public BuildSize size;

		public int prob = 0;

		public RandomBuildSize(BuildSize bs) {
			this.size = bs;
		}

		@Override
		public int getProbability() {
			return this.prob;
		}
	}
}
