package otd.nms;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public interface EquipHands {
	public Object get(Object mob, String weapon, String offhand, SpawnPotential sp);
}