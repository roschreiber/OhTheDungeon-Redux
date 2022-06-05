package otd.config.storage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class SQLiteConfig extends Database {
	String dbname = "config.db";

	public SQLiteConfig(JavaPlugin instance) {
		super(instance);
	}

	public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS otd (" + "`key` varchar(32) NOT NULL,"
			+ "`value` TEXT NOT NULL," + "PRIMARY KEY (`key`)" + ");";

	// SQL creation stuff, You can leave the blow stuff untouched.
	public Connection getSQLConnection() {
		File dataFolder = new File(plugin.getDataFolder(), dbname);
		if (!dataFolder.exists()) {
			try {
				dataFolder.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().log(Level.SEVERE, "File write error: " + dbname);
			}
		}
		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
			return connection;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
		} catch (ClassNotFoundException ex) {
			plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
		}
		return null;
	}

	public void load() {
		connection = getSQLConnection();
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(SQLiteCreateTokensTable);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initialize();
	}
}