// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats;

import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.RealChunk;
import java.util.Random;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import org.bukkit.util.noise.NoiseGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Generator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class RiverGenerator extends WaterGenerator
{
    public static final double xRiverFactor = 40.0;
    public static final double zRiverFactor = 40.0;
    public static final double threshholdMinRiver = 0.4;
    public static final double threshholdMaxRiver = 0.525;
    public static final double xRiverbedNoiseFactor = 2.5;
    public static final double zRiverbedNoiseFactor = 2.5;
    public static final double scaleRiverbedNoise = 3.0;
    private int riverDepth;
    private PlatGenerator generatorLake;
    private SimplexNoiseGenerator noiseRiver;
    private SimplexNoiseGenerator noiseRiverbedDeviance;
    
    public RiverGenerator(final Generator noise, final PlatGenerator aGeneratorLake) {
        super(noise);
        this.generatorLake = aGeneratorLake;
        this.noiseRiver = new SimplexNoiseGenerator(noise.getNextSeed());
        this.noiseRiverbedDeviance = new SimplexNoiseGenerator(noise.getNextSeed());
        this.riverDepth = Math.min(this.waterLevel - noise.getSeabedLevel(), NoiseGenerator.floor(6.0));
    }
    
    @Override
    public void generateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
        chunk.setLayer(0, RiverGenerator.byteBedrock);
        if (this.noise.isDelta(chunkX, chunkZ)) {
            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16; ++z) {
                    this.generateChunkColumn(chunk, chunkX, chunkZ, x, z, this.getDeltaGroundSurfaceY(chunkX, chunkZ, x, z));
                }
            }
        }
        else {
            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16; ++z) {
                    this.generateChunkColumn(chunk, chunkX, chunkZ, x, z, this.getGroundSurfaceY(chunkX, chunkZ, x, z));
                }
            }
        }
        this.generateSeawalls(chunk, random, chunkX, chunkZ);
    }
    
    @Override
    public int generateChunkColumn(final ByteChunk chunk, final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
        final int blockY = this.noise.isDelta(chunkX, chunkZ) ? this.getDeltaGroundSurfaceY(chunkX, chunkZ, blockX, blockZ) : this.getGroundSurfaceY(chunkX, chunkZ, blockX, blockZ);
        return this.generateChunkColumn(chunk, chunkX, chunkZ, blockX, blockZ, blockY);
    }
    
    @Override
    public void populateChunk(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ) {
    }
    
    @Override
    public boolean isChunk(final int chunkX, final int chunkZ) {
        final double noiseLevel = (this.noiseRiver.noise(chunkX / 40.0, chunkZ / 40.0) + 1.0) / 2.0;
        return noiseLevel > 0.4 && noiseLevel < 0.525 && !this.generatorLake.isChunk(chunkX, chunkZ);
    }
    
    @Override
    public int getGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
        final double x = this.calcBlock(chunkX, blockX);
        final double z = this.calcBlock(chunkZ, blockZ);
        final double riverNoise = this.noiseRiverbedDeviance.noise(x / 2.5, z / 2.5) * 3.0;
        final int riverbedLevel = NoiseGenerator.floor(this.riverDepth + riverNoise - 3.0);
        return Math.max(0, Math.min(riverbedLevel, this.riverDepth - 1)) + this.waterLevel - this.riverDepth;
    }
    
    public int getDeltaGroundSurfaceY(final int chunkX, final int chunkZ, final int blockX, final int blockZ) {
        return (this.generatorLake.getGroundSurfaceY(chunkX, chunkZ, blockX, blockZ) + this.getGroundSurfaceY(chunkX, chunkZ, blockX, blockZ)) / 2;
    }
}
