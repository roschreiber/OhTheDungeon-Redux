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
package otd.gui.customstruct;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import otd.config.WorldConfig;
import otd.config.WorldConfig.CustomDungeon;
import otd.gui.Content;
import otd.util.I18n;

/**
 *
 * @author shadow
 */
public class MobSelect extends Content {
    public final static MobSelect instance = new MobSelect();
    
    private final static int SLOT = 54;
    public final Content parent;
    public final CustomDungeon dungeon;
    public int offset = 0;
    
    private final static Material EGG = Material.EGG;
    
    public MobSelect() {
        super(I18n.instance.Mob_Select, SLOT);
        parent = null;
        dungeon = null;
    }
    
    public MobSelect(CustomDungeon dungeon, Content parent) {
        super(I18n.instance.Mob_Select, SLOT);
        this.parent = parent;
        this.dungeon = dungeon;
    }
    
    @Override
    public void init() {
        inv.clear();
        List<String> MOBLIST = new ArrayList<>();
        for(EntityType type : EntityType.values()) {
                MOBLIST.add(type.toString());
        }
        int index = offset * 45;
        int count = 0;
        while(count < 45 && index < MOBLIST.size()) {
            String mob = MOBLIST.get(index);
            Material icon;
            if(dungeon.mobs.contains(mob)) icon = EGG;
            else icon = Material.BARRIER;
            ItemStack is = new ItemStack(icon);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(mob);
            is.setItemMeta(im);
            
            addItem(count, is);
            
            count++;
            index++;
        }
        
        {
            ItemStack is = new ItemStack(Material.END_CRYSTAL);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Previous);
            is.setItemMeta(im);
            
            addItem(5, 0, is);
        }
        
        {
            ItemStack is = new ItemStack(Material.END_CRYSTAL);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Next);
            is.setItemMeta(im);
            
            addItem(5, 1, is);
        }
        
        {
            ItemStack is = new ItemStack(Material.LEVER);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(I18n.instance.Back);
            is.setItemMeta(im);
            
            addItem(5, 8, is);
        }
    }
    
    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof MobSelect)) {
            return;
        }
        
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            kcancel(e);
            return;
        }

        kcancel(e);
        int slot = e.getRawSlot();
        Player p = (Player) e.getWhoClicked();
        MobSelect holder = (MobSelect) e.getInventory().getHolder();
        if(holder == null) return;
        
        if(slot == 0) return;
        
        if(slot == 45) {
            holder.offset--;
            if(holder.offset < 0) holder.offset = 0;
            holder.init();
            return;
        }
        if(slot == 46) {
            holder.offset++;
            holder.init();
            return;
        }
        
        if(slot == 53) {
            holder.parent.openInventory(p);
            return;
        }
        
        if(slot > 53) return;
        
        if(slot >= 0 && slot <= 44) {
            ItemStack is = e.getInventory().getItem(slot);
            if(is != null && (is.getType() == Material.EGG || is.getType() == Material.BARRIER)) {
                String mobName = is.getItemMeta().getDisplayName();
                if(!holder.dungeon.mobs.contains(mobName)) {
                    holder.dungeon.mobs.add(mobName);
                    WorldConfig.save();
                } else {
                    holder.dungeon.mobs.remove(mobName);
                    WorldConfig.save();
                }
                holder.init();
            }
        }
    }
}
