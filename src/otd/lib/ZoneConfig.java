/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.lib;

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
        return sb.toString();
    }
    
    public ZoneConfig(Biome b, String g, String p, ZoneDungeonType t) {
        biome = b.toString();
        generator = g;
        populator = p;
        type = t;
    }
    
    public ZoneConfig(String str) {
        String[] strs = str.split(",");
        if(strs.length != 4) {
            biome = Biome.PLAINS.toString();
        } else {
            biome = strs[0];
            generator = strs[1];
            populator = strs[2];
            type = ZoneDungeonType.valueOf(strs[3]);
        }
    }
}
