/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.item;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.addon.com.ohthedungeon.storydungeon.util.I18n;
import otd.addon.com.ohthedungeon.storydungeon.world.ZoneConfig;
import otd.addon.com.ohthedungeon.storydungeon.world.ZoneDungeonType;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LecternInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import otd.Main;

/**
 *
 * @author shadow_wind
 */
public class Book {
	public final static String ITEM_TAG = ChatColor.GRAY + "OTD_DUNGEON_BOOK";

	@SuppressWarnings("deprecation")
	public static ItemStack get(String title, int dx, int dy, String player, String generator, String populator,
			String tower, ZoneConfig zc) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		String array_page[] = { ChatColor.BLUE + I18n.get("Tip1") + "\n\n" + ChatColor.RED + I18n.get("Tip2"),
				ChatColor.RED + I18n.get("Tip3"), };
		bookMeta.addPage(array_page);
		bookMeta.setAuthor(player);
		bookMeta.setTitle(ChatColor.BLUE + "dx = " + dx + ", dy = " + dy);

		List<String> lores = new ArrayList<>();
		lores.add(ITEM_TAG);
		lores.add(dx + "," + dy);
		lores.add(ChatColor.BLUE + generator);
		lores.add(ChatColor.BLUE + populator);
		lores.add(ChatColor.BLUE + tower);
		lores.add(ChatColor.GRAY + zc.generator + "," + zc.populator + "," + zc.extra);
		bookMeta.setLore(lores);

		book.setItemMeta(bookMeta);

		return book;
	}

	@SuppressWarnings("deprecation")
	public static boolean isOtdBook(ItemStack is) {
		if (is == null)
			return false;
		Material type = is.getType();
		if (type != Material.WRITTEN_BOOK)
			return false;

		ItemMeta im = is.getItemMeta();
		if (im == null)
			return false;

		if (!im.hasLore())
			return false;
		List<String> lores = im.getLore();
		if (lores == null)
			return false;
		if (lores.isEmpty())
			return false;

		String lore = lores.get(0);
		return lore.equals(ITEM_TAG);
	}

	@SuppressWarnings("deprecation")
	public static int[] getDungeonPos(ItemStack is) {
		if (is == null)
			return new int[] { -1, -1 };
//        if(!isOtdBook(is)) return false;

		ItemMeta im = is.getItemMeta();
		if (im == null)
			return new int[] { -1, -1 };
		if (!im.hasLore())
			return new int[] { -1, -1 };
		List<String> lores = im.getLore();
		if (lores == null)
			return new int[] { -1, -1 };
		if (lores.size() < 2)
			return new int[] { -1, -1 };
		String lore = lores.get(1);

		String[] s_pos = lore.split(",");
		if (s_pos.length != 2)
			return new int[] { -1, -1 };
		try {
			int pos1 = Integer.parseInt(s_pos[0]);
			int pos2 = Integer.parseInt(s_pos[1]);

			return new int[] { pos1, pos2 };
		} catch (NumberFormatException ex) {
			return new int[] { -1, -1 };
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean isFinishedDungeon(ItemStack is) {
		if (is == null)
			return false;
//        if(!isOtdBook(is)) return false;

		ItemMeta im = is.getItemMeta();
		if (im == null)
			return false;
		if (!im.hasLore())
			return false;
		List<String> lores = im.getLore();
		if (lores == null)
			return false;
		if (lores.size() < 2)
			return false;
		String lore = lores.get(1);

		String[] s_pos = lore.split(",");
		if (s_pos.length != 2)
			return false;
		try {
			int pos1 = Integer.parseInt(s_pos[0]);
			int pos2 = Integer.parseInt(s_pos[1]);

			if (PerPlayerDungeonInstance.getInstance().getAsyncTaskPool().hasTask(pos1, pos2))
				return false;

			World w = PerPlayerDungeonInstance.getInstance().getWorld();
			if (!w.isChunkGenerated(pos1, pos2))
				return false;
			Chunk chunk = w.getChunkAt(pos1, pos2);
			return chunk.getBlock(0, 0, 0).getType() == Material.GLASS;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static ZoneConfig getZoneConfig(ItemStack is) {
		try {
			ItemMeta im = is.getItemMeta();
			BookMeta bm = (BookMeta) im;
			List<String> lores = im.getLore();
			String zc = lores.get(5);
			zc = ChatColor.stripColor(zc);
			String strs[] = zc.split(",");

			return new ZoneConfig(Biome.THE_VOID, strs[0], strs[1], ZoneDungeonType.ROGUELIKE, strs[2], bm.getAuthor());
		} catch (Exception ex) {
			return new ZoneConfig(Biome.THE_VOID, "Nordic", "Oak", ZoneDungeonType.ROGUELIKE, "Forest", "shadow");
		}
	}

	public static boolean isDungeonLectern(Block block) {
		if (block == null)
			return false;
		BlockState bs = block.getState();
		if (!(bs instanceof InventoryHolder))
			return false;

		InventoryHolder invh = (InventoryHolder) bs;
		Inventory inv = invh.getInventory();
		if (!(inv instanceof LecternInventory))
			return false;
		LecternInventory linv = (LecternInventory) inv;

		ItemStack is = linv.getItem(0);
		if (is == null)
			return false;
		return true;
//        return isFinishedDungeon(is);
	}

	@SuppressWarnings("deprecation")
	private static void sendTitle(Player p) {
		String str1 = ChatColor.RED + I18n.get("DungeonTitle");
		String str2 = ChatColor.GREEN + I18n.get("DungeonTitle2");
		p.sendTitle(str1, str2, 10, 70, 20);

		p.sendMessage(ChatColor.YELLOW + I18n.get("Back"));
	}

	@SuppressWarnings("deprecation")
	public static boolean teleportToDungeon(ItemStack is, Player p) {
		ItemMeta im = is.getItemMeta();
		if (im == null)
			return false;
		if (!im.hasLore())
			return false;
		List<String> lores = im.getLore();
		if (lores == null)
			return false;
		if (lores.size() < 2)
			return false;
		String lore = lores.get(1);

		String[] s_pos = lore.split(",");
		if (s_pos.length != 2)
			return false;
		try {
			int pos1 = Integer.parseInt(s_pos[0]);
			int pos2 = Integer.parseInt(s_pos[1]);

			World w = PerPlayerDungeonInstance.getInstance().getWorld();
			if (!w.isChunkGenerated(pos1, pos2))
				return false;
			w.getChunkAt(pos1, pos2);

			int y = w.getHighestBlockYAt(pos1 * 16, pos2 * 16);
			Location loc = new Location(w, pos1 * 16, y, pos2 * 16);
			p.teleport(loc);

			Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
				sendTitle(p);
			}, 20L);

			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
