package otd.addon.com.ohthedungeon.storydungeon.themes;

import java.util.ArrayList;
import java.util.List;

import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingIdentifier;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SpawnCriteria;
import forge_sandbox.greymerk.roguelike.dungeon.settings.TowerSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsBase;
import forge_sandbox.greymerk.roguelike.dungeon.towers.Tower;
import forge_sandbox.greymerk.roguelike.theme.Theme;
import otd.lib.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary;

public class SettingsMesaTheme extends DungeonSettings{
	
	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "mesa");
	
	public SettingsMesaTheme(){
		
		this.id = ID;
		this.inherit.add(SettingsBase.ID);
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<>();
		biomes.add(BiomeDictionary.Type.MESA);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new TowerSettings(Tower.ETHO, Theme.getTheme(Theme.ETHOTOWER));
		
		Theme[] themes = {Theme.ETHOTOWER, Theme.ETHOTOWER, Theme.CRYPT, Theme.CRYPT, Theme.NETHER};
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}
}
