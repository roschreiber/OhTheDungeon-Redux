/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Content implements InventoryHolder, Listener {
    protected final Inventory inv;
    
    public String title;
    
    public static void registerEvent(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(new Content("", 45), plugin);
    }
    
    @Override
    public Inventory getInventory() {
        return inv;
    }
    
    public void openInventory(Player p) {
        init();
        p.openInventory(inv);
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) {
            return;
        }
        e.setCancelled(true);

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
    
    protected void init() {
        
    }
    
    protected void addRow(int row, Material material) {
        for(int i = 0; i < 9; i++)
            addItem(row, i, new ItemStack(material));
    }
    
    protected void addCol(int col, Material material) {
        int ROW = inv.getSize() / 9;
        for(int i = 0; i < ROW; i++)
            addItem(i, col, new ItemStack(material));
    }
}
