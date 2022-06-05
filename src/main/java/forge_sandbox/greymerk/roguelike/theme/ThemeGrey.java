package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeGrey extends ThemeBase {

	public ThemeGrey() {

		MetaBlock andesite = BlockType.get(BlockType.ANDESITE);
		MetaBlock smoothAndesite = BlockType.get(BlockType.ANDESITE_POLISHED);

		MetaStair stair = new MetaStair(StairType.STONEBRICK);

		this.primary = new BlockSet(andesite, smoothAndesite, stair, smoothAndesite);
		this.secondary = this.primary;
	}
}
