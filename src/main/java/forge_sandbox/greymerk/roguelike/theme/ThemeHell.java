package forge_sandbox.greymerk.roguelike.theme;

//import forge_sandbox.greymerk.roguelike.config.RogueConfig;
import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.Door;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.DoorType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.IDoor;
import otd.config.WorldConfig;

public class ThemeHell extends ThemeBase {

	public ThemeHell() {

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.NETHERBRICK), 200);
		walls.addBlock(BlockType.get(BlockType.NETHERRACK), 20);
		walls.addBlock(BlockType.get(BlockType.ORE_QUARTZ), 20);
		walls.addBlock(BlockType.get(BlockType.SOUL_SAND), 15);
		walls.addBlock(BlockType.get(BlockType.NETHER_WART_BLOCK), 10);
		walls.addBlock(BlockType.get(BlockType.COAL_BLOCK), 5);

		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(walls, 1500);
		floor.addBlock(BlockType.get(BlockType.RED_NETHERBRICK), 500);
		floor.addBlock(BlockType.get(BlockType.REDSTONE_BLOCK), 50);

		if (WorldConfig.wc.preciousBlocks)
			floor.addBlock(BlockType.get(BlockType.GOLD_BLOCK), 2);
		if (WorldConfig.wc.preciousBlocks)
			floor.addBlock(BlockType.get(BlockType.DIAMOND_BLOCK), 1);

		MetaStair stair = new MetaStair(StairType.NETHERBRICK);
		MetaBlock pillar = BlockType.get(BlockType.OBSIDIAN);

		IDoor door = new Door(DoorType.IRON);
		IBlockFactory lightstone = BlockType.get(BlockType.GLOWSTONE);
		IBlockFactory liquid = BlockType.get(BlockType.LAVA_FLOWING);

		this.primary = new BlockSet(floor, walls, stair, pillar, door, lightstone, liquid);
		this.secondary = new BlockSet(floor, BlockType.get(BlockType.RED_NETHERBRICK), stair,
				BlockType.get(BlockType.MAGMA), door, lightstone, liquid);

	}
}
