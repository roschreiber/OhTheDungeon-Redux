/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class SQLite extends Database {
	String dbname;

	public SQLite(JavaPlugin instance) {
		super(instance);
		dbname = "dungeon";
	}

	public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS dungeon_table (" + // make sure to put your
																							// table name in here too.
			"`key` varchar(32) NOT NULL," + "`player` varchar(32) NOT NULL," + // This creates the different colums you
																				// will save data too. varchar(32) Is a
																				// string, int = integer
			"`dx` int(11) NOT NULL," + "`dy` int(11) NOT NULL," + "PRIMARY KEY (`key`)" + // This is creating 3 colums
																							// Player, Kills, Total.
																							// Primary key is what you
																							// are going to use as your
																							// indexer. Here we want to
																							// use player so
			");"; // we can search by player, and get kills and total. If you some how were
					// searching kills it would provide total and player.

	// SQL creation stuff, You can leave the blow stuff untouched.
	@Override
	public Connection getSQLConnection() {
		File dataFolder = new File(
				plugin.getDataFolder().getParentFile().toString() + File.separator + "PerPlayerDungeonInstance",
				dbname + ".db");
		if (!dataFolder.exists()) {
			try {
				dataFolder.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().log(Level.SEVERE, "File write error: " + dbname + ".db");
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

	@Override
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
