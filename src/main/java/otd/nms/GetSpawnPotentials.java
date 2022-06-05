package otd.nms;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;

public interface GetSpawnPotentials {
	public Object get(Random rand, int level, Spawnable s);
}