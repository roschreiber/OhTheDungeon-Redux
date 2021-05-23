/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest;

import forge_sandbox.StructureBoundingBox;
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
import otd.lib.ZoneWorld;
import otd.lib.async.AsyncRoguelikeDungeon;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.io.papermc.lib.PaperLib;
import otd.lib.async.later.roguelike.Later;
import otd.lib.async.later.twilightforest.Art_Later;
import forge_sandbox.twilightforest.structures.lichtower.StructureStartLichTower;
import otd.config.WorldConfig;

/**
 *
 * @author shadow
 */
public class TFBukkitGenerator {
    
    public static boolean generateLichTower(World world, Location location, Random random) {
        AsyncWorldEditor w = new AsyncWorldEditor(world);
        w.setDefaultState(Material.AIR);
        StructureStartLichTower l = new StructureStartLichTower(w, TFFeature.LICH_TOWER, random, location.getChunk().getX(),
            location.getChunk().getZ());
        int i = location.getBlockX();
        int j = location.getBlockZ();
        l.generateStructure(w, random, new StructureBoundingBox(i - 48, j - 48, i + 48, j + 48));
        asyncGenerate(w);
        return true;
    }
    
    public static void asyncGenerate(AsyncWorldEditor w) {
        Set<int[]> chunks0 = w.getAsyncWorld().getCriticalChunks();
        
        int delay = 0;
        
        for(int[] chunk : chunks0) {
            int chunkX = chunk[0];
            int chunkZ = chunk[1];
            
            List<ZoneWorld.CriticalNode> cn = w.getAsyncWorld().getCriticalBlock(chunkX, chunkZ);
            List<Later> later = w.getAsyncWorld().getCriticalLater(chunkX, chunkZ);
            
            if(!PaperLib.isPaper()) delay++;
            
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                try {
                    PaperLib.getChunkAtAsync(w.getWorld(), chunkX, chunkZ, true).thenAccept( (Chunk c) -> {
                        for(ZoneWorld.CriticalNode node : cn) {
                            int[] pos = node.pos;
                            if(node.bd != null) {
                                Material type = node.bd.getMaterial();
                                if(type != Material.GLASS_PANE && type != Material.OAK_FENCE)
                                    c.getBlock(pos[0], pos[1], pos[2]).setBlockData(node.bd, false);
                                else
                                    c.getBlock(pos[0], pos[1], pos[2]).setType(node.bd.getMaterial(), true);
                            } else {
                                Material type = node.material;
                                if(type != Material.GLASS_PANE && type != Material.OAK_FENCE)
                                    c.getBlock(pos[0], pos[1], pos[2]).setType(node.material, false);
                                else
                                    c.getBlock(pos[0], pos[1], pos[2]).setType(node.material, true);
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
        {
            String world_name = w.getWorld().getName();
            if(WorldConfig.wc.dict.containsKey(world_name)) {
                if(!WorldConfig.wc.dict.get(world_name).lich_tower.doArt) return;
            }
            delay++;
            for(Later later : w.getAsyncWorld().later_task) {
                if(later instanceof Art_Later) {
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                        later.doSomething();
                    }, delay);
                }
            }
        }
    }
}
