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
package otd.lib.async.later.roguelike;

import forge_sandbox.greymerk.roguelike.treasure.TreasureChest;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import org.bukkit.Chunk;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author
 */
public class Chest_SetRandomSlot_Later extends Later {
    
    private TreasureChest tc;
    private ItemStack item;
    private Coord pos;
    
    @Override
    public Coord getPos() {
        return pos;
    }
    
    public Chest_SetRandomSlot_Later(TreasureChest tc, ItemStack item, Coord pos) {
        this.tc = tc;
        this.item = item;
        this.pos = pos;
    }
    
    @Override
    public void doSomething() {
        this.tc.setRandomEmptySlot_later(item);
    }

    @Override
    public void doSomethingInChunk(Chunk c) {
        this.tc.setRandomEmptySlot_later(item);
    }
}