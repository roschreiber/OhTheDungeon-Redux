/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.noise.NoiseGenerator;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author shadow_wind
 */
public class Tatooine extends BaseGenerator {
    private NoiseGenerator noiseGenerator = null;
    
    private static class HothUtils {
        public static void setPos(AsyncChunk chunk, int x, int y, int z, Material material) {
            chunk.setBlock(x, y, z, material);
        }
        
        public static void replaceTop(AsyncChunk chunk, Material from1, Material from2, Material to, int maxy)
        {
            for(int x=0;x<16;x++)
            {
                for(int z=0;z<16;z++)
                {
                    int y = HothUtils.getMaxY(chunk, x, z, maxy);
                    if(y>0)
                    {
                        Material old = chunk.getType(x, y, z);
                        if(old==from1 || old==from2)
                        {
                            HothUtils.setPos(chunk, x, y, z, to);
                        }
                    }
                }
            }
        }

        private static int getMaxY(AsyncChunk chunk, int x, int z, int maxy)
        {
            for(int i=(maxy-1);i>0;i--)
            {
                Material type = HothUtils.getRawPos(chunk, x, i, z); 
                if(type!= Material.AIR)
                {
                    return i;
                }
            }

            return 0;
        }
        
        private static Material getRawPos(AsyncChunk chunk, int x, int y, int z)
	{
            return chunk.getType(x, y, z);
	}
    }
    
    private static Material getBedrockMaterial(Random localRand, int limit) {
        return getBedrockMaterial(localRand, limit, Material.STONE);
    }
    
    private static Material getBedrockMaterial(Random localRand, int limit, Material material)
    {
        int ran = localRand.nextInt() & 0xff;
        if(ran>limit) return material;
        return Material.BEDROCK;
    }

    
    @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random random, int chunkx, int chunkz) {
        Material mAIR = Material.AIR;
        Material mLAVA = Material.LAVA;
        
        if(this.noiseGenerator==null)
        {
            this.noiseGenerator = new NoiseGenerator(seed);
        }
        
        int surfaceOffset = 0;
        
        Random localRand = new Random(chunkx*chunkz);
        
        AsyncChunk chunk = new AsyncChunk();
        
        for(int z=0;z<16;z++)
        {
            for(int x=0;x<16;x++)
            {
                int rx = chunkx*16 + x;
                int rz = chunkz*16 + z;
                
                float factor = 1.0f;
                // BEDROCK Layer
                int y = 0;
                HothUtils.setPos(chunk, x,y,z,Material.BEDROCK);
                HothUtils.setPos(chunk, x,y+1,z, getBedrockMaterial(localRand, (int)(256*0.9f))); // 90%
                HothUtils.setPos(chunk, x,y+2,z, getBedrockMaterial(localRand, (int)(256*0.7f))); // 70%
                HothUtils.setPos(chunk, x,y+3,z, getBedrockMaterial(localRand, (int)(256*0.5f))); // 50%
                HothUtils.setPos(chunk, x,y+4,z, getBedrockMaterial(localRand, (int)(256*0.3f))); // 30%
                HothUtils.setPos(chunk, x,y+5,z, getBedrockMaterial(localRand, (int)(256*0.2f))); // 20%
                
                // STONE Layer, solid
                for(y=6    ;y<27 + surfaceOffset ;y++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.STONE);
                }
                
                // STONE Layer
                double stone = this.noiseGenerator.noise(rx, rz, 8, 16)*3;
                for(int i=0;i<(int)(stone);i++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.STONE);
                    y++;
                }
                
                // DIRT Layer
                double dirt = this.noiseGenerator.noise(rx, rz, 8, 11)*5;
                for(int i=2;i< (int)(dirt);i++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.DIRT);
                    y++;
                }

                // GRAVEL Layer
                double gravel = this.noiseGenerator.noise(rx, rz, 7, 16)*5;
                for(int i=2;i< (int)(gravel);i++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.GRAVEL);
                    y++;
                }

                /*
                // SANDSTONE Layer
                double sandstone = this.noiseGenerator.noise(rx, rz, 8, 23)*4;
                for(int i=1;i< (int)(sandstone);i++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.SANDSTONE);
                    y++;
                }

                // SAND Layer
                double sand = 1+this.noiseGenerator.noise(rx, rz, 8, 43)*4;
                for(int i=0;i< (int)(sand);i++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.SAND);
                    y++;
                }
                */

                // CLAY Layer
                double clay = 1+this.noiseGenerator.noise(rx, rz, 3, 9)*5;
                for(int i=3;i< (int)(clay);i++)
                {
                    if(i==3)
                    {
                        HothUtils.setPos(chunk, x,y,z, Material.TERRACOTTA);
                    }
                    else
                    {
                        HothUtils.setPos(chunk, x,y,z, Material.CLAY);
                    }
                    y++;
                }
                

                /*
                // ice Layer
                while(y<34+surfaceOffset)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.ICE);
                    y++;
                }
                
                double icel = this.noiseGenerator.noise(rx, rz, 3, 68)*8;
                for(int i=3;i< (int)(icel);i++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.ICE);
                    y++;
                }
                */
                
                double iceh = this.noiseGenerator.noise(rx, rz, 3, 7)*15;
                
                // ICE mountains and hills
                double ice = factor * (this.noiseGenerator.noise(rx, rz, 4, 63)*2 + 
                              this.noiseGenerator.noise(rx, rz, 10, 12)) * 2.5;
                
                int icey = surfaceOffset + 64+(int)(ice);
                double dicey = surfaceOffset + 64+ice;
                for(;y<(icey-iceh);y++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.SANDSTONE);
                }

                for(;y<(icey);y++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.SAND);
                }
                
                
                // Inject stone mountains
                double domountain = this.noiseGenerator.noise(rx, rz, 4, 236)*20;
                double mfactor = 0.0f;
                if(domountain>18)
                {
                    mfactor = 1.0f;
                }
                else if (domountain>16)
                {
                    mfactor = (domountain-16)*0.5;
                }
                    
                if(mfactor>0)
                {
                    double mountain = this.noiseGenerator.noise(rx, rz, 4, 27)*84; // bulk of the mountain
                    mountain = mountain + this.noiseGenerator.noise(rx, rz, 8, 3)*5; // Add a bit more noise
                    for(int i=0;i<(int)(mountain*mfactor);i++)
                    {
                        HothUtils.setPos(chunk, x,i+26 + surfaceOffset,z, Material.STONE);
                        
                        if(i+26+surfaceOffset>y)
                        {
                            y = i+26+surfaceOffset;
                        }
                    }
                }
                
                // Sand cover
                double snowblocks = 1+this.noiseGenerator.noise(rx, rz, 8, 76)*2;

                for(int i = 0;i<(int)(snowblocks + (dicey - (int)dicey)); i++)
                {
                    HothUtils.setPos(chunk, x,y,z, Material.SAND);
                    y++;
                }
                
                // Cave layer
                double a = this.noiseGenerator.noise(rx, rz, 2, 60)*8;
                for(int i=4;i<128+surfaceOffset;i++)
                {
                    double d = this.noiseGenerator.noise(rx, i, rz, 4, 8)*16;
                    
                    if(i>(96+surfaceOffset))
                    {
                        a = a + 8  * (((i+surfaceOffset)-32.0)/32.0); // fade out the higher we go
                    }
                    if(d>(a+10))
                    {
                        Material old = chunk.getType(x, i, z);
                        if( old == Material.STONE
                         || old == Material.SANDSTONE 
                         || old == Material.SAND
                                )
                        
                        if(i<12)
                        {
                            HothUtils.setPos(chunk, x,i,z, mLAVA);
                        }
                        else
                        {
                            HothUtils.setPos(chunk, x,i,z, mAIR);
                        }
                    }
                }
                
                
                // LAVA Layer
                double dolava = this.noiseGenerator.noise(rx, rz, 4, 71)*10;
                if(dolava>7)
                {
                    double lava = this.noiseGenerator.noise(rx, rz, 4, 7)*21;
                    int lavay = (int)lava-18;
                    if(lavay>-2)
                    {
                        int start = 8-(2+lavay)/2;
                        
                        for(int i=-1;i<lavay;i++)
                        {
                            if(start+i>6)
                            {
                                HothUtils.setPos(chunk, x,start+i,z, Material.AIR);
                            }
                            else
                            {
                                HothUtils.setPos(chunk, x,start+i,z, Material.LAVA);
                            }
                        }
                    }
                }

                
                // WATER Layer
                double dowater = this.noiseGenerator.noise(rx, rz, 4, 91)*10;
                if(dowater>7)
                {
                    double water = this.noiseGenerator.noise(rx, rz, 4, 8)*21;
                    int watery = (int)water-18;
                    if(watery>-2)
                    {
                        int start = 23-(2+watery)/2;
                        
                        for(int i=-1;i<watery;i++)
                        {
                            if(start+i>21)
                            {
                                HothUtils.setPos(chunk, x,start+i,z, Material.AIR);
                            }
                            else
                            {
                                HothUtils.setPos(chunk, x,start+i,z, Material.WATER);
                            }
                            
                        }
                    }
                }
                
            }
        }
        

        HothUtils.replaceTop(chunk, Material.SANDSTONE, Material.STONE, Material.SAND, 256);
                        
        return chunk;
    }
}
