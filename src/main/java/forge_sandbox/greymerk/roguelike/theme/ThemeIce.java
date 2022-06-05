package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeIce extends ThemeBase {

	public ThemeIce() {

		MetaBlock walls = BlockType.get(BlockType.SNOW);
		MetaStair stair = new MetaStair(StairType.QUARTZ);
		MetaBlock pillar = BlockType.get(BlockType.ICE_PACKED);

		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = this.primary;
	}
}
