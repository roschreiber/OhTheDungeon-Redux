package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.BlockJumble;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Terracotta;

public class ThemeTerracotta extends ThemeBase {

	public ThemeTerracotta() {

		BlockJumble blocks = new BlockJumble();
		for (Cardinal dir : Cardinal.directions) {
			blocks.addBlock(Terracotta.get(DyeColor.MAGENTA, dir));
		}

		MetaStair stair = new MetaStair(StairType.PURPUR);
		MetaBlock pillar = BlockType.get(BlockType.PURPUR_PILLAR);
		MetaBlock deco = BlockType.get(BlockType.PURPUR_DOUBLE_SLAB);

		this.primary = new BlockSet(blocks, stair, pillar);
		this.secondary = new BlockSet(deco, stair, pillar);

	}
}
