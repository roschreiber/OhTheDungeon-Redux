package forge_sandbox.greymerk.roguelike.dungeon.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsBase;
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsLayout;
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsLootRules;
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsRooms;
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsSecrets;
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsSegments;
import forge_sandbox.greymerk.roguelike.dungeon.settings.base.SettingsTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsBunkerTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsDesertTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsForestTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsHouseTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsIceTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsJungleTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsMesaTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsMountainTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsRareTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsRuinTheme;
import forge_sandbox.greymerk.roguelike.dungeon.settings.builtin.SettingsSwampTheme;
import otd.Main;

public class SettingsContainer implements ISettingsContainer {

	public static final String DEFAULT_NAMESPACE = "default";
	public static final String BUILTIN_NAMESPACE = "builtin";

	private static SettingsLootRules loot;

	private final ConcurrentHashMap<String, ConcurrentHashMap<String, DungeonSettings>> settingsByNamespace;

	public final static SettingsHouseTheme house = new SettingsHouseTheme();
	public final static SettingsDesertTheme desert = new SettingsDesertTheme();
	public final static SettingsBunkerTheme bunker = new SettingsBunkerTheme();
	public final static SettingsJungleTheme jungle = new SettingsJungleTheme();
	public final static SettingsSwampTheme swamp = new SettingsSwampTheme();
	public final static SettingsMountainTheme mountain = new SettingsMountainTheme();
	public final static SettingsForestTheme forest = new SettingsForestTheme();
	public final static SettingsMesaTheme mesa = new SettingsMesaTheme();
	public final static SettingsIceTheme ice = new SettingsIceTheme();
	public final static SettingsRuinTheme ruin = new SettingsRuinTheme();
	public final static SettingsRareTheme rare = new SettingsRareTheme();

	public SettingsContainer() {
		this.settingsByNamespace = new ConcurrentHashMap<>();

		this.put(new SettingsRooms());
		this.put(new SettingsSecrets());
		this.put(new SettingsSegments());
		this.put(new SettingsLayout());
		this.put(new SettingsTheme());

		loot = new SettingsLootRules();

		this.put(loot);
		this.put(new SettingsBase());

		this.put(house); // HOUSE
		this.put(desert); // PYRAMID
		this.put(bunker); // BUNKER
		this.put(jungle); // JUNGLE
		this.put(swamp); // WITCH
		this.put(mountain); // ENIKO
		this.put(forest); // ROGUE
		this.put(mesa); // ETHO
		this.put(ice); // PYRAMID
		this.put(ruin); // PYRAMID
		this.put(rare); // BUMBO
	}

	public static final String CONFIG_DIR_NAME = Main.instance.getDataFolder().toString() + File.separator
			+ "forge_sandbox" + File.separator + "roguelike";

	public void parseCustomSettings(Map<String, String> files) throws Exception {
		for (String name : files.keySet()) {
			DungeonSettings toAdd = null;
			try {
				toAdd = parseFile(files.get(name));
			} catch (Exception e) {
				throw new Exception("Error in: " + name + " : " + e.getMessage());
			}
			this.put(toAdd);
		}
	}

	@SuppressWarnings("deprecation")
	private DungeonSettings parseFile(String content) throws Exception {

		JsonParser jParser = new JsonParser();
		JsonObject root = null;
		DungeonSettings toAdd;

		try {
			root = (JsonObject) jParser.parse(content);
		} catch (JsonSyntaxException e) {
			Throwable cause = e.getCause();
			throw new Exception(cause.getMessage());
		} catch (Exception e) {
			throw new Exception("An unknown error occurred while parsing json");
		}

		toAdd = new DungeonSettings(root);

		return toAdd;
	}

	public final void put(DungeonSettings setting) {
		String namespace = setting.getNameSpace() != null ? setting.getNameSpace() : BUILTIN_NAMESPACE; // DEFAULT_NAMESPACE;
		String name = setting.getName();

		if (!settingsByNamespace.containsKey(namespace)) {
			settingsByNamespace.put(namespace, new ConcurrentHashMap<>());
		}

		Map<String, DungeonSettings> settings = this.settingsByNamespace.get(namespace);
		settings.put(name, setting);
	}

	@Override
	public Collection<DungeonSettings> getByNamespace(String namespace) {
		if (!this.settingsByNamespace.containsKey(namespace))
			return new ArrayList<>();
		return this.settingsByNamespace.get(namespace).values();
	}

	@Override
	public Collection<DungeonSettings> getBuiltinSettings() {
		List<DungeonSettings> settings = new ArrayList<>();

		for (String namespace : settingsByNamespace.keySet()) {
			if (!namespace.equals(SettingsContainer.BUILTIN_NAMESPACE))
				continue;
			settings.addAll(settingsByNamespace.get(namespace).values());
		}

		return settings;
	}

	@Override
	public Collection<DungeonSettings> getCustomSettings() {

		List<DungeonSettings> settings = new ArrayList<>();

		for (String namespace : settingsByNamespace.keySet()) {
			if (namespace.equals(SettingsContainer.BUILTIN_NAMESPACE))
				continue;
			settings.addAll(settingsByNamespace.get(namespace).values());
		}

		return settings;
	}

	@Override
	public DungeonSettings get(SettingIdentifier id) {
		if (!contains(id))
			return null;
		Map<String, DungeonSettings> settings = settingsByNamespace.get(id.getNamespace());
		return settings.get(id.getName());
	}

	@Override
	public boolean contains(SettingIdentifier id) {

		if (!settingsByNamespace.containsKey(id.getNamespace()))
			return false;

		Map<String, DungeonSettings> settings = settingsByNamespace.get(id.getNamespace());
		return settings.containsKey(id.getName());
	}

	@Override
	public String toString() {
		String strg = "";
		for (String namespace : settingsByNamespace.keySet()) {
			Map<String, DungeonSettings> settings = settingsByNamespace.get(namespace);

			for (DungeonSettings setting : settings.values()) {
				strg += setting.id.toString() + " ";
			}
		}

		return strg;
	}
}
