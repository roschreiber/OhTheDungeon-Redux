/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otd.addon.com.ohthedungeon.storydungeon.populator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class GiantFlowerPopulator extends BlockPopulator {
    private static class IWorld {
        public World world;
        public IWorld(World world) {
            this.world = world;
        }
        public void setMaterial(int x, int y, int z, Material material) {
            world.getBlockAt(x, y, z).setType(material, false);
        }
        public boolean isAirBlock(int x, int y, int z) {
            return world.getBlockAt(x, y, z).getType() == Material.AIR;
        }
    }
    
    public static boolean generate_yellow(World world, Random random, int xx, int yy, int zz)
    {
        IWorld var1 = new IWorld(world);
        while (var1.isAirBlock(xx, yy, zz) && yy > 2)
        {
            --yy;
        }


        {
            for (int var7 = -2; var7 <= 2; ++var7)
            {
                for (int var8 = -2; var8 <= 2; ++var8)
                {
                    if (var1.isAirBlock(xx + var7, yy - 1, zz + var8) && var1.isAirBlock(xx + var7, yy - 2, zz + var8))
                        return false;
                }
            }

            var1.setMaterial(xx, yy, zz, Material.DIRT);
            var1.setMaterial(xx, yy + 1, zz, Material.BIRCH_LOG);
            var1.setMaterial(xx, yy + 2, zz, Material.BIRCH_LOG);
            var1.setMaterial(xx, yy + 3, zz, Material.BIRCH_LOG);
            var1.setMaterial(xx, yy + 4, zz, Material.BIRCH_LOG);
            var1.setMaterial(xx, yy + 5, zz, Material.BIRCH_LOG);

            var1.setMaterial(xx - 1, yy + 5, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx + 1, yy + 5, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx, yy + 5, zz - 1, Material.YELLOW_WOOL);
            var1.setMaterial(xx, yy + 5, zz + 1, Material.YELLOW_WOOL);
            var1.setMaterial(xx + 2, yy + 5, zz + 2, Material.YELLOW_WOOL);
            var1.setMaterial(xx + 2, yy + 5, zz - 2, Material.YELLOW_WOOL);
            var1.setMaterial(xx - 2, yy + 5, zz + 2, Material.YELLOW_WOOL);
            var1.setMaterial(xx - 2, yy + 5, zz - 2, Material.YELLOW_WOOL);

            var1.setMaterial(xx, yy + 6, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx - 1, yy + 6, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx + 1, yy + 6, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx, yy + 6, zz - 1, Material.YELLOW_WOOL);
            var1.setMaterial(xx, yy + 6, zz + 1, Material.YELLOW_WOOL);
            var1.setMaterial(xx + 1, yy + 6, zz + 1, Material.YELLOW_WOOL);
            var1.setMaterial(xx + 1, yy + 6, zz - 1, Material.YELLOW_WOOL);
            var1.setMaterial(xx - 1, yy + 6, zz + 1, Material.YELLOW_WOOL);
            var1.setMaterial(xx - 1, yy + 6, zz - 1, Material.YELLOW_WOOL);
            var1.setMaterial(xx + 2, yy + 6, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx - 2, yy + 6, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx, yy + 6, zz + 2, Material.YELLOW_WOOL);
            var1.setMaterial(xx, yy + 6, zz - 2, Material.YELLOW_WOOL);

            var1.setMaterial(xx, yy + 7, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx + 3, yy + 7, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx - 3, yy + 7, zz, Material.YELLOW_WOOL);
            var1.setMaterial(xx, yy + 7, zz + 3, Material.YELLOW_WOOL);
            var1.setMaterial(xx, yy + 7, zz - 3, Material.YELLOW_WOOL);

            return true;
        }
    }

    
    public static boolean generate_red(World world, Random random, int xx, int yy, int zz)
    {
        IWorld var1 = new IWorld(world);
        while (var1.isAirBlock(xx, yy, zz) && yy > 2)
        {
            --yy;
        }

        {
            for (int var7 = -2; var7 <= 2; ++var7)
            {
                for (int var8 = -2; var8 <= 2; ++var8)
                {
                    if (var1.isAirBlock(xx + var7, yy - 1, zz + var8) && var1.isAirBlock(xx + var7, yy - 2, zz + var8))
                        return false;
                }
            }

            var1.setMaterial(xx, yy, zz, Material.DIRT);
            var1.setMaterial(xx, yy + 1, zz, Material.ACACIA_LOG);
            var1.setMaterial(xx, yy + 2, zz, Material.ACACIA_LOG);
            var1.setMaterial(xx, yy + 3, zz, Material.ACACIA_LOG);
            var1.setMaterial(xx, yy + 4, zz, Material.ACACIA_LOG);
            var1.setMaterial(xx, yy + 5, zz, Material.ACACIA_LOG);

            var1.setMaterial(xx - 1, yy + 5, zz, Material.RED_WOOL);
            var1.setMaterial(xx + 1, yy + 5, zz, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 5, zz - 1, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 5, zz + 1, Material.RED_WOOL);

            var1.setMaterial(xx, yy + 6, zz, Material.RED_WOOL);
            var1.setMaterial(xx - 1, yy + 6, zz, Material.RED_WOOL);
            var1.setMaterial(xx + 1, yy + 6, zz, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 6, zz - 1, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 6, zz + 1, Material.RED_WOOL);
            var1.setMaterial(xx + 1, yy + 6, zz + 1, Material.RED_WOOL);
            var1.setMaterial(xx + 1, yy + 6, zz - 1, Material.RED_WOOL);
            var1.setMaterial(xx - 1, yy + 6, zz + 1, Material.RED_WOOL);
            var1.setMaterial(xx - 1, yy + 6, zz - 1, Material.RED_WOOL);
            var1.setMaterial(xx + 2, yy + 6, zz, Material.RED_WOOL);
            var1.setMaterial(xx - 2, yy + 6, zz, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 6, zz + 2, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 6, zz - 2, Material.RED_WOOL);

            var1.setMaterial(xx + 1, yy + 7, zz + 1, Material.RED_WOOL);
            var1.setMaterial(xx + 1, yy + 7, zz - 1, Material.RED_WOOL);
            var1.setMaterial(xx - 1, yy + 7, zz + 1, Material.RED_WOOL);
            var1.setMaterial(xx - 1, yy + 7, zz - 1, Material.RED_WOOL);
            var1.setMaterial(xx + 2, yy + 7, zz, Material.RED_WOOL);
            var1.setMaterial(xx - 2, yy + 7, zz, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 7, zz + 2, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 7, zz - 2, Material.RED_WOOL);
            var1.setMaterial(xx + 2, yy + 7, zz + 2, Material.RED_WOOL);
            var1.setMaterial(xx + 2, yy + 7, zz - 2, Material.RED_WOOL);
            var1.setMaterial(xx - 2, yy + 7, zz + 2, Material.RED_WOOL);
            var1.setMaterial(xx - 2, yy + 7, zz - 2, Material.RED_WOOL);

            var1.setMaterial(xx + 2, yy + 8, zz, Material.RED_WOOL);
            var1.setMaterial(xx - 2, yy + 8, zz, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 8, zz + 2, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 8, zz - 2, Material.RED_WOOL);

            var1.setMaterial(xx + 3, yy + 9, zz, Material.RED_WOOL);
            var1.setMaterial(xx - 3, yy + 9, zz, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 9, zz + 3, Material.RED_WOOL);
            var1.setMaterial(xx, yy + 9, zz - 3, Material.RED_WOOL);

            return true;
        }
    }
    
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int X = random.nextInt(15);
        int Z = random.nextInt(15);
        
        int x = chunk.getX() * 16 + X;
        int z = chunk.getZ() * 16 + Z;
        int y = world.getHighestBlockYAt(x, z);
        
        Material type = world.getHighestBlockAt(x, z).getType();
        if(type != Material.DIRT && type != Material.GRASS_BLOCK) return;
        
        if(random.nextInt() % 2 == 0)
            generate_yellow(world, random, x, y, z);
        else 
            generate_red(world, random, x, y, z);
    }
}
