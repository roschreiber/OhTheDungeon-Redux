package otd.nms;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public class EquipHands {
	public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
		net.minecraft.nbt.CompoundTag nbt = (net.minecraft.nbt.CompoundTag) mob;
		net.minecraft.nbt.CompoundTag equipment;
		if (nbt.contains("equipment")) {
			equipment = nbt.getCompound("equipment").orElseGet(net.minecraft.nbt.CompoundTag::new);
		} else {
			equipment = new net.minecraft.nbt.CompoundTag();
		}
		
		if (weapon != null) {
			equipment.put("mainhand", (net.minecraft.nbt.Tag) sp.getItem(weapon));
		}
		if (offhand != null) {
			equipment.put("offhand", (net.minecraft.nbt.Tag) sp.getItem(offhand));
		}
		nbt.put("equipment", equipment);
		return nbt;
	}
}
