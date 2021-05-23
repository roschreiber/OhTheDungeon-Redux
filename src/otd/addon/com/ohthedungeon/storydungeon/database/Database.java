/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Database {
    JavaPlugin plugin;
    Connection connection;
    // The name of the table we created back in SQLite class.
    public String table = "dungeon_table";
    public int tokens = 0;
    public Database(JavaPlugin instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE player = 'test'");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
   
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }

    // These are the methods you can use to get things out of your database. You of course can make new ones to return different things in the database.
    // This returns the number of people the player killed.
    public Integer getDungeonCount(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '"+string+"';");
   
            rs = ps.executeQuery();
            int res = 0;
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(string.toLowerCase())){ // Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                    res++;
                }
            }
            return res;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }
    // Exact same method here, Except as mentioned above i am looking for total!
    public String getOwner(int dx, int dy) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            String key = dx + "," + dy;
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE key = '"+key+"';");
   
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getInt("dx") == dx && rs.getInt("dy") == dy) {
                    return rs.getString("player");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return null;
    }
    
    public void addDungeon(Player player, Integer dx, Integer dy) {
        this.addDungeon(player.getUniqueId().toString(), dx, dy);
    }

// Now we need methods to save things to the database
    private void addDungeon(String player, Integer dx, Integer dy) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (key,player,dx,dy) VALUES(?,?,?,?)"); // IMPORTANT. In SQLite class, We made 3 colums. player, Kills, Total.
            ps.setString(1, dx+","+dy);
            ps.setString(2, player.toLowerCase());
            ps.setInt(3, dx);
            ps.setInt(4, dy);
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;      
    }


    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }
}

