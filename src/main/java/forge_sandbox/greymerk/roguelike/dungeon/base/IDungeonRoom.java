package forge_sandbox.greymerk.roguelike.dungeon.base;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public interface IDungeonRoom {

	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances,
			Coord origin);

	public int getSize();

	public boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos);

}
