package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeon;
import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public interface IDungeonTask {

	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index);

}
