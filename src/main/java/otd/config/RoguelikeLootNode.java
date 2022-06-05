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

import org.bukkit.inventory.ItemStack;

/**
 *
 * @author
 */
public class RoguelikeLootNode extends LootNode {
	public final int level;
	public int weight;
	public boolean each;

	public RoguelikeLootNode(ItemStack item, int weight, int max, int level, boolean each) {
		super(item, 1.0, max, max);
		this.weight = weight;
		this.level = level;
		this.each = each;
	}
}
