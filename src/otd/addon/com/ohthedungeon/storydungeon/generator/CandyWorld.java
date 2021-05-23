/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import otd.addon.com.ohthedungeon.storydungeon.com.ochotonida.candymod.enums.EnumGummy;
import otd.addon.com.ohthedungeon.storydungeon.generator.b173gen.oldgen.MathHelper;
import otd.addon.com.ohthedungeon.storydungeon.generator.b173gen.oldnoisegen.NoiseGeneratorOctaves3D;
import otd.addon.com.ohthedungeon.storydungeon.generator.b173gen.oldnoisegen.NoiseGeneratorPerlin;
import java.util.Random;
import org.bukkit.Material;

public class CandyWorld extends BaseGenerator {

    public static final Material ROCK = Material.TERRACOTTA;
    public static final Material OCEAN_BLOCK = Material.WATER;
    private Random rand;
    private NoiseGeneratorOctaves3D minLimitPerlinNoise;
    private NoiseGeneratorOctaves3D maxLimitPerlinNoise;
    private NoiseGeneratorOctaves3D mainPerlinNoise;
    private NoiseGeneratorPerlin surfaceNoise;
    public NoiseGeneratorOctaves3D scaleNoise;
    public NoiseGeneratorOctaves3D depthNoise;
    public NoiseGeneratorOctaves3D forestNoise;
    private double[] heightMap;
    private float[] biomeWeights;
    private double[] depthBuffer = new double[256];
    double[] mainNoiseRegion;
    double[] minLimitRegion;
    double[] maxLimitRegion;
    double[] depthRegion;

