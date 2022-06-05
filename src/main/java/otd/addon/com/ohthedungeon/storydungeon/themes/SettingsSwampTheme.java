package otd.addon.com.ohthedungeon.storydungeon.themes;

import java.util.ArrayList;
import java.util.List;

import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonFactory;
import forge_sandbox.greymerk.roguelike.dungeon.base.DungeonRoom;
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
import forge_sandbox.greymerk.roguelike.util.WeightedRandomizer;
import forge_sandbox.greymerk.roguelike.worldgen.filter.Filter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import otd.lib.BiomeDictionary;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.common.BiomeDictionary;

public class SettingsSwampTheme extends DungeonSettings {

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "swamp");

	public SettingsSwampTheme() {

		this.id = ID;
		this.inherit.add(SettingsBase.ID);

		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<>();
		biomes.add(BiomeDictionary.Type.SWAMP);
		this.criteria.setBiomeTypes(biomes);

		this.towerSettings = new TowerSettings(Tower.WITCH, Theme.getTheme(Theme.DARKOAK));

		Theme[] themes = { Theme.DARKHALL, Theme.DARKHALL, Theme.MUDDY, Theme.MOSSY, Theme.NETHER };

		WeightedRandomizer<ItemStack> brewing = new WeightedRandomizer<>();
		brewing.add(new WeightedRandomLoot(Material.GLASS_BOTTLE, 0, 1, 3, 3));
		brewing.add(new WeightedRandomLoot(Material.MAGMA_CREAM, 0, 1, 2, 1));
		brewing.add(new WeightedRandomLoot(Material.GLISTERING_MELON_SLICE, 0, 1, 3, 1));
		brewing.add(new WeightedRandomLoot(Material.BLAZE_POWDER, 0, 1, 3, 1));
		brewing.add(new WeightedRandomLoot(Material.SUGAR, 0, 1, 3, 1));
		this.lootRules = new LootRuleManager();
		for (int i = 0; i < 5; ++i) {
			this.lootRules.add(null, brewing, i, true, 2);
			this.lootRules.add(null, new WeightedRandomLoot(Material.SLIME_BALL, 0, 1, 1 + i, 1), i, false, 4 + i * 3);
		}
		for (int i = 0; i < 5; ++i) {

			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));

			if (i == 0) {

				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 8);
				segments.add(Segment.LAMP, 2);
				segments.add(Segment.FLOWERS, 1);
				segments.add(Segment.MUSHROOM, 2);
				level.setSegments(segments);

				DungeonFactory factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.CAKE);
				factory.addSingle(DungeonRoom.DARKHALL);
				factory.addRandom(DungeonRoom.BRICK, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				level.setRooms(factory);
				level.addFilter(Filter.MUD);
			}

			if (i == 1) {

				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 8);
				segments.add(Segment.SHELF, 4);
				segments.add(Segment.INSET, 4);
				segments.add(Segment.MUSHROOM, 3);
				level.setSegments(segments);

				DungeonFactory factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.CAKE);
				factory.addSingle(DungeonRoom.LAB);
				factory.addSingle(DungeonRoom.SPIDER);
				factory.addSingle(DungeonRoom.PIT);
				factory.addSingle(DungeonRoom.PRISON);
				factory.addRandom(DungeonRoom.BRICK, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				level.setRooms(factory);
				level.addFilter(Filter.MUD);
			}

			levels.put(i, level);
		}

		levels.get(2).addFilter(Filter.VINE);
		levels.get(3).addFilter(Filter.VINE);
	}
}