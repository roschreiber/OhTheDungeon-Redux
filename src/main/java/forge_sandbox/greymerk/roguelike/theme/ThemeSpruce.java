package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Log;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;

public class ThemeSpruce extends ThemeBase {

	public ThemeSpruce() {

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 20);
		MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);

		walls.addBlock(cracked, 10);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 1);

		MetaStair stair = new MetaStair(StairType.STONEBRICK);

		this.primary = new BlockSet(walls, stair, walls);

		MetaBlock spruce = Wood.getPlank(Wood.SPRUCE);
		MetaBlock SegmentWall = spruce;
		MetaStair SegmentStair = new MetaStair(StairType.SPRUCE);

		MetaBlock pillar = Log.get(Wood.SPRUCE, Cardinal.DOWN);
		this.secondary = new BlockSet(SegmentWall, SegmentStair, pillar);

	}
}
