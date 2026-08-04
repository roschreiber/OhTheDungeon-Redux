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
import otd.util.I18n;
import otd.redux.util.MenuHelper;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;

/**
 *
 * @author
 */
public class WorldSpawnerManager extends Content {
	public final String world;
	public final WorldEditor parent;
	private boolean egg_change_spawner;
	private boolean silk_vanilla_spawner;
	private boolean silk_dungeon_spawner;
	private boolean mob_drop_in_vanilla_spawner;
	private boolean mob_drop_in_dungeon_spawner;
	private float disappearance_rate_vanilla;
	private float disappearance_rate_dungeon;
	private boolean prevent_spawner_break;
	private boolean prevent_spawner_drop;

	private final static int SLOT = 18;

	private final static Material RATE = Material.WRITABLE_BOOK;
	private final static Material APPLY = Material.LEVER;

	private WorldSpawnerManager() {
		super("", SLOT);
		this.world = null;
		this.parent = null;
	}

	public static WorldSpawnerManager instance = new WorldSpawnerManager();

	public WorldSpawnerManager(String world, WorldEditor parent) {
		super(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.World_Spawner_Manager + " " + world, SLOT);
		this.world = world;
		this.parent = parent;

		{
			if (WorldConfig.wc.dict.containsKey(world)) {
				SimpleWorldConfig config = WorldConfig.wc.dict.get(world);

				egg_change_spawner = config.egg_change_spawner;
				silk_vanilla_spawner = config.silk_vanilla_spawner;
				silk_dungeon_spawner = config.silk_dungeon_spawner;
				mob_drop_in_vanilla_spawner = config.mob_drop_in_vanilla_spawner;
				mob_drop_in_dungeon_spawner = config.mob_drop_in_dungeon_spawner;
				disappearance_rate_vanilla = config.disappearance_rate_vanilla;
				disappearance_rate_dungeon = config.disappearance_rate_dungeon;
				prevent_spawner_break = config.prevent_spawner_breaking;
				prevent_spawner_drop = config.prevent_spawner_dropping;
			} else {
				egg_change_spawner = false;
				silk_vanilla_spawner = false;
				silk_dungeon_spawner = false;
				mob_drop_in_vanilla_spawner = true;
				mob_drop_in_dungeon_spawner = false;
				disappearance_rate_vanilla = 0;
				disappearance_rate_dungeon = 0;
				prevent_spawner_break = false;
				prevent_spawner_drop = false;
			}

			if (disappearance_rate_vanilla < 0)
				disappearance_rate_vanilla = 0;
			if (disappearance_rate_vanilla > 100)
				disappearance_rate_vanilla = 100;
			if (disappearance_rate_dungeon < 0)
				disappearance_rate_dungeon = 0;
			if (disappearance_rate_dungeon > 100)
				disappearance_rate_dungeon = 100;
		}

	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof WorldSpawnerManager)) {
			return;
		}
		kcancel(e);

		int slot = e.getRawSlot();
		if (slot < 0 && slot >= 18) {
			return;
		}

		Player p = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();

		if (clickedItem == null || clickedItem.getType() == Material.AIR)
			return;
		WorldSpawnerManager holder = (WorldSpawnerManager) e.getInventory().getHolder();
		if (holder == null || holder.world == null)
			return;

//        Bukkit.getLogger().log(Level.SEVERE, "!"+slot);

		if (slot == 0) {
			holder.egg_change_spawner = !holder.egg_change_spawner;
			WorldConfig.save();
			holder.init();
		}
		if (slot == 1) {
			holder.mob_drop_in_vanilla_spawner = !holder.mob_drop_in_vanilla_spawner;
			WorldConfig.save();
			holder.init();
		}
		if (slot == 2) {
			holder.mob_drop_in_dungeon_spawner = !holder.mob_drop_in_dungeon_spawner;
			WorldConfig.save();
			holder.init();
		}
		if (slot == 3) {
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.disappearance_rate_vanilla += 1;
				WorldConfig.save();
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				holder.disappearance_rate_vanilla -= 1;
				WorldConfig.save();
			}
			holder.init();
		}
		if (slot == 4) {
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.disappearance_rate_dungeon += 1;
				WorldConfig.save();
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				holder.disappearance_rate_dungeon -= 1;
				WorldConfig.save();
			}
			holder.init();
		}
		if (slot == 5) {
			holder.prevent_spawner_break = !holder.prevent_spawner_break;
			WorldConfig.save();
			holder.init();
		}
		if (slot == 6) {
			holder.prevent_spawner_drop = !holder.prevent_spawner_drop;
			WorldConfig.save();
			holder.init();
		}
		if (slot == 17) {
			holder.save();
			WorldConfig.save();

			holder.parent.openInventory(p);
		}
	}

	private void save() {
		if (WorldConfig.wc.dict.containsKey(world)) {
			SimpleWorldConfig config = WorldConfig.wc.dict.get(world);
			config.egg_change_spawner = this.egg_change_spawner;
			config.silk_vanilla_spawner = this.silk_vanilla_spawner;
			config.silk_dungeon_spawner = this.silk_dungeon_spawner;
			config.mob_drop_in_vanilla_spawner = this.mob_drop_in_vanilla_spawner;
			config.mob_drop_in_dungeon_spawner = this.mob_drop_in_dungeon_spawner;
			config.disappearance_rate_vanilla = this.disappearance_rate_vanilla;
			config.disappearance_rate_dungeon = this.disappearance_rate_dungeon;
			config.prevent_spawner_breaking = this.prevent_spawner_break;
			config.prevent_spawner_dropping = this.prevent_spawner_drop;
		} else {
			SimpleWorldConfig config = new SimpleWorldConfig();
			config.egg_change_spawner = this.egg_change_spawner;
			config.silk_vanilla_spawner = this.silk_vanilla_spawner;
			config.silk_dungeon_spawner = this.silk_dungeon_spawner;
			config.mob_drop_in_vanilla_spawner = this.mob_drop_in_vanilla_spawner;
			config.mob_drop_in_dungeon_spawner = this.mob_drop_in_dungeon_spawner;
			config.disappearance_rate_vanilla = this.disappearance_rate_vanilla;
			config.disappearance_rate_dungeon = this.disappearance_rate_dungeon;
			config.prevent_spawner_breaking = this.prevent_spawner_break;
			config.prevent_spawner_dropping = this.prevent_spawner_drop;
			WorldConfig.wc.dict.put(world, config);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		if (disappearance_rate_vanilla < 0)
			disappearance_rate_vanilla = 0;
		if (disappearance_rate_vanilla > 100)
			disappearance_rate_vanilla = 100;
		if (disappearance_rate_dungeon < 0)
			disappearance_rate_dungeon = 0;
		if (disappearance_rate_dungeon > 100)
			disappearance_rate_dungeon = 100;
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(egg_change_spawner));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.ChangeSpawner);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(egg_change_spawner));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(0, 0, is);
		}
