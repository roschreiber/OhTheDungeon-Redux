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
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.util.I18n;

/**
 *
 * @author shadow
 */
public class RogueLikeDungeonTower extends Content {
	private final static int SLOT = 18;

	public static RogueLikeDungeonTower instance = new RogueLikeDungeonTower();

	private final String world_name;
	private final Content parent;

	private SimpleWorldConfig swc;

	private RogueLikeDungeonTower() {
		super(I18n.instance.Roguelike_Dungeon_Tower, SLOT);
		world_name = null;
		parent = null;
	}

	private final static Material DISABLE = Material.MUSIC_DISC_BLOCKS;
	private final static Material ENABLE = Material.MUSIC_DISC_CAT;

	public RogueLikeDungeonTower(String world_name, Content parent) {
		super(I18n.instance.Roguelike_Dungeon_Tower, SLOT);
		this.world_name = world_name;

		if (!WorldConfig.wc.dict.containsKey(world_name)) {
			WorldConfig.wc.dict.put(world_name, new SimpleWorldConfig());
		}

		swc = WorldConfig.wc.dict.get(world_name);

		this.parent = parent;
	}

	private boolean isAllDisabled() {
		return !(swc.roguelike.themes.bunker || swc.roguelike.themes.desert || swc.roguelike.themes.forest
				|| swc.roguelike.themes.house || swc.roguelike.themes.ice || swc.roguelike.themes.jungle
				|| swc.roguelike.themes.mesa || swc.roguelike.themes.mountain || swc.roguelike.themes.rare
				|| swc.roguelike.themes.ruin || swc.roguelike.themes.swamp);
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof RogueLikeDungeonTower)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		RogueLikeDungeonTower holder = (RogueLikeDungeonTower) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (holder.world_name == null)
			return;
		// String key = holder.world_name;
		// SimpleWorldConfig swc = WorldConfig.wc.dict.get(key);

		if (slot == 0) {
			holder.swc.roguelike.themes.bunker = !holder.swc.roguelike.themes.bunker;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.bunker = !holder.swc.roguelike.themes.bunker;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 1) {
			holder.swc.roguelike.themes.desert = !holder.swc.roguelike.themes.desert;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.desert = !holder.swc.roguelike.themes.desert;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 2) {
			holder.swc.roguelike.themes.forest = !holder.swc.roguelike.themes.forest;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.forest = !holder.swc.roguelike.themes.forest;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 3) {
			holder.swc.roguelike.themes.house = !holder.swc.roguelike.themes.house;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.house = !holder.swc.roguelike.themes.house;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 4) {
			holder.swc.roguelike.themes.ice = !holder.swc.roguelike.themes.ice;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.ice = !holder.swc.roguelike.themes.ice;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 5) {
			holder.swc.roguelike.themes.jungle = !holder.swc.roguelike.themes.jungle;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.jungle = !holder.swc.roguelike.themes.jungle;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 6) {
			holder.swc.roguelike.themes.mesa = !holder.swc.roguelike.themes.mesa;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.mesa = !holder.swc.roguelike.themes.mesa;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 7) {
			holder.swc.roguelike.themes.mountain = !holder.swc.roguelike.themes.mountain;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.mountain = !holder.swc.roguelike.themes.mountain;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 8) {
			holder.swc.roguelike.themes.ruin = !holder.swc.roguelike.themes.ruin;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.ruin = !holder.swc.roguelike.themes.ruin;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 9) {
			holder.swc.roguelike.themes.rare = !holder.swc.roguelike.themes.rare;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.rare = !holder.swc.roguelike.themes.rare;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 10) {
			holder.swc.roguelike.themes.swamp = !holder.swc.roguelike.themes.swamp;
			if (holder.isAllDisabled()) {
				holder.swc.roguelike.themes.swamp = !holder.swc.roguelike.themes.swamp;
				p.sendMessage(ChatColor.RED + I18n.instance.Roguelike_Dungeon_Tower_Warn);
			}
			WorldConfig.save();
			holder.init();
		}
		if (slot == 17) {
			holder.parent.openInventory(p);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		{
			Material type;
			String status;
			if (swc.roguelike.themes.bunker) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Bunker);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(0, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.desert) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Desert);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(1, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.forest) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Forest);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(2, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.house) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.House);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(3, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.ice) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Ice);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(4, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.jungle) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Jungle);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(5, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.mesa) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Mesa);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(6, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.mountain) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Mountain);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(7, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.ruin) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Ruin);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(8, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.rare) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Cactus);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(9, is);
		}
		{
			Material type;
			String status;
			if (swc.roguelike.themes.swamp) {
				type = ENABLE;
				status = I18n.instance.Enable;
			} else {
				type = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(type);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Swamp);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);

			is.setItemMeta(im);
			addItem(10, is);
		}
		{
			ItemStack is = new ItemStack(Material.LEVER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Back);
			is.setItemMeta(im);

			addItem(1, 8, is);
		}
	}
}
