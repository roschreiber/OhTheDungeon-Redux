package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.treasure.ChestPlacementException;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentChest extends SegmentBase {

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme,
			Coord origin) {

		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getSecondary().getStair();

		Coord cursor;
		Coord start;
		Coord end;

		Cardinal[] orth = Cardinal.orthogonal(dir);

		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, air);
		start.add(dir, 1);
		end.add(dir, 1);
		RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());

		for (Cardinal d : orth) {
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 2);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, cursor);

			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			stair.setOrientation(Cardinal.reverse(d), false);
			stair.set(editor, cursor);
		}

		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 1);
		cursor.add(dir, 3);
		air.set(editor, rand, cursor);
		cursor.add(Cardinal.UP, 1);
		stair.setOrientation(Cardinal.reverse(dir), true);
		stair.set(editor, cursor);

		Coord shelf = new Coord(origin);
		shelf.add(dir, 3);
		Coord below = new Coord(shelf);
		shelf.add(Cardinal.UP, 1);

		if (editor.isAirBlock(below))
			return;

		try {
			boolean trapped = Dungeon.getLevel(origin.getY()) == 3 && rand.nextInt(3) == 0;
			Treasure.generate(editor, rand, shelf, Dungeon.getLevel(origin.getY()), trapped);
			if (trapped) {
				BlockType.get(BlockType.TNT).set(editor, new Coord(shelf.getX(), shelf.getY() - 2, shelf.getZ()));
				if (rand.nextBoolean())
					BlockType.get(BlockType.TNT).set(editor, new Coord(shelf.getX(), shelf.getY() - 3, shelf.getZ()));
			}
		} catch (ChestPlacementException cpe) {
			// do nothing
		}
	}
}
