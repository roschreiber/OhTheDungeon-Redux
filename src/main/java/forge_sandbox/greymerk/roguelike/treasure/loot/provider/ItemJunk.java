package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.treasure.loot.PotionMixture;
import forge_sandbox.greymerk.roguelike.treasure.loot.Shield;
import forge_sandbox.greymerk.roguelike.treasure.loot.TippedArrow;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;

public class ItemJunk extends ItemBase {

	public ItemJunk(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {

		if (level > 0 && rand.nextInt(200) == 0) {
			if (level > 2 && rand.nextInt(10) == 0)
				return new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1);
			if (level > 1 && rand.nextInt(5) == 0)
				return new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1);
			if (rand.nextInt(3) == 0)
				return new ItemStack(Material.IRON_HORSE_ARMOR, 1);
			return new ItemStack(Material.SADDLE);
		}

		if (rand.nextInt(100) == 0)
			return PotionMixture.getRandom(rand);

		if (level > 1 && rand.nextInt(100) == 0)
			return new ItemStack(Material.GHAST_TEAR);

		if (level < 3 && rand.nextInt(80) == 0)
			return new ItemStack(Material.BOOK);

		if (rand.nextInt(80) == 0)
			return Shield.get(rand);

		if (level > 1 && rand.nextInt(60) == 0)
			return TippedArrow.get(rand, 4 + rand.nextInt(level) * 2);

		if (level > 1 && rand.nextInt(50) == 0) {
			switch (rand.nextInt(6)) {
			case 0:
				return new ItemStack(Material.GUNPOWDER, 1 + rand.nextInt(3));
			case 1:
				return new ItemStack(Material.BLAZE_POWDER, 1 + rand.nextInt(3));
			case 2:
				return new ItemStack(Material.GOLD_NUGGET, 1 + rand.nextInt(3));
			case 3:
				return new ItemStack(Material.REDSTONE, 1 + rand.nextInt(3));
			case 4:
				return new ItemStack(Material.GLOWSTONE_DUST, 1 + rand.nextInt(8));
			case 5:
				return new ItemStack(Material.COCOA_BEANS, 1 + rand.nextInt(3));
			}
		}

		if (rand.nextInt(60) == 0)
			return PotionMixture.getPotion(rand, PotionMixture.LAUDANUM);

		if (rand.nextInt(30) == 0)
			return new ItemStack(Material.TORCH, 6 + rand.nextInt(20));

		if (level > 0 && rand.nextInt(8) == 0) {
			switch (rand.nextInt(8)) {
			case 0:
				return new ItemStack(Material.SLIME_BALL);
			case 1:
				return new ItemStack(Material.SNOWBALL);
			case 2:
				return new ItemStack(Material.MUSHROOM_STEW);
			case 3:
				return new ItemStack(Material.CLAY_BALL);
			case 4:
				return new ItemStack(Material.FLINT);
			case 5:
				return new ItemStack(Material.FEATHER);
			case 6:
				return new ItemStack(Material.GLASS_BOTTLE);
			case 7:
				return new ItemStack(Material.LEATHER);
			}
		}

		switch (rand.nextInt(7)) {
		case 0:
			return new ItemStack(Material.BONE);
		case 1:
			return new ItemStack(Material.ROTTEN_FLESH);
		case 2:
			return new ItemStack(Material.SPIDER_EYE);
		case 3:
			return new ItemStack(Material.PAPER);
		case 4:
			return new ItemStack(Material.STRING);
		case 5:
			return new ItemStack(Material.STICK);
		default:
			return new ItemStack(Material.STICK);
		}
	}
}
