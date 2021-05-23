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

//import zhehe.com.timvisee.dungeonmaze.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import otd.dungeon.dungeonmaze.util.NumberUtils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;


//import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
//import com.timvisee.dungeonmaze.event.generation.GenerationSpawnerEvent;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.dungeon.dungeonmaze.util.ChestUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.BlockData;
import otd.Main;
import otd.lib.api.SpawnerDecryAPI;
import otd.dungeon.dungeonmaze.util.SpawnerUtils;

public class BlazeSpawnerRoomPopulator extends MazeRoomBlockPopulator {

	// TODO: Fix the treasure chests not spawning!
        public boolean const_room = true;
        @Override
        public boolean getConstRoom() {
            return const_room;
        }

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 4;
	private static final float ROOM_CHANCE = .002f;
        
        private static final BlockData NETHER_BRICK_STAIRS0 = 
                Bukkit.createBlockData("minecraft:nether_brick_stairs[half=bottom,shape=straight,facing=east]");
        private static final BlockData NETHER_BRICK_STAIRS1 =
                Bukkit.createBlockData("minecraft:nether_brick_stairs[half=bottom,shape=straight,facing=west]");
        private static final BlockData NETHER_BRICK_STAIRS2 =
                Bukkit.createBlockData("minecraft:nether_brick_stairs[half=bottom,shape=straight,facing=south]");
        private static final BlockData NETHER_BRICK_STAIRS3 =
                Bukkit.createBlockData("minecraft:nether_brick_stairs[half=bottom,shape=straight,facing=north]");

    /** Populator constants. */
	private static final double SPAWN_DISTANCE_MIN = 5; // Chunks

