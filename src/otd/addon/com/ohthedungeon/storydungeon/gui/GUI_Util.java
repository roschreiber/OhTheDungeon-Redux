/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.gui;

import org.bukkit.entity.Player;

/**
 *
 * @author shadow_wind
 */
public class GUI_Util {
    public static void showMenu(Player player) {
        Menu menu = new Menu();
        menu.openInventory(player);
    }
}
