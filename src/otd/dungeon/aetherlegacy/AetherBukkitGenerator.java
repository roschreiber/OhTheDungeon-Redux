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
package otd.dungeon.aetherlegacy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import otd.Main;
import otd.api.event.DungeonGeneratedEvent;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
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
public class AetherBukkitGenerator {

    public static void generate(World world, Random random, int x, int z) {
        AsyncWorldEditor w = new AsyncWorldEditor(world);
        
        int height = 180;
        boolean cloud = true;
        Material cloud_material = Material.PACKED_ICE;
        
        String world_name = world.getName();
        
        if(WorldConfig.wc.dict.containsKey(world_name)) {
            SimpleWorldConfig swc = WorldConfig.wc.dict.get(world_name);
            height = swc.aether_dungeon.height;
            cloud = swc.aether_dungeon.cloud;
            cloud_material = Material.PACKED_ICE;
        }
        
        final int h = height;
        final boolean c = cloud;
        final Material cm = cloud_material;
        
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            asyncGenerate(w, random, x, z, h, c, cm);
        });
    }
    
    private static boolean asyncGenerate(AsyncWorldEditor w, Random random, int x, int z, 
            int height, boolean cloud, Material cloud_material) {
        w.setDefaultState(Material.AIR);
        
        int firstStaircaseZ, secondStaircaseZ, finalStaircaseZ;
        int xTendency, zTendency;
        ComponentSilverDungeon dungeon = new ComponentSilverDungeon();
        dungeon.setBaseStructureOffset(x - 55, height, z - 45);
        dungeon.setStructureOffset(0, 0, 0);
        firstStaircaseZ = random.nextInt(3);
        secondStaircaseZ = random.nextInt(3);
        finalStaircaseZ = random.nextInt(3);

        xTendency = random.nextInt(3) - 1;
        zTendency = random.nextInt(3) - 1;

        dungeon.setStaircasePosition(firstStaircaseZ, secondStaircaseZ, finalStaircaseZ);
        dungeon.setCloudTendencies(xTendency, zTendency);
        
        dungeon.addComponentParts(w, random, cloud, cloud_material);

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
            DungeonGeneratedEvent event = new DungeonGeneratedEvent(chunks0, DungeonType.Aether, x, z);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }, eventDelay);
        
        return true;
    }
}
