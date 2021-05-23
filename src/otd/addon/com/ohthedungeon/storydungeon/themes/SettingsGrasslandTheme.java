package otd.addon.com.ohthedungeon.storydungeon.themes;

import java.util.ArrayList;
import java.util.List;

import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonFactory;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonRoom;
import forge_sandbox.greymerk.roguelike.dungeon.base.SecretFactory;
import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingIdentifier;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SpawnCriteria;
import forge_sandbox.greymerk.roguelike.dungeon.settings.TowerSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsBase;
import forge_sandbox.greymerk.roguelike.dungeon.towers.Tower;
import forge_sandbox.greymerk.roguelike.theme.Theme;
import forge_sandbox.greymerk.roguelike.worldgen.filter.Filter;
import otd.lib.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary;

public class SettingsGrasslandTheme extends DungeonSettings{

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "grassland");
	
	public SettingsGrasslandTheme(){
		
		this.id = ID;
		this.inherit.add(SettingsBase.ID);
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<>();
		biomes.add(BiomeDictionary.Type.PLAINS);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new TowerSettings(Tower.BUNKER, Theme.getTheme(Theme.STONE));
		
		for(int i = 0; i < 5; ++i){
			
			LevelSettings level = new LevelSettings();
			SecretFactory secrets = new SecretFactory();
			DungeonFactory rooms = new DungeonFactory();
			
			switch(i){
			case 0:
				secrets.addRoom(DungeonRoom.BEDROOM);
				secrets.addRoom(DungeonRoom.SMITH);
				secrets.addRoom(DungeonRoom.FIREWORK);
				level.setSecrets(secrets);
				break;
			case 1:
				secrets.addRoom(DungeonRoom.BTEAM);
				rooms.addSingle(DungeonRoom.MUSIC);
				rooms.addSingle(DungeonRoom.PIT);
				rooms.addSingle(DungeonRoom.MESS);
				rooms.addSingle(DungeonRoom.LAB);
				rooms.addRandom(DungeonRoom.CORNER, 10);
				rooms.addRandom(DungeonRoom.BRICK, 3);
				level.setSecrets(secrets);
				level.setRooms(rooms);
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
			levels.put(i, level);
		}
		
		levels.get(3).addFilter(Filter.VINE);
	}
	
	
}
