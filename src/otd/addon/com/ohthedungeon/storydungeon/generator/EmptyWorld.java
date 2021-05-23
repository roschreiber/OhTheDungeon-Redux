/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;

/**
 *
 * @author shadow_wind
 */
public class EmptyWorld extends BaseGenerator {
    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkx, int chunkz, BiomeGrid biomes) {
        ChunkData cd = Bukkit.createChunkData(world);
        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 16; j++) {
                biomes.setBiome(i, j, Biome.THE_VOID);
            }
        }
        return cd;
    }
}
