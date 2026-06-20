package otd.nms;

import java.util.Random;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;

public class GetSpawnPotentials {
	public Object get(Random rand, int level, Spawnable s) {
		return getInner(rand, level, s);
	}

	private Object getInner(Random rand, int level, Spawnable s) {
		if (s.type != null) {
			SpawnPotential potential = new SpawnPotential(Spawner.getName(s.type));
			return potential.getNBTTagList(rand, level);
		}

		ReadWriteNBT root = NBT.createNBTObject();
		ReadWriteNBTCompoundList potentials = root.getCompoundList("SpawnPotentials");

		for (SpawnPotential potential : s.potentials) {
			ReadWriteNBT nbt = (ReadWriteNBT) potential.getNBTTagCompound(level);
			ReadWriteNBT holder = potentials.addCompound();
			holder.getOrCreateCompound("data").mergeCompound(nbt);
			holder.setInteger("weight", potential.weight);
		}

		return root;
	}
}
