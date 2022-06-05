package otd.nms;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public interface GetRoguelike {
	public Object get(int level, String type, Object otag, SpawnPotential sp);
}