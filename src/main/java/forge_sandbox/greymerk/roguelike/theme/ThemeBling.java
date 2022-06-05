package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeBling extends ThemeBase {

	public ThemeBling() {

		BlockWeightedRandom walls = new BlockWeightedRandom();
//		walls.addBlock(BlockType.get(BlockType.IRON_BLOCK), 10);
//		walls.addBlock(BlockType.get(BlockType.GOLD_BLOCK), 3);
//		walls.addBlock(BlockType.get(BlockType.EMERALD_BLOCK), 10);
//		walls.addBlock(BlockType.get(BlockType.DIAMOND_BLOCK), 20);
		walls.addBlock(BlockType.get(BlockType.WHITE_TERRACOTTA), 10);
		walls.addBlock(BlockType.get(BlockType.YELLOW_TERRACOTTA), 3);
		walls.addBlock(BlockType.get(BlockType.LIME_TERRACOTTA), 10);
		walls.addBlock(BlockType.get(BlockType.LIGHT_BLUE_TERRACOTTA), 20);

		MetaStair stair = new MetaStair(StairType.QUARTZ);
		MetaBlock pillar = BlockType.get(BlockType.LAPIS_BLOCK);
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = this.primary;
	}
}
