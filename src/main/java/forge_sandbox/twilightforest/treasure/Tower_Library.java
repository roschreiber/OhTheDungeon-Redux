/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest.treasure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;

/**
 *
 * @author shadow
 */
public class Tower_Library implements Treasure {

	private final static LootTable[] TABLES = { Bukkit.getLootTable(LootTables.STRONGHOLD_LIBRARY.getKey()),
			Bukkit.getLootTable(LootTables.SIMPLE_DUNGEON.getKey()),
			Bukkit.getLootTable(LootTables.SIMPLE_DUNGEON.getKey()),
			Bukkit.getLootTable(LootTables.STRONGHOLD_LIBRARY.getKey()),
			Bukkit.getLootTable(LootTables.SIMPLE_DUNGEON.getKey()),
			Bukkit.getLootTable(LootTables.SIMPLE_DUNGEON.getKey()),
			Bukkit.getLootTable(LootTables.STRONGHOLD_LIBRARY.getKey()),
			Bukkit.getLootTable(LootTables.SIMPLE_DUNGEON.getKey()),
			Bukkit.getLootTable(LootTables.SIMPLE_DUNGEON.getKey()),
			Bukkit.getLootTable(LootTables.END_CITY_TREASURE.getKey()), };

	@Override
	public List<ItemStack> getLoots(Random random, Location location) {
		LootTable table = TABLES[random.nextInt(TABLES.length)];
		LootContext.Builder builder = new LootContext.Builder(location);
		Collection<ItemStack> collection = table.populateLoot(random, builder.build());
		List<ItemStack> result = new ArrayList<>();
		for (ItemStack item : collection) {
			result.add(item);
		}

		return result;
	}
}
