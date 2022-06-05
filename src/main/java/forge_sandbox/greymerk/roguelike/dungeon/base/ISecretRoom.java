package forge_sandbox.greymerk.roguelike.dungeon.base;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public interface ISecretRoom {

	public IDungeonRoom genRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos);

	public int getCount();

	public void add(int count);

}
