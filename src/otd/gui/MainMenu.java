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

import otd.gui.dungeon_plot.CreateDungeonWorld;
import otd.gui.dungeon_plot.RemoveDungeonWorld;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import otd.util.Diagnostic;
import otd.config.WorldConfig;
import otd.gui.customstruct.CustomDungeonList;
import otd.gui.storydungeon.PPDI_Config;
import otd.integration.WorldEdit;
import otd.world.DungeonTask;
import otd.util.I18n;
import otd.integration.PlaceholderAPI;

/**
 *
 * @author shadow
 */
public class MainMenu extends Content {
    
    public final static MainMenu instance = new MainMenu();
    
    public MainMenu() {
        super(I18n.instance.Main_Menu, 9);
    }
    
    @Override
    public void init() {
        inv.clear();
        {
            ItemStack is = new ItemStack(Material.MAP);
            ItemMeta im = is.getItemMeta();
            
            im.setDisplayName(I18n.instance.Menu1);
            List<String> lores = new ArrayList<>();
            lores.add(I18n.instance.Menu1_Lore1);
            lores.add(I18n.instance.Menu1_Lore2);
            lores.add(I18n.instance.Menu1_Lore3);
            lores.add(I18n.instance.Menu1_Lore4);
            im.setLore(lores);
            
            is.setItemMeta(im);
            
            addItem(0, is);
        }
        {
            ItemStack is = new ItemStack(Material.COMPASS);
            ItemMeta im = is.getItemMeta();
            
            im.setDisplayName(I18n.instance.Menu2);
            List<String> lores = new ArrayList<>();
            lores.add(I18n.instance.Menu2_Lore1);
            lores.add(I18n.instance.Menu2_Lore2);
            lores.add(I18n.instance.Menu2_Lore3);
            lores.add(I18n.instance.Menu2_Lore4);
            im.setLore(lores);
            
            is.setItemMeta(im);
            
            addItem(1, is);
        }
        {
            ItemStack is = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
            ItemMeta im = is.getItemMeta();
            
            im.setDisplayName(I18n.instance.Automatic_Diagnostic);
            
            is.setItemMeta(im);
            
            addItem(2, is);
        }
        {
            ItemStack is = new ItemStack(Material.FEATHER);
            ItemMeta im = is.getItemMeta();
            
            im.setDisplayName(I18n.instance.Config_Backup);
            
            is.setItemMeta(im);
            
            addItem(3, is);
        }
        {
            ItemStack is = new ItemStack(Material.CHEST);
            ItemMeta im = is.getItemMeta();
            
            im.setDisplayName(I18n.instance.Creative_Inventory);
            
            is.setItemMeta(im);
            
            addItem(4, is);
        }
        {
            ItemStack is = new ItemStack(WorldEdit.isReady() ? Material.STRUCTURE_BLOCK : Material.BARRIER);
            ItemMeta im = is.getItemMeta();
            
            im.setDisplayName(I18n.instance.Custom_Dungeon);
            
            List<String> lores = new ArrayList<>();
            lores.add(I18n.instance.Require_WorldEdit);
            
            im.setLore(lores);
            is.setItemMeta(im);
            
            addItem(5, is);
        }
        {
            ItemStack is = new ItemStack(Material.OAK_SIGN);
            ItemMeta im = is.getItemMeta();
            
            im.setDisplayName(I18n.instance.PAPI_Title);
            
            addItem(6, is);
        }
        {
            ItemStack is = new ItemStack(Material.BOOKSHELF);
            ItemMeta im = is.getItemMeta();
            
            im.setDisplayName(I18n.instance.PerPlayerDungeonInstance);
            List<String> lores = new ArrayList<>();
            lores.add(I18n.instance.PerPlayerDungeonInstance_Lore);
            im.setLore(lores);
            
            addItem(7, is);
        }
    }
    
    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof MainMenu)) {
            return;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            kcancel(e);
            return;
        }

        kcancel(e);
        int slot = e.getRawSlot();
        Player p = (Player) e.getWhoClicked();
        MainMenu holder = (MainMenu) e.getInventory().getHolder();
        if(holder == null) return;
        if(slot == 0) {
            WorldManager wm = new WorldManager();
            wm.openInventory(p);
        }
        if(slot == 1) {
            if(DungeonTask.isGenerating()) {
                p.sendMessage(ChatColor.BLUE + I18n.instance.Dungeon_Plot_In_Progress);
                return;
            }
            if(WorldConfig.wc.dungeon_world.finished) {
                RemoveDungeonWorld r = new RemoveDungeonWorld();
                r.openInventory(p);
            } else {
                CreateDungeonWorld c = new CreateDungeonWorld();
                c.openInventory(p);
            }
        }
        if(slot == 2) {
            p.closeInventory();
            Diagnostic.check(p);
        }
        if(slot == 3) {
            BackupGUI backup = new BackupGUI(holder);
            backup.openInventory(p);
        }
        if(slot == 4) {
            CreativeInventory ci = new CreativeInventory();
            ci.openInventory(p);
        }
        if(slot == 5) {
            if(!WorldEdit.isReady()) return;
            CustomDungeonList ci = new CustomDungeonList(this);
            ci.openInventory(p);
        }
        if(slot == 6) {
            PlaceholderAPI.openBook(p);
        }
        if(slot == 7) {
            PPDI_Config cfg = new PPDI_Config();
            cfg.openInventory(p);
        }
    }
}
