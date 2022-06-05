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

import otd.config.LootNode;
import otd.config.WorldConfig;
import otd.util.I18n;

/**
 *
 * @author
 */
public class LootItem extends Content {
	public final int index;
	public final Content parent;
	public List<LootNode> loots;
	public ItemStack itemstack;
	public int max;
	public int min;
	private final static Material ADD = Material.RED_STAINED_GLASS_PANE;
	private final static Material SUB = Material.GREEN_STAINED_GLASS_PANE;
	private final static Material ISO = Material.BLUE_STAINED_GLASS_PANE;
	private final static int SLOT = 36;

	public double chance;

	private LootItem() {
		super(I18n.instance.Loot_Config, SLOT);
		parent = null;
		loots = null;
		index = -1;
		chance = 1.0;
		max = 1;
		min = 1;
	}

	public static LootItem instance = new LootItem();

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof LootItem)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		int slot = e.getRawSlot();

		LootItem holder = (LootItem) e.getInventory().getHolder();

		Player p = (Player) e.getWhoClicked();
		// ItemStack clickedItem = e.getCurrentItem();

//        p.sendMessage(Integer.toString(slot));
		if (holder == null)
			return;
//        p.sendMessage(Integer.toString(slot));
		if (slot >= 0 && slot <= 35) {
			kcancel(e);
		} else {
			e.setCancelled(false);
			return;
		}
		if (slot == 7) {
			kcancel(e);
			holder.chance -= 0.1;
			if (holder.chance < 0)
				holder.chance = 0;
			if (holder.chance > 1)
				holder.chance = 1;
			holder.init();
		}
		if (slot == 8) {
			kcancel(e);
			holder.chance += 0.1;
			if (holder.chance < 0)
				holder.chance = 0;
			if (holder.chance > 1)
				holder.chance = 1;
			holder.init();
		}
		if (slot == 10) {
//            kcancel(e);
			kcancel(e);
			if (p.getItemOnCursor().getType() == Material.AIR)
				return;
			holder.itemstack = p.getItemOnCursor().clone();
			holder.itemstack.setAmount(1);
			holder.addItem(1, 1, holder.itemstack, false);
			holder.init();
//            holder.init();
		}
		if (slot == 13) {
			kcancel(e);
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.max++;
				if (holder.max > 64)
					holder.max = 64;
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				holder.max--;
				if (holder.max < holder.min)
					holder.max = holder.min;
			}
			holder.init();
		}
		if (slot == 22) {
			kcancel(e);
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.min++;
				if (holder.min > holder.max)
					holder.min = holder.max;
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				holder.min--;
				if (holder.min < 1)
					holder.min = 1;
			}
			holder.init();
		}
		if (slot == 16) {
			kcancel(e);
			holder.chance -= 0.01;
			holder.init();
		}
		if (slot == 17) {
			kcancel(e);
			holder.chance += 0.01;
			holder.init();
		}
		if (slot == 25) {
			kcancel(e);
			holder.chance -= 0.001;
			holder.init();
		}
		if (slot == 26) {
			kcancel(e);
			holder.chance += 0.001;
			holder.init();
		}
		if (slot == 33) {
			kcancel(e);
			if (holder.index < holder.loots.size()) {
				holder.loots.remove(holder.index);
				WorldConfig.save();
			}
			holder.parent.openInventory(p);
		}
		if (slot == 34) {
			kcancel(e);
			holder.parent.openInventory(p);
		}
		if (slot == 35) {
			kcancel(e);
			ItemStack is = holder.inv.getItem(10);
			if (is != null && is.getType() != Material.AIR) {
				LootNode ln = new LootNode(is, holder.chance, holder.max, holder.min);
//                Bukkit.getLogger().log(Level.SEVERE, "" + holder.index);
				if (holder.index < holder.loots.size())
					holder.loots.set(holder.index, ln);
				else
					holder.loots.add(ln);

				WorldConfig.save();
			}
			holder.parent.openInventory(p);
		}
	}

	public LootItem(List<LootNode> loots, int index, Content parent) {
		super(I18n.instance.Loot_Config, SLOT);
		this.index = index;
		this.loots = loots;
		this.parent = parent;
		if (index >= loots.size()) {
			this.chance = 1.0;
			this.max = 1;
			this.min = 1;
		} else {
			this.chance = loots.get(index).chance;
			this.max = loots.get(index).max;
			this.min = loots.get(index).min;
		}
		if (index >= loots.size())
			itemstack = new ItemStack(Material.AIR);
		else
			itemstack = loots.get(index).getItem().clone();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		int tmp = (int) (chance * 1000);
		chance = (double) tmp / 1000.0;
		if (chance < 0)
			chance = 0;
		if (chance > 1)
			chance = 1;
		{
			ItemStack is = new ItemStack(ISO);
			addItem(0, 0, is);
			addItem(0, 1, is);
			addItem(0, 2, is);
			addItem(1, 0, is);
			addItem(1, 2, is);
			addItem(2, 0, is);
			addItem(2, 1, is);
			addItem(2, 2, is);
		}
		{
			if (itemstack != null) {
				itemstack.setAmount(1);
				addItem(1, 1, itemstack, false);
			}
		}
		{
			{
				ItemStack is = new ItemStack(Material.OAK_SIGN);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Current_Chance);
				List<String> lores = new ArrayList<>();
				lores.add(Double.toString(chance));
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(0, 6, is);
			}
			{
				ItemStack is = new ItemStack(SUB);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Reduce_Loot_Chance);
				List<String> lores = new ArrayList<>();
				lores.add("-0.1");
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(0, 7, is);
			}
			{
				ItemStack is = new ItemStack(ADD);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Increase_Loot_Chance);
				List<String> lores = new ArrayList<>();
				lores.add("+0.1");
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(0, 8, is);
			}
			{
				ItemStack is = new ItemStack(SUB);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Reduce_Loot_Chance);
				List<String> lores = new ArrayList<>();
				lores.add("-0.01");
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(1, 7, is);
			}
			{
				ItemStack is = new ItemStack(ADD);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Increase_Loot_Chance);
				List<String> lores = new ArrayList<>();
				lores.add("+0.01");
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(1, 8, is);
			}
			{
				ItemStack is = new ItemStack(SUB);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Reduce_Loot_Chance);
				List<String> lores = new ArrayList<>();
				lores.add("-0.001");
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(2, 7, is);
			}
			{
				ItemStack is = new ItemStack(ADD);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Increase_Loot_Chance);
				List<String> lores = new ArrayList<>();
				lores.add("+0.001");
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(2, 8, is);
			}
		}
		{
			ItemStack is = new ItemStack(Material.LAVA_BUCKET);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Remove);
			is.setItemMeta(im);
			addItem(3, 6, is);
		}
		{
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Cancel);
			is.setItemMeta(im);
			addItem(3, 7, is);
		}
		{
			ItemStack is = new ItemStack(Material.STONE_BUTTON);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Apply);
			is.setItemMeta(im);
			addItem(3, 8, is);
		}
		{
			ItemStack is = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Max_Item);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Max_Item + " : " + Integer.toString(max));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(13, is);
		}
		{
			ItemStack is = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Min_Item);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Min_Item + " : " + Integer.toString(min));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(22, is);
		}
	}
}
