package forge_sandbox.greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.worldgen.BlockWeightedRandom;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectHollow;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;

public class DungeonsNetherBrick extends DungeonBase {

	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances,
			Coord origin) {

		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();

		int height = 3;
		int length = 2 + rand.nextInt(3);
		int width = 2 + rand.nextInt(3);

		IBlockFactory walls = theme.getPrimary().getWall();
		RectHollow.fill(editor, rand, new Coord(x - length - 1, y - 1, z - width - 1),
				new Coord(x + length + 1, y + height + 1, z + width + 1), walls, false, true);

		IBlockFactory floor = theme.getPrimary().getFloor();
		RectSolid.fill(editor, rand, new Coord(x - length - 1, y - 1, z - width - 1),
				new Coord(x + length + 1, y - 1, z + width + 1), floor);

		// lava crap under the floor
		BlockWeightedRandom subFloor = new BlockWeightedRandom();
		subFloor.addBlock(BlockType.get(BlockType.LAVA_FLOWING), 8);
		subFloor.addBlock(BlockType.get(BlockType.OBSIDIAN), 3);
		RectSolid.fill(editor, rand, new Coord(x - length, y - 5, z - width), new Coord(x + length, y - 2, z + width),
				subFloor);

		BlockWeightedRandom ceiling = new BlockWeightedRandom();
		ceiling.addBlock(BlockType.get(BlockType.FENCE_NETHER_BRICK), 10);
		ceiling.addBlock(BlockType.get(BlockType.AIR), 5);
		RectSolid.fill(editor, rand, new Coord(x - length, y + height, z - width),
				new Coord(x + length, y + height, z + width), ceiling);

		Treasure.createChests(editor, rand, 1,
				new RectSolid(new Coord(x - length, y, z - width), new Coord(x + length, y, z + width)).get(),
				Dungeon.getLevel(y));

		Spawner.generate(editor, rand, settings, new Coord(x - length - 1, y + rand.nextInt(2), z - width - 1));
		Spawner.generate(editor, rand, settings, new Coord(x - length - 1, y + rand.nextInt(2), z + width + 1));
		Spawner.generate(editor, rand, settings, new Coord(x + length + 1, y + rand.nextInt(2), z - width - 1));
		Spawner.generate(editor, rand, settings, new Coord(x + length + 1, y + rand.nextInt(2), z + width + 1));

		return true;
	}

	public int getSize() {
		return 6;
	}
}
