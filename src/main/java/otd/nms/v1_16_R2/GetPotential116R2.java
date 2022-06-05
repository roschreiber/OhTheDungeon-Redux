package otd.nms.v1_16_R2;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.GetPotential;

public class GetPotential116R2 implements GetPotential {
	public Object get(Object mob, SpawnPotential sp) {
		net.minecraft.server.v1_16_R2.NBTTagCompound potential = new net.minecraft.server.v1_16_R2.NBTTagCompound();
		potential.set("Entity", (net.minecraft.server.v1_16_R2.NBTBase) mob);
		potential.setInt("Weight", sp.weight);
		return potential;
	}
}
