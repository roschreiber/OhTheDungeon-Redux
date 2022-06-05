package forge_sandbox.greymerk.roguelike.worldgen.blocks.door;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.init.Blocks;

public enum DoorType {

	IRON, OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK;

	public static MetaBlock get(DoorType type) {

		MetaBlock door;
		switch (type) {
		case IRON:
			door = new MetaBlock(Material.IRON_DOOR);
			break;
		case BIRCH:
			door = new MetaBlock(Material.BIRCH_DOOR);
			break;
		case SPRUCE:
			door = new MetaBlock(Material.SPRUCE_DOOR);
			break;
		case JUNGLE:
			door = new MetaBlock(Material.JUNGLE_DOOR);
			break;
		case ACACIA:
			door = new MetaBlock(Material.ACACIA_DOOR);
			break;
		case DARKOAK:
			door = new MetaBlock(Material.DARK_OAK_DOOR);
			break;
		default:
			door = new MetaBlock(Material.OAK_DOOR);
			break;
		}

		return new MetaBlock(door);
	}

}
