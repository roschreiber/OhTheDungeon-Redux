package otd.nms.v1_16_R3;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;
import otd.nms.GetNBTTagList;

public class GetNBTTagList116R3 implements GetNBTTagList {
	public Object get(Random rand, int level, SpawnPotential sp) {
		net.minecraft.server.v1_16_R3.NBTTagList potentials = new net.minecraft.server.v1_16_R3.NBTTagList();
		if (sp.name.equals(Spawner.getName(Spawner.ZOMBIE))) {
			for (int i = 0; i < 24; ++i) {
				net.minecraft.server.v1_16_R3.NBTTagCompound mob = new net.minecraft.server.v1_16_R3.NBTTagCompound();
				mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);

				Equipment tool;
				switch (rand.nextInt(3)) {
				case 0:
					tool = Equipment.SHOVEL;
					break;
				case 1:
					tool = Equipment.AXE;
					break;
				case 2:
					tool = Equipment.PICK;
					break;
				default:
					tool = Equipment.PICK;
					break;
				}

				mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.equipHands(mob,
						Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
				mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.equipArmour(mob, rand, level);

				potentials.add((net.minecraft.server.v1_16_R3.NBTBase) sp.getPotential(mob));
			}

			return potentials;
		}

		if (sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
			for (int i = 0; i < 12; ++i) {
				net.minecraft.server.v1_16_R3.NBTTagCompound mob = new net.minecraft.server.v1_16_R3.NBTTagCompound();
				mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);
				mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.equipHands(mob, "minecraft:bow", null);
				mob = (net.minecraft.server.v1_16_R3.NBTTagCompound) sp.equipArmour(mob, rand, level);
				potentials.add((net.minecraft.server.v1_16_R3.NBTBase) sp.getPotential(mob));
			}

			return potentials;
		}

		potentials.add((net.minecraft.server.v1_16_R3.NBTBase) sp
				.getPotential(sp.getRoguelike(level, sp.name, new net.minecraft.server.v1_16_R3.NBTTagCompound())));
		return potentials;
	}
}
