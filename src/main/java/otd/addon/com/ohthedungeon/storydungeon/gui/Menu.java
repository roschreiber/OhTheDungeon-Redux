/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.gui;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.addon.com.ohthedungeon.storydungeon.config.DungeonConfig;
import otd.addon.com.ohthedungeon.storydungeon.database.AsyncUtil;
import otd.addon.com.ohthedungeon.storydungeon.generator.BaseGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.FakeGenerator;
import otd.addon.com.ohthedungeon.storydungeon.item.Book;
import otd.addon.com.ohthedungeon.storydungeon.util.I18n;
import otd.addon.com.ohthedungeon.storydungeon.util.ZoneUtils;
import otd.addon.com.ohthedungeon.storydungeon.world.ZoneConfig;
import otd.addon.com.ohthedungeon.storydungeon.world.ZoneDungeonType;
import forge_sandbox.greymerk.roguelike.dungeon.settings.DungeonSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Menu extends Content {
	private final static int SLOT = 6 * 9;
	private String generator;
	private String populator;
	private String tower;
	private final Map<Integer, String> slots;
	private final Map<Integer, Type> types;

	private static enum Type {
		GENERATOR, POPULATOR, TOWER, EMPTY
	}

	private static void send_create(Player p) {
		p.sendMessage(ChatColor.YELLOW + I18n.get("Creating"));
	}

	public static void registerEvent(JavaPlugin plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(new Menu(), plugin);
	}

	public Menu() {
		super(I18n.get("Private_Dungeon_Menu"), SLOT);
		generator = "Nordic";
		populator = "Oak";
		tower = "Forest";

		slots = new HashMap<>();
		types = new HashMap<>();
	}

	private final static Material TERRAIN = Material.GRASS_BLOCK;
	private final static Material DECORATION = Material.SUNFLOWER;
	private final static Material TOWER = Material.MAP;

	private static boolean playerHasEmptySlot(Player p) {
		return p.getInventory().firstEmpty() != -1;
	}

	@EventHandler
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof Menu)) {
			return;
		}
		e.setCancelled(true);
		if (e.getClick().equals(ClickType.NUMBER_KEY)) {
			return;
		}

		int slot = e.getRawSlot();
		Player p = (Player) e.getWhoClicked();

		Menu holder = (Menu) e.getInventory().getHolder();
		if (holder == null)
			return;

		if (slot == 52) {
			// Money
			if (!DungeonConfig.getEnableMoneyPayment()) {
				holder.init();
				return;
			}
			if (!playerHasEmptySlot(p)) {
				p.sendMessage(ChatColor.RED + I18n.get("NoEmpty"));
				return;
			}

			double amount = DungeonConfig.getMoney();
			double player_amount = PerPlayerDungeonInstance.getInstance().getVault().getBalance(p);
			if (player_amount < amount) {
				p.sendMessage(ChatColor.YELLOW + I18n.get("NotEnoughMoney"));
				return;
			}

			PerPlayerDungeonInstance.getInstance().getVault().withDraw(p, amount);
			p.closeInventory();
			createNewDungeon(p, holder.generator, holder.populator, holder.tower);
			return;
		}
		if (slot == 53) {
			// Exp
			if (!DungeonConfig.getEnableLevelPayment()) {
				holder.init();
				return;
			}
			if (!playerHasEmptySlot(p)) {
				p.sendMessage(ChatColor.RED + I18n.get("NoEmpty"));
				return;
			}

			int level = DungeonConfig.getLevel();
			int player_level = p.getLevel();

			if (player_level < level) {
				p.sendMessage(ChatColor.YELLOW + I18n.get("NotEnoughLevel"));
				return;
			}

			p.setLevel(player_level - level);
			p.closeInventory();
			createNewDungeon(p, holder.generator, holder.populator, holder.tower);
			return;
		}

		Type type = holder.getType(slot);
		String str = holder.getString(slot);

		if (type == Type.EMPTY)
			return;
		if (type == Type.GENERATOR)
			holder.generator = str;
		if (type == Type.POPULATOR)
			holder.populator = str;
		if (type == Type.TOWER)
			holder.tower = str;

		holder.init();
	}

	public static ItemStack createNewDungeon_Cmd(Player player, String g, String p, String t) {
		int zone[] = ZoneUtils.getNextZone();

//        if(Math.abs(zone[0]) > 48 || Math.abs(zone[1]) > 48) {
//            player.sendMessage(ChatColor.RED + "For lite version, there could only be at most 10 dungeon instances in this server");
//            return;
//        }

		ZoneUtils.saveZonePos();
		AsyncUtil.asyncAddDungeon(player, zone[0], zone[1]);
		ZoneConfig zc = new ZoneConfig(Biome.SUNFLOWER_PLAINS, g, p, ZoneDungeonType.ROGUELIKE, t, "shadow");
		PerPlayerDungeonInstance.getInstance().getFakeGenerator()
				.generateZone(PerPlayerDungeonInstance.getInstance().getWorld(), zone[0], zone[1], zc);
		send_create(player);
		return Book.get(I18n.get("DungeonBook"), zone[0], zone[1], player.getName(), I18n.get(g), I18n.get(p),
				I18n.get(t), zc);
	}

	private static void createNewDungeon(Player player, String g, String p, String t) {
		int zone[] = ZoneUtils.getNextZone();

//        if(Math.abs(zone[0]) > 48 || Math.abs(zone[1]) > 48) {
//            player.sendMessage(ChatColor.RED + "For lite version, there could only be at most 10 dungeon instances in this server");
//            return;
//        }

		ZoneUtils.saveZonePos();
		AsyncUtil.asyncAddDungeon(player, zone[0], zone[1]);
		ZoneConfig zc = new ZoneConfig(Biome.SUNFLOWER_PLAINS, g, p, ZoneDungeonType.ROGUELIKE, t, "shadow");
		PerPlayerDungeonInstance.getInstance().getFakeGenerator()
				.generateZone(PerPlayerDungeonInstance.getInstance().getWorld(), zone[0], zone[1], zc);
		send_create(player);
		player.getInventory().addItem(Book.get(I18n.get("DungeonBook"), zone[0], zone[1], player.getName(), I18n.get(g),
				I18n.get(p), I18n.get(t), zc));
	}

	private Type getType(int slot) {
		if (!types.containsKey(slot))
			return Type.EMPTY;
		return types.get(slot);
	}

	private String getString(int slot) {
		if (!slots.containsKey(slot))
			return "";
		return slots.get(slot);
	}

	@SuppressWarnings("deprecation")
	private void addItem(Material enabled, int[] slot, List<String> list, Type type, String choose, ItemStack sign) {
		{
			addItem(slot[0], sign);
			types.put(slot[0], Type.EMPTY);
			slot[0]++;
		}
		for (String str : list) {
			Material material = Material.BARRIER;
			if (str.equals(choose)) {
				material = enabled;
			}

			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.get(str));
			is.setItemMeta(im);

			addItem(slot[0], is);

			slots.put(slot[0], str);
			types.put(slot[0], type);

			slot[0]++;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void init() {
		slots.clear();
		types.clear();
//        int slot;
		int[] slot = { 0 };
		List<String> generators = new ArrayList<>();
		for (Map.Entry<String, BaseGenerator> entry : FakeGenerator.getGenerators().entrySet()) {
			generators.add(entry.getKey());
		}
		List<String> populators = new ArrayList<>();
		for (Map.Entry<String, BlockPopulator> entry : FakeGenerator.getPopulators().entrySet()) {
			populators.add(entry.getKey());
		}
		List<String> towers = new ArrayList<>();
		for (Map.Entry<String, DungeonSettings> entry : FakeGenerator.getTowers().entrySet()) {
			towers.add(entry.getKey());
		}

		slot[0] = 0;
		ItemStack terrain = new ItemStack(Material.OAK_SIGN);
		{
			ItemMeta im = terrain.getItemMeta();
			im.setDisplayName(I18n.get("Terrain"));
			List<String> lores = new ArrayList<>();
			lores.add(I18n.get("TerrainLore"));
			im.setLore(lores);
			terrain.setItemMeta(im);
		}
		addItem(TERRAIN, slot, generators, Type.GENERATOR, generator, terrain);
		slot[0] = 18;
		ItemStack decoration = new ItemStack(Material.OAK_SIGN);
		{
			ItemMeta im = decoration.getItemMeta();
			im.setDisplayName(I18n.get("Decoration"));
			List<String> lores = new ArrayList<>();
			lores.add(I18n.get("DecorationLore"));
			im.setLore(lores);
			decoration.setItemMeta(im);
		}
		addItem(DECORATION, slot, populators, Type.POPULATOR, populator, decoration);
		slot[0] = 27;
		ItemStack tow = new ItemStack(Material.OAK_SIGN);
		{
			ItemMeta im = tow.getItemMeta();
			im.setDisplayName(I18n.get("Tower"));
			List<String> lores = new ArrayList<>();
			lores.add(I18n.get("TowerLore"));
			im.setLore(lores);
			tow.setItemMeta(im);
		}
		addItem(TOWER, slot, towers, Type.TOWER, tower, tow);

		{
			Material material;
			String lore;
			if (!DungeonConfig.getEnableMoneyPayment()) {
				material = Material.BARRIER;
				lore = I18n.get("DISABLE_ADMIN");
			} else {
				material = Material.CHEST;
				lore = I18n.get("PAY_MONEY") + DungeonConfig.getMoney();
			}
			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.get("MONEY"));
			List<String> lores = new ArrayList<>();
			lores.add(lore);
			lores.add("");
			lores.add(ChatColor.YELLOW + I18n.get("CLICK"));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(52, is);
		}
		{
			Material material;
			String lore;
			if (!DungeonConfig.getEnableLevelPayment()) {
				material = Material.BARRIER;
				lore = I18n.get("DISABLE_ADMIN");
			} else {
				material = Material.EXPERIENCE_BOTTLE;
				lore = I18n.get("PAY_LEVEL") + DungeonConfig.getLevel();
			}
			ItemStack is = new ItemStack(material);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(I18n.get("LEVEL"));
			List<String> lores = new ArrayList<>();
			lores.add(lore);
			lores.add("");
			lores.add(ChatColor.YELLOW + I18n.get("CLICK"));
			im.setLore(lores);
			is.setItemMeta(im);

			addItem(53, is);
		}
	}
}
