package forge_sandbox.greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.BlockType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Cake;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Log;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectHollow;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsWood extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances,
			Coord origin) {

		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		final int HEIGHT = 3;
		final int WIDTH = rand.nextInt(2) + 2;
		final int LENGTH = rand.nextInt(2) + 3;

		MetaBlock pillar = Log.getLog(Wood.values()[rand.nextInt(Wood.values().length)]);
		MetaBlock planks = Wood.getPlank(Wood.OAK);

		IBlockFactory glowstone = settings.getTheme().getPrimary().getLightBlock();
		MetaBlock air = BlockType.get(BlockType.AIR);

		RectSolid.fill(editor, rand, new Coord(x - WIDTH, y, z - LENGTH), new Coord(x + WIDTH, y + HEIGHT, z + LENGTH),
				air);
		RectHollow.fill(editor, rand, new Coord(x - WIDTH - 1, y - 1, z - LENGTH - 1),
				new Coord(x + WIDTH + 1, y + HEIGHT + 1, z + LENGTH + 1), planks, false, true);

		// log beams
		RectSolid.fill(editor, rand, new Coord(x - WIDTH, y, z - LENGTH), new Coord(x - WIDTH, y + HEIGHT, z - LENGTH),
				pillar);
		RectSolid.fill(editor, rand, new Coord(x - WIDTH, y, z + LENGTH), new Coord(x - WIDTH, y + HEIGHT, z + LENGTH),
				pillar);
		RectSolid.fill(editor, rand, new Coord(x + WIDTH, y, z - LENGTH), new Coord(x + WIDTH, y + HEIGHT, z - LENGTH),
				pillar);
		RectSolid.fill(editor, rand, new Coord(x + WIDTH, y, z + LENGTH), new Coord(x + WIDTH, y + HEIGHT, z + LENGTH),
				pillar);

		// glowstone
		glowstone.set(editor, rand, new Coord(x - WIDTH + 1, y - 1, z - LENGTH + 1));
		glowstone.set(editor, rand, new Coord(x - WIDTH + 1, y - 1, z + LENGTH - 1));
		glowstone.set(editor, rand, new Coord(x + WIDTH - 1, y - 1, z - LENGTH + 1));
		glowstone.set(editor, rand, new Coord(x + WIDTH - 1, y - 1, z + LENGTH - 1));

		planks.set(editor, rand, new Coord(x, y, z));
		Cake.get().set(editor, new Coord(x, y + 1, z));

		List<Coord> space = new ArrayList<>();
		space.add(new Coord(x - WIDTH, y, z - LENGTH + 1));
		space.add(new Coord(x - WIDTH, y, z + LENGTH - 1));
		space.add(new Coord(x + WIDTH, y, z - LENGTH + 1));
		space.add(new Coord(x + WIDTH, y, z + LENGTH - 1));

		Treasure.generate(editor, rand, space, Treasure.FOOD, Dungeon.getLevel(y));

		return true;
	}

	public int getSize() {
		return 6;
	}

}
