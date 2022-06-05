package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeSewer extends ThemeBase {

	public ThemeSewer() {

		MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);
		MetaBlock mossy = BlockType.get(BlockType.STONE_BRICK_MOSSY);

		BlockWeightedRandom wall = new BlockWeightedRandom();
		wall.addBlock(BlockType.get(BlockType.STONE_BRICK), 10);
		wall.addBlock(mossy, 4);
		wall.addBlock(cracked, 1);

		MetaBlock floor = BlockType.get(BlockType.STONE_BRICK);

		MetaStair stair = new MetaStair(StairType.STONEBRICK);

		this.primary = new BlockSet(floor, wall, stair, wall);

		this.secondary = this.primary;
	}
}
