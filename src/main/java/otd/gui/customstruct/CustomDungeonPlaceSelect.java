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
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import otd.config.WorldConfig;
import otd.config.WorldConfig.CustomDungeon;
import otd.gui.Content;
import otd.struct.SchematicLoader;
import otd.util.I18n;

/**
 *
 * @author shadow
 */
public class CustomDungeonPlaceSelect extends Content {
	public final static CustomDungeonPlaceSelect instance = new CustomDungeonPlaceSelect();
	private final static int SLOT = 54;
	public int offset = 0;
	public List<CustomDungeon> list = new ArrayList<>();

	public CustomDungeonPlaceSelect() {
		super(I18n.instance.Custom_Dungeon_List, SLOT);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof CustomDungeonPlaceSelect)) {
			return;
		}

		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		CustomDungeonPlaceSelect holder = (CustomDungeonPlaceSelect) e.getInventory().getHolder();
		if (holder == null)
			return;

		if (slot == 0) {
			holder.offset--;
			if (holder.offset < 0)
				holder.offset = 0;
			holder.init();
			return;
		}
		if (slot == 1) {
			holder.offset++;
			holder.init();
			return;
		}
		if (slot == 8) {
			CustomDungeonEditor cde = new CustomDungeonEditor(new CustomDungeon(), holder);
			cde.openInventory(p);
			return;
		}

		if (slot > 53)
			return;

		if (slot > 8) {
			ItemStack is = e.getInventory().getItem(slot);
			if (is != null && is.getType() == Material.STRUCTURE_BLOCK) {
				String uuid = is.getItemMeta().getDisplayName();
				UUID id = UUID.fromString(uuid);

				CustomDungeon dungeon = WorldConfig.wc.custom_dungeon.get(id);
				if (dungeon == null)
					return;

				Location loc = p.getLocation();
				SchematicLoader.createInWorldAsync(dungeon, loc.getWorld(), loc.getBlockX(), loc.getBlockZ(),
						new Random());

				p.sendMessage("Done");
				p.closeInventory();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		list.clear();
		for (CustomDungeon dungeon : WorldConfig.wc.custom_dungeon.values())
			list.add(dungeon);

		int index = offset * 45;
		int count = 0;
		while (count < 45 && index < list.size()) {
			ItemStack is = list.get(index).getIcon();

			addItem(9 + count, is);

			count++;
			index++;
		}

		{
			ItemStack is = new ItemStack(Material.END_CRYSTAL);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Previous);
			is.setItemMeta(im);

			addItem(0, 0, is);
		}

		{
			ItemStack is = new ItemStack(Material.END_CRYSTAL);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Next);
			is.setItemMeta(im);

			addItem(0, 1, is);
		}

		{
			ItemStack is = new ItemStack(Material.BUCKET);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Add_Custom_Dungeon);
			is.setItemMeta(im);

			addItem(0, 8, is);
		}
	}
}
