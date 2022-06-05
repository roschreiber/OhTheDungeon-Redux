/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.greymerk.roguelike.dungeon.settings.builtin;

import java.util.ArrayList;
import java.util.List;

import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingIdentifier;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SpawnCriteria;
import forge_sandbox.greymerk.roguelike.dungeon.settings.TowerSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsBase;
import forge_sandbox.greymerk.roguelike.dungeon.towers.Tower;
import forge_sandbox.greymerk.roguelike.theme.Theme;
import otd.lib.BiomeDictionary;

public class SettingsHouseTheme extends DungeonSettings {

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "house");

	public SettingsHouseTheme() {

		this.id = ID;
		this.inherit.add(SettingsBase.ID);

		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<>();
		biomes.add(BiomeDictionary.Type.MUSHROOM);
		biomes.add(BiomeDictionary.Type.FOREST);
		this.criteria.setBiomeTypes(biomes);

		this.towerSettings = new TowerSettings(Tower.HOUSE, Theme.getTheme(Theme.HOUSE));
	}
}
