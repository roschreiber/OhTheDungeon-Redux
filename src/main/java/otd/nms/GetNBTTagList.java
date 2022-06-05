package otd.nms;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public interface GetNBTTagList {
	public Object get(Random rand, int level, SpawnPotential sp);
}
