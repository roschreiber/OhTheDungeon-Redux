package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
//import net.minecraft.block.BlockPlanks;
//import net.minecraft.block.BlockSapling;
//import net.minecraft.init.Blocks;

public enum Wood {

	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK;

	public static MetaBlock get(WoodBlock block) {
		return get(OAK, block);
	}

	public static MetaBlock get(Wood type, WoodBlock block) {
		switch (block) {
		case LOG:
			return Log.getLog(type);
		case PLANK:
			return getPlank(type);
		case FENCE:
			return getFence(type);
		default:
			return Log.getLog(OAK);
		}
	}

	public static MetaBlock getPlank(Wood type) {

		MetaBlock plank;

		switch (type) {
		case OAK:
			plank = new MetaBlock(Material.OAK_PLANKS);
			break;
		case SPRUCE:
			plank = new MetaBlock(Material.SPRUCE_PLANKS);
			break;
		case BIRCH:
			plank = new MetaBlock(Material.BIRCH_PLANKS);
			break;
		case JUNGLE:
			plank = new MetaBlock(Material.JUNGLE_PLANKS);
			break;
		case ACACIA:
			plank = new MetaBlock(Material.ACACIA_PLANKS);
			break;
		case DARKOAK:
			plank = new MetaBlock(Material.DARK_OAK_PLANKS);
			break;
		default:
			plank = new MetaBlock(Material.OAK_PLANKS);
			break;
		}

		return plank;
	}

	public static MetaBlock getFence(Wood type) {

		MetaBlock fence;

		switch (type) {
		case OAK:
			fence = new MetaBlock(Material.OAK_FENCE);
			break;
		case SPRUCE:
			fence = new MetaBlock(Material.SPRUCE_FENCE);
			break;
		case BIRCH:
			fence = new MetaBlock(Material.BIRCH_FENCE);
			break;
		case JUNGLE:
			fence = new MetaBlock(Material.JUNGLE_FENCE);
			break;
		case ACACIA:
			fence = new MetaBlock(Material.ACACIA_FENCE);
			break;
		case DARKOAK:
			fence = new MetaBlock(Material.DARK_OAK_FENCE);
			break;
		default:
			fence = new MetaBlock(Material.OAK_FENCE);
			break;
		}

		return fence;
	}

	public static MetaBlock getSapling(Wood type) {
		MetaBlock sapling;

		switch (type) {
		case OAK:
			sapling = new MetaBlock(Material.OAK_SAPLING);
			break;
		case SPRUCE:
			sapling = new MetaBlock(Material.SPRUCE_SAPLING);
			break;
		case BIRCH:
			sapling = new MetaBlock(Material.BIRCH_SAPLING);
			break;
		case JUNGLE:
			sapling = new MetaBlock(Material.JUNGLE_SAPLING);
			break;
		case ACACIA:
			sapling = new MetaBlock(Material.ACACIA_SAPLING);
			break;
		case DARKOAK:
			sapling = new MetaBlock(Material.DARK_OAK_SAPLING);
			break;
		default:
			sapling = new MetaBlock(Material.OAK_SAPLING);
			break;
		}

		return sapling;
	}

}
