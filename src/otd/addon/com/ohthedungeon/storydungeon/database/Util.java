/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import otd.Main;
import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;

/**
 *
 * @author shadow_wind
 */
public class Util {
    public static void addDungeon(Player player, int dx, int dy) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            PerPlayerDungeonInstance.getInstance().getDatabase().addDungeon(player, dx, dy);
        });
    }
}
