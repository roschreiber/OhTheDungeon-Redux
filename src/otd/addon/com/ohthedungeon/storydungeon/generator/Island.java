/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.generator;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class Island extends BaseGenerator
{
    private long seed = -1;
    private SimplexOctaveGenerator bg;
    private SimplexOctaveGenerator g;
    private PerlinOctaveGenerator e;
    private PerlinOctaveGenerator c;
    private SimplexOctaveGenerator d;
    
    private void init(long s) {
        if(s == seed) return;
        seed = s;
        bg = new SimplexOctaveGenerator(seed, 18);
        bg.setScale(0.0078125);
        g = new SimplexOctaveGenerator(seed, 36);
        g.setScale(0.015625);
        e = new PerlinOctaveGenerator(seed, 32);
        e.setScale(0.03125);
        c = new PerlinOctaveGenerator(seed, 38);
        c.setScale(0.015625);
        d = new SimplexOctaveGenerator(seed, 18);
        d.setScale(0.0078125);
    }
    
    public Island() {
        this.seed = -1;
        init(this.seed + 1);
    }
    @Override
    public AsyncChunk asyncGenerateChunkData(long seed, Random rand, int ChunkX, int ChunkZ) {
        init(seed);
        AsyncChunk chunk = new AsyncChunk();
        int max = 255;
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int rx = x + ChunkX * 16;
                int rz = z + ChunkZ * 16;
                int h = (int)(bg.noise((double)rx, (double)rz, 0.75, 0.75) * 38.0 + 58.0);
                int bh = (int)(g.noise((double)rx, (double)rz, 0.5, 0.5) * 24.0 + 56.0);
                int ch = (int)(c.noise((double)rx, (double)rz, 0.75, 0.25) + g.noise((double)rx, (double)rz, 1.25, 0.75) + bg.noise((double)rx, (double)rz, 0.25, 0.5) * 18.0 + 20.0);
                int eg = (int)(e.noise((double)rx, (double)rz, 0.5, 0.5) + g.noise((double)rx, (double)rz, 1.25, 0.75) * 38.0 + 64.0);
                int ds = (int)(d.noise((double)rx, (double)rz, 0.25, 0.75) + c.noise((double)rx, (double)rz, 0.75, 0.25) + g.noise((double)rx, (double)rz, 1.25, 0.75) + bg.noise((double)rx, (double)rz, 0.25, 0.5) * 32.0 + 25.0);
                for (int y = 2; y < max; ++y) {
                    if (y < h || y < bh || y < eg) {
                        chunk.setBlock(x, y, z, Material.STONE);
                    }
                    if (y > ch * -1 + 95 || y > ds * -1 + 101) {
                        chunk.setBlock(x, y, z, Material.AIR);
                    }
                    if (y > 70) {
                        int it = 0;
                        int maxX = Math.max(x + 1, x - 1);
                        int maxY = Math.max(y + 1, y - 1);
                        int maxZ = Math.max(z + 1, z - 1);
                        int minX = Math.max(x + 1, x - 1);
                        int minY = Math.max(y + 1, y - 1);
                        int minZ = Math.max(z + 1, z - 1);
                        for (int i = 0; i <= Math.abs(maxX - minX); ++i) {
                            for (int ii = 0; ii <= Math.abs(maxZ - minZ); ++ii) {
                                for (int iii = 0; iii <= Math.abs(maxY - minY); ++iii) {
                                    Material material = chunk.getType(minX + i, minY + iii, minZ + ii);
                                    if (material == Material.AIR || material == Material.WATER) {
                                        ++it;
                                    }
                                }
                            }
                        }
                        if (it > 5) {
                            chunk.setBlock(x, y, z, Material.AIR);
                        }
                    }
                }
                Material b2;
                Material b3;
//                if (b.equals((Object)Biome.DESERT) || b.equals((Object)Biome.DESERT_HILLS) || b.equals((Object)Biome.BEACH)) {
//                    b2 = Material.SAND;
//                    b3 = Material.SANDSTONE;
//                }
//                else {
                    b2 = Material.GRASS_BLOCK;
                    b3 = Material.DIRT;
//                }
                for (int y2 = 74; y2 < max; ++y2) {
                    Material thisblock = chunk.getType(x, y2, z);
                    Material blockabove = chunk.getType(x, y2 + 1, z);
                    if (thisblock != Material.AIR && blockabove == Material.AIR) {
                        chunk.setBlock(x, y2, z, b2);
                        int y3 = rand.nextInt(5) + 1;
                        int y4 = rand.nextInt(5) + 1;
                        if (y2 < 128) {
                            if (chunk.getType(x, y2 - y3, z) != Material.AIR) {
                                chunk.setBlock(x, y2 - y3, z, b3);
                            }
                            if (chunk.getType(x, y2 - y4, z) != Material.AIR) {
                                chunk.setBlock(x, y2 - y4, z, b3);
                            }
                        }
                    }
                }
                for (int y2 = 1; y2 < 75; ++y2) {
                    Material thisblock = chunk.getType(x, y2, z);
                    if (thisblock == Material.AIR) {
                        chunk.setBlock(x, y2, z, Material.WATER);
                        
                        Material blockbelow = chunk.getType(x, y2 - 1, z);
                        if (blockbelow == Material.GRASS_BLOCK || blockbelow == Material.STONE) {
                            chunk.setBlock(x, y2 - 1, z, Material.GRAVEL);
                        }
                    }
                }
                chunk.setBlock(x, 1, z, Material.BEDROCK);
                chunk.setBlock(x, 0, z, Material.BEDROCK);
            }
        }
        return chunk;
    }
}
