package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeSandstoneRed extends ThemeBase {

	public ThemeSandstoneRed() {

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.SANDSTONE_RED), 100);
		walls.addBlock(BlockType.get(BlockType.SAND_RED), 5);

		MetaStair stair = new MetaStair(StairType.RED_SANDSTONE);

		MetaBlock pillar = BlockType.get(BlockType.SANDSTONE_RED_SMOOTH);

		this.primary = new BlockSet(walls, stair, pillar);

		MetaBlock segmentWall = BlockType.get(BlockType.SANDSTONE_RED_CHISELED);

		this.secondary = new BlockSet(segmentWall, stair, pillar);

	}
}
