package forge_sandbox.greymerk.roguelike.dungeon;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
//import forge_sandbox.greymerk.roguelike.config.RogueConfig;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsResolver;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.DungeonTaskRegistry;
import forge_sandbox.greymerk.roguelike.treasure.ITreasureChest;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import otd.Main;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.util.FileUtils;

//
public class Dungeon implements IDungeon {
	public static int count = 0;
	public static boolean isSpawning = false;
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;

	public static boolean init = false;

	private static final String SETTINGS_DIRECTORY = Main.instance.getDataFolder().toString() + File.separator
			+ "forge_sandbox" + File.separator + "roguelike" + File.separator + "settings";
	public static SettingsResolver settingsResolver;
//        public static SettingsResolver empty_loot_settingsResolver;

	public Coord origin;
	private List<IDungeonLevel> levels;

	private IWorldEditor editor;

	static {
		try {
//			RogueConfig.reload(false);
			initResolver();
		} catch (Exception e) {
			// do nothing
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			Bukkit.getLogger().log(Level.SEVERE, sw.toString());
		}
	}

	public static void initResolver() throws Exception {
		File settingsDir = new File(SETTINGS_DIRECTORY);

		if (settingsDir.exists() && !settingsDir.isDirectory()) {
			throw new Exception("Settings directory is a file");
		}

		if (!settingsDir.exists()) {
			settingsDir.mkdir();
		}

		File[] settingsFiles = settingsDir.listFiles();
		Arrays.sort(settingsFiles);

		SettingsContainer settings = new SettingsContainer();
		settingsResolver = new SettingsResolver(settings);

		Map<String, String> files = new HashMap<>();

		for (File file : Arrays.asList(settingsFiles)) {

			if (!FileUtils.getExtension(file).equals("json"))
				continue;

			try {
				@SuppressWarnings("deprecation")
				String content = Files.toString(file, Charsets.UTF_8);
				files.put(file.getName(), content);
			} catch (IOException e) {
				throw new Exception("Error reading file : " + file.getName());
			}
		}

		settings.parseCustomSettings(files);
	}

	public Dungeon(IWorldEditor editor) {
		this.editor = editor;
		this.levels = new ArrayList<>();
	}

//	public void forceGenerateNear(Random rand, int x, int z){
//		if(Dungeon.settingsResolver == null) return;
//		int attempts = 50;
//		
//		for(int i = 0;i < attempts;i++){
//			Coord location = new Coord(x, 0 ,z);
//			
//			if(!validLocation(rand, location)) continue;
//			ISettings setting;
//			
//			try{
//				setting = Dungeon.settingsResolver.getSettings(editor, rand, location);
//			} catch(Exception e){
//                                StringWriter sw = new StringWriter();
//                                PrintWriter pw = new PrintWriter(sw);
//                                e.printStackTrace(pw);
//                                Bukkit.getLogger().log(Level.SEVERE, sw.toString());
//				return;
//			}
//                        if(setting == null) setting = new SettingsRandom(rand);
//			 
////                        synchronized(dict) {
////                            List<Node> queue = dict.get(editor.getWorldName());
////                            if(queue == null) {
////                                queue = new ArrayList<>();
////                                dict.put(editor.getWorldName(), queue);
////                            }
////                            for(Node n : queue) {
////                                int dx = (x-4)/16 - n.x;
////                                int dz = (z-4)/16 - n.z;
////                                double distance = Math.sqrt(dx*dx+dz*dz);
////                                if(distance < THRESHOLD) return;
////                            }
////                            
////                            queue.add(new Node((x-4)/16, (z-4)/16));
////                            if(queue.size() > max_len) queue.remove(0);
////                            count++;
////                        }
//			int size = generate(setting, location);
//
//                        AsyncLog.logMessage("[Command] Place gold dungeon @" + editor.getWorldName() + " x=" + location.getX() + ", z=" + location.getZ());
//			return;
//		}
//	}

	public boolean generateNear(Random rand, int x, int z) {
		if (Dungeon.settingsResolver == null)
			return false;
		int attempts = 50;

		for (int i = 0; i < attempts; i++) {
			Coord location = new Coord(x, 0, z);

			if (!validLocation(rand, location))
				continue;
			ISettings setting;

			try {
				setting = Dungeon.settingsResolver.getSettings(editor, rand, location);
				if (setting == null)
					return false;
				if (WorldConfig.wc.dict.containsKey(editor.getWorldName())) {
					SimpleWorldConfig swc = WorldConfig.wc.dict.get(editor.getWorldName());
					if (!swc.roguelike.builtinLoot) {
						((DungeonSettings) setting).clearLootRule();
					}
				}
			} catch (Exception e) {
//                                StringWriter sw = new StringWriter();
//                                PrintWriter pw = new PrintWriter(sw);
//                                e.printStackTrace(pw);
//                                Bukkit.getLogger().log(Level.SEVERE, sw.toString());
				return false;
			}
			return generate(setting, location) > 0;
		}
		return false;
	}

	@Override
	public int generate(ISettings settings, Coord pos) {
		this.origin = new Coord(pos.getX(), Dungeon.TOPLEVEL, pos.getZ());
		return DungeonGenerator.generate(editor, this, settings, DungeonTaskRegistry.getTaskRegistry());
	}

	public static boolean canSpawnInChunk(int chunkX, int chunkZ, IWorldEditor editor) {
		if (!WorldConfig.wc.dict.containsKey(editor.getWorldName()))
			return false;
		if (!WorldConfig.wc.dict.get(editor.getWorldName()).roguelike.doNaturalSpawn)
			return false;
		return true;
	}

	public static boolean isVillageChunk(IWorldEditor editor, int chunkX, int chunkZ) {
		// TODO
		return true;
	}

	@Override
	public void spawnInChunk(Random rand, int chunkX, int chunkZ) {
		if (Dungeon.canSpawnInChunk(chunkX, chunkZ, editor)) {

			int x = chunkX * 16 + rand.nextInt(12);
			int z = chunkZ * 16 + rand.nextInt(12);
			generateNear(rand, x, z);
		}
	}

	public boolean forceSpawnInChunk(Random rand, int chunkX, int chunkZ) {
		int x = chunkX * 16 + 4;
		int z = chunkZ * 16 + 4;

		return generateNear(rand, x, z);
	}

	public static int getLevel(int y) {

		if (y < 15)
			return 4;
		if (y < 25)
			return 3;
		if (y < 35)
			return 2;
		if (y < 45)
			return 1;
		return 0;
	}

	public boolean validLocation(Random rand, Coord column) {
		return true;
	}

	public static Coord getNearbyCoord(Random rand, int x, int z, int min, int max) {

		int distance = min + rand.nextInt(max - min);

		double angle = rand.nextDouble() * 2 * Math.PI;

		int xOffset = (int) (Math.cos(angle) * distance);
		int zOffset = (int) (Math.sin(angle) * distance);

		Coord nearby = new Coord(x + xOffset, 0, z + zOffset);
		return nearby;
	}

	public static Random getRandom(IWorldEditor editor, Coord pos) {
		return new Random(Objects.hash(editor.getSeed(), pos));
	}

	@Override
	public List<ITreasureChest> getChests() {
		return this.editor.getTreasure().getChests();
	}

	@Override
	public Coord getPosition() {
		return new Coord(this.origin);
	}

	private Coord backup;

	@Override
	public Coord getTaskPosition() {
		return backup;
	}

	@Override
	public void setTaskPosition(Coord pos) {
		backup = pos;
	}

	@Override
	public List<IDungeonLevel> getLevels() {
		return this.levels;
	}
}
