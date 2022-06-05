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
package otd.dungeon.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.smoofy.Furnace_Later;

public class GreatFurnaceRoomPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 5;
	private static final float ROOM_CHANCE = .001f;

	public boolean const_room = true;

	@Override
	public boolean getConstRoom() {
		return const_room;
	}

	private static final BlockData FURNACE2 = Bukkit.createBlockData("minecraft:furnace[facing=north,lit=false]");
	private static final BlockData FURNACE3 = Bukkit.createBlockData("minecraft:furnace[facing=south,lit=false]");

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
//		final int y = args.getChunkY();
		final int y = args.getFloorY();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Register the room as constant room
		//// DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

		// Floor
		for (int x2 = x; x2 < x + 8; x2 += 1)
			for (int z2 = z; z2 < z + 8; z2 += 1)
				world.setChunkType(x2, y + 1, z2, Material.STONE);

		// Change the layer below the stone floor to cobblestone
		for (int x2 = x; x2 < x + 8; x2++)
			for (int z2 = z; z2 < z + 8; z2++)
				world.setChunkType(x2, y, z2, Material.COBBLESTONE);

		// Pillar1
		for (int y2 = y + 1; y2 <= y + 5; y2 += 1)
			world.setChunkType(x + 1, y2, z + 1, Material.COBBLESTONE);

		// Pillar2
		for (int y2 = y + 1; y2 <= y + 5; y2 += 1)
			world.setChunkType(x + 7, y2, z + 1, Material.COBBLESTONE);

		// Pillar3
		for (int y2 = y + 1; y2 <= y + 5; y2 += 1)
			world.setChunkType(x + 1, y2, z + 7, Material.COBBLESTONE);

		// Pillar4
		for (int y2 = y + 1; y2 <= y + 5; y2 += 1)
			world.setChunkType(x + 7, y2, z + 7, Material.COBBLESTONE);

		// Furnace base
		world.setChunkData(x + 2, y + 2, z + 2, FURNACE2);
		{
			ItemStack item = getItemsToFurnace(rand, world.getWorld());
			if (item != null) {
				Furnace_Later later = new Furnace_Later(chunkx * 16 + x + 2, y + 2, chunkz * 16 + z + 2, item);
				world.addLater(later);
			}
		}
//		addItemsToFurnace(rand, (Furnace) world.setChunkType(x + 2, y + 2, z + 2).getState(), world);
		world.setChunkType(x + 3, y + 2, z + 2, Material.GLASS);
		world.setChunkType(x + 4, y + 2, z + 2, Material.GLASS);
		world.setChunkData(x + 5, y + 2, z + 2, FURNACE2);
		{
			ItemStack item = getItemsToFurnace(rand, world.getWorld());
			if (item != null) {
				Furnace_Later later = new Furnace_Later(chunkx * 16 + x + 5, y + 2, chunkz * 16 + z + 2, item);
				world.addLater(later);
			}
		}
//		addItemsToFurnace(rand, (Furnace) world.setChunkType(x + 5, y + 2, z + 2).getState(), world);
		world.setChunkType(x + 2, y + 2, z + 3, Material.GLASS);
		world.setChunkType(x + 3, y + 2, z + 3, Material.LAVA);
		world.setChunkType(x + 4, y + 2, z + 3, Material.LAVA);
		world.setChunkType(x + 5, y + 2, z + 3, Material.GLASS);
		world.setChunkType(x + 2, y + 2, z + 4, Material.GLASS);
		world.setChunkType(x + 3, y + 2, z + 4, Material.LAVA);
		world.setChunkType(x + 4, y + 2, z + 4, Material.LAVA);
		world.setChunkType(x + 5, y + 2, z + 4, Material.GLASS);
		world.setChunkData(x + 2, y + 2, z + 5, FURNACE3);
		{
			ItemStack item = getItemsToFurnace(rand, world.getWorld());
			if (item != null) {
				Furnace_Later later = new Furnace_Later(chunkx * 16 + x + 2, y + 2, chunkz * 16 + z + 5, item);
				world.addLater(later);
			}
		}
//		addItemsToFurnace(rand, (Furnace) world.setChunkType(x + 2, y + 2, z + 5).getState(), world);
		world.setChunkType(x + 3, y + 2, z + 5, Material.GLASS);
		world.setChunkType(x + 4, y + 2, z + 5, Material.GLASS);
		world.setChunkData(x + 5, y + 2, z + 5, FURNACE3);
		{
			ItemStack item = getItemsToFurnace(rand, world.getWorld());
			if (item != null) {
				Furnace_Later later = new Furnace_Later(chunkx * 16 + x + 5, y + 2, chunkz * 16 + z + 5, item);
				world.addLater(later);
			}
		}
//		addItemsToFurnace(rand, (Furnace) world.setChunkType(x + 5, y + 2, z + 5).getState(), world);

		// Furnace pipe
		for (int x2 = x + 3; x2 <= x + 4; x2 += 1)
			for (int y2 = y + 3; y2 <= y + 5; y2 += 1)
				for (int z2 = z + 3; z2 <= z + 4; z2 += 1)
					world.setChunkType(x2, y2, z2, Material.BRICKS);
		if (world.getChunkType(x + 3, y + 6, z + 3) == Material.AIR)
			for (int x2 = x + 3; x2 <= x + 4; x2 += 1)
				for (int z2 = z + 3; z2 <= z + 4; z2 += 1)
					world.setChunkType(x2, y + 6, z2, Material.BRICKS);
	}

	public ItemStack getItemsToFurnace(Random random, World world) {
		// Create a list to put the furnace items in
		List<ItemStack> items = new ArrayList<>();

		// Add the items to the list
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.GOLD_BLOCK, 1));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.IRON_BLOCK, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.BRICK, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COAL, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.CHARCOAL, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.IRON_INGOT, 2));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.IRON_INGOT, 4));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.GOLD_INGOT, 2));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.GOLD_INGOT, 4));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.BREAD, 1));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.BUCKET, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COOKED_CHICKEN, 2));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.COOKED_CHICKEN, 4));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.FLINT, 3));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.FLINT, 5));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.PORKCHOP, 1));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.COOKED_COD, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.ENDER_PEARL, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.BLAZE_ROD, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.GHAST_TEAR, 1));
		if (random.nextInt(100) < 45)
			items.add(new ItemStack(Material.GOLD_NUGGET, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.NETHER_WART, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.SPIDER_EYE, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.BLAZE_POWDER, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.MAGMA_CREAM, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.ENDER_EYE, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.GLISTERING_MELON_SLICE, 1));

		// Add the selected items into the furnace
		if (random.nextInt(100) < 60)
			return items.get(random.nextInt(items.size()));
		return null;

		// Update the furnace
//		furnace.update();
	}

	@Override
	public float getRoomChance() {
		return ROOM_CHANCE;
	}

	/**
	 * Get the minimum layer
	 * 
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}

	/**
	 * Get the maximum layer
	 * 
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
}