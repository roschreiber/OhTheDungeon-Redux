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
import otd.util.I18n;
import otd.config.LootNode;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;

/**
 *
 * @author
 */
public class AetherDungeonConfig extends Content {
	public static AetherDungeonConfig instance = new AetherDungeonConfig();
	private final static int SLOT = 18;
	public String world;
	private final Content parent;

	private AetherDungeonConfig() {
		super("", SLOT);
		this.world = null;
		this.parent = null;
	}

	public AetherDungeonConfig(String world, Content parent) {
		super(I18n.instance.AetherDungeon_Config, SLOT);
		this.world = world;
		this.parent = parent;
	}

	private final static Material DISABLE = Material.MUSIC_DISC_BLOCKS;
	private final static Material ENABLE = Material.MUSIC_DISC_CAT;

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof AetherDungeonConfig)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		AetherDungeonConfig holder = (AetherDungeonConfig) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (holder.world == null)
			return;
		String key = holder.world;
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(key);

		if (slot == 0) {
			swc.aether_dungeon.doNaturalSpawn = !swc.aether_dungeon.doNaturalSpawn;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 1) {
			List<LootNode> loots = swc.aether_dungeon.loots;
			LootManager lm = new LootManager(loots, holder);
			lm.openInventory(p);
		}
		if (slot == 2) {
			Set<String> biomes = swc.aether_dungeon.biomeExclusions;
			BiomeSetting bs = new BiomeSetting(holder.world, holder, biomes);
			bs.openInventory(p);
		}
		if (slot == 3) {
			swc.aether_dungeon.builtinLoot = !swc.aether_dungeon.builtinLoot;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 4) {
			swc.aether_dungeon.cloud = !swc.aether_dungeon.cloud;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 5) {
			if (e.getClick().equals(ClickType.LEFT)) {
				swc.aether_dungeon.height++;
				if (swc.aether_dungeon.height > 220)
					swc.aether_dungeon.height = 220;
				WorldConfig.save();
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				swc.aether_dungeon.height--;
				if (swc.aether_dungeon.height < 90)
					swc.aether_dungeon.height = 90;
				WorldConfig.save();
			}
			holder.init();
		}
		if (slot == 9) {
			p.sendMessage(ChatColor.BLUE + dungeonURL + "#aether");
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
		{
			Material material;
			String status;
			if (swc.aether_dungeon.doNaturalSpawn) {
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
		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Loot_Config);
			is.setItemMeta(im);

			addItem(1, is);
		}
		{
			ItemStack is = new ItemStack(Material.LILAC);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Biome_Setting);
			is.setItemMeta(im);

			addItem(2, is);
		}
		{
			Material material;
			String status;
			if (swc.aether_dungeon.builtinLoot) {
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

			addItem(3, is);
		}
		{
			Material material;
			String status;
			if (swc.aether_dungeon.cloud) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Aether_Dungeon_Cloud);

			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			lores.add(I18n.instance.Aether_Dungeon_Cloud_Lore);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(4, is);
		}
		{
			Material material = Material.ELYTRA;

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Aether_Dungeon_Height);

			String height = "Y = " + swc.aether_dungeon.height;
			List<String> lores = new ArrayList<>();
			lores.add(height);
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(5, is);
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
