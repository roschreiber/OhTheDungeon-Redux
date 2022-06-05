package otd.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import otd.Main;
import otd.config.storage.SimpleConfig;
import otd.config.storage.SimpleConfigManager;

public class YamlPluginConfig {
	public static boolean update = true;

	public static enum DATA_TYPE {
		MYSQL, SQLITE
	};

	public static DATA_TYPE type = DATA_TYPE.SQLITE;
	public static String host;
	public static int port;
	public static String database;
	public static String user;
	public static String password;

	private final static String[] header = { "data_type could be SQLITE/MYSQL" };

	public static void init() throws IOException {
		JavaPlugin plugin = Main.instance;
		// make sure file exists
		File configDir = plugin.getDataFolder();
		if (!configDir.exists()) {
			configDir.mkdir();
		}

		SimpleConfigManager manager = new SimpleConfigManager(Main.instance);

		String config_file = configDir.toString() + File.separator + "settings.json";
		File cfile = new File(config_file);
		if (cfile.exists()) {
			cfile.delete();
		}
		config_file = configDir.toString() + File.separator + "settings.yml";
		cfile = new File(config_file);
		if (!cfile.exists()) {
			cfile.createNewFile();
			SimpleConfig config = manager.getNewConfig("settings.yml", header);
//			FileConfiguration config = YamlConfiguration.loadConfiguration(cfile);
			config.set("check_update", true);
			config.set("data_type", "SQLITE", "SQLITE/MYSQL");
			config.set("MYSQL.host", "localhost");
			config.set("MYSQL.port", 3306);
			config.set("MYSQL.database", "db");
			config.set("MYSQL.user", "username");
			config.set("MYSQL.password", "passy");
			config.saveConfig();
		}

		FileConfiguration config = YamlConfiguration.loadConfiguration(cfile);
		update = config.getBoolean("check_update", true);
		type = DATA_TYPE.valueOf(config.getString("data_type", "SQLITE").toUpperCase());
		host = config.getString("MYSQL.host", "localhost");
		port = config.getInt("MYSQL.port", 3306);
		database = config.getString("MYSQL.database", "db");
		user = config.getString("MYSQL.user", "username");
		password = config.getString("MYSQL.password", "passy");
	}
}
