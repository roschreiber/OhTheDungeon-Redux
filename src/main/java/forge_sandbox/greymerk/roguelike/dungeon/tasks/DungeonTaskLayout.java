package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.dungeon.DungeonNode;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.dungeon.ILevelGenerator;
import forge_sandbox.greymerk.roguelike.dungeon.ILevelLayout;
import forge_sandbox.greymerk.roguelike.dungeon.LevelGenerator;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonRoom;
import forge_sandbox.greymerk.roguelike.dungeon.base.IDungeonFactory;
import forge_sandbox.greymerk.roguelike.dungeon.base.IDungeonRoom;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskLayout implements IDungeonTask {

	@Override
	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {
		List<IDungeonLevel> levels = dungeon.getLevels();

		if (index == 0) {
			Coord start = dungeon.getPosition();
			dungeon.setTaskPosition(start);
		}

		// generate level layouts
		if (index <= levels.size() - 1) {
			IDungeonLevel level = levels.get(index);
			ILevelGenerator generator = LevelGenerator.getGenerator(editor, rand, level.getSettings().getGenerator(),
					level);

			Coord start = dungeon.getTaskPosition();

			try {
				level.generate(generator, start);
			} catch (Exception e) {
			}

			ILevelLayout layout = generator.getLayout();
//			rand = Dungeon.getRandom(editor, start);
			start = new Coord(layout.getEnd().getPosition());
			start.add(Cardinal.DOWN, Dungeon.VERTICAL_SPACING);
			dungeon.setTaskPosition(start);

			return false;
		} else {
			index = index - levels.size();
			IDungeonLevel level = levels.get(index);
			// assign dungeon rooms
			ILevelLayout layout = level.getLayout();
			IDungeonFactory rooms = level.getSettings().getRooms();

			int count = 0;
			while (layout.hasEmptyRooms()) {
				IDungeonRoom toGenerate = count < level.getSettings().getNumRooms() ? rooms.get(rand)
						: DungeonRoom.getInstance(DungeonRoom.CORNER);
				DungeonNode node = layout.getBestFit(toGenerate);
				node.setDungeon(toGenerate);
				++count;
			}
			return index == levels.size() - 1;
		}
	}
}
