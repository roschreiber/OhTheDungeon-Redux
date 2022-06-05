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
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

//import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.smoofy.Chest_Later;

public class LibraryRoomPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 3;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .002f;

	public boolean const_room = true;

	@Override
	public boolean getConstRoom() {
		return const_room;
	}

	// TODO: Implement this feature!
	public static final double CHANCE_LIBRARY_ADDITION_EACH_LEVEL = -0.167; /* to 1 */

	private static final BlockData TORCH1 = Bukkit.createBlockData("minecraft:wall_torch[facing=east]");
	private static final BlockData TORCH2 = Bukkit.createBlockData("minecraft:wall_torch[facing=west]");
	private static final BlockData TORCH3 = Bukkit.createBlockData("minecraft:wall_torch[facing=south]");
	private static final BlockData TORCH4 = Bukkit.createBlockData("minecraft:wall_torch[facing=north]");

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
//		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Register the current room als constant room
		//// DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

		// stone floor in the bottom of the room
		for (int x2 = x + 1; x2 <= x + 6; x2 += 1)
			for (int z2 = z + 1; z2 <= z + 6; z2 += 1)
				world.setChunkType(x2, yFloor, z2, Material.STONE);

		// Cobblestone layer underneath the stone floor
		for (int x2 = x + 1; x2 <= x + 6; x2 += 1)
			for (int z2 = z + 1; z2 <= z + 6; z2 += 1)
				world.setChunkType(x2, yFloor - 1, z2, Material.COBBLESTONE);

		// Make stone walls on each side of the room
		for (int x2 = x + 1; x2 <= x + 6; x2 += 1)
			for (int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
				world.setChunkType(x2, y2, z, Material.STONE_BRICKS);
		for (int x2 = x + 1; x2 <= x + 6; x2 += 1)
			for (int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
				world.setChunkType(x2, y2, z + 7, Material.STONE_BRICKS);
		for (int z2 = z + 1; z2 <= z + 6; z2 += 1)
			for (int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
				world.setChunkType(x, y2, z2, Material.STONE_BRICKS);
		for (int z2 = z + 1; z2 <= z + 6; z2 += 1)
			for (int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
				world.setChunkType(x + 7, y2, z2, Material.STONE_BRICKS);

		// Generate some holes in the wall to make some kind of doors
		for (int x2 = x + 3; x2 <= x + 4; x2 += 1)
			for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
				world.setChunkType(x2, y2, z, Material.AIR);
		for (int x2 = x + 3; x2 <= x + 4; x2 += 1)
			for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
				world.setChunkType(x2, y2, z + 7, Material.AIR);
		for (int z2 = z + 3; z2 <= z + 4; z2 += 1)
			for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
				world.setChunkType(x, y2, z2, Material.AIR);
		for (int z2 = z + 3; z2 <= z + 4; z2 += 1)
			for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
				world.setChunkType(x + 7, y2, z2, Material.AIR);

		// Generate the bookshelves, one on each side
		for (int x2 = x + 5; x2 <= x + 6; x2 += 1)
			for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
				world.setChunkType(x2, y2, z + 1, Material.BOOKSHELF);
		for (int x2 = x + 1; x2 <= x + 2; x2 += 1)
			for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
				world.setChunkType(x2, y2, z + 6, Material.BOOKSHELF);
		for (int z2 = z + 1; z2 <= z + 2; z2 += 1)
			for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
				world.setChunkType(x + 1, y2, z2, Material.BOOKSHELF);
		for (int z2 = z + 5; z2 <= z + 6; z2 += 1)
			for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
				world.setChunkType(x + 6, y2, z2, Material.BOOKSHELF);

		/*
		 * // Make the two pilars - Change to enchant table for (int y2 = yFloor + 1; y2
		 * <= yFloor + 3; y2+=1) { c.getBlock(x + 3, y2, z + 4,Material.PUMPKIN);
		 * c.getBlock(x + 4, y2, z + 3,Material.PUMPKIN); }
		 */

		// Add enchant tables supports
		world.setChunkType(x + 3, yFloor + 1, z + 4, Material.BOOKSHELF);
		world.setChunkType(x + 4, yFloor + 1, z + 3, Material.BOOKSHELF);
		// Add the two enchant tables
		world.setChunkType(x + 3, yFloor + 2, z + 4, Material.ENCHANTING_TABLE);
		world.setChunkType(x + 4, yFloor + 2, z + 3, Material.ENCHANTING_TABLE);
		// Add the two chests
		world.setChunkType(x + 3, yFloor + 1, z + 3, Material.CHEST);

//        // Call the Chest generation event
//        GenerationChestEvent event = new GenerationChestEvent(world.setChunkType(x + 3, yFloor + 1, z + 3), rand, genChestContent(rand), MazeStructureType.LIBRARY_ROOM);
//        Bukkit.getServer().getPluginManager().callEvent(event);

//		Block chest = world.setChunkType(x + 3, yFloor + 1, z + 3);
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 3, yFloor + 1, chunkz * 16 + z + 3, rand,
					genChestContent(rand));
			world.addLater(later);
		}
		// Add the contents to the chest
//		ChestUtils.addItemsToChest(chest, genChestContent(rand), true, rand, world);

		world.setChunkType(x + 4, yFloor + 1, z + 4, Material.CHEST);
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 4, yFloor + 1, chunkz * 16 + z + 4, rand,
					genChestContent(rand));
			world.addLater(later);
		}

