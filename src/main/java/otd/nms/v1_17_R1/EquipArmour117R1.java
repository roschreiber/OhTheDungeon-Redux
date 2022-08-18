package otd.nms.v1_17_R1;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.EquipArmour;

public class EquipArmour117R1 implements EquipArmour {
	public Object get(Object mob, Random rand, int level, SpawnPotential sp) {
		net.minecraft.nbt.NBTTagList armour = new net.minecraft.nbt.NBTTagList();
		armour.add((net.minecraft.nbt.NBTBase) sp
				.getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.nbt.NBTBase) sp
				.getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.nbt.NBTBase) sp
				.getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add((net.minecraft.nbt.NBTBase) sp
				.getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		((net.minecraft.nbt.NBTTagCompound) mob).a("ArmorItems", armour);
//		try {
//			MultiVersion.NMS_1_17.NBTTagCompound_Set.invoke(mob, "ArmorItems", armour);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			if (Main.DEBUG) {
//				Bukkit.getLogger().log(Level.SEVERE, ExceptionRepoter.exceptionToString(e));
//			}
//		}
		return mob;
	}
}
