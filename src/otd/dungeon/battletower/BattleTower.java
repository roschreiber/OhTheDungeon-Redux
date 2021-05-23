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
package otd.dungeon.battletower;

import otd.dungeon.battletower.TreasureList.ItemStackNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import otd.Main;
import otd.lib.api.SpawnerDecryAPI;
import otd.config.EnumType.ChestType;
import otd.config.LootNode;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;

public class BattleTower
{
    public static void generate(World world, Random random, int ix, int jy, int kz, int towerchoice, boolean underground)
    {
        TowerTypes towerChosen = TowerTypes.values()[towerchoice];

        Material towerWallBlockMaterial = towerChosen.getWallBlockMaterial();
        Material towerLightBlockMaterial = towerChosen.getLightBlockMaterial();
        BlockData towerFloorBlockData = towerChosen.getFloorBlockData();

        int startingHeight = underground ? Math.max(jy - 70, 15) : jy - 6;
        int maximumHeight = underground ? jy + 7 : 120;

        int floor = 1;
        boolean topFloor = false;
        int builderHeight = startingHeight;
        
        List<Location> loc = new ArrayList<>();
        List<Material> mat = new ArrayList<>();
        
        for (; builderHeight < maximumHeight; builderHeight += 7) // builderHeight jumps floors
        {
            if (builderHeight + 7 >= maximumHeight)
            {
                topFloor = true;
            }

            for (int floorIterator = 0; floorIterator < 7; floorIterator++) // build each floor height block till next floor
            {
                if (floor == 1 && floorIterator < 4) // initial floor
                {
                    floorIterator = 4;
                }
                for (int xIterator = -7; xIterator < 7; xIterator++) // do each X
                {
                    for (int zIterator = -7; zIterator < 7; zIterator++) // do each Z
                    {
                        int iCurrent = xIterator + ix;
                        int jCurrent = floorIterator + builderHeight;
                        int zCurrent = zIterator + kz;

                        if (zIterator == -7) // last row, 14
                        {
                            if (xIterator > -5 && xIterator < 4) // rear outer wall
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                            }
                            continue;
                        }
                        if (zIterator == -6 || zIterator == -5) // rows 12 and 13
                        {
                            if (xIterator == -5 || xIterator == 4) // outer wall parts
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                                continue;
                            }
                            if (zIterator == -6) // row 13 extra
                            {
                                if (xIterator == (floorIterator + 1) % 7 - 3) // stairwell!!
                                {
                                    if (!(underground && floor == 1))
                                    {
                                        Directional dir = (Directional) Bukkit.createBlockData(towerChosen.getStairBlockMaterial());
                                        dir.setFacing(BlockFace.EAST);
                                        world.getBlockAt(iCurrent, jCurrent, zCurrent).setBlockData(dir, false);
                                    }
                                    if (floorIterator == 5)
                                    {
                                        world.getBlockAt(iCurrent - 7, jCurrent, zCurrent).setBlockData(towerFloorBlockData, false);
                                    }
                                    if (floorIterator == 6 && topFloor) // top ledge part
                                    {
                                        buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                                    }
                                    continue;
                                }
                                if (xIterator < 4 && xIterator > -5) // tower insides
                                {
                                    world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                                }
                                continue;
                            }
                            if (zIterator != -5 || xIterator <= -5 || xIterator >= 5) // outside tower
                            {
                                continue;
                            }
                            if (floorIterator != 0 && floorIterator != 6 || xIterator != -4 && xIterator != 3)
                            {
                                if (floorIterator == 5 && (xIterator == 3 || xIterator == -4))
                                {
                                    buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockData);
                                }
                                else
                                {
                                    buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator); // under stairwell
                                }
                            }
                            else
                            {
                                world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                            }
                            continue;
                        }
                        if (zIterator == -4 || zIterator == -3 || zIterator == 2 || zIterator == 3) // rows 11, 10, 5, 4
                        {
                            if (xIterator == -6 || xIterator == 5) // outer wall parts
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                                continue;
                            }
                            if (xIterator <= -6 || xIterator >= 5) // outside tower
                            {
                                continue;
                            }
                            if (floorIterator == 5)
                            {
                                buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockData);
                                continue;
                            }
                            if (world.getBlockAt(iCurrent, jCurrent, zCurrent).getType()!= Material.CHEST) // tower inside space
                            {
                                world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                            }
                            continue;
                        }
                        if (zIterator > -3 && zIterator < 2) // rows 10 to 5
                        {
                            if (xIterator == -7 || xIterator == 6)
                            {
                                if (floorIterator < 0 || floorIterator > 3 || ((xIterator != -7 && xIterator != 6) || underground) || zIterator != -1 && zIterator != 0) // wall, short of window
                                {
                                    buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                                }
                                else
                                {
                                    world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                                }
                                continue;
                            }
                            if (xIterator <= -7 || xIterator >= 6)
                            {
                                continue;
                            }
                            if (floorIterator == 5)
                            {
                                buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockData);
                            }
                            else
                            {
                                world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                            }
                            continue;
                        }
                        if (zIterator == 4) // row 3
                        {
                            if (xIterator == -5 || xIterator == 4)
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                                continue;
                            }
                            if (xIterator <= -5 || xIterator >= 4)
                            {
                                continue;
                            }
                            if (floorIterator == 5)
                            {
                                buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockData);
                            }
                            else
                            {
                                world.getBlockAt(iCurrent, jCurrent, zCurrent).setType(Material.AIR, false);
                            }
                            continue;
                        }
                        if (zIterator == 5) // row 2
                        {
                            if (xIterator == -4 || xIterator == -3 || xIterator == 2 || xIterator == 3)
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                                continue;
                            }
                            if (xIterator <= -3 || xIterator >= 2)
                            {
                                continue;
                            }
                            if (floorIterator == 5)
                            {
                                buildFloorPiece(world, iCurrent, jCurrent, zCurrent, towerFloorBlockData);
                            }
                            else
                            {
                                buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                            }
                            continue;
                        }
                        if (zIterator != 6 || xIterator <= -3 || xIterator >= 2)
                        {
                            continue;
                        }
                        if (floorIterator < 0 || floorIterator > 3 || xIterator != -1 && xIterator != 0)
                        {
                            buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                        }
                        else
                        {
                            buildWallPiece(world, iCurrent, jCurrent, zCurrent, towerWallBlockMaterial, floor, floorIterator);
                        }
                    }

                }
            }

            if (floor == 2)
            {
                world.getBlockAt(ix + 3, builderHeight, kz - 5).setType(towerWallBlockMaterial, false);
                world.getBlockAt(ix + 3, builderHeight - 1, kz - 5).setType(towerWallBlockMaterial, false);
            }
            if ((!underground && topFloor) || (underground && floor == 1))
            {
                if (towerChosen != TowerTypes.Null)
                {
                    world.spawnEntity(new Location(world, ix + 0.5D, builderHeight + 6, kz + 0.5D), EntityType.WITHER_SKELETON);
                    world.spawnEntity(new Location(world, ix + 0.5D, builderHeight + 6, kz + 0.5D), EntityType.WITHER_SKELETON);
                    world.spawnEntity(new Location(world, ix + 0.5D, builderHeight + 6, kz + 0.5D), EntityType.WITHER_SKELETON);
                }
            }
            else
            {
                if (towerChosen != TowerTypes.Null)
                {
                    try {
                        Block block = world.getBlockAt(ix + 2, builderHeight + 6, kz + 2);
                        block.setType(Material.SPAWNER, true);
                        CreatureSpawner tileentitymobspawner = ((CreatureSpawner)block.getState());
                        tileentitymobspawner.setSpawnedType(getMobType(random));
                        tileentitymobspawner.update();
                        SpawnerDecryAPI.setSpawnerDecry(block, Main.instance);

                        block = world.getBlockAt(ix - 3, builderHeight + 6, kz + 2);
                        block.setType(Material.SPAWNER, true);
                        tileentitymobspawner = (CreatureSpawner) block.getState();
                        tileentitymobspawner.setSpawnedType(getMobType(random));
                        tileentitymobspawner.update();
                        SpawnerDecryAPI.setSpawnerDecry(block, Main.instance);
                    } catch(Exception ex) {
                        Block block = world.getBlockAt(ix + 2, builderHeight + 6, kz + 2);
                        block.setType(Material.AIR, true);
                    }
                }
                else
                {
                    world.getBlockAt(ix + 2, builderHeight + 6, kz + 2).setType(Material.AIR, false);
                    world.getBlockAt(ix - 3, builderHeight + 6, kz + 2).setType(Material.AIR, false);
                }
            }
            // chest pedestal
            world.getBlockAt(ix, builderHeight + 6, kz + 3).setBlockData(towerFloorBlockData, false);
            world.getBlockAt(ix - 1, builderHeight + 6, kz + 3).setBlockData(towerFloorBlockData, false);

            if (builderHeight + 56 >= 120 && floor == 1)
            {
                floor = 2;
            }
            if (towerChosen != TowerTypes.Null) {
                boolean best_chest = false;
                if(!underground && topFloor) best_chest = true;
                if(underground && floor == 1) best_chest = true;
                Material material1, material2;
                if(!best_chest) {
                    material1 = TreasureList.treasure_block[random.nextInt(TreasureList.treasure_block.length)];
                    material2 = TreasureList.treasure_block[random.nextInt(TreasureList.treasure_block.length)];
                } else {
                    String world_name = world.getName();
                    boolean box = true;
                    if(WorldConfig.wc.dict.containsKey(world_name)) {
                        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world_name);
                        if(swc.battletower.chest == ChestType.CHEST) box = false;
                    }
                    
                    if(box) material1 = Material.SHULKER_BOX;
                    else material1 = Material.CHEST;
                    material2 = TreasureList.top_treasure_block[random.nextInt(TreasureList.top_treasure_block.length)];
                }
                loc.add(new Location(world, ix, builderHeight + 7, kz + 3));
                loc.add(new Location(world, ix - 1, builderHeight + 7, kz + 3));
                mat.add(material1);
                mat.add(material2);
            }

            // move lights builder a bit higher, to support non-opaque lights such as lamps
            if (towerLightBlockMaterial == Material.TORCH)
            {
                world.getBlockAt(ix + 3,  builderHeight+2,  kz - 6).setBlockData(BlockConst.TORCH, false);
                world.getBlockAt(ix - 4,  builderHeight+2,  kz - 6).setBlockData(BlockConst.TORCH, false);
                world.getBlockAt(ix + 1,  builderHeight+2,  kz - 4).setBlockData(BlockConst.TORCH, false);
                world.getBlockAt(ix - 2,  builderHeight+2,  kz - 4).setBlockData(BlockConst.TORCH, false);
            }
            else
            {
                world.getBlockAt(ix + 3,  builderHeight+2,  kz - 6).setType(towerLightBlockMaterial, false);
                world.getBlockAt(ix - 4,  builderHeight+2,  kz - 6).setType(towerLightBlockMaterial, false);
                world.getBlockAt(ix + 1,  builderHeight+2,  kz - 4).setType(towerLightBlockMaterial, false);
                world.getBlockAt(ix - 2,  builderHeight+2,  kz - 4).setType(towerLightBlockMaterial, false);
            }

            if (towerChosen != TowerTypes.Null)
            {
                for (int l3 = 0; l3 < (floor * 4 + towerChosen.ordinal()) - 8 && !topFloor; l3++) // random hole poker
                {
                    int k4 = 5 - random.nextInt(12);
                    int k5 = builderHeight + 5;
                    int j6 = 5 - random.nextInt(10);
                    if (j6 < -2 && k4 < 4 && k4 > -5 && k4 != 1 && k4 != -2)
                    {
                        continue;
                    }
                    k4 += ix;
                    j6 += kz;
                    if (world.getBlockAt(k4, k5, j6).getType()== towerFloorBlockData.getMaterial() && world.getBlockAt(k4, k5 + 1, j6).getType()!= Material.SPAWNER)
                    {
                        world.getBlockAt(k4, k5, j6).setType(Material.AIR, false);
                    }
                }
            }

            floor++;
        }
        
        int len = loc.size();
        for(int i = 0; i < len; i++) {
            Location l = loc.get(i);
            Material m = mat.get(i);
            world.getBlockAt(l).setType(m, true);
            
            if(m == Material.SHULKER_BOX || m == Material.CHEST) {
                    Block block = world.getBlockAt(l);
                    Inventory inv;
                    if(m == Material.SHULKER_BOX) {
                        ShulkerBox sb = (ShulkerBox) block.getState();
                        inv = sb.getInventory();
                    } else {
                        Chest ch = (Chest) block.getState();
                        inv = ch.getInventory();
                    }
                    String world_name = world.getName();
                    boolean builtin = true;
                    if(WorldConfig.wc.dict.containsKey(world_name)) {
                        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world_name);
                        if(!swc.battletower.builtinLoot) builtin = false;
                    }
                    if(builtin) {
                        for(ItemStackNode isn : TreasureList.TOP) {
                            if(random.nextDouble() < isn.chance) {
                                int amount = isn.min + random.nextInt(isn.max - isn.min + 1);
                                ItemStack is = isn.is.clone();
                                is.setAmount(amount);
                                inv.addItem(is);
                            }
                        }
                    }
                    if(WorldConfig.wc.dict.containsKey(world_name)) {
                        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world_name);
                        for(LootNode ln : swc.battletower.loots) {
                            if(random.nextDouble() < ln.chance) {
                                ItemStack is = ln.getItem();
                                int amount = ln.min + random.nextInt(ln.max - ln.min + 1);
                                is.setAmount(amount);
                                inv.addItem(is);
                            }
                        }
                    }
            }
        }
    }

    private static void buildFloorPiece(World world, int i, int j, int k, BlockData towerFloorBlockID)
    {
        world.getBlockAt(i, j, k).setBlockData(towerFloorBlockID, false);
    }

    private static void buildWallPiece(World world, int i, int j, int k, Material towerWallBlockID, int floor, int floorIterator)
    {
        world.getBlockAt(i, j, k).setType(towerWallBlockID, false);
        if (floor == 1 && floorIterator == 4)
        {
            fillTowerBaseToGround(world, i, j, k, towerWallBlockID);
        }
    }

    private static void fillTowerBaseToGround(World world, int i, int j, int k, Material blocktype)
    {
        int y = j - 1;
        while (y > 0 && !world.getBlockAt(i, y, k).getType().isSolid())
        {
            world.getBlockAt(i, y, k).setType(blocktype, true);
            y--;
        }
    }

    private static EntityType getMobType(Random random)
    {
        switch (random.nextInt(10))
        {
        case 0:
        case 1:
        case 2:
        {
            return EntityType.SKELETON;
        }
        case 3:
        case 4:
        case 5:
        case 6:
        {
            return EntityType.ZOMBIE;
        }
        case 7:
        case 8:
        {
            return EntityType.SPIDER;
        }
        case 9:
        {
            return EntityType.CAVE_SPIDER;
        }
        default:
            return EntityType.ZOMBIE;
        }
    }
}

