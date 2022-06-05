package forge_sandbox.greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.dungeon.segment.ISegment;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public abstract class SegmentBase implements ISegment {

	public SegmentBase() {
	}

	@Override
	public void generate(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord pos) {

		if (level.hasNearbyNode(new Coord(pos)))
			return;

		if (isValidWall(editor, dir, new Coord(pos))) {
			genWall(editor, rand, level, dir, theme, new Coord(pos));
		}
	}

	protected abstract void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme,
			Coord pos);

	protected boolean isValidWall(IWorldEditor editor, Cardinal wallDirection, Coord pos) {

		switch (wallDirection) {
		case NORTH:
			if (editor.isAirBlock(new Coord(-1, 1, 2).add(pos)))
				return false;
			if (editor.isAirBlock(new Coord(1, 1, 2).add(pos)))
				return false;
			break;
		case SOUTH:
			if (editor.isAirBlock(new Coord(-1, 1, 2).add(pos)))
				return false;
			if (editor.isAirBlock(new Coord(1, 1, 2).add(pos)))
				return false;
			break;
		case EAST:
			if (editor.isAirBlock(new Coord(2, 1, -1).add(pos)))
				return false;
			if (editor.isAirBlock(new Coord(2, 1, 1).add(pos)))
				return false;
			break;
		case WEST:
			if (editor.isAirBlock(new Coord(-2, 1, -1).add(pos)))
				return false;
			if (editor.isAirBlock(new Coord(-2, 1, 1).add(pos)))
				return false;
			break;
		default:
			return false;
		}

		return true;
	}
}