    public CandyWorld(long seed) {
        this.rand = new Random(seed);
        this.minLimitPerlinNoise = new NoiseGeneratorOctaves3D(this.rand, 16);
        this.maxLimitPerlinNoise = new NoiseGeneratorOctaves3D(this.rand, 16);
        this.mainPerlinNoise = new NoiseGeneratorOctaves3D(this.rand, 8);
        this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
        this.scaleNoise = new NoiseGeneratorOctaves3D(this.rand, 10);
        this.depthNoise = new NoiseGeneratorOctaves3D(this.rand, 16);
        this.forestNoise = new NoiseGeneratorOctaves3D(this.rand, 8);
        this.heightMap = new double[825];
        this.biomeWeights = new float[25];

        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
                this.biomeWeights[i + 2 + (j + 2) * 5] = f;
            }
        }
    }

    public void setBlocksInChunk(int x, int z, AsyncChunk primer) {
        this.generateHeightmap(x * 4, z * 4);

        for (int i = 0; i < 4; ++i) {
            int j = i * 5;
            int k = (i + 1) * 5;

            for (int l = 0; l < 4; ++l) {
                int i1 = (j + l) * 33;
                int j1 = (j + l + 1) * 33;
                int k1 = (k + l) * 33;
                int l1 = (k + l + 1) * 33;

                for (int i2 = 0; i2 < 32; ++i2) {
                    //double d0 = 0.125D;
                    double d1 = this.heightMap[i1 + i2];
                    double d2 = this.heightMap[j1 + i2];
                    double d3 = this.heightMap[k1 + i2];
                    double d4 = this.heightMap[l1 + i2];
                    double d5 = (this.heightMap[i1 + i2 + 1] - d1) * 0.125D;
                    double d6 = (this.heightMap[j1 + i2 + 1] - d2) * 0.125D;
                    double d7 = (this.heightMap[k1 + i2 + 1] - d3) * 0.125D;
                    double d8 = (this.heightMap[l1 + i2 + 1] - d4) * 0.125D;

                    for (int j2 = 0; j2 < 8; ++j2) {
                        //double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for (int k2 = 0; k2 < 4; ++k2) {
                            //double d14 = 0.25D;
                            double d16 = (d11 - d10) * 0.25D;
                            double lvt_45_1_ = d10 - d16;

                            for (int l2 = 0; l2 < 4; ++l2) {
                                if ((lvt_45_1_ += d16) > 0.0D) {
                                    primer.setBlock(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, ROCK);
                                } else if (i2 * 8 + j2 < 64) {
                                    primer.setBlock(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, OCEAN_BLOCK);
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBiomeBlocks(int x, int z, AsyncChunk primer) {
        //double d0 = 0.03125D;
        this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (x * 16), (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                generateCustomTerrain(this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
            }
        }
    }
    
    
    public void generateCustomTerrain(Random rand, AsyncChunk chunkPrimerIn, int x, int z, double noiseVal) {
        int seaLevel = 64;
        int j = -1;
        int fillerToPlace = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        final EnumGummy gummy_color = EnumGummy.getGummyForGeneration(noiseVal);
        Material topState = gummy_color.getMaterial();
        Material fillerState = gummy_color.getMaterial();
        int chunkZ = x & 15;
        int chunkX = z & 15;

        for (int currentY = 255; currentY >= 0; --currentY) {
            if (currentY <= rand.nextInt(5)) {
                chunkPrimerIn.setBlock(chunkX, currentY, chunkZ, Material.BEDROCK);
            } else {
                Material iblockstate2 = chunkPrimerIn.getType(chunkX, currentY, chunkZ);

                if (iblockstate2 == Material.AIR) {
                    j = -1;
                } else if (iblockstate2 == Material.STONE || iblockstate2 == ROCK) {
                    if (j == -1) {
                        if (currentY >= seaLevel - 4 && currentY <= seaLevel + 1) {
                            topState = gummy_color.getMaterial();
                            fillerState = gummy_color.getMaterial();
                        }

                        if (currentY < seaLevel && (topState == null || topState == Material.AIR)) {
                            topState = OCEAN_BLOCK;
                        }

                        j = fillerToPlace + rand.nextInt(8) + 4;

                        if (currentY >= seaLevel - 1) {
                            chunkPrimerIn.setBlock(chunkX, currentY, chunkZ, topState);
                        } else if (currentY < seaLevel - 7 - fillerToPlace) {
                            chunkPrimerIn.setBlock(chunkX, currentY, chunkZ, Material.GRAVEL);
                        } else {
                            chunkPrimerIn.setBlock(chunkX, currentY, chunkZ, fillerState);
                        }
                    } else if (j > 0) {
                        --j;
                        chunkPrimerIn.setBlock(chunkX, currentY, chunkZ, fillerState);
                    }
                }
            }
        }
    }

    
    
    @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random random, int x, int z) {
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        AsyncChunk chunkprimer = new AsyncChunk();
        this.setBlocksInChunk(x, z, chunkprimer);
        this.replaceBiomeBlocks(x, z, chunkprimer);
        
        return chunkprimer;
    }

    private void generateHeightmap(int x, int z) {
        this.depthRegion = this.depthNoise.generateNoiseArray(this.depthRegion, x, z, 5, 5, 200D, 200D, 0.5D);
        float f = 684.412F;
        float f1 = 684.412F;
        this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseArray(this.mainNoiseRegion, x, 0, z, 5, 33, 5, f / 80D, f1 / 160D, f / 80D);
        this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseArray(this.minLimitRegion, x, 0, z, 5, 33, 5, (double) f, (double) f1, (double) f);
        this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseArray(this.maxLimitRegion, x, 0, z, 5, 33, 5, (double) f, (double) f1, (double) f);
        int i = 0;
        int j = 0;

        for (int k = 0; k < 5; ++k) {
            for (int l = 0; l < 5; ++l) {
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                //int i1 = 2;
//                Biome biome = this.biomesForGeneration[k + 2 + (l + 2) * 10];

                for (int j1 = -2; j1 <= 2; ++j1) {
                    for (int k1 = -2; k1 <= 2; ++k1) {
//                        Biome biome1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
                        float f5 = 0.125F;
                        float f6 = 0.01F;

                        float f7 = this.biomeWeights[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);

//                        if (biome1.getBaseHeight() > biome.getBaseHeight()) {
//                            f7 /= 2.0F;
//                        }

                        f2 += f6 * f7;
                        f3 += f5 * f7;
                        f4 += f7;
                    }
                }

                f2 = f2 / f4;
                f3 = f3 / f4;
                f2 = f2 * 0.9F + 0.1F;
                f3 = (f3 * 4.0F - 1.0F) / 8.0F;
                double d7 = this.depthRegion[j] / 8000.0D;

                if (d7 < 0.0D) {
                    d7 = -d7 * 0.3D;
                }

                d7 = d7 * 3.0D - 2.0D;

                if (d7 < 0.0D) {
                    d7 = d7 / 2.0D;

                    if (d7 < -1.0D) {
                        d7 = -1.0D;
                    }

                    d7 = d7 / 1.4D;
                    d7 = d7 / 2.0D;
                } else {
                    if (d7 > 1.0D) {
                        d7 = 1.0D;
                    }

                    d7 = d7 / 8.0D;
                }

                ++j;
                double d8 = (double) f3;
                double d9 = (double) f2;
                d8 = d8 + d7 * 0.2D;
                d8 = d8 * 8.5D / 8.0D;
                double d0 = 8.5D + d8 * 4.0D;

                for (int l1 = 0; l1 < 33; ++l1) {
                    double d1 = ((double) l1 - d0) * 12D * 128.0D / 256.0D / d9;

                    if (d1 < 0.0D) {
                        d1 *= 4.0D;
                    }

                    double d2 = this.minLimitRegion[i] / 512D;
                    double d3 = this.maxLimitRegion[i] / 512D;
                    double d4 = (this.mainNoiseRegion[i] / 10.0D + 1.0D) / 2.0D;
                    double d5 = MathHelper.clamp(d2, d3, d4) - d1;

                    if (l1 > 29) {
                        double d6 = (double) ((float) (l1 - 29) / 3.0F);
                        d5 = d5 * (1.0D - d6) + -10.0D * d6;
                    }

                    this.heightMap[i] = d5;
                    ++i;
                }
            }
        }
    }
}
