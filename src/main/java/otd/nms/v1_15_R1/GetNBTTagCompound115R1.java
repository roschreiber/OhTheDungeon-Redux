package otd.nms.v1_15_R1;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.GetNBTTagCompound;

public class GetNBTTagCompound115R1 implements GetNBTTagCompound {
	public Object get(int level, String name, Object inbt, SpawnPotential sp) {
		Object nbt;
		if (inbt == null) {
			nbt = new net.minecraft.server.v1_15_R1.NBTTagCompound();
		} else {
			nbt = ((net.minecraft.server.v1_15_R1.NBTTagCompound) inbt).clone();
		}
		return sp.getPotential(sp.getRoguelike(level, name, nbt));
	}
}
