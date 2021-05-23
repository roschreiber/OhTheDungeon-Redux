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
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Furnace;
import org.bukkit.Chunk;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author
 */
public class Furnace_Later extends Later {
    
    private IWorldEditor editor;
    private ItemStack fuel;
    private Coord pos;
    
    @Override
    public Coord getPos() {
        return pos;
    }
    
    public Furnace_Later(IWorldEditor editor, ItemStack fuel, Coord pos) {
        this.editor = editor;
        this.fuel = fuel;
        this.pos = pos;
    }
    
    @Override
    public void doSomething() {
        Furnace.generate_later(editor, fuel, pos);
    }

    @Override
    public void doSomethingInChunk(Chunk c) {
        Furnace.generate_later_chunk(c, fuel, pos);
    }
}
