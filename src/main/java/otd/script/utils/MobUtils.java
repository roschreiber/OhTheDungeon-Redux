package otd.script.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import otd.integration.MythicMobsImpl;

public class MobUtils {
	public static Entity spawnCustomMythicMobs(Location loc, String name) {
		return MythicMobsImpl.spawnCustom(loc, name);
	}
}
