/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.dungeon.aetherlegacy;

import java.util.Random;
import org.bukkit.Material;
import otd.lib.async.AsyncWorldEditor;

public abstract class AetherStructure  {
    public int chance;

    public Material airState = Material.AIR, blockState, extraBlockState;

    public boolean replaceAir, replaceSolid;

    public Random random;

    public AsyncWorldEditor worldObj;

    public int startX, startY, startZ;

    public void setBlocks(Material blockState) {
        this.blockState = blockState;
        this.extraBlockState = null;
        this.chance = 0;
    }
    
    public void setBlocks(Material blockState, Material extraBlockState, int chance) {
        this.blockState = blockState;
        this.extraBlockState = extraBlockState;
        this.chance = chance;

        if (this.chance < 1) {
            this.chance = 1;
        }
    }
    
    private int baseX, baseY, baseZ;
    
    public void setBaseStructureOffset(int x, int y, int z) {
        this.baseX = x;
        this.baseY = y;
        this.baseZ = z;
    }

    public void setStructureOffset(int x, int y, int z) {
        this.startX = x + baseX;
        this.startY = y + baseY;
        this.startZ = z + baseZ;
    }

    public void addLineX(int x, int y, int z, int xRange) {
        for (int lineX = x; lineX < x + xRange; lineX++) {
            Material block = this.getBlockState(lineX + this.startX, y + this.startY, z + this.startZ);

            if ((this.replaceAir || block != Material.AIR) && (this.replaceSolid || block == Material.AIR)) {
                this.setBlock(lineX + this.startX, y + this.startY, z + this.startZ);
            }
        }
    }

    public void addLineY(int x, int y, int z, int yRange) {
        for (int lineY = y; lineY < y + yRange; lineY++) {
            Material block = this.getBlockState(x + this.startX, lineY + this.startY, z + this.startZ);

            if ((this.replaceAir || block != Material.AIR) && (this.replaceSolid || block == Material.AIR)) {
                this.setBlock(x + this.startX, lineY + this.startY, z + this.startZ);
            }
        }
    }

    public void addLineZ(int x, int y, int z, int zRange) {
        for (int lineZ = z; lineZ < z + zRange; lineZ++) {
            Material block = this.getBlockState(x + this.startX, y + this.startY, lineZ + this.startZ);

            if ((this.replaceAir || block != Material.AIR) && (this.replaceSolid || block == Material.AIR)) {
                this.setBlock(x + this.startX, y + this.startY, lineZ + this.startZ);
            }
        }
    }

    public void addPlaneX(int x, int y, int z, int yRange, int zRange) {
        for (int lineY = y; lineY < y + yRange; lineY++) {
            for (int lineZ = z; lineZ < z + zRange; lineZ++) {
                Material block = this.getBlockState(x + this.startX, lineY + this.startY, lineZ + this.startZ);
                if ((this.replaceAir || block != Material.AIR) && (this.replaceSolid || block == Material.AIR)) {
                    this.setBlock(x + this.startX, lineY + this.startY, lineZ + this.startZ);
                }
            }
        }
    }

    public void addPlaneY(int x, int y, int z, int xRange, int zRange) {
        for (int lineX = x; lineX < x + xRange; lineX++) {
            for (int lineZ = z; lineZ < z + zRange; lineZ++) {
                Material block = this.getBlockState(lineX + this.startX, y + this.startY, lineZ + this.startZ);

                if ((this.replaceAir || block != Material.AIR) && (this.replaceSolid || block == Material.AIR)) {
                    this.setBlock(lineX + this.startX, y + this.startY, lineZ + this.startZ);
                }
            }
        }
    }

    public void addPlaneZ(int x, int y, int z, int xRange, int yRange) {
        for (int lineX = x; lineX < x + xRange; lineX++) {
            for (int lineY = y; lineY < y + yRange; lineY++) {
                Material block = this.getBlockState(lineX + this.startX, lineY + this.startY, z + this.startZ);

                if ((this.replaceAir || block != Material.AIR) && (this.replaceSolid || block == Material.AIR)) {
                    this.setBlock(lineX + this.startX, lineY + this.startY, z + this.startZ);
                }
            }
        }
    }

    public void addHollowBox(int x, int y, int z, int xRange, int yRange, int zRange) {
        Material temp1 = this.blockState;
        Material temp2 = this.extraBlockState;

        this.setBlocks(this.airState, this.airState, this.chance);
        this.addSolidBox(x, y, z, xRange, yRange, zRange);
        this.setBlocks(temp1, temp2, this.chance);
        this.addPlaneY(x, y, z, xRange, zRange);
        this.addPlaneY(x, y + (yRange - 1), z, xRange, zRange);
        this.addPlaneX(x, y, z, yRange, zRange);
        this.addPlaneX(x + (xRange - 1), y, z, yRange, zRange);
        this.addPlaneZ(x, y, z, xRange, yRange);
        this.addPlaneZ(x, y, z + (zRange - 1), xRange, yRange);
    }

