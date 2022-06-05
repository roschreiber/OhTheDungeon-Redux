package otd.nms.v1_18_R2;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.GetNBTTagCompound;

public class GetNBTTagCompound118R2 implements GetNBTTagCompound {
	public Object get(int level, String name, Object inbt, SpawnPotential sp) {
		Object nbt;
		if (inbt == null) {
			nbt = new net.minecraft.nbt.NBTTagCompound();
		} else {
			nbt = ((net.minecraft.nbt.NBTTagCompound) inbt).g();
		}
		return sp.getPotential(sp.getRoguelike(level, name, nbt));
	}
}
