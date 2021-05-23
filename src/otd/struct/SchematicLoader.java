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
package otd.struct;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.sk89q.worldedit.world.block.BlockState;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import otd.Main;
import otd.api.event.DungeonGeneratedEvent;
import otd.config.CustomDungeonType;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.config.WorldConfig.CustomDungeon;
import otd.util.RandomCollection;
import otd.world.DungeonType;
import otd.lib.ZoneWorld;
import otd.lib.async.AsyncRoguelikeDungeon;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.io.papermc.lib.PaperLib;
import otd.lib.async.later.customstruct.Chest_Later;
import otd.lib.async.later.customstruct.Spawner_Later;
import otd.lib.async.later.roguelike.Later;


/**
 *
 * @author shadow
 */
public class SchematicLoader {
    
    private static File schematics;
    
    public static void initDir(JavaPlugin plugin) {
        schematics = new File(plugin.getDataFolder(), "schematics");
        if(!schematics.exists()) schematics.mkdir();
    }
    
    public static File getSchematicDir() {
        return schematics;
    }
    
    public static CustomDungeon getRandomDungeon(World world) {
        if(!WorldConfig.wc.dict.containsKey(world.getName())) return null;
        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
        
        RandomCollection<CustomDungeon> dungeons = new RandomCollection<>();
        for(UUID id : swc.custom_dungeons) {
            if(WorldConfig.wc.custom_dungeon.containsKey(id)) {
                CustomDungeon d = WorldConfig.wc.custom_dungeon.get(id);
                dungeons.add(d.weight, d);
            }
        }
        
        if(dungeons.isEmpty()) return null;
        return dungeons.next();
    }
    
    public static CustomDungeon getRandomDungeon(World world, String biome) {
        if(!WorldConfig.wc.dict.containsKey(world.getName())) return null;
        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
        
        RandomCollection<CustomDungeon> dungeons = new RandomCollection<>();
        for(UUID id : swc.custom_dungeons) {
            if(WorldConfig.wc.custom_dungeon.containsKey(id)) {
                CustomDungeon d = WorldConfig.wc.custom_dungeon.get(id);
                if(d.biomeExclusions.contains(biome)) continue;
                dungeons.add(d.weight, d);
            } else {
                Bukkit.getLogger().info(id.toString());
            }
        }
        
        if(dungeons.isEmpty()) return null;
        return dungeons.next();
    }
    
    public static void createInWorldAsync(CustomDungeon dungeon, World world, int x, int z, Random random) {
        Location loc;
        if(dungeon.type == CustomDungeonType.ground) loc = world.getHighestBlockAt(x, z).getLocation();
        else loc = new Location(world, x, dungeon.sky_spawn_height, z);
        createInWorldAsync(dungeon, loc, random);
    }
    
    private static void createInWorld(AsyncWorldEditor w, CustomDungeon dungeon, Location loc, Random random) throws FileNotFoundException, IOException {
        Clipboard c = load(new File(getSchematicDir(), dungeon.file));
        applyAsync(w, loc, c, dungeon, random);
        addToWorld(w, loc.getBlockX(), loc.getBlockZ(), dungeon);
    }
    
    public static void createInWorldAsync(CustomDungeon dungeon, Location loc, Random random) {
        AsyncWorldEditor w = new AsyncWorldEditor(loc.getWorld());
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            try {
                createInWorld(w, dungeon, loc, random);
            } catch (IOException ex) {
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
        });
    }
    
    public static Clipboard load(File schematic) throws FileNotFoundException, IOException {
        ClipboardFormat format = ClipboardFormats.findByFile(schematic);
        if(format == null) return null;
        ClipboardReader reader = format.getReader(new FileInputStream(schematic));
        return reader.read();
    }
    
    public static void applyAsync(AsyncWorldEditor world, Location loc, Clipboard clipboard, CustomDungeon dungeon, Random random) {
        BlockVector3 min = clipboard.getMinimumPoint();
        BlockVector3 max = clipboard.getMaximumPoint();
        
         for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                    BlockVector3 sub = BlockVector3.at(x, y, z);
                    BlockState bs = clipboard.getBlock(sub);
                    int xi = x + dungeon.offset[0] + loc.getBlockX();
                    int yi = y + dungeon.offset[1] + loc.getBlockY();
                    int zi = z + dungeon.offset[2] + loc.getBlockZ();
                    if(bs.getBlockType() == BlockTypes.CHEST || bs.getBlockType() == BlockTypes.TRAPPED_CHEST) {
                        Material chest = bs.getBlockType() == BlockTypes.CHEST ? Material.CHEST : Material.TRAPPED_CHEST;
                        world.addLater(new Chest_Later(world, new Coord(xi, yi, zi), random, chest, dungeon));
                    } else if(bs.getBlockType() == BlockTypes.SPAWNER) {
                        world.addLater(new Spawner_Later(world, new Coord(xi, yi, zi), dungeon, random));
                    } else {
                        world.setBlockData(xi, yi, zi, BukkitAdapter.adapt(bs));
                    }
                }
            }
         }
    }
    
    public static void addToWorld(AsyncWorldEditor w, int x, int z, CustomDungeon dungeon) {
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
                                if(ZoneWorld.PHY_BLOCKS.contains(node.bd.getMaterial()))
                                    c.getBlock(pos[0], pos[1], pos[2]).setType(node.bd.getMaterial(), true);
                                else
                                    c.getBlock(pos[0], pos[1], pos[2]).setBlockData(node.bd, false);
                            } else {
                                if(ZoneWorld.PHY_BLOCKS.contains(node.material))
                                    c.getBlock(pos[0], pos[1], pos[2]).setType(node.material, true);
                                else
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
            DungeonGeneratedEvent event = new DungeonGeneratedEvent(chunks0, DungeonType.CustomDungeon, x, z, dungeon.file);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }, eventDelay);
    }
}
