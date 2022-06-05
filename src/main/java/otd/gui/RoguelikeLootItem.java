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

/**
 *
 * @author
 */
public class RoguelikeLootItem extends Content {
	public final int index;
	public final Content parent;
	public List<RoguelikeLootNode> loots;
	public int level;
	public ItemStack itemstack;
	public int max;
	public boolean each;
	private final static Material ADD = Material.RED_STAINED_GLASS_PANE;
	private final static Material SUB = Material.GREEN_STAINED_GLASS_PANE;
	private final static Material ISO = Material.BLUE_STAINED_GLASS_PANE;
	private final static int SLOT = 36;

	private final static Material DISABLE = Material.MUSIC_DISC_BLOCKS;
	private final static Material ENABLE = Material.MUSIC_DISC_CAT;

	public int weight;

	private RoguelikeLootItem() {
		super(I18n.instance.Loot_Config, SLOT);
		parent = null;
		loots = null;
		index = -1;
		weight = 1;
		max = 1;
		level = 0;
		each = false;
	}

	public static RoguelikeLootItem instance = new RoguelikeLootItem();

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof RoguelikeLootItem)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		int slot = e.getRawSlot();

		RoguelikeLootItem holder = (RoguelikeLootItem) e.getInventory().getHolder();

		Player p = (Player) e.getWhoClicked();
		// ItemStack clickedItem = e.getCurrentItem();

//        p.sendMessage(Integer.toString(slot));
		if (holder == null)
			return;
		if (slot >= 0 && slot <= 35) {
			kcancel(e);
		} else {
			e.setCancelled(false);
		}
		if (slot == 7) {
			kcancel(e);
			holder.weight -= 1;
			if (holder.weight < 0)
				holder.weight = 0;
			holder.init();
		}
		if (slot == 8) {
			kcancel(e);
			holder.weight += 1;
			if (holder.weight < 0)
				holder.weight = 0;
			holder.init();
		}
		if (slot == 10) {
			kcancel(e);
			if (p.getItemOnCursor().getType() == Material.AIR)
				return;
			holder.itemstack = p.getItemOnCursor().clone();
			holder.itemstack.setAmount(1);
			holder.addItem(1, 1, holder.itemstack, false);
			holder.init();
//            kcancel(e);
//            holder.itemstack = p.getItemOnCursor().clone();
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
				if (holder.max < 1)
					holder.max = 1;
			}
			holder.init();
		}
		if (slot == 27) {
			kcancel(e);
			holder.level = 0;
			holder.init();
		}
		if (slot == 28) {
			kcancel(e);
			holder.level = 1;
			holder.init();
		}
		if (slot == 29) {
			kcancel(e);
			holder.level = 2;
			holder.init();
		}
		if (slot == 30) {
			kcancel(e);
			holder.level = 3;
			holder.init();
		}
		if (slot == 31) {
			kcancel(e);
			holder.level = 4;
			holder.init();
		}
		if (slot == 32) {
			kcancel(e);
			holder.each = !holder.each;
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
				RoguelikeLootNode rln = new RoguelikeLootNode(is, holder.weight, holder.max, holder.level, holder.each);
				if (holder.index < holder.loots.size())
					holder.loots.set(holder.index, rln);
				else
					holder.loots.add(rln);

				WorldConfig.save();
				WorldConfig.handleRoguelikePatch();
			}
			holder.parent.openInventory(p);
		}
	}

	public RoguelikeLootItem(List<RoguelikeLootNode> loots, int index, Content parent) {
		super(I18n.instance.Loot_Config, SLOT);
		this.index = index;
		this.loots = loots;
		this.parent = parent;
		if (index >= loots.size()) {
			this.weight = 1;
			this.max = 1;
			this.each = false;
		} else {
			this.weight = loots.get(index).weight;
			this.max = loots.get(index).max;
			this.each = loots.get(index).each;
		}
		if (index >= loots.size()) {
			itemstack = new ItemStack(Material.AIR);
			level = 0;
		} else {
			itemstack = loots.get(index).getItem().clone();
			level = loots.get(index).level;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		if (weight < 0)
			weight = 0;
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
				lores.add(I18n.instance.Weight + " : " + Integer.toString(weight));
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(0, 6, is);
			}
			{
				ItemStack is = new ItemStack(SUB);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Reduce_Loot_Chance);
				List<String> lores = new ArrayList<>();
				lores.add("-1");
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(0, 7, is);
			}
			{
				ItemStack is = new ItemStack(ADD);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Increase_Loot_Chance);
				List<String> lores = new ArrayList<>();
				lores.add("+1");
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(0, 8, is);
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
			for (int i = 0; i < 5; i++) {
				Material material;
				String status;
				if (level == i) {
					material = ENABLE;
					status = I18n.instance.Enable;
				} else {
					material = DISABLE;
					status = I18n.instance.Disable;
				}
				ItemStack is = new ItemStack(material);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Dungeon_Level + " : " + i);
				List<String> lores = new ArrayList<>();
				lores.add(status);
				lores.add(I18n.instance.Level_Tip);
				if (i == 0)
					lores.add(I18n.instance.Beginner_Level);
				if (i == 4)
					lores.add(I18n.instance.Deepest_Level);
				im.setLore(lores);
				is.setItemMeta(im);
				addItem(3, i, is);
			}
		}
		{
			Material material;
			String status;
			if (this.each) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Each);
			List<String> lores = new ArrayList<>(I18n.instance.EachTip);
			lores.add(0, status);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(3, 5, is);
		}
	}
}
