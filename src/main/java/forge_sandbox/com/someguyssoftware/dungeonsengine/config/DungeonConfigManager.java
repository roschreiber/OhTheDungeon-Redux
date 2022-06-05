/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeonsengine.config;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import forge_sandbox.com.someguyssoftware.dungeons2.Dungeons2;
import otd.util.ExceptionReporter;

/**
 * @author Mark Gottschling on Dec 20, 2018
 *
 */
public class DungeonConfigManager {

	/*
	 * Guava Table of Dungeon Configs based on Biome Name and Size
	 */
//	public static Table<String, DungeonSize, List<IDungeonConfig>> DUNGEON_CONFIG_TABLE = HashBasedTable.create();
	public static Table<Integer, DungeonSize, List<IDungeonConfig>> DUNGEON_CONFIG_TABLE = HashBasedTable.create();

	public static IDungeonConfig DEFAULT_CONFIG;
	public List<IDungeonConfig> configs;

	/**
	 * 
	 */
	public DungeonConfigManager() {
		Dungeons2.log.debug("instansiating DungeonConfigManager...");
		// load the default config
		try {
			DEFAULT_CONFIG = DungeonConfigLoader.loadDefault();
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, ExceptionReporter.exceptionToString(e));
			// TODO manually set values ?
		}

		// load all the configs
		configs = DungeonConfigLoader.loadAll();
		// map the configs to the table
	}

}
