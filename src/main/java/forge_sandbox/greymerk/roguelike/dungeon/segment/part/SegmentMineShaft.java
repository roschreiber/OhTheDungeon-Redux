package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMineShaft extends SegmentBase {

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme,
			Coord origin) {

		MetaBlock air = BlockType.get(BlockType.AIR);

		IBlockFactory wall = theme.getSecondary().getWall();

		Cardinal[] orth = Cardinal.orthogonal(dir);

		Coord cursor = new Coord(origin);
		Coord start;
		Coord end;

		start = new Coord(cursor);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, start, end, air);

		cursor.add(Cardinal.UP, 3);
		cursor.add(orth[0]);
		RectSolid.fill(editor, rand, start, end, air);

		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		end.add(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, start, end, wall);
		start = new Coord(end);
		cursor = new Coord(end);
		start.add(orth[0]);
		end.add(orth[1]);
		RectSolid.fill(editor, rand, start, end, wall);

		start = new Coord(cursor);
		end = new Coord(cursor);
		end.add(Cardinal.reverse(dir), 2);
		RectSolid.fill(editor, rand, start, end, wall);
	}
}
