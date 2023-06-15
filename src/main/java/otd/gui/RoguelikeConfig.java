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
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import otd.lib.DungeonWorldManager;
import otd.util.I18n;
import otd.config.RoguelikeLootNode;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;

/**
 *
 * @author
 */
public class RoguelikeConfig extends Content {
	public String world;
	private final static int SLOT = 18;
	public final Content parent;

	public static RoguelikeConfig instance = new RoguelikeConfig();

	private RoguelikeConfig() {
		super("", SLOT);
		this.world = null;
		this.parent = null;
	}

	public RoguelikeConfig(String world, Content parent) {
		super(I18n.instance.Roguelike_Config + " : " + world, SLOT);
		this.world = world;
		this.parent = parent;
	}

	private final static Material DISABLE = Material.MUSIC_DISC_BLOCKS;
	private final static Material ENABLE = Material.MUSIC_DISC_CAT;

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof RoguelikeConfig)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();

		RoguelikeConfig holder = (RoguelikeConfig) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (holder.world == null)
			return;
		String key = holder.world;
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(key);
		if (slot == 0) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			swc.roguelike.doNaturalSpawn = !swc.roguelike.doNaturalSpawn;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 1) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			swc.roguelike.encase = !swc.roguelike.encase;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 2) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			swc.roguelike.generous = !swc.roguelike.generous;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 3) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			swc.roguelike.random = !swc.roguelike.random;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 4) {
			// loot
			List<RoguelikeLootNode> loots = swc.roguelike.loots;
			RoguelikeLootManager lm = new RoguelikeLootManager(loots, holder);
			lm.openInventory(p);
		}
		if (slot == 5) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			Set<String> biomes = swc.roguelike.biomeExclusions;
			BiomeSetting bs = new BiomeSetting(holder.world, holder, biomes);
			bs.openInventory(p);
		}
		if (slot == 6) {
			swc.roguelike.builtinLoot = !swc.roguelike.builtinLoot;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 7) {
			RogueLikeDungeonTower tower = new RogueLikeDungeonTower(holder.world, holder);
			tower.openInventory(p);
		}
		if (slot == 9) {
			p.sendMessage(ChatColor.BLUE + dungeonURL + "#roguelike");
		}
		if (slot == 17) {
			holder.parent.openInventory(p);
		}
	}

	@Override
	public void init() {
		if (WorldConfig.wc.dict.get(world) == null) {
			SimpleWorldConfig swc = new SimpleWorldConfig();
			WorldConfig.wc.dict.put(world, swc);
			WorldConfig.save();
		}
		show();
	}

	@SuppressWarnings("deprecation")
	private void show() {
		inv.clear();
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(world);
		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Natural_Spawn);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, is);
		} else {
			Material material;
			String status;
			if (swc.roguelike.doNaturalSpawn) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Natural_Spawn);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			for (String str : I18n.instance.NaturalSpawnStr) {
				lores.add(str);
			}
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Encase);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, is);
		} else {
			Material material;
			String status;
			if (swc.roguelike.encase) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Encase);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Generous);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(2, is);
		} else {
			Material material;
			String status;
			if (swc.roguelike.generous) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Generous);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			int len = I18n.instance.GenerousStr.size();
			for (int i = 0; i < len; i++)
				lores.add(I18n.instance.GenerousStr.get(i));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(2, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Random_Dungeon);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(3, is);
		} else {
			Material material;
			String status;
			if (swc.roguelike.random) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Random_Dungeon);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			lores.add(I18n.instance.Random_Dungeon_Content);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(3, is);
		}
		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Loot_Config);
			is.setItemMeta(im);

			addItem(4, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Biome_Setting);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(5, is);
		} else {
			ItemStack is = new ItemStack(Material.LILAC);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Biome_Setting);
			is.setItemMeta(im);

			addItem(5, is);
		}
		{
			Material material;
			String status;
			if (swc.roguelike.builtinLoot) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Builtin_Loot);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(6, is);
		}
		{
			ItemStack is = new ItemStack(Material.JUKEBOX);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Roguelike_Dungeon_Tower);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Roguelike_Dungeon_Tower_Lore);

			im.setLore(lores);

			is.setItemMeta(im);

			addItem(7, is);
		}
		{
			ItemStack is = new ItemStack(Material.PAINTING);
			ItemMeta im = is.getItemMeta();
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Preview_Lore1);
			lores.add(I18n.instance.Preview_Lore2);
			im.setLore(lores);
			im.setDisplayName(I18n.instance.Preview);
			is.setItemMeta(im);

			addItem(1, 0, is);
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
