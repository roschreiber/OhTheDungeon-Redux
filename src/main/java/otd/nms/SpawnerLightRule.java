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

		// Handle SpawnData
		NBTCompound spawnData = nbt.getCompound("SpawnData");
		if (spawnData == null) {
			spawnData = nbt.addCompound("SpawnData");
		}
		NBTCompound customSpawnRules = spawnData.getCompound("custom_spawn_rules");
		if (customSpawnRules == null) {
			customSpawnRules = spawnData.addCompound("custom_spawn_rules");
		}
		NBTCompound skyLightLimit = customSpawnRules.getCompound("sky_light_limit");
		if (skyLightLimit == null) {
			skyLightLimit = customSpawnRules.addCompound("sky_light_limit");
		}
		skyLightLimit.setInteger("min_inclusive", 0);
		skyLightLimit.setInteger("max_inclusive", 15);
		NBTCompound blockLightLimit = customSpawnRules.getCompound("block_light_limit");
		if (blockLightLimit == null) {
			blockLightLimit = customSpawnRules.addCompound("block_light_limit");
		}
		blockLightLimit.setInteger("min_inclusive", 0);
		blockLightLimit.setInteger("max_inclusive", 15);

		// Handle Spawner Spawn Potentials
		if (nbt.hasTag("SpawnPotentials")) {
			NBTCompoundList spawnPotentials = nbt.getCompoundList("SpawnPotentials");
			for (int i = 0; i < spawnPotentials.size(); i++) {
				NBTCompound potential = spawnPotentials.get(i);
				NBTCompound data = potential.getCompound("data");
				if (data == null) {
					data = potential.addCompound("data");
				}
				customSpawnRules = data.getCompound("custom_spawn_rules");
				if (customSpawnRules == null) {
					customSpawnRules = data.addCompound("custom_spawn_rules");
				}
				skyLightLimit = customSpawnRules.getCompound("sky_light_limit");
				if (skyLightLimit == null) {
					skyLightLimit = customSpawnRules.addCompound("sky_light_limit");
				}
				skyLightLimit.setInteger("min_inclusive", 0);
				skyLightLimit.setInteger("max_inclusive", 15);
				blockLightLimit = customSpawnRules.getCompound("block_light_limit");
				if (blockLightLimit == null) {
					blockLightLimit = customSpawnRules.addCompound("block_light_limit");
				}
				blockLightLimit.setInteger("min_inclusive", 0);
				blockLightLimit.setInteger("max_inclusive", 15);
			}
		}
	}
}
