package otd.nms;

import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public class EquipHands {
	public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
		ReadWriteNBT nbt = (ReadWriteNBT) mob;
		ReadWriteNBT equipment = nbt.getOrCreateCompound("equipment");

		if (weapon != null) {
			equipment.getOrCreateCompound("mainhand").mergeCompound((ReadWriteNBT) sp.getItem(weapon));
		}
		if (offhand != null) {
			equipment.getOrCreateCompound("offhand").mergeCompound((ReadWriteNBT) sp.getItem(offhand));
		}
		return nbt;
	}
}
