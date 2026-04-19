package otd.nms;

import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;

public class SpawnerLightRule {

	private static final String spawnerNBT =
			"{custom_spawn_rules:{sky_light_limit:{min_inclusive:0,max_inclusive:15},block_light_limit:{min_inclusive:0,max_inclusive:15}}}";

	public void update(Block tileentity, JavaPlugin plugin) {
		if (!(tileentity.getState() instanceof CreatureSpawner)) {
			return;
		}

		NBTTileEntity nbt = new NBTTileEntity(tileentity.getState());

		nbt.mergeCompound(new NBTContainer("{SpawnData:" + spawnerNBT + "}"));

		// Handle Spawner Spawn Potentials
		if (nbt.hasTag("SpawnPotentials")) {
			NBTCompoundList spawnPotentials = nbt.getCompoundList("SpawnPotentials");
			for (int i = 0; i < spawnPotentials.size(); i++) {
				NBTCompound potential = spawnPotentials.get(i);
				potential.mergeCompound(new NBTContainer("{data:" + spawnerNBT + "}"));
			}
		}
	}
}
