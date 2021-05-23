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
package otd.lib.async.later.doomlike;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import otd.Main;
import otd.lib.api.SpawnerDecryAPI;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;

/**
 *
 * @author
 */
public class Spawner_Later extends Later {
    private Coord coords;
    private AsyncWorldEditor world;
    private EntityType mob;
    
    private static boolean generate(Chunk chunk, Coord pos, EntityType mob) {

        int x = pos.getX() % 16;
        int y = pos.getY();
        int z = pos.getZ() % 16;
        if(x < 0) x = x + 16;
        if(z < 0) z = z + 16;            
        Block b = chunk.getBlock(x, y, z);
        CreatureSpawner  sp = (CreatureSpawner) b.getState();
        sp.setSpawnedType(mob);
        sp.update();
        SpawnerDecryAPI.setSpawnerDecry(b, Main.instance);
        
        return true;
    }
    
    public static boolean generate_later(AsyncWorldEditor world, Coord pos, EntityType mob) {
        Spawner_Later later = new Spawner_Later();
        later.world = world;
        later.coords = new Coord(pos.getX(), pos.getY(), pos.getZ());
        later.mob = mob;
        
        world.addLater(later);
        
        return true;
    }

    @Override
    public Coord getPos() {
        return this.coords;
    }

    @Override
    public void doSomething() {
    }

    @Override
    public void doSomethingInChunk(Chunk c) {
        Spawner_Later.generate(c, coords, mob);
        this.world = null;
    }
}

