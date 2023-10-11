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
package otd.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import forge_sandbox.greymerk.roguelike.treasure.loot.BookBase;
import net.md_5.bungee.api.ChatColor;
import otd.config.WorldConfig;
import otd.gui.dungeon_plot.UserTeleport;
import otd.util.Diagnostic;
import otd.util.I18n;
import otd.util.Roll;
import otd.world.WorldDefine;

/**
 *
 * @author
 */
public class MobListener implements Listener {

	public static Map<String, Long> roll_cool_down = new HashMap<>();
	public final static Set<Material> SPAWN_EGGS;
	static {
		SPAWN_EGGS = new HashSet<>();
		for (Material m : Material.values()) {
			String s = m.toString();
			if (s.contains("SPAWN_EGG")) {
				SPAWN_EGGS.add(m);
			}
		}
	}

	private static class BookNotice extends BookBase {
		public BookNotice() {
			super("Shadow_Wind", "Warning in your Spawner Setting");
			this.addPage(I18n.instance.Nerf_Msg);
		}

		@SuppressWarnings("deprecation")
		@Override
		public ItemStack get() {
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
			BookMeta bookMeta = (BookMeta) book.getItemMeta();
			String array_page[] = new String[pages.size()];
			for (int i = 0; i < array_page.length; i++) {
				array_page[i] = pages.get(i);
			}
			bookMeta.addPage(array_page);
			bookMeta.setAuthor(this.author == null ? "Anonymous" : this.author);
			bookMeta.setTitle(this.title == null ? "Book" : this.title);

			book.setItemMeta(bookMeta);
			return book;
		}
	}

	private final static BookNotice BOOK = new BookNotice();

	@EventHandler
	public void onPlayerJoin_Updater(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (p.hasPermission("oh_the_dungeons.admin")) {
			if (Diagnostic.isSpawnerNotReady()
					&& System.getProperty("IReallyKnowWhatIAmDoingForSpawnerISwear") == null) {
				p.openBook(BOOK.get());
			}
		}

		if (WorldConfig.wc.dungeon_world.finished) {
			if (p.hasPermission("oh_the_dungeons.teleport"))
				p.sendMessage(ChatColor.GREEN + I18n.instance.User_TP_Suggest);
		}

		if (p.hasPermission("perplayerdungeoninstance.menu")) {
			if (!I18n.instance.User_PI_Suggest.isEmpty())
				p.sendMessage(ChatColor.GREEN + I18n.instance.User_PI_Suggest);
		}
	}

	@EventHandler
	public void onPlayQuit(PlayerQuitEvent e) {
		roll_cool_down.remove(e.getPlayer().getName());
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerUse(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Inventory inv = p.getInventory();

		if (inv instanceof PlayerInventory) {
			boolean use = false;
			PlayerInventory pinv = (PlayerInventory) inv;
			if (!use) {
				ItemStack item = pinv.getItemInMainHand();
				ItemMeta im = item.getItemMeta();
				if (im != null && im.hasLore()) {
					List<String> lores = im.getLore();
					if (lores != null && lores.size() > 0) {
						if (lores.get(0).equals(Roll.DICE)) {
							use = true;
						}
					}
				}
			}
			if (!use) {
				ItemStack item = pinv.getItemInOffHand();
				ItemMeta im = item.getItemMeta();
				if (im != null && im.hasLore()) {
					List<String> lores = im.getLore();
					if (lores != null && lores.size() > 0) {
						if (lores.get(0).equals(Roll.DICE)) {
							use = true;
						}
					}
				}
			}
			if (use) {
				event.setCancelled(true);
				Roll.roll(p);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		Entity p = e.getEntity();
		if (((p instanceof Player)) && (p.getWorld().getName().equalsIgnoreCase(WorldDefine.WORLD_NAME))) {
			Player player = (Player) p;
			if (e.getCause() == DamageCause.VOID) {
				e.setCancelled(true);
				player.setFallDistance(0);
				UserTeleport.teleportBed(player);
			}
		}
	}
}
