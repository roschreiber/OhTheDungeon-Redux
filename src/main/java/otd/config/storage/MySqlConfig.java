package otd.config.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Deprecated - MySQL support is no longer used, there is no sense in maintaining it..
 */
public class MySqlConfig extends Database {

	public MySqlConfig(JavaPlugin instance) {
		super(instance);
	}

	public String MySqlCreateTokensTable = "CREATE TABLE IF NOT EXISTS otd (" + "`key` varchar(32) NOT NULL,"
			+ "`value` TEXT NOT NULL," + "PRIMARY KEY (`key`)" + ");";

	public Connection getSQLConnection() {
		return null;
	}

	public void load() {
		connection = getSQLConnection();
		if (connection == null) return;
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