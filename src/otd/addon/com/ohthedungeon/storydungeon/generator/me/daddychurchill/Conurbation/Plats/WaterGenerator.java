// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.Material;

public abstract class WaterGenerator extends PlatGenerator
{
    protected static final Material byteSand = Material.SAND;
    protected static final Material byteDirt = Material.DIRT;
    protected static final Material byteSeawall = Material.STONE_BRICKS;
    public static final int shoreHeight = 3;
    public static final int seawallHeight = 2;
    protected int streetLevel;
    protected int waterLevel;
    protected int seabedLevel;
    private double oddsOfStairs;
        
    public WaterGenerator(final Generator noise) {
        super(noise);
        this.oddsOfStairs = 0.25;
        this.streetLevel = noise.getStreetLevel();
        this.waterLevel = this.streetLevel - 3;
        this.seabedLevel = noise.getSeabedLevel();
    }
    
    @Override
    public boolean isCompatibleEdgeChunk(final PlatGenerator generator) {
        return true;
    }
    
    @Override
    public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
        return Material.SAND;
    }
    
    protected int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX, final int blockZ, final int waterbedY) {
        chunk.setBlocks(blockX, 1, waterbedY, blockZ, WaterGenerator.byteSand);
        if (waterbedY <= this.waterLevel) {
            chunk.setBlocks(blockX, waterbedY, this.waterLevel + 1, blockZ, WaterGenerator.byteWater);
        }
        return Math.max(waterbedY, this.waterLevel);
    }
    
    protected void generateSeawalls(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
        final boolean toNorth = !this.noise.isWater(chunkX, chunkZ - 1);
        final boolean toSouth = !this.noise.isWater(chunkX, chunkZ + 1);
        final boolean toWest = !this.noise.isWater(chunkX - 1, chunkZ);
        final boolean toEast = !this.noise.isWater(chunkX + 1, chunkZ);
        if (toNorth && toWest && !toSouth && !toEast) {
            final PlatGenerator neighborPlat = this.noise.getNeighboringPlatGenerator(chunkX, chunkZ, -1, -1);
            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16 - x; ++z) {
                    final int y = neighborPlat.generateChunkColumn(chunk, chunkX, chunkZ, x, z);
                    if (z == 16 - x - 1) {
                        chunk.setBlocks(x, this.seabedLevel, y + 2, z, WaterGenerator.byteSeawall);
                    }
                }
            }
        }
        else if (toNorth && toEast && !toSouth && !toWest) {
            final PlatGenerator neighborPlat = this.noise.getNeighboringPlatGenerator(chunkX, chunkZ, 1, -1);
            for (int z2 = 0; z2 < 16; ++z2) {
                for (int x2 = z2; x2 < 16; ++x2) {
                    final int y = neighborPlat.generateChunkColumn(chunk, chunkX, chunkZ, x2, z2);
                    if (x2 == z2) {
                        chunk.setBlocks(x2, this.seabedLevel, y + 2, z2, WaterGenerator.byteSeawall);
                    }
                }
            }
        }
        else if (toSouth && toWest && !toNorth && !toEast) {
            final PlatGenerator neighborPlat = this.noise.getNeighboringPlatGenerator(chunkX, chunkZ, -1, 1);
            for (int x = 0; x < 16; ++x) {
                for (int z = x; z < 16; ++z) {
                    final int y = neighborPlat.generateChunkColumn(chunk, chunkX, chunkZ, x, z);
                    if (z == x) {
                        chunk.setBlocks(x, this.seabedLevel, y + 2, z, WaterGenerator.byteSeawall);
                    }
                }
            }
        }
        else if (toSouth && toEast && !toNorth && !toWest) {
            final PlatGenerator neighborPlat = this.noise.getNeighboringPlatGenerator(chunkX, chunkZ, 1, 1);
            for (int z2 = 0; z2 < 16; ++z2) {
                for (int x2 = 15 - z2; x2 < 16; ++x2) {
                    final int y = neighborPlat.generateChunkColumn(chunk, chunkX, chunkZ, x2, z2);
                    if (x2 == 15 - z2) {
                        chunk.setBlocks(x2, this.seabedLevel, y + 2, z2, WaterGenerator.byteSeawall);
                    }
                }
            }
        }
        else {
            final boolean isBridge = this.noise.isRoad(chunkX, chunkZ);
            if (toNorth) {
                final boolean addStairs = this.isStairs(random);
                final PlatGenerator neighborPlat2 = this.noise.getTopPlatGenerator(chunkX, chunkZ - 1);
                final int seawallOffset = (isBridge && this.noise.isRoad(chunkX, chunkZ - 1)) ? -2 : 0;
                for (int x3 = 0; x3 < 16; ++x3) {
                    final int y2 = neighborPlat2.getGroundSurfaceY(chunkX, chunkZ - 1, x3, 15);
                    if (addStairs && x3 == 1) {
                        chunk.setBlocks(x3, this.seabedLevel, y2 + 2 + seawallOffset - 1, 0, WaterGenerator.byteSeawall);
                    }
                    else {
                        chunk.setBlocks(x3, this.seabedLevel, y2 + 2 + seawallOffset, 0, WaterGenerator.byteSeawall);
                    }
                    if (addStairs && x3 > 0 && x3 < 14) {
                        chunk.setBlock(x3, y2 + 2 + seawallOffset - x3 - 1, 1, WaterGenerator.byteSeawall);
                    }
                    if (x3 == 0 || x3 == 15) {
                        chunk.setBlocks(x3, this.seabedLevel, y2 + 2 + seawallOffset, 1, WaterGenerator.byteSeawall);
                    }
                }
            }
            if (toSouth) {
                final boolean addStairs = this.isStairs(random);
                final PlatGenerator neighborPlat2 = this.noise.getTopPlatGenerator(chunkX, chunkZ + 1);
                final int seawallOffset = (isBridge && this.noise.isRoad(chunkX, chunkZ + 1)) ? -2 : 0;
                for (int x3 = 0; x3 < 16; ++x3) {
                    final int y2 = neighborPlat2.getGroundSurfaceY(chunkX, chunkZ + 1, x3, 0);
                    if (addStairs && x3 == 1) {
                        chunk.setBlocks(x3, this.seabedLevel, y2 + 2 + seawallOffset - 1, 15, WaterGenerator.byteSeawall);
                    }
                    else {
                        chunk.setBlocks(x3, this.seabedLevel, y2 + 2 + seawallOffset, 15, WaterGenerator.byteSeawall);
                    }
                    if (addStairs && x3 > 0 && x3 < 14) {
                        chunk.setBlock(x3, y2 + 2 + seawallOffset - x3 - 1, 14, WaterGenerator.byteSeawall);
                    }
                    if (x3 == 0 || x3 == 15) {
                        chunk.setBlocks(x3, this.seabedLevel, y2 + 2 + seawallOffset, 14, WaterGenerator.byteSeawall);
                    }
                }
            }
            if (toWest) {
                final boolean addStairs = this.isStairs(random);
                final PlatGenerator neighborPlat2 = this.noise.getTopPlatGenerator(chunkX - 1, chunkZ);
                final int seawallOffset = (isBridge && this.noise.isRoad(chunkX - 1, chunkZ)) ? -2 : 0;
                for (int z3 = 0; z3 < 16; ++z3) {
                    final int y2 = neighborPlat2.getGroundSurfaceY(chunkX - 1, chunkZ, 15, z3);
                    if (addStairs && z3 == 1) {
                        chunk.setBlocks(0, this.seabedLevel, y2 + 2 + seawallOffset - 1, z3, WaterGenerator.byteSeawall);
                    }
                    else {
                        chunk.setBlocks(0, this.seabedLevel, y2 + 2 + seawallOffset, z3, WaterGenerator.byteSeawall);
                    }
                    if (addStairs && z3 > 0 && z3 < 14) {
                        chunk.setBlock(1, y2 + 2 + seawallOffset - z3 - 1, z3, WaterGenerator.byteSeawall);
                    }
                    if (z3 == 0 || z3 == 15) {
                        chunk.setBlocks(1, this.seabedLevel, y2 + 2 + seawallOffset, z3, WaterGenerator.byteSeawall);
                    }
                }
            }
            if (toEast) {
                final boolean addStairs = this.isStairs(random);
                final PlatGenerator neighborPlat2 = this.noise.getTopPlatGenerator(chunkX + 1, chunkZ);
                final int seawallOffset = (isBridge && this.noise.isRoad(chunkX - 1, chunkZ)) ? -2 : 0;
                for (int z3 = 0; z3 < 16; ++z3) {
                    final int y2 = neighborPlat2.getGroundSurfaceY(chunkX + 1, chunkZ, 0, z3);
                    if (addStairs && z3 == 1) {
                        chunk.setBlocks(15, this.seabedLevel, y2 + 2 + seawallOffset - 1, z3, WaterGenerator.byteSeawall);
                    }
                    else {
                        chunk.setBlocks(15, this.seabedLevel, y2 + 2 + seawallOffset, z3, WaterGenerator.byteSeawall);
                    }
                    if (addStairs && z3 > 0 && z3 < 14) {
                        chunk.setBlock(14, y2 + 2 + seawallOffset - z3 - 1, z3, WaterGenerator.byteSeawall);
                    }
                    if (z3 == 0 || z3 == 15) {
                        chunk.setBlocks(14, this.seabedLevel, y2 + 2 + seawallOffset, z3, WaterGenerator.byteSeawall);
                    }
                }
            }
        }
    }
    
    private boolean isStairs(final Random random) {
        return random.nextDouble() < this.oddsOfStairs;
    }
}
