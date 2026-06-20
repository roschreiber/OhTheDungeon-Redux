package otd.nms;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public class GetNBTTagCompound {
	public Object get(int level, String name, Object inbt, SpawnPotential sp) {
		ReadWriteNBT nbt = NBT.createNBTObject();
		if (inbt != null) {
			// copy the supplied compound so we don't mutate the shared template
			nbt.mergeCompound((ReadWriteNBT) inbt);
		}
		return sp.getPotential(sp.getRoguelike(level, name, nbt));
	}
}
