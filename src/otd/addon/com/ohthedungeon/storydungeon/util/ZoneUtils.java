package otd.addon.com.ohthedungeon.storydungeon.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import otd.Main;

/**
 *
 * @author shadow_wind
 */
public class ZoneUtils {
    
    public static class ZonePos {
        int cx, cy;
        int r;
        public ZonePos() {
            cx = 48;
            cy = 48;
            r = 48;
        }
    }
    
    public static ZonePos zonePos = new ZonePos();
    
    public static void saveZonePos() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(zonePos);
        JavaPlugin plugin = Main.instance;
        // make sure file exists
        String path = plugin.getDataFolder().getParentFile().toString() + File.separator + "PerPlayerDungeonInstance";
        File configDir = new File(path);
	if(!configDir.exists()){
            configDir.mkdir();
	}
//        Bukkit.getLogger().log(Level.SEVERE, "save " + json);
        String world_config_file = configDir.toString() + File.separator + "zone.json";
        File file = new File(world_config_file);
        try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
            oStreamWriter.append(json);
            oStreamWriter.close();
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot save zone.json");
        }
    }
    
    public static void loadZonePos() {
        JavaPlugin plugin = Main.instance;
        // make sure file exists
        String path = plugin.getDataFolder().getParentFile().toString() + File.separator + "PerPlayerDungeonInstance";
        File configDir = new File(path);
	if(!configDir.exists()){
            configDir.mkdir();
	}
        
        String world_config_file = configDir.toString() + File.separator + "zone.json";
        File cfile = new File(world_config_file);
        if(!cfile.exists()){
            try {
                cfile.createNewFile();
                saveZonePos();
            } catch (IOException ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Cannot save zone.json");
            }
        }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(world_config_file), "UTF8"))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            zonePos = (new Gson()).fromJson(sb.toString(), ZonePos.class);
        } catch (IOException ex) {
            zonePos = new ZonePos();
        }
        //update();
    }

    
    public static int[] getNextZone() {
        int x = zonePos.cx;
        int y = zonePos.cy;
        int r = zonePos.r;
        if(x > 0 && x == y) {
            x = x - 32;
            zonePos.cx = x;
            zonePos.cy = y;
            zonePos.r = r;
            return new int[] {x,y};
        }
        if(x < 0 && (-x) == y) {
            y = y - 32;
            zonePos.cx = x;
            zonePos.cy = y;
            zonePos.r = r;
            return new int[] {x,y};
        }
        if(x < 0 && x == y) {
            x = x + 32;
            zonePos.cx = x;
            zonePos.cy = y;
            zonePos.r = r;
            return new int[] {x,y};
        }
        if(x > 0 && (-y) == x) {
            y = y + 32;
            zonePos.cx = x;
            zonePos.cy = y;
            zonePos.r = r;
            return new int[] {x,y};
        }
        if(y == r) {
            x = x - 32;
            zonePos.cx = x;
            zonePos.cy = y;
            zonePos.r = r;
            return new int[] {x,y};
        }
        if(x == -r) {
            y = y - 32;
            zonePos.cx = x;
            zonePos.cy = y;
            zonePos.r = r;
            return new int[] {x,y};
        }
        if(y == -r) {
            x = x + 32;
            zonePos.cx = x;
            zonePos.cy = y;
            zonePos.r = r;
            return new int[] {x,y};
        }
        if(x == r) {
            y = y + 32;
            if(y == r) {
                x = x + 32;
                y = y + 32;
                r = r + 32;
            }
            zonePos.cx = x;
            zonePos.cy = y;
            zonePos.r = r;
            return new int[] {x,y};
        }
        return null;
    }
    
    public static void main(String[] args) {
        for(int i = 0; i < 60; i++) System.out.println(Arrays.toString(getNextZone()));
    }
}
