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
package otd.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author
 */
public class Content implements InventoryHolder, Listener {
    protected final Inventory inv;
    
    public String title;
    
    @Override
    public Inventory getInventory() {
        return inv;
    }
    
    public void openInventory(Player p) {
        init();
        p.openInventory(inv);
    }
    
    protected void kcancel(InventoryClickEvent e) {
//        Bukkit.getLogger().log(Level.SEVERE, this.getClass().toString());
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) {
            return;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            kcancel(e);
        }
        kcancel(e);

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        // Using slots click is a best option for your inventory click's
        if (e.getRawSlot() == 10) p.sendMessage("You clicked at slot " + e.getRawSlot());
    }
        
    public final void addItem(int x, int y, ItemStack item) {
        int index = x * 9 + y;
        inv.setItem(index, item);
    }
    
    public final void addItem(int index, ItemStack item) {
        inv.setItem(index, item);
    }
    
    public Content(String title, int slot) {
        this.title = title;
        inv = Bukkit.createInventory(this, slot, this.title);
    }
        
    public void init() {
        
    }
}
