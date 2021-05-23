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
package otd.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import otd.Main;
import otd.populator.AetherPopulator;
import otd.populator.AntManPopulator;
import otd.populator.BattleTowerPopulator;
import otd.populator.DoomlikePopulator;
import otd.populator.DraylarBattleTowerPopulator;
import otd.populator.LichTowerPopulator;
import otd.populator.RoguelikePopulator;
import otd.populator.SmoofyPopulator;
import otd.config.WorldConfig;
import otd.config.WorldConfig.CustomDungeon;
import otd.world.task.DungeonChunkTask;
import otd.world.task.DungeonPlaceTask;
import otd.world.task.DungeonWorldTask;
import otd.lib.async.io.papermc.lib.PaperLib;
import otd.util.I18n;
import otd.populator.IPopulator;
import otd.struct.SchematicLoader;

/**
 *
 * @author shadow
 */
public class DungeonTask {
    private static int step = 0;
    private static int next = 0;
    private static int pointer = 0;
    
    private static boolean generating = false;
    private static boolean breaking = false;
    
    private final static Random random = new Random();
    
    public static boolean isGenerating() {
        return generating;
    }
    
    public static void setBreak() {
        breaking = true;
    }
    
    public static int getTaskCount() {
        return ChunkList.task_pool.size();
    }
    
    
    private final static AetherPopulator aether;
    private final static AntManPopulator antman;
    private final static BattleTowerPopulator battle_tower;
    private final static DoomlikePopulator doomlike;
    private final static DraylarBattleTowerPopulator draylar;
    private final static LichTowerPopulator lich_tower;
    private final static RoguelikePopulator roguelike;
    private final static SmoofyPopulator smoofy;
    private final static Map<DungeonType, IPopulator> dict;
    static {
        dict = new HashMap<>();
        aether = new AetherPopulator();
        antman = new AntManPopulator();
        battle_tower = new BattleTowerPopulator();
        doomlike = new DoomlikePopulator();
        draylar = new DraylarBattleTowerPopulator();
        lich_tower = new LichTowerPopulator();
        roguelike = new RoguelikePopulator();
        smoofy = new SmoofyPopulator();
        
        dict.put(DungeonType.Aether, aether);
        dict.put(DungeonType.AntMan, antman);
        dict.put(DungeonType.BattleTower, battle_tower);
        dict.put(DungeonType.Doomlike, doomlike);
        dict.put(DungeonType.Draylar, draylar);
        dict.put(DungeonType.Lich, lich_tower);
        dict.put(DungeonType.Roguelike, roguelike);
        dict.put(DungeonType.DungeonMaze, smoofy);
    }
    
    public static void placeAether(int x, int z) {
        Chunk chunk = DungeonWorld.world.getChunkAt(x, z);
//        
//        chunk.getBlock(8, 64, 8).setType(Material.RED_WOOL);
//        chunk.getBlock(8, 65, 8).setType(Material.RED_WOOL);
//        chunk.getBlock(8, 66, 8).setType(Material.RED_WOOL);
//        chunk.getBlock(8, 67, 8).setType(Material.RED_WOOL);
//        chunk.getBlock(8, 68, 8).setType(Material.RED_WOOL);
//        chunk.getBlock(8, 69, 8).setType(Material.RED_WOOL);
//        
//        chunk.getBlock(7, 68, 8).setType(Material.RED_WOOL);
//        chunk.getBlock(9, 68, 8).setType(Material.RED_WOOL);
//        
//        chunk.getBlock(6, 67, 8).setType(Material.RED_WOOL);
//        chunk.getBlock(10, 67, 8).setType(Material.RED_WOOL);
        
        aether.generateDungeon(DungeonWorld.world, random, chunk);
    }
    public static void placeAntMan(int x, int z) {
        antman.generateDungeon(DungeonWorld.world, random, DungeonWorld.world.getChunkAt(x, z));
    }
    public static void placeBattleTower(int x, int z) {
        battle_tower.generateDungeon(DungeonWorld.world, random, DungeonWorld.world.getChunkAt(x, z));
    }
    public static void placeDoomLike(int x, int z) {
        doomlike.generateDungeon(DungeonWorld.world, random, DungeonWorld.world.getChunkAt(x, z));
    }
    public static void placeDraylar(int x, int z) {
        draylar.generateDungeon(DungeonWorld.world, random, DungeonWorld.world.getChunkAt(x, z));
    }
    public static void placeLichTower(int x, int z) {
        lich_tower.generateDungeon(DungeonWorld.world, random, DungeonWorld.world.getChunkAt(x, z));
    }
    public static void placeSmoofy(int x, int z) {
        smoofy.generateDungeon(DungeonWorld.world, random, DungeonWorld.world.getChunkAt(x, z));
    }
    public static void placeRoguelike(int x, int z) {
        roguelike.generateDungeonWithRandomTheme(DungeonWorld.world, random, DungeonWorld.world.getChunkAt(x, z));
    }
    public static void placeCustom(int x, int z) {
        x = x * 16 + 7;
        z = z * 16 + 7;
        
        Location loc = DungeonWorld.world.getHighestBlockAt(x, z).getLocation();
        CustomDungeon dungeon = SchematicLoader.getRandomDungeon(DungeonWorld.world);
        
        if(dungeon == null) return;
        SchematicLoader.createInWorldAsync(dungeon, DungeonWorld.world, x, z, random);
    }
    
