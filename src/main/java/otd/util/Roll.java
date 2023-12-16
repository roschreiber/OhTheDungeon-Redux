/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import otd.Main;
import otd.listener.MobListener;
import otd.config.WorldConfig;

/**
 *
 * @author
 */
public class Roll {

	public final static String DICE = "DICE_TAG";

	public static ItemStack getDice() {
		return createHead(WorldConfig.wc.diceUUID, WorldConfig.wc.diceTexture);
	}

	public static ItemStack createHead(final String uuid, final String textures) {
		ItemStack is = Skull.DICE.getItem();
		ItemMeta im = is.getItemMeta();

		im.setDisplayName(I18n.instance.Dice);
		List<String> lores = new ArrayList<>();
		lores.add(I18n.instance.DiceContent);
		lores.add(0, DICE);
		im.setLore(lores);

		is.setItemMeta(im);
		return is;
	}

	public static void registerRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(Main.instance, "dice"), getDice());
		recipe.shape(" X ", "XSX", " X ");
		recipe.setIngredient('X', Material.IRON_INGOT);
		recipe.setIngredient('S', Material.CRAFTING_TABLE);
		Bukkit.getServer().addRecipe(recipe);
	}

	private static Random random = new Random();

	public static void roll(Player p) {
		String name = p.getName();
		Long stamp = System.currentTimeMillis() / 1000;
		if (!MobListener.roll_cool_down.containsKey(name)) {
			MobListener.roll_cool_down.put(name, 0L);
		}
		long ts = MobListener.roll_cool_down.get(name);
		if (stamp - ts <= WorldConfig.wc.rollCoolDownInSecond)
			return;
		int range = WorldConfig.wc.rollRange;
		int r = random.nextInt(100);
		String str = name + "'s roll: " + r + "/100";
		for (Entity entity : p.getNearbyEntities(range, range, range)) {
			if (entity.getType() == EntityType.PLAYER) {
				Player player = (Player) entity;
				player.sendMessage(str);
			}
		}
		p.sendMessage(str);
		MobListener.roll_cool_down.put(name, stamp);
	}
}
