package otd.nms;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public class GetPotential {

	public Object get(Object mob, SpawnPotential sp) {
		return getInner(mob, sp);
	}

	private Object getInner(Object mob, SpawnPotential sp) {
		ReadWriteNBT potential = NBT.createNBTObject();
		potential.getOrCreateCompound("entity").mergeCompound((ReadWriteNBT) mob);

		ReadWriteNBT custom_spawn_rules = potential.getOrCreateCompound("custom_spawn_rules");
		ReadWriteNBT sky_light_limit = custom_spawn_rules.getOrCreateCompound("sky_light_limit");
		sky_light_limit.setInteger("min_inclusive", 0);
		sky_light_limit.setInteger("max_inclusive", 15);
		ReadWriteNBT block_light_limit = custom_spawn_rules.getOrCreateCompound("block_light_limit");
		block_light_limit.setInteger("min_inclusive", 0);
		block_light_limit.setInteger("max_inclusive", 15);
		return potential;
	}
}
