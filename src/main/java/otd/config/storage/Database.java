package otd.config.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import otd.util.GZIPUtils;

public abstract class Database implements ConfigImpl {
	JavaPlugin plugin;
	Connection connection;
	public String table = "otd";
	public int tokens = 0;

	public Database(JavaPlugin instance) {
		plugin = instance;
	}

	public abstract Connection getSQLConnection();

	public abstract void load();

	public void initialize() {
		connection = getSQLConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE `key` = ?");
			ps.setString(1, "config");
			ResultSet rs = ps.executeQuery();
			close(ps, rs);

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		}
	}

	public String getValue(String string) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE `key` = '" + string + "';");

			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("key").equalsIgnoreCase(string.toLowerCase())) {
					String value = rs.getString("value");
					return GZIPUtils.uncompress(value);
				}
			}
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Config load/store error", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, "Config load/store error", ex);
			}
		}
		return null;
	}

// Now we need methods to save things to the database
	public void setValue(String key, String value) {
		value = GZIPUtils.compress(value);
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement("REPLACE INTO " + table + " (`key`,`value`) VALUES(?,?)");
			ps.setString(1, key);

			ps.setString(2, value);
			ps.executeUpdate();
			return;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Config load/store error", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, "Config load/store error", ex);
			}
		}
		return;
	}

	public void close(PreparedStatement ps, ResultSet rs) {
		try {
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Config load/store error", ex);
		}
	}

	public void close() {
		try {
			if (this.connection != null && !this.connection.isClosed())
				this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
