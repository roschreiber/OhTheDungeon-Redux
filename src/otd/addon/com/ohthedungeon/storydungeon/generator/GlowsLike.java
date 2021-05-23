/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import org.bukkit.Material;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import java.util.Random;

public class GlowsLike extends BaseGenerator
{    
    SimplexOctaveGenerator gen = null;
    Random random = null;
    
    @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random rand, int ChunkX, int ChunkZ) {
        AsyncChunk result = new AsyncChunk();
        if(gen == null) {
            random = new Random(seed);
            gen = new SimplexOctaveGenerator(random, 8);
        }
        gen.setScale(0.00390625);
        final double threshold = 0.0;
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                final int real_x = x + ChunkX * 16;
                final int real_z = z + ChunkZ * 16;
                for (int y = 1; y < 256; ++y) {
                    if (gen.noise((double)real_x, (double)y, (double)real_z, 0.5, 0.5) > threshold) {
                        result.setBlock(x, y, z, Material.STONE);
                    }
                }
            }
        }
        
        return result;
    }
}
