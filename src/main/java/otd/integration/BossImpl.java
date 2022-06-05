package otd.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.mineacademy.boss.lib.model.Tuple;
import org.mineacademy.boss.model.Boss;
import org.mineacademy.boss.model.BossSpawnReason;
import org.mineacademy.boss.model.BossSpawnResult;
import org.mineacademy.boss.model.SpawnedBoss;

import forge_sandbox.team.cqr.cqrepoured.boss.CastleKing;
import forge_sandbox.twilightforest.structures.lichtower.boss.Lich;

public class BossImpl {
	private static boolean ready = false;

	public static boolean isBossReady() {
		return ready;
	}

	public static void enable() {
		try {
			if (Class.forName("org.mineacademy.boss.BossPlugin") != null) {
				Bukkit.getLogger().info("Loading Boss support ...");
				BossImpl.inst = new BossOTDImpl();
				ready = true;
			}
		} catch (ClassNotFoundException e) {
			ready = false;
		}
	}

	public static BossOTDImpl inst = null;

	public static boolean lichBossReady() {
		if (inst != null) {
			return inst.isMobExist(OTD_LICH);
		}
		return false;
	}

	public static void spawnLich(Location loc) {
		if (inst != null) {
			Entity e = inst.spawnMob(OTD_LICH, loc);
			if (e != null)
				Lich.setBossTag(e);
		}
	}

	public static boolean kingCastleBossReady() {
		if (inst != null) {
			return inst.isMobExist(OTD_CASTLE_KING);
		}
		return false;
	}

	public static void spawnCastleKing(Location loc) {
		if (inst != null) {
			Entity e = inst.spawnMob(OTD_CASTLE_KING, loc);
			if (e != null)
				CastleKing.setBossTag(e);
		}
	}

	public static void spawnSmallBoss(Location loc, Random rand) {
		if (inst != null) {
			List<String> mobs = new ArrayList<>();
			for (String mob : inst.getMobNames()) {
				if (mob.startsWith(OTD_SMALL_BOSS)) {
					mobs.add(mob);
				}
			}
			if (mobs.isEmpty())
				return;
			String mob = mobs.get(rand.nextInt(mobs.size()));
			inst.spawnMob(mob, loc);
		}
	}

	public static interface BossOTD {
		public Set<String> getMobNames();

		public Entity spawnMob(String type, Location loc);

		public boolean isMobExist(String type);
	}

	public final static String OTD_LICH = "otd_lich_boss";
	public final static String OTD_CASTLE_KING = "otd_castle_king";
	public final static String OTD_SMALL_BOSS = "otd_small_boss_";

	public static class BossOTDImpl implements BossOTD {
		public Set<String> getMobNames() {
			return Boss.getBossesNames();
		}

		public Entity spawnMob(String type, Location loc) {
			Boss boss = Boss.findBoss(type);
			if (boss != null) {
				Tuple<BossSpawnResult, SpawnedBoss> tuple = boss.spawn(loc, BossSpawnReason.CUSTOM);
				SpawnedBoss sboss = tuple.getValue();
				return sboss.getEntity();
			}
			return null;
		}

		public boolean isMobExist(String type) {
			return Boss.findBoss(type) != null;
		}
	}
}
