package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemePyramid extends ThemeBase {

	public ThemePyramid() {

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.SANDSTONE_SMOOTH), 100);
		walls.addBlock(BlockType.get(BlockType.SANDSTONE), 10);
		walls.addBlock(BlockType.get(BlockType.SANDSTONE_CHISELED), 5);

		MetaStair stair = new MetaStair(StairType.SANDSTONE);
		MetaBlock pillar = BlockType.get(BlockType.SANDSTONE_SMOOTH);
		MetaBlock SegmentWall = BlockType.get(BlockType.SANDSTONE_CHISELED);

		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = new BlockSet(SegmentWall, stair, pillar);

	}

}
