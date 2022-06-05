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

public class ArmoryRoomPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .001f;

	private static final BlockData CHEST0 = Bukkit.createBlockData("minecraft:chest[facing=east,type=left]");
	private static final BlockData CHEST1 = Bukkit.createBlockData("minecraft:chest[facing=east,type=right]");
	private static final BlockData CHEST2 = Bukkit.createBlockData("minecraft:chest[facing=north,type=left]");
	private static final BlockData CHEST3 = Bukkit.createBlockData("minecraft:chest[facing=north,type=right]");
	private static final BlockData CHEST4 = Bukkit.createBlockData("minecraft:chest[facing=west,type=right]");
	private static final BlockData CHEST5 = Bukkit.createBlockData("minecraft:chest[facing=west,type=left]");

	public boolean const_room = true;

	@Override
	public boolean getConstRoom() {
		return const_room;
	}

//        private static final BlockData DOOR0 =
//                Bukkit.createBlockData("minecraft:iron_door[hinge=right,half=lower,powered=false,facing=east,open=false]");
//        private static final BlockData DOOR8 =
//                Bukkit.createBlockData("minecraft:iron_door[hinge=right,half=upper,powered=false,facing=east,open=false]");
//         private static final BlockData DOOR3 =
//                Bukkit.createBlockData("minecraft:iron_door[hinge=left,half=lower,powered=false,facing=east,open=false]");
//        private static final BlockData DOOR11 =
//                Bukkit.createBlockData("minecraft:iron_door[hinge=left,half=upper,powered=false,facing=east,open=false]");

	// TODO: Armory room still not used, finish it and put it into Dungeon Maze

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		AsyncWorldEditor world = args.getWorld();
		Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int floorOffset = args.getFloorOffset();
		final int z = args.getRoomChunkZ();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Register the current room as constant room
		//// DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

		// Blocks
		for (int x2 = x; x2 <= x + 7; x2 += 1)
			for (int y2 = y; y2 <= y + 5; y2++)
				for (int z2 = z; z2 <= z + 7; z2 += 1)
					world.setChunkType(x2, y2 + floorOffset, z2, Material.COBBLESTONE);

		// Bedrock
		for (int x2 = x + 1; x2 <= x + 6; x2 += 1)
			for (int y2 = y; y2 <= y + 4; y2++)
				for (int z2 = z + 1; z2 <= z + 6; z2 += 1)
					world.setChunkType(x2, y2 + floorOffset, z2, Material.OBSIDIAN);

		// Air
		for (int x2 = x + 2; x2 <= x + 5; x2 += 1)
			for (int y2 = y + 1; y2 <= y + 3; y2++)
				for (int z2 = z + 2; z2 <= z + 5; z2 += 1)
					world.setChunkType(x2, y2 + floorOffset, z2, Material.AIR);
		for (int x2 = x + 1; x2 <= x + 6; x2 += 1)
			for (int y2 = y + 1; y2 <= y + 5; y2++)
				world.setChunkType(x2, y2 + floorOffset, z, Material.AIR);

		// Pumpkins
		world.setChunkType(x + 2, yFloor + 1, z + 2, Material.JACK_O_LANTERN);
		world.setChunkType(x + 2, yFloor + 1, z + 5, Material.JACK_O_LANTERN);
		world.setChunkType(x + 5, yFloor + 1, z + 2, Material.JACK_O_LANTERN);
		world.setChunkType(x + 5, yFloor + 1, z + 5, Material.JACK_O_LANTERN);
		world.setChunkType(x + 2, yFloor + 2, z + 2, Material.JACK_O_LANTERN);
		world.setChunkType(x + 2, yFloor + 2, z + 5, Material.JACK_O_LANTERN);
		world.setChunkType(x + 5, yFloor + 2, z + 2, Material.JACK_O_LANTERN);
		world.setChunkType(x + 5, yFloor + 2, z + 5, Material.JACK_O_LANTERN);
		world.setChunkType(x + 2, yFloor + 3, z + 2, Material.JACK_O_LANTERN);
		world.setChunkType(x + 2, yFloor + 3, z + 5, Material.JACK_O_LANTERN);
		world.setChunkType(x + 5, yFloor + 3, z + 2, Material.JACK_O_LANTERN);
		world.setChunkType(x + 5, yFloor + 3, z + 5, Material.JACK_O_LANTERN);

		// Chests //TODO
