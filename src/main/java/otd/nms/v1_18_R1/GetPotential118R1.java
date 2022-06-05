package otd.nms.v1_18_R1;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.GetPotential;

public class GetPotential118R1 implements GetPotential {

	public Object get(Object mob, SpawnPotential sp) {
		Object obj = null;
		obj = getInner(mob, sp);
//		try {
//			obj = getInner(mob, sp);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			if (Main.DEBUG) {
//				Bukkit.getLogger().log(Level.SEVERE, ExceptionRepoter.exceptionToString(e));
//			}
//		}
		return obj;
	}

	private Object getInner(Object mob, SpawnPotential sp) {
//			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		net.minecraft.nbt.NBTTagCompound potential = new net.minecraft.nbt.NBTTagCompound();
		potential.a("entity", (net.minecraft.nbt.NBTBase) mob);

		net.minecraft.nbt.NBTTagCompound custom_spawn_rules = new net.minecraft.nbt.NBTTagCompound();
		net.minecraft.nbt.NBTTagCompound sky_light_limit = new net.minecraft.nbt.NBTTagCompound();
		sky_light_limit.a("min_inclusive", 0);
		sky_light_limit.a("max_exclusive", 15);
		net.minecraft.nbt.NBTTagCompound block_light_limit = new net.minecraft.nbt.NBTTagCompound();
		block_light_limit.a("min_inclusive", 0);
		block_light_limit.a("max_exclusive", 15);
		custom_spawn_rules.a("sky_light_limit", sky_light_limit);
		custom_spawn_rules.a("block_light_limit", block_light_limit);
		potential.a("custom_spawn_rules", custom_spawn_rules);
//		MultiVersion.NMS_1_18.NBTTagCompound_Set.invoke(potential, "entity", (net.minecraft.nbt.NBTBase) mob);
		// MultiVersion.NMS_1_18.NBTTagCompound_SetInt.invoke(potential, "Weight",
		// sp.weight);
		// potential.set("Entity", (net.minecraft.nbt.NBTBase) mob);
		// potential.setInt("Weight", sp.weight);
		return potential;
	}
}
