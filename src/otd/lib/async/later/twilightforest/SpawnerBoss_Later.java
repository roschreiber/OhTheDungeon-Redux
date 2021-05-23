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
import org.bukkit.block.Block;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;
import forge_sandbox.twilightforest.TFBoss;
import forge_sandbox.twilightforest.structures.lichtower.boss.Lich;

/**
 *
 * @author shadow
 */
public class SpawnerBoss_Later extends Later {
    private AsyncWorldEditor world;
    private final Coord loc;
    private final TFBoss boss;
    
    public SpawnerBoss_Later(final AsyncWorldEditor world, final Coord loc, final TFBoss boss) {
        this.world = world;
        this.loc = loc;
        this.boss = boss;
    }
    
    public static boolean generate_later(final AsyncWorldEditor world, final Coord loc, final TFBoss boss) {
        SpawnerBoss_Later later = new SpawnerBoss_Later(world, loc, boss);
        world.addLater(later);
        
        return true;
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
        
        if(boss == TFBoss.Lich) {
            Lich.createSpawner(block);
        }
        this.world = null;
    }

    @Override
    public Coord getPos() {
        return this.loc;
    }
}
