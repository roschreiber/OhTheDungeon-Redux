package forge_sandbox.greymerk.roguelike.monster.profiles;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.monster.IEntity;
import forge_sandbox.greymerk.roguelike.monster.IMonsterProfile;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;
import forge_sandbox.greymerk.roguelike.treasure.loot.Shield;
import forge_sandbox.greymerk.roguelike.treasure.loot.Slot;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import forge_sandbox.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.World;

public class ProfileRleahy implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		ItemStack weapon = ItemNovelty.getItem(ItemNovelty.RLEAHY);
		mob.setSlot(EquipmentSlot.HAND, weapon);
		mob.setSlot(EquipmentSlot.OFF_HAND, Shield.get(rand));

		ItemStack item = ItemArmour.get(rand, Slot.FEET, Quality.WOOD);
		ItemArmour.dyeArmor(item, 32, 32, 32);
		mob.setSlot(EquipmentSlot.FEET, item);

		item = ItemArmour.get(rand, Slot.LEGS, Quality.WOOD);
		ItemArmour.dyeArmor(item, 0, 51, 102);
		mob.setSlot(EquipmentSlot.LEGS, item);

		item = ItemArmour.get(rand, Slot.CHEST, Quality.WOOD);
		ItemArmour.dyeArmor(item, 255, 204, 229);
		mob.setSlot(EquipmentSlot.CHEST, item);
	}
}
