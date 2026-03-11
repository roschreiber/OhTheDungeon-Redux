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

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import otd.redux.util.ChatManager;
import otd.redux.util.MenuHelper;
import otd.config.LootNode;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.util.I18n;

/**
 *
 * @author
 */
public class CastleDungeonConfig extends Content {
	public static CastleDungeonConfig instance = new CastleDungeonConfig();
	private final static int SLOT = 18;
	public final String world;
	private final Content parent;

	private CastleDungeonConfig() {
		super("", SLOT);
		this.world = null;
		this.parent = null;
	}

	public CastleDungeonConfig(String world, Content parent) {
		super(MenuHelper.color(MenuHelper.LIGHT) + I18n.instance.CastleDungeon_Config, SLOT);
		this.world = world;
		this.parent = parent;
	}


	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof CastleDungeonConfig)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		CastleDungeonConfig holder = (CastleDungeonConfig) e.getInventory().getHolder();
		if (holder == null)
			return;
		if (holder.world == null)
			return;
		String key = holder.world;
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(key);

		if (slot == 0) {
			swc.castle.doNaturalSpawn = !swc.castle.doNaturalSpawn;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 1) {
			List<LootNode> loots = swc.castle.loots;
			LootManager lm = new LootManager(loots, holder);
			lm.openInventory(p);
		}
		if (slot == 2) {
			Set<String> biomes = swc.castle.biomeExclusions;
			BiomeSetting bs = new BiomeSetting(holder.world, holder, biomes);
			bs.openInventory(p);
		}
		if (slot == 3) {
			swc.castle.builtinLoot = !swc.castle.builtinLoot;
			WorldConfig.wc.dict.put(key, swc);
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 9) {
			ChatManager.getInstance().sendInfo(p, dungeonURL + "#castle");
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
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(swc.castle.doNaturalSpawn));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Natural_Spawn);

			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(swc.castle.doNaturalSpawn));
			lores.add(MenuHelper.separator());
			for (String str : I18n.instance.NaturalSpawnStr) {
				lores.add(MenuHelper.desc(str));
			}
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to toggle"));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, is);
		}
		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Loot_Config);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.actionHint("Click to configure"));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, is);
		}
		{
			ItemStack is = new ItemStack(Material.LILAC);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Biome_Setting);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.actionHint("Click to configure"));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(2, is);
		}
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(swc.castle.builtinLoot));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Builtin_Loot);

			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(swc.castle.builtinLoot));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to toggle"));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(3, is);
		}
		{
			ItemStack is = new ItemStack(Material.PAINTING);
			ItemMeta im = is.getItemMeta();
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.desc(I18n.instance.Preview_Lore1));
			lores.add(MenuHelper.desc(I18n.instance.Preview_Lore2));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to preview"));
			im.setLore(lores);
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Preview);
			is.setItemMeta(im);

			addItem(1, 0, is);
		}
		addItem(1, 8, MenuHelper.back());
	}
}
