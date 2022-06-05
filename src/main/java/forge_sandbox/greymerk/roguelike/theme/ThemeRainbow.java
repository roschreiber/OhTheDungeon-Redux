package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.BlockStripes;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.ColorBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeRainbow extends ThemeBase {

	public ThemeRainbow() {

		BlockStripes rainbow = new BlockStripes();
		for (DyeColor color : DyeColor.values()) {
			rainbow.addBlock(ColorBlock.get(ColorBlock.CONCRETE, color));
		}

		MetaStair stair = new MetaStair(StairType.ACACIA);
		MetaBlock pillar = Wood.get(Wood.ACACIA, WoodBlock.LOG);
		MetaBlock planks = Wood.get(Wood.ACACIA, WoodBlock.PLANK);

		this.primary = new BlockSet(rainbow, stair, pillar);
		this.secondary = new BlockSet(planks, stair, pillar);

	}
}
