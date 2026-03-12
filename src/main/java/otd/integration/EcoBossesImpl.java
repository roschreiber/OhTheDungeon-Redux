package otd.integration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.willfp.ecomobs.mob.EcoMob;
import com.willfp.ecomobs.mob.EcoMobs;
import com.willfp.ecomobs.mob.LivingMob;
import com.willfp.ecomobs.mob.SpawnReason;

import forge_sandbox.team.cqr.cqrepoured.boss.CastleKing;
import forge_sandbox.twilightforest.structures.lichtower.boss.Lich;

import otd.redux.util.ConsoleManager;

public class EcoBossesImpl {
	private static boolean ready = false;

	public static boolean isEcoBossesReady() {
		return ready;
	}

	public static void enable() {
		try {
			if (Class.forName("com.willfp.ecomobs.EcoMobsPlugin") != null
					&& Bukkit.getPluginManager().isPluginEnabled("EcoMobs")) {
				ConsoleManager.logInfo("Loading EcoMobs support ...");
				EcoBossesImpl.inst = new EcoBossesOTDImpl();
				ready = true;
			} else {
				ready = false;
			}
		} catch (Exception e) {
			ready = false;
		}
	}

	public static EcoBossesOTDImpl inst = null;

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

	public static class EcoBossesOTDImpl implements BossOTD {
		public Set<String> getMobNames() {
			Set<String> res = new HashSet<>();
			for (EcoMob mob : EcoMobs.INSTANCE.values()) {
				String id = mob.getID();
				res.add(id);
			}
			return res;
		}

		public Entity spawnMob(String type, Location loc) {
			EcoMob mob = EcoMobs.INSTANCE.getByID(type);
			if (mob != null) {
				LivingMob e = mob.spawn(loc, SpawnReason.COMMAND);
				if (e != null) {
					return e.getEntity();
				}
			}
			return null;
		}

		public boolean isMobExist(String type) {
			return EcoMobs.INSTANCE.getByID(type) != null;
		}
	}
}
