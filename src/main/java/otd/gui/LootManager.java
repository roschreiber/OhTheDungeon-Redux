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
import otd.config.LootNode;
import otd.config.WorldConfig;
import otd.util.I18n;

/**
 *
 * @author
 */
public class LootManager extends Content {

	public static final List<LootNode> buffer = new ArrayList<>();

	public final List<LootNode> loots;
	public final Content parent;
	private final static int SLOT = 54;
	public int offset;

	private final static Material PAGE = Material.END_CRYSTAL;
	private final static Material ISO = Material.BLUE_STAINED_GLASS_PANE;

	private LootManager() {
		super("", SLOT);
		loots = null;
		offset = 0;
		parent = null;
	}

	public static LootManager instance = new LootManager();

	public LootManager(List<LootNode> loots, Content parent) {
		super(I18n.instance.Loot_Manager, SLOT);
		this.loots = loots;
		offset = 0;
		this.parent = parent;
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof LootManager)) {
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
		LootManager holder = (LootManager) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (slot == 0) {
			LootItem li = new LootItem(holder.loots, holder.loots.size(), holder);
			li.openInventory(p);
		}
		if (slot == 3) {
			holder.loots.clear();
			holder.init();
		}
		if (slot == 4) {
			holder.loots.addAll(buffer);
			holder.init();
		}
		if (slot == 5) {
			buffer.clear();
			buffer.addAll(holder.loots);
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
			LootItem li = new LootItem(holder.loots, index, holder);
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
			im.setDisplayName(I18n.instance.Add_New_Loot);
			is.setItemMeta(im);

			addItem(0, 0, is);
		}
		{
			ItemStack is = new ItemStack(Material.LAVA_BUCKET);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Remove_All);
			List<String> lores = new ArrayList<>();
			lores.add(ChatColor.RED + I18n.instance.Remove_All_Warn);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 3, is);
		}
		{
			ItemStack is = new ItemStack(Material.BUCKET);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Import);
			is.setItemMeta(im);

			addItem(0, 4, is);
		}
		{
			ItemStack is = new ItemStack(Material.WATER_BUCKET);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Export);
			is.setItemMeta(im);

			addItem(0, 5, is);
		}
		{
			ItemStack is = new ItemStack(PAGE);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Previous);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Page + " : " + Integer.toString(offset + 1));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 6, is);
		}
		{
			ItemStack is = new ItemStack(PAGE);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Next);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Page + " : " + Integer.toString(offset + 1));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 7, is);
		}
		{
			ItemStack is = new ItemStack(Material.STONE_BUTTON);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Apply);
			is.setItemMeta(im);

			addItem(0, 8, is);
		}
		{
			for (int i = 0; i < 9; i++) {
				ItemStack is = new ItemStack(ISO);
				addItem(1, i, is);
			}
		}
		{
			int index = 18;
			int i = offset * 36;
			while (index < SLOT && i < loots.size()) {
				LootNode it = loots.get(i);
				ItemStack is = it.getItem().clone();
				ItemMeta im = is.getItemMeta();
				if (im != null) {
					List<String> lores;
					if (im.hasLore())
						lores = im.getLore();
					else
						lores = new ArrayList<>();
					lores.add(0, I18n.instance.Click_To_Edit);
					lores.add(0, I18n.instance.Min_Item + " : " + Integer.toString(it.min));
					lores.add(0, I18n.instance.Max_Item + " : " + Integer.toString(it.max));
					lores.add(0, I18n.instance.Loot_Chance + " : " + Double.toString(it.chance));
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
