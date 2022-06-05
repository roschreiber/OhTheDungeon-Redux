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

//import zhehe.com.timvisee.dungeonmaze.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.smoofy.Chest_Later;
import otd.lib.async.later.smoofy.Furnace_Later;

public class AbandonedDefenceCastleRoomPopulator extends MazeRoomBlockPopulator {

	// TODO: Use material enums instead of ID's due to ID deprecation by Mojang

	/** General populator constants. */
	private static final int LAYER_MIN = 2;
	private static final int LAYER_MAX = 6;
	private static final float ROOM_CHANCE = .001f;

	private static final BlockData[] CAKES = {
//            Bukkit.createBlockData("minecraft:cake[bites=0]"),
			Bukkit.createBlockData("minecraft:cake[bites=1]"), Bukkit.createBlockData("minecraft:cake[bites=2]"),
			Bukkit.createBlockData("minecraft:cake[bites=3]"), Bukkit.createBlockData("minecraft:cake[bites=4]"),
			Bukkit.createBlockData("minecraft:cake[bites=5]"),
//            Bukkit.createBlockData("minecraft:cake[bites=6]"),
	};
	private static final BlockData CHEST4 = Bukkit.createBlockData("minecraft:chest[facing=west,type=right]");
	private static final BlockData CHEST5 = Bukkit.createBlockData("minecraft:chest[facing=west,type=left]");

	public boolean const_room = true;

	@Override
	public boolean getConstRoom() {
		return const_room;
	}

	/** Populator constants. */
	private static final float MOSS_CHANCE = .7f;
	private static final int MOSS_ITERATIONS = 80;
	private static final float CRACKED_CHANCE = .7f;
	private static final int CRACKED_ITERATIONS = 80;

	private static final BlockData FURNACE4 = Bukkit.createBlockData("minecraft:furnace[facing=west,lit=false]");
	private static final BlockData TORCH5 = Bukkit.createBlockData(Material.TORCH);
	private static final BlockData LADDER2 = Bukkit.createBlockData("minecraft:ladder[facing=north]");
//        private static final BlockData CHEST2 =
//                Bukkit.createBlockData("minecraft:chest[facing=north,type=single]");

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int floorOffset = args.getFloorOffset();
		final int yFloor = args.getFloorY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Register the room as constant room
		//// DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

		// Break out the original walls
		for (int xx = 1; xx < 7; xx++) {
			for (int yy = yFloor + 1; yy <= yCeiling - 1; yy++) {
				world.setChunkType(x + xx, yy, z, Material.AIR);
				world.setChunkType(x + xx, yy, z + 7, Material.AIR);
				world.setChunkType(x, yy, z + xx, Material.AIR);
				world.setChunkType(x + 7, yy, z + xx, Material.AIR);
			}
		}

		// Walls
		for (int xx = 1; xx < 7; xx++) {
			for (int yy = floorOffset + 1; yy <= floorOffset + 2; yy++) {
				world.setChunkType(x + xx, y + yy, z + 1, Material.STONE_BRICKS);
				world.setChunkType(x + xx, y + yy, z + 6, Material.STONE_BRICKS);
				world.setChunkType(x + 1, y + yy, z + xx, Material.STONE_BRICKS);
				world.setChunkType(x + 6, y + yy, z + xx, Material.STONE_BRICKS);
			}
		}

		// Generate merlons
		for (int xx = 0; xx < 7; xx++) {
			world.setChunkType(x + xx, yFloor + 3, z, Material.STONE_BRICKS);
			world.setChunkType(x + xx, yFloor + 3, z + 7, Material.STONE_BRICKS);
			world.setChunkType(x, yFloor + 3, z + xx, Material.STONE_BRICKS);
			world.setChunkType(x + 7, yFloor + 3, z + xx, Material.STONE_BRICKS);
		}

