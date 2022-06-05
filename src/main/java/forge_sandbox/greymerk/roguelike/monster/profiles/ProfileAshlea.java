package forge_sandbox.greymerk.roguelike.monster.profiles;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.monster.IEntity;
import forge_sandbox.greymerk.roguelike.monster.IMonsterProfile;
import forge_sandbox.greymerk.roguelike.monster.MonsterProfile;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.treasure.loot.Slot;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class ProfileAshlea implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {

		mob.setChild(true);

		MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, level, mob);

		ItemStack weapon = ItemNovelty.getItem(ItemNovelty.ASHLEA);
		mob.setSlot(EquipmentSlot.HAND, weapon);

		for (EquipmentSlot slot : new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS,
				EquipmentSlot.FEET }) {
			ItemStack item = ItemArmour.get(rand, Slot.getSlot(slot), Quality.WOOD);
			ItemArmour.dyeArmor(item, 255, 100, 255);
			mob.setSlot(slot, item);
		}
	}
}
