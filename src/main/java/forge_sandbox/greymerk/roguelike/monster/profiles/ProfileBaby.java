package forge_sandbox.greymerk.roguelike.monster.profiles;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.monster.IEntity;
import forge_sandbox.greymerk.roguelike.monster.IMonsterProfile;
import forge_sandbox.greymerk.roguelike.monster.MonsterProfile;
import forge_sandbox.greymerk.roguelike.treasure.loot.Enchant;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemTool;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.World;

public class ProfileBaby implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		mob.setChild(true);

		if (rand.nextBoolean()) {
			MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, level, mob);
		}

		ItemStack weapon = ItemTool.getRandom(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level));
		mob.setSlot(EquipmentSlot.HAND, weapon);
	}

}
