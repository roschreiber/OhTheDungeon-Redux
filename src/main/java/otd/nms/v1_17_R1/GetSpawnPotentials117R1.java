package otd.nms.v1_17_R1;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;
import otd.nms.GetSpawnPotentials;

public class GetSpawnPotentials117R1 implements GetSpawnPotentials {
	public Object get(Random rand, int level, Spawnable s) {
		if (s.type != null) {
			SpawnPotential potential = new SpawnPotential(Spawner.getName(s.type));
			return potential.getNBTTagList(rand, level);
		}

		net.minecraft.nbt.NBTTagList potentials = new net.minecraft.nbt.NBTTagList();

		for (SpawnPotential potential : s.potentials) {
			net.minecraft.nbt.NBTTagCompound nbt = (net.minecraft.nbt.NBTTagCompound) potential
					.getNBTTagCompound(level);
			potentials.add(nbt);
		}

		return potentials;
	}
}
