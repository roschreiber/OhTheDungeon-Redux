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
package otd.lib.spawner;

import java.util.SplittableRandom;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import otd.Main;
import otd.MultiVersion;
import otd.api.SpawnerManager;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.util.PluginKeys;
import otd.world.DungeonType;

/**
 *
 * @author
 */
public class SpawnerDecryAPI {

	public static SplittableRandom random = new SplittableRandom();

	public static boolean hasLightRuleUpdate(TileState ts, JavaPlugin plugin) {
		if (otd == null) {
			otd = new NamespacedKey(plugin, "ohthedungeon_" + Main.version.toString());
			lightrule = new NamespacedKey(plugin, PluginKeys.lightrule);
		}
		{
			if (!SpawnerManager.instance().isOtdSpawner((CreatureSpawner) ts))
				return true;
		}
		NamespacedKey key = lightrule;
		return ts.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
	}

	private static NamespacedKey otd = null;
	private static NamespacedKey lightrule = null;

	public static void setSpawnerDecry(Block block, JavaPlugin plugin, DungeonType type, boolean light_update) {
		if (otd == null) {
			otd = new NamespacedKey(plugin, "ohthedungeon_" + Main.version.toString());
			lightrule = new NamespacedKey(plugin, PluginKeys.lightrule);
		}

		World world = block.getWorld();
		String name = world.getName();

		double rate = 0;
		if (WorldConfig.wc.dict.containsKey(name)) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(name);
			rate = swc.spawner_rejection_rate;
		}

		if (random.nextDouble() < rate) {
			block.setType(Material.AIR);
			return;
		}

		if (!(block.getState() instanceof CreatureSpawner))
			return;

		if (light_update && MultiVersion.spawnerNeedLightUpdate()) {
			updateSpawnerLightRule(block, plugin);
		}

		CreatureSpawner ts = (CreatureSpawner) block.getState();
		SpawnerManager.instance().setOtdSpawnerTag(ts);
//		ts.getPersistentDataContainer().set(decry, PersistentDataType.BYTE, (byte) 15);
		ts.getPersistentDataContainer().set(otd, PersistentDataType.BYTE, (byte) 15);
		NamespacedKey key = new NamespacedKey(plugin, type.toString());
		ts.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 15);
		if (MultiVersion.spawnerNeedLightUpdate())
			ts.getPersistentDataContainer().set(lightrule, PersistentDataType.BYTE, (byte) 15);

		ts.update(true, false);
	}

	public static void updateSpawnerLightRule(Block tileentity, JavaPlugin plugin) {
		if (otd == null) {
			otd = new NamespacedKey(plugin, "ohthedungeon_" + Main.version.toString());
			lightrule = new NamespacedKey(plugin, PluginKeys.lightrule);
		}

//		V118R1SpawnerLightRule updater = new V118R1SpawnerLightRule();
//		updater.update(tileentity, plugin);
		if (MultiVersion.spawnerLightRule == null)
			return;
		MultiVersion.spawnerLightRule.update(tileentity, plugin);

		TileState ts = (TileState) tileentity.getState();
		ts.getPersistentDataContainer().set(lightrule, PersistentDataType.BYTE, (byte) 15);
		ts.update(true, false);
	}

}
