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
import otd.dungeon.dungeonmaze.populator.maze.MazeLayerBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeLayerBlockPopulatorArgs;
import otd.dungeon.dungeonmaze.util.NumberUtils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.World;
import otd.Main;
import otd.lib.api.SpawnerDecryAPI;
import otd.dungeon.dungeonmaze.util.ChestUtils;
import otd.dungeon.dungeonmaze.util.SpawnerUtils;

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
        final World world = args.getWorld();
        final Chunk chunk = args.getSourceChunk();
        final int x = 0;
        final int y = args.getY();
        final int z = 0;

        // Make sure the distance between the spawn chunk and the current chunk is allowed
        if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
            return;

        // Set this chunk as custom chunk
        // TODO: Flag the rooms used instead of the whole chunk!
        args.custom.add(Integer.toString(chunk.getX()) + "," + Integer.toString(chunk.getZ()));

        // Clear the room!
        for(int x2 = x; x2 < x + 15; x2 += 1)
            for(int y2 = y + 1; y2 <= y + (6 * 3) - 1; y2 += 1)
                for(int z2 = z; z2 < z + 15; z2 += 1)
                    chunk.getBlock(x2, y2, z2).setType(Material.AIR);
        // Floor
        for(int x2 = x; x2 < x + 15; x2 += 1)
            for(int y2 = y; y2 < y + 1; y2 += 1)
                for(int z2 = z; z2 < z + 15; z2 += 1)
                    chunk.getBlock(x2, y2, z2).setType(Material.OBSIDIAN);

        // Treasures
        chunk.getBlock(x + 7, y + 1, z + 7).setType(Material.GOLD_BLOCK);
        chunk.getBlock(x + 8, y + 1, z + 8).setType(Material.IRON_BLOCK);

        // Chest1
        chunk.getBlock(x + 7, y + 1, z + 8).setType(Material.CHEST);
        addItemsToChest(rand, (Chest) chunk.getBlock(x + 7, y + 1, z + 8).getState(), world);

        // Chest2
        chunk.getBlock(x + 8, y + 1, z + 7).setType(Material.CHEST);
        addItemsToChest(rand, (Chest) chunk.getBlock(x + 8, y + 1, z + 7).getState(), world);

        // Glass shields
        chunk.getBlock(x + 2, y + 1, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 2, y + 1, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 1, z + 2).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 1, z + 4).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 1, z + 11).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 1, z + 13).setType(Material.GLASS);
        chunk.getBlock(x + 4, y + 1, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 4, y + 1, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 11, y + 1, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 11, y + 1, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 1, z + 2).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 1, z + 4).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 1, z + 11).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 1, z + 13).setType(Material.GLASS);
        chunk.getBlock(x + 13, y + 1, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 13, y + 1, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 2, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 2, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 2, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 2, z + 12).setType(Material.GLASS);

        // Hull
        chunk.getBlock(x + 5, y + 1, z + 7).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 5, y + 1, z + 8).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 6, y + 1, z + 6).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 6, y + 1, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 6, y + 1, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 6, y + 1, z + 9).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 7, y + 1, z + 5).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 7, y + 1, z + 6).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 1, z + 9).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 1, z + 10).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 8, y + 1, z + 5).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 8, y + 1, z + 6).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 1, z + 9).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 1, z + 10).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 9, y + 1, z + 6).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 9, y + 1, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 9, y + 1, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 9, y + 1, z + 9).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 10, y + 1, z + 7).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 10, y + 1, z + 8).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 5, y + 2, z + 7).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 5, y + 2, z + 8).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 6, y + 2, z + 6).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 6, y + 2, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 6, y + 2, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 6, y + 2, z + 9).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 7, y + 2, z + 5).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 7, y + 2, z + 6).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 2, z + 9).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 2, z + 10).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 8, y + 2, z + 5).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 8, y + 2, z + 6).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 2, z + 9).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 2, z + 10).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 9, y + 2, z + 6).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 9, y + 2, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 9, y + 2, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 9, y + 2, z + 9).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 10, y + 2, z + 7).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 10, y + 2, z + 8).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 6, y + 3, z + 7).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 6, y + 3, z + 8).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 7, y + 3, z + 6).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 7, y + 3, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 3, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 3, z + 9).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 8, y + 3, z + 6).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 8, y + 3, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 3, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 3, z + 9).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 9, y + 3, z + 7).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 9, y + 3, z + 8).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 7, y + 4, z + 7).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 7, y + 4, z + 8).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 8, y + 4, z + 7).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 8, y + 4, z + 8).setType(Material.NETHER_BRICKS);

        boolean zombie = SpawnerUtils.isZombieAllowed(world);
        boolean pigzombie = SpawnerUtils.isPigZombieAllowed(world);
        boolean skeleton = SpawnerUtils.isSkeletonAllowed(world);
        // Core spawners
        if(SpawnerUtils.isGhastAllowed(world)) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 2, z + 7);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.GHAST);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(zombie) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 2, z + 8);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.ZOMBIE);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(pigzombie) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 2, z + 7);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            if(otd.Main.version == otd.MultiVersion.Version.V1_16_R1 
                    || otd.Main.version == otd.MultiVersion.Version.V1_16_R2
                    || otd.Main.version == otd.MultiVersion.Version.V1_16_R3) {
                spawner.setSpawnedType(EntityType.valueOf("ZOMBIFIED_PIGLIN"));
            } else {
                spawner.setSpawnedType(EntityType.valueOf("PIG_ZOMBIE"));
            }
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(pigzombie) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 2, z + 8);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            if(otd.Main.version == otd.MultiVersion.Version.V1_16_R1
                    || otd.Main.version == otd.MultiVersion.Version.V1_16_R2
                    || otd.Main.version == otd.MultiVersion.Version.V1_16_R3) {
                spawner.setSpawnedType(EntityType.valueOf("ZOMBIFIED_PIGLIN"));
            } else {
                spawner.setSpawnedType(EntityType.valueOf("PIG_ZOMBIE"));
            }
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(skeleton) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 3, z + 7);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.SKELETON);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(zombie) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 3, z + 8);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.ZOMBIE);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(pigzombie) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 3, z + 7);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            if(otd.Main.version == otd.MultiVersion.Version.V1_16_R1
                    || otd.Main.version == otd.MultiVersion.Version.V1_16_R2
                    || otd.Main.version == otd.MultiVersion.Version.V1_16_R3) {
                spawner.setSpawnedType(EntityType.valueOf("ZOMBIFIED_PIGLIN"));
            } else {
                spawner.setSpawnedType(EntityType.valueOf("PIG_ZOMBIE"));
            }
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(zombie) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 3, z + 8);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.ZOMBIE);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        //loose spawners
        if(zombie) {
            Block spawnerBlock = chunk.getBlock(x + 3, y + 1, z + 3);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.ZOMBIE);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(skeleton) {
            Block spawnerBlock = chunk.getBlock(x + 3, y + 1, z + 12);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.SKELETON);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(zombie) {
            Block spawnerBlock = chunk.getBlock(x + 12, y + 1, z + 3);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.ZOMBIE);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
        }

        if(SpawnerUtils.isSpiderAllowed(world)) {
            Block spawnerBlock = chunk.getBlock(x + 12, y + 1, z + 12);
            spawnerBlock.setType(Material.SPAWNER, true);
            BlockState blockState = spawnerBlock.getState();
            CreatureSpawner spawner = ((CreatureSpawner)blockState);
            spawner.setSpawnedType(EntityType.SPIDER);
            blockState.update();
            SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance);
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

    private void addItemsToChest(Random random, Chest chest, World world) {
        ChestUtils.addCustomLoots(chest, random, world);
        if(!ChestUtils.isBuiltInLootEnable(world)) return;
        // Create a list to put the chest items in
        List<ItemStack> items = new ArrayList<>();

        // Add the items to the list
        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.TORCH, 16));

        if(random.nextInt(100) < 40)
            items.add(new ItemStack(Material.TORCH, 20));

        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.ARROW, 24));

        if(random.nextInt(100) < 40)
            items.add(new ItemStack(Material.ARROW, 1));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.DIAMOND, 3));

        if(random.nextInt(100) < 50)
            items.add(new ItemStack(Material.IRON_INGOT, 3));

        if(random.nextInt(100) < 50)
            items.add(new ItemStack(Material.GOLD_INGOT, 3));

        if(random.nextInt(100) < 50)
            items.add(new ItemStack(Material.IRON_SWORD, 1));

        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.MUSHROOM_STEW, 1));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.IRON_HELMET, 1));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.IRON_CHESTPLATE, 1));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.IRON_LEGGINGS, 1));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.IRON_BOOTS, 1));

        if(random.nextInt(100) < 5)
            items.add(new ItemStack(Material.DIAMOND_HELMET, 1));

        if(random.nextInt(100) < 5)
            items.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));

        if(random.nextInt(100) < 5)
            items.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1));

        if(random.nextInt(100) < 5)
            items.add(new ItemStack(Material.DIAMOND_BOOTS, 1));

        if(random.nextInt(100) < 40)
            items.add(new ItemStack(Material.FLINT, 1));

        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.PORKCHOP, 1));

        if(random.nextInt(100) < 10)
            items.add(new ItemStack(Material.GOLDEN_APPLE, 1));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.REDSTONE, 7));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.CAKE, 1));

        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.COOKIE, 8));

        // Determine the number of items to add to the chest
        int itemCountInChest;
        switch(random.nextInt(8)) {
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
        for(int i = 0; i < itemCountInChest; i++)
            chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), items.get(random.nextInt(items.size())));

        // Update the chest
        chest.update();
    }

    @Override
    public float getLayerIterationsChance() {
        return LAYER_CHANCE;
    }
}