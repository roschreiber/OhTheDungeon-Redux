/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.listener;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.addon.com.ohthedungeon.storydungeon.database.AsyncUtil;
import otd.addon.com.ohthedungeon.storydungeon.item.Book;
import otd.addon.com.ohthedungeon.storydungeon.util.I18n;
import otd.addon.com.ohthedungeon.storydungeon.world.ZoneConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import otd.Main;

/**
 *
 * @author shadow_wind
 */
public class EventManager implements Listener {
    
//    @EventHandler
//    public void onPlayerJoin_Updater(PlayerJoinEvent event) {
//        Player p = event.getPlayer();
//        if(p.hasPermission("perplayerdungeoninstance.admin")) {
//            p.sendMessage(ChatColor.RED + "[OP ONLY] You are using PerPlayerDungeonInstance Lite Version");
//            p.sendMessage(ChatColor.RED + "Upload to Pro version to remove this message");
//            p.sendMessage(ChatColor.RED + "https://www.spigotmc.org/resources/perplayerdungeoninstance-pro.77798/");
//        }
//    }
//
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.isSneaking()) return;
        
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if(block == null) return;
            if(block.getType() != Material.LECTERN) return;
            
            InventoryHolder invh = (InventoryHolder) block.getState();
            ItemStack is = invh.getInventory().getItem(0);
            
            if(!Book.isOtdBook(is)) return;
            event.setCancelled(true);
            
            if(!Book.isFinishedDungeon(is)) {
                int[] pos = Book.getDungeonPos(is);
//                Bukkit.getLogger().log(Level.SEVERE, pos[0] + "," + pos[1]);
                asyncCheckDatabase(pos[0], pos[1], player, is);
                return;
            }
            
            if(!Book.isDungeonLectern(block)) return;
            
            Book.teleportToDungeon(is, player);
        }
    }
    
    private static void asyncCheckDatabase(int cx, int cz, Player player, ItemStack is) {
        JavaPlugin plugin = Main.instance;
        if(PerPlayerDungeonInstance.getInstance().getAsyncTaskPool().hasTask(cx, cz)) {
            player.sendMessage(ChatColor.YELLOW + I18n.get("PendingDungeon"));
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String uuid = PerPlayerDungeonInstance.getInstance().getDatabase().getOwner(cx, cz);
            if(uuid == null) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.sendMessage(ChatColor.RED + I18n.get("InvalidDungeon"));
                }, 1L);
                return;
            }
            if(!uuid.equals(player.getUniqueId().toString())) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.sendMessage(ChatColor.RED + I18n.get("NotOwner"));
                }, 1L);
                return;
            }
            {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.sendMessage(ChatColor.RED + I18n.get("BreakDungeon"));
                    AsyncUtil.asyncAddDungeon(player, cx, cz);
                    
                    ZoneConfig zc = Book.getZoneConfig(is);
                    PerPlayerDungeonInstance.getInstance().getFakeGenerator().generateZone(
                        PerPlayerDungeonInstance.getInstance().getWorld(), cx, cz, zc);
                    player.sendMessage(ChatColor.BLUE + zc.toString());
                }, 1L);
            }
        });
    }
}
