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
import otd.redux.util.ChatManager;
import otd.redux.util.MenuHelper;
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
		super(MenuHelper.color(MenuHelper.DANGER) + I18n.instance.Doomlike_Config, 18);
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
			ChatManager.getInstance().sendInfo(p, dungeonURL + "#doomlike");
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
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(swc.doomlike.doNaturalSpawn));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Natural_Spawn);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(swc.doomlike.doNaturalSpawn));
			lores.add(MenuHelper.separator());
			for (String str : I18n.instance.NaturalSpawnStr) {
				lores.add(MenuHelper.desc(str));
			}
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 0, is);
		}
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(swc.doomlike.easyFind));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Easy_Find);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(swc.doomlike.easyFind));
			lores.add(MenuHelper.separator());
			for (String str : I18n.instance.EasyFindStr) {
				lores.add(MenuHelper.desc(str));
			}
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 1, is);
		}
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(swc.doomlike.singleEntrance));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Single_Entrance);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(swc.doomlike.singleEntrance));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 2, is);
		}
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(swc.doomlike.thinSpawners));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Thin_Spawners);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(swc.doomlike.thinSpawners));
			lores.add(MenuHelper.separator());
			for (String str : I18n.instance.ThinSpawnersStr) {
				lores.add(MenuHelper.desc(str));
			}
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));
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
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Difficulty[0]);
			List<String> lores = new ArrayList<>();
			lores.add(status);
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint("Click to change"));
			im.setLore(lores);
			is.setItemMeta(im);
			addItem(0, 4, is);
		}
		{
			ItemStack is = new ItemStack(Material.LILAC);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Biome_Setting);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Configure));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 5, is);
		}
		{
			ItemStack is = new ItemStack(Material.CHEST);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Loot_Config);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Configure));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 6, is);
		}
		{
			ItemStack is = new ItemStack(MenuHelper.toggleMaterial(swc.doomlike.builtinLoot));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Builtin_Loot);
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.status(swc.doomlike.builtinLoot));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Toggle));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, 7, is);
		}
		{
			ItemStack is = new ItemStack(Material.PAINTING);
			ItemMeta im = is.getItemMeta();
			List<String> lores = new ArrayList<>();
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.desc(I18n.instance.Preview_Lore1));
			lores.add(MenuHelper.desc(I18n.instance.Preview_Lore2));
			lores.add(MenuHelper.separator());
			lores.add(MenuHelper.actionHint(I18n.instance.Click_To_Preview));
			im.setLore(lores);
			im.setDisplayName(MenuHelper.color(MenuHelper.ACCENT) + I18n.instance.Preview);
			is.setItemMeta(im);

			addItem(1, 0, is);
		}
		addItem(1, 8, MenuHelper.back());
	}
}
