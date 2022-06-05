package otd.nms.v1_17_R1;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.GetPotential;

public class GetPotential117R1 implements GetPotential {

	public Object get(Object mob, SpawnPotential sp) {
		Object obj = null;
		obj = getInner(mob, sp);
		return obj;
	}

	private Object getInner(Object mob, SpawnPotential sp) {
		net.minecraft.nbt.NBTTagCompound potential = new net.minecraft.nbt.NBTTagCompound();

		potential.set("Entity", (net.minecraft.nbt.NBTBase) mob);
		potential.setInt("Weight", sp.weight);

		return potential;
	}
}
