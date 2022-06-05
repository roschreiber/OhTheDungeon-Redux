package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Quartz;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeQuartz extends ThemeBase {

	public ThemeQuartz() {

		MetaBlock walls = BlockType.get(BlockType.QUARTZ);
		MetaStair stair = new MetaStair(StairType.QUARTZ);
		MetaBlock pillar = Quartz.getPillar(Cardinal.UP);

		this.primary = new BlockSet(walls, stair, pillar);
		MetaBlock SegmentWall = Quartz.get(Quartz.CHISELED);
		this.secondary = new BlockSet(SegmentWall, stair, pillar);

	}
}
