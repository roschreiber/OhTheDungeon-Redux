package otd.config.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import otd.config.YamlPluginConfig;

public class MySqlConfig extends Database {

	public MySqlConfig(JavaPlugin instance) {
		super(instance);
	}

	public String MySqlCreateTokensTable = "CREATE TABLE IF NOT EXISTS otd (" + "`key` varchar(32) NOT NULL,"
			+ "`value` TEXT NOT NULL," + "PRIMARY KEY (`key`)" + ");";

	// SQL creation stuff, You can leave the blow stuff untouched.
	public Connection getSQLConnection() {

		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}
			connection = DriverManager.getConnection("jdbc:mysql://" + YamlPluginConfig.host + ":"
					+ YamlPluginConfig.port + "/" + YamlPluginConfig.database, YamlPluginConfig.user,
					YamlPluginConfig.password);
			return connection;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
		}

		return null;
	}

	public void load() {
		connection = getSQLConnection();
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(MySqlCreateTokensTable);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initialize();
	}
}