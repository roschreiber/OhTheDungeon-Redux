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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import otd.config.CustomDungeonType;
import otd.config.WorldConfig;
import otd.config.WorldConfig.CustomDungeon;
import otd.gui.BiomeSetting;
import otd.gui.Content;
import otd.gui.LootManager;
import otd.util.I18n;

/**
 *
 * @author shadow
 */
public class CustomDungeonEditor extends Content {
	public final static CustomDungeonEditor instance = new CustomDungeonEditor();
	private final static int SLOT = 18;
	public final Content parent;
	public final CustomDungeon dungeon;

	private final static Material RECORD = Material.MUSIC_DISC_WAIT;
//    private final static Material DISABLE = Material.MUSIC_DISC_BLOCKS;
//    private final static Material ENABLE = Material.MUSIC_DISC_CAT;

	private CustomDungeonEditor() {
		super(I18n.instance.Custom_Dungeon_Editor, SLOT);
		parent = null;
		dungeon = null;
	}

	public CustomDungeonEditor(CustomDungeon dungeon, Content parent) {
		super(I18n.instance.Custom_Dungeon_Editor, SLOT);
		this.parent = parent;
		this.dungeon = dungeon;
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof CustomDungeonEditor)) {
			return;
		}

		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		CustomDungeonEditor holder = (CustomDungeonEditor) e.getInventory().getHolder();
		if (holder == null)
			return;

		if (slot == 0) {
			SchematicSelect ss = new SchematicSelect(holder, holder.dungeon);
			ss.openInventory(p);
		}

		if (slot == 1) {
			BiomeSetting bs = new BiomeSetting(null, holder, holder.dungeon.biomeExclusions);
			bs.openInventory(p);
		}

		if (slot == 2) {
			LootManager lm = new LootManager(holder.dungeon.loots, holder);
			lm.openInventory(p);
		}

		if (slot == 3) {
			MobSelect ms = new MobSelect(holder.dungeon, holder);
			ms.openInventory(p);
		}

		if (slot == 4) {
			if (holder.dungeon.type == CustomDungeonType.ground)
				holder.dungeon.type = CustomDungeonType.sky;
			else
				holder.dungeon.type = CustomDungeonType.ground;
			WorldConfig.save();
			holder.init();
		}

		if (slot == 5) {
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.dungeon.sky_spawn_height++;
				WorldConfig.save();
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				holder.dungeon.sky_spawn_height--;
				WorldConfig.save();
			}
			holder.init();
		}
		if (slot == 6) {
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.dungeon.offset[0]++;
				WorldConfig.save();
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				holder.dungeon.offset[0]--;
				WorldConfig.save();
			}
			holder.init();
		}
		if (slot == 7) {
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.dungeon.offset[1]++;
				WorldConfig.save();
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				holder.dungeon.offset[1]--;
				WorldConfig.save();
			}
			holder.init();
		}
		if (slot == 8) {
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.dungeon.offset[2]++;
				WorldConfig.save();
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				holder.dungeon.offset[2]--;
				WorldConfig.save();
			}
			holder.init();
		}
		if (slot == 9) {
			if (e.getClick().equals(ClickType.LEFT)) {
				holder.dungeon.weight++;
				WorldConfig.save();
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				holder.dungeon.weight--;
				if (holder.dungeon.weight <= 0)
					holder.dungeon.weight = 1;
				WorldConfig.save();
			}
			holder.init();
		}

		if (slot == 16) {
			ItemStack is = e.getInventory().getItem(slot);
			if (is != null && is.getType() == Material.LAVA_BUCKET) {
				WorldConfig.wc.custom_dungeon.remove(holder.dungeon.id);
				WorldConfig.save();
			}
			holder.parent.openInventory(p);

		}
		if (slot == 17) {
			if (holder.dungeon.file == null || holder.dungeon.file.isEmpty()) {
				p.sendMessage(ChatColor.RED + I18n.instance.Custom_Dungeon_Err_Msg);
				return;
			}
			if (!WorldConfig.wc.custom_dungeon.containsKey(holder.dungeon.id)) {
				WorldConfig.wc.custom_dungeon.put(holder.dungeon.id, holder.dungeon);
			}
			WorldConfig.save();
			holder.parent.openInventory(p);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		{
			ItemStack is = new ItemStack(RECORD);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Schematic_Select);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Schematic_File + " : " + ChatColor.BLUE + dungeon.file);
			lores.add(I18n.instance.Schematic_Select_Lore);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, is);
		}
		{
			ItemStack is = new ItemStack(Material.LILAC);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Biome_Setting);
			is.setItemMeta(im);

			addItem(1, is);
		}
		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Loot_Config);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Custom_Dungeon_Chest_Lore);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(2, is);
		}
		{
			ItemStack is = new ItemStack(Material.SPAWNER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Mob_Select);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Mob_Select_Lore);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(3, is);
		}
		{
			Material icon;
			if (dungeon.type == CustomDungeonType.ground)
				icon = Material.GRASS_BLOCK;
			else
				icon = Material.VINE;
			ItemStack is = new ItemStack(icon);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Custom_Dungeon_Type);
			List<String> lores = new ArrayList<>();
			lores.add(dungeon.type.toString());
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(4, is);
		}
		{
			Material material = Material.ELYTRA;

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Aether_Dungeon_Height);

			String height = "Y = " + dungeon.sky_spawn_height;
			List<String> lores = new ArrayList<>();
			lores.add(height);
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(5, is);
		}
		{
			Material material = Material.COMPASS;

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Custom_Dungeon_Spawn_Offset + " : x");

			String height = "x = " + dungeon.offset[0];
			List<String> lores = new ArrayList<>();
			lores.add(height);
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(6, is);
		}
		{
			Material material = Material.COMPASS;

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Custom_Dungeon_Spawn_Offset + " : y");

			String height = "y = " + dungeon.offset[1];
			List<String> lores = new ArrayList<>();
			lores.add(height);
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(7, is);
		}
		{
			Material material = Material.COMPASS;

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Custom_Dungeon_Spawn_Offset + " : z");

			String height = "z = " + dungeon.offset[2];
			List<String> lores = new ArrayList<>();
			lores.add(height);
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(8, is);
		}
		{
			Material material = Material.ANVIL;

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Weight);

			String height = "" + dungeon.weight;
			List<String> lores = new ArrayList<>();
			lores.add(height);
			lores.add(I18n.instance.Amount_Item_Tip1);
			lores.add(I18n.instance.Amount_Item_Tip2);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(9, is);
		}

		if (WorldConfig.wc.custom_dungeon.containsKey(dungeon.id)) {
			ItemStack is = new ItemStack(Material.LAVA_BUCKET);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Remove);
			is.setItemMeta(im);

			addItem(1, 7, is);
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
