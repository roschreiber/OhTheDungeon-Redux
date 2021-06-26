package forge_sandbox.greymerk.roguelike.monster;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public enum MobType {
	
	ZOMBIE, ZOMBIEVILLAGER, HUSK, SKELETON, STRAY, SPIDER, CREEPER, WITHERSKELETON, PIGZOMBIE, EVOKER, VINDICATOR, WITCH;
	
	
	public static Entity getEntity(World world, Location loc, MobType type){
		switch(type){
		case ZOMBIE: return world.spawnEntity(loc, EntityType.ZOMBIE);
		case ZOMBIEVILLAGER: return world.spawnEntity(loc, EntityType.ZOMBIE_VILLAGER);
		case HUSK: return world.spawnEntity(loc, EntityType.HUSK);
		case SKELETON: return world.spawnEntity(loc, EntityType.SKELETON);
		case STRAY: return world.spawnEntity(loc, EntityType.STRAY);
		case SPIDER: return world.spawnEntity(loc, EntityType.SPIDER);
		case CREEPER: return world.spawnEntity(loc, EntityType.CREEPER);
		case WITHERSKELETON: return world.spawnEntity(loc, EntityType.WITHER_SKELETON);
		case PIGZOMBIE: {
                    if(otd.Main.version == otd.MultiVersion.Version.UNKNOWN) {
                        return world.spawnEntity(loc, otd.MultiVersion.getPigZombieForUnknownVersion());
                    } else if(otd.Main.version == otd.MultiVersion.Version.V1_16_R1
                            || otd.Main.version == otd.MultiVersion.Version.V1_16_R2
                            || otd.Main.version == otd.MultiVersion.Version.V1_16_R3
                            || otd.Main.version == otd.MultiVersion.Version.V1_17_R1) {
                        return world.spawnEntity(loc, EntityType.valueOf("ZOMBIFIED_PIGLIN"));
                    } else {
                        return world.spawnEntity(loc, EntityType.valueOf("PIG_ZOMBIE"));
                    }
                }
		case EVOKER: return world.spawnEntity(loc, EntityType.EVOKER);
		case VINDICATOR: return world.spawnEntity(loc, EntityType.VINDICATOR);
		case WITCH: return world.spawnEntity(loc, EntityType.WITCH);
		
		default: return world.spawnEntity(loc, EntityType.ZOMBIE);
		}
	}
}
