package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import forge_sandbox.greymerk.roguelike.treasure.loot.Equipment;
import forge_sandbox.greymerk.roguelike.treasure.loot.Quality;

import java.util.Random;
import org.bukkit.inventory.ItemStack;
//import net.minecraft.item.ItemStack;

public class ItemSmithy extends ItemBase {

	public ItemSmithy(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return ItemSpecialty.getRandomItem(Equipment.SWORD, rand, Quality.IRON);
	}
}
