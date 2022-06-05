package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Log;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeHouse extends ThemeBase {

	public ThemeHouse() {

		IBlockFactory walls = BlockType.get(BlockType.BRICK);
		MetaStair stair = new MetaStair(StairType.BRICK);
		MetaBlock pillar = BlockType.get(BlockType.GRANITE_POLISHED);
		MetaBlock floor = BlockType.get(BlockType.GRANITE_POLISHED);
		this.primary = new BlockSet(floor, walls, stair, pillar);

		MetaBlock secondaryWalls = Wood.get(Wood.OAK, WoodBlock.PLANK);
		MetaBlock secondaryFloor = BlockType.get(BlockType.ANDESITE_POLISHED);
		MetaStair secondaryStair = new MetaStair(StairType.OAK);
		MetaBlock secondaryPillar = Log.getLog(Wood.OAK);
		this.secondary = new BlockSet(secondaryFloor, secondaryWalls, secondaryStair, secondaryPillar);

	}
}
