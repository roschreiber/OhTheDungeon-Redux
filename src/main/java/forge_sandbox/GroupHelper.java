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
package forge_sandbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

/**
 *
 * @author
 */
public class GroupHelper {
	public final static List<Material> CARPETS;
	static {
		CARPETS = new ArrayList<>();
		for (Material material : Material.values()) {
			if (material.toString().toUpperCase().contains("CARPET")) {
				CARPETS.add(material);
			}
		}
	}

	public final static BlockData OAK_LOG_Y = Bukkit.createBlockData("minecraft:oak_log[axis=y]");
	public final static Map<BlockFace, BlockData> CHEST;
	static {
		CHEST = new HashMap<>();
		CHEST.put(BlockFace.NORTH, Bukkit.createBlockData("minecraft:chest[facing=north,type=single]"));
		CHEST.put(BlockFace.SOUTH, Bukkit.createBlockData("minecraft:chest[facing=south,type=single]"));
		CHEST.put(BlockFace.EAST, Bukkit.createBlockData("minecraft:chest[facing=east,type=single]"));
		CHEST.put(BlockFace.WEST, Bukkit.createBlockData("minecraft:chest[facing=west,type=single]"));
	}
}
