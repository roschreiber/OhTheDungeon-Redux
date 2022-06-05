package otd.api;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import forge_sandbox.team.cqr.cqrepoured.boss.CastleKing;
import forge_sandbox.twilightforest.structures.lichtower.boss.Lich;
import otd.Main;

public class MobManager {
	private final NamespacedKey otd_mob;

	private MobManager() {
		otd_mob = new NamespacedKey(Main.instance, "otd_mob");
	}

	private static MobManager INSTANCE = new MobManager();

	public static MobManager instance() {
		return INSTANCE;
	}

	public void setOtdMobTag(Entity entity) {
		entity.getPersistentDataContainer().set(otd_mob, PersistentDataType.BYTE, (byte) 1);
	}

	public boolean isOtdNormalMob(Entity e) {
		return e.getPersistentDataContainer().has(otd_mob, PersistentDataType.BYTE);
	}

	public boolean isLich(Entity e) {
		return Lich.isBoss(e);
	}

	public boolean isCastleKing(Entity e) {
		return CastleKing.isBoss(e);
	}
}
