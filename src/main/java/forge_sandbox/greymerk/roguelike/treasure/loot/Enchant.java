package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.Random;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public enum Enchant {

	SHARPNESS, SMITE, ARTHOPODS, LOOTING, KNOCKBACK, FIREASPECT, AQUAAFFINITY, RESPIRATION, FEATHERFALLING,
	DEPTHSTRIDER, PROTECTION, BLASTPROTECTION, FIREPROTECTION, PROJECTILEPROTECTION, THORNS, UNBREAKING, EFFICIENCY,
	SILKTOUCH, FORTUNE, POWER, PUNCH, FLAME, INFINITY, LURE, LUCKOFTHESEA, MENDING;

	public static Enchantment getEnchant(Enchant type) {
		String name = getName(type);
		Enchantment res = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(name));
		if (res != null)
			return res;
		return Enchantment.VANISHING_CURSE;
	}

	public static String getName(Enchant type) {
		switch (type) {
		case SHARPNESS:
			return "sharpness";
		case SMITE:
			return "smite";
		case ARTHOPODS:
			return "bane_of_arthropods";
		case LOOTING:
			return "looting";
		case KNOCKBACK:
			return "knockback";
		case FIREASPECT:
			return "fire_aspect";
		case AQUAAFFINITY:
			return "aqua_affinity";
		case RESPIRATION:
			return "respiration";
		case FEATHERFALLING:
			return "feather_falling";
		case DEPTHSTRIDER:
			return "depth_strider";
		case PROTECTION:
			return "protection";
		case BLASTPROTECTION:
			return "blast_protection";
		case FIREPROTECTION:
			return "fire_protection";
		case PROJECTILEPROTECTION:
			return "projectile_protection";
		case THORNS:
			return "thorns";
		case UNBREAKING:
			return "unbreaking";
		case EFFICIENCY:
			return "efficiency";
		case SILKTOUCH:
			return "silk_touch";
		case FORTUNE:
			return "fortune";
		case POWER:
			return "power";
		case PUNCH:
			return "punch";
		case FLAME:
			return "flame";
		case INFINITY:
			return "infinity";
		case LURE:
			return "lure";
		case LUCKOFTHESEA:
			return "luck_of_the_sea";
		case MENDING:
			return "mending";
		default:
			return "efficiency";
		}
	}

	public static int getLevel(Random rand, int level) {

//		switch(level){
//		case 4: return 30 + rand.nextInt(10);
//		case 3: return 15 + rand.nextInt(15);
//		case 2: return 5 + rand.nextInt(15);
//		case 1: return 1 + rand.nextInt(10);
//		case 0: return 1 + rand.nextInt(5);
//		default: return 1;
//		}
		if (level > 4 || level < 0)
			return 1;
		return level;
	}

	public static boolean canEnchant(Difficulty difficulty, Random rand, int level) {

		if (difficulty == null)
			difficulty = Difficulty.NORMAL;

		switch (difficulty) {
		case PEACEFUL:
			return false;
		case EASY:
			return rand.nextInt(6) == 0;
		case NORMAL:
			return level >= 1 && rand.nextInt(4) == 0;
		case HARD:
			return rand.nextBoolean();
		}

		return false;
	}

	private final static Enchantment[] BASIC = { Enchantment.VANISHING_CURSE, Enchantment.BINDING_CURSE,
			Enchantment.EFFICIENCY, Enchantment.KNOCKBACK, Enchantment.UNBREAKING, };

	public static ItemStack enchantItem(Random rand, ItemStack item, int enchantLevel) {

		if (item == null)
			return null;
		if (item.getType() != Material.ENCHANTED_BOOK)
			return item;

		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
		int amount = 1 + rand.nextInt(3);
		boolean flag = false;
		while (enchantLevel > 0) {
			for (int i = 0; i < amount; i++) {
				Enchantment randEnchant = Enchantment.values()[(int) (rand.nextFloat() * Enchantment.values().length)];
				flag = flag | meta.addStoredEnchant(randEnchant, enchantLevel, false);
			}
			if (!flag)
				enchantLevel--;
			else
				break;
		}
		if (!flag)
			meta.addStoredEnchant(BASIC[rand.nextInt(BASIC.length)], 1, false);
		item.setItemMeta(meta);

		// TODO
//		List<EnchantmentData> enchants = null;
//		try{
//			enchants = EnchantmentHelper.buildEnchantmentList(rand, item, enchantLevel, false);
//		} catch(NullPointerException e){
//			throw e;
//		}
//		
//		boolean isBook = item.getType()== Material.BOOK;
//
//		if (isBook){
//			item = new ItemStack(Material.ENCHANTED_BOOK);
//			if(enchants.size() > 1){
//				enchants.remove(rand.nextInt(enchants.size()));
//			}
//		}
//
//		for (EnchantmentData toAdd : enchants){
//			if (isBook){
//				ItemEnchantedBook.addEnchantment(item, toAdd);
//			} else {
//				item.addEnchantment(toAdd.enchantment, toAdd.enchantmentLevel);
//			}
//		}

		return item;
	}
}
