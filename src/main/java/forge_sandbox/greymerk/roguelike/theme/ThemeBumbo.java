package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.ColorBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Stair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeBumbo extends ThemeBase {
	public ThemeBumbo() {
		IBlockFactory green = ColorBlock.get(ColorBlock.CLAY, DyeColor.GREEN);
		IBlockFactory moustache = ColorBlock.get(ColorBlock.CLAY, DyeColor.BLACK);
		IBlockFactory black = ColorBlock.get(ColorBlock.CONCRETE, DyeColor.BLACK);
		IBlockFactory white = ColorBlock.get(ColorBlock.CONCRETE, DyeColor.WHITE);

		IBlockFactory yellow = ColorBlock.get(ColorBlock.CONCRETE, DyeColor.YELLOW);
		IBlockFactory red = ColorBlock.get(ColorBlock.CONCRETE, DyeColor.RED);

		primary = new BlockSet(moustache, green, Stair.get(StairType.ACACIA), white);
		secondary = new BlockSet(red, yellow, Stair.get(StairType.ACACIA), black);
	}
}
