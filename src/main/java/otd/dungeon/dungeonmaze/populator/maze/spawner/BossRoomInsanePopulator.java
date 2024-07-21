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
package otd.dungeon.dungeonmaze.populator.maze.spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import otd.dungeon.dungeonmaze.populator.maze.MazeLayerBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeLayerBlockPopulatorArgs;
import otd.dungeon.dungeonmaze.util.NumberUtils;
import otd.dungeon.dungeonmaze.util.SpawnerUtils;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.smoofy.Chest_Later;
import otd.lib.async.later.smoofy.Spawner_Later;

public class BossRoomInsanePopulator extends MazeLayerBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 3;
	private static final float LAYER_CHANCE = .001f;

	/** Populator constants. */
	private static final double SPAWN_DISTANCE_MIN = 10; // Chunks

	@Override
	public void populateLayer(MazeLayerBlockPopulatorArgs args) {
		final Random rand = args.getRandom();
		final AsyncWorldEditor world = args.getWorld();
		final int x = 0;
		final int y = args.getY();
		final int z = 0;
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		// Make sure the distance between the spawn chunk and the current chunk is
		// allowed
		if (NumberUtils.distanceFromZero(chunkx, chunkz) < SPAWN_DISTANCE_MIN)
			return;

		// Set this chunk as custom chunk
		// TODO: Flag the rooms used instead of the whole chunk!
		args.custom.add(Integer.toString(chunkx) + "," + Integer.toString(chunkz));

		// Clear the room!
		for (int x2 = x; x2 < x + 15; x2 += 1)
			for (int y2 = y + 1; y2 <= y + (6 * 3) - 1; y2 += 1)
				for (int z2 = z; z2 < z + 15; z2 += 1)
					world.setChunkType(x2, y2, z2, Material.AIR);
		// Floor
		for (int x2 = x; x2 < x + 15; x2 += 1)
			for (int y2 = y; y2 < y + 1; y2 += 1)
				for (int z2 = z; z2 < z + 15; z2 += 1)
					world.setChunkType(x2, y2, z2, Material.OBSIDIAN);

		// Treasures
		world.setChunkType(x + 7, y + 1, z + 7, Material.GOLD_BLOCK);
		world.setChunkType(x + 8, y + 1, z + 8, Material.IRON_BLOCK);

		// Chest1
		world.setChunkType(x + 7, y + 1, z + 8, Material.CHEST);
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 7, y + 1, chunkz * 16 + z + 8, rand,
					getItemsToChest(rand, world));
			world.addLater(later);
		}
//		addItemsToChest(rand, (Chest) world.setChunkType(x + 7, y + 1, z + 8).getState(), world);

		// Chest2
		world.setChunkType(x + 8, y + 1, z + 7, Material.CHEST);
		{
			Chest_Later later = new Chest_Later(chunkx * 16 + x + 8, y + 1, chunkz * 16 + z + 7, rand,
					getItemsToChest(rand, world));
			world.addLater(later);
		}
