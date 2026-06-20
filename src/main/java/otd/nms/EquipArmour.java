package otd.nms;

import java.util.Random;

import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public class EquipArmour {
	public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
		ReadWriteNBT nbt = (ReadWriteNBT) mob;
		ReadWriteNBT equipment = nbt.getOrCreateCompound("equipment");

		equipment.getOrCreateCompound("feet").mergeCompound((ReadWriteNBT) sp
				.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		equipment.getOrCreateCompound("legs").mergeCompound((ReadWriteNBT) sp
				.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		equipment.getOrCreateCompound("chest").mergeCompound((ReadWriteNBT) sp
				.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		equipment.getOrCreateCompound("head").mergeCompound((ReadWriteNBT) sp
				.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		return nbt;
	}
}
