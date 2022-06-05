package otd.script.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;

import otd.Main;
import otd.lib.spawner.SpawnerDecryAPI;

public class BlockUtils {
	public static void placeBlock(Location loc, Material type) {
		loc.getBlock().setType(type);
	}

	public static void placeSpawner(Location loc, String mob) {
		World w = loc.getWorld();
		Block block = w.getBlockAt(loc);
		block.setType(Material.SPAWNER);

		CreatureSpawner tileentitymobspawner = ((CreatureSpawner) block.getState());
		tileentitymobspawner.setSpawnedType(EntityType.valueOf(mob.toUpperCase()));
		tileentitymobspawner.update();
	}

	public final static NamespacedKey KEY = new NamespacedKey(Main.instance, "script");
	public final static NamespacedKey ASYNC = new NamespacedKey(Main.instance, "use_async_script");

	public static void placeScriptSpawner(Location loc, String script_file) {
		World w = loc.getWorld();
		Block block = w.getBlockAt(loc);
		block.setType(Material.SPAWNER);

		CreatureSpawner tileentitymobspawner = ((CreatureSpawner) block.getState());
		tileentitymobspawner.setSpawnedType(EntityType.BLAZE);
		tileentitymobspawner.update();

		tileentitymobspawner.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, script_file);
		tileentitymobspawner.update(true, false);

		SpawnerDecryAPI.updateSpawnerLightRule(block, Main.instance);
	}
}
