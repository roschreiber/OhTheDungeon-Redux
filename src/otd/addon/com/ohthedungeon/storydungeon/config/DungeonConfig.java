/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import otd.Main;

/**
 *
 * @author shadow_wind
 */
public class DungeonConfig {
    private static DungeonConfig instance;
    
    private boolean enableMoneyPayment = true;
    private int money = 10000;
    
    private boolean enableLevelPayment = true;
    private int level = 30;
    
    private int version = 1;
    
    public static boolean getEnableMoneyPayment() {
        return instance.enableMoneyPayment;
    }
    public static void setEnableMoneyPayment(boolean value) {
        instance.enableMoneyPayment = value;
    }
    public static int getMoney() {
        return instance.money;
    }
    public static void setMoney(int money) {
        instance.money = money;
    }
    public static boolean getEnableLevelPayment() {
        return instance.enableLevelPayment;
    }
    public static void setEnableLevelPayment(boolean value) {
        instance.enableLevelPayment = value;
    }
    public static int getLevel() {
        return instance.level;
    }
    public static void setLevel(int level) {
        instance.level = level;
    }
    
    public static void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(instance);
        JavaPlugin plugin = Main.instance;
        // make sure file exists
        String path = plugin.getDataFolder().getParentFile().toString() + File.separator + "PerPlayerDungeonInstance";
        File configDir = new File(path);
	if(!configDir.exists()){
            configDir.mkdir();
	}
        
        String world_config_file = configDir.toString() + File.separator + "dungeon_config.json";
        File file = new File(world_config_file);
        try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
            oStreamWriter.append(json);
            oStreamWriter.close();
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot save dungeon_config.json");
        }
    }
    
    public static void load() {
        JavaPlugin plugin = Main.instance;
        // make sure file exists
        String path = plugin.getDataFolder().getParentFile().toString() + File.separator + "PerPlayerDungeonInstance";
        File configDir = new File(path);
	if(!configDir.exists()){
            configDir.mkdir();
	}
        
        String dungeon_config_file = configDir.toString() + File.separator + "dungeon_config.json";
        File cfile = new File(dungeon_config_file);
        if(!cfile.exists()){
            try {
                instance = new DungeonConfig();
                cfile.createNewFile();
                save();
            } catch (IOException ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Cannot save dungeon_config.json");
            }
        }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dungeon_config_file), "UTF8"))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            instance = (new Gson()).fromJson(sb.toString(), DungeonConfig.class);
        } catch (IOException ex) {
            instance = new DungeonConfig();
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load dungeon_config.json");
        }
    }
}
