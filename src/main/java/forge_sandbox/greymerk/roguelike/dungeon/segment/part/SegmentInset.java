package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentInset extends SegmentBase {

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
		air.set(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		stair.setOrientation(Cardinal.reverse(dir), true);
		stair.set(editor, cursor);
	}
}
