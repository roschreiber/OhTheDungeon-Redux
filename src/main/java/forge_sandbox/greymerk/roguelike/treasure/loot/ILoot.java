package forge_sandbox.greymerk.roguelike.treasure.loot;

import forge_sandbox.greymerk.roguelike.util.IWeighted;
import org.bukkit.inventory.ItemStack;
//import net.minecraft.item.ItemStack;

public interface ILoot {

	public IWeighted<ItemStack> get(Loot type, int level);

}
