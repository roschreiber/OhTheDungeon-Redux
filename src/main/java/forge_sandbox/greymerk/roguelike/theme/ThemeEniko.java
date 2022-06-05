package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.BlockStripes;
import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.ColorBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Slab;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeEniko extends ThemeBase {

	public ThemeEniko() {

		BlockStripes floor = new BlockStripes();
		floor.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.LIGHT_BLUE));
		floor.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.WHITE));

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 100);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 1);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 5);

		MetaStair stair = new MetaStair(StairType.STONEBRICK);
		MetaBlock pillar = Slab.get(Slab.STONE, false, true, true);

		this.primary = new BlockSet(floor, walls, stair, pillar);
		this.secondary = primary;
	}
}
