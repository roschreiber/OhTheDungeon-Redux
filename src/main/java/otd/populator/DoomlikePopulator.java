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
package otd.populator;

import static forge_sandbox.jaredbgreat.dldungeons.builder.Builder.placeDungeonAsync;

import java.util.Random;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.World;

import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.util.AsyncLog;

/**
 *
 * @author
 */
public class DoomlikePopulator extends IPopulator {
	@Override
	public Set<String> getBiomeExclusions(World world) {
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
		return swc.doomlike.biomeExclusions;
	}

	@Override
	public boolean generateDungeon(World world, Random random, Chunk chunk) {
		boolean res;
		try {
			res = placeDungeonAsync(random, chunk.getX(), chunk.getZ(), world);
		} catch (Throwable e) {
			return false;
		}
		if (res)
			AsyncLog.logMessage(
					"[Doomlike Dungeon @ " + world.getName() + "] x=" + chunk.getX() * 16 + ", z=" + chunk.getZ() * 16);
		return res;
	}
}