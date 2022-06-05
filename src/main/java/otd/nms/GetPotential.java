package otd.nms;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public interface GetPotential {
	public Object get(Object mob, SpawnPotential sp);
}