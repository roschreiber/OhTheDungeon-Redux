/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.dungeon.DungeonLevel;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskEncase;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskFilters;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskLayout;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskLinks;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskLoot;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskRooms;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskSegments;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskTower;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskTunnels;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.IDungeonTask;
import forge_sandbox.greymerk.roguelike.treasure.ITreasureChest;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import otd.addon.com.ohthedungeon.storydungeon.async.AsyncTask_Chunk;
import otd.addon.com.ohthedungeon.storydungeon.async.AsyncTask_Finish;
import otd.addon.com.ohthedungeon.storydungeon.async.AsyncTask_Loot;
import otd.addon.com.ohthedungeon.storydungeon.async.AsyncTask_Populator;
import otd.addon.com.ohthedungeon.storydungeon.generator.BaseGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.FakeGenerator;
import otd.addon.com.ohthedungeon.storydungeon.world.ZoneConfig;
import otd.lib.ZoneWorld;
import otd.lib.ZoneWorldEditor;
import otd.lib.async.later.roguelike.Later;

public class TaskHolder {

	private final static int CHUNK_SLOT = 1;

	private int step = 0;
	private final static int WIDTH = 12;
	private final static int MAX_STEP = WIDTH * WIDTH * 2;
	private final static int HALF_STEP = WIDTH * WIDTH;
	private final ZoneConfig zc;
	private final World world;
	private final int chunkX;
	private final int chunkZ;
	private final List<int[]> tasks = new ArrayList<>();
	private ZoneWorldEditor zwe;
	private RoguelikeDungeonBundle rb;
	private final FakeGenerator generator;
	private boolean init;

	public TaskHolder(FakeGenerator generator, World world, int chunkX, int chunkZ, ZoneConfig zc) {
		init = false;
		this.generator = generator;
		this.world = world;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.zc = zc;
	}

	public int getChunkX() {
		return this.chunkX;
	}

	public int getChunkZ() {
		return this.chunkZ;
	}

	@SuppressWarnings("deprecation")
	public boolean run() {
		NamespacedKey key = NamespacedKey.minecraft(zc.biome.toLowerCase());
		if (!init) {
			init = true;
			this.zwe = new ZoneWorldEditor(Registry.BIOME.get(key), world);
			zwe.setSeaLevel(63);
			zwe.setBottom(5);

			// tasks.add(new int[]{-1,-1});
			for (int x = chunkX - (WIDTH / 2 - 1); x <= chunkX + (WIDTH / 2); x++) {
				for (int z = chunkZ - (WIDTH / 2 - 1); z <= chunkZ + (WIDTH / 2); z++) {
					tasks.add(new int[] { x, z });
				}
			}
			for (int x = chunkX - (WIDTH / 2 - 1); x <= chunkX + (WIDTH / 2); x++) {
				for (int z = chunkZ - (WIDTH / 2 - 1); z <= chunkZ + (WIDTH / 2); z++) {
					if ((x == chunkX - (WIDTH / 2 - 1) || x == chunkX + (WIDTH / 2))
							|| (z == chunkZ - (WIDTH / 2 - 1) || z == chunkZ + (WIDTH / 2))) {
						tasks.add(null);
					} else {
						tasks.add(new int[] { x, z });
					}
				}
			}
		}
		if (step >= MAX_STEP)
			return true;
		if (generator.getPoolSize() > 10)
			return false;
		if (step == 0) {
			rb = AsyncBuildRoguelikeDungeon(zwe, zc, chunkX + 1, chunkZ + 1);
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException ex) {

			}
		}
		if (step == MAX_STEP - 1) {
			DungeonSyncTask task1 = new DungeonSyncTask(this.chunkX, this.chunkZ, TaskType.LOOT,
					new AsyncTask_Loot(this));
			DungeonSyncTask task2 = new DungeonSyncTask(this.chunkX, this.chunkZ, TaskType.FINISH,
					new AsyncTask_Finish(chunkX, chunkZ, zc));
			generator.addTaskToPool(task1, task2);
		}
		int[] pos = tasks.get(step);
		if (pos == null) {
			step++;
			return false;
		}
		if (step >= HALF_STEP) {
			BlockPopulator bp;
			int rr = FakeGenerator.getRandom().nextInt(6);
			if (rr == 0) {
				bp = FakeGenerator.getBlockPopulator(zc.populator);
			} else {
				bp = FakeGenerator.getBlockPopulator("Void");
			}
			List<ZoneWorld.CriticalNode> cn = zwe.world.getCriticalBlock(pos[0], pos[1]);
			List<Later> cl = zwe.world.getCriticalLater(pos[0], pos[1]);
			AsyncTask_Populator ap = new AsyncTask_Populator(bp, cn, cl, pos[0], pos[1]);

			if (rr == 0 || !ap.getCriticalNodes().isEmpty()) {
				generator.addTaskToPool(new DungeonSyncTask(pos[0], pos[1], TaskType.POPULATOR, ap));
			}

			step++;
		} else {
			BaseGenerator bg = FakeGenerator.getGenerator(zc.generator);
			AsyncChunk chunk = bg.asyncGenerateChunkData(17L, FakeGenerator.getRandom(), pos[0], pos[1]);
			step++;
			AsyncTask_Chunk ac = new AsyncTask_Chunk(chunk, Registry.BIOME.get(key), pos[0], pos[1]);

			ac.flat(CHUNK_SLOT);

			generator.addTaskToPool(new DungeonSyncTask(pos[0], pos[1], TaskType.CHUNK, ac));
		}

