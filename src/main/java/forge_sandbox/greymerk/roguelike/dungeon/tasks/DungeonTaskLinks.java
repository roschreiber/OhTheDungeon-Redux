package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.DungeonNode;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.dungeon.LevelGenerator;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskLinks implements IDungeonTask {

	@Override
	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {

		List<IDungeonLevel> levels = dungeon.getLevels();

		// generate level links
		IDungeonLevel previous;
		if (index == 0)
			previous = null;
		else
			previous = levels.get(index - 1);
		IDungeonLevel level = levels.get(index);
		DungeonNode upper = previous == null ? null : previous.getLayout().getEnd();
		DungeonNode lower = level.getLayout().getStart();
		LevelGenerator.generateLevelLink(editor, rand, level.getSettings(), lower, upper);
		return index == levels.size() - 1;
	}
}
