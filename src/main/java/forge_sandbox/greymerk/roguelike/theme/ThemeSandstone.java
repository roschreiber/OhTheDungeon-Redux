package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeSandstone extends ThemeBase {

	public ThemeSandstone() {

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.SANDSTONE), 100);
		walls.addBlock(BlockType.get(BlockType.SAND), 5);

		MetaStair stair = new MetaStair(StairType.SANDSTONE);

		MetaBlock pillar = BlockType.get(BlockType.SANDSTONE_SMOOTH);

		this.primary = new BlockSet(walls, stair, pillar);

		MetaBlock segmentWall = BlockType.get(BlockType.SANDSTONE_CHISELED);

		this.secondary = new BlockSet(segmentWall, stair, pillar);

	}
}
