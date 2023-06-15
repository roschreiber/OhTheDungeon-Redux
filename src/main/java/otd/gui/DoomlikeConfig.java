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

import forge_sandbox.jaredbgreat.dldungeons.Difficulty;
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
public class DoomlikeConfig extends Content {
	public static DoomlikeConfig instance = new DoomlikeConfig();
	private final static int SLOT = 18;

	private final static Material DISABLE = Material.MUSIC_DISC_BLOCKS;
	private final static Material ENABLE = Material.MUSIC_DISC_CAT;

	private final static Material NONE = Material.WHITE_DYE;
	private final static Material BABY = Material.BLUE_DYE;
	private final static Material NOOB = Material.GREEN_DYE;
	private final static Material NORM = Material.YELLOW_DYE;
	private final static Material HARD = Material.ORANGE_DYE;
	private final static Material NUTS = Material.RED_DYE;

	private DoomlikeConfig() {
		super("", SLOT);
		this.world = null;
		this.parent = null;
	}

	public final String world;
	public final Content parent;

	public DoomlikeConfig(String world, Content parent) {
		super(I18n.instance.Doomlike_Config, 18);
		this.world = world;
		this.parent = parent;
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof DoomlikeConfig)) {
			return;
		}
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			kcancel(e);
			return;
		}

		kcancel(e);
		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();
		DoomlikeConfig holder = (DoomlikeConfig) e.getInventory().getHolder();
		if (holder == null)
			return;

		SimpleWorldConfig swc = WorldConfig.wc.dict.get(holder.world);

		if (slot == 0) {
			swc.doomlike.doNaturalSpawn = !swc.doomlike.doNaturalSpawn;
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 1) {
			swc.doomlike.easyFind = !swc.doomlike.easyFind;
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 2) {
			swc.doomlike.singleEntrance = !swc.doomlike.singleEntrance;
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 3) {
			swc.doomlike.thinSpawners = !swc.doomlike.thinSpawners;
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 4) {
			Difficulty diff = swc.doomlike.difficulty;
			if (null != diff)
				switch (diff) {
				case NONE:
					diff = Difficulty.BABY;
					break;
				case BABY:
					diff = Difficulty.NOOB;
					break;
				case NOOB:
					diff = Difficulty.NORM;
					break;
				case NORM:
					diff = Difficulty.HARD;
					break;
				case HARD:
					diff = Difficulty.NUTS;
					break;
				case NUTS:
					diff = Difficulty.BABY;
					break;
				default:
					break;
				}
			swc.doomlike.difficulty = diff;
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 5) {
			Set<String> biomes = swc.doomlike.biomeExclusions;
			BiomeSetting bs = new BiomeSetting(holder.world, holder, biomes);
			bs.openInventory(p);
		}
		if (slot == 6) {
			// loot
			List<LootNode> loots = swc.doomlike.loots;
			LootManager lm = new LootManager(loots, holder);
			lm.openInventory(p);
		}
		if (slot == 7) {
			swc.doomlike.builtinLoot = !swc.doomlike.builtinLoot;
			WorldConfig.save();
			p.sendMessage(I18n.instance.World_Config_Save);
			holder.init();
		}
		if (slot == 9) {
			p.sendMessage(ChatColor.BLUE + dungeonURL + "#doomlike");
		}
		if (slot == 17) {
			holder.parent.openInventory(p);
		}
	}

	@Override
	public void init() {
		String world_name = world;
		SimpleWorldConfig swc;
		if (WorldConfig.wc.dict.containsKey(world_name))
			swc = WorldConfig.wc.dict.get(world_name);
		else {
			swc = new SimpleWorldConfig();
			WorldConfig.wc.dict.put(world_name, swc);
		}
		show();
	}

	@SuppressWarnings("deprecation")
	private void show() {
		inv.clear();
		String world_name = world;
		SimpleWorldConfig swc;
		if (WorldConfig.wc.dict.containsKey(world_name))
			swc = WorldConfig.wc.dict.get(world_name);
		else {
			swc = new SimpleWorldConfig();
			WorldConfig.wc.dict.put(world_name, swc);
		}
		{
			Material material;
			String status;
			if (swc.doomlike.doNaturalSpawn) {
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

			addItem(0, 0, is);
		}
		{
			Material material;
			String status;
			if (swc.doomlike.easyFind) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Easy_Find);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			for (String str : I18n.instance.EasyFindStr) {
				lores.add(str);
			}
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 1, is);
		}
		{
			Material material;
			String status;
			if (swc.doomlike.singleEntrance) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Single_Entrance);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 2, is);
		}
		{
			Material material;
			String status;
			if (swc.doomlike.thinSpawners) {
				material = ENABLE;
				status = I18n.instance.Enable;
			} else {
				material = DISABLE;
				status = I18n.instance.Disable;
			}
			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Thin_Spawners);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.Status + " : " + status);
			for (String str : I18n.instance.ThinSpawnersStr) {
				lores.add(str);
			}
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 3, is);
		}
		{
			Material material;
			String status;
			switch (swc.doomlike.difficulty) {
			case NONE:
				material = NONE;
				status = I18n.instance.Difficulty[1];
				break;
			case BABY:
				material = BABY;
				status = I18n.instance.Difficulty[2];
				break;
			case NOOB:
				material = NOOB;
				status = I18n.instance.Difficulty[3];
				break;
			case NORM:
				material = NORM;
				status = I18n.instance.Difficulty[4];
				break;
			case HARD:
				material = HARD;
				status = I18n.instance.Difficulty[5];
				break;
			case NUTS:
				material = NUTS;
				status = I18n.instance.Difficulty[6];
				break;
			default:
				material = NORM;
				status = I18n.instance.Difficulty[4];
				break;
			}

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Difficulty[0]);
			List<String> lores = new ArrayList<>();
			lores.add(status);
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(0, 4, is);
		}
		{
			ItemStack is = new ItemStack(Material.LILAC);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Biome_Setting);
			is.setItemMeta(im);

			addItem(0, 5, is);
		}
		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Loot_Config);
			is.setItemMeta(im);

			addItem(0, 6, is);
		}
		{
			Material material;
			String status;
			if (swc.doomlike.builtinLoot) {
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

			addItem(0, 7, is);
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
