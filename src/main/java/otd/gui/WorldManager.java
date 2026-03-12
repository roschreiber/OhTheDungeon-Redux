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

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.lib.DungeonWorldManager;
import otd.util.I18n;
import otd.world.WorldDefine;

import otd.redux.util.MenuHelper;

/**
 *
 * @author
 */
public class WorldManager extends Content {
	private final static int SLOT = 54;
	private final Content parent;

	public static WorldManager instance = new WorldManager();

	public WorldManager() {
		super(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.World_Manager, SLOT);
		parent = null;
	}

	public WorldManager(Content parent) {
		super(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.World_Manager, SLOT);
		worlds = new ArrayList<>();
		offset = 0;
		this.parent = parent;
	}

	public final static Material NORMAL = Material.GRASS_BLOCK;
	public final static Material NETHER = Material.NETHER_BRICKS;
	public final static Material ENDER = Material.END_PORTAL_FRAME;
	public final static Material MAP = Material.WRITTEN_BOOK;

	private List<World> worlds;
	private int offset;

	private final static String WORLD_KEYWORD = "World";

	@SuppressWarnings("deprecation")
	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof WorldManager)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}
		kcancel(e);

		int slot = e.getRawSlot();
		if (slot < 0 && slot >= 54) {
			return;
		}

		Player p = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();

		// verify current item is not null
		if (clickedItem == null || clickedItem.getType() == Material.AIR)
			return;

		ItemStack is = clickedItem;
		ItemMeta im = is.getItemMeta();
		if (im == null)
			return;
		String name = im.getDisplayName();
		WorldManager holder = (WorldManager) e.getInventory().getHolder();

		if (slot == 8) {
			holder.parent.openInventory(p);
		}
		if (slot == 45) {
			holder.offset--;
			if (holder.offset < 0)
				holder.offset = 0;
			holder.init();
			return;
		}
		if (slot == 53) {
			holder.offset++;
			holder.init();
			return;
		}
		List<String> lores = im.getLore();
		if (lores == null)
			return;
		if (lores.isEmpty())
			return;
		String keyword = lores.get(0);
		if (keyword.equals(WORLD_KEYWORD)) {
			String world_name = ChatColor.stripColor(im.getDisplayName()).trim();
            World world = Bukkit.getServer().getWorld(world_name);
			if (world == null) {
			} else {
				WorldEditor we = new WorldEditor(world.getName(), world.getEnvironment(), holder);
				we.openInventory(p);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		worlds.clear();

		boolean include_instance = false;
		for (World w : Bukkit.getServer().getWorlds()) {
			if (w.getName().equals(DungeonWorldManager.WORLD_NAME)) {
				include_instance = true;
			}
			if (w.getName().equalsIgnoreCase(WorldDefine.WORLD_NAME))
				continue;
			worlds.add(w);
		}
		if (!include_instance)
			worlds.add(null);

		// Header row
		MenuHelper.fillRow(this, 0);
		{
			ItemStack first = new ItemStack(Material.OAK_SIGN);
			ItemMeta im = first.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.World_List);
			first.setItemMeta(im);
			addItem(0, 0, first);
		}
		addItem(0, 8, MenuHelper.back());

		// Footer row
		MenuHelper.fillRow(this, 5);
		addItem(5, 0, MenuHelper.prev(offset + 1));
		addItem(5, 8, MenuHelper.next(offset + 1));
		if (offset * 4 * 9 > worlds.size()) {
			return;
		}

		int start = offset * 4 * 9;
		int count = 0;
		while (count < 4 * 9 && start + count < worlds.size()) {
			World w = worlds.get(start + count);
			Material mat;
			if (w != null) {
				Environment env = w.getEnvironment();
				switch (env) {
				case NORMAL:
					mat = NORMAL;
					break;
				case NETHER:
					mat = NETHER;
					break;
				case THE_END:
					mat = ENDER;
					break;
				default:
					mat = ENDER;
					break;
				}
				if (w.getName().equals(DungeonWorldManager.WORLD_NAME)) {
					mat = MAP;
				}
			} else {
				mat = MAP;
			}

			ItemStack is = new ItemStack(mat);
			ItemMeta im = is.getItemMeta();
			if (w != null) {
				String ncolor;
				if (w.getName().equals(DungeonWorldManager.WORLD_NAME)) {
					ncolor = MenuHelper.SECONDARY;
				} else {
					switch (w.getEnvironment()) {
					case NORMAL:
						ncolor = MenuHelper.SUCCESS;
						break;
					case NETHER:
						ncolor = MenuHelper.DANGER;
						break;
					case THE_END:
						ncolor = MenuHelper.INFO;
						break;
					default:
						ncolor = MenuHelper.LIGHT;
					}
				}
				im.setDisplayName(MenuHelper.color(ncolor) + w.getName());
			} else {
				im.setDisplayName(MenuHelper.color(MenuHelper.SECONDARY) + DungeonWorldManager.WORLD_NAME);
			}

			if (w == null) {
				List<String> lores = new ArrayList<>();
				lores.add(WORLD_KEYWORD);
				lores.add(MenuHelper.separator());
				lores.add(MenuHelper.info(I18n.instance.PPDI_WORLD));
				lores.add(MenuHelper.color(MenuHelper.DANGER) + I18n.instance.Addon_Not_Installed);
				lores.add(MenuHelper.separator());
				lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Install));
				im.setLore(lores);
			} else if (WorldConfig.wc.dict.containsKey(w.getName())) {
				SimpleWorldConfig config = WorldConfig.wc.dict.get(w.getName());
				List<String> lores = new ArrayList<>();
				lores.add(WORLD_KEYWORD);
				lores.add(MenuHelper.separator());
				if (w.getName().equals(DungeonWorldManager.WORLD_NAME)) {
					lores.add(MenuHelper.info(I18n.instance.PPDI_WORLD));
				} else {
					lores.add(worldStatusLine(I18n.instance.Roguelike_Dungeon_Natural_Spawn, config.roguelike.doNaturalSpawn));
					lores.add(worldStatusLine(I18n.instance.Doomlike_Dungeon_Natural_Spawn, config.doomlike.doNaturalSpawn));
					lores.add(worldStatusLine(I18n.instance.Battle_Tower_Natural_Spawn, config.battletower.doNaturalSpawn));
					lores.add(worldStatusLine(I18n.instance.Smoofy_Dungeon_Natural_Spawn, config.smoofydungeon.doNaturalSpawn));
					lores.add(worldStatusLine(I18n.instance.Draylar_Battle_Tower_Natural_Spawn, config.draylar_battletower.doNaturalSpawn));
					lores.add(worldStatusLine(I18n.instance.Ant_Man_Dungeon_Natural_Spawn, config.ant_man_dungeon.doNaturalSpawn));
					lores.add(worldStatusLine(I18n.instance.Aether_Dungeon_Natural_Spawn, config.aether_dungeon.doNaturalSpawn));
					lores.add(worldStatusLine(I18n.instance.LichTower_Natural_Spawn, config.lich_tower.doNaturalSpawn));
					lores.add(worldStatusLine(I18n.instance.Castle_Natural_Spawn, config.castle.doNaturalSpawn));
				}
				lores.add(MenuHelper.separator());
				lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Configure));
				im.setLore(lores);
			} else {
				List<String> lores = new ArrayList<>();
				lores.add(WORLD_KEYWORD);
				lores.add(MenuHelper.separator());
				if (w.getName().equals(DungeonWorldManager.WORLD_NAME)) {
					lores.add(MenuHelper.info(I18n.instance.PPDI_WORLD));
				} else {
					lores.add(worldStatusLine(I18n.instance.Roguelike_Dungeon_Natural_Spawn, false));
					lores.add(worldStatusLine(I18n.instance.Doomlike_Dungeon_Natural_Spawn, false));
					lores.add(worldStatusLine(I18n.instance.Battle_Tower_Natural_Spawn, false));
					lores.add(worldStatusLine(I18n.instance.Smoofy_Dungeon_Natural_Spawn, false));
					lores.add(worldStatusLine(I18n.instance.Draylar_Battle_Tower_Natural_Spawn, false));
					lores.add(worldStatusLine(I18n.instance.Ant_Man_Dungeon_Natural_Spawn, false));
					lores.add(worldStatusLine(I18n.instance.Aether_Dungeon_Natural_Spawn, false));
					lores.add(worldStatusLine(I18n.instance.LichTower_Natural_Spawn, false));
					lores.add(worldStatusLine(I18n.instance.CastleDungeon_Config, false));
				}
				lores.add(MenuHelper.separator());
				lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Configure));
				im.setLore(lores);
			}
			is.setItemMeta(im);

			addItem(9 + count, is);

			count++;
		}
	}

	private static String worldStatusLine(String label, boolean enabled) {
		if (enabled) {
			return MenuHelper.color(MenuHelper.MUTED) + label + ": " + MenuHelper.color(MenuHelper.SUCCESS) + "\u2714 " + I18n.instance.Enable;
		} else {
			return MenuHelper.color(MenuHelper.MUTED) + label + ": " + MenuHelper.color("#555555") + "\u2718 " + I18n.instance.Disable;
		}
	}
}
