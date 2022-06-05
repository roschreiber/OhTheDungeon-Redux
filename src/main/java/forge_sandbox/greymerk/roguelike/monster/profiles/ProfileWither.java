package forge_sandbox.greymerk.roguelike.monster.profiles;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.monster.IEntity;
import forge_sandbox.greymerk.roguelike.monster.IMonsterProfile;
import forge_sandbox.greymerk.roguelike.monster.MobType;
import forge_sandbox.greymerk.roguelike.monster.MonsterProfile;
import forge_sandbox.greymerk.roguelike.treasure.loot.Enchant;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.World;

public class ProfileWither implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		mob.setMobClass(MobType.WITHERSKELETON, false);
		mob.setSlot(EquipmentSlot.HAND,
				ItemWeapon.getSword(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level)));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, level, mob);
	}

}
