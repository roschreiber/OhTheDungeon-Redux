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
package otd.config;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author
 */
public class LootNode {
	public String item;
	public double chance;
	public int min = 1;
	public int max = 1;
	public String itemStackString;
	private transient ItemStack itemstack;

	public static String itemStackToString(ItemStack is) {
		YamlConfiguration yml = new YamlConfiguration();
		yml.set("item", is);
		return yml.saveToString();
	}

	public static ItemStack stringToItemStack(String str) {
		YamlConfiguration yml = new YamlConfiguration();
		ItemStack item;
		try {
			yml.loadFromString(str);
			item = yml.getItemStack("item");
		} catch (InvalidConfigurationException ex) {
			item = new ItemStack(Material.AIR, 1);
		}
		return item;
	}

	public LootNode(ItemStack item, double chance, int max, int min) {
		this.chance = chance;
		this.itemStackString = itemStackToString(item);
		this.itemstack = item.clone();
		if (max >= min) {
			this.max = max;
			this.min = min;
		} else {
			this.max = min;
			this.min = max;
		}
	}

	public ItemStack getItem() {
		if (itemstack != null)
			return itemstack;
		itemstack = stringToItemStack(itemStackString);
		return itemstack;
	}
}
