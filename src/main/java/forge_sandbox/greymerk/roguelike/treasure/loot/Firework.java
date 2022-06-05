package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.Random;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import otd.util.ColorUtil;

public class Firework {
	private static class FireworkUnknown {
		public ItemStack get(Random rand, int stackSize) {
			ItemStack rocket = new ItemStack(Material.FIREWORK_ROCKET, stackSize);

			ItemMeta im = rocket.getItemMeta();
			if (!(im instanceof FireworkMeta))
				return rocket;
			FireworkMeta fm = (FireworkMeta) im;

			fm.setPower((rand.nextInt(3) + 1));

			FireworkEffect.Builder builder = FireworkEffect.builder().flicker(rand.nextBoolean())
					.trail(rand.nextBoolean())
					.with(FireworkEffect.Type.values()[rand.nextInt(FireworkEffect.Type.values().length)]);

			int size = rand.nextInt(4) + 1;
			Color[] colorArr = new Color[size];
			for (int i = 0; i < size; ++i) {
				int[] rgb = ColorUtil.hslToRgb(rand.nextFloat(), (float) 1.0, (float) 0.5);
				colorArr[i] = Color.fromRGB(rgb[0], rgb[1], rgb[2]);
			}

			for (Color color : colorArr) {
				builder.withColor(color);
			}

			FireworkEffect properties = builder.build();

			fm.addEffect(properties);
			rocket.setItemMeta(fm);

			return rocket;
		}
	}

	public static ItemStack get(Random rand, int stackSize) {
		return (new FireworkUnknown()).get(rand, stackSize);
	}
}
