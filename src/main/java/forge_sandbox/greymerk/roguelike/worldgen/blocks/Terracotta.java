package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
//import net.minecraft.block.BlockGlazedTerracotta;
//import net.minecraft.init.Blocks;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;

public class Terracotta {

	public static MetaBlock get(DyeColor color, Cardinal dir) {
		MetaBlock block = getByColor(color);
		Directional state = (Directional) block.getState();
		state.setFacing(Cardinal.facing(dir));
		block.setState(state);
		return block;
	}

	public static MetaBlock getByColor(DyeColor color) {

		switch (color) {
		case WHITE:
			return new MetaBlock(Material.WHITE_GLAZED_TERRACOTTA);
		case ORANGE:
			return new MetaBlock(Material.ORANGE_GLAZED_TERRACOTTA);
		case MAGENTA:
			return new MetaBlock(Material.MAGENTA_GLAZED_TERRACOTTA);
		case LIGHT_BLUE:
			return new MetaBlock(Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
		case YELLOW:
			return new MetaBlock(Material.YELLOW_GLAZED_TERRACOTTA);
		case LIME:
			return new MetaBlock(Material.LIME_GLAZED_TERRACOTTA);
		case PINK:
			return new MetaBlock(Material.PINK_GLAZED_TERRACOTTA);
		case GRAY:
			return new MetaBlock(Material.GRAY_GLAZED_TERRACOTTA);
		case LIGHT_GRAY:
			return new MetaBlock(Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
		case CYAN:
			return new MetaBlock(Material.CYAN_GLAZED_TERRACOTTA);
		case PURPLE:
			return new MetaBlock(Material.PURPLE_GLAZED_TERRACOTTA);
		case BLUE:
			return new MetaBlock(Material.BLUE_GLAZED_TERRACOTTA);
		case BROWN:
			return new MetaBlock(Material.BROWN_GLAZED_TERRACOTTA);
		case GREEN:
			return new MetaBlock(Material.GREEN_GLAZED_TERRACOTTA);
		case RED:
			return new MetaBlock(Material.RED_GLAZED_TERRACOTTA);
		case BLACK:
			return new MetaBlock(Material.BLACK_GLAZED_TERRACOTTA);
		}

		return null;
	}

}
