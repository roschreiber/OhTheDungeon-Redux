/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import java.util.Random;
import org.bukkit.Material;

public class Town extends BaseGenerator {
    enum ChunkType
    {
        NULL,
        GRASS,
        ROAD,
        BUILDING,
    };

    public static ChunkType[][] chunkTypes = new ChunkType[256][256];

    //enum Direction{EAST, NORTH, WEST, SOUTH};

    @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random rand, int chunkX, int chunkZ) {
        int offX = chunkX + 128;
        int offZ = chunkZ + 128;
        
        offX = offX % 256;
        offX = offX + 256;
        offZ = offZ % 256;
        offZ = offZ + 256;

        ChunkType west = ChunkType.NULL;
        ChunkType east = ChunkType.NULL;
        ChunkType north = ChunkType.NULL;
        ChunkType south = ChunkType.NULL;

        if(offX > 0) west = chunkTypes[offX-1][offZ];
        if(offX < 255) east = chunkTypes[offX+1][offZ];
        if(offZ > 0) north = chunkTypes[offX][offZ-1];
        if(offZ == 0) south = chunkTypes[offX][offZ+1];

        AsyncChunk chunk = new AsyncChunk();
        int buildingHeight = 127;
        int floorHeight = 5;
        ChunkType type;
        double chance = rand.nextDouble();
        if(chance < 0.60)
            type = ChunkType.ROAD;
        else //if(chance < 0.60)
        {
            type = ChunkType.BUILDING;
            buildingHeight = randRange(rand,60/floorHeight,125/floorHeight)*5+2;
        }

        boolean makestair = true;

        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                for (int y = 0; y < buildingHeight; y++)
                {
                    //Lower layers
                    if(y <= 1)
                        chunk.setBlock(x, y, z, Material.BEDROCK);
                    else if(y < 20)
                        chunk.setBlock(x, y, z, Material.STONE);
                    else if(y < 50)
                        chunk.setBlock(x, y, z, Material.DIRT);
                    else if(y == 50)
                    {
                        chunk.setBlock(x, y, z, Material.GRASS_BLOCK);

                        //Generate roads
                        if(type == ChunkType.ROAD)
                        {
                            //Generate vertical road
                            /*if(x < 2 || z < 2); //Do nothing
                            else if(x < 4 || z < 4) chunk[index] = getId(Material.COBBLESTONE);
                            else if(x < 12 || z < 12) chunk[index] = getId(Material.STONE);
                            else if(x < 14 || z < 14) chunk[index] = getId(Material.COBBLESTONE);*/
                            chunk.setBlock(x, y, z, Material.OBSIDIAN);
                        }
                    }
                    else
                    {
                        //y = 1
                        //z = 128
                        //x = 2048
                        //x*2048+z*128+y
                        if(type == ChunkType.BUILDING)
                        {
                            if (makestair)
                            {
                                makestair = true;
                                for (int yy = floorHeight; yy < buildingHeight; yy+=floorHeight)
                                {
                                    chunk.setBlock(4, yy + 0, 5, Material.GRAY_TERRACOTTA);
                                    chunk.setBlock(5, yy + 0, 5, Material.GRAY_TERRACOTTA);
                                    chunk.setBlock(6, yy + 1, 5, Material.LIGHT_GRAY_TERRACOTTA);
                                    chunk.setBlock(7, yy + 1, 5, Material.GRAY_TERRACOTTA);
                                    chunk.setBlock(7, yy + 2, 4, Material.LIGHT_GRAY_TERRACOTTA);
                                    chunk.setBlock(7, yy + 2, 3, Material.GRAY_TERRACOTTA);
                                    chunk.setBlock(6, yy + 3, 3, Material.LIGHT_GRAY_TERRACOTTA);
                                    chunk.setBlock(5, yy + 3, 3, Material.GRAY_TERRACOTTA);
                                    chunk.setBlock(4, yy + 4, 3, Material.LIGHT_GRAY_TERRACOTTA);
                                    chunk.setBlock(3, yy + 4, 3, Material.GRAY_TERRACOTTA);
                                    chunk.setBlock(3, yy + 5, 4, Material.LIGHT_GRAY_TERRACOTTA);
                                    chunk.setBlock(3, yy + 5, 5, Material.GRAY_TERRACOTTA);
                                    
                                    for (int air = 1; air <= 3; air++)
                                    {
                                        chunk.setBlock(4, yy, 5, Material.AIR);
                                        chunk.setBlock(5, yy, 5, Material.AIR);
                                        chunk.setBlock(6, yy + 1, 5, Material.AIR);
                                        chunk.setBlock(7, yy + 1, 5, Material.AIR);
                                        chunk.setBlock(7, yy + 2, 4, Material.AIR);
                                        chunk.setBlock(7, yy + 2, 3, Material.AIR);
                                        chunk.setBlock(6, yy + 3, 3, Material.AIR);
                                        chunk.setBlock(5, yy + 3, 3, Material.AIR);
                                        chunk.setBlock(4, yy + 4, 3, Material.AIR);
                                        chunk.setBlock(3, yy + 4, 3, Material.AIR);
                                        chunk.setBlock(3, yy + 5, 4, Material.AIR);
                                        chunk.setBlock(3, yy + 5, 5, Material.AIR);
                                    }
                                }

                            }
                            if(x >= 2 && z >= 2 && x <= 13 && z <= 13)
                            {
                                Material buildingMaterial = Material.STONE;
                                //default is stone
                                if(chance < 0.80) buildingMaterial = Material.COBBLESTONE;
                                else if(chance < 0.81)buildingMaterial = Material.OAK_WOOD;
                                //chance of cobblestone
                                if(x == 2 || z == 2 || x == 13 || z == 13)
                                {
                                    //Outer walls
                                    chunk.setBlock(x, y, z, buildingMaterial);
                                    //walls
                                    if((y+2)%floorHeight < 2) chunk.setBlock(x, y, z, Material.GRASS_BLOCK);
                                    //windows
                                }
                                else
                                {
                                    //inside buildings
                                if((y%floorHeight) == 0) chunk.setBlock(x, y, z, buildingMaterial);
                                //floors
                                else if(((y+3)%floorHeight) == 0 && (x%floorHeight == 1 || z%floorHeight == 1) && ( x == 3 || z == 3 || x == 12 || z == 12)) chunk.setBlock(x, y, z, Material.TORCH);
                                //lighting
//                                else chunk[index] = 0;
                                }
                            }
                        }
                    }
                }
            }
        }

        return chunk;
    }

    int randRange(Random rand, int low, int high)
    {
        return rand.nextInt(high-low) + low;
    }
}
