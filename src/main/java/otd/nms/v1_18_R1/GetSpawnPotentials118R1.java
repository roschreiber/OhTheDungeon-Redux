package otd.nms.v1_18_R1;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;
import otd.nms.GetSpawnPotentials;

public class GetSpawnPotentials118R1 implements GetSpawnPotentials {
	public Object get(Random rand, int level, Spawnable s) {
		return getInner(rand, level, s);
//		try {
//			return getInner(rand, level, s);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			return null;
//		}
	}

	private Object getInner(Random rand, int level, Spawnable s) {
//			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (s.type != null) {
			SpawnPotential potential = new SpawnPotential(Spawner.getName(s.type));
			Object res = potential.getNBTTagList(rand, level);
			return res;
		}

		net.minecraft.nbt.NBTTagList potentials = new net.minecraft.nbt.NBTTagList();

		for (SpawnPotential potential : s.potentials) {
			net.minecraft.nbt.NBTTagCompound nbt = (net.minecraft.nbt.NBTTagCompound) potential
					.getNBTTagCompound(level);
			net.minecraft.nbt.NBTTagCompound holder = new net.minecraft.nbt.NBTTagCompound();
			holder.a("data", nbt);
			holder.a("weight", potential.weight);
//			MultiVersion.NMS_1_18.NBTTagCompound_Set.invoke(holder, "data", nbt);
//			MultiVersion.NMS_1_18.NBTTagCompound_SetInt.invoke(holder, "weight", potential.weight);
			potentials.add(holder);
		}

		return potentials;
	}
}
