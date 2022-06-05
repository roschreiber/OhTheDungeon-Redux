package forge_sandbox.greymerk.roguelike.monster.profiles;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.monster.IEntity;
import forge_sandbox.greymerk.roguelike.monster.IMonsterProfile;
import forge_sandbox.greymerk.roguelike.monster.MonsterProfile;
import forge_sandbox.greymerk.roguelike.treasure.loot.Enchant;
import forge_sandbox.greymerk.roguelike.treasure.loot.Shield;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.World;

public class ProfileSwordsman implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		ItemStack weapon = rand.nextInt(20) == 0 ? ItemNovelty.getItem(ItemNovelty.VALANDRAH)
				: ItemWeapon.getSword(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level));

		mob.setSlot(EquipmentSlot.HAND, weapon);
		mob.setSlot(EquipmentSlot.OFF_HAND, Shield.get(rand));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, level, mob);
	}

}
