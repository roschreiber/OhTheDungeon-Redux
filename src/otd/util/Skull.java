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
package otd.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author shadow
 */
public class Skull {    
    public static SkullMeta applyHead(String uuid, String textures, SkullMeta headMeta) {
        GameProfile profile = new GameProfile(UUID.fromString(uuid), null);
        profile.getProperties().put("textures", new Property("textures", textures));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NullPointerException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            //e1.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[Oh The Dungeons] Dice is not working properly");
        }
        
        return headMeta;
    }
    
    public static ItemStack getHead(String uuid, String textures) {
        ItemStack is = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im = applyHead(uuid, textures, im);
        is.setItemMeta(im);
        return is;
    }
}
