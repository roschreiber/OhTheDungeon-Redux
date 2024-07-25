package otd.nms;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;

public class GetPotential {

	public Object get(Object mob, SpawnPotential sp) {
		Object obj = null;
		obj = getInner(mob, sp);
		return obj;
	}

	private Object getInner(Object mob, SpawnPotential sp) {
		net.minecraft.nbt.CompoundTag potential = new net.minecraft.nbt.CompoundTag();
		potential.put("entity", (net.minecraft.nbt.Tag) mob);

		net.minecraft.nbt.CompoundTag custom_spawn_rules = new net.minecraft.nbt.CompoundTag();
		net.minecraft.nbt.CompoundTag sky_light_limit = new net.minecraft.nbt.CompoundTag();
		sky_light_limit.putInt("min_inclusive", 0);
		sky_light_limit.putInt("max_exclusive", 15);
		net.minecraft.nbt.CompoundTag block_light_limit = new net.minecraft.nbt.CompoundTag();
		block_light_limit.putInt("min_inclusive", 0);
		block_light_limit.putInt("max_exclusive", 15);
		custom_spawn_rules.put("sky_light_limit", sky_light_limit);
		custom_spawn_rules.put("block_light_limit", block_light_limit);
		potential.put("custom_spawn_rules", custom_spawn_rules);
		return potential;
	}
}
