package otd.nms.v1_15_R1;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.EquipHands;

public class EquipHands115R1 implements EquipHands {
	public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
		net.minecraft.server.v1_15_R1.NBTTagList hands = new net.minecraft.server.v1_15_R1.NBTTagList();
		hands.add((net.minecraft.server.v1_15_R1.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.server.v1_15_R1.NBTBase) sp.getItem(offhand));
		((net.minecraft.server.v1_15_R1.NBTTagCompound) mob).set("HandItems", hands);
		return mob;
	}
}
