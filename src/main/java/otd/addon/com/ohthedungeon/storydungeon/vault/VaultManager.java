/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.vault;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

/**
 *
 * @author shadow_wind
 */
public class VaultManager {
	private static Economy econ = null;

	public boolean setupEconomy() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public double getBalance(Player p) {
		if (econ == null)
			return -1d;
		return econ.getBalance(p);
	}

	public double withDraw(Player p, double d) {
		if (econ == null)
			return -1d;
		econ.withdrawPlayer(p, d);
		return getBalance(p);
	}

	public double addBalance(Player p, double d) {
		if (econ == null)
			return -1d;
		econ.depositPlayer(p, d);
		return getBalance(p);
	}
}
