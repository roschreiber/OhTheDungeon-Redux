package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.BlockStripes;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.ColorBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeEnder extends ThemeBase {

	public ThemeEnder() {

		BlockStripes floor = new BlockStripes();
		floor.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.BROWN));
		floor.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.YELLOW));

		MetaBlock walls = BlockType.get(BlockType.ENDER_BRICK);

		MetaStair stair = new MetaStair(StairType.SANDSTONE);
		MetaBlock pillar = BlockType.get(BlockType.OBSIDIAN);

		this.primary = new BlockSet(floor, walls, stair, pillar);

		this.secondary = new BlockSet(floor, BlockType.get(BlockType.SANDSTONE_CHISELED), stair, pillar);
	}
}
