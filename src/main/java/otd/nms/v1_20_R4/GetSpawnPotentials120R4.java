package otd.nms.v1_20_R4;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;
import otd.nms.GetSpawnPotentials;

public class GetSpawnPotentials120R4 implements GetSpawnPotentials {
	public Object get(Random rand, int level, Spawnable s) {
		return getInner(rand, level, s);
	}

	private Object getInner(Random rand, int level, Spawnable s) {
		if (s.type != null) {
			SpawnPotential potential = new SpawnPotential(Spawner.getName(s.type));
			Object res = potential.getNBTTagList(rand, level);
			return res;
		}

		net.minecraft.nbt.ListTag potentials = new net.minecraft.nbt.ListTag();

		for (SpawnPotential potential : s.potentials) {
			net.minecraft.nbt.CompoundTag nbt = (net.minecraft.nbt.CompoundTag) potential
					.getNBTTagCompound(level);
			net.minecraft.nbt.CompoundTag holder = new net.minecraft.nbt.CompoundTag();
			holder.put("data", nbt);
			holder.putInt("weight", potential.weight);
			potentials.add(holder);
		}

		return potentials;
	}
}
