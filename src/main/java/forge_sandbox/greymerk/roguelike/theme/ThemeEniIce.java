package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.BlockStripes;
import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.ColorBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Quartz;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeEniIce extends ThemeBase {

	public ThemeEniIce() {

		MetaBlock ice = BlockType.get(BlockType.ICE_PACKED);
		MetaBlock purple = ColorBlock.get(ColorBlock.CLAY, DyeColor.PURPLE);

		BlockWeightedRandom light = new BlockWeightedRandom();
		light.addBlock(purple, 100);
		light.addBlock(BlockType.get(BlockType.WATER_FLOWING), 5);
		light.addBlock(BlockType.get(BlockType.LAPIS_BLOCK), 1);

		BlockWeightedRandom dark = new BlockWeightedRandom();
		dark.addBlock(BlockType.get(BlockType.OBSIDIAN), 100);
		dark.addBlock(BlockType.get(BlockType.LAVA_FLOWING), 5);
		dark.addBlock(BlockType.get(BlockType.REDSTONE_BLOCK), 1);

		BlockStripes floor = new BlockStripes();
		floor.addBlock(light);
		floor.addBlock(dark);

		// MetaBlock quartz = new MetaBlock(Blocks.quartz_block);
		MetaStair stair = new MetaStair(StairType.QUARTZ);
		MetaBlock quartzPillar = Quartz.getPillar(Cardinal.UP);

		this.primary = new BlockSet(floor, ice, stair, quartzPillar);
		this.secondary = new BlockSet(floor, ice, stair, quartzPillar);
		;
	}
}