		world.setChunkType(x, yFloor + 4, z + 1, Material.STONE_BRICK_SLAB);
		world.setChunkType(x, yFloor + 4, z + 3, Material.STONE_BRICK_SLAB);
		world.setChunkType(x, yFloor + 4, z + 5, Material.STONE_BRICK_SLAB);
		world.setChunkType(x + 7, yFloor + 4, z + 2, Material.STONE_BRICK_SLAB);
		world.setChunkType(x + 7, yFloor + 4, z + 4, Material.STONE_BRICK_SLAB);
		world.setChunkType(x + 7, yFloor + 4, z + 6, Material.STONE_BRICK_SLAB);
		world.setChunkType(x + 1, yFloor + 4, z, Material.STONE_BRICK_SLAB);
		world.setChunkType(x + 3, yFloor + 4, z, Material.STONE_BRICK_SLAB);
		world.setChunkType(x + 5, yFloor + 4, z, Material.STONE_BRICK_SLAB);
		world.setChunkType(x + 2, yFloor + 4, z + 7, Material.STONE_BRICK_SLAB);
		world.setChunkType(x + 4, yFloor + 4, z + 7, Material.STONE_BRICK_SLAB);
		world.setChunkType(x + 6, yFloor + 4, z + 7, Material.STONE_BRICK_SLAB);

		// Place torches
		world.setChunkData(x + 1, yFloor + 3, z + 1, TORCH5);
		world.setChunkData(x + 1, yFloor + 3, z + 6, TORCH5);
		world.setChunkData(x + 6, yFloor + 3, z + 1, TORCH5);
		world.setChunkData(x + 6, yFloor + 3, z + 6, TORCH5);

		// Place ladders
		world.setChunkData(x + 2, yFloor + 1, z + 5, LADDER2);
		world.setChunkData(x + 2, yFloor + 2, z + 5, LADDER2);

		// Place crafting table, chests and furnaces
		world.setChunkType(x + 2, yFloor + 1, z + 2, Material.CRAFTING_TABLE);
		world.setChunkData(x + 5, yFloor + 1, z + 2, CHEST4);
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 5, yFloor + 1, chunkz * 16 + z + 2, rand,
					genChestContent(rand));
			world.addLater(later);
		}

		world.setChunkData(x + 5, yFloor + 1, z + 3, CHEST5);
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 5, yFloor + 1, chunkz * 16 + z + 3, rand,
					genChestContent(rand));
			world.addLater(later);
		}

		world.setChunkData(x + 5, yFloor + 1, z + 4, FURNACE4);
		world.setChunkData(x + 5, yFloor + 1, z + 5, FURNACE4);
		{
			ItemStack item = getItemsToFurnace(rand, world);
			if (item != null) {
				Furnace_Later later = new Furnace_Later(chunkx * 16 + x + 5, yFloor + 1, chunkz * 16 + z + 4, item);
				world.addLater(later);
			}
		}
		{
			ItemStack item = getItemsToFurnace(rand, world);
			if (item != null) {
				Furnace_Later later = new Furnace_Later(chunkx * 16 + x + 5, yFloor + 1, chunkz * 16 + z + 5, item);
				world.addLater(later);
			}
		}

		// Place cake (with random pieces eaten)
		world.setChunkData(x + 5, yFloor + 2, z + 5, CAKES[rand.nextInt(CAKES.length)]);
