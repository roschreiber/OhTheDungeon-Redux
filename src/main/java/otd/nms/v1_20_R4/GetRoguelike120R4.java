package otd.nms.v1_20_R4;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.config.WorldConfig;
import otd.nms.GetRoguelike;

public class GetRoguelike120R4 implements GetRoguelike {
	public Object get(int level, String type, Object otag, SpawnPotential sp) {
		Object obj = null;
		obj = getInner(level, type, otag, sp);

		return obj;
	}

	private Object getInner(int level, String type, Object otag, SpawnPotential sp) {
		net.minecraft.nbt.CompoundTag tag = (net.minecraft.nbt.CompoundTag) otag;
		tag.putString("id", type);

		if (!(WorldConfig.wc.rogueSpawners && sp.equip))
			return tag;
		net.minecraft.nbt.ListTag activeEffects = new net.minecraft.nbt.ListTag();
		tag.put("ActiveEffects", activeEffects);

		net.minecraft.nbt.CompoundTag buff = new net.minecraft.nbt.CompoundTag();
		activeEffects.add(buff);

		buff.putByte("Id", (byte) 4);
		buff.putByte("Amplifier", (byte) level);
		buff.putInt("Duration", 10);
		buff.putByte("Ambient", (byte) 0);

		return tag;
	}
}
