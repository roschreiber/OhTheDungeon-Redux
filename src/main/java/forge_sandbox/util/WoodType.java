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
package forge_sandbox.util;

import org.bukkit.Material;

/**
 *
 * @author
 */
public enum WoodType {
	OAK, BIRCH, SPRUCE, DARK_OAK, JUNGLE, ACACIA;

	public static Material getDoor(WoodType type) {
		if (type == OAK)
			return Material.OAK_DOOR;
		if (type == BIRCH)
			return Material.BIRCH_DOOR;
		if (type == SPRUCE)
			return Material.SPRUCE_DOOR;
		if (type == DARK_OAK)
			return Material.DARK_OAK_DOOR;
		if (type == JUNGLE)
			return Material.JUNGLE_DOOR;
		if (type == ACACIA)
			return Material.ACACIA_DOOR;
		return Material.AIR;
	}

	public static Material getFence(WoodType type) {
		if (type == OAK)
			return Material.OAK_FENCE;
		if (type == BIRCH)
			return Material.BIRCH_FENCE;
		if (type == SPRUCE)
			return Material.SPRUCE_FENCE;
		if (type == DARK_OAK)
			return Material.DARK_OAK_FENCE;
		if (type == JUNGLE)
			return Material.JUNGLE_FENCE;
		if (type == ACACIA)
			return Material.ACACIA_FENCE;
		return Material.AIR;
	}

	public static Material getStair(WoodType type) {
		if (type == OAK)
			return Material.OAK_STAIRS;
		if (type == BIRCH)
			return Material.BIRCH_STAIRS;
		if (type == SPRUCE)
			return Material.SPRUCE_STAIRS;
		if (type == DARK_OAK)
			return Material.DARK_OAK_STAIRS;
		if (type == JUNGLE)
			return Material.JUNGLE_STAIRS;
		if (type == ACACIA)
			return Material.ACACIA_STAIRS;
		return Material.AIR;
	}

	public static Material getSlab(WoodType type) {
		if (type == OAK)
			return Material.OAK_SLAB;
		if (type == BIRCH)
			return Material.BIRCH_SLAB;
		if (type == SPRUCE)
			return Material.SPRUCE_SLAB;
		if (type == DARK_OAK)
			return Material.DARK_OAK_SLAB;
		if (type == JUNGLE)
			return Material.JUNGLE_SLAB;
		if (type == ACACIA)
			return Material.ACACIA_SLAB;
		return Material.AIR;
	}

	public static Material getPlanks(WoodType type) {
		if (type == OAK)
			return Material.OAK_PLANKS;
		if (type == BIRCH)
			return Material.BIRCH_PLANKS;
		if (type == SPRUCE)
			return Material.SPRUCE_PLANKS;
		if (type == DARK_OAK)
			return Material.DARK_OAK_PLANKS;
		if (type == JUNGLE)
			return Material.JUNGLE_PLANKS;
		if (type == ACACIA)
			return Material.ACACIA_PLANKS;
		return Material.AIR;
	}
}
