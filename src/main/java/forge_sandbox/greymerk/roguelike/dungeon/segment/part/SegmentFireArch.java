package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IStair;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFireArch extends SegmentBase {

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme,
			Coord origin) {

		IStair stair = theme.getPrimary().getStair();
		IBlockFactory walls = theme.getPrimary().getWall();

		Coord start;
		Coord end;
		Coord cursor;

		Cardinal[] orths = Cardinal.orthogonal(dir);

		start = new Coord(origin);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orths[0]);
		end.add(orths[0]);
		end.add(Cardinal.UP, 2);
		end.add(dir);
		RectSolid.fill(editor, rand, start, end, walls);
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);
		cursor.add(Cardinal.UP, 2);
		stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
		cursor.add(Cardinal.DOWN, 2);
		cursor.add(dir);
		BlockType.get(BlockType.NETHERRACK).set(editor, cursor);
		cursor.add(Cardinal.UP);
		BlockType.get(BlockType.FIRE).set(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		BlockType.get(BlockType.IRON_BAR).set(editor, cursor);

		for (Cardinal orth : orths) {

			cursor = new Coord(origin);
			cursor.add(dir);
			cursor.add(orth);
			cursor.add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);

		}
	}
}
