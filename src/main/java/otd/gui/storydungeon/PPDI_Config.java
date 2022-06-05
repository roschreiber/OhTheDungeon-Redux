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

/**
 *
 * @author shadow
 */
public class PPDI_Config extends Content {
	public static PPDI_Config instance = new PPDI_Config();
	private final static int SLOT = 36;

	public PPDI_Config() {
		super(I18n.instance.PPDI_Cfg, SLOT);
	}

	private final static Material DISABLE = Material.MUSIC_DISC_BLOCKS;
	private final static Material ENABLE = Material.MUSIC_DISC_CAT;
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

		if (slot == 0) {
			DungeonConfig.instance.enableMoneyPayment = !DungeonConfig.instance.enableMoneyPayment;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 2) {
			DungeonConfig.instance.money += 10000;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 3) {
			DungeonConfig.instance.money += 1000;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 4) {
			DungeonConfig.instance.money += 100;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 5) {
			DungeonConfig.instance.money += 10;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 6) {
			DungeonConfig.instance.money += 1;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 9 + 2) {
			DungeonConfig.instance.money -= 10000;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 9 + 3) {
			DungeonConfig.instance.money -= 1000;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 9 + 4) {
			DungeonConfig.instance.money -= 100;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 9 + 5) {
			DungeonConfig.instance.money -= 10;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 9 + 6) {
			DungeonConfig.instance.money -= 1;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}

		if (slot == 18 + 0) {
			DungeonConfig.instance.enableLevelPayment = !DungeonConfig.instance.enableLevelPayment;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 18 + 2) {
			DungeonConfig.instance.level += 10;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 18 + 3) {
			DungeonConfig.instance.level += 1;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 9 + 18 + 2) {
			DungeonConfig.instance.level -= 10;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 9 + 18 + 3) {
			DungeonConfig.instance.level -= 1;
			DungeonConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
	}

	@Override
	public void init() {
		show();
	}

	@SuppressWarnings("deprecation")
	private void show() {
		inv.clear();
		{
			Material material;
			String status;
			if (DungeonConfig.instance.enableMoneyPayment) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack icon = new ItemStack(material, 1);
			ItemMeta im = icon.getItemMeta();

			List<String> lores = new ArrayList<>();
			lores.add(status);
			lores.add(I18n.instance.Require_Vault);
			im.setLore(lores);

			im.setDisplayName(I18n.instance.EnableMoneyPayment);

			icon.setItemMeta(im);

			addItem(0, icon);
		}
		{
			ItemStack icon = new ItemStack(INC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("+10000");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(2, icon);
		}
		{
			ItemStack icon = new ItemStack(INC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("+1000");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(3, icon);
		}
		{
			ItemStack icon = new ItemStack(INC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("+100");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(4, icon);
		}
		{
			ItemStack icon = new ItemStack(INC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("+10");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(5, icon);
		}
		{
			ItemStack icon = new ItemStack(INC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("+1");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(6, icon);
		}

		{
			ItemStack icon = new ItemStack(DEC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("-10000");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(9 + 2, icon);
		}
		{
			ItemStack icon = new ItemStack(DEC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("-1000");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(9 + 3, icon);
		}
		{
			ItemStack icon = new ItemStack(DEC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("-100");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(9 + 4, icon);
		}
		{
			ItemStack icon = new ItemStack(DEC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("-10");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(9 + 5, icon);
		}
		{
			ItemStack icon = new ItemStack(DEC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("-1");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.money);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(9 + 6, icon);
		}

		{
			Material material;
			String status;
			if (DungeonConfig.instance.enableLevelPayment) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack icon = new ItemStack(material, 1);
			ItemMeta im = icon.getItemMeta();

			List<String> lores = new ArrayList<>();
			lores.add(status);
			im.setLore(lores);

			im.setDisplayName(I18n.instance.EnableLevelPayment);

			icon.setItemMeta(im);

			addItem(18 + 0, icon);
		}
		{
			ItemStack icon = new ItemStack(INC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("+10");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.level);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(18 + 2, icon);
		}
		{
			ItemStack icon = new ItemStack(INC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("+1");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.level);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(18 + 3, icon);
		}

		{
			ItemStack icon = new ItemStack(DEC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("-10");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.level);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(18 + 9 + 2, icon);
		}
		{
			ItemStack icon = new ItemStack(DEC, 1);
			ItemMeta im = icon.getItemMeta();

			im.setDisplayName("-1");
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Current_Value + " : " + DungeonConfig.instance.level);
			im.setLore(lores);

			icon.setItemMeta(im);

			addItem(18 + 9 + 3, icon);
		}
	}
}
