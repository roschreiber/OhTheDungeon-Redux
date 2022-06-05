package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockPlanks;
//import net.minecraft.block.BlockSlab;
//import net.minecraft.block.BlockStoneSlab;
//import net.minecraft.block.BlockWoodSlab;
//import net.minecraft.init.Blocks;

public enum Slab {

	STONE, STONEBRICK, COBBLE, BRICK, NETHERBRICK, QUARTZ, LEGACY_OAK, SANDSTONE, SANDSTONE_RED, OAK, SPRUCE, BIRCH,
	JUNGLE, ACACIA, DARKOAK;

	public static MetaBlock get(Slab type, boolean upsideDown, boolean full, boolean seamless) {

		MetaBlock slab = new MetaBlock(getBaseSlab(type));

		if (!full && upsideDown) {
			org.bukkit.block.data.type.Slab state = (org.bukkit.block.data.type.Slab) slab.getState();
			state.setType(org.bukkit.block.data.type.Slab.Type.TOP);
			slab.setState(state);
		}

//		if(full && seamless){
//			slab.withProperty(BlockStoneSlab.SEAMLESS, true);
//		}

		return slab;

	}

	public static MetaBlock get(Slab type) {
		return get(type, false, false, false);
	}

	public static Material getBaseSlab(Slab type) {
		switch (type) {
		case STONE:
			return Material.STONE_SLAB;
		case SANDSTONE:
			return Material.SANDSTONE_SLAB;
		case LEGACY_OAK:
			return Material.OAK_SLAB;
		case COBBLE:
			return Material.COBBLESTONE_SLAB;
		case BRICK:
			return Material.BRICK_SLAB;
		case STONEBRICK:
			return Material.STONE_BRICK_SLAB;
		case NETHERBRICK:
			return Material.NETHER_BRICK_SLAB;
		case QUARTZ:
			return Material.QUARTZ_SLAB;
		case SANDSTONE_RED:
			return Material.RED_SANDSTONE_SLAB;
		case OAK:
			return Material.OAK_SLAB;
		case SPRUCE:
			return Material.SPRUCE_SLAB;
		case BIRCH:
			return Material.BIRCH_SLAB;
		case JUNGLE:
			return Material.JUNGLE_SLAB;
		case ACACIA:
			return Material.ACACIA_SLAB;
		case DARKOAK:
			return Material.DARK_OAK_SLAB;
		default:
			return Material.STONE_SLAB;
		}
	}

}
