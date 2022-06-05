package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.ColorBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeEthoTower extends ThemeBase {

	public ThemeEthoTower() {

		MetaBlock primaryWall = ColorBlock.get(ColorBlock.CLAY, DyeColor.CYAN);
		MetaStair stair = new MetaStair(StairType.SANDSTONE);
		MetaBlock secondaryWall = BlockType.get(BlockType.SANDSTONE_SMOOTH);

		this.primary = new BlockSet(primaryWall, primaryWall, stair, primaryWall);
		this.secondary = new BlockSet(secondaryWall, secondaryWall, stair, secondaryWall);
		;
	}

}
