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
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.gui.customstruct.WorldCustomDungeon;
import otd.integration.WorldEdit;
import otd.lib.DungeonWorldManager;
import otd.util.I18n;
import otd.util.Skull;
import otd.util.Wrap;
import otd.world.WorldDefine;

/**
 *
 * @author
 */
public class WorldEditor extends Content {
	public final String world;
	public final Environment env;
	public final Content parent;
	private boolean roguelike;
	private boolean doomlike;
	private boolean battletower;
	private boolean smoofy;
	private boolean draylar;
	private boolean ant;
	private boolean aether;
	private boolean lich;
	private boolean castle;
	// private boolean egg;
	private final static int SLOT = 36;
	private final static int WRAP = 30;
	private final ChatColor DESCRIPTION_COLOR = ChatColor.AQUA;

//    private final static Material DISABLE = Material.MUSIC_DISC_BLOCKS;
//    private final static Material ENABLE = Material.MUSIC_DISC_CAT;

	private WorldEditor() {
		super("", SLOT);
		this.world = null;
		this.env = Environment.NORMAL;
		this.parent = null;
	}

	public static WorldEditor instance = new WorldEditor();

	public WorldEditor(String world, Environment env, Content parent) {
		super(I18n.instance.World_Editor + " : " + world, SLOT);
		this.world = world;
		this.env = env;
		this.parent = parent;

		if (!WorldConfig.wc.dict.containsKey(world)) {
			WorldConfig.wc.dict.put(world, new SimpleWorldConfig());
		}
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof WorldEditor)) {
			return;
		}
		kcancel(e);

		int slot = e.getRawSlot();
		if (slot < 0 && slot >= 27) {
			return;
		}

		Player p = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();

		// verify current item is not null
		if (clickedItem == null || clickedItem.getType() == Material.AIR)
			return;
		WorldEditor holder = (WorldEditor) e.getInventory().getHolder();
		if (holder == null || holder.world == null)
			return;
		if (slot == 9) {
			RoguelikeConfig rc = new RoguelikeConfig(holder.world, holder);
			rc.openInventory(p);
		}
		if (slot == 10) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			DoomlikeConfig dc = new DoomlikeConfig(holder.world, holder);
			dc.openInventory(p);
		}
		if (slot == 11) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			BattleTowerConfig btc = new BattleTowerConfig(holder.world, holder);
			btc.openInventory(p);
		}
		if (slot == 12) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			SmoofyConfig sc = new SmoofyConfig(holder.world, holder);
			sc.openInventory(p);
		}
		if (slot == 13) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			DraylarBattleTowerConfig db = new DraylarBattleTowerConfig(holder.world, holder);
			db.openInventory(p);
		}
		if (slot == 14) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			AntManDungeonConfig am = new AntManDungeonConfig(holder.world, holder);
			am.openInventory(p);
		}
		if (slot == 15) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			AetherDungeonConfig ad = new AetherDungeonConfig(holder.world, holder);
			ad.openInventory(p);
		}
		if (slot == 16) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			LichTowerConfig ad = new LichTowerConfig(holder.world, holder);
			ad.openInventory(p);
		}
		if (slot == 17) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			if (holder.world.equals(WorldDefine.WORLD_NAME))
				return;
			if (!WorldEdit.isReady())
				return;
			WorldCustomDungeon wcd = new WorldCustomDungeon(holder.world, holder);
			wcd.openInventory(p);
		}
		if (slot == 18) {
			if (holder.world.equals(DungeonWorldManager.WORLD_NAME))
				return;
			CastleDungeonConfig cdc = new CastleDungeonConfig(holder.world, holder);
			cdc.openInventory(p);
		}
		if (slot == 27) {
			DungeonSpawnSetting dss = new DungeonSpawnSetting(holder.world, holder);
			dss.openInventory(p);
		}
		if (slot == 28) {
			WorldSpawnerManager wsm = new WorldSpawnerManager(holder.world, holder);
			wsm.openInventory(p);
		}
		if (slot == 29) {
			BossConfig boss = new BossConfig(holder.world, holder);
			boss.openInventory(p);
		}
		if (slot == 30) {
			if (holder.world.equals(WorldDefine.WORLD_NAME))
				return;
			if (e.getClick().equals(ClickType.LEFT)) {
				WorldParameter wp = new WorldParameter(holder.world, holder);
				wp.openInventory(p);
			}
			if (e.getClick().equals(ClickType.RIGHT)) {
				WorldParameter.sendURL(p);
			}
		}
		if (slot == 35) {
			holder.parent.openInventory(p);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		inv.clear();
		{
			if (WorldConfig.wc.dict.containsKey(world)) {
				SimpleWorldConfig config = WorldConfig.wc.dict.get(world);
				roguelike = config.roguelike.doNaturalSpawn;
				doomlike = config.doomlike.doNaturalSpawn;
				battletower = config.battletower.doNaturalSpawn;
				smoofy = config.smoofydungeon.doNaturalSpawn;
				draylar = config.draylar_battletower.doNaturalSpawn;
				ant = config.ant_man_dungeon.doNaturalSpawn;
				aether = config.aether_dungeon.doNaturalSpawn;
				lich = config.lich_tower.doNaturalSpawn;
				castle = config.castle.doNaturalSpawn;
				// egg = config.egg_change_spawner;
			} else {
				roguelike = false;
				doomlike = false;
				battletower = false;
				smoofy = false;
				draylar = false;
				ant = false;
				aether = false;
				lich = false;
				castle = false;
				// egg = true;
			}
		}
		{
			Material material;
			switch (env) {
			case NORMAL:
				material = WorldManager.NORMAL;
				break;
			case NETHER:
				material = WorldManager.NETHER;
				break;
			default:
				material = WorldManager.ENDER;
				break;
			}
			if (world.equals(DungeonWorldManager.WORLD_NAME)) {
				material = WorldManager.MAP;
			}

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(world);

			List<String> lores = new ArrayList<>();
			if (world.equals(DungeonWorldManager.WORLD_NAME)) {
				lores.add(I18n.instance.PPDI_WORLD);
			} else {
				if (roguelike) {
					lores.add(I18n.instance.Roguelike_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
				} else {
					lores.add(I18n.instance.Roguelike_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
				}
				if (doomlike) {
					lores.add(I18n.instance.Doomlike_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
				} else {
					lores.add(I18n.instance.Doomlike_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
				}
				if (battletower) {
					lores.add(I18n.instance.Battle_Tower + " : " + ChatColor.RED + I18n.instance.Enable);
				} else {
					lores.add(I18n.instance.Battle_Tower + " : " + ChatColor.GRAY + I18n.instance.Disable);
				}
				if (smoofy) {
					lores.add(I18n.instance.Smoofy_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
				} else {
					lores.add(I18n.instance.Smoofy_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
				}
				if (draylar) {
					lores.add(I18n.instance.Draylar_Battle_Tower + " : " + ChatColor.RED + I18n.instance.Enable);
				} else {
					lores.add(I18n.instance.Draylar_Battle_Tower + " : " + ChatColor.GRAY + I18n.instance.Disable);
				}
				if (ant) {
					lores.add(I18n.instance.Ant_Man_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
				} else {
					lores.add(I18n.instance.Ant_Man_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
				}
				if (aether) {
					lores.add(I18n.instance.Aether_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
				} else {
					lores.add(I18n.instance.Aether_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
				}
				if (lich) {
					lores.add(I18n.instance.LichTower + " : " + ChatColor.RED + I18n.instance.Enable);
				} else {
					lores.add(I18n.instance.LichTower + " : " + ChatColor.GRAY + I18n.instance.Disable);
				}
				if (castle) {
					lores.add(I18n.instance.Castle_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
				} else {
					lores.add(I18n.instance.Castle_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
				}
			}
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(0, is);
		}

		{
			ItemStack is = Skull.ROGUELIKE.getItem(); /* new ItemStack(Material.DIAMOND_BLOCK); */
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Roguelike_Dungeon);
			List<String> lores = new ArrayList<>();
			if (roguelike) {
				lores.add(I18n.instance.Roguelike_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
			} else {
				lores.add(I18n.instance.Roguelike_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
			}
			List<String> wraps = Wrap.wordWrap(I18n.instance.Roguelike_Description, WRAP);
			for (String str : wraps)
				lores.add(DESCRIPTION_COLOR + str);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 0, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Doomlike_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 1, is);
		} else {
			ItemStack is = Skull.DOOMLIKE.getItem(); /* new ItemStack(Material.GOLD_BLOCK); */
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Doomlike_Dungeon);
			List<String> lores = new ArrayList<>();
			if (doomlike) {
				lores.add(I18n.instance.Doomlike_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
			} else {
				lores.add(I18n.instance.Doomlike_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
			}
			List<String> wraps = Wrap.wordWrap(I18n.instance.Doomlike_Description, WRAP);
			for (String str : wraps)
				lores.add(DESCRIPTION_COLOR + str);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 1, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Battle_Tower);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 2, is);
		} else {
			ItemStack is = Skull.BATTLE.getItem(); /* new ItemStack(Material.IRON_BLOCK); */
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Battle_Tower);
			List<String> lores = new ArrayList<>();
			if (battletower) {
				lores.add(I18n.instance.Battle_Tower + " : " + ChatColor.RED + I18n.instance.Enable);
			} else {
				lores.add(I18n.instance.Battle_Tower + " : " + ChatColor.GRAY + I18n.instance.Disable);
			}
			List<String> wraps = Wrap.wordWrap(I18n.instance.BattleTower_Description, WRAP);
			for (String str : wraps)
				lores.add(DESCRIPTION_COLOR + str);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 2, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Smoofy_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 3, is);
		} else {
			ItemStack is = Skull.SMOOFY.getItem(); /* new ItemStack(Material.COBBLESTONE); */
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Smoofy_Dungeon);
			List<String> lores = new ArrayList<>();
			if (smoofy) {
				lores.add(I18n.instance.Smoofy_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
			} else {
				lores.add(I18n.instance.Smoofy_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
			}
			List<String> wraps = Wrap.wordWrap(I18n.instance.Smoofy_Description, WRAP);
			for (String str : wraps)
				lores.add(DESCRIPTION_COLOR + str);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 3, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Draylar_Battle_Tower);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 4, is);
		} else {
			ItemStack is = Skull.DRAYLAR.getItem(); /* new ItemStack(Material.BOOKSHELF); */
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Draylar_Battle_Tower);
			List<String> lores = new ArrayList<>();
			if (draylar) {
				lores.add(I18n.instance.Draylar_Battle_Tower + " : " + ChatColor.RED + I18n.instance.Enable);
			} else {
				lores.add(I18n.instance.Draylar_Battle_Tower + " : " + ChatColor.GRAY + I18n.instance.Disable);
			}
			List<String> wraps = Wrap.wordWrap(I18n.instance.DraylarBattleTower_Description, WRAP);
			for (String str : wraps)
				lores.add(DESCRIPTION_COLOR + str);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 4, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Ant_Man_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 5, is);
		} else {
			ItemStack is = Skull.ANTMAN.getItem();
			/* new ItemStack(Material.END_PORTAL_FRAME) */;
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Ant_Man_Dungeon);
			List<String> lores = new ArrayList<>();
			if (ant) {
				lores.add(I18n.instance.Ant_Man_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
			} else {
				lores.add(I18n.instance.Ant_Man_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
			}
			List<String> wraps = Wrap.wordWrap(I18n.instance.Antman_Description, WRAP);
			for (String str : wraps)
				lores.add(DESCRIPTION_COLOR + str);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 5, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Aether_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 6, is);
		} else {
			ItemStack is = Skull.AETHER.getItem(); /* new ItemStack(Material.GLOWSTONE); */
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Aether_Dungeon);
			List<String> lores = new ArrayList<>();
			if (aether) {
				lores.add(I18n.instance.Aether_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
			} else {
				lores.add(I18n.instance.Aether_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
			}
			List<String> wraps = Wrap.wordWrap(I18n.instance.Aether_Description, WRAP);
			for (String str : wraps)
				lores.add(DESCRIPTION_COLOR + str);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 6, is);
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.LichTower);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 7, is);
		} else {
			ItemStack is = Skull.LICH.getItem(); /* new ItemStack(Material.PAINTING); */
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.LichTower);
			List<String> lores = new ArrayList<>();
			if (lich) {
				lores.add(I18n.instance.LichTower + " : " + ChatColor.RED + I18n.instance.Enable);
			} else {
				lores.add(I18n.instance.LichTower + " : " + ChatColor.GRAY + I18n.instance.Disable);
			}
			List<String> wraps = Wrap.wordWrap(I18n.instance.Lich_Description, WRAP);
			for (String str : wraps)
				lores.add(DESCRIPTION_COLOR + str);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(1, 7, is);
		}

		if (!world.equals(WorldDefine.WORLD_NAME)) {
			if (world.equals(DungeonWorldManager.WORLD_NAME)) {
				ItemStack is = new ItemStack(Material.BARRIER);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Custom_Dungeon);
				List<String> lores = new ArrayList<>();
				lores.add(I18n.instance.PPDI_WORLD_LORE);
				im.setLore(lores);
				is.setItemMeta(im);

				addItem(1, 8, is);
			} else {
				ItemStack is = (WorldEdit.isReady() ? Skull.CUSTOM.getItem() : new ItemStack(Material.BARRIER));
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(I18n.instance.Custom_Dungeon);
				List<String> lores = new ArrayList<>();
				lores.add(I18n.instance.Require_WorldEdit);
				im.setLore(lores);
				is.setItemMeta(im);

				addItem(1, 8, is);
			}
		}

		if (world.equals(DungeonWorldManager.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Castle_Dungeon);
			List<String> lores = new ArrayList<>();
			lores.add(I18n.instance.PPDI_WORLD_LORE);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(2, 0, is);
		} else {
			ItemStack is = Skull.CASTLE.getItem(); /* new ItemStack(Material.IRON_TRAPDOOR); */
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Castle_Dungeon);
			List<String> lores = new ArrayList<>();
			if (castle) {
				lores.add(I18n.instance.Castle_Dungeon + " : " + ChatColor.RED + I18n.instance.Enable);
			} else {
				lores.add(I18n.instance.Castle_Dungeon + " : " + ChatColor.GRAY + I18n.instance.Disable);
			}
			List<String> wraps = Wrap.wordWrap(I18n.instance.Castle_Description, WRAP);
			for (String str : wraps)
				lores.add(DESCRIPTION_COLOR + str);
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(2, 0, is);
		}

		{
			ItemStack is = new ItemStack(Material.ACTIVATOR_RAIL);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Dungeon_Spawn_Setting);

			List<String> lores = new ArrayList<>();
			for (String line : Wrap.wordWrap(I18n.instance.Dungeon_Spawn_Setting_Lore, 30)) {
				lores.add(line);
			}
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(3, 0, is);
		}

		{
			ItemStack is = new ItemStack(Material.COMMAND_BLOCK);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.World_Spawner_Manager);

			List<String> lores = new ArrayList<>();
			for (String line : Wrap.wordWrap(I18n.instance.World_Spawner_Manager_Lore, 30)) {
				lores.add(line);
			}
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(3, 1, is);
		}

		{
			ItemStack is = new ItemStack(Material.WITHER_SKELETON_SKULL);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.Boss_Config);

			List<String> lores = new ArrayList<>();
			for (String line : Wrap.wordWrap(I18n.instance.Boss_Cfg_Lore, 30)) {
				lores.add(line);
			}
			im.setLore(lores);

			is.setItemMeta(im);

			addItem(3, 2, is);
		}

		if (!world.equals(WorldDefine.WORLD_NAME)) {
			ItemStack is = new ItemStack(Material.CARTOGRAPHY_TABLE);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.instance.WorldGeneratorCompatibility);
			is.setItemMeta(im);

			List<String> lores = new ArrayList<>();
			for (String line : Wrap.wordWrap(I18n.instance.WorldGeneratorCompatibility_Lore1, 30)) {
				lores.add(line);
			}
			for (String line : Wrap.wordWrap(I18n.instance.WorldGeneratorCompatibility_Lore2, 30)) {
				lores.add(line);
			}
			for (String line : Wrap.wordWrap(I18n.instance.WorldGeneratorCompatibility_Lore3, 30)) {
				lores.add(ChatColor.RED + line);
			}
			for (String line : Wrap.wordWrap(I18n.instance.WorldGeneratorCompatibility_Lore4, 30)) {
				lores.add(line);
			}
			im.setLore(lores);

			addItem(3, 3, is);
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
