package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Quartz;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.Door;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.DoorType;

public class ThemeBirthday extends ThemeBase {

	public ThemeBirthday() {

		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.PINK_CONCRETE), 30);
		MetaBlock cracked = BlockType.get(BlockType.WHITE_TERRACOTTA);
		walls.addBlock(cracked, 20);
//		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
//		walls.addBlock(BlockType.get(BlockType.GRAVEL), 1);

		MetaStair stair = new MetaStair(StairType.QUARTZ);

		this.primary = new BlockSet(walls, walls, stair, walls, new Door(DoorType.SPRUCE));

		MetaBlock pillar = Quartz.getPillar(Cardinal.UP);
		MetaBlock segmentWall = Quartz.get(Quartz.CHISELED);
		MetaStair segmentStair = new MetaStair(StairType.QUARTZ);

		this.secondary = new BlockSet(segmentWall, segmentWall, segmentStair, pillar, new Door(DoorType.SPRUCE));
	}
}
