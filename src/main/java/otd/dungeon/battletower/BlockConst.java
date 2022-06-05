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
package otd.dungeon.battletower;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

public class BlockConst {
	public static BlockData DOUBLE_STONE_SLAB = Bukkit.createBlockData("minecraft:stone_slab[type=double]");
	public static BlockData DOUBLE_SANDSTONE_SLAB = Bukkit.createBlockData("minecraft:sandstone_slab[type=double]");
	public static BlockData PETRIFIED_OAK_SLAB = Bukkit.createBlockData("minecraft:petrified_oak_slab[type=double]");
	public static BlockData TORCH = Bukkit.createBlockData("minecraft:wall_torch[facing=south]");
}
