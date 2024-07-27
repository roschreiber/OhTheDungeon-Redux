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

/**
 *
 * @author shadow
 */
public class MainMenu extends Content {

	private final static int SLOT = 27;
	public final static MainMenu instance = new MainMenu();

	public MainMenu() {
		super(I18n.instance.Main_Menu, SLOT);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		ItemStack dummy = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		for (int i = 0; i < SLOT; i++) {
			addItem(i, dummy);
		}
		{
			ItemStack is = Skull.EARTH.getItem();
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Menu1);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Menu1_Lore1);
			lores.add(I18n.instance.Menu1_Lore2);
			lores.add(ChatColor.YELLOW + I18n.instance.Menu1_Lore3);
			lores.add(I18n.instance.Menu1_Lore4);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(1, 1, is);
		}
		{
			ItemStack is = Skull.CITY.getItem();
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Menu2);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Menu2_Lore1);
			lores.add(I18n.instance.Menu2_Lore2);
			lores.add(ChatColor.YELLOW + I18n.instance.Menu2_Lore3);
			lores.add(I18n.instance.Menu2_Lore4);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(1, 4, is);
		}
		{
			ItemStack is = Skull.TOOL.getItem();
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Util_Menu);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Util_Menu_Lore);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(1, 7, is);
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
		if (slot == 10) {
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
		if (slot == 16) {
			UtilMenu um = new UtilMenu(holder);
			um.openInventory(p);
		}
	}
}