//		Block chest2 = world.setChunkType(x + 4, yFloor + 1, z + 4);

//        // Call the Chest generation event
//        GenerationChestEvent event2 = new GenerationChestEvent(world.setChunkType(x + 4, yFloor + 1, z + 4), rand, genChestContent(rand), MazeStructureType.LIBRARY_ROOM);
//        Bukkit.getServer().getPluginManager().callEvent(event2);

		// Add the contents to the chest
//		ChestUtils.addItemsToChest(chest2, genChestContent(rand), true, rand, world);

		// Add 4 lanterns on each side of the room near the book shelves
		world.setChunkData(x + 2, yFloor + 2, z + 1, TORCH3);
		world.setChunkData(x + 6, yFloor + 2, z + 2, TORCH2);
		world.setChunkData(x + 1, yFloor + 2, z + 5, TORCH1);
		world.setChunkData(x + 5, yFloor + 2, z + 6, TORCH4);
	}

	public List<ItemStack> genChestContent(Random random) {
		// Create a list to put all the chest contents in
		List<ItemStack> items = new ArrayList<>();

		// Add the items to the list
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.TORCH, 16));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.TORCH, 20));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.ARROW, 24));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.ARROW, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.DIAMOND, 3));
		if (random.nextInt(100) < 50)
			items.add(new ItemStack(Material.IRON_INGOT, 3));
		if (random.nextInt(100) < 50)
			items.add(new ItemStack(Material.GOLD_INGOT, 3));
		if (random.nextInt(100) < 50)
			items.add(new ItemStack(Material.IRON_SWORD, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.MUSHROOM_STEW, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.IRON_HELMET, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.IRON_CHESTPLATE, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.IRON_LEGGINGS, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.IRON_BOOTS, 1));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.DIAMOND_HELMET, 1));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.DIAMOND_BOOTS, 1));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.FLINT, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.PORKCHOP, 1));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.GOLDEN_APPLE, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.REDSTONE, 7));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.CAKE, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COOKIE, 8));

		// Determine the number of items to put in the chest
		int itemCountInChest;
		switch (random.nextInt(8)) {
		case 0:
			itemCountInChest = 2;
			break;
		case 1:
			itemCountInChest = 2;
			break;
		case 2:
			itemCountInChest = 3;
			break;
		case 3:
			itemCountInChest = 3;
			break;
		case 4:
			itemCountInChest = 4;
			break;
		case 5:
			itemCountInChest = 4;
			break;
		case 6:
			itemCountInChest = 4;
			break;
		case 7:
			itemCountInChest = 5;
			break;
		default:
			itemCountInChest = 4;
			break;
		}

		// Create the result list
		List<ItemStack> result = new ArrayList<>();

		// Add the selected items randomly
		for (int i = 0; i < itemCountInChest; i++)
			result.add(items.get(random.nextInt(items.size())));

		// Return the result
		return result;
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