package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Stair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeStone extends ThemeBase {

	public ThemeStone() {
		MetaBlock walls = BlockType.get(BlockType.STONE_BRICK);
		MetaStair stair = Stair.get(StairType.STONEBRICK);
		MetaBlock pillar = BlockType.get(BlockType.ANDESITE_POLISHED);

		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = primary;
	}
}
