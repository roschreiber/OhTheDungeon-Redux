/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.populator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 *
 * @author shadow_wind
 */
public class SmallOakPopulator extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int X = random.nextInt(15);
        int Z = random.nextInt(15);
        
        int x = chunk.getX() * 16 + X;
        int z = chunk.getZ() * 16 + Z;
        int y = world.getHighestBlockYAt(x, z);
        
        world.generateTree(world.getBlockAt(x, y - 1, z).getLocation(), TreeType.TREE);
    }
    
}
