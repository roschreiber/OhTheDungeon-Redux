package otd.nms.v1_20_R4;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.EquipArmour;

public class EquipArmour120R4 implements EquipArmour {
	public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
		net.minecraft.nbt.ListTag armour = new net.minecraft.nbt.ListTag();
		armour.add((net.minecraft.nbt.Tag) sp
				.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.nbt.Tag) sp
				.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.nbt.Tag) sp
				.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.nbt.Tag) sp
				.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		((net.minecraft.nbt.CompoundTag) mob).put("ArmorItems", armour);

		return mob;
	}
}