    // TODO: Implement this!
	public static final double CHANCE_SPANWER_ROOM_ADDITION_EACH_LEVEL = -0.167; /* to 1 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getRoomChunkZ();
		
		// Make sure the distance between the spawn and the current chunk is allowed
		if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
			return;

        // Register the current room as constant room
        ////DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        // Netherbrick floor in the bottom of the room
        for(int xx = x; xx <= x + 7; xx += 1)
            for(int zz = z; zz <= z + 7; zz += 1)
                chunk.getBlock(xx, yFloor, zz).setType(Material.NETHER_BRICKS);

        // Cobblestone layer underneath the stone floor
        for(int xx = x; xx <= x + 7; xx += 1)
            for(int zz = z + 1; zz <= z + 6; zz += 1)
                chunk.getBlock(xx, yFloor - 1, zz).setType(Material.COBBLESTONE);

        // Break out the walls and things inside the room
        for (int xx = 0; xx < 8; xx++)
            for (int yy = yFloor + 1; yy < yCeiling; yy++)
                for(int zz = 0; zz < 8; zz++)
                    chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);

        // Generate corners
        for(int yy = yFloor + 1; yy < yCeiling; yy++) {
            chunk.getBlock(x, yy, z).setType(Material.NETHER_BRICKS);
            chunk.getBlock(x + 7, yy, z).setType(Material.NETHER_BRICKS);
            chunk.getBlock(x, yy, z + 7).setType(Material.NETHER_BRICKS);
            chunk.getBlock(x + 7, yy, z + 7).setType(Material.NETHER_BRICKS);
        }

        // Generate fences in the corners
        for(int yy = yFloor + 1; yy < yCeiling; yy++) {
            chunk.getBlock(x + 1, yy, z).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x, yy, z + 1).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 6, yy, z).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 7, yy, z + 1).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 1, yy, z + 7).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x, yy, z + 6).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 6, yy, z + 7).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 7, yy, z + 6).setType(Material.NETHER_BRICK_FENCE);
        }

        // Generate platform in the middle
        for (int xx=x + 2; xx <= x + 5; xx+=1)
            for (int zz=z + 2; zz <= z + 5; zz+=1)
                chunk.getBlock(xx, yFloor + 1, zz).setType(Material.NETHER_BRICKS);

        // Generate stairs off the platform
        chunk.getBlock(x + 3, yFloor + 1, z + 2).setBlockData(NETHER_BRICK_STAIRS2);
        chunk.getBlock(x + 4, yFloor + 1, z + 2).setBlockData(NETHER_BRICK_STAIRS2);

        chunk.getBlock(x + 3, yFloor + 1, z + 5).setBlockData(NETHER_BRICK_STAIRS3);
        chunk.getBlock(x + 4, yFloor + 1, z + 5).setBlockData(NETHER_BRICK_STAIRS3);

        chunk.getBlock(x + 2, yFloor + 1, z + 3).setBlockData(NETHER_BRICK_STAIRS0);
        chunk.getBlock(x + 2, yFloor + 1, z + 4).setBlockData(NETHER_BRICK_STAIRS0);

        chunk.getBlock(x + 5, yFloor + 1, z + 3).setBlockData(NETHER_BRICK_STAIRS1);
        chunk.getBlock(x + 5, yFloor + 1, z + 4).setBlockData(NETHER_BRICK_STAIRS1);

        // Generate poles on the platform
        chunk.getBlock(x + 2, yFloor + 2, z + 2).setType(Material.NETHER_BRICK_FENCE);
        chunk.getBlock(x + 5, yFloor + 2, z + 2).setType(Material.NETHER_BRICK_FENCE);
        chunk.getBlock(x + 2, yFloor + 2, z + 5).setType(Material.NETHER_BRICK_FENCE);
        chunk.getBlock(x + 5, yFloor + 2, z + 5).setType(Material.NETHER_BRICK_FENCE);

        // Generate the spawner
        if(SpawnerUtils.isBlazeAllowed(world)) {
            int spawnerX = x + 3 + rand.nextInt(2);
            int spawnerY = yFloor + 2;
            int spawnerZ = z + 3 + rand.nextInt(2);
            Block spawnerBlock = chunk.getBlock(spawnerX, spawnerY, spawnerZ);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.BLAZE);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        // Generate hidden content/resources underneath the platform
        // Generate a list of chest contents
        List<ItemStack> contents = generateChestContents(rand);
        Block chest1 = chunk.getBlock(x + 3, yFloor, z + 3);
        chest1.setType(Material.CHEST, true);

        ChestUtils.addItemsToChest(chest1, contents, true, rand, world);

        // Generate a list of chest contents
        List<ItemStack> contents2 = generateChestContents(rand);
        Block chest2 = chunk.getBlock(x + 4, yFloor, z + 4);
        chest2.setType(Material.CHEST, true);

        ChestUtils.addItemsToChest(chest2, contents2, true, rand, world);
    }
	
	private List<ItemStack> generateChestContents(Random random) {
		// Create a list to put the item stacks in
		List<ItemStack> items = new ArrayList<>();

		// Add items to the stack
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.TORCH, 4));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.TORCH, 8));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.TORCH, 12));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.APPLE, 1));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.ARROW, 16));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.ARROW, 24));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.DIAMOND, 1));
		if(random.nextInt(100) < 50)
			items.add(new ItemStack(Material.IRON_INGOT, 1));
		if(random.nextInt(100) < 60)
			items.add(new ItemStack(Material.GOLD_INGOT, 1));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_SWORD, 1));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.WOODEN_SWORD, 1));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.STONE_SWORD, 1));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.WHEAT, 1));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.WHEAT, 2));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.WHEAT, 3));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.BREAD, 1));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_HELMET, 1));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_BOOTS, 1));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_HELMET, 1));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_CHESTPLATE, 1));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_LEGGINGS, 1));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_BOOTS, 1));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.FLINT, 3));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.FLINT, 5));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.FLINT, 7));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.BEEF, 1));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.PORKCHOP, 1));
		if(random.nextInt(100) < 15)
			items.add(new ItemStack(Material.REDSTONE, 5));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.REDSTONE, 8));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.REDSTONE, 13));
		if(random.nextInt(100) < 3)
			items.add(new ItemStack(Material.REDSTONE, 21));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.COMPASS, 1));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COD, 1));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.COOKED_COD, 1));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.INK_SAC, 1));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.CAKE, 1));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COOKIE, 3));
		if(random.nextInt(100) < 20)
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
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
}