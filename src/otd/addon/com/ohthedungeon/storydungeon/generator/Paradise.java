/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 *
 * @author
 */
public class Paradise extends BaseGenerator {
    @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random random, int chunkX, int chunkZ) {
        AsyncChunk chunkData = new AsyncChunk();
        SimplexOctaveGenerator noise = new SimplexOctaveGenerator(seed, 2);
        noise.setScale(0.015625D);
        int height = 256;
        for (int x = 0; x < 16; x++) {
          for (int z = 0; z < 16; z++) {
            int y;
            for (y = 0; y < height / 2; y++) {
              double val = noise.noise((x + chunkX * 16), y, (z + chunkZ * 16), 0.5D, 0.5D);
              if (val >= 0.5D)
                chunkData.setBlock(x, y, z, Material.STONE); 
              if (val <= -0.99D && random.nextInt(10000) < 1 && x > 0 && x < 15 && z > 0 && z < 15 && y > 0)
                for (int i = 1; i <= 3; i++) {
                  for (int j = 1; j <= 3; j++) {
                    for (int k = 1; k <= 3; k++)
                      chunkData.setBlock(x + i - 2, y + j - 2, z + k - 2, Material.OBSIDIAN); 
                  } 
                }  
            }
          } 
        } 
        return chunkData;
    }
}
