/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.com.someguyssoftware.dungeons2;

import forge_sandbox.com.someguyssoftware.dungeons2.builder.DungeonBuilderTopDown;
import forge_sandbox.com.someguyssoftware.dungeons2.builder.IDungeonBuilder;
import static forge_sandbox.com.someguyssoftware.dungeons2.builder.IDungeonBuilder.EMPTY_DUNGEON;
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import otd.Main;
import otd.api.event.DungeonGeneratedEvent;
import otd.world.DungeonType;
import otd.lib.ZoneWorld;
import otd.lib.async.AsyncRoguelikeDungeon;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.io.papermc.lib.PaperLib;
import otd.lib.async.later.roguelike.Later;

/**
 *
 * @author
 */
public class BukkitDungeonGenerator {
    private static DungeonGenerator generator;
    private static StyleSheet styleSheet;
    private static boolean init = false;
    private static DungeonConfigManager configs;
    private static SpawnSheet spawnSheet;

    
    private static void init() throws Exception {
        if(init) return;
        generator = new DungeonGenerator();
        styleSheet = StyleSheetLoader.loadAll();
        configs = new DungeonConfigManager();
        spawnSheet = SpawnSheetLoader.loadAll();
        init = true;
    }
    
    public static boolean generate(World world, Location location, Random random) throws Exception {
        try {
            init();
        } catch(Exception ex) {
            init = false;
            return false;
        }
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            asyncGenerate(world, location, random);
        });
        return true;
    }
    
    private static boolean asyncGenerate(World world, Location location, Random random) {
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
        
        BuildPattern pattern = ((RandomBuildPattern)patterns.next()).pattern;
        BuildSize levelSize = ((RandomBuildSize)levelSizes.next()).size;
//        BuildSize dungeonSize = ((RandomBuildSize)dungeonSizes.next()).size;
        BuildDirection direction = BuildDirection.values()[random.nextInt(BuildDirection.values().length)];
        
        LevelConfig levelConfig = PRESET_LEVEL_CONFIGS.getConfig(pattern, levelSize, direction);

        LevelBuilder levelBuilder = new LevelBuilder(levelConfig);
        IDungeonBuilder builder = new DungeonBuilderTopDown(levelBuilder);
//        List<IDungeonConfig> dcList = configs.configs;

        
        AsyncWorldEditor w = new AsyncWorldEditor(world);

        Dungeon dungeon = builder.build(w, null, random, new Coords(location.getBlockX(), location.getBlockY(), location.getBlockZ()), DungeonConfigManager.DEFAULT_CONFIG);
        if(dungeon == EMPTY_DUNGEON) return false;
        
        levelConfig.setSupportOn(true);
        dungeon.setTheme(theme);
        
        generator.generate(w, random, dungeon, styleSheet, spawnSheet);
        
        Set<int[]> chunks0 = w.getAsyncWorld().getCriticalChunks();
        
        int delay = 0;
        int eventDelay = 0;
        boolean isPaper = PaperLib.isPaper();
        
        for(int[] chunk : chunks0) {
            int chunkX = chunk[0];
            int chunkZ = chunk[1];
            
            List<ZoneWorld.CriticalNode> cn = w.getAsyncWorld().getCriticalBlock(chunkX, chunkZ);
            List<Later> later = w.getAsyncWorld().getCriticalLater(chunkX, chunkZ);
            
            if(!isPaper) delay++;
            eventDelay += 2;
            
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                try {
                    PaperLib.getChunkAtAsync(w.getWorld(), chunkX, chunkZ, true).thenAccept( (Chunk c) -> {
                        for(ZoneWorld.CriticalNode node : cn) {
                            int[] pos = node.pos;
                            if(node.bd != null) {
                                if(node.bd.getMaterial() != Material.GLASS_PANE)
                                    c.getBlock(pos[0], pos[1], pos[2]).setBlockData(node.bd, false);
                            } else {
                                if(node.material != Material.GLASS_PANE)
                                    c.getBlock(pos[0], pos[1], pos[2]).setType(node.material, false);
                            }
                        }
                        if(later != null) {
                            for(Later l : later) {
                                l.doSomethingInChunk(c);
                            }
                        }
                    });
                } catch (Exception ex) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
                        try (FileWriter writer = new FileWriter(AsyncRoguelikeDungeon.errfile, true)) {
                            writer.write(sw.toString());
                            writer.write("\n");
                        }
                        catch(IOException e)
                        {
                        }
                    });
                }
            }, delay);
        }
        
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            DungeonGeneratedEvent event = new DungeonGeneratedEvent(chunks0, DungeonType.AntMan, location.getBlockX(), location.getBlockZ());
            Bukkit.getServer().getPluginManager().callEvent(event);
        }, eventDelay);
        
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
