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

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.redstone.Dispenser;
import org.bukkit.Chunk;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author
 */
public class Dispenser_Later extends Later {
    
    private IWorldEditor editor;
    private Coord pos;
    private int slot;
    private ItemStack item;
    
    @Override
    public Coord getPos() {
        return pos;
    }
    
    public Dispenser_Later(IWorldEditor editor, Coord pos, int slot, ItemStack item) {
        this.editor = editor;
        this.pos = pos;
        this.slot = slot;
        this.item = item;
    }
    
    @Override
    public void doSomething() {
        Dispenser.add_later(editor, pos, slot, item);
    }

    @Override
    public void doSomethingInChunk(Chunk c) {
        Dispenser.add_later_chunk(c, pos, slot, item);
    }
}