//        world.setChunkType(x + 2, yFloor + 1, z + 3,Material.CHEST);
//        world.setChunkType(x + 2, yFloor + 1, z + 4,Material.CHEST);
		world.setChunkData(x + 2, yFloor + 1, z + 3, CHEST0);
		world.setChunkData(x + 2, yFloor + 1, z + 4, CHEST1);
		world.setChunkData(x + 3, yFloor + 1, z + 5, CHEST2);
		world.setChunkData(x + 4, yFloor + 1, z + 5, CHEST3);
		world.setChunkData(x + 5, yFloor + 1, z + 3, CHEST4);
		world.setChunkData(x + 5, yFloor + 1, z + 4, CHEST5);
//        world.setChunkType(x + 3, yFloor + 1, z + 5,Material.CHEST);
//        world.setChunkType(x + 4, yFloor + 1, z + 5,Material.CHEST);
//        world.setChunkType(x + 5, yFloor + 1, z + 3,Material.CHEST);
//        world.setChunkType(x + 5, yFloor + 1, z + 4,Material.CHEST);

		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 2, yFloor + 1, chunkz * 16 + z + 3, rand,
					getItemsToChest(rand, world));
			world.addLater(later);
		}
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 2, yFloor + 1, chunkz * 16 + z + 4, rand,
					getItemsToChest(rand, world));
			world.addLater(later);
		}
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 3, yFloor + 1, chunkz * 16 + z + 5, rand,
					getItemsToChest(rand, world));
			world.addLater(later);
		}
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 4, yFloor + 1, chunkz * 16 + z + 5, rand,
					getItemsToChest(rand, world));
			world.addLater(later);
		}
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 5, yFloor + 1, chunkz * 16 + z + 3, rand,
					getItemsToChest(rand, world));
			world.addLater(later);
		}
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 5, yFloor + 1, chunkz * 16 + z + 4, rand,
					getItemsToChest(rand, world));
			world.addLater(later);
		}

		world.setChunkType(x + 3, yFloor + 1, z + 1, Material.IRON_BARS);// TODO_DOOR
		world.setChunkType(x + 4, yFloor + 1, z + 1, Material.IRON_BARS);
		world.setChunkType(x + 3, yFloor + 2, z + 1, Material.IRON_BARS);
		world.setChunkType(x + 4, yFloor + 2, z + 1, Material.IRON_BARS);

		// Iron doors
//        world.setChunkType(x + 3, yFloor + 1, z + 1,DOOR0);//TODO_DOOR
//        world.setChunkType(x + 4, yFloor + 1, z + 1,DOOR3);
//        world.setChunkType(x + 3, yFloor + 2, z + 1,DOOR8);
//        world.setChunkType(x + 4, yFloor + 2, z + 1,DOOR11);
	}

	public List<ItemStack> getItemsToChest(Random random, AsyncWorldEditor world) {
		List<ItemStack> list = new ArrayList<>();
		// Create a list to put the chest items in
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
			items.add(new ItemStack(Material.RED_SANDSTONE_STAIRS, 7));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.CAKE, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COOKIE, 8));

		// Determine the number of items to add to the chest
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
		}

		// Add the selected items to a random place inside the chest
		for (int i = 0; i < itemCountInChest; i++) {
			list.add(items.get(random.nextInt(items.size())));
		}
		return list;
		// Update the chest
//		chest.update();
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