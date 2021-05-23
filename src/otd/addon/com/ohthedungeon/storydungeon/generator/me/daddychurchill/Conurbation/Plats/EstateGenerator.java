// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.RealChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.HouseFactory;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.Material;

public class EstateGenerator extends PlatGenerator
{
    private static final Material matGround;
    private static final Material byteGround;
    private static final Material matGrass;
    private static final Material byteGrass;
    private static final double oddsOfHouse = 0.5;
    private static final int slotHouse = 0;
    int groundLevel;
    
    static {
        matGround = Material.DIRT;
        byteGround = EstateGenerator.matGround;
        matGrass = Material.GRASS_BLOCK;
        byteGrass = EstateGenerator.matGrass;
    }
    
    public EstateGenerator(final Generator noise) {
        super(noise);
        this.groundLevel = noise.getStreetLevel();
    }
    
    @Override
    public boolean isChunk(final int chunkX, final int chunkZ) {
        return this.noise.isSuburban(chunkX, chunkZ) && this.noise.isGreenBelt(chunkX, chunkZ);
    }
    
    @Override
    public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
        chunk.setLayer(this.groundLevel, EstateGenerator.byteGround);
        chunk.setLayer(this.groundLevel + 1, EstateGenerator.byteGrass);
        if (this.isHouse(chunkX, chunkZ)) {
            final Material byteFloor = this.pickFloorMaterial(chunkX, chunkZ);
            final Material byteWall = this.pickWallMaterial(chunkX, chunkZ);
            final Material byteRoof = this.pickRoofMaterial(chunkX, chunkZ);
            HouseFactory.generateColonial(chunk, random, chunkX, chunkZ, this.groundLevel + 2, byteFloor, byteWall, byteRoof, 3);
        }
    }
    
    @Override
    public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
        if (!this.isHouse(chunkX, chunkZ)) {
            this.noise.generatorParks.populateChunk(chunk, random, chunkX, chunkZ);
        }
    }
    
    @Override
    public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
        chunk.setBlock(blockX, this.groundLevel, blockZ, EstateGenerator.byteGround);
        chunk.setBlock(blockX, this.groundLevel + 1, blockZ, EstateGenerator.byteGrass);
        return this.groundLevel + 1;
    }
    
    @Override
    public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
        return this.groundLevel + 1;
    }
    
    @Override
    public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
        return EstateGenerator.matGround;
    }
    
    private boolean isHouse(final int chunkX, final int chunkZ) {
        boolean possibleHouse = this.ifFeatureAt(chunkX, chunkZ, 0, 0.5);
        if (possibleHouse) {
            final boolean roadToNorth = this.noise.isRoad(chunkX, chunkZ - 1);
            final boolean roadToSouth = this.noise.isRoad(chunkX, chunkZ + 1);
            final boolean roadToWest = this.noise.isRoad(chunkX - 1, chunkZ);
            final boolean roadToEast = this.noise.isRoad(chunkX + 1, chunkZ);
            possibleHouse = (roadToNorth || roadToSouth || roadToWest || roadToEast);
        }
        return possibleHouse;
    }
}
