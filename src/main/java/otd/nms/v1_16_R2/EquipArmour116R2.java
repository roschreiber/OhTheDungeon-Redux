package otd.nms.v1_16_R2;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.EquipArmour;

public class EquipArmour116R2 implements EquipArmour {
	public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
		net.minecraft.server.v1_16_R2.NBTTagList armour = new net.minecraft.server.v1_16_R2.NBTTagList();
		armour.add((net.minecraft.server.v1_16_R2.NBTBase) sp
				.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R2.NBTBase) sp
				.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R2.NBTBase) sp
				.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.server.v1_16_R2.NBTBase) sp
				.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		((net.minecraft.server.v1_16_R2.NBTTagCompound) mob).set("ArmorItems", armour);

		return mob;
	}
}
