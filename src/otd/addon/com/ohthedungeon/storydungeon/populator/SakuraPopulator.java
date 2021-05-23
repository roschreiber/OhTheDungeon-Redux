/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.populator;

import otd.addon.com.ohthedungeon.storydungeon.populator.cherry.WorldGenCherry1;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 *
 * @author shadow_wind
 */
public class SakuraPopulator extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int X = random.nextInt(15);
        int Z = random.nextInt(15);
        
        int x = chunk.getX() * 16 + X;
        int z = chunk.getZ() * 16 + Z;
        int y = world.getHighestBlockYAt(x, z);
        
        Material type = world.getBlockAt(x, y, z).getType();
        if(type != Material.DIRT && type != Material.GRASS_BLOCK) return;
        
        WorldGenCherry1 cherry = new WorldGenCherry1();
        cherry.generate(world, random, x, y, z);
    }
    
}