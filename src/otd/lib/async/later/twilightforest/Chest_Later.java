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
import java.util.List;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import otd.config.LootNode;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;
import forge_sandbox.twilightforest.TFTreasure;
import forge_sandbox.twilightforest.treasure.Tower_Library;
import forge_sandbox.twilightforest.treasure.Tower_Room;

/**
 *
 * @author shadow
 */
public class Chest_Later extends Later {
    
    private Coord coord;
    private Random random;
    private TFTreasure treasureType;
    private Material chest;
    private AsyncWorldEditor world;
    
    public Chest_Later(AsyncWorldEditor world, Coord coord, Random random, TFTreasure treasureType, Material chest) {
        this.world = world;
        this.coord = coord;
        this.random = random;
        this.treasureType = treasureType;
        this.chest = chest;
    }
    
    public static boolean generate_later(AsyncWorldEditor world, Coord coord, Random random, TFTreasure treasureType, Material chest) {
        Chest_Later later = new Chest_Later(world, coord, random, treasureType, chest);
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
        if(bs instanceof Chest) {
            Chest chest = (Chest) bs;
            Inventory inv = chest.getInventory();
            
            String world_name = c.getWorld().getName();
            boolean builtin= true;
            if(WorldConfig.wc.dict.containsKey(world_name) && !WorldConfig.wc.dict.get(world_name).lich_tower.builtinLoot) {
                builtin = false;
            }
            
            if(builtin) {
                Location loc = new Location(world.getWorld(), coord.getX(), coord.getY(), coord.getZ());
                if(treasureType == TFTreasure.tower_library) {
                    List<ItemStack> items = (new Tower_Library()).getLoots(random, loc);
                    for(ItemStack item : items) {
                        inv.addItem(item);
                    }
                }
                if(treasureType == TFTreasure.tower_room) {
                    List<ItemStack> items = (new Tower_Room()).getLoots(random, loc);
                    for(ItemStack item : items) {
                        inv.addItem(item);
                    }
                }
            }
            
            if(WorldConfig.wc.dict.containsKey(world_name)) {
                SimpleWorldConfig config = WorldConfig.wc.dict.get(world_name);
                for(LootNode node : config.lich_tower.loots) {
                    if(random.nextDouble() <= node.chance) {
                        ItemStack item = node.getItem();
                        int amount;
                        if(node.min == node.max) amount = node.max;
                        else  amount = node.min + random.nextInt(node.max - node.min + 1);
                        item.setAmount(amount);
                        inv.setItem(random.nextInt(inv.getSize()), item);
                    }

                }
            }
        }
        
        world = null;
    }
}
