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
package otd.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import otd.Main;

/**
 *
 * @author shadow
 */
public class SpawnerOpt implements Listener {
    
    private static Map<UUID, int[]> cache = new HashMap<>();
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        cache.remove(e.getPlayer().getUniqueId());
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        playerMove(e.getFrom(), e.getTo(), e.getPlayer());
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        Player p = e.getPlayer();
        
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            playerMove(from, to, p);
        }, 8L);
    }
    
    public void playerMove(Location lfrom, Location lto, Player p) {
        Chunk from = lfrom.getChunk();
        Chunk to = lto.getChunk();
        
        if(from.getWorld() != to.getWorld() || from.getX() != to.getX() || from.getZ() != to.getZ()) {
            if(!cache.containsKey(p.getUniqueId())) {
                optimizeSpawner(p);
            } else {
                int[] pos = cache.get(p.getUniqueId());
                if(to.getX() != pos[0] || to.getZ() != pos[1]) {
                    optimizeSpawner(p);
                }
            }
            
            cache.put(p.getUniqueId(), new int[] {to.getX(), to.getZ()});
        }
    }
    
    private static void optimizeSpawner(Player p) {
        Map<String, List<Location>> map = new LinkedHashMap<>();
        for(Chunk chunk : getSurroundingChunks(p, p.getWorld().getViewDistance() * 16)) {
            String c = chunk.getX() + "," + chunk.getZ();
            for (BlockState state : chunk.getTileEntities()) {
                if(state.getType() == Material.SPAWNER) {
                    if(!map.containsKey(c)) map.put(c, new ArrayList<>());
                    List<Location> list = map.get(c);
                    list.add(state.getLocation());
                }
            }
        }
        
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            for(Map.Entry<String, List<Location>> entry : map.entrySet()) {
                List<Location> locs = entry.getValue();
                for(Location loc : locs) {
                    p.sendBlockChange(loc, Material.GLASS, (byte)0);
                    p.sendBlockChange(loc, Material.SPAWNER, (byte)0);
                }
            }
        });
    }
    
    private static Set<Chunk> getSurroundingChunks(Player p, int radius) {
        Chunk chunk = p.getLocation().getChunk();
        radius = radius / 16 + 1;
        Set<Chunk> chunks = new LinkedHashSet<>();
        World world = chunk.getWorld();
        int cX = chunk.getX();
        int cZ = chunk.getZ();
        for (int x = radius; x >= 0; x--) {
          for (int z = radius; z >= 0; z--) {
            chunks.add(world.getChunkAt(cX + x, cZ + z));
            chunks.add(world.getChunkAt(cX - x, cZ - z));
            chunks.add(world.getChunkAt(cX + x, cZ - z));
            chunks.add(world.getChunkAt(cX - x, cZ + z));
            chunks.add(world.getChunkAt(cX + x, cZ));
            chunks.add(world.getChunkAt(cX - x, cZ));
            chunks.add(world.getChunkAt(cX, cZ + z));
            chunks.add(world.getChunkAt(cX, cZ - z));
          } 
        } 
        return chunks;
    }
}
