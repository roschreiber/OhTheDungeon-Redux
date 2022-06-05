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

public class ThemeEniQuartz extends ThemeBase {

	public ThemeEniQuartz() {

		BlockWeightedRandom red = new BlockWeightedRandom();
		red.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.RED), 100);
		red.addBlock(BlockType.get(BlockType.WATER_FLOWING), 5);
		red.addBlock(BlockType.get(BlockType.REDSTONE_BLOCK), 1);

		BlockStripes floor = new BlockStripes();
		floor.addBlock(red);
		floor.addBlock(BlockType.get(BlockType.OBSIDIAN));

		MetaBlock walls = BlockType.get(BlockType.BRICK);

		MetaStair stair = new MetaStair(StairType.BRICK);

		this.primary = new BlockSet(floor, walls, stair, walls);

		MetaBlock quartz = BlockType.get(BlockType.QUARTZ);
		MetaStair quartzStair = new MetaStair(StairType.QUARTZ);
		MetaBlock quartzPillar = Quartz.getPillar(Cardinal.UP);

		this.secondary = new BlockSet(floor, quartz, quartzStair, quartzPillar);
		;
	}
}