		return false;
	}

	public void addLoot() {
		if (rb == null)
			return;
		int sub_index = 0;
		DungeonTaskLoot dtl = new DungeonTaskLoot();
		while (!dtl.execute(rb.getZoneWorldEditor(), FakeGenerator.getRandom(), rb.getDungeon(), rb.getSettings(),
				sub_index))
			sub_index++;
		for (ITreasureChest chest : zwe.chests.chests) {
			chest.addBufferedItems(zwe);
		}
	}

	private static void delay() {
		try {
			Thread.sleep(100L);
		} catch (InterruptedException ex) {

		}
	}

	private final static IDungeonTask[] TASKS = { new DungeonTaskLayout(), new DungeonTaskEncase(),
			new DungeonTaskTunnels(), new DungeonTaskRooms(), new DungeonTaskSegments(), new DungeonTaskLinks(),
			new DungeonTaskTower(), new DungeonTaskFilters(), };

	private static RoguelikeDungeonBundle AsyncBuildRoguelikeDungeon(ZoneWorldEditor zwe, ZoneConfig zc, int chunkX,
			int chunkZ) {
		Coord location = new Coord(chunkX * 16 + 7, 0, chunkZ * 16 + 7);
		Coord start = new Coord(location.getX(), Dungeon.TOPLEVEL, location.getZ());

		try {
//            ISettings settings = Dungeon.settingsResolver.getSettings(zwe, FakeGenerator.getRandom(), location);
			DungeonSettings dungeons = FakeGenerator.getDungeonTheme(zc.extra);
			ISettings settings = Dungeon.settingsResolver.generateSettings(dungeons, zwe, FakeGenerator.getRandom(),
					location);

			Dungeon dungeon = new Dungeon(zwe);
			dungeon.origin = start;
			List<IDungeonLevel> levels = dungeon.getLevels();
			int numLevels = settings.getNumLevels();

			// create level objects
			for (int i = 0; i < numLevels; ++i) {
				LevelSettings levelSettings = settings.getLevelSettings(i);
				DungeonLevel level = new DungeonLevel(zwe, FakeGenerator.getRandom(), levelSettings, new Coord(start));
				levels.add(level);
			}

			for (IDungeonTask task : TASKS) {
				int sub_index = 0;
				while (!task.execute(zwe, FakeGenerator.getRandom(), dungeon, settings, sub_index))
					sub_index++;
				delay();
			}

			return new RoguelikeDungeonBundle(zwe, dungeon, settings);
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			Bukkit.getLogger().log(Level.SEVERE, sw.toString());
			return null;
		}
	}

}
