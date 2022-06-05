package forge_sandbox.greymerk.roguelike.worldgen.blocks;

//import net.minecraft.block.Block;

import org.bukkit.Material;

//import net.minecraft.init.Blocks;

public enum StairType {

	COBBLE, STONEBRICK, BRICK, SANDSTONE, RED_SANDSTONE, QUARTZ, NETHERBRICK, OAK, SPRUCE, BIRCH, JUNGLE, ACACIA,
	DARKOAK, PURPUR;

	public static Material getBlock(StairType type) {
		switch (type) {
		case COBBLE:
			return Material.STONE_STAIRS;
		case STONEBRICK:
			return Material.STONE_BRICK_STAIRS;
		case BRICK:
			return Material.BRICK_STAIRS;
		case SANDSTONE:
			return Material.SANDSTONE_STAIRS;
		case RED_SANDSTONE:
			return Material.RED_SANDSTONE_STAIRS;
		case QUARTZ:
			return Material.QUARTZ_STAIRS;
		case NETHERBRICK:
			return Material.NETHER_BRICK_STAIRS;
		case OAK:
			return Material.OAK_STAIRS;
		case SPRUCE:
			return Material.SPRUCE_STAIRS;
		case BIRCH:
			return Material.BIRCH_STAIRS;
		case JUNGLE:
			return Material.JUNGLE_STAIRS;
		case ACACIA:
			return Material.ACACIA_STAIRS;
		case DARKOAK:
			return Material.DARK_OAK_STAIRS;
		case PURPUR:
			return Material.PURPUR_STAIRS;
		default:
			return Material.STONE_STAIRS;
		}
	}
}