//        {
//            Material material;
//            String status;
//            if(silk_vanilla_spawner) {
//                material = ENABLE;
//                status = ChatColor.of("#2ECC71") + "\u2714 " + I18n.instance.Enable;
//            } else {
//                material = DISABLE;
//                status = ChatColor.of("#555555") + "\u2718 " + I18n.instance.Disable;
//            }
//            ItemStack is = new ItemStack(material);
//            ItemMeta im = is.getItemMeta();
//            im.setDisplayName(I18n.instance.Silk_Vanilla_Spawner);
//            List<String> lores = new ArrayList<>();
//            lores.add(ChatColor.of("#7F8C8D") + I18n.instance.Status + ": " + status);
//            im.setLore(lores);
//            
//            is.setItemMeta(im);
//            
//            addItem(0, 1, is);
//        }
//        {
//            Material material;
//            String status;
//            if(silk_dungeon_spawner) {
//                material = ENABLE;
//                status = ChatColor.of("#2ECC71") + "\u2714 " + I18n.instance.Enable;
//            } else {
//                material = DISABLE;
//                status = ChatColor.of("#555555") + "\u2718 " + I18n.instance.Disable;
//            }
//            ItemStack is = new ItemStack(material);
//            ItemMeta im = is.getItemMeta();
//            im.setDisplayName(I18n.instance.Silk_Dungeon_Spawner);
//            List<String> lores = new ArrayList<>();
//            lores.add(ChatColor.of("#7F8C8D") + I18n.instance.Status + ": " + status);
//            lores.add(I18n.instance.Dungeon_Spawner_Lore1);
//            lores.add(I18n.instance.Dungeon_Spawner_Lore2);
//            im.setLore(lores);
//            
//            is.setItemMeta(im);
//            
//            addItem(0, 2, is);
//        }
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(mob_drop_in_vanilla_spawner));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Mob_Drop_In_Vanilla_Spawner);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(mob_drop_in_vanilla_spawner));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(0, 1, is);
		}
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(mob_drop_in_dungeon_spawner));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Mob_Drop_In_Dungeon_Spawner);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(mob_drop_in_dungeon_spawner));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.desc(I18n.instance.Dungeon_Spawner_Lore1));
			lores.add(MenuHelper.desc(I18n.instance.Dungeon_Spawner_Lore2));
			lores.add(MenuHelper.desc(I18n.instance.Dungeon_Spawner_Lore3));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(0, 2, is);
		}
		{
			Material material = RATE;
			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Spawner_Disappear_Vanilla);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.value(I18n.instance.Rate, disappearance_rate_vanilla + "%"));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Amount_Item_Tip1));
			lores.add(MenuHelper.actionHint(I18n.instance.Amount_Item_Tip2));
			lores.add(MenuHelper.separator());
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(0, 3, is);
		}
		{
			Material material = RATE;
			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Spawner_Disappear_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.value(I18n.instance.Rate, disappearance_rate_dungeon + "%"));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.desc(I18n.instance.Dungeon_Spawner_Lore1));
			lores.add(MenuHelper.desc(I18n.instance.Dungeon_Spawner_Lore2));
			lores.add(MenuHelper.desc(I18n.instance.Dungeon_Spawner_Lore3));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Amount_Item_Tip1));
			lores.add(MenuHelper.actionHint(I18n.instance.Amount_Item_Tip2));
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(0, 4, is);
		}
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(prevent_spawner_break));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Prevent_Breaking_Of_Dungeon_Spawner);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(prevent_spawner_break));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.desc(I18n.instance.Prevent_Breaking_Of_Dungeon_Spawner_Lore));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));

			im.setLore(lores);

			is.setItemMeta(im);

			addItem(0, 5, is);
		}
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(prevent_spawner_drop));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Prevent_Dropping_Of_Dungeon_Spawner);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(prevent_spawner_drop));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.desc(I18n.instance.Prevent_Dropping_Of_Dungeon_Spawner_Lore));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));

			im.setLore(lores);

			is.setItemMeta(im);

			addItem(0, 6, is);
		}
		addItem(1, 8, MenuHelper.back());
	}
}
