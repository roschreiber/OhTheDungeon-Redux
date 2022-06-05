package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

//import forge_sandbox.greymerk.roguelike.config.RogueConfig;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.filter.Filter;
import otd.lib.DungeonWorldManager;
//import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import otd.config.WorldConfig;

public class DungeonTaskEncase implements IDungeonTask {

	@Override
	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {

		List<IDungeonLevel> levels = dungeon.getLevels();

		@SuppressWarnings("unused")
		boolean encase = false;
		String world_name = editor.getWorldName();
		if (world_name.equals(DungeonWorldManager.WORLD_NAME) || (WorldConfig.wc.dict.containsKey(world_name)
				&& WorldConfig.wc.dict.get(world_name).roguelike.encase))
			encase = true;

		IDungeonLevel level = levels.get(index);
		level.filter(editor, rand, Filter.get(Filter.ENCASE));
		return levels.size() == index + 1;
	}
}