//		addItemsToChest(rand, (Chest) world.setChunkType(x + 8, y + 1, z + 7).getState(), world);

		// Glass shields
		world.setChunkType(x + 2, y + 1, z + 3, Material.GLASS);
		world.setChunkType(x + 2, y + 1, z + 12, Material.GLASS);
		world.setChunkType(x + 3, y + 1, z + 2, Material.GLASS);
		world.setChunkType(x + 3, y + 1, z + 4, Material.GLASS);
		world.setChunkType(x + 3, y + 1, z + 11, Material.GLASS);
		world.setChunkType(x + 3, y + 1, z + 13, Material.GLASS);
		world.setChunkType(x + 4, y + 1, z + 3, Material.GLASS);
		world.setChunkType(x + 4, y + 1, z + 12, Material.GLASS);
		world.setChunkType(x + 11, y + 1, z + 3, Material.GLASS);
		world.setChunkType(x + 11, y + 1, z + 12, Material.GLASS);
		world.setChunkType(x + 12, y + 1, z + 2, Material.GLASS);
		world.setChunkType(x + 12, y + 1, z + 4, Material.GLASS);
		world.setChunkType(x + 12, y + 1, z + 11, Material.GLASS);
		world.setChunkType(x + 12, y + 1, z + 13, Material.GLASS);
		world.setChunkType(x + 13, y + 1, z + 3, Material.GLASS);
		world.setChunkType(x + 13, y + 1, z + 12, Material.GLASS);
		world.setChunkType(x + 3, y + 2, z + 3, Material.GLASS);
		world.setChunkType(x + 3, y + 2, z + 12, Material.GLASS);
		world.setChunkType(x + 12, y + 2, z + 3, Material.GLASS);
		world.setChunkType(x + 12, y + 2, z + 12, Material.GLASS);

		// Hull
		world.setChunkType(x + 5, y + 1, z + 7, Material.NETHER_BRICKS);
		world.setChunkType(x + 5, y + 1, z + 8, Material.NETHER_BRICKS);
		world.setChunkType(x + 6, y + 1, z + 6, Material.NETHER_BRICKS);
		world.setChunkType(x + 6, y + 1, z + 7, Material.SOUL_SAND);
		world.setChunkType(x + 6, y + 1, z + 8, Material.SOUL_SAND);
		world.setChunkType(x + 6, y + 1, z + 9, Material.NETHER_BRICKS);
		world.setChunkType(x + 7, y + 1, z + 5, Material.NETHER_BRICKS);
		world.setChunkType(x + 7, y + 1, z + 6, Material.SOUL_SAND);
		world.setChunkType(x + 7, y + 1, z + 9, Material.SOUL_SAND);
		world.setChunkType(x + 7, y + 1, z + 10, Material.NETHER_BRICKS);
		world.setChunkType(x + 8, y + 1, z + 5, Material.NETHER_BRICKS);
		world.setChunkType(x + 8, y + 1, z + 6, Material.SOUL_SAND);
		world.setChunkType(x + 8, y + 1, z + 9, Material.SOUL_SAND);
		world.setChunkType(x + 8, y + 1, z + 10, Material.NETHER_BRICKS);
		world.setChunkType(x + 9, y + 1, z + 6, Material.NETHER_BRICKS);
		world.setChunkType(x + 9, y + 1, z + 7, Material.SOUL_SAND);
		world.setChunkType(x + 9, y + 1, z + 8, Material.SOUL_SAND);
		world.setChunkType(x + 9, y + 1, z + 9, Material.NETHER_BRICKS);
		world.setChunkType(x + 10, y + 1, z + 7, Material.NETHER_BRICKS);
		world.setChunkType(x + 10, y + 1, z + 8, Material.NETHER_BRICKS);
		world.setChunkType(x + 5, y + 2, z + 7, Material.NETHER_BRICKS);
		world.setChunkType(x + 5, y + 2, z + 8, Material.NETHER_BRICKS);
		world.setChunkType(x + 6, y + 2, z + 6, Material.NETHER_BRICKS);
		world.setChunkType(x + 6, y + 2, z + 7, Material.SOUL_SAND);
		world.setChunkType(x + 6, y + 2, z + 8, Material.SOUL_SAND);
		world.setChunkType(x + 6, y + 2, z + 9, Material.NETHER_BRICKS);
		world.setChunkType(x + 7, y + 2, z + 5, Material.NETHER_BRICKS);
		world.setChunkType(x + 7, y + 2, z + 6, Material.SOUL_SAND);
		world.setChunkType(x + 7, y + 2, z + 9, Material.SOUL_SAND);
		world.setChunkType(x + 7, y + 2, z + 10, Material.NETHER_BRICKS);
		world.setChunkType(x + 8, y + 2, z + 5, Material.NETHER_BRICKS);
		world.setChunkType(x + 8, y + 2, z + 6, Material.SOUL_SAND);
		world.setChunkType(x + 8, y + 2, z + 9, Material.SOUL_SAND);
		world.setChunkType(x + 8, y + 2, z + 10, Material.NETHER_BRICKS);
		world.setChunkType(x + 9, y + 2, z + 6, Material.NETHER_BRICKS);
		world.setChunkType(x + 9, y + 2, z + 7, Material.SOUL_SAND);
		world.setChunkType(x + 9, y + 2, z + 8, Material.SOUL_SAND);
		world.setChunkType(x + 9, y + 2, z + 9, Material.NETHER_BRICKS);
		world.setChunkType(x + 10, y + 2, z + 7, Material.NETHER_BRICKS);
		world.setChunkType(x + 10, y + 2, z + 8, Material.NETHER_BRICKS);
		world.setChunkType(x + 6, y + 3, z + 7, Material.NETHER_BRICKS);
		world.setChunkType(x + 6, y + 3, z + 8, Material.NETHER_BRICKS);
		world.setChunkType(x + 7, y + 3, z + 6, Material.NETHER_BRICKS);
		world.setChunkType(x + 7, y + 3, z + 7, Material.SOUL_SAND);
		world.setChunkType(x + 7, y + 3, z + 8, Material.SOUL_SAND);
		world.setChunkType(x + 7, y + 3, z + 9, Material.NETHER_BRICKS);
		world.setChunkType(x + 8, y + 3, z + 6, Material.NETHER_BRICKS);
		world.setChunkType(x + 8, y + 3, z + 7, Material.SOUL_SAND);
		world.setChunkType(x + 8, y + 3, z + 8, Material.SOUL_SAND);
		world.setChunkType(x + 8, y + 3, z + 9, Material.NETHER_BRICKS);
		world.setChunkType(x + 9, y + 3, z + 7, Material.NETHER_BRICKS);
		world.setChunkType(x + 9, y + 3, z + 8, Material.NETHER_BRICKS);
		world.setChunkType(x + 7, y + 4, z + 7, Material.NETHER_BRICKS);
		world.setChunkType(x + 7, y + 4, z + 8, Material.NETHER_BRICKS);
		world.setChunkType(x + 8, y + 4, z + 7, Material.NETHER_BRICKS);
		world.setChunkType(x + 8, y + 4, z + 8, Material.NETHER_BRICKS);

		boolean zombie = SpawnerUtils.isZombieAllowed(world.getWorld());
		boolean pigzombie = SpawnerUtils.isPigZombieAllowed(world.getWorld());
		boolean skeleton = SpawnerUtils.isSkeletonAllowed(world.getWorld());
		// Core spawners
		if (SpawnerUtils.isGhastAllowed(world.getWorld())) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 7, y + 2, chunkz * 16 + z + 7, EntityType.GHAST);
			world.addLater(later);
		}

		if (zombie) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 7, y + 2, chunkz * 16 + z + 8, EntityType.ZOMBIE);
			world.addLater(later);
		}

		if (pigzombie) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 8, y + 2, chunkz * 16 + z + 7, EntityType.ZOMBIFIED_PIGLIN);
			world.addLater(later);
		}

		if (pigzombie) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 8, y + 2, chunkz * 16 + z + 8, EntityType.ZOMBIFIED_PIGLIN);
			world.addLater(later);
		}

		if (skeleton) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 7, y + 3, chunkz * 16 + z + 7, EntityType.SKELETON);
			world.addLater(later);
		}

		if (zombie) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 7, y + 3, chunkz * 16 + z + 8, EntityType.ZOMBIE);
			world.addLater(later);
		}

		if (pigzombie) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 8, y + 3, chunkz * 16 + z + 7, EntityType.ZOMBIFIED_PIGLIN);
			world.addLater(later);
		}

		if (zombie) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 8, y + 3, chunkz * 16 + z + 8, EntityType.ZOMBIE);
			world.addLater(later);
		}

		// loose spawners
		if (zombie) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 3, y + 1, chunkz * 16 + z + 3, EntityType.ZOMBIE);
			world.addLater(later);
		}

		if (skeleton) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 3, y + 1, chunkz * 16 + z + 12,
					EntityType.SKELETON);
			world.addLater(later);
		}

		if (zombie) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 12, y + 1, chunkz * 16 + z + 3,
					EntityType.ZOMBIE);
			world.addLater(later);
		}

		if (SpawnerUtils.isSpiderAllowed(world.getWorld())) {
			Spawner_Later later = new Spawner_Later(chunkx * 16 + x + 12, y + 1, chunkz * 16 + z + 12,
					EntityType.SPIDER);
			world.addLater(later);
		}
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

	private List<ItemStack> getItemsToChest(Random random, AsyncWorldEditor world) {
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
			items.add(new ItemStack(Material.REDSTONE, 7));

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
			break;
		}

		// Add the selected items to a random place inside the chest
		for (int i = 0; i < itemCountInChest; i++)
			list.add(items.get(random.nextInt(items.size())));

		return list;
	}

	@Override
	public float getLayerIterationsChance() {
		return LAYER_CHANCE;
	}
}