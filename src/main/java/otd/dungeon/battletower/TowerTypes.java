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
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public enum TowerTypes {
	Null("null", Material.AIR, Material.AIR, Material.AIR, Material.AIR),
	CobbleStone("cobblestone", Material.COBBLESTONE, Material.TORCH, BlockConst.DOUBLE_STONE_SLAB,
			Material.STONE_STAIRS),
	CobbleStoneMossy("cobblestonemossy", Material.MOSSY_COBBLESTONE, Material.TORCH, BlockConst.DOUBLE_STONE_SLAB,
			Material.STONE_STAIRS),
	SandStone("sandstone", Material.SANDSTONE, Material.TORCH, BlockConst.DOUBLE_SANDSTONE_SLAB,
			Material.SANDSTONE_STAIRS),
	Ice("ice", Material.ICE, Material.AIR /* Blocks.GLOWSTONE */, Material.CLAY, Material.OAK_STAIRS), // since when
																										// does
																										// glowstone
																										// melt ice
	SmoothStone("smoothstone", Material.STONE, Material.TORCH, BlockConst.PETRIFIED_OAK_SLAB, Material.STONE_STAIRS),
	Netherrack("netherrack", Material.NETHERRACK, Material.GLOWSTONE, Material.SOUL_SAND, Material.NETHER_BRICK_STAIRS),
	Jungle("jungle", Material.MOSSY_COBBLESTONE, Material.COBWEB, Material.DIRT, Material.JUNGLE_STAIRS);

	private final Material wallBlock;
	private final Material lightBlock;
	private final BlockData floorBlock;
	private final Material stairBlock;
	private final String typeName;

	TowerTypes(String t, Material a, Material b, Material c, Material e) {
		this.wallBlock = a;
		this.lightBlock = b;
		this.floorBlock = Bukkit.createBlockData(c);
		this.stairBlock = e;
		this.typeName = t;
	}

	TowerTypes(String t, Material a, Material b, BlockData c, Material e) {
		this.wallBlock = a;
		this.lightBlock = b;
		this.floorBlock = c;
		this.stairBlock = e;
		this.typeName = t;
	}

	Material getWallBlockMaterial() {
		return wallBlock;
	}

	Material getLightBlockMaterial() {
		return lightBlock;
	}

	BlockData getFloorBlockData() {
		return floorBlock;
	}

	Material getStairBlockMaterial() {
		return stairBlock;
	}

	public String getName() {
		return this.typeName;
	}
}