    public static void globalMessage() {
        Bukkit.broadcastMessage(ChatColor.BLUE + I18n.instance.Dungeon_Plot_Finish);
    }
    
    public static void start() {
        step = 0;
        next = 0;
        pointer = 0;
        generating = true;
        breaking = false;
        
        int dungeon_count = WorldConfig.wc.dungeon_world.dungeon_count;
        
        BukkitRunnable r = new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if(step == next) {
                    if(DungeonWorld.world == null) {
                        this.cancel();
                        return;
                    }
                    if(ChunkList.task_pool.size() <= pointer) {
                        this.cancel();
                        generating = false;
                        WorldConfig.wc.dungeon_world.finished = true;
                        WorldConfig.wc.dungeon_world.dungeon_count_finish = WorldConfig.wc.dungeon_world.dungeon_count;
                        WorldConfig.save();
                        globalMessage();
                        return;
                    }
                    DungeonWorldTask dwt = ChunkList.task_pool.get(pointer);
                    pointer++;
                    
                    if(dwt instanceof DungeonChunkTask) {
                        int[] chunkPos = dwt.getChunkPos();
                        //DungeonWorld.world.getChunkAt(chunkPos[0], chunkPos[1]);
                        PaperLib.getChunkAtAsync(DungeonWorld.world, chunkPos[0], chunkPos[1], true, false);
                        step += dwt.getDelay();
                        next++;
                    }
                    if(dwt instanceof DungeonPlaceTask) {
                        DungeonPlaceTask dungeon = (DungeonPlaceTask) dwt;
                        int[] chunkPos = dungeon.getChunkPos();
                        
                        if(dungeon.dungeon == DungeonType.Aether) {
                            placeAether(chunkPos[0], chunkPos[1]);
                        } else if(dungeon.dungeon == DungeonType.AntMan) {
                            placeAntMan(chunkPos[0], chunkPos[1]);
                        } else if(dungeon.dungeon == DungeonType.BattleTower) {
                            placeBattleTower(chunkPos[0], chunkPos[1]);
                        } else if(dungeon.dungeon == DungeonType.Doomlike) {
                            placeDoomLike(chunkPos[0], chunkPos[1]);
                        } else if(dungeon.dungeon == DungeonType.Draylar) {
                            placeDraylar(chunkPos[0], chunkPos[1]);
                        } else if(dungeon.dungeon == DungeonType.DungeonMaze) {
                            placeSmoofy(chunkPos[0], chunkPos[1]);
                        } else if(dungeon.dungeon == DungeonType.Lich) {
                            placeLichTower(chunkPos[0], chunkPos[1]);
                        } else if(dungeon.dungeon == DungeonType.CustomDungeon) {
                            placeCustom(chunkPos[0], chunkPos[1]);
                        } else {
                            placeRoguelike(chunkPos[0], chunkPos[1]);
                        }
                        
                        count++;
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            if(p.hasPermission("oh_the_dungeons.admin")) {
                                p.sendMessage("Dungeon Plot: " + count + "/" + dungeon_count);
                            }
                        }
                        
                        step += dungeon.getDelay();
                        next++;
                        
                        Bukkit.getLogger().log(Level.INFO, "Dungeon {0}, chunkx={1}, chunkz={2}", new Object[]{dungeon.dungeon.toString(), chunkPos[0], chunkPos[1]});

                    }
                } else if(next < step) {
                    next++;
                } else {
                    this.cancel();
                    generating = false;
                    WorldConfig.wc.dungeon_world.finished = true;
                    WorldConfig.wc.dungeon_world.dungeon_count_finish = WorldConfig.wc.dungeon_world.dungeon_count;
                    WorldConfig.save();
                }
            }
        };
        r.runTaskTimer(Main.instance, 1, 1);
    }
}