//        world.setChunkType(x + 5, yFloor + 2, z + 5,Material.CAKE);//TODO_CAKE
//        world.setChunkType(x + 5, yFloor + 2, z + 5).setData((byte) rand.nextInt(4));

		// TODO: Place painting

		// Place some cobweb
		world.setChunkType(x + 2, yFloor + 2, z + 2, Material.COBWEB);
		world.setChunkType(x + 3, yFloor + 1, z + 2, Material.COBWEB);
		world.setChunkType(x + 6, yFloor + 3, z + 6, Material.COBWEB);
		world.setChunkType(x + 6, yFloor + 4, z + 6, Material.COBWEB);
		world.setChunkType(x + 5, yFloor + 3, z + 6, Material.COBWEB);
		world.setChunkType(x + 6, yFloor + 3, z + 5, Material.COBWEB);
		world.setChunkType(x, yFloor + 4, z + 6, Material.COBWEB);

		// Add some moss and cracked stone bricks
		for (int i = 0; i < MOSS_ITERATIONS; i++) {
			if (rand.nextInt(100) < MOSS_CHANCE) {

				int sx = x + rand.nextInt(8);
				int sy = rand.nextInt((y + 6) - y + 1) + y;
				int sz = z + rand.nextInt(8);
				Material type = world.getChunkType(sx, sy, sz);
				if (type == Material.COBBLESTONE)
					world.setChunkType(sx, sy, sz, Material.MOSSY_COBBLESTONE);

				if (type == Material.STONE_BRICKS)
					world.setChunkType(sx, sy, sz, Material.MOSSY_STONE_BRICKS);
			}
		}

		for (int i = 0; i < CRACKED_ITERATIONS; i++) {
			if (rand.nextInt(100) < CRACKED_CHANCE) {

				int sx = x + rand.nextInt(8);
				int sy = rand.nextInt((y + 6) - y + 1) + y;
				int sz = z + rand.nextInt(8);

				if (world.getChunkType(sx, sy, sz) == Material.STONE_BRICKS)
					world.setChunkType(sx, sy, sz, Material.CRACKED_STONE_BRICKS);
			}
		}
	}

	private ItemStack getItemsToFurnace(Random random, AsyncWorldEditor world) {
		// Create a list to put the items in
		List<ItemStack> items = new ArrayList<>();

		// Put the items in the list
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.GOLD_BLOCK, 1));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.IRON_LEGGINGS, 1));
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
		if (random.nextInt(100) < 70)
			return items.get(random.nextInt(items.size()));
		return null;

		// Update the furnace
//		furnace.update();
	}

	private List<ItemStack> genChestContent(Random random) {
		// Create a list to put the chest items in
		List<ItemStack> items = new ArrayList<>();

		// Put the items in the list
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.TORCH, 4));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.TORCH, 8));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.TORCH, 12));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.APPLE, 1));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.ARROW, 16));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.ARROW, 24));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.DIAMOND, 1));
		if (random.nextInt(100) < 50)
			items.add(new ItemStack(Material.IRON_INGOT, 1));
		if (random.nextInt(100) < 60)
			items.add(new ItemStack(Material.GOLD_INGOT, 1));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_SWORD, 1));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.WOODEN_SWORD, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.STONE_SWORD, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.WHEAT, 1));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.WHEAT, 2));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.WHEAT, 3));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.BREAD, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_HELMET, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_BOOTS, 1));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		if (random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_HELMET, 1));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_CHESTPLATE, 1));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_LEGGINGS, 1));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_BOOTS, 1));
		if (random.nextInt(100) < 30)
			items.add(new ItemStack(Material.FLINT, 3));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.FLINT, 5));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.FLINT, 7));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.BEEF, 1));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.PORKCHOP, 1));
		if (random.nextInt(100) < 15)
			items.add(new ItemStack(Material.REDSTONE, 5));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.REDSTONE, 8));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.REDSTONE, 13));
		if (random.nextInt(100) < 3)
			items.add(new ItemStack(Material.REDSTONE, 21));
		if (random.nextInt(100) < 10)
			items.add(new ItemStack(Material.COMPASS, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COD, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.COOKED_COD, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.COOKED_COD, 1));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.INK_SAC, 1));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.CAKE, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COOKIE, 3));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.COOKIE, 5));

		// Determine how many items to put in the chest
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
			itemCountInChest = 3;
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
			itemCountInChest = 3;
			break;
		}

		// Create a list of results
		List<ItemStack> result = new ArrayList<>();

		// Add the selected items randomly
		for (int i = 0; i < itemCountInChest; i++)
			result.add(items.get(random.nextInt(items.size())));

		// Return the list of results
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