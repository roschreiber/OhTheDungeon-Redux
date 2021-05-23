/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import otd.addon.com.ohthedungeon.storydungeon.util.FastNoise;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author shadow_wind
 */
public class OldValley extends BaseGenerator {
    
    private final FastNoise fastnoise1 = new FastNoise();
    private final FastNoise fastnoise2 = new FastNoise();
    private final FastNoise fastnoise3 = new FastNoise();
    private final FastNoise fastnoise4 = new FastNoise();
    private final FastNoise fastnoise5 = new FastNoise();
    private final FastNoise fastnoise6 = new FastNoise();
    private long seed;
    
    public OldValley() {
        fastnoise1.SetNoiseType(FastNoise.NoiseType.CubicFractal);
        fastnoise1.SetFrequency(0.002f);
        fastnoise1.SetFractalType(FastNoise.FractalType.RigidMulti);
        fastnoise1.SetFractalOctaves(1);
        fastnoise1.SetFractalLacunarity(0F);
        fastnoise1.SetFractalGain(0F);
        
        fastnoise2.SetFrequency(0.01F);
        
        fastnoise3.SetFrequency(0.005F);
        
        fastnoise4.SetNoiseType(FastNoise.NoiseType.Cellular);
        fastnoise4.SetCellularReturnType(FastNoise.CellularReturnType.Distance);
        fastnoise4.SetCellularDistanceFunction(FastNoise.CellularDistanceFunction.Natural);
        fastnoise4.SetGradientPerturbAmp(0.45f);
        
        fastnoise5.SetNoiseType(FastNoise.NoiseType.WhiteNoise);
        
        fastnoise6.SetNoiseType(FastNoise.NoiseType.CubicFractal);
        fastnoise6.SetFrequency(0.25f);
        fastnoise6.SetFractalType(FastNoise.FractalType.Billow);
        fastnoise6.SetFractalOctaves(8);
        fastnoise6.SetFractalLacunarity(0.6f);
        fastnoise6.SetFractalGain(2.0F);
        fastnoise6.SetGradientPerturbAmp(0.2f);
        
        
        seed = -1;
        initNoise(seed + 1);
    }
    
    private void initNoise(long s) {
        if(s == seed) return;
        seed = s;
        fastnoise1.SetSeed((int)seed);
    }
    
    private double CreateNumberForAlpha(double d, double d2, double d3) {
        return (1.0d / (d3 - d2)) * (d - d2);
    }
    
    private float lerp(float f, float f2, float f3) {
        return ((f2 - f) * f3) + f;
    }
    
