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

import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;

/**
 *
 * @author shadow
 */
public class WorldGenOptimization implements Listener {
    @EventHandler
    public void onWorldInit(WorldInitEvent e){
         if(e.getWorld().getName().equalsIgnoreCase(WorldDefine.WORLD_NAME)){
               e.getWorld().setKeepSpawnInMemory(false);
               e.getWorld().setTime(6000);
               e.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
               e.getWorld().setGameRule(GameRule.DO_WEATHER_CYCLE, false);
               e.getWorld().setDifficulty(Difficulty.HARD);
               e.getWorld().setSpawnFlags(true, false);
         }
    }
    @EventHandler
    public void onWorldLoad(WorldLoadEvent e){
         if(e.getWorld().getName().equalsIgnoreCase(WorldDefine.WORLD_NAME)){
               e.getWorld().setKeepSpawnInMemory(false);
               e.getWorld().setTime(6000);
               e.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
               e.getWorld().setGameRule(GameRule.DO_WEATHER_CYCLE, false);
               e.getWorld().setDifficulty(Difficulty.HARD);
               e.getWorld().setSpawnFlags(true, false);
         }
    }
}
