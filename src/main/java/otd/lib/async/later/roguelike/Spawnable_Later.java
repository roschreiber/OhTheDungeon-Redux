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

import java.util.Random;

import org.bukkit.Chunk;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;

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
		this.pos = new Coord(pos);
		this.editor = editor;
		this.rand = rand;
		this.cursor = cursor;
		this.level = level;
	}

	@Override
	public void doSomething() {
		Coord npos = new Coord(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
		spawnable.generate_later(npos, editor, rand, cursor, level);
	}

	@Override
	public void doSomethingInChunk(Chunk c) {
		Coord npos = new Coord(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
		spawnable.generate_later_chunk(c, npos, editor, rand, cursor, level);
	}
}