    @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random random, int x, int z) {
        AsyncChunk chunk = new AsyncChunk();
        initNoise(seed);
        
        int i = 0;
        while (true) {
            int step1 = i;
            if(i > 16) return chunk;
            
            int i2 = 0;
            while(true) {
                int step2 = i2;
                if(i2 > 16) break;
                int var1 = (x * 16) + step1;
                int var2 = (z * 16) + step2;
                
                double d1 = fastnoise1.GetNoise(var1, var2);
                double d2 = fastnoise2.GetNoise(var1, var2) * 8;
                double d3 = fastnoise3.GetNoise(var1, var2) * 20;
                double d4 = fastnoise4.GetNoise(var1, var2) * 10;
                double d5 = fastnoise5.GetNoise(var1, var2) * 0.25;
                double d6 = fastnoise6.GetNoise(var1, var2) * 64;
                double d = 41;
                
                if (d1 >= 0.65d) {
                    if (0.65d < d1 && d1 < 0.83d) {
                        double CreateNumberForAlpha = CreateNumberForAlpha(d1, 0.65d, 0.83d);
                        double lerp = ((double) lerp(((float) d6) + ((float) 260.0d), lerp(((float) d2) + ((float) 30.0d), ((float) d3) + ((float) d4) + ((float) 30.0d) + ((float) 40.0d), 1.0f), (float) CreateNumberForAlpha)) + d5;
                        int i9 = 0;
                        while (true) {
                            int i10 = i9;
                            if (((double) i9) >= lerp - 3.0d) {
                                break;
                            }
                            if (i10 == 0) {
                                chunk.setBlock(step1, i10, step2, Material.BEDROCK);
                            } else {
                                chunk.setBlock(step1, i10, step2, Material.STONE);
                            }
                            i9 = i10 + 1;
                        }
                        int i11 = ((int) lerp) - 3;
                        while (true) {
                            int i12 = i11;
                            if (((double) i11) >= lerp) {
                                break;
                            }
                            if (i12 == 126 || i12 == 127 || i12 == 151) {
                                chunk.setBlock(step1, i12, step2, Material.YELLOW_TERRACOTTA);
                            } else if (i12 == 128 || i12 == 129 || i12 == 130 || i12 == 173 || i12 == 174 || i12 == 92) {
                                chunk.setBlock(step1, i12, step2, Material.RED_SAND);
                            } else if (i12 == 131 || i12 == 175) {
                                if (random.nextInt(2) == 1) {
                                    chunk.setBlock(step1, i12, step2, Material.RED_SAND);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.TERRACOTTA);
                                }
                            } else if (i12 == 190 || i12 == 191) {
                                if (random.nextInt(101) < 80) {
                                    chunk.setBlock(step1, i12, step2, Material.WHITE_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.TERRACOTTA);
                                }
                            } else if (i12 == 189 || i12 == 172 || i12 == 171 || i12 == 89) {
                                chunk.setBlock(step1, i12, step2, Material.RED_TERRACOTTA);
                            } else if (i12 == 150 || i12 == 152 || i12 == 149 || i12 == 153 || i12 == 96 || i12 == 97) {
                                chunk.setBlock(step1, i12, step2, Material.WHITE_TERRACOTTA);
                            } else if (i12 == 88) {
                                if (random.nextInt(101) >= 98) {
                                    chunk.setBlock(step1, i12, step2, Material.RED_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.TERRACOTTA);
                                }
                            } else if (i12 == 90) {
                                if (random.nextInt(100) > 10) {
                                    chunk.setBlock(step1, i12, step2, Material.RED_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.RED_SAND);
                                }
                            } else if (i12 == 91) {
                                if (random.nextInt(100) > 10) {
                                    chunk.setBlock(step1, i12, step2, Material.RED_SAND);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.RED_TERRACOTTA);
                                }
                            } else if (i12 == 93) {
                                if (random.nextInt(100) > 20) {
                                    chunk.setBlock(step1, i12, step2, Material.RED_SAND);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.YELLOW_TERRACOTTA);
                                }
                            } else if (i12 == 94) {
                                if (random.nextInt(100) > 40) {
                                    chunk.setBlock(step1, i12, step2, Material.YELLOW_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.RED_SAND);
                                }
                            } else if (i12 == 95) {
                                if (random.nextInt(100) > 10) {
                                    chunk.setBlock(step1, i12, step2, Material.WHITE_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.YELLOW_TERRACOTTA);
                                }
                            } else if (i12 == 98) {
                                if (random.nextInt(100) > 90) {
                                    chunk.setBlock(step1, i12, step2, Material.WHITE_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.TERRACOTTA);
                                }
                            } else if (i12 == 107) {
                                if (random.nextInt(100) > 90) {
                                    chunk.setBlock(step1, i12, step2, Material.LIGHT_GRAY_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.TERRACOTTA);
                                }
                            } else if (i12 == 108) {
                                chunk.setBlock(step1, i12, step2, Material.LIGHT_GRAY_TERRACOTTA);
                            } else if (i12 == 109) {
                                if (random.nextInt(100) > 70) {
                                    chunk.setBlock(step1, i12, step2, Material.LIGHT_GRAY_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.TERRACOTTA);
                                }
                            } else if (i12 == 110) {
                                if (random.nextInt(100) > 80) {
                                    chunk.setBlock(step1, i12, step2, Material.TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.ORANGE_TERRACOTTA);
                                }
                            } else if (i12 == 111) {
                                if (random.nextInt(100) > 85) {
                                    chunk.setBlock(step1, i12, step2, Material.BROWN_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.ORANGE_TERRACOTTA);
                                }
                            } else if (i12 == 112) {
                                if (random.nextInt(100) < 33) {
                                    chunk.setBlock(step1, i12, step2, Material.ORANGE_TERRACOTTA);
                                } else if (random.nextInt(100) >= 66 || random.nextInt(100) <= 33) {
                                    chunk.setBlock(step1, i12, step2, Material.BROWN_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.RED_TERRACOTTA);
                                }
                            } else if (i12 == 113) {
                                if (random.nextInt(100) > 70) {
                                    chunk.setBlock(step1, i12, step2, Material.BROWN_TERRACOTTA);
                                } else {
                                    chunk.setBlock(step1, i12, step2, Material.RED_TERRACOTTA);
                                }
                            } else if (i12 == 115 || i12 == 114) {
                                chunk.setBlock(step1, i12, step2, Material.ORANGE_TERRACOTTA);
                            } else if (i12 != 116) {
                                chunk.setBlock(step1, i12, step2, Material.TERRACOTTA);
                            } else if (random.nextInt(100) > 60) {
                                chunk.setBlock(step1, i12, step2, Material.ORANGE_TERRACOTTA);
                            } else {
                                chunk.setBlock(step1, i12, step2, Material.TERRACOTTA);
                            }
                            i11 = i12 + 1;
                        }
                    } else if (0.83d >= d1 || d1 >= 0.995d) {
                        double dd2 = 30.0d + d2;
                        if (dd2 > 41.0d) {
                            d = dd2;
                        }
                        int i13 = 0;
                        while (true) {
                            int i14 = i13;
                            if (((double) i13) >= d) {
                                break;
                            }
                            if (i14 == 0) {
                                chunk.setBlock(step1, i14, step2, Material.BEDROCK);
                            } else if (((double) i14) <= d2) {
                                chunk.setBlock(step1, i14, step2, Material.STONE);
                            } else {
                                chunk.setBlock(step1, i14, step2, Material.WATER);
                            }
                            i13 = i14 + 1;
                        }
                    } else {
                        double CreateNumberForAlpha2 = CreateNumberForAlpha(d1, 0.83d, 0.995d);
                        int nextInt = random.nextInt(10);
                        double lerp2 = (0.5d * d5) + ((double) lerp(((float) d3) + ((float) d4) + ((float) 30.0d) + ((float) 40.0d), ((float) d2) + ((float) 30.0d), (float) CreateNumberForAlpha2));
                        double dd3 = lerp2 > 41.0d ? lerp2 : 41.0d;
                        int i15 = 0;
                        while (true) {
                            int i16 = i15;
                            if (((double) i15) >= dd3) {
                                break;
                            }
                            if (i16 == 0) {
                                chunk.setBlock(step1, i16, step2, Material.BEDROCK);
                            } else if (((double) i16) <= lerp2) {
                                chunk.setBlock(step1, i16, step2, Material.STONE);
                            } else {
                                chunk.setBlock(step1, i16, step2, Material.WATER);
                            }
                            i15 = i16 + 1;
                        }
                        if (lerp2 >= 41.0d - 1.0d) {
                            if (nextInt > 3) {
                                chunk.setBlock(step1, (int) lerp2, step2, Material.GRASS_BLOCK);
                            } else {
                                chunk.setBlock(step1, (int) lerp2, step2, Material.RED_SAND);
                            }
                        }
                    }
                } else {
                    double dd7 = 260.0d + d6;
                    int i17 = 0;
                    while (true) {
                        int i18 = i17;
                        if (((double) i17) >= dd7) {
                            break;
                        }
                        if (i18 == 0) {
                            chunk.setBlock(step1, i18, step2, Material.BEDROCK);
                        } else {
                            chunk.setBlock(step1, i18, step2, Material.TERRACOTTA);
                        }
                        i17 = i18 + 1;
                    }
                }
                i2 = step2 + 1;
            }
            i = step1 + 1;
        }
    }
}
