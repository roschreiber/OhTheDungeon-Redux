/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.async;

import otd.addon.com.ohthedungeon.storydungeon.generator.FakeGenerator;
import otd.addon.com.ohthedungeon.storydungeon.util.AsyncErrorLogger;
import otd.addon.com.ohthedungeon.storydungeon.util.TaskHolder;
import otd.addon.com.ohthedungeon.storydungeon.world.ZoneConfig;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author shadow_wind
 */
public class Async_Task_Pool {
    private final Map<String, TaskHolder> TASK_POOL = new LinkedHashMap<>();
    private final Map<String, TaskHolder> TASK_RUN_POOL = new LinkedHashMap<>();
    private final int MAX_RUNNING = 1;
    private final JavaPlugin plugin;
    
    public Async_Task_Pool(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if(currentPending() == 0) return;
            if(currentRunning() >= MAX_RUNNING) return;
            
            start();
        }, 1L, 20L);
        
        plugin.getLogger().log(Level.INFO, "{0}[Oh The Dungeons You''ll Go] Create PerPlayerDungeonInstance Task Pool Successful", ChatColor.BLUE);
    }
    
    public List<String> getPending() {
        List<String> res = new ArrayList<>();
        for(Map.Entry<String, TaskHolder> entry : TASK_POOL.entrySet()) res.add(entry.getKey());
        return res;
    }
    
    public List<String> getRunning() {
        List<String> res = new ArrayList<>();
        for(Map.Entry<String, TaskHolder> entry : TASK_RUN_POOL.entrySet()) res.add(entry.getKey());
        return res;
    }
    
    public boolean hasTask(int chunkX, int chunkZ) {
        String key = getKey(chunkX, chunkZ);
        return TASK_POOL.containsKey(key) || TASK_RUN_POOL.containsKey(key);
    }
    
    public Async_Task_Pool addTask(FakeGenerator fakeGenerator, World world, int chunkX, int chunkZ, ZoneConfig zc) {
        TaskHolder t = new TaskHolder(fakeGenerator, world, chunkX, chunkZ, zc);
        String key = getKey(chunkX, chunkZ);
        TASK_POOL.put(key, t);
        return this;
    }
    
    private int currentRunning() {
        return TASK_RUN_POOL.size();
    }
    
    private int currentPending() {
        return TASK_POOL.size();
    }
    
    public static String getKey(int chunkX, int chunkZ) {
        return chunkX + "," + chunkZ;
    }
    
    private void start() {
        if(currentPending() == 0) return;
        if(currentRunning() >= MAX_RUNNING) return;
        
        String key = "";
        TaskHolder holder = null;
        for(Map.Entry<String, TaskHolder> entry : TASK_POOL.entrySet()) {
            key = entry.getKey();
            holder = entry.getValue();
            break;
        }
        if(holder == null) return;
        
        TASK_POOL.remove(key);
        TASK_RUN_POOL.put(key, holder);
//        Bukkit.getLogger().log(Level.SEVERE, key);
        
        final TaskHolder t = holder;
        
        BukkitRunnable t_run = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    while(!t.run()) {
                        try {
                            Thread.sleep(1000L);
                        } catch(InterruptedException ex) {

                        }
                    }
                } catch(Exception ex) {
                    AsyncErrorLogger.normalLog(t.getChunkX(), t.getChunkZ(), AsyncErrorLogger.exceptionToString(ex));
                }
            }
        };
        t_run.runTaskLaterAsynchronously(this.plugin, 1L);
    }
    
    private void kill(String key) {
        TASK_RUN_POOL.remove(key);
    }
    
    public void kill(int cx, int cz) {
        kill(getKey(cx, cz));
    }
}
