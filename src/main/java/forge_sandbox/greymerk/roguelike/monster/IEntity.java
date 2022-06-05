package forge_sandbox.greymerk.roguelike.monster;

//import net.minecraft.inventory.EntityEquipmentSlot;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

//import net.minecraft.item.ItemStack;

public interface IEntity {

	public void setSlot(EquipmentSlot slot, ItemStack item);

	public void setMobClass(MobType type, boolean clear);

	public void setChild(boolean child);

	public boolean instance(Class<?> type);

	public void setName(String name);

}
