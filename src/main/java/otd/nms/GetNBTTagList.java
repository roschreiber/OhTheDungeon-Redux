package otd.nms;

import java.util.Random;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;

public class GetNBTTagList {
	public Object get(Random rand, int level, SpawnPotential sp) {
		ReadWriteNBT root = NBT.createNBTObject();
		ReadWriteNBTCompoundList potentials = root.getCompoundList("SpawnPotentials");

		if (sp.name.equals(Spawner.getName(Spawner.ZOMBIE))) {
			for (int i = 0; i < 24; ++i) {
				ReadWriteNBT mob = NBT.createNBTObject();
				mob = (ReadWriteNBT) sp.getRoguelike(level, sp.name, mob);

				Equipment tool;
				switch (rand.nextInt(4)) {
				case 0:
					tool = Equipment.SHOVEL;
					break;
				case 1:
					tool = Equipment.AXE;
					break;
				case 2:
					tool = Equipment.PICK;
					break;
				case 3:
					tool = Equipment.SPEAR;
					break;
				default:
					tool = Equipment.PICK;
					break;
				}

				mob = (ReadWriteNBT) sp.equipHands(mob,
						Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
				mob = (ReadWriteNBT) sp.equipArmour(mob, rand, level);

				ReadWriteNBT data = potentials.addCompound();
				data.getOrCreateCompound("data").mergeCompound((ReadWriteNBT) sp.getPotential(mob));
				data.setInteger("weight", sp.weight);
			}

			return root;
		}

		if (sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
			for (int i = 0; i < 12; ++i) {
				ReadWriteNBT mob = NBT.createNBTObject();
				mob = (ReadWriteNBT) sp.getRoguelike(level, sp.name, mob);
				mob = (ReadWriteNBT) sp.equipHands(mob, "minecraft:bow", null);
				mob = (ReadWriteNBT) sp.equipArmour(mob, rand, level);

				ReadWriteNBT data = potentials.addCompound();
				data.getOrCreateCompound("data").mergeCompound((ReadWriteNBT) sp.getPotential(mob));
				data.setInteger("weight", sp.weight);
			}

			return root;
		}

		{
			ReadWriteNBT data = potentials.addCompound();
			data.getOrCreateCompound("data")
					.mergeCompound((ReadWriteNBT) sp.getPotential(sp.getRoguelike(level, sp.name, NBT.createNBTObject())));
			data.setInteger("weight", sp.weight);
			return root;
		}
	}
}
