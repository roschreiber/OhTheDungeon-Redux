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
import forge_sandbox.greymerk.roguelike.worldgen.blocks.FlowerPot;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFlowers extends SegmentBase {

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme,
			Coord origin) {

		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getSecondary().getStair();

		Coord cursor = new Coord(origin);
		Coord start;
		Coord end;

		Cardinal[] orth = Cardinal.orthogonal(dir);

		cursor.add(dir, 2);
		start = new Coord(cursor);
		start.add(orth[0], 1);
		end = new Coord(cursor);
		end.add(orth[1], 1);
		RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);
		start.add(dir, 1);
		end.add(dir, 1);
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);
		start.add(Cardinal.reverse(dir), 1);
		start.add(Cardinal.UP, 1);
		end.add(Cardinal.reverse(dir), 1);
		RectSolid.fill(editor, rand, start, end, air, false, true);
		cursor.add(Cardinal.UP, 2);
		for (Cardinal d : orth) {
			Coord c = new Coord(cursor);
			c.add(d, 1);
			stair.setOrientation(Cardinal.reverse(d), true);
			stair.set(editor, c);
		}

		start = new Coord(origin);
		start.add(dir, 2);
		start.add(Cardinal.UP);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		for (Coord c : new RectSolid(start, end)) {
			if (rand.nextInt(3) == 0 && editor.getMaterial(c).isSolid()) {
				FlowerPot.generate(editor, rand, c);
			}
		}
	}
}
