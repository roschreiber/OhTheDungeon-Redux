package forge_sandbox.greymerk.roguelike.treasure.loot;

//import net.minecraft.item.ItemStack;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;

public enum PotionEffect {

	SPEED(1), SLOWNESS(2), HASTE(3), FATIGUE(4), STRENGTH(5), HEALTH(6), DAMAGE(7), JUMP(8), NAUSIA(9), REGEN(10),
	RESISTANCE(11), FIRERESIST(12), WATERBREATH(13), INVISIBILITY(14), BLINDNESS(15), NIGHTVISION(16), HUNGER(17),
	WEAKNESS(18), POISON(19), WITHER(20), HEALTHBOOST(21), ABSORPTION(22), SATURATION(23), GLOWING(24), LEVITATION(25),
	LUCK(26), BAD_LUCK(27);

	public static int TICKS_PER_SECOND = 20;

	private int id;

	PotionEffect(int id) {
		this.id = id;
	}

	public static int getEffectID(PotionEffect type) {
		return type.id;
	}

	public static PotionEffectType toPotionEffectType(PotionEffect pe) {
		if (pe == SPEED)
			return PotionEffectType.SPEED;
		else if (pe == SLOWNESS)
			return PotionEffectType.SLOWNESS;
		else if (pe == HASTE)
			return PotionEffectType.HASTE;
		else if (pe == FATIGUE)
			return PotionEffectType.MINING_FATIGUE;
		else if (pe == STRENGTH)
			return PotionEffectType.STRENGTH;
		else if (pe == HEALTH)
			return PotionEffectType.HEALTH_BOOST;
		else if (pe == DAMAGE)
			return PotionEffectType.INSTANT_DAMAGE;
		else if (pe == JUMP)
			return PotionEffectType.JUMP_BOOST;
		else if (pe == NAUSIA)
			return PotionEffectType.NAUSEA;
		else if (pe == REGEN)
			return PotionEffectType.REGENERATION;
		else if (pe == RESISTANCE)
			return PotionEffectType.RESISTANCE;
		else if (pe == FIRERESIST)
			return PotionEffectType.FIRE_RESISTANCE;
		else if (pe == WATERBREATH)
			return PotionEffectType.WATER_BREATHING;
		else if (pe == INVISIBILITY)
			return PotionEffectType.INVISIBILITY;
		else if (pe == BLINDNESS)
			return PotionEffectType.BLINDNESS;
		else if (pe == NIGHTVISION)
			return PotionEffectType.NIGHT_VISION;
		else if (pe == HUNGER)
			return PotionEffectType.HUNGER;
		else if (pe == WEAKNESS)
			return PotionEffectType.WEAKNESS;
		else if (pe == POISON)
			return PotionEffectType.POISON;
		else if (pe == WITHER)
			return PotionEffectType.WITHER;
		else if (pe == HEALTHBOOST)
			return PotionEffectType.HEALTH_BOOST;
		else if (pe == ABSORPTION)
			return PotionEffectType.ABSORPTION;
		else if (pe == SATURATION)
			return PotionEffectType.SATURATION;
		else if (pe == GLOWING)
			return PotionEffectType.GLOWING;
		else if (pe == LEVITATION)
			return PotionEffectType.LEVITATION;
		else if (pe == LUCK)
			return PotionEffectType.LUCK;
		else if (pe == BAD_LUCK)
			return PotionEffectType.UNLUCK;
		return PotionEffectType.SPEED;
	}

	public static void addCustomEffect(ItemStack potion, PotionEffect type, int amplifier, int duration) {
		PotionMeta meta = (PotionMeta) potion.getItemMeta();
		if (meta == null)
			return;
		meta.addCustomEffect(new org.bukkit.potion.PotionEffect(toPotionEffectType(type), duration, amplifier), true);
		potion.setItemMeta(meta);
//		final String CUSTOM = "CustomPotionEffects";
//		
//		NBTTagCompound tag = potion.getTagCompound();
//		if(tag == null){
//			tag = new NBTTagCompound();
//			potion.setTagCompound(tag);
//		}
//		
//		
//		NBTTagList effects;
//		effects = tag.getTagList(CUSTOM, 10);
//		if (effects == null){
//			effects = new NBTTagList();
//			tag.setTag(CUSTOM, effects);
//		}
//		
//		NBTTagCompound toAdd = new NBTTagCompound();
//		
//		toAdd.setByte("Id", (byte)type.id);
//		toAdd.setByte("Amplifier", (byte)(amplifier - 1));
//		toAdd.setInteger("Duration", duration * TICKS_PER_SECOND);
//		toAdd.setBoolean("Ambient", true);
//		
//		effects.appendTag(toAdd);
//		tag.setTag(CUSTOM, effects);
//		potion.setTagCompound(tag);
	}
}
