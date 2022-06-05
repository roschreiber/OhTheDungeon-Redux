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

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

import forge_sandbox.greymerk.roguelike.treasure.TreasureChest;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import otd.api.event.ChestEvent;
import otd.world.DungeonType;

/**
 *
 * @author
 */
public class Chest_Generate_Later extends Later {

	private TreasureChest tc;
	private IWorldEditor editor;
	private Coord pos;

	@Override
	public Coord getPos() {
		return pos;
	}

	public Chest_Generate_Later(TreasureChest tc, IWorldEditor editor, Coord pos) {
		this.tc = tc;
		this.editor = editor;
		this.pos = new Coord(pos);
	}

	@Override
	public void doSomething() {
		Coord npos = new Coord(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
		tc.generate_later(editor, npos);
		callEvent(editor, npos);
	}

	@Override
	public void doSomethingInChunk(Chunk c) {
		Coord npos = new Coord(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
		tc.generate_later_chunk(c, npos);
		callEvent(editor, npos);
	}

	private static void callEvent(IWorldEditor editor, Coord pos) {
		Location loc = new Location(editor.getWorld(), pos.getX(), pos.getY(), pos.getZ());
		ChestEvent event = new ChestEvent(DungeonType.Roguelike, "", loc);
		Bukkit.getServer().getPluginManager().callEvent(event);
	}
}
