package otd.addon.com.ohthedungeon.storydungeon.themes;

import java.util.ArrayList;
import java.util.List;

import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonFactory;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonRoom;
import forge_sandbox.greymerk.roguelike.dungeon.base.SecretFactory;
import forge_sandbox.greymerk.roguelike.dungeon.segment.Segment;
import forge_sandbox.greymerk.roguelike.dungeon.segment.SegmentGenerator;
import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingIdentifier;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SettingsContainer;
import forge_sandbox.greymerk.roguelike.dungeon.settings.SpawnCriteria;
import forge_sandbox.greymerk.roguelike.dungeon.settings.TowerSettings;
import forge_sandbox.greymerk.roguelike.dungeon.towers.Tower;
import forge_sandbox.greymerk.roguelike.theme.Theme;
import otd.lib.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary;

public class SettingsBirthdayTheme extends DungeonSettings {

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "mountain");

	public SettingsBirthdayTheme() {

		this.id = ID;
//		this.inherit.add(SettingsBase.ID);

		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<>();
		this.criteria.setBiomeTypes(biomes);

		this.towerSettings = new TowerSettings(Tower.ENIKO, Theme.getTheme(Theme.QUARTZ));

		Theme[] themes = { Theme.BIRTHDAY, Theme.BIRTHDAY, Theme.BIRTHDAY, Theme.BIRTHDAY, Theme.BIRTHDAY };

		for (int i = 0; i < 5; ++i) {
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			SecretFactory secrets = new SecretFactory();
			SegmentGenerator segments;

			if (i == 0) {
				level.setScatter(16);
				level.setRange(60);
				level.setNumRooms(10);

				DungeonFactory factory;

				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.LIBRARY);
				factory.addSingle(DungeonRoom.FIRE);
				factory.addSingle(DungeonRoom.FIREWORK);
				factory.addRandom(DungeonRoom.CAKE, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				factory.addRandom(DungeonRoom.TREETHO, 3);
				level.setRooms(factory);

				secrets = new SecretFactory();
				secrets.addRoom(DungeonRoom.BEDROOM, 2);
				secrets.addRoom(DungeonRoom.SMITH);
				level.setSecrets(secrets);

				segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 7);
				segments.add(Segment.ANKH, 2);
				segments.add(Segment.PLANT, 3);
				segments.add(Segment.LAMP, 1);
				segments.add(Segment.FLOWERS, 1);
				level.setSegments(segments);
			}

			if (i == 1) {
				level.setScatter(16);
				level.setRange(80);
				level.setNumRooms(20);

				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.FIRE);
				factory.addSingle(DungeonRoom.MESS);
				factory.addSingle(DungeonRoom.LIBRARY);
				factory.addSingle(DungeonRoom.LAB);
				factory.addRandom(DungeonRoom.CAKE, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				level.setRooms(factory);

				secrets = new SecretFactory();
				secrets.addRoom(DungeonRoom.ENCHANT);
				level.setSecrets(secrets);

				segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 7);
				segments.add(Segment.ANKH, 2);
				segments.add(Segment.PLANT, 3);
				segments.add(Segment.LAMP, 1);
				segments.add(Segment.FLOWERS, 1);
				level.setSegments(segments);
			}

			if (i == 2) {
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addRandom(DungeonRoom.AVIDYA, 4);
				factory.addRandom(DungeonRoom.MUSIC, 7);
				factory.addRandom(DungeonRoom.CORNER, 3);
				factory.addRandom(DungeonRoom.BTEAM, 2);
				factory.addRandom(DungeonRoom.DARKHALL, 2);
				factory.addRandom(DungeonRoom.REWARD, 3);
				level.setRooms(factory);

				secrets = new SecretFactory();
				secrets.addRoom(DungeonRoom.BEDROOM);
				secrets.addRoom(DungeonRoom.BTEAM);
				level.setSecrets(secrets);

				segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 7);
				segments.add(Segment.ANKH, 2);
				segments.add(Segment.PLANT, 3);
				segments.add(Segment.LAMP, 1);
				segments.add(Segment.FLOWERS, 1);
				level.setSegments(segments);

			}

			if (i == 3) {
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addRandom(DungeonRoom.CAKE, 4);
				factory.addRandom(DungeonRoom.MUSIC, 7);
				factory.addRandom(DungeonRoom.CORNER, 3);
				factory.addRandom(DungeonRoom.BTEAM, 2);
				factory.addRandom(DungeonRoom.STORAGE, 2);
				level.setRooms(factory);

				secrets = new SecretFactory();
				secrets.addRoom(DungeonRoom.BEDROOM);
				secrets.addRoom(DungeonRoom.BTEAM);
				level.setSecrets(secrets);

				segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 7);
				segments.add(Segment.ANKH, 2);
				segments.add(Segment.PLANT, 3);
				segments.add(Segment.LAMP, 1);
				segments.add(Segment.FLOWERS, 1);
				level.setSegments(segments);
			}

			if (i == 4) {
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addRandom(DungeonRoom.CAKE, 4);
				factory.addRandom(DungeonRoom.MUSIC, 7);
				factory.addRandom(DungeonRoom.CORNER, 3);
				factory.addRandom(DungeonRoom.BTEAM, 2);
				factory.addRandom(DungeonRoom.STORAGE, 2);
				level.setRooms(factory);

				secrets = new SecretFactory();
				secrets.addRoom(DungeonRoom.BEDROOM);
				secrets.addRoom(DungeonRoom.BTEAM);
				level.setSecrets(secrets);

				segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 7);
				segments.add(Segment.ANKH, 2);
				segments.add(Segment.PLANT, 3);
				segments.add(Segment.LAMP, 1);
				segments.add(Segment.FLOWERS, 1);
				level.setSegments(segments);
			}

			levels.put(i, level);
		}
		// levels.get(3).addFilter(Filter.VINE);
	}
}
