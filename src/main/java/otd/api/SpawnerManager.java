package otd.api;

import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.persistence.PersistentDataType;

import otd.Main;
import otd.util.PluginKeys;

public class SpawnerManager {
	private final NamespacedKey decry;
	private static SpawnerManager INSTANCE = new SpawnerManager();

	private SpawnerManager() {
		decry = new NamespacedKey(Main.instance, PluginKeys.decry);
	}

	public static SpawnerManager instance() {
		return INSTANCE;
	}

	public void setOtdSpawnerTag(CreatureSpawner cs) {
		cs.getPersistentDataContainer().set(decry, PersistentDataType.BYTE, (byte) 15);
	}

	public boolean isOtdSpawner(CreatureSpawner cs) {
		return cs.getPersistentDataContainer().has(decry, PersistentDataType.BYTE);
	}
}
