package otd.script.utils;

import org.bukkit.entity.Player;

import otd.addon.com.ohthedungeon.storydungeon.PerPlayerDungeonInstance;

public class VaultUtils {
	public double getBalance(Player p) {
		return PerPlayerDungeonInstance.getInstance().getVault().getBalance(p);
	}

	public double withDraw(Player p, double d) {
		return PerPlayerDungeonInstance.getInstance().getVault().withDraw(p, d);
	}

	public double addBalance(Player p, double d) {
		return PerPlayerDungeonInstance.getInstance().getVault().addBalance(p, d);
	}
}
