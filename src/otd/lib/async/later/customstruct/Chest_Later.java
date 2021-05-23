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
package otd.lib.async.later.customstruct;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import otd.config.LootNode;
import otd.config.WorldConfig.CustomDungeon;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;

/**
 *
 * @author shadow
 */
public class Chest_Later extends Later {
    
    private final Coord coord;
    private final Random random;
    private final Material chest;
    private final AsyncWorldEditor world;
    private final CustomDungeon dungeon;
    
    public Chest_Later(AsyncWorldEditor world, Coord coord, Random random, Material chest, CustomDungeon dungeon) {
        this.world = world;
        this.coord = coord;
        this.random = random;
        this.chest = chest;
        this.dungeon = dungeon;
    }
    
    public static boolean generate_later(AsyncWorldEditor world, Coord coord, Random random, Material chest, CustomDungeon dungeon) {
        Chest_Later later = new Chest_Later(world, coord, random, chest, dungeon);
        world.addLater(later);
        
        return true;
    }

    @Override
    public Coord getPos() {
        return this.coord;
    }

    @Override
    public void doSomething() {
        
    }

    @Override
    public void doSomethingInChunk(Chunk c) {
        int x = coord.getX() % 16;
        int y = coord.getY();
        int z = coord.getZ() % 16;
        if(x < 0) x = x + 16;
        if(z < 0) z = z + 16;
        Block block = c.getBlock(x, y, z);
        block.setType(chest, true);
        BlockState bs = block.getState();
        if(bs instanceof BlockInventoryHolder) {
            BlockInventoryHolder h = (BlockInventoryHolder) bs;
            Inventory inv = h.getInventory();
            
            for(LootNode node : dungeon.loots) {
                if(random.nextDouble() < node.chance) {
                    int index = random.nextInt(inv.getSize());
                    ItemStack item = node.getItem();
                    int amount;
                    if(node.min == node.max) amount = node.max;
                    else  amount = node.min + random.nextInt(node.max - node.min + 1);
                    item.setAmount(amount);
                    inv.setItem(index, item);
                }
            }
        }
    }
}