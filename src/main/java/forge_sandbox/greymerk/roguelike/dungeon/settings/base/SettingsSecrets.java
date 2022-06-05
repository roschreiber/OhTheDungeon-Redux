package forge_sandbox.greymerk.roguelike.dungeon.settings.base;

import forge_sandbox.greymerk.roguelike.dungeon.base.SecretFactory;
import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingIdentifier;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;

public class SettingsSecrets extends DungeonSettings {

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "secrets");

	public SettingsSecrets() {

		this.id = ID;

		for (int i = 0; i < 5; ++i) {

			SecretFactory factory = new SecretFactory();

			switch (i) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				break;
			}

			LevelSettings level = new LevelSettings();
			level.setSecrets(factory);
			levels.put(i, level);
		}
	}
}
