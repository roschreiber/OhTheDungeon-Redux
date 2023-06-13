package otd.nms.v1_20_R1;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.GetPotential;

public class GetPotential120R1 implements GetPotential {

	public Object get(Object mob, SpawnPotential sp) {
		Object obj = null;
		obj = getInner(mob, sp);
		return obj;
	}

	private Object getInner(Object mob, SpawnPotential sp) {
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
		return potential;
	}
}
