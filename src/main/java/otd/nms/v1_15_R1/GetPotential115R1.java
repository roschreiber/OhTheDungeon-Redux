package otd.nms.v1_15_R1;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.GetPotential;

public class GetPotential115R1 implements GetPotential {
	public Object get(Object mob, SpawnPotential sp) {
		net.minecraft.server.v1_15_R1.NBTTagCompound potential = new net.minecraft.server.v1_15_R1.NBTTagCompound();
		potential.set("Entity", (net.minecraft.server.v1_15_R1.NBTBase) mob);
		potential.setInt("Weight", sp.weight);
		return potential;
	}
}
