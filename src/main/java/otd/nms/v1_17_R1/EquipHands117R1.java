package otd.nms.v1_17_R1;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.EquipHands;

public class EquipHands117R1 implements EquipHands {
	public Object get(Object mob, String weapon, String offhand, SpawnPotential sp) {
		net.minecraft.nbt.NBTTagList hands = new net.minecraft.nbt.NBTTagList();
		hands.add((net.minecraft.nbt.NBTBase) sp.getItem(weapon));
		hands.add((net.minecraft.nbt.NBTBase) sp.getItem(offhand));
		net.minecraft.nbt.NBTTagCompound nbt = (net.minecraft.nbt.NBTTagCompound) mob;
		nbt.a("HandItems", hands);

//		try {
//			MultiVersion.NMS_1_17.NBTTagCompound_Set.invoke(mob, "HandItems", hands);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			if (Main.DEBUG) {
//				Bukkit.getLogger().log(Level.SEVERE, ExceptionRepoter.exceptionToString(e));
//			}
//		}
		// ((net.minecraft.nbt.NBTTagCompound) mob).set("HandItems", hands);
		return nbt;
	}
}
