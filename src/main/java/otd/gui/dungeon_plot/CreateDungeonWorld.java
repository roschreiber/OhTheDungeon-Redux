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
package otd.gui.dungeon_plot;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import otd.Main;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.gui.Content;
import otd.gui.WorldEditor;
import otd.populator.DungeonPopulator;
import otd.world.ChunkList;
import otd.world.DungeonTask;
import otd.world.DungeonWorld;
import otd.world.WorldDefine;
import otd.util.I18n;

/**
 *
 * @author zhehe
 */
public class CreateDungeonWorld extends Content {

	public final static CreateDungeonWorld instance = new CreateDungeonWorld();
	private final Content parent;

	public CreateDungeonWorld() {
		super(I18n.instance.Menu_Remove_World, 9);
		this.parent = null;
	}

	public CreateDungeonWorld(Content parent) {
		super(I18n.instance.Menu_Remove_World, 9);
		this.parent = parent;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		{
			ItemStack is = new ItemStack(Material.MAP);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Menu_create_world_config);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Menu_create_world_config_lore);
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(0, is);
		}
		{
			ItemStack is = new ItemStack(Material.COMPASS);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Menu_create_dungeon_count);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Dungeon_Count + " : " + WorldConfig.wc.dungeon_world.dungeon_count);
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, is);
		}
		{
			ItemStack is = new ItemStack(Material.OAK_SIGN);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Menu_create);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Menu_create_lore);
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(7, is);
		}
		{
			ItemStack is = new ItemStack(Material.LEVER);
			ItemMeta im = is.getItemMeta();

			im.setDisplayName(I18n.instance.Back);

			is.setItemMeta(im);
			addItem(8, is);
		}
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof CreateDungeonWorld)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		CreateDungeonWorld holder = (CreateDungeonWorld) e.getInventory().getHolder();
		if (holder == null)
			return;

		if (slot == 0) {
			WorldEditor we = new WorldEditor(WorldDefine.WORLD_NAME, Environment.NORMAL, holder);
			we.openInventory(p);
		}
		if (slot == 1) {
			if (e.getClick().equals(ClickType.LEFT)) {
				WorldConfig.wc.dungeon_world.dungeon_count++;
				if (WorldConfig.wc.dungeon_world.dungeon_count > 30)
					WorldConfig.wc.dungeon_world.dungeon_count = 30;
				WorldConfig.save();
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				WorldConfig.wc.dungeon_world.dungeon_count--;
				if (WorldConfig.wc.dungeon_world.dungeon_count < 1)
					WorldConfig.wc.dungeon_world.dungeon_count = 1;
				WorldConfig.save();
			}
			holder.init();
		}
		if (slot == 7) {
			if (!checkEnabled()) {
				p.sendMessage(ChatColor.RED + I18n.instance.Must_Natural_Spawn);
			} else {
				if (!DungeonTask.isGenerating()) {
					ChunkList.initChunksMap(WorldConfig.wc.dungeon_world);
					p.closeInventory();
					p.sendMessage(ChatColor.BLUE + I18n.instance.Dungeon_Plot_In_Progress);
					p.sendMessage(ChatColor.BLUE + I18n.instance.Creating_World);
					Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
						DungeonWorld.generateDungeonWorld();
						DungeonTask.start();
					}, 1L);
				} else {
					p.sendMessage(ChatColor.BLUE + I18n.instance.Dungeon_Plot_In_Progress);
					p.closeInventory();
				}
			}
		}
		if (slot == 8) {
			holder.parent.openInventory(p);
		}
	}

	private static boolean checkEnabled() {
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(WorldDefine.WORLD_NAME);

		return DungeonPopulator.isDungeonEnabled(swc);
	}
}
