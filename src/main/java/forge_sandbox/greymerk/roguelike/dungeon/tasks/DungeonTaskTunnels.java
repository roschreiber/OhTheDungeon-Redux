package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.DungeonTunnel;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskTunnels implements IDungeonTask {

	@Override
	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {
		List<IDungeonLevel> levels = dungeon.getLevels();

		// generate tunnels
		IDungeonLevel level = levels.get(index);
		{
			for (DungeonTunnel t : level.getLayout().getTunnels()) {
				t.construct(editor, rand, level.getSettings());
			}
		}
		return index == levels.size() - 1;
	}
}
