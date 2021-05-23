/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.world;

import org.bukkit.block.Biome;

/**
 *
 * @author shadow_wind
 */
public class ZoneConfig {
    public String biome;
    public String generator;
    public String populator;
    public ZoneDungeonType type;
    public String extra;
    public String player;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(biome);
        sb.append(',');
        sb.append(generator);
        sb.append(',');
        sb.append(populator);
        sb.append(',');
        sb.append(type);
        sb.append(',');
        sb.append(extra);
        sb.append(',');
        sb.append(player);
        return sb.toString();
    }
    
    public ZoneConfig(Biome b, String g, String p, ZoneDungeonType t, String e, String pn) {
        biome = b.toString();
        generator = g;
        populator = p;
        type = t;
        extra = e;
        player = pn;
    }
    
    public ZoneConfig(String str) {
        String[] strs = str.split(",");
        if(strs.length != 6) {
            biome = Biome.PLAINS.toString();
        } else {
            biome = strs[0];
            generator = strs[1];
            populator = strs[2];
            type = ZoneDungeonType.valueOf(strs[3]);
            extra = strs[4];
            player = strs[5];
        }
    }
}
