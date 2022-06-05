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
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.config.WorldConfig.CustomDungeon;
import otd.gui.Content;
import otd.util.I18n;

/**
 *
 * @author shadow
 */
public class WorldCustomDungeon extends Content {
	public final static WorldCustomDungeon instance = new WorldCustomDungeon();
	private final static int SLOT = 54;
	public final Content parent;
	public final Set<UUID> dungeons;
	public int offset = 0;

	private final static Material RECORD = Material.MUSIC_DISC_WAIT;

	private WorldCustomDungeon() {
		super(I18n.instance.World_Custom_Dungeon, SLOT);
		parent = null;
		dungeons = null;
	}

	public WorldCustomDungeon(String world, Content parent) {
		super(I18n.instance.World_Custom_Dungeon + " : " + world, SLOT);
		this.parent = parent;
		if (!WorldConfig.wc.dict.containsKey(world)) {
			WorldConfig.wc.dict.put(world, new SimpleWorldConfig());
		}

		SimpleWorldConfig swc = WorldConfig.wc.dict.get(world);
		this.dungeons = swc.custom_dungeons;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof WorldCustomDungeon)) {
			return;
		}

		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		WorldCustomDungeon holder = (WorldCustomDungeon) e.getInventory().getHolder();
		if (holder == null)
			return;

		if (slot == 53) {
			holder.parent.openInventory(p);
			return;
		}

		if (slot == 45) {
			holder.offset--;
			if (holder.offset < 0)
				holder.offset = 0;
			holder.init();
			return;
		}
		if (slot == 46) {
			holder.offset++;
			holder.init();
			return;
		}

		if (slot == 0) {
			CustomDungeonEditor cde = new CustomDungeonEditor(new CustomDungeon(), holder);
			cde.openInventory(p);
			return;
		}

		if (slot > 0 & slot < 9)
			return;

		if (slot > 44)
			return;

		ItemStack is = e.getInventory().getItem(slot);
		if (is != null) {
			UUID uuid = UUID.fromString(is.getItemMeta().getDisplayName());

			if (holder.dungeons.contains(uuid)) {
				holder.dungeons.remove(uuid);
				WorldConfig.save();
				holder.init();
			} else {
				holder.dungeons.add(uuid);
				WorldConfig.save();
				holder.init();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		List<UUID> uuids = new ArrayList<>();
		for (Map.Entry<UUID, CustomDungeon> entry : WorldConfig.wc.custom_dungeon.entrySet()) {
			uuids.add(entry.getKey());
		}

		{
			ItemStack is = new ItemStack(Material.BUCKET);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Add_Custom_Dungeon);
			is.setItemMeta(im);

			addItem(0, 0, is);
		}

		for (int i = 1; i < 9; i++) {
			ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS, 1);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName("");
			item.setItemMeta(im);

			addItem(i, item);
		}

		int index = offset * 36;
		int count = 0;
		while (count < 36 && index < uuids.size()) {
			Material icon = RECORD;
			if (!dungeons.contains(uuids.get(index)))
				icon = Material.BARRIER;
			ItemStack is = WorldConfig.wc.custom_dungeon.get(uuids.get(index)).getIcon();
			is.setType(icon);

			addItem(9 + count, is);

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
}
