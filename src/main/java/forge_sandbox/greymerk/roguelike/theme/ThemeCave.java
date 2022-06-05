package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockJumble;
import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeCave extends ThemeBase {

	public ThemeCave() {

		BlockJumble floor = new BlockJumble();
		floor.addBlock(BlockType.get(BlockType.GRAVEL));
		floor.addBlock(BlockType.get(BlockType.DIRT));
		floor.addBlock(BlockType.get(BlockType.COBBLESTONE));

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_SMOOTH), 1000);
		walls.addBlock(BlockType.get(BlockType.DIRT), 100);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 50);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 20);
		walls.addBlock(BlockType.get(BlockType.ORE_COAL), 10);
		walls.addBlock(BlockType.get(BlockType.ORE_IRON), 5);
		walls.addBlock(BlockType.get(BlockType.ORE_EMERALD), 2);
		walls.addBlock(BlockType.get(BlockType.ORE_DIAMOND), 1);
		walls.addBlock(BlockType.get(BlockType.WATER_FLOWING), 3);
		walls.addBlock(BlockType.get(BlockType.LAVA_FLOWING), 1);

		MetaStair stair = new MetaStair(StairType.COBBLE);
		MetaBlock pillar = BlockType.get(BlockType.COBBLESTONE);

		this.primary = new BlockSet(floor, walls, stair, pillar);

		this.secondary = primary;
	}
}
