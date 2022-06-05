package otd.nms;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public interface EquipArmour {
	public Object get(Object mob, Random rand, int level, SpawnPotential sp);
}
