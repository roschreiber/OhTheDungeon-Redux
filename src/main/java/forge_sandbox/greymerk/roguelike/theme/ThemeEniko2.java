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

public class ThemeEniko2 extends ThemeBase {

	public ThemeEniko2() {

		BlockStripes floor = new BlockStripes();
		floor.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.BLUE));
		floor.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.LIGHT_GRAY));

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 20);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 10);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 5);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 3);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 1);

		MetaStair stair = new MetaStair(StairType.STONEBRICK);
		MetaBlock pillar = Slab.get(Slab.STONE, false, true, true);

		this.primary = new BlockSet(floor, walls, stair, pillar);

		this.secondary = primary;
	}
}
