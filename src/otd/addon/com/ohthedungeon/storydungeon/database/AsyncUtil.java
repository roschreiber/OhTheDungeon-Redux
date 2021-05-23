/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.database;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import otd.Main;

/**
 *
 * @author shadow_wind
 */
public class AsyncUtil {
    public static void asyncAddDungeon(Player player, int dx, int dz) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            PerPlayerDungeonInstance.getInstance().getDatabase().addDungeon(player, dx, dz);
        });
    }
}