    public void addSquareTube(int x, int y, int z, int xRange, int yRange, int zRange, int angel) {
        Material temp1 = this.blockState;
        Material temp2 = this.extraBlockState;

        this.setBlocks(this.airState, this.airState, this.chance);
        this.addSolidBox(x, y, z, xRange, yRange, zRange);
        this.setBlocks(temp1, temp2, this.chance);

        if (angel == 0 || angel == 2) {
            this.addPlaneY(x, y, z, xRange, zRange);
            this.addPlaneY(x, y + (yRange - 1), z, xRange, zRange);
        }

        if (angel == 1 || angel == 2) {
            this.addPlaneX(x, y, z, yRange, zRange);
            this.addPlaneX(x + (xRange - 1), y, z, yRange, zRange);
        }

        if (angel == 0 || angel == 1) {
            this.addPlaneZ(x, y, z, xRange, yRange);
            this.addPlaneZ(x, y, z + (zRange - 1), xRange, yRange);
        }
    }

    public void addSolidBox(int x, int y, int z, int xRange, int yRange, int zRange) {
        for (int lineX = x; lineX < x + xRange; lineX++) {
            for (int lineY = y; lineY < y + yRange; lineY++) {
                for (int lineZ = z; lineZ < z + zRange; lineZ++) {
                    Material block = this.getBlockState(lineX + this.startX, lineY + this.startY, lineZ + this.startZ);

                    if ((this.replaceAir || block != Material.AIR) && (this.replaceSolid || block == Material.AIR)) {
                        this.setBlock(lineX + this.startX, lineY + this.startY, lineZ + this.startZ);
                    }
                }
            }
        }
    }

    public boolean isBoxSolid(int x, int y, int z, int xRange, int yRange, int zRange) {
        boolean flag = true;

        for (int lineX = x; lineX < x + xRange; lineX++) {
            for (int lineY = y; lineY < y + yRange; lineY++) {
                for (int lineZ = z; lineZ < z + zRange; lineZ++) {
                    if (this.getBlockState(lineX + this.startX, lineY + this.startY, lineZ + this.startZ) == Material.AIR) {
                        flag = false;
                    }
                }
            }
        }

        return flag;
    }

    public boolean isBoxEmpty(int x, int y, int z, int xRange, int yRange, int zRange) {
        boolean flag = true;

        for (int lineX = x; lineX < x + xRange; lineX++) {
            for (int lineY = y; lineY < y + yRange; lineY++) {
                for (int lineZ = z; lineZ < z + zRange; lineZ++) {
                    if (this.getBlockState(lineX + this.startX, lineY + this.startY, lineZ + this.startZ) != Material.AIR) {
                        flag = false;
                    }
                }
            }
        }

        return flag;
    }
        
        public Material getBlockAtCurrentPosition(AsyncWorldEditor world, int x, int y, int z) {
            return world.getBlockState(x, y, z);
        }
        
        public void placeBlockAtCurrentPosition(AsyncWorldEditor world, Material material, int x, int y, int z) {
            world.setBlockState(x, y, z, material);
        }

    public Material getBlockStateWithOffset(int x, int y, int z) {
        return this.getBlockAtCurrentPosition(this.worldObj, x + this.startX, y + this.startY, z + this.startZ);
    }

    public Material getBlockState(int x, int y, int z) {
        return this.getBlockAtCurrentPosition(this.worldObj, x, y, z);
    }

    public void setBlockWithOffset(int x, int y, int z, Material state) {
        this.placeBlockAtCurrentPosition(this.worldObj, state, x + this.startX, y + this.startY, z + this.startZ);
    }

    public void setBlock(int x, int y, int z, Material state) {
        this.placeBlockAtCurrentPosition(this.worldObj, state, x, y, z);
    }

    public void setBlockWithOffset(int x, int y, int z) {
        if (this.chance == 0) {
            this.setBlock(x + this.startX, y + this.startY, z + this.startZ, this.blockState);
            return;
        }

        if (this.random.nextInt(this.chance) == 0) {
            this.placeBlockAtCurrentPosition(this.worldObj, this.extraBlockState, x + this.startX, y + this.startY, z + this.startZ);
        } else {
            this.placeBlockAtCurrentPosition(this.worldObj, this.blockState, x + this.startX, y + this.startY, z + this.startZ);
        }
    }

    public void setBlock(int x, int y, int z) {
        if (this.chance == 0) {
            this.setBlock(x, y, z, this.blockState);
            return;
        }

        if (this.random.nextInt(this.chance) == 0) {
            this.placeBlockAtCurrentPosition(this.worldObj, this.extraBlockState, x, y, z);
        } else {
            this.placeBlockAtCurrentPosition(this.worldObj, this.blockState, x, y, z);
        }
    }

    public boolean addComponentParts(AsyncWorldEditor worldIn, Random randomIn, boolean cloud, Material cloud_material) {
        this.worldObj = worldIn;
        this.random = randomIn;

        return this.generate(cloud, cloud_material);
    }

    public abstract boolean generate(boolean cloud, Material cloud_material);
}
