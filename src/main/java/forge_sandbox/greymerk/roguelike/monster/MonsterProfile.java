package forge_sandbox.greymerk.roguelike.monster;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileArcher;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileAshlea;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileBaby;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileBogged;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileEvoker;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileHusk;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileJohnny;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileMagicArcher;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfilePigman;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfilePoisonArcher;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileRleahy;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileSkeleton;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileSwordsman;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileTallMob;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileVillager;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileVindicator;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileWitch;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileWither;
import forge_sandbox.greymerk.roguelike.monster.profiles.ProfileZombie;
import org.bukkit.World;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
//import net.minecraft.entity.monster.EntitySkeleton;
//import net.minecraft.entity.monster.EntityZombie;
//import net.minecraft.world.World;

public enum MonsterProfile {

	TALLMOB, ZOMBIE, PIGMAN, SKELETON, VILLAGER, HUSK, BABY, ASHLEA, RLEAHY, ARCHER, WITHER, POISONARCHER, MAGICARCHER,
	SWORDSMAN, EVOKER, VINDICATOR, WITCH, JOHNNY, BOGGED;

	public static IMonsterProfile get(MonsterProfile profile) {
		switch (profile) {
		case TALLMOB:
			return new ProfileTallMob();
		case ZOMBIE:
			return new ProfileZombie();
		case PIGMAN:
			return new ProfilePigman();
		case SKELETON:
			return new ProfileSkeleton();
		case VILLAGER:
			return new ProfileVillager();
		case HUSK:
			return new ProfileHusk();
		case BABY:
			return new ProfileBaby();
		case ASHLEA:
			return new ProfileAshlea();
		case RLEAHY:
			return new ProfileRleahy();
		case ARCHER:
			return new ProfileArcher();
		case WITHER:
			return new ProfileWither();
		case POISONARCHER:
			return new ProfilePoisonArcher();
		case MAGICARCHER:
			return new ProfileMagicArcher();
		case SWORDSMAN:
			return new ProfileSwordsman();
		case EVOKER:
			return new ProfileEvoker();
		case VINDICATOR:
			return new ProfileVindicator();
		case WITCH:
			return new ProfileWitch();
		case JOHNNY:
			return new ProfileJohnny();
		case BOGGED:
			return new ProfileBogged();
		default:
			return new ProfileTallMob();
		}
	}

	public static void equip(World world, Random rand, int level, IEntity mob) {

		IMonsterProfile profile = null;

		if (mob.instance(Zombie.class))
			profile = get(ZOMBIE);

		if (mob.instance(Skeleton.class))
			profile = get(SKELETON);

		if (profile == null)
			return;

		profile.addEquipment(world, rand, level, mob);
	}

}
