package otd.nms.v1_20_R4;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.EquipHands;

public class EquipHands120R4 implements EquipHands {
	public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
		net.minecraft.nbt.ListTag hands = new net.minecraft.nbt.ListTag();
		hands.add((net.minecraft.nbt.Tag) sp.getItem(weapon));
		hands.add((net.minecraft.nbt.Tag) sp.getItem(offhand));
		net.minecraft.nbt.CompoundTag nbt = (net.minecraft.nbt.CompoundTag) mob;
		nbt.put("HandItems", hands);
		return nbt;
	}
}
