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
package otd.lib;

import org.bukkit.World;

/**
 *
 * @author
 */
public class DungeonWorldManager {
//    public static FakeGenerator generator = new FakeGenerator();
    public static World world;
    public final static String WORLD_NAME = "otd_dungeon";
//    public static void createDungeonWorld() {
//        WorldCreator wc = new WorldCreator(WORLD_NAME);
//        wc.type(WorldType.NORMAL);
//        wc.generator(generator);
//        
//        world = wc.createWorld();
//        world.setDifficulty(Difficulty.PEACEFUL);
//        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
//        world.setTime(6000);
//        
//        FakeGenerator.setDefaultWorld(world);
//        FakeGenerator.registerAsyncChunkTask();
//    }
//    
//    public static void startTest() {
//        int[] pos = ZoneUtils.getNextZone();
//        ZoneConfig zc = new ZoneConfig(Biome.FOREST, "Nordic", "Oak", ZoneDungeonType.ROGUELIKE);
//        FakeGenerator.generateZone(world, pos[0], pos[1], zc);
//    }
}
