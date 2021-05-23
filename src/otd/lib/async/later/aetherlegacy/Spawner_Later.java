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
package otd.lib.async.later.aetherlegacy;

import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import java.util.Random;
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
 * @author
 */
public class Spawner_Later extends Later {
    private Coord coords;
    private AsyncWorldEditor world;
    private Random random;
    private EntityType mob;
    
    private static boolean generate(final Chunk chunk, final Random random, Coord pos, EntityType mob) {

        int x = pos.getX() % 16;
        int y = pos.getY();
        int z = pos.getZ() % 16;
        if(x < 0) x = x + 16;
        if(z < 0) z = z + 16;            

        Block block = chunk.getBlock(x, y, z);
        block.setType(Material.SPAWNER);
        CreatureSpawner tileentitymobspawner = ((CreatureSpawner)block.getState());
        tileentitymobspawner.setSpawnedType(mob);
        tileentitymobspawner.update();
        SpawnerDecryAPI.setSpawnerDecry(block, Main.instance);
        
        return true;
    }
    
    public static boolean generate_later(final AsyncWorldEditor world, final Random random, ICoords coords, EntityType mob) {
        Spawner_Later later = new Spawner_Later();
        later.world = world;
        later.random = random;
        later.coords = new Coord(coords.getX(), coords.getY(), coords.getZ());
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
        Spawner_Later.generate(c, random, coords, mob);
    }
}
