package forge_sandbox.greymerk.roguelike.monster.profiles;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.monster.IEntity;
import forge_sandbox.greymerk.roguelike.monster.IMonsterProfile;
import forge_sandbox.greymerk.roguelike.treasure.loot.Enchant;
import forge_sandbox.greymerk.roguelike.treasure.loot.Loot;
import forge_sandbox.greymerk.roguelike.treasure.loot.Slot;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.World;

public class ProfileTallMob implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		for (EquipmentSlot slot : new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS,
				EquipmentSlot.FEET }) {
			ItemStack item = Loot.getEquipmentBySlot(rand, Slot.getSlot(slot), level,
					Enchant.canEnchant(world.getDifficulty(), rand, level));
			mob.setSlot(slot, item);
		}

	}

}
