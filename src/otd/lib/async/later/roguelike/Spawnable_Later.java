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
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;
import java.util.Random;
import org.bukkit.Chunk;

/**
 *
 * @author
 */
public class Spawnable_Later extends Later {
    
    private Coord pos;
    private IWorldEditor editor;
    private Random rand;
    private Coord cursor;
    private int level;
    private Spawnable spawnable;
    
    @Override
    public Coord getPos() {
        return pos;
    }
    
    public Spawnable_Later(Spawnable spawnable, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level) {
        this.spawnable = spawnable;
        this.pos = pos;
        this.editor = editor;
        this.rand = rand;
        this.cursor = cursor;
        this.level = level;
    }
    
    @Override
    public void doSomething() {
        spawnable.generate_later(pos, editor, rand, cursor, level);
    }

    @Override
    public void doSomethingInChunk(Chunk c) {
        spawnable.generate_later_chunk(c, pos, editor, rand, cursor, level);
    }
}
