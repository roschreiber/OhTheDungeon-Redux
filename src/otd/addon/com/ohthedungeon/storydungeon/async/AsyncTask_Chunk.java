/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.async;

import otd.addon.com.ohthedungeon.storydungeon.util.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import otd.lib.async.io.papermc.lib.PaperLib;

/**
 *
 * @author shadow_wind
 */
public class AsyncTask_Chunk implements AsyncTask {
    private AsyncChunk chunk = null;
    private Biome biome = Biome.THE_VOID;
    
    private int chunkX = 0, chunkZ = 0;
    
    public AsyncTask_Chunk(AsyncChunk chunk, Biome biome, int chunkX, int chunkZ) {
        this.chunk = chunk;
        this.biome = biome;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }
    
    @Override
    public boolean doTask(World world, Random random) {
        List<Node> list = null;
        if(!this.isEmpty()) {
            boolean b = false;
            int r_key = -1;
            for(Map.Entry<Integer, List<Node>> entry : flat_map.entrySet()) {
                r_key = entry.getKey();
                list = entry.getValue();
                b = true;
                break;
            }

            if(!b) return true;

            this.remove(r_key);
        }
        final List<Node> cc = list;
        PaperLib.getChunkAtAsync(world, chunkX, chunkZ, true).thenAccept( (Chunk c) ->
	{
            if (c != null && cc != null) {
                Chunk cs = c;
                for(Node n : cc) {
                    int[] pos = n.getPos();
                    cs.getBlock(pos[0], pos[1], pos[2]).setType(n.getMaterial(), false);
                }
            }
        });
        return this.isEmpty();

//        Chunk cs = world.getChunkAt(chunkX, chunkZ);
//        if(!this.isEmpty()) {
//            
//            int r_key = -1;
//            List<Node> list = null;
//            
//            for(Map.Entry<Integer, List<Node>> entry : flat_map.entrySet()) {
//                r_key = entry.getKey();
//                list = entry.getValue();
//                break;
//            }
//            
//            if(list == null) return true;
//            
//            this.remove(r_key);
//            for(Node n : list) {
//                int[] pos = n.getPos();
//                cs.getBlock(pos[0], pos[1], pos[2]).setType(n.getMaterial(), false);
//            }
//        }
//        return this.isEmpty();
    }
    
    public int getX() {
        return this.chunkX;
    }
    public AsyncTask_Chunk setX(int x) {
        this.chunkX = x;
        return this;
    }
    public int getZ() {
        return this.chunkZ;
    }
    public AsyncTask_Chunk setZ(int z) {
        this.chunkZ = z;
        return this;
    }
    public Biome getBiome() {
        return this.biome;
    }
    public AsyncTask_Chunk setBiome(Biome biome) {
        this.biome = biome;
        return this;
    }
    public AsyncChunk getAsyncChunk() {
        return this.chunk;
    }
    public AsyncTask_Chunk setAsyncChunk(AsyncChunk chunk) {
        this.chunk = chunk;
        return this;
    } 
    
    private Map<Integer, List<Node>> flat_map;
    
    public void flat(int n) {
        flat_map = new HashMap<>();
        int k = chunk.getSize() / n;
        for(int i = 0; i <= n - 1; i++) {
            int count = 0;
            List<Node> nodes = new ArrayList<>();
            List<Integer> r_key = new ArrayList<>();
            
            for(Map.Entry<Integer, Material> entry : chunk.getMap().entrySet()) {
                int key = entry.getKey();
                r_key.add(key);
                nodes.add(new Node(AsyncChunk.posToXYZ(key), entry.getValue()));
                count++;
                if(i < n - 1 && count >= k) break;
            }
            for(int key : r_key) {
                chunk.remove(key);
            }
            flat_map.put(i, nodes);
//            Bukkit.getLogger().log(Level.SEVERE, i + ":" + chunk.getMap().size());
//            Bukkit.getLogger().log(Level.SEVERE, i + ":" + nodes.size());
//            Bukkit.getLogger().log(Level.SEVERE, i + ":" + nodes.get(0).getMaterial() + " " + nodes.get(nodes.size() - 1).getMaterial());
        }
    }
    
    public boolean isEmpty() {
        return flat_map.isEmpty();
    }
    
    public Set<Map.Entry<Integer, List<Node>>> getEntrySet() {
        return flat_map.entrySet();
    }
    
    public AsyncTask_Chunk remove(int key) {
        flat_map.remove(key);
        return this;
    }
}
