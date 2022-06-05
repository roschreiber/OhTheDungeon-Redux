package otd.nms;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public interface GetNBTTagCompound {
	public Object get(int level, String name, Object inbt, SpawnPotential sp);
}
