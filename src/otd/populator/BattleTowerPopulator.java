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
package otd.populator;

import otd.dungeon.battletower.BattleTower;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import otd.Main;
import otd.api.event.DungeonGeneratedEvent;
import otd.util.AsyncLog;
import otd.lib.BiomeDictionary;
import otd.lib.BiomeDictionary.Type;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.world.DungeonType;

/**
 *
 * @author
 */
public class BattleTowerPopulator implements IPopulator {
    @Override
    public Set<String> getBiomeExclusions(World world) {
        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
        return swc.battletower.biomeExclusions;
    }
    @Override
    public boolean generateDungeon(World world, Random random, Chunk chunk) {
        
        int rx = chunk.getX() * 16 + 7;
        int rz = chunk.getZ() * 16 + 7;
        int ry = world.getHighestBlockYAt(rx, rz);
        
        int choose;
        
        if(random.nextInt(100) == 0) choose = 6;
        else {
            Biome b = world.getBiome(rx, rz);
            Set<Integer> typelist = new HashSet<>();
            Set<Type> set = BiomeDictionary.getTypes(b);
            if(set.contains(Type.SANDY)) typelist.add(3);
            if(set.contains(Type.COLD)) typelist.add(4);
            if(set.contains(Type.SNOWY)) typelist.add(4);
            if(set.contains(Type.MOUNTAIN)) typelist.add(5);
            if(set.contains(Type.HILLS)) typelist.add(5);
            if(set.contains(Type.WET)) typelist.add(2);
            if(set.contains(Type.OCEAN)) typelist.add(2);
            if(set.contains(Type.RIVER)) typelist.add(2);
            if(set.contains(Type.JUNGLE)) typelist.add(7);
            if(set.contains(Type.PLAINS)) typelist.add(1);
            
            int size = typelist.size();
            if(size == 0) return false;
            int item = random.nextInt(size); // In real life, the Random object should be rather more shared than this
            int i = 0;
            Integer c = null;
            for(Integer obj : typelist)
            {
                if (i == item)
                    c = obj;
                i++;
            }
            if(c == null) c = 1;
            choose = c;
        }
        
        boolean under = false;
        if(ry > 50) under = random.nextBoolean();
        
        BattleTower.generate(world, random, rx, ry, rz, choose, under);
        
        Set<int[]> chunks0 = new HashSet<>();
        int cx = chunk.getX(), cz = chunk.getZ();
        chunks0.add(new int[] {cx-1, cz-1});
        chunks0.add(new int[] {cx-1, cz});
        chunks0.add(new int[] {cx-1, cz+1});
        chunks0.add(new int[] {cx, cz-1});
        chunks0.add(new int[] {cx, cz});
        chunks0.add(new int[] {cx, cz+1});
        chunks0.add(new int[] {cx+1, cz-1});
        chunks0.add(new int[] {cx+1, cz});
        chunks0.add(new int[] {cx+1, cz+1});

        Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            DungeonGeneratedEvent event = new DungeonGeneratedEvent(chunks0, DungeonType.BattleTower, rx, rz);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }, 1L);
        
        AsyncLog.logMessage("[BattleTower Dungeon @ " + world.getName() + "] x=" + rx + ", z=" + rz);
        return true;
    }
}
