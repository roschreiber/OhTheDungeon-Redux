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

import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.integration.WorldEdit;
import otd.util.I18n;
import otd.util.Skull;
import otd.world.WorldDefine;

/**
 *
 * @author
 */
public class DungeonSpawnSetting extends Content {
	private final static int SLOT = 36;
	public static DungeonSpawnSetting instance = new DungeonSpawnSetting();
	public final String world;
	public final Content parent;

	private int roguelike;
	private int doomlike;
	private int battle;
	private int smoofy;
	private int draylar;
	private int ant;
	private int aether;
	private int lich;
	private int custom;
	private int castle;
	private int distance;
	private double rate;

//	private final static Material ROGUELIKE = Material.DIAMOND_BLOCK;
//	private final static Material DOOMLIKE = Material.GOLD_BLOCK;
//	private final static Material BATTLE = Material.IRON_BLOCK;
//	private final static Material SMOOFY = Material.COBBLESTONE;
//	private final static Material DRAYLAR = Material.BOOKSHELF;
//	private final static Material ANT = Material.END_PORTAL_FRAME;
//	private final static Material AETHER = Material.GLOWSTONE;
//	private final static Material LICH = Material.PAINTING;
//	private final static Material CASTLE = Material.IRON_TRAPDOOR;
//	private final static Material CUSTOM = Material.STRUCTURE_BLOCK;

	private DungeonSpawnSetting() {
		super("", SLOT);
		this.world = null;
		this.parent = null;
	}

