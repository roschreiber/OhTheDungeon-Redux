package forge_sandbox.greymerk.roguelike.dungeon.rooms;

import java.util.Random;

//import forge_sandbox.greymerk.roguelike.config.RogueConfig;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.worldgen.BlockCheckers;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.EnderChest;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Quartz;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.IShape;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;
import otd.config.WorldConfig;

public class DungeonsEnder extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances,
			Coord origin) {

		MetaBlock black = BlockType.get(BlockType.OBSIDIAN);
		MetaBlock white = Quartz.get(Quartz.SMOOTH);
		MetaBlock air = BlockType.get(BlockType.AIR);

		Coord start;
		Coord end;
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, 0, -3));
		end.add(new Coord(3, 2, 3));
		RectSolid.fill(editor, inRandom, start, end, air);
		for (Cardinal dir : Cardinal.directions) {

			Cardinal[] orth = Cardinal.orthogonal(dir);

			start = new Coord(origin);
			start.add(dir, 4);
			end = new Coord(start);
			start.add(orth[0], 4);
			start.add(Cardinal.DOWN, 1);
			end.add(orth[1], 4);
			end.add(Cardinal.UP, 5);
			RectSolid.fill(editor, inRandom, start, end, black, false, true);

		}

		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, 2, -3));
		end.add(new Coord(3, 10, 3));

		int top = end.getY() - start.getY() + 1;
		for (Coord cell : new RectSolid(start, end)) {
			boolean disolve = inRandom.nextInt((cell.getY() - start.getY()) + 1) < 2;
			air.set(editor, inRandom, cell, false, disolve);
			black.set(editor, inRandom, cell, false,
					inRandom.nextInt(top - (cell.getY() - start.getY())) == 0 && !disolve);
		}

		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-4, -1, -4));
		end.add(new Coord(4, -1, 4));

		BlockCheckers checkers = new BlockCheckers(black, white);
		RectSolid.fill(editor, inRandom, start, end, checkers);

		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-4, 0, -4));
		end.add(new Coord(4, 0, 4));
		boolean generous = false;
		String world_name = editor.getWorldName();
		if (WorldConfig.wc.dict.containsKey(world_name) && WorldConfig.wc.dict.get(world_name).roguelike.generous)
			generous = true;
		if (generous)
			addEnderChest(editor, new RectSolid(start, end));
		Spawner.generate(editor, inRandom, settings, origin, Spawner.ENDERMAN);

		return true;
	}

	private void addEnderChest(IWorldEditor editor, IShape area) {
		for (Coord pos : area) {
			if (!editor.isAirBlock(pos))
				continue;

			Coord cursor = new Coord(pos);
			for (Cardinal dir : Cardinal.directions) {
				cursor.add(dir);
				if (editor.getMaterial(cursor).isSolid()) {
					EnderChest.set(editor, Cardinal.reverse(dir), pos);
					return;
				}
				cursor.add(Cardinal.reverse(dir));
			}
		}
	}

	public int getSize() {
		return 7;
	}
}
