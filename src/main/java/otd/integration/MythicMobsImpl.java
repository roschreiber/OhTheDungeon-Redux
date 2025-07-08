package otd.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import forge_sandbox.team.cqr.cqrepoured.boss.CastleKing;
import forge_sandbox.twilightforest.structures.lichtower.boss.Lich;
import io.lumine.mythic.bukkit.MythicBukkit;
import net.md_5.bungee.api.ChatColor;

public class MythicMobsImpl {
	private static boolean ready = false;

	public static boolean isMythicMobsReady() {
		return ready;
	}

	public static void enable() {
		MythicMobsImpl.inst = null;
		if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
			Bukkit.getLogger().info("Loading MythicMobs support ...");

			try {
				Class.forName("io.lumine.xikage.mythicmobs.MythicMobs");
				MythicMobsImpl.inst = new MythicMobsOTDImpl4();
			} catch (ClassNotFoundException ex) {

			}

			try {
				Class.forName("io.lumine.mythic.bukkit.MythicBukkit");
				MythicMobsImpl.inst = new MythicMobsOTDImpl5();
			} catch (ClassNotFoundException ex) {

			}

			if (MythicMobsImpl.inst == null) {
				Bukkit.getLogger().info("You are using an unsupported version of MythicMobs");
				ready = false;
			} else {
				ready = true;
			}
		}
	}

	public static void report() {
		if (!ready)
			return;
		List<String> mobs = new ArrayList<>();
		for (String mob : inst.getMobNames()) {
			if (mob.startsWith(OTD_SMALL_BOSS)) {
				mobs.add(mob);
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("otd normal boss table: [");
		sb.append(mobs.stream().collect(Collectors.joining(",")));
		sb.append("]");
		Bukkit.getLogger().info(sb.toString());

		Bukkit.getLogger()
				.info("Lich Boss: " + (lichBossReady() ? ChatColor.GREEN + "Found" : ChatColor.RED + "Not Found"));
		Bukkit.getLogger().info(
				"Castle King: " + (kingCastleBossReady() ? ChatColor.GREEN + "Found" : ChatColor.RED + "Not Found"));

	}

	public static MythicMobsOTD inst = null;

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

	public static Entity spawnCustom(Location loc, String name) {
		Entity e = null;
		if (inst != null) {
			e = inst.spawnMob(name, loc);
		}
		return e;
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

	public static interface MythicMobsOTD {
		public Collection<String> getMobNames();

		public Entity spawnMob(String type, Location loc);

		public boolean isMobExist(String type);
	}

	public final static String OTD_LICH = "otd_lich_boss";
	public final static String OTD_CASTLE_KING = "otd_castle_king";
	public final static String OTD_SMALL_BOSS = "otd_small_boss_";

	public static class MythicMobsOTDImpl4 implements MythicMobsOTD {
		public MythicMobsOTDImpl4() {
			Bukkit.getLogger().info("[OTD] Hook to MythicMobs with API-4");
		}

		public Collection<String> getMobNames() {
			io.lumine.mythic.api.mobs.MobManager mm = MythicBukkit.inst().getMobManager();
			return mm.getMobNames();
		}

		public Entity spawnMob(String type, Location loc) {
			io.lumine.mythic.core.mobs.ActiveMob ab = MythicBukkit.inst().getMobManager().spawnMob(type, loc);
			if (ab != null) {
				io.lumine.mythic.api.adapters.AbstractEntity ae = ab.getEntity();
				if (ae != null) {
					Entity entity = io.lumine.mythic.bukkit.BukkitAdapter.adapt(ae);
					return entity;
				}
			}
			return null;
		}

		public boolean isMobExist(String type) {
			return MythicBukkit.inst().getMobManager().getMythicMob(type) != null;
		}
	}

	public static class MythicMobsOTDImpl5 implements MythicMobsOTD {
		public MythicMobsOTDImpl5() {
			Bukkit.getLogger().info("[OTD] Hook to MythicMobs with API-5");
		}

		public Collection<String> getMobNames() {
			io.lumine.mythic.api.mobs.MobManager mm = MythicBukkit.inst().getMobManager();
			return mm.getMobNames();
		}

		public Entity spawnMob(String type, Location loc) {
			io.lumine.mythic.core.mobs.ActiveMob ab = MythicBukkit.inst().getMobManager().spawnMob(type, loc);
			if (ab != null) {
				io.lumine.mythic.api.adapters.AbstractEntity ae = ab.getEntity();
				if (ae != null) {
					Entity entity = io.lumine.mythic.bukkit.BukkitAdapter.adapt(ae);
					return entity;
				}
			}
			return null;
		}

		public boolean isMobExist(String type) {
			return MythicBukkit.inst().getMobManager().getMythicMob(type).isPresent();
		}
	}

}
