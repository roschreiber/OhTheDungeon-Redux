/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.b173gen.oldgen.MathHelper;
import otd.addon.com.ohthedungeon.storydungeon.generator.b173gen.oldnoisegen.NoiseGeneratorOctaves3D;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author
 */
public class Valley extends BaseGenerator {
    
    private NoiseGeneratorOctaves3D noiseGen1;
    private NoiseGeneratorOctaves3D noiseGen2;
    private NoiseGeneratorOctaves3D noiseGen3;
    private NoiseGeneratorOctaves3D noiseGen4;
    private NoiseGeneratorOctaves3D noiseGen5;
    private Random rand = new Random();
    
    public Valley() {
//        this.noiseGen1 = new NoiseGeneratorOctaves3D(this.rand, 16);
//        this.noiseGen2 = new NoiseGeneratorOctaves3D(this.rand, 16);
//        this.noiseGen3 = new NoiseGeneratorOctaves3D(this.rand, 8);
//        this.noiseGen4 = new NoiseGeneratorOctaves3D(this.rand, 10);
//        this.noiseGen5 = new NoiseGeneratorOctaves3D(this.rand, 16);
    }
    
    @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random rand, int ChunkX, int ChunkZ) {
        this.rand.setSeed(seed);
        if(this.noiseGen1 == null) {
            this.noiseGen1 = new NoiseGeneratorOctaves3D(this.rand, 16);
            this.noiseGen2 = new NoiseGeneratorOctaves3D(this.rand, 16);
            this.noiseGen3 = new NoiseGeneratorOctaves3D(this.rand, 8);
            this.noiseGen4 = new NoiseGeneratorOctaves3D(this.rand, 10);
            this.noiseGen5 = new NoiseGeneratorOctaves3D(this.rand, 16);
        }
        AsyncChunk chunk = new AsyncChunk();
        this.generateTerrain(ChunkX, ChunkZ, chunk);
        this.replaceBlocksForBiome(ChunkX, ChunkZ, chunk);
        
        return chunk;
    }

    
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }
        
        float parabolicField[] = null;

        if (parabolicField == null)
        {
            parabolicField = new float[25];

            for (int k1 = -2; k1 <= 2; ++k1)
            {
                for (int l1 = -2; l1 <= 2; ++l1)
                {
                    float f = 10.0F / MathHelper.sqrt((float)(k1 * k1 + l1 * l1) + 0.2F);
                    parabolicField[k1 + 2 + (l1 + 2) * 5] = f;
                }
            }
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        double[] noise1 = null;
        double[] noise2 = null;
        double[] noise3 = null;
//        double[] noise4 = null;
        double[] noise5 = null;
        noise1 = this.noiseGen1.generateNoiseArray(noise1, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        noise2 = this.noiseGen2.generateNoiseArray(noise2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        noise3 = this.noiseGen3.generateNoiseArray(noise3, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
//        noise4 = this.noiseGen4.generateNoiseArray(noise4, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
        noise5 = this.noiseGen5.generateNoiseArray(noise5, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
        boolean flag = false;
        boolean flag1 = false;
        int i2 = 0;
        int j2 = 0;

        for (int k2 = 0; k2 < par5; ++k2)
        {
            for (int l2 = 0; l2 < par7; ++l2)
            {
                float f1 = 0.0F;
                float f2 = 0.0F;
                float f3 = 0.0F;
                byte b0 = 2;
                float rootHeight = 0.1f;//2;
                float heightVariation = 2.5f;//12;

                for (int i3 = -b0; i3 <= b0; ++i3)
                {
                    for (int j3 = -b0; j3 <= b0; ++j3)
                    {
                        float f4 = parabolicField[i3 + 2 + (j3 + 2) * 5] / (rootHeight + 2.0F);

                        f1 += heightVariation * f4;
                        f2 += rootHeight * f4;
                        f3 += f4;
                    }
                }

                f1 /= f3;
                f2 /= f3;
                f1 = f1 * 0.9F + 0.1F;
                f2 = (f2 * 4.0F - 1.0F) / 8.0F;
                double d2 = noise5[j2] / 8000.0D;

                if (d2 < 0.0D)
                {
                    d2 = -d2 * 0.3D;
                }

                d2 = d2 * 3.0D - 2.0D;

                if (d2 < 0.0D)
                {
                    d2 /= 2.0D;

                    if (d2 < -1.0D)
                    {
                        d2 = -1.0D;
                    }

                    d2 /= 1.4D;
                    d2 /= 2.0D;
                }
                else
                {
                    if (d2 > 1.0D)
                    {
                        d2 = 1.0D;
                    }

                    d2 /= 8.0D;
                }

                ++j2;

                for (int k3 = 0; k3 < par6; ++k3)
                {
                    double d3 = (double)f2;
                    double d4 = (double)f1;
                    d3 += d2 * 0.2D;
                    d3 = d3 * (double)par6 / 16.0D;
                    double d5 = (double)par6 / 2.0D + d3 * 4.0D;
                    double d6 = 0.0D;
                    double d7 = ((double)k3 - d5) * 12.0D * 128.0D / 128.0D / d4;

                    if (d7 < 0.0D)
                    {
                        d7 *= 4.0D;
                    }

                    double d8 = noise1[i2] / 512.0D;
                    double d9 = noise2[i2] / 512.0D;
                    double d10 = (noise3[i2] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D)
                    {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D)
                    {
                        d6 = d9;
                    }
                    else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;

                    if (k3 > par6 - 4)
                    {
                        double d11 = (double)((float)(k3 - (par6 - 4)) / 3.0F);
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    par1ArrayOfDouble[i2] = d6;
                    ++i2;
                }
            }
        }

        return par1ArrayOfDouble;
    }
    
    public void replaceBlocksForBiome(int x, int z, AsyncChunk chunk)
    {
        Material sandBlock = Material.RED_SAND;

        int a = -1;

        boolean flag = false;

        int k = (int)63;
        double d = 0.03125D;
        for(int l = 0; l < 16; l++)
        {
            for(int i1 = 0; i1 < 16; i1++)
            {
                Material top = Material.GRASS_BLOCK;
                Material filler = Material.DIRT;

                Material btop = sandBlock;
                Material bfiller = btop;

                for(int l1 = 128 - 1; l1 >= 0; l1--)
                {
                    int bx = i1, by = l1, bz = l;
                    Material block = chunk.getType(bx, by, bz);
                    
                    if(l1 <= 0)
                    {
                        chunk.setBlock(bx, by, bz, Material.BEDROCK);
                        continue;
                    }

                    if(block == Material.AIR || block == Material.WATER)
                    {
                        a = 0;
                        continue;
                    }

                    if(a >= 0 && a < 5)
                    {
                        Material blockUsed = Material.STONE;
                        if(a == 0 && l1 < 63 + 3)
                        {
                            flag = true;
                        }
                        if(flag)
                        {
                            if(a < 5) {
                                blockUsed = btop;
                            }
                        }
                        else
                        {
                            if(top != Material.SAND)
                            {
                                if(a == 0)
                                {
                                    blockUsed = top;
                                }
                                else if(a < 5)
                                {
                                    blockUsed = filler;
                                }
                            }
                        }
                        chunk.setBlock(bx, by, bz, blockUsed);
                        a++;
                        continue;
                    }

                    flag = false;
                    a = -1;

                }

                a = -1;

            }

        }
    }


    
    private void generateTerrain(int x, int z, AsyncChunk chunk)
    {
        byte chunkSizeGenXZ = 4;
        byte chunkSizeGenY = 16;
        byte midHeight = 63;
        int k = chunkSizeGenXZ + 1;
        byte b3 = 17;
        int l = chunkSizeGenXZ + 1;
        double[] noiseArray = null;
        noiseArray = this.initializeNoiseField(noiseArray, x * chunkSizeGenXZ, 0, z * chunkSizeGenXZ, k, b3, l);

        for (int i1 = 0; i1 < chunkSizeGenXZ; ++i1)
        {
            for (int j1 = 0; j1 < chunkSizeGenXZ; ++j1)
            {
                for (int k1 = 0; k1 < chunkSizeGenY; ++k1)
                {
                    double d0 = 0.125D;
                    double d1 = noiseArray[((i1 + 0) * l + j1 + 0) * b3 + k1 + 0];
                    double d2 = noiseArray[((i1 + 0) * l + j1 + 1) * b3 + k1 + 0];
                    double d3 = noiseArray[((i1 + 1) * l + j1 + 0) * b3 + k1 + 0];
                    double d4 = noiseArray[((i1 + 1) * l + j1 + 1) * b3 + k1 + 0];
                    double d5 = (noiseArray[((i1 + 0) * l + j1 + 0) * b3 + k1 + 1] - d1) * d0;
                    double d6 = (noiseArray[((i1 + 0) * l + j1 + 1) * b3 + k1 + 1] - d2) * d0;
                    double d7 = (noiseArray[((i1 + 1) * l + j1 + 0) * b3 + k1 + 1] - d3) * d0;
                    double d8 = (noiseArray[((i1 + 1) * l + j1 + 1) * b3 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2)
                        {
                            double d14 = 0.25D;
                            double d15 = (d11 - d10) * d14;
                            double d16 = d10 - d15;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                int bx = i1 * 4 + i2, by = k1 * 8 + l1, bz = j1 * 4 + k2;

                                if ((d16 += d15) > 0.0D)
                                {
                                    chunk.setBlock(bx, by, bz, Material.STONE);
                                }
                                else if (k1 * 8 + l1 < midHeight)
                                {
                                    chunk.setBlock(bx, by, bz, Material.WATER);
                                }
                                else
                                {
                                    chunk.setBlock(bx, by, bz, Material.AIR);
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

}
