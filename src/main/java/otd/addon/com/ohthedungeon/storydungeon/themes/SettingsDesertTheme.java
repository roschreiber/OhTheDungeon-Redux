package otd.addon.com.ohthedungeon.storydungeon.themes;

import java.util.ArrayList;
import java.util.List;

import forge_sandbox.greymerk.roguelike.dungeon.LevelGenerator;
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
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsBase;
import forge_sandbox.greymerk.roguelike.dungeon.towers.Tower;
import forge_sandbox.greymerk.roguelike.theme.Theme;
import forge_sandbox.greymerk.roguelike.treasure.loot.LootRuleManager;
import forge_sandbox.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import org.bukkit.Material;
import otd.lib.BiomeDictionary;
//import net.minecraft.init.Items;
//import net.minecraftforge.common.BiomeDictionary;

public class SettingsDesertTheme extends DungeonSettings {

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "desert");

	public SettingsDesertTheme() {

		this.id = ID;

		this.inherit.add(SettingsBase.ID);

		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<>();
		biomes.add(BiomeDictionary.Type.SANDY);
		this.criteria.setBiomeTypes(biomes);

		this.towerSettings = new TowerSettings(Tower.PYRAMID, Theme.getTheme(Theme.PYRAMID));

		this.lootRules = new LootRuleManager();
		for (int i = 0; i < 5; ++i) {
			this.lootRules.add(null, new WeightedRandomLoot(Material.GOLD_INGOT, 0, 1, 1 + i, 1), i, false, 6);
		}

		Theme[] themes = { Theme.PYRAMID, Theme.SANDSTONE, Theme.SANDSTONERED, Theme.ENDER, Theme.NETHER };

		for (int i = 0; i < 5; ++i) {

			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));

			if (i == 0) {
				level.setDifficulty(2);
				SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
				segments.add(Segment.WALL, 10);
				segments.add(Segment.ANKH, 5);
				segments.add(Segment.SKULL, 2);
				segments.add(Segment.TOMB, 1);
				level.setSegments(segments);

				DungeonFactory factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.PYRAMIDTOMB);
				factory.addRandom(DungeonRoom.PYRAMIDSPAWNER, 5);
				factory.addRandom(DungeonRoom.PYRAMIDCORNER, 3);
				level.setRooms(factory);

				SecretFactory secrets = new SecretFactory();
				secrets.addRoom(DungeonRoom.PYRAMIDTOMB);
				level.setSecrets(secrets);

				level.setGenerator(LevelGenerator.CLASSIC);
			}

			if (i == 1) {
				level.setDifficulty(2);
				SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
				segments.add(Segment.SPAWNER, 1);
				segments.add(Segment.WALL, 10);
				segments.add(Segment.INSET, 5);
				segments.add(Segment.SHELF, 5);
				segments.add(Segment.CHEST, 1);
				segments.add(Segment.ANKH, 1);
				segments.add(Segment.SKULL, 2);
				segments.add(Segment.TOMB, 1);
				level.setSegments(segments);

				DungeonFactory factory = new DungeonFactory();
				factory.addRandom(DungeonRoom.PYRAMIDTOMB, 2);
				factory.addRandom(DungeonRoom.PYRAMIDSPAWNER, 10);
				factory.addRandom(DungeonRoom.PYRAMIDCORNER, 5);
				level.setRooms(factory);

				level.setGenerator(LevelGenerator.CLASSIC);
			}

			if (i == 2) {
				level.setDifficulty(2);
				SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
				segments.add(Segment.SPAWNER, 1);
				segments.add(Segment.WALL, 10);
				segments.add(Segment.INSET, 5);
				segments.add(Segment.SHELF, 5);
				segments.add(Segment.CHEST, 1);
				segments.add(Segment.SKULL, 2);
				segments.add(Segment.TOMB, 1);
				level.setSegments(segments);

				DungeonFactory factory = new DungeonFactory();
				factory.addRandom(DungeonRoom.PYRAMIDTOMB, 1);
				factory.addRandom(DungeonRoom.CRYPT, 4);
				factory.addSingle(DungeonRoom.OSSUARY);
				factory.addRandom(DungeonRoom.SPIDER, 2);
				factory.addRandom(DungeonRoom.PYRAMIDSPAWNER, 5);
				factory.addRandom(DungeonRoom.PYRAMIDCORNER, 4);

				level.setRooms(factory);

				level.setGenerator(LevelGenerator.CLASSIC);
			}

			if (i == 3) {
				level.setDifficulty(2);
				SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
				segments.add(Segment.SPAWNER, 1);
				segments.add(Segment.WALL, 10);
				segments.add(Segment.INSET, 5);
				segments.add(Segment.SHELF, 5);
				segments.add(Segment.CHEST, 1);
				segments.add(Segment.SKULL, 2);
				segments.add(Segment.TOMB, 1);
				level.setSegments(segments);

				DungeonFactory factory = new DungeonFactory();
				factory.addRandom(DungeonRoom.PYRAMIDTOMB, 1);
				factory.addRandom(DungeonRoom.SLIME, 2);
				factory.addRandom(DungeonRoom.FIRE, 2);
				factory.addRandom(DungeonRoom.PYRAMIDSPAWNER, 5);
				factory.addRandom(DungeonRoom.PYRAMIDCORNER, 4);
				factory.addRandom(DungeonRoom.SPIDER, 2);
				level.setRooms(factory);

				level.setGenerator(LevelGenerator.CLASSIC);
			}

			levels.put(i, level);
		}
	}
}