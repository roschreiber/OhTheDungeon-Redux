package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;

public class TippedArrow {

	public static ItemStack get(Random rand) {
		return get(rand, 1);
	}

	public static ItemStack get(Random rand, int amount) {
		Potion type = Potion.values()[rand.nextInt(Potion.values().length)];
		return get(type, amount);
	}

	public static ItemStack get(Potion type) {
		return get(type, 1);
	}

	public static ItemStack get(PotionEffectType type) {
		return get(type, 1);
	}

	public static ItemStack get(Potion type, int amount) {

		PotionEffectType pot = Potion.getEffect(type, false, false);
		return get(pot, amount);

	}

	public static ItemStack get(PotionEffectType type, int amount) {

		ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, amount);
		PotionMeta meta = (PotionMeta) arrow.getItemMeta();
		meta.addCustomEffect(new org.bukkit.potion.PotionEffect(type, 1, 1), true);
		arrow.setItemMeta(meta);
//		String id = net.minecraft.potion.PotionType.REGISTRY.getNameForObject(type).toString();
//		
//		ItemStack arrow = new ItemStack(Items.TIPPED_ARROW, amount);
//		
//		NBTTagCompound nbt = new NBTTagCompound();
//		nbt.setString("Potion", id);
//		
//		arrow.setTagCompound(nbt);

		return arrow;
	}

	public static ItemStack getHarmful(Random rand, int amount) {
		switch (rand.nextInt(4)) {
		case 0:
			return TippedArrow.get(Potion.HARM, amount);
		case 1:
			return TippedArrow.get(Potion.POISON, amount);
		case 2:
			return TippedArrow.get(Potion.SLOWNESS, amount);
		case 3:
			return TippedArrow.get(Potion.WEAKNESS, amount);
		default:
			return new ItemStack(Material.ARROW, amount);
		}
	}

}
