/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class Maxi extends BaseGenerator {
    private final int stoneThickness; // how thick is the stone bit
    private final int dirtThickness; // how thick is the dirt bit
    private final int waterLevel; // how thick is the water bit

    private final double horizontalFactor;
    private final double verticalFactor;

    private final double oddsForATree = 0.010;

    private int blocksPerBlock = 8;
    private final int xBlockHeight;
    private final int zBlockHeight;

    private SimplexNoiseGenerator noiseMaker;
    private long seed;
    
    private final static int chunksBlockWidth = 16;
    
    private void initNoise(long s) {
        if(s == seed) return;
        seed = s;
        noiseMaker = new SimplexNoiseGenerator(seed);
    }

    public Maxi() {
        seed = -1;
        initNoise(seed + 1);

        blocksPerBlock = 4;

        horizontalFactor = blocksPerBlock * 20.0;
        verticalFactor = 7.0 * (8.0 / (double) blocksPerBlock);

        xBlockHeight = chunksBlockWidth / blocksPerBlock;
        zBlockHeight = chunksBlockWidth / blocksPerBlock;

        stoneThickness = 1;
        dirtThickness = 1;
        waterLevel = (int) verticalFactor - stoneThickness - dirtThickness + 1;
    }
    
    public class InitialBlocks {
        public AsyncChunk data;

        public static final int chunksBlockWidth = 16;

        public InitialBlocks(AsyncChunk chunkData, int aChunkX, int aChunkZ) {
            super();
            data = chunkData;
        }

        public void setBlock(int x, int y, int z, Material material) {
            data.setBlock(x, y, z, material);
        }

        public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
            for (int x = x1; x < x2; x++) {
                for (int z = z1; z < z2; z++) {
                    for (int y = y1; y < y2; y++)
                        setBlock(x, y, z, material);
                }
            }
        }
    }
    
    @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random random, int chunkx, int chunkz) {
        initNoise(seed);
        InitialBlocks ib = new InitialBlocks(new AsyncChunk(), chunkx, chunkz);
        generateChunk(ib, random, chunkx, chunkz);
        
        return ib.data;
    }

    private int getGroundSurfaceY(int chunkX, int chunkZ, int blockX, int blockZ) {
        double x = chunkX * blocksPerBlock + blockX;
        double z = chunkZ * blocksPerBlock + blockZ;
        double noiseLevel = noiseMaker.noise(x / horizontalFactor, z / horizontalFactor);
        return stoneThickness + dirtThickness + waterLevel + NoiseGenerator.floor(noiseLevel * verticalFactor);
    }

//    private boolean ifATree(Random random) {
//        return random.nextDouble() < oddsForATree;
//    }

    public void generateChunk(InitialBlocks chunk, Random random, int chunkX, int chunkZ) {
        for (int x = 0; x < xBlockHeight; x++) {
            for (int z = 0; z < zBlockHeight; z++) {
                int ySurface = getGroundSurfaceY(chunkX, chunkZ, x, z);
                // MaxiWorld.log.info("ySurface = " + ySurface);

                int xOrigin = x * blocksPerBlock;
                int zOrigin = z * blocksPerBlock;

                for (int y = 0; y < ySurface + 1; y++) {
                    int yOrigin = y * blocksPerBlock;

                    // generate the layers
                    if (y >= 0 && y < ySurface - dirtThickness) {
                        generateStoneBlock(chunk, xOrigin, yOrigin, zOrigin, random);
                    } else if (y >= ySurface - dirtThickness && y < ySurface) {
                        generateDirtBlock(chunk, xOrigin, yOrigin, zOrigin);
                    } else if (y == ySurface) {
                        if (y < waterLevel) {
                            generateSandBlock(chunk, xOrigin, yOrigin, zOrigin);
                            for (int yLake = y + 1; yLake < waterLevel; yLake++)
                                generateWaterBlock(chunk, xOrigin, yLake * blocksPerBlock, zOrigin);
                        } else {
                            generateGrassBlock(chunk, xOrigin, yOrigin, zOrigin);
//                            if (ifATree(random))
//                                generateTrunkBlock(chunk, xOrigin, (y + 1) * blocksPerBlock, zOrigin);
                        }
                    }
                }
            }
        }
    }

    private void generateStoneBlock(InitialBlocks chunk, int x, int y, int z, Random random) {
        if (y == 0) {
            chunk.setBlocks(x, x + blocksPerBlock, 0, 1, z, z + blocksPerBlock, Material.BEDROCK);
            chunk.setBlocks(x, x + blocksPerBlock, y + 1, y + blocksPerBlock, z, z + blocksPerBlock, Material.STONE);
        } else
            chunk.setBlocks(x, x + blocksPerBlock, y, y + blocksPerBlock, z, z + blocksPerBlock, Material.STONE);
    }

    private void generateDirtBlock(InitialBlocks chunk, int x, int y, int z) {
        chunk.setBlocks(x, x + blocksPerBlock, y, y + blocksPerBlock, z, z + blocksPerBlock, Material.DIRT);
    }

    private void generateGrassBlock(InitialBlocks chunk, int x, int y, int z) {
        chunk.setBlocks(x, x + blocksPerBlock, y, y + blocksPerBlock - 1, z, z + blocksPerBlock, Material.DIRT);
        chunk.setBlocks(x, x + blocksPerBlock, y + blocksPerBlock - 1, y + blocksPerBlock, z, z + blocksPerBlock,
                Material.GRASS_BLOCK);
    }

    private void generateSandBlock(InitialBlocks chunk, int x, int y, int z) {
        chunk.setBlocks(x, x + blocksPerBlock, y, y + blocksPerBlock, z, z + blocksPerBlock, Material.SAND);
    }

    private void generateWaterBlock(InitialBlocks chunk, int x, int y, int z) {
        chunk.setBlocks(x, x + blocksPerBlock, y, y + blocksPerBlock, z, z + blocksPerBlock, Material.WATER);
    }

//    private void generateTrunkBlock(InitialBlocks chunk, int x, int y, int z) {
//        chunk.setBlocks(x, x + blocksPerBlock, y, y + blocksPerBlock, z, z + blocksPerBlock, Material.SPRUCE_LOG);
//    }
}
