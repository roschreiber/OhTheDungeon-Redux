package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.Random;

import com.google.gson.JsonObject;

import forge_sandbox.greymerk.roguelike.util.IWeighted;
import forge_sandbox.greymerk.roguelike.util.WeightedChoice;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;

public enum Potion {

	HEALING, HARM, REGEN, POISON, STRENGTH, WEAKNESS, SLOWNESS, SWIFTNESS, FIRERESIST;

	public static ItemStack getRandom(Random rand) {
		Potion type = Potion.values()[rand.nextInt(Potion.values().length)];
		return getSpecific(rand, type);
	}

	public static ItemStack getSpecific(Random rand, Potion effect) {
		return getSpecific(PotionForm.REGULAR, effect, rand.nextBoolean(), rand.nextBoolean());
	}

	public static ItemStack getSpecific(Random rand, PotionForm type, Potion effect) {
		return getSpecific(type, effect, rand.nextBoolean(), rand.nextBoolean());
	}

	public static IWeighted<ItemStack> get(JsonObject data, int weight) throws Exception {
		if (!data.has("name"))
			throw new Exception("Potion missing name field");
		String nameString = data.get("name").getAsString();
		NamespacedKey key = NamespacedKey.minecraft(nameString.toLowerCase());
		PotionEffectType type = Registry.EFFECT.get(key);
		if (type == null) {
			type = PotionEffectType.REGENERATION;
		}
		ItemStack item = !data.has("form") ? new ItemStack(Material.POTION)
				: data.get("form").getAsString().toLowerCase().equals("splash") ? new ItemStack(Material.SPLASH_POTION)
						: data.get("form").getAsString().toLowerCase().equals("lingering")
								? new ItemStack(Material.LINGERING_POTION)
								: new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.addCustomEffect(new PotionEffect(type, 60, 1), true);
		item.setItemMeta(meta);
		return new WeightedChoice<>(item, weight);
//		return new WeightedChoice<ItemStack>(PotionUtils.addPotionToItemStack(item, type), weight);
	}

	public static ItemStack getSpecific(PotionForm type, Potion effect, boolean upgrade, boolean extend) {

		ItemStack potion;

		switch (type) {
		case REGULAR:
			potion = new ItemStack(Material.POTION);
			break;
		case SPLASH:
			potion = new ItemStack(Material.SPLASH_POTION);
			break;
		case LINGERING:
			potion = new ItemStack(Material.LINGERING_POTION);
			break;
		default:
			potion = new ItemStack(Material.POTION);
			break;
		}

		PotionEffectType data = getEffect(effect, upgrade, extend);

		return addPotionToItemStack(potion, data, upgrade, extend);
	}

	private static ItemStack addPotionToItemStack(ItemStack potion, PotionEffectType data, boolean upgrade,
			boolean extend) {
		PotionMeta meta = (PotionMeta) potion.getItemMeta();
		int level = upgrade ? 1 : 0;
		if (upgrade)
			extend = false;
		int time = extend ? 8 * 60 * 20 : 3 * 60 * 20;
		meta.addCustomEffect(new PotionEffect(data, time, level), true);
		potion.setItemMeta(meta);
		return potion;
	}

	public static PotionEffectType getEffect(Potion effect, boolean upgrade, boolean extend) {

		if (effect == null)
			return PotionEffectType.GLOWING;

		switch (effect) {
		case HEALING:
			return upgrade ? PotionEffectType.INSTANT_HEALTH : PotionEffectType.INSTANT_HEALTH;
		case HARM:
			return upgrade ? PotionEffectType.INSTANT_DAMAGE : PotionEffectType.INSTANT_DAMAGE;
		case REGEN:
			if (extend) {
				return PotionEffectType.REGENERATION;
			} else {
				return upgrade ? PotionEffectType.REGENERATION : PotionEffectType.REGENERATION;
			}
		case POISON:
			if (extend) {
				return PotionEffectType.POISON;
			} else {
				return upgrade ? PotionEffectType.POISON : PotionEffectType.POISON;
			}
		case STRENGTH:
			if (extend) {
				return PotionEffectType.STRENGTH;
			} else {
				return upgrade ? PotionEffectType.STRENGTH : PotionEffectType.STRENGTH;
			}
		case WEAKNESS:
			if (extend) {
				return PotionEffectType.WEAKNESS;
			} else {
				return PotionEffectType.WEAKNESS;
			}
		case SLOWNESS:
			if (extend) {
				return PotionEffectType.SLOWNESS;
			} else {
				return PotionEffectType.SLOWNESS;
			}
		case SWIFTNESS:
			if (extend) {
				return PotionEffectType.SPEED;
			} else {
				return upgrade ? PotionEffectType.SPEED : PotionEffectType.SPEED;
			}
		case FIRERESIST:
			if (extend) {
				return PotionEffectType.FIRE_RESISTANCE;
			} else {
				return PotionEffectType.FIRE_RESISTANCE;
			}
		default:
			return PotionEffectType.GLOWING;
		}
	}
}
