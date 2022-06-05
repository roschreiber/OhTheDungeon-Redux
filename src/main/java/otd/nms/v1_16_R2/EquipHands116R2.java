package otd.nms.v1_16_R2;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.EquipHands;

public class EquipHands116R2 implements EquipHands {
	public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
		net.minecraft.server.v1_16_R2.NBTTagList hands = new net.minecraft.server.v1_16_R2.NBTTagList();
		hands.add((net.minecraft.server.v1_16_R2.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.server.v1_16_R2.NBTBase) sp.getItem(offhand));
		((net.minecraft.server.v1_16_R2.NBTTagCompound) mob).set("HandItems", hands);
		return mob;
	}
}
