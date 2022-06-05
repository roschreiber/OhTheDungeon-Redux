package forge_sandbox.greymerk.roguelike.dungeon.settings;

import java.util.List;
import java.util.Random;
import java.util.Set;

import forge_sandbox.greymerk.roguelike.treasure.TreasureManager;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public interface ISettings {

	public boolean isValid(IWorldEditor editor, Coord pos);

	public List<SettingIdentifier> getInherits();

	public boolean isExclusive();

	public LevelSettings getLevelSettings(int level);

	public TowerSettings getTower();

	public void processLoot(Random rand, TreasureManager treasure);

	public int getNumLevels();

	public Set<SettingsType> getOverrides();
}
