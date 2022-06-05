package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Log;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.Door;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.DoorType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.IDoor;

public class ThemeDarkHall extends ThemeBase {

	public ThemeDarkHall() {

		MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 30);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 10);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 20);
		walls.addBlock(cracked, 10);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 5);
		MetaStair stair = new MetaStair(StairType.STONEBRICK);
		MetaBlock pillar = BlockType.get(BlockType.STONE_BRICK_MOSSY);

		MetaBlock walls2 = Wood.getPlank(Wood.DARKOAK);
		MetaStair stair2 = new MetaStair(StairType.DARKOAK);
		MetaBlock pillar2 = Log.getLog(Wood.DARKOAK);

		IDoor door = new Door(DoorType.DARKOAK);

		this.primary = new BlockSet(walls, walls, stair, pillar, door);
		this.secondary = new BlockSet(walls2, walls2, stair2, pillar2, door);

	}
}
