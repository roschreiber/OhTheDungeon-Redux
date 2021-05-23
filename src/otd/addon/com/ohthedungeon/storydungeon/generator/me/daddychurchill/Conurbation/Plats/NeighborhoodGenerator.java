// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.HouseFactory;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.Material;

public class NeighborhoodGenerator extends PlatGenerator
{
    public static final Material matGround;
    public static final Material byteGround;
    public static final Material matGrass;
    public static final Material byteGrass;
    public static final double oddsOfHouse = 0.7;
    public static final double oddsOfSecondFloor = 0.4;
    private static final int slotHouse = 0;
    private static final int slotSecondFloor = 1;
    int groundLevel;
    
    static {
        matGround = Material.DIRT;
        byteGround = NeighborhoodGenerator.matGround;
        matGrass = Material.GRASS_BLOCK;
        byteGrass = NeighborhoodGenerator.matGrass;
    }
    
    public NeighborhoodGenerator(final Generator noise) {
        super(noise);
        this.groundLevel = noise.getStreetLevel();
    }
    
    @Override
    public boolean isChunk(final int chunkX, final int chunkZ) {
        return this.noise.isSuburban(chunkX, chunkZ) && !this.noise.isGreenBelt(chunkX, chunkZ);
    }
    
    @Override
    public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
        chunk.setLayer(this.groundLevel, NeighborhoodGenerator.byteGround);
        chunk.setLayer(this.groundLevel + 1, NeighborhoodGenerator.byteGrass);
        if (this.isHouse(chunkX, chunkZ)) {
            final Material byteFloor = this.pickFloorMaterial(chunkX, chunkZ);
            final Material byteWall = this.pickWallMaterial(chunkX, chunkZ);
            final Material byteRoof = this.pickRoofMaterial(chunkX, chunkZ);
            final int floors = this.ifFeatureAt(chunkX, chunkZ, 1, 0.4) ? 2 : 1;
            HouseFactory.generateColonial(chunk, random, chunkX, chunkZ, this.groundLevel + 2, byteFloor, byteWall, byteRoof, floors);
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
        chunk.setBlock(blockX, this.groundLevel, blockZ, NeighborhoodGenerator.byteGround);
        chunk.setBlock(blockX, this.groundLevel + 1, blockZ, NeighborhoodGenerator.byteGrass);
        return this.groundLevel + 1;
    }
    
    @Override
    public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
        return this.groundLevel + 1;
    }
    
    @Override
    public Material getGroundSurfaceMaterial(final int chunkX, final int chunkZ) {
        return NeighborhoodGenerator.matGrass;
    }
    
    private boolean isHouse(final int chunkX, final int chunkZ) {
        boolean possibleHouse = this.ifFeatureAt(chunkX, chunkZ, 0, 0.7);
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
