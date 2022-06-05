package forge_sandbox.greymerk.roguelike.dungeon.settings.base;

import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingIdentifier;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;
import forge_sandbox.greymerk.roguelike.dungeon.settings.TowerSettings;
import forge_sandbox.greymerk.roguelike.dungeon.towers.Tower;
import forge_sandbox.greymerk.roguelike.theme.Theme;

public class SettingsTheme extends DungeonSettings {

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "theme");

	public SettingsTheme() {

		this.id = ID;

		this.towerSettings = new TowerSettings(Tower.ROGUE, Theme.getTheme(Theme.TOWER));

		Theme[] themes = { Theme.OAK, Theme.SPRUCE, Theme.CRYPT, Theme.MOSSY, Theme.HELL };

		for (int i = 0; i < 5; ++i) {
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}
}
