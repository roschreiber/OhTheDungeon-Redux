package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockJumble;
import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeTower extends ThemeBase {

	public ThemeTower() {

		BlockJumble stone = new BlockJumble();
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK));
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED));
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY));

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.AIR), 5);
		walls.addBlock(stone, 30);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 10);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 5);

		MetaStair stair = new MetaStair(StairType.STONEBRICK);

		IBlockFactory pillar = BlockType.get(BlockType.ANDESITE_POLISHED);
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = this.primary;

	}
}
