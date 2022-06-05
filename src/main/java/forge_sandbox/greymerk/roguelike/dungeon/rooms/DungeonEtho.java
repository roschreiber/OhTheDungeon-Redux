package forge_sandbox.greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonEtho extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances,
			Coord origin) {
		return false;
	}

	public int getSize() {
		return 8;
	}

}
