package otd.nms.v1_20_R1;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawner;
import otd.nms.GetNBTTagList;

public class GetNBTTagList120R1 implements GetNBTTagList {
	public Object get(Random rand, int level, SpawnPotential sp) {
		net.minecraft.nbt.NBTTagList potentials = new net.minecraft.nbt.NBTTagList();
		if (sp.name.equals(Spawner.getName(Spawner.ZOMBIE))) {
			for (int i = 0; i < 24; ++i) {
				net.minecraft.nbt.NBTTagCompound mob = new net.minecraft.nbt.NBTTagCompound();
				mob = (net.minecraft.nbt.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);

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

				mob = (net.minecraft.nbt.NBTTagCompound) sp.equipHands(mob,
						Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
				mob = (net.minecraft.nbt.NBTTagCompound) sp.equipArmour(mob, rand, level);

				net.minecraft.nbt.NBTTagCompound data = new net.minecraft.nbt.NBTTagCompound();
				data.a("data", (net.minecraft.nbt.NBTBase) sp.getPotential(mob));
				data.a("weight", sp.weight);

				potentials.add(data);
			}

			return potentials;
		}

		if (sp.name.equals(Spawner.getName(Spawner.SKELETON))) {
			for (int i = 0; i < 12; ++i) {
				net.minecraft.nbt.NBTTagCompound mob = new net.minecraft.nbt.NBTTagCompound();
				mob = (net.minecraft.nbt.NBTTagCompound) sp.getRoguelike(level, sp.name, mob);
				mob = (net.minecraft.nbt.NBTTagCompound) sp.equipHands(mob, "minecraft:bow", null);
				mob = (net.minecraft.nbt.NBTTagCompound) sp.equipArmour(mob, rand, level);

				net.minecraft.nbt.NBTTagCompound data = new net.minecraft.nbt.NBTTagCompound();
				data.a("data", (net.minecraft.nbt.NBTBase) sp.getPotential(mob));
				data.a("weight", sp.weight);

				if (data != null)
					potentials.add(data);
			}

			return potentials;
		}

		{
			net.minecraft.nbt.NBTTagCompound data = new net.minecraft.nbt.NBTTagCompound();
			data.a("data", (net.minecraft.nbt.NBTBase) sp
					.getPotential(sp.getRoguelike(level, sp.name, new net.minecraft.nbt.NBTTagCompound())));
			data.a("weight", sp.weight);

			if (data != null)
				potentials.add(data);
			return potentials;
		}
	}
}
