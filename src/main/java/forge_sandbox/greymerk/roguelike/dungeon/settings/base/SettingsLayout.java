package forge_sandbox.greymerk.roguelike.dungeon.settings.base;

import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingIdentifier;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;
import forge_sandbox.greymerk.roguelike.dungeon.LevelGenerator;
import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;

public class SettingsLayout extends DungeonSettings {

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "layout");

	public SettingsLayout() {

		this.id = ID;

		int[] numRooms = { 10, 15, 15, 20, 15 };
		int[] scatter = { 15, 15, 17, 12, 15 };
		int[] range = { 50, 50, 80, 70, 50 };

		LevelGenerator[] generator = { LevelGenerator.CLASSIC, LevelGenerator.CLASSIC, LevelGenerator.MST,
				LevelGenerator.CLASSIC, LevelGenerator.CLASSIC };

		for (int i = 0; i < 5; ++i) {
			LevelSettings level = new LevelSettings();
			level.setNumRooms(numRooms[i]);
			level.setRange(range[i]);
			level.setScatter(scatter[i]);
			level.setGenerator(generator[i]);
			levels.put(i, level);
		}
	}

}
