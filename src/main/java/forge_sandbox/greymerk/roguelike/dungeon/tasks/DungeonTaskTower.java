package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.Dungeon;
import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.dungeon.towers.Tower;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskTower implements IDungeonTask {

	@Override
	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {
		Coord pos = dungeon.getPosition();

		Tower tower = settings.getTower().getTower();
		Random r = Dungeon.getRandom(editor, pos);
		Tower.get(tower).generate(editor, r, settings.getTower().getTheme(), pos);

		return true;
	}

}
