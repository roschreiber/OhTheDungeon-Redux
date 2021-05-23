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
package otd.lib.async.later.twilightforest;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import otd.Main;
import otd.lib.api.SpawnerDecryAPI;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;

/**
 *
 * @author shadow
 */
public class Spawner_Later extends Later {
    private AsyncWorldEditor world;
    private final Coord loc;
    private final EntityType type;
    
    public Spawner_Later(AsyncWorldEditor world, Coord loc, EntityType type) {
        this.world = world;
        this.loc = loc;
        this.type = type;
    }
    
    public static boolean generate_later(final AsyncWorldEditor world, final Coord loc, final EntityType type) {
        Spawner_Later later = new Spawner_Later(world, loc, type);
        world.addLater(later);
        
        return true;
    }

    @Override
    public Coord getPos() {
        return this.loc;
    }

    @Override
    public void doSomething() {
        
    }

    @Override
    public void doSomethingInChunk(Chunk c) {
        int x = loc.getX() % 16;
        int y = loc.getY();
        int z = loc.getZ() % 16;
        if(x < 0) x = x + 16;
        if(z < 0) z = z + 16;

        Block block = c.getBlock(x, y, z);
        block.setType(Material.SPAWNER);
        CreatureSpawner tileentitymobspawner = ((CreatureSpawner)block.getState());
        tileentitymobspawner.setSpawnedType(type);
        tileentitymobspawner.update();
        SpawnerDecryAPI.setSpawnerDecry(block, Main.instance);
        
        this.world = null;
    }
}
