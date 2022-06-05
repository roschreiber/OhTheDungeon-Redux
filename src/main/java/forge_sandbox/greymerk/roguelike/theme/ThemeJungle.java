package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockJumble;
import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Log;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;

public class ThemeJungle extends ThemeBase {

	public ThemeJungle() {
		MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);
		MetaBlock mossy = BlockType.get(BlockType.STONE_BRICK_MOSSY);
		MetaBlock chisel = BlockType.get(BlockType.STONE_BRICK_CHISELED);

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 50);
		walls.addBlock(mossy, 30);
		walls.addBlock(cracked, 20);
		walls.addBlock(chisel, 15);

		MetaStair stair = new MetaStair(StairType.COBBLE);

		MetaBlock pillar = chisel;
		MetaBlock pillar2 = Log.getLog(Wood.JUNGLE);

		BlockJumble stairJumble = new BlockJumble();
		for (Cardinal dir : Cardinal.directions) {
			MetaStair s = new MetaStair(StairType.STONEBRICK);
			s.setOrientation(dir, false);
			stairJumble.addBlock(s);
		}

		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(stairJumble, 1);
		floor.addBlock(walls, 5);

		this.primary = new BlockSet(floor, walls, stair, pillar);
		this.secondary = new BlockSet(chisel, stair, pillar2);
	}
}
