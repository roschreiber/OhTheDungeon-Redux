/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.world;

import otd.addon.com.ohthedungeon.storydungeon.generator.FakeGenerator;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import otd.lib.DungeonWorldManager;

/**
 *
 * @author shadow_wind
 */
public class WorldManager {    
    public static World createDungeonWorld(FakeGenerator generator) {
        String WORLD_NAME = DungeonWorldManager.WORLD_NAME;
        
        WorldCreator wc = new WorldCreator(WORLD_NAME);
        wc.type(WorldType.NORMAL);
        wc.generator(generator);
        
        World world = wc.createWorld();
        world.setDifficulty(Difficulty.HARD);
        world.setSpawnFlags(true, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setTime(6000);
        
        return world;
    }
    
//    private static void startTest(FakeGenerator generator) {
//        int[] pos = ZoneUtils.getNextZone();
//        ZoneConfig zc = new ZoneConfig(Biome.SUNFLOWER_PLAINS, "AlmostFlatlands", "Sakura", ZoneDungeonType.ROGUELIKE, "Mountain", "shadow");
//        generator.generateZone(generator.getDungeonWorld(), pos[0], pos[1], zc);
//    }

}
