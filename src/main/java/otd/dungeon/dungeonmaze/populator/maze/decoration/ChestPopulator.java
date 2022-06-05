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
package otd.dungeon.dungeonmaze.populator.maze.decoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.smoofy.Chest_Later;

public class ChestPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .03f;

	// TODO: Implement this!
	public static final double CHANCE_CHEST_ADDITION_EACH_LEVEL = -0.333; // to 1

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
		final int chestX = x + rand.nextInt(6) + 1;
		final int chestY = args.getFloorY() + 1;
		final int chestZ = z + rand.nextInt(6) + 1;

		if (!(world.getChunkType(chestX, chestY - 1, chestZ) == Material.AIR)) {
			Material chestBlock = world.getChunkType(chestX, chestY, chestZ);
			if (chestBlock == Material.AIR) {

				// Generate new inventory contents
				List<ItemStack> contents = generateChestContents(rand);
				world.setChunkType(chestX, chestY, chestZ, Material.CHEST);
				{
					Chest_Later later = new Chest_Later(chunkx * 16 + chestX, chestY, chunkz * 16 + chestZ, rand,
							contents);
					world.addLater(later);
				}
//				ChestUtils.addItemsToChest(chestBlock, contents, true, rand, world);
			} else if (chestBlock == Material.CHEST) {
				// The follow is for rare case when the chest is generate before the plugin does
				// the event
//                Chest chest = (Chest) chestBlock.getState();
				// Generate new inventory contents
				List<ItemStack> contents = generateChestContents(rand);
				// Call the chest generation event
//                    GenerationChestEvent event = new GenerationChestEvent(chestBlock, rand, contents, MazeStructureType.UNSTRUCTURE);
//                    Bukkit.getServer().getPluginManager().callEvent(event);
				{
					Chest_Later later = new Chest_Later(chunkx * 16 + chestX, chestY, chunkz * 16 + chestZ, rand,
							contents);
					world.addLater(later);
				}
//				ChestUtils.addItemsToChest(chestBlock, contents, true, rand, world);
			}
		}
	}

	private List<ItemStack> generateChestContents(Random random) {
		// TODO: Use class for this, to also add feature to re loot chests
		// Create a list to put the item stacks in
		List<ItemStack> items = new ArrayList<>();

		// Add items to the stack
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
			items.add(new ItemStack(Material.INK_SAC, 1));
		if (random.nextInt(100) < 5)
			items.add(new ItemStack(Material.CAKE, 1));
		if (random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COOKIE));
		if (random.nextInt(100) < 20)
			items.add(new ItemStack(Material.COOKIE, 5));

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
		}

		// Create a list of item contents with the right amount of items
		List<ItemStack> newContents = new ArrayList<>();
		for (int i = 0; i < itemCountInChest; i++)
			newContents.add(items.get(random.nextInt(items.size())));
		return newContents;
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