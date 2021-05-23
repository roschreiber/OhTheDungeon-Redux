/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.async;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;
import otd.addon.com.ohthedungeon.storydungeon.database.AsyncUtil;
import otd.addon.com.ohthedungeon.storydungeon.util.I18n;
import otd.addon.com.ohthedungeon.storydungeon.world.ZoneConfig;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author shadow_wind
 */
public class AsyncTask_Finish implements AsyncTask {
    private final int cx;
    private final int cz;
    private final ZoneConfig zc;
    public AsyncTask_Finish(int cx, int cz, ZoneConfig zc) {
        this.cx = cx;
        this.cz = cz;
        this.zc = zc;
    }
    
    public int getX() {
        return cx;
    }
    
    public int getZ() {
        return cz;
    }
    
    @Override
    public boolean doTask(World world, Random random) {
        Chunk c = world.getChunkAt(cx, cz);
        c.getBlock(0, 0, 0).setType(Material.GLASS, false);
        PerPlayerDungeonInstance.getInstance().getAsyncTaskPool().kill(cx, cz);
//        AsyncUtil.asyncAddDungeon(zc.player, cx, cz);
        playSoundOnPlayer(zc.player);
        sendMsgToPlayer(zc.player);
//        Bukkit.getLogger().log(Level.INFO, "done");
        return true;
    }
    
    private static void playSoundOnPlayer(String playerName) {
        Player player = Bukkit.getServer().getPlayer(playerName);
        if(player == null) return;
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0);
    }
    
    private static void sendMsgToPlayer(String playerName) {
        Player player = Bukkit.getServer().getPlayer(playerName);
        if(player == null) return;
        player.sendMessage(I18n.get("DungeonCreated"));
    }
}
