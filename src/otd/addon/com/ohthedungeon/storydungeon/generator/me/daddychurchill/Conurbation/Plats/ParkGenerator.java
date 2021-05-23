// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import otd.addon.com.ohthedungeon.storydungeon.async.AsyncChunk;
import org.bukkit.World;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.RealChunk;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.Material;

public class ParkGenerator extends PlatGenerator
{
    public static final Material matGround;
    public static final Material byteGround;
    public static final Material matTurf;
    public static final Material byteTurf;
    public static final Material matTrunk;
    int groundLevel;
    int fenceLevel;
    private static final double oddsOfGrass = 0.2;
    private static final double oddsOfFlower = 0.05;
    private static final double oddsOfTree = 0.005;
    private static final double oddsCenterplace = 0.2;
    private static final int featureCenterplace = 0;
    
    static {
        matGround = Material.DIRT;
        byteGround = ParkGenerator.matGround;
        matTurf = Material.GRASS_BLOCK;
        byteTurf = ParkGenerator.matTurf;
        matTrunk = Material.OAK_LOG;
    }
    
    public ParkGenerator(final Generator noise) {
        super(noise);
        this.groundLevel = noise.getStreetLevel() + 1;
        this.fenceLevel = this.groundLevel + 1;
    }
    
    @Override
    public boolean isChunk(final int chunkX, final int chunkZ) {
        return this.noise.isUrban(chunkX, chunkZ) && this.noise.isGreenBelt(chunkX, chunkZ);
    }
    
    @Override
    public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
        final boolean roadToNorth = this.noise.isRoad(chunkX, chunkZ - 1);
        final boolean roadToSouth = this.noise.isRoad(chunkX, chunkZ + 1);
        final boolean roadToWest = this.noise.isRoad(chunkX - 1, chunkZ);
        final boolean roadToEast = this.noise.isRoad(chunkX + 1, chunkZ);
        boolean sidewalkNorth = true;
        boolean sidewalkSouth = true;
        boolean sidewalkWest = true;
        boolean sidewalkEast = true;
        chunk.setLayer(this.groundLevel - 1, ParkGenerator.byteGround);
        chunk.setLayer(this.groundLevel, ParkGenerator.byteTurf);
        if (roadToNorth) {
            sidewalkNorth = this.generateFence(chunk, random, 0, 16, this.fenceLevel, 0, 1);
        }
        if (roadToSouth) {
            sidewalkSouth = this.generateFence(chunk, random, 0, 16, this.fenceLevel, 15, 16);
        }
        if (roadToWest) {
            sidewalkWest = this.generateFence(chunk, random, 0, 1, this.fenceLevel, 0, 16);
        }
        if (roadToEast) {
            sidewalkEast = this.generateFence(chunk, random, 15, 16, this.fenceLevel, 0, 16);
        }
        if (sidewalkNorth) {
            this.generateParkwalk(chunk, 7, 9, this.groundLevel, 0, 7);
        }
        if (sidewalkSouth) {
            this.generateParkwalk(chunk, 7, 9, this.groundLevel, 9, 16);
        }
        if (sidewalkWest) {
            this.generateParkwalk(chunk, 0, 7, this.groundLevel, 7, 9);
        }
        if (sidewalkEast) {
            this.generateParkwalk(chunk, 9, 16, this.groundLevel, 7, 9);
        }
        if ((sidewalkNorth || sidewalkSouth || sidewalkWest || sidewalkEast) && !this.generateCenterplace(chunk, chunkX, chunkZ)) {
            this.generateParkwalk(chunk, 7, 9, this.groundLevel, 7, 9);
        }
    }
    
    @Override
    public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
        final int y = this.groundLevel + 1;
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                final Material surface = chunk.getBlock(x, y - 1, z);
                if (surface == ParkGenerator.matTurf) {
                    final double plantNoise = random.nextDouble();
                    if (this.noise.isDecrepit(random)) {
                        if (plantNoise < 0.005) {
                            chunk.setBlocks(x, y, y + random.nextInt(4), z, ParkGenerator.matTrunk);
                        }
                        else if (plantNoise < 0.05) {
                            chunk.setBlock(x, y, z, FarmGenerator.deadOnDirt);
                        }
                    }
                    else if (plantNoise < 0.005) {
                    }
                    else if (plantNoise < 0.05) {
                        chunk.setBlock(x, y, z, this.getRandomFlowerType(random));
                    }
                    else if (plantNoise < 0.2) {
                        chunk.setBlock(x, y, z, ParkGenerator.intGrassBlades);
                    }
                }
            }
        }
    }
    
    @Override
    public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
        chunk.setBlock(blockX, this.groundLevel, blockZ, ParkGenerator.byteGround);
        chunk.setBlock(blockX, this.groundLevel + 1, blockZ, ParkGenerator.byteTurf);
        return this.groundLevel + 1;
    }
    
    @Override
    public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
        return this.groundLevel + 1;
    }
    
    @Override
    public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
        return ParkGenerator.matTurf;
    }
    
    private boolean generateCenterplace(final ByteChunk chunk, final int chunkX, final int chunkZ) {
        final boolean doCenterplace = this.ifFeatureAt(chunkX, chunkZ, 0, 0.2);
        if (doCenterplace) {
            this.generateParkwalk(chunk, 4, 12, this.groundLevel, 4, 12);
            chunk.setCircle(8, 8, 2, this.groundLevel + 1, ParkGenerator.byteCobblestone);
        }
        return doCenterplace;
    }
}
