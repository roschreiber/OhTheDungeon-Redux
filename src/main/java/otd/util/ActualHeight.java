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
package otd.util;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;

/**
 *
 * @author
 */
public class ActualHeight {

	private static Set<Material> set;
	static {
		set = new HashSet<>();
		set.add(Material.ACACIA_LOG);
		set.add(Material.BIRCH_LOG);
		set.add(Material.DARK_OAK_LOG);
		set.add(Material.JUNGLE_LOG);
		set.add(Material.OAK_LOG);
		set.add(Material.SPRUCE_LOG);

		set.add(Material.ACACIA_LEAVES);
		set.add(Material.BIRCH_LEAVES);
		set.add(Material.DARK_OAK_LEAVES);
		set.add(Material.JUNGLE_LEAVES);
		set.add(Material.OAK_LEAVES);
		set.add(Material.SPRUCE_LEAVES);

		set.add(Material.VINE);
		set.add(Material.BAMBOO);

		set.add(Material.AIR);
	}

	public static Location getHeight(Location loc) {
		World world = loc.getWorld();
		Block block = world.getHighestBlockAt(loc);
		int top = 50;
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
			top = swc.worldParameter.bottom + 45;
		}

		while (block.getLocation().getY() > top && set.contains(block.getType())) {
			block = block.getRelative(BlockFace.DOWN);
		}
		return block.getLocation();
	}
}
