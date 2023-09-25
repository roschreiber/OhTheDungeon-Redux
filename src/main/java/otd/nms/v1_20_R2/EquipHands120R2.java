package otd.nms.v1_20_R1;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.EquipHands;

public class EquipHands120R1 implements EquipHands {
	public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
		net.minecraft.nbt.NBTTagList hands = new net.minecraft.nbt.NBTTagList();
		hands.add((net.minecraft.nbt.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.nbt.NBTBase) sp.getItem(offhand));
		net.minecraft.nbt.NBTTagCompound nbt = (net.minecraft.nbt.NBTTagCompound) mob;
		nbt.a("HandItems", hands);
		return nbt;
	}
}
