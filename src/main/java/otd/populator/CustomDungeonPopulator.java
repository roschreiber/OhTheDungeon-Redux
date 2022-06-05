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

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;

import otd.MultiVersion;
import otd.config.WorldConfig;
import otd.struct.SchematicLoader;
import otd.util.AsyncLog;

/**
 *
 * @author shadow
 */
public class CustomDungeonPopulator extends IPopulator {

	@Override
	public boolean generateDungeon(World world, Random random, Chunk chunk) {
		int x = chunk.getX() * 16 + 7;
		int z = chunk.getZ() * 16 + 7;

		Biome biome = MultiVersion.getBiome(world, x, z);

		WorldConfig.CustomDungeon dungeon = SchematicLoader.getRandomDungeon(world, biome.toString());
		if (dungeon != null) {
			SchematicLoader.createInWorldAsync(dungeon, world, x, z, random);
			AsyncLog.logMessage("[Custom Dungeon " + dungeon.file + " @ " + world.getName() + "] x=" + x + ", z=" + z);
		}

		return dungeon != null;
	}

	@Override
	public Set<String> getBiomeExclusions(World world) {
		return new HashSet<>();
	}
}
