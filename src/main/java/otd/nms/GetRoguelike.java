package otd.nms;

import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.config.WorldConfig;

public class GetRoguelike {
	public Object get(int level, String type, Object otag, SpawnPotential sp) {
		return getInner(level, type, otag, sp);
	}

	private Object getInner(int level, String type, Object otag, SpawnPotential sp) {
		ReadWriteNBT tag = (ReadWriteNBT) otag;
		tag.setString("id", type);

		if (!(WorldConfig.wc.rogueSpawners && sp.equip))
			return tag;

		ReadWriteNBTCompoundList activeEffects = tag.getCompoundList("active_effects");
		ReadWriteNBT buff = activeEffects.addCompound();
		buff.setString("id", "minecraft:mining_fatigue");
		buff.setByte("amplifier", (byte) level);
		buff.setInteger("duration", 10);
		buff.setBoolean("ambient", false);

		return tag;
	}
}
