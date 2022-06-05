package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockLeaves;
//import net.minecraft.block.BlockNewLeaf;
//import net.minecraft.block.BlockOldLeaf;
//import net.minecraft.block.BlockPlanks;
//import net.minecraft.init.Blocks;

public class Leaves {

	public static MetaBlock get(Wood type, boolean decay) {

		Material base = getBlockId(type);

		MetaBlock leaf = new MetaBlock(base);

//		leaf.withProperty(BlockLeaves.DECAYABLE, decay);
		org.bukkit.block.data.type.Leaves leaves = (org.bukkit.block.data.type.Leaves) leaf.getState();
		leaves.setPersistent(!decay);
		leaf.setState(leaves);

		return leaf;

	}

	public static Material getBlockId(Wood type) {
		switch (type) {
		case OAK:
			return Material.OAK_LEAVES;
		case SPRUCE:
			return Material.SPRUCE_LEAVES;
		case BIRCH:
			return Material.BIRCH_LEAVES;
		case JUNGLE:
			return Material.JUNGLE_LEAVES;
		case ACACIA:
			return Material.ACACIA_LEAVES;
		case DARKOAK:
			return Material.DARK_OAK_LEAVES;
		default:
			return Material.OAK_LEAVES;
		}
	}

//	private static BlockPlanks.EnumType getType(Wood type){
//		
//		switch(type){
//		case OAK: return BlockPlanks.EnumType.OAK;
//		case SPRUCE: return BlockPlanks.EnumType.SPRUCE;
//		case BIRCH: return BlockPlanks.EnumType.BIRCH;
//		case JUNGLE: return BlockPlanks.EnumType.JUNGLE;
//		case ACACIA: return BlockPlanks.EnumType.ACACIA;
//		case DARKOAK: return BlockPlanks.EnumType.DARK_OAK;
//		default: return BlockPlanks.EnumType.OAK;
//		}		
//	}
}
