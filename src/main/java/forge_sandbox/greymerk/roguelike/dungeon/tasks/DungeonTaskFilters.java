package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskFilters implements IDungeonTask {

	@Override
	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {

		List<IDungeonLevel> levels = dungeon.getLevels();

		IDungeonLevel level = levels.get(index);
		level.applyFilters(editor, rand);
		return index == levels.size() - 1;
	}
}
