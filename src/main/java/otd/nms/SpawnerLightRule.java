package otd.nms;

import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTTileEntity;

public class SpawnerLightRule {
	public void update(Block tileentity, JavaPlugin plugin) {
		if (!(tileentity.getState() instanceof CreatureSpawner)) {
			return;
		}

		NBTTileEntity nbt = new NBTTileEntity(tileentity.getState());

		if (nbt.hasTag("SpawnData")) {
			NBTCompound spawnData = nbt.getCompound("SpawnData");
			NBTCompound customSpawnRules = spawnData.addCompound("custom_spawn_rules");

			NBTCompound skyLightLimit = customSpawnRules.addCompound("sky_light_limit");
			skyLightLimit.setInteger("min_inclusive", 0);
			skyLightLimit.setInteger("max_inclusive", 15);

			NBTCompound blockLightLimit = customSpawnRules.addCompound("block_light_limit");
			blockLightLimit.setInteger("min_inclusive", 0);
			blockLightLimit.setInteger("max_inclusive", 15);
		}

		if (nbt.hasTag("SpawnPotentials")) {
			NBTCompoundList spawnPotentials = nbt.getCompoundList("SpawnPotentials");
			for (int i = 0; i < spawnPotentials.size(); i++) {
				NBTCompound potential = spawnPotentials.get(i);
				if (potential.hasTag("data")) {
					NBTCompound data = potential.getCompound("data");
					NBTCompound customSpawnRules = data.addCompound("custom_spawn_rules");

					NBTCompound skyLightLimit = customSpawnRules.addCompound("sky_light_limit");
					skyLightLimit.setInteger("min_inclusive", 0);
					skyLightLimit.setInteger("max_inclusive", 15);

					NBTCompound blockLightLimit = customSpawnRules.addCompound("block_light_limit");
					blockLightLimit.setInteger("min_inclusive", 0);
					blockLightLimit.setInteger("max_inclusive", 15);
				}
			}
		}
	}
}
