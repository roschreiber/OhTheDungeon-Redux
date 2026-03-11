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
package otd.gui.storydungeon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import otd.addon.com.ohthedungeon.storydungeon.config.DungeonConfig;
import otd.gui.Content;
import otd.util.I18n;
import otd.redux.util.MenuHelper;

/**
 *
 * @author shadow
 */
public class PPDI_Config extends Content {
	public static PPDI_Config instance = new PPDI_Config();
	private final static int SLOT = 45;

	public PPDI_Config() {
		super(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.PPDI_Cfg, SLOT);
	}

	private final static Material INC = Material.MAGMA_CREAM;
	private final static Material DEC = Material.SLIME_BALL;

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof PPDI_Config)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		PPDI_Config holder = (PPDI_Config) e.getInventory().getHolder();
		if (holder == null)
			return;

		if (slot == 9) {
			DungeonConfig.instance.enableMoneyPayment = !DungeonConfig.instance.enableMoneyPayment;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}

		if (slot == 12) { DungeonConfig.instance.money += 10000; DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 13) { DungeonConfig.instance.money += 1000;  DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 14) { DungeonConfig.instance.money += 100;   DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 15) { DungeonConfig.instance.money += 10;    DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 16) { DungeonConfig.instance.money += 1;     DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }

		if (slot == 21) { DungeonConfig.instance.money -= 10000; DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 22) { DungeonConfig.instance.money -= 1000;  DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 23) { DungeonConfig.instance.money -= 100;   DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 24) { DungeonConfig.instance.money -= 10;    DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 25) { DungeonConfig.instance.money -= 1;     DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }

		if (slot == 27) {
			DungeonConfig.instance.enableLevelPayment = !DungeonConfig.instance.enableLevelPayment;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		// Level increments (slots 30-31)
		if (slot == 30) { DungeonConfig.instance.level += 10; DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 31) { DungeonConfig.instance.level += 1;  DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		// Level decrements (slots 39-40)
		if (slot == 39) { DungeonConfig.instance.level -= 10; DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		if (slot == 40) { DungeonConfig.instance.level -= 1;  DungeonConfig.save(); p.sendMessage(I18n.instance.World_Config_Save); holder.init(); }
		// Back (slot 44)
		if (slot == 44) {
			p.closeInventory();
		}
	}

	@Override
	public void init() {
		show();
	}

	@SuppressWarnings("deprecation")
	private void show() {
		inv.clear();
		MenuHelper.fillRow(this, 0);
		{
			ItemStack header = new ItemStack(Material.BOOKSHELF);
			ItemMeta im = header.getItemMeta();
			im.setDisplayName(MenuHelper.gradient(I18n.instance.PPDI_Cfg, MenuHelper.PRIMARY, MenuHelper.ACCENT));
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.desc("Configure entry costs for dungeon instances"));
			lores.add(MenuHelper.separator());
			im.setLore(lores);
			header.setItemMeta(im);
			addItem(0, 4, header);
		}
		for (int row = 1; row <= 4; row++) {
			addItem(row, 1, MenuHelper.filler(MenuHelper.FILLER_ACCENT));
		}

		{
			boolean enabled = DungeonConfig.instance.enableMoneyPayment;
			ItemStack icon = new ItemStack(MenuHelper.toggleMaterial(enabled), 1);
			ItemMeta im = icon.getItemMeta();

			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(enabled));
			lores.add(MenuHelper.desc(I18n.instance.Require_Vault));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to toggle"));
			im.setLore(lores);

			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.EnableMoneyPayment);

			icon.setItemMeta(im);

			addItem(1, 0, icon);
		}
		{
			ItemStack is = new ItemStack(Material.GOLD_INGOT);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.GOLD) + I18n.instance.EnableMoneyPayment);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.value(I18n.instance.Current_Value, Integer.toString(DungeonConfig.instance.money)));
			lores.add(MenuHelper.separator());
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(1, 2, is);
		}
		adjuster(1, 3, true,  10000, DungeonConfig.instance.money);
		adjuster(1, 4, true,   1000, DungeonConfig.instance.money);
		adjuster(1, 5, true,    100, DungeonConfig.instance.money);
		adjuster(1, 6, true,     10, DungeonConfig.instance.money);
		adjuster(1, 7, true,      1, DungeonConfig.instance.money);

		adjuster(2, 3, false, 10000, DungeonConfig.instance.money);
		adjuster(2, 4, false,  1000, DungeonConfig.instance.money);
		adjuster(2, 5, false,   100, DungeonConfig.instance.money);
		adjuster(2, 6, false,    10, DungeonConfig.instance.money);
		adjuster(2, 7, false,     1, DungeonConfig.instance.money);

		{
			boolean enabled = DungeonConfig.instance.enableLevelPayment;
			ItemStack icon = new ItemStack(MenuHelper.toggleMaterial(enabled));
			ItemMeta im = icon.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.EnableLevelPayment);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(enabled));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to toggle"));
			im.setLore(lores);
			icon.setItemMeta(im);
			addItem(3, 0, icon);
		}
		{
			ItemStack is = new ItemStack(Material.EXPERIENCE_BOTTLE);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.INFO) + I18n.instance.EnableLevelPayment);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.value(I18n.instance.Current_Value, Integer.toString(DungeonConfig.instance.level)));
			lores.add(MenuHelper.separator());
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(3, 2, is);
		}
		adjuster(3, 3, true,  10, DungeonConfig.instance.level);
		adjuster(3, 4, true,   1, DungeonConfig.instance.level);

		adjuster(4, 3, false, 10, DungeonConfig.instance.level);
		adjuster(4, 4, false,  1, DungeonConfig.instance.level);
		addItem(4, 8, MenuHelper.back());
	}

	private void adjuster(int row, int col, boolean increment, int amount, int current) {
		ItemStack icon = new ItemStack(increment ? INC : DEC);
		ItemMeta im = icon.getItemMeta();
		im.setDisplayName((increment
				? MenuHelper.color(MenuHelper.SUCCESS) + "+"
				: MenuHelper.color(MenuHelper.DANGER) + "-") + amount);
		List<String> lores = new ArrayList<>();
		lores.add(MenuHelper.value(I18n.instance.Current_Value, Integer.toString(current)));
		im.setLore(lores);
		icon.setItemMeta(im);
		addItem(row, col, icon);
	}
}
