package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Log;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeBrick extends ThemeBase {

	public ThemeBrick() {

		MetaBlock walls = BlockType.get(BlockType.BRICK);

		MetaStair stair = new MetaStair(StairType.BRICK);
		MetaBlock pillar = Log.getLog(Wood.SPRUCE);

		this.primary = new BlockSet(walls, stair, walls);

		MetaBlock segmentWall = Wood.get(Wood.SPRUCE, WoodBlock.PLANK);

		this.secondary = new BlockSet(segmentWall, stair, pillar);

	}
}
