package forge_sandbox.greymerk.roguelike.monster;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public enum MobType {

	ZOMBIE, ZOMBIEVILLAGER, HUSK, SKELETON, STRAY, SPIDER, CREEPER, WITHERSKELETON, PIGZOMBIE, EVOKER, VINDICATOR,
	WITCH, BOGGED;

	public static Entity getEntity(World world, Location loc, MobType type) {
		return switch (type) {
		case ZOMBIE -> {
			yield world.spawnEntity(loc, EntityType.ZOMBIE);
		}
		case ZOMBIEVILLAGER -> {
			yield world.spawnEntity(loc, EntityType.ZOMBIE_VILLAGER);
		}
		case HUSK -> {
			yield world.spawnEntity(loc, EntityType.HUSK);
		}
		case SKELETON -> {
			yield world.spawnEntity(loc, EntityType.SKELETON);
		}
		case STRAY -> {
			yield world.spawnEntity(loc, EntityType.STRAY);
		}
		case SPIDER -> {
			yield world.spawnEntity(loc, EntityType.SPIDER);
		}
		case CREEPER -> {
			yield world.spawnEntity(loc, EntityType.CREEPER);
		}
		case WITHERSKELETON -> {
			yield world.spawnEntity(loc, EntityType.WITHER_SKELETON);
		}
		case PIGZOMBIE -> {
			yield world.spawnEntity(loc, EntityType.ZOMBIFIED_PIGLIN);
		}
		case EVOKER -> {
			yield world.spawnEntity(loc, EntityType.EVOKER);
		}
		case VINDICATOR -> {
			yield world.spawnEntity(loc, EntityType.VINDICATOR);
		}
		case WITCH -> {
			yield world.spawnEntity(loc, EntityType.WITCH);
		}
		case BOGGED -> {
			yield world.spawnEntity(loc, EntityType.BOGGED);
		}
		default -> {
			yield world.spawnEntity(loc, EntityType.ZOMBIE);
		}
		};
	}
}
