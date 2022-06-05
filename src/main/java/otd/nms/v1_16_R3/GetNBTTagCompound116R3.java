package otd.nms.v1_16_R3;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.GetNBTTagCompound;

public class GetNBTTagCompound116R3 implements GetNBTTagCompound {
	public Object get(int level, String name, Object inbt, SpawnPotential sp) {
		Object nbt;
		if (inbt == null) {
			nbt = new net.minecraft.server.v1_16_R3.NBTTagCompound();
		} else {
			nbt = ((net.minecraft.server.v1_16_R3.NBTTagCompound) inbt).clone();
		}
		return sp.getPotential(sp.getRoguelike(level, name, nbt));
	}
}
