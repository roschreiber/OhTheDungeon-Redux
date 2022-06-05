package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeEtho extends ThemeBase {

	public ThemeEtho() {

		MetaBlock floor = BlockType.get(BlockType.GRASS);

		MetaBlock walls = Wood.get(WoodBlock.PLANK);

		MetaStair stair = new MetaStair(StairType.OAK);
		MetaBlock pillar = Wood.get(WoodBlock.LOG);

		this.primary = new BlockSet(floor, walls, stair, pillar);

		this.secondary = primary;
	}
}
