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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import otd.config.WorldConfig.CustomDungeon;
import otd.gui.Content;
import otd.struct.SchematicLoader;
import otd.util.FileUtils;
import otd.util.I18n;

/**
 *
 * @author shadow
 */
public class SchematicSelect extends Content {
	public final static SchematicSelect instance = new SchematicSelect();
	private final static int SLOT = 54;
	public final Content parent;
	public final CustomDungeon dungeon;
	public int offset = 0;

	private final static Material RECORD = Material.MUSIC_DISC_WAIT;

	public SchematicSelect() {
		super(I18n.instance.Schematic_Select, SLOT);
		parent = null;
		dungeon = null;
	}

	public SchematicSelect(Content parent, CustomDungeon dungeon) {
		super(I18n.instance.Schematic_Select, SLOT);
		this.parent = parent;
		this.dungeon = dungeon;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof SchematicSelect)) {
			return;
		}

		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		SchematicSelect holder = (SchematicSelect) e.getInventory().getHolder();
		if (holder == null)
			return;

		if (slot == 53) {
			holder.parent.openInventory(p);
			return;
		}

		if (slot == 52) {
			p.sendMessage(ChatColor.GREEN + "https://discord.gg/4ueBxWBgTk");
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

		if (slot > 53)
			return;

		ItemStack is = e.getInventory().getItem(slot);
		if (is != null && is.getType() == RECORD) {
			String fileName = is.getItemMeta().getDisplayName();

			File file = new File(SchematicLoader.getSchematicDir(), fileName);
			if (file.exists() && file.isFile()) {
				holder.dungeon.file = fileName;
				holder.parent.openInventory(p);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		List<File> files = new ArrayList<>();
		for (File file : SchematicLoader.getSchematicDir().listFiles()) {
			if (file == null)
				continue;
			Bukkit.getLogger().info(file.getAbsolutePath());

			String ext = FileUtils.getExtension(file).trim();
			// Bukkit.getLogger().info(ext);
			if (ext.equals("schem") || ext.equals("schematic") || ext.equals("nbt")) {
				files.add(file);
			}
		}

		int index = offset * 45;
		int count = 0;
		while (count < 45 && index < files.size()) {
			String fileName = files.get(index).getName();
			ItemStack is = new ItemStack(RECORD);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(fileName);
			is.setItemMeta(im);

			addItem(count, is);

			count++;
			index++;
		}

//        {
//            ItemStack is = new ItemStack(Material.CHEST);
//            ItemMeta im = is.getItemMeta();
//            im.setDisplayName(I18n.instance.Schematic_Select);
//            List<String> lores = new ArrayList<>();
//            lores.add(I18n.instance.Schematic_Select_Lore);
//            im.setLore(lores);
//            is.setItemMeta(im);
//            
//            addItem(0, is);
//        }

		{
			ItemStack is = new ItemStack(Material.BUCKET);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Get_Schematic);
			is.setItemMeta(im);

			addItem(5, 7, is);
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
