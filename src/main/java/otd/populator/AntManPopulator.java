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

import java.util.Random;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import forge_sandbox.com.someguyssoftware.dungeons2.BukkitDungeonGenerator;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.util.ActualHeight;
import otd.util.AsyncLog;

/**
 *
 * @author
 */
public class AntManPopulator extends IPopulator {
	@Override
	public Set<String> getBiomeExclusions(World world) {
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
		return swc.ant_man_dungeon.biomeExclusions;
	}

	@Override
	public boolean generateDungeon(World world, Random random, Chunk chunk) {

		int rx = chunk.getX() * 16 + 7;
		int rz = chunk.getZ() * 16 + 7;
		Location loc = world.getHighestBlockAt(rx, rz).getLocation();
		loc = ActualHeight.getHeight(loc);
		int ry = loc.getBlockY();
//        Bukkit.getLogger().log(Level.SEVERE, ""+ry);
		int bottom = 5;
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			bottom = WorldConfig.wc.dict.get(world.getName()).worldParameter.bottom;
		}
		if (ry < bottom + 45)
			return false;
		try {
			BukkitDungeonGenerator.generate(world, new Location(world, rx, ry, rz), new Random());
		} catch (Exception ex) {
			return false;
		}
		AsyncLog.logMessage("[Ant Man Dungeon @ " + world.getName() + "] x=" + rx + ", z=" + rz);
		return true;
	}
}
