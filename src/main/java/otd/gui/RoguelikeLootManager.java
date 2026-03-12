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

import otd.config.RoguelikeLootNode;
import otd.config.WorldConfig;
import otd.util.I18n;
import otd.redux.util.MenuHelper;

/**
 *
 * @author
 */
public class RoguelikeLootManager extends Content {
	public final List<RoguelikeLootNode> loots;
	public final Content parent;
	private final static int SLOT = 54;
	public int offset;

	private RoguelikeLootManager() {
		super("", SLOT);
		loots = null;
		offset = 0;
		parent = null;
	}

	public static RoguelikeLootManager instance = new RoguelikeLootManager();

	public RoguelikeLootManager(List<RoguelikeLootNode> loots, Content parent) {
		super(MenuHelper.color(MenuHelper.SECONDARY) + I18n.instance.Loot_Manager, SLOT);
		this.loots = loots;
		offset = 0;
		this.parent = parent;
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof RoguelikeLootManager)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);

		Player p = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();

		// verify current item is not null
		if (clickedItem == null || clickedItem.getType() == Material.AIR)
			return;

		int slot = e.getRawSlot();
		RoguelikeLootManager holder = (RoguelikeLootManager) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (slot == 0) {
			RoguelikeLootItem li = new RoguelikeLootItem(holder.loots, holder.loots.size(), holder);
			li.openInventory(p);
		}
		if (slot == 6) {
			holder.offset--;
			if (holder.offset < 0)
				holder.offset = 0;
			holder.init();
		}
		if (slot == 7) {
			holder.offset++;
			holder.init();
		}
		if (slot == 8) {
			WorldConfig.save();
			holder.parent.openInventory(p);
		}

		if (slot >= 18 && slot <= 53) {
			int index = holder.offset * 36 + slot - 18;
			if (index >= holder.loots.size())
				return;
			RoguelikeLootItem li = new RoguelikeLootItem(holder.loots, index, holder);
			li.openInventory(p);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		WorldConfig.save();
		inv.clear();
		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Add_New_Loot);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Add));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 0, is);
		}
		addItem(6, MenuHelper.prev(offset + 1));
		addItem(7, MenuHelper.next(offset + 1));
		addItem(8, MenuHelper.apply());
		MenuHelper.fillRow(this, 1);
		{
			int index = 18;
			int i = offset * 36;
			while (index < SLOT && i < loots.size()) {
				RoguelikeLootNode it = loots.get(i);
				ItemStack is = it.getItem().clone();
				ItemMeta im = is.getItemMeta();
				if (im != null) {
					List<String> lores;
					if (im.hasLore())
						lores = im.getLore();
					else
						lores = new ArrayList<>();
					lores.add(0, MenuHelper.actionHint(I18n.instance.Click_To_Edit));
					lores.add(0, MenuHelper.separator());
					lores.add(0, MenuHelper.value(I18n.instance.Min_Item, it.min));
					lores.add(0, MenuHelper.value(I18n.instance.Max_Item, it.max));
					lores.add(0, MenuHelper.value(I18n.instance.Loot_Weight, Integer.toString(it.weight)));
					im.setLore(lores);
					is.setItemMeta(im);
					addItem(index, is, false);
				}
				index++;
				i++;
			}
		}
	}
}
