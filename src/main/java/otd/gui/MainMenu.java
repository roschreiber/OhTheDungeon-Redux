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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.md_5.bungee.api.ChatColor;
import otd.config.WorldConfig;
import otd.gui.dungeon_plot.CreateDungeonWorld;
import otd.gui.dungeon_plot.RemoveDungeonWorld;
import otd.util.I18n;
import otd.util.Skull;
import otd.world.DungeonTask;

import static otd.redux.util.MenuHelper.*;

/**
 *
 * @author shadow
 */
public class MainMenu extends Content {

	private final static int SLOT = 27;
	public final static MainMenu instance = new MainMenu();

	public MainMenu() {
		super(color(ACCENT) + I18n.instance.Main_Menu, SLOT);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		fillBorder(this, 3);

		{
			ItemStack is = Skull.EARTH.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(gradient(I18n.instance.Menu1, "#2ECC71", "#1ABC9C"));
			List<String> lores = new ArrayList<>();
			lores.add(separator());
			lores.add(desc(I18n.instance.Menu1_Lore1));
			lores.add(desc(I18n.instance.Menu1_Lore2));
			lores.add(color(WARNING) + I18n.instance.Menu1_Lore3);
			lores.add(desc(I18n.instance.Menu1_Lore4));
			lores.add(separator());
			lores.add(actionHint("Click to open"));
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 2, is);
		}

		{
			ItemStack is = Skull.CITY.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(gradient(I18n.instance.Menu2, "#E67E22", "#F39C12"));
			List<String> lores = new ArrayList<>();
			lores.add(separator());
			lores.add(desc(I18n.instance.Menu2_Lore1));
			lores.add(desc(I18n.instance.Menu2_Lore2));
			lores.add(color(WARNING) + I18n.instance.Menu2_Lore3);
			lores.add(desc(I18n.instance.Menu2_Lore4));
			lores.add(separator());
			lores.add(actionHint("Click to open"));
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 4, is);
		}

		{
			ItemStack is = Skull.TOOL.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(gradient(I18n.instance.Util_Menu, "#9B59B6", "#8E44AD"));
			List<String> lores = new ArrayList<>();
			lores.add(separator());
			lores.add(desc(I18n.instance.Util_Menu_Lore));
			lores.add(separator());
			lores.add(actionHint("Click to open"));
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 6, is);
		}
		{
			ItemStack is = new ItemStack(Material.PAPER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(color(SECONDARY) + "Language");
			List<String> lores = new ArrayList<>();
			lores.add(separator());
			lores.add(desc("Current " + WorldConfig.wc.language));
			lores.add(separator());
			lores.add(actionHint("Click to open"));
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(26, is);
		}
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof MainMenu)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		MainMenu holder = (MainMenu) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (slot == 11) {
			WorldManager wm = new WorldManager(holder);
			wm.openInventory(p);
		}
		if (slot == 13) {
			if (DungeonTask.isGenerating()) {
				p.sendMessage(ChatColor.BLUE + I18n.instance.Dungeon_Plot_In_Progress);
				return;
			}
			if (WorldConfig.wc.dungeon_world.finished) {
				RemoveDungeonWorld r = new RemoveDungeonWorld(holder);
				r.openInventory(p);
			} else {
				CreateDungeonWorld c = new CreateDungeonWorld(holder);
				c.openInventory(p);
			}
		}
		if (slot == 15) {
			UtilMenu um = new UtilMenu(holder);
			um.openInventory(p);
		}
		if (slot == 26) {
            LanguageGUI lg = new LanguageGUI(holder);
            lg.openInventory(p);
        }
	}
}
