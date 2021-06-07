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
package otd.integration;

import forge_sandbox.greymerk.roguelike.treasure.loot.BookBase;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import otd.Main;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.populator.DungeonPopulator;
import otd.util.I18n;

/**
 *
 * @author shadow
 */
public class PlaceholderAPI {
    private final static String identifier = "otd";
    
    private static class BookNotice extends BookBase{
        public BookNotice(){
            super("Shadow_Wind", "PlaceHolderAPI");
            this.addPage("otd_<dungeontype>_<worldname>\n\nShow dungeon status on world\nDungeon Type:\nall,roguelike,doomlike,battletower,smoofy,draylar,antman,aether,lich,custom");
        }
        
        @Override
	public ItemStack get(){
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
            BookMeta bookMeta = (BookMeta) book.getItemMeta();
            String array_page[] = new String[pages.size()];
            for(int i = 0; i < array_page.length; i++) {
                array_page[i] = pages.get(i);
            }
            bookMeta.addPage(array_page);
            bookMeta.setAuthor(this.author == null ? "Anonymous" : this.author);
            bookMeta.setTitle(this.title == null ? "Book" : this.title);
                
            book.setItemMeta(bookMeta);		
            return book;
	}
    }
    private final static ItemStack BOOK = (new BookNotice()).get();
    
    public static void openBook(Player p) {
        p.openBook(BOOK);
    }
    
    public static void enable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getLogger().info("Loading PlaceholderAPI support ...");
            new WorldStatus(Main.mainInstance).register();
        }
    }
    
    public static class WorldStatus extends PlaceholderExpansion {
        private Main plugin;
        public WorldStatus(Main plugin) {
            this.plugin = plugin;
        }
        
        @Override
        public boolean persist() {
            return true;
        }
        
        @Override
        public boolean canRegister() {
            return true;
        }
        
        @Override
        public String getAuthor(){
            return plugin.getDescription().getAuthors().toString();
        }
        
        @Override
        public String getIdentifier(){
            return identifier;
        }
        
        @Override
        public String getVersion(){
            return plugin.getDescription().getVersion();
        }
        
        @Override
        public String onPlaceholderRequest(Player player, String identifier){            
            if(!plugin.isEnabled()) return null;
            
            String[] array = identifier.split("_");
            if(array != null && array.length >= 2) {
                int start = identifier.indexOf("_");
                String worldName = identifier.substring(start + 1);
                if(Bukkit.getWorld(worldName) != null) {
                    if(WorldConfig.wc.dict.containsKey(worldName)) {
                        SimpleWorldConfig swc = WorldConfig.wc.dict.get(worldName);
                        
                        boolean res;
                        if(array[0].equalsIgnoreCase("all")) {
                            res = DungeonPopulator.isDungeonEnabled(swc);
                        } else if(array[0].equalsIgnoreCase("roguelike")) {
                            res = swc.roguelike.doNaturalSpawn;
                        } else if(array[0].equalsIgnoreCase("doomlike")) {
                            res = swc.doomlike.doNaturalSpawn;
                        } else if(array[0].equalsIgnoreCase("battletower")) {
                            res = swc.battletower.doNaturalSpawn;
                        } else if(array[0].equalsIgnoreCase("smoofy")) {
                            res = swc.smoofydungeon.doNaturalSpawn;
                        } else if(array[0].equalsIgnoreCase("draylar")) {
                            res = swc.draylar_battletower.doNaturalSpawn;
                        } else if(array[0].equalsIgnoreCase("antman")) {
                            res = swc.ant_man_dungeon.doNaturalSpawn;
                        } else if(array[0].equalsIgnoreCase("aether")) {
                            res = swc.aether_dungeon.doNaturalSpawn;
                        } else if(array[0].equalsIgnoreCase("lich")) {
                            res = swc.lich_tower.doNaturalSpawn;
                        } else if(array[0].equalsIgnoreCase("custom")) {
                            res = WorldEdit.isReady() && !swc.custom_dungeons.isEmpty();
                        } else {
                            return null;
                        }
                        
                        return res ? I18n.instance.Status_True : I18n.instance.Status_False;
                    }
                }
            }

            return null;
        }
    }
}
