package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Stair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemePurpur extends ThemeBase {

	public ThemePurpur() {

		MetaBlock walls = BlockType.get(BlockType.PURPUR_BLOCK);
		MetaStair stair = Stair.get(StairType.PURPUR);
		MetaBlock pillar = BlockType.get(BlockType.PURPUR_PILLAR);

		this.primary = new BlockSet(walls, stair, pillar);
		MetaBlock SegmentWall = BlockType.get(BlockType.ENDER_BRICK);
		this.secondary = new BlockSet(SegmentWall, stair, pillar);

	}
}