	public DungeonSpawnSetting(String world, Content parent) {
		super(I18n.instance.Dungeon_Spawn_Setting, SLOT);
		this.world = world;
		this.parent = parent;

		SimpleWorldConfig swc;
		String world_name = this.world;
		if (!WorldConfig.wc.dict.containsKey(world_name)) {
			swc = new SimpleWorldConfig();
			WorldConfig.wc.dict.put(world_name, swc);
			WorldConfig.save();
		} else {
			swc = WorldConfig.wc.dict.get(world_name);
		}

		this.roguelike = swc.roguelike_weight;
		this.doomlike = swc.doomlike_weight;
		this.battle = swc.battle_tower_weight;
		this.smoofy = swc.smoofy_weight;
		this.draylar = swc.draylar_weight;
		this.ant = swc.antman_weight;
		this.aether = swc.aether_weight;
		this.lich = swc.lich_weight;
		this.custom = swc.custom_dungeon_weight;
		this.castle = swc.castle_weight;
		this.distance = swc.distance;
		this.rate = swc.spawner_rejection_rate;
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof DungeonSpawnSetting)) {
			return;
		}

		kcancel(e);
		ClickType type = e.getClick();

		int slot = e.getRawSlot();
		if (slot < 0 && slot >= 27) {
			return;
		}
		DungeonSpawnSetting holder = (DungeonSpawnSetting) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (slot == 9) {
			if (type == ClickType.LEFT) {
				holder.roguelike++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.roguelike--;
				holder.init();
			}
		}
		if (slot == 10) {
			if (type == ClickType.LEFT) {
				holder.doomlike++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.doomlike--;
				holder.init();
			}
		}
		if (slot == 11) {
			if (type == ClickType.LEFT) {
				holder.battle++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.battle--;
				holder.init();
			}
		}
		if (slot == 12) {
			if (type == ClickType.LEFT) {
				holder.smoofy++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.smoofy--;
				holder.init();
			}
		}
		if (slot == 13) {
			if (type == ClickType.LEFT) {
				holder.draylar++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.draylar--;
				holder.init();
			}
		}
		if (slot == 14) {
			if (type == ClickType.LEFT) {
				holder.ant++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.ant--;
				holder.init();
			}
		}
		if (slot == 15) {
			if (type == ClickType.LEFT) {
				holder.aether++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.aether--;
				holder.init();
			}
		}
		if (slot == 16) {
			if (type == ClickType.LEFT) {
				holder.lich++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.lich--;
				holder.init();
			}
		}
		if (slot == 17) {
			if (type == ClickType.LEFT) {
				holder.custom++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.custom--;
				holder.init();
			}
		}
		if (slot == 18) {
			if (type == ClickType.LEFT) {
				holder.castle++;
				holder.init();
			}
			if (type == ClickType.RIGHT) {
				holder.castle--;
				holder.init();
			}
		}
		if (slot == 27) {
			if (!holder.world.equalsIgnoreCase(WorldDefine.WORLD_NAME)) {
				if (type == ClickType.LEFT) {
					holder.distance++;
					holder.init();
				}
				if (type == ClickType.RIGHT) {
					holder.distance--;
					if (holder.distance < 15)
						holder.distance = 15;
					holder.init();
				}
			}
		}
		if (slot == 28) {
			if (!holder.world.equalsIgnoreCase(WorldDefine.WORLD_NAME)) {
				if (type == ClickType.LEFT) {
					holder.rate = holder.rate + 0.1;
					if (holder.rate > 1)
						holder.rate = 1;
					holder.init();
				}
				if (type == ClickType.RIGHT) {
					holder.rate = holder.rate - 0.1;
					if (holder.rate < 0)
						holder.rate = 0;
					holder.init();
				}
			}
		}
		if (slot == 35) {
			if (type == ClickType.LEFT) {
				SimpleWorldConfig swc = WorldConfig.wc.dict.get(holder.world);
				swc.roguelike_weight = holder.roguelike;
				swc.doomlike_weight = holder.doomlike;
				swc.battle_tower_weight = holder.battle;
				swc.smoofy_weight = holder.smoofy;
				swc.draylar_weight = holder.draylar;
				swc.antman_weight = holder.ant;
				swc.aether_weight = holder.aether;
				swc.lich_weight = holder.lich;
				swc.custom_dungeon_weight = holder.custom;
				swc.castle_weight = holder.castle;
				swc.distance = holder.distance;
				swc.spawner_rejection_rate = holder.rate;
				WorldConfig.save();
				Player p = (Player) e.getWhoClicked();
				p.sendMessage(I18n.instance.World_Config_Save);
				holder.parent.openInventory(p);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		if (this.battle < 0)
			this.battle = 0;
		if (this.doomlike < 0)
			this.doomlike = 0;
		if (this.roguelike < 0)
			this.doomlike = 0;
		if (this.smoofy < 0)
			this.smoofy = 0;
		if (this.draylar < 0)
			this.draylar = 0;
		if (this.ant < 0)
			this.ant = 0;
		if (this.aether < 0)
			this.aether = 0;
		if (this.lich < 0)
			this.lich = 0;
		if (this.custom < 0)
			this.custom = 0;
		if (this.castle < 0)
			this.castle = 0;
		inv.clear();
		{
			ItemStack is = new ItemStack(Material.OAK_SIGN);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Dungeon_Spawn_Tip);
			is.setItemMeta(im);
			addItem(0, 0, is);
		}
		int total = this.battle + this.doomlike + this.roguelike + this.smoofy + this.draylar + this.ant + this.aether
				+ this.lich + this.custom + this.castle;
		{
			ItemStack is = Skull.ROGUELIKE.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Roguelike_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.roguelike) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 0, is);
		}
		{
			ItemStack is = Skull.DOOMLIKE.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Doomlike_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.doomlike) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 1, is);
		}
		{
			ItemStack is = Skull.BATTLE.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Battle_Tower);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.battle) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 2, is);
		}
		{
			ItemStack is = Skull.SMOOFY.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Smoofy_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.smoofy) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 3, is);
		}
		{
			ItemStack is = Skull.DRAYLAR.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Draylar_Battle_Tower);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.draylar) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 4, is);
		}
		{
			ItemStack is = Skull.ANTMAN.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Ant_Man_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.ant) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 5, is);
		}
		{
			ItemStack is = Skull.AETHER.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Aether_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.aether) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 6, is);
		}
		{
			ItemStack is = Skull.LICH.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.LichTower);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.lich) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 7, is);
		}
		{
			ItemStack is = (WorldEdit.isReady() ? Skull.CUSTOM.getItem() : new ItemStack(Material.BARRIER));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Custom_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.custom) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 8, is);
		}
		{
			ItemStack is = Skull.CASTLE.getItem();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Castle_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(this.castle) + " / " + Integer.toString(total));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(2, 0, is);
		}
		if (!this.world.equalsIgnoreCase(WorldDefine.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.MAP);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Average_Dungeon_Chunk_Distance);

			List<String> lores = new ArrayList<>();
			lores.add(Integer.toString(distance));
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(3, 0, is);
		}
		if (!this.world.equalsIgnoreCase(WorldDefine.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BELL);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Spawner_Rejection_Rate);

			List<String> lores = new ArrayList<>();
			lores.add(Double.toString(rate));
			lores.add(I18n.instance.Spawner_Rejection_Rate_Lore);
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(3, 1, is);
		}
		{
			ItemStack is = new ItemStack(Material.LEVER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Back);
			is.setItemMeta(im);

			addItem(3, 8, is);
		}
	}
}
