// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation;

import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.RealChunk;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support.ByteChunk;
import java.util.Random;
import org.bukkit.Material;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.UnknownGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.AirGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.RoadGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.FarmGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.NeighborhoodGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.CityGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.ForestGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.EstateGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.ParkGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.GroundGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.RiverGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.LakeGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.PlatGenerator;

public class Generator
{
    private WorldConfig config;
    public PlatGenerator generatorLake;
    public PlatGenerator generatorRiver;
    public PlatGenerator generatorGround;
    public PlatGenerator generatorParks;
    public PlatGenerator generatorEstates;
    public PlatGenerator generatorForests;
    public PlatGenerator generatorCities;
    public PlatGenerator generatorNeighborhoods;
    public PlatGenerator generatorFarms;
    public PlatGenerator generatorRoad;
    public PlatGenerator generatorAir;
    public PlatGenerator generatorUnknown;
    private static final double xUrbanizationFactor = 30.0;
    private static final double zUrbanizationFactor = 30.0;
    private static final double threshholdUrban = 0.6;
    private static final double threshholdSuburban = 0.35;
    private static final double xGreenBeltFactor = 30.0;
    private static final double zGreenBeltFactor = 30.0;
    private static final double threshholdGreenBeltMin = 0.4;
    private static final double threshholdGreenBeltMax = 0.5;
    private SimplexNoiseGenerator noiseUrbanization;
    private SimplexNoiseGenerator noiseGreenBelt;
    private int maximumLevel;
    private int streetLevel;
    private int seabedLevel;
    private int maximumFloors;
    private double decrepitLevel;
    private boolean includeAgricultureZones;
    private boolean includeResidentialZones;
    private boolean includeUrbanZones;
    public static final int floorHeight = 4;
    public static final int floorsReserved = 2;
    private int seedInc;
    private long seedWorld;
    
    public Generator(final WorldConfig aConfig) {
        this.seedInc = 0;
        this.config = aConfig;
        this.noiseUrbanization = new SimplexNoiseGenerator(this.getNextSeed());
        this.noiseGreenBelt = new SimplexNoiseGenerator(this.getNextSeed());
        this.streetLevel = this.config.getStreetLevel();
        this.seabedLevel = this.config.getSeabedLevel();
        this.decrepitLevel = this.config.getDecrepitLevel();
        this.includeAgricultureZones = this.config.getIncludeAgricultureZones();
        this.includeResidentialZones = this.config.getIncludeResidentialZones();
        this.includeUrbanZones = this.config.getIncludeUrbanZones();
        this.maximumLevel = 255;
        if (this.streetLevel < 0) {
            this.streetLevel = 67;
        }
        if (this.seabedLevel < 0) {
            this.seabedLevel = this.streetLevel / 2;
        }
        this.streetLevel = this.rangeCheck(this.streetLevel, this.maximumLevel);
        this.seabedLevel = this.rangeCheck(this.seabedLevel, this.streetLevel);
        this.maximumLevel = Math.min(this.maximumLevel, (this.config.getMaximumFloors() + 2) * 4 + this.streetLevel);
        this.maximumFloors = (this.maximumLevel - this.streetLevel) / 4 - 2;
        this.generatorLake = new LakeGenerator(this);
        this.generatorRiver = new RiverGenerator(this, this.generatorLake);
        this.generatorGround = new GroundGenerator(this);
        this.generatorParks = new ParkGenerator(this);
        this.generatorEstates = new EstateGenerator(this);
        this.generatorForests = new ForestGenerator(this);
        this.generatorCities = new CityGenerator(this);
        this.generatorNeighborhoods = new NeighborhoodGenerator(this);
        this.generatorFarms = new FarmGenerator(this);
        this.generatorRoad = new RoadGenerator(this);
        this.generatorAir = new AirGenerator(this);
        this.generatorUnknown = new UnknownGenerator(this);
    }
    
    private int rangeCheck(final int i, final int extreme) {
        final int min = extreme / 4;
        final int max = extreme * 3;
        return Math.max(min, Math.min(i, max));
    }
        
    public int getMaximumLevel() {
        return this.maximumLevel;
    }
    
    public int getStreetLevel() {
        return this.streetLevel;
    }
    
    public int getSeabedLevel() {
        return this.seabedLevel;
    }
    
    public int getMaximumFloors() {
        return this.maximumFloors;
    }
    
    public boolean isDecrepit(final Random random) {
        return random.nextDouble() < this.decrepitLevel;
    }
    
    public void generate(final ByteChunk byteChunk, final Random random, final int chunkX, final int chunkZ) {
        this.getBottomPlatGenerator(chunkX, chunkZ).generateChunk(byteChunk, random, chunkX, chunkZ);
        final PlatGenerator generator = this.getTopPlatGenerator(chunkX, chunkZ);
        if (generator != this.generatorUnknown) {
            generator.generateChunk(byteChunk, random, chunkX, chunkZ);
        }
    }
    
    public void populate(final ByteChunk realChunk, final Random random, final int chunkX, final int chunkZ) {
        this.getBottomPlatGenerator(chunkX, chunkZ).populateChunk(realChunk, random, chunkX, chunkZ);
        final PlatGenerator generator = this.getTopPlatGenerator(chunkX, chunkZ);
        if (generator != this.generatorUnknown) {
            generator.populateChunk(realChunk, random, chunkX, chunkZ);
        }
    }
    
    public PlatGenerator getBottomPlatGenerator(final int chunkX, final int chunkZ) {
        if (this.isLake(chunkX, chunkZ)) {
            return this.generatorLake;
        }
        if (this.isRiver(chunkX, chunkZ)) {
            return this.generatorRiver;
        }
        return this.generatorGround;
    }
    
    public boolean ifBottomPlatGenerator(final int chunkX, final int chunkZ, final PlatGenerator generator) {
        return this.getBottomPlatGenerator(chunkX, chunkZ) == generator;
    }
    
    public PlatGenerator getTopPlatGenerator(final int chunkX, final int chunkZ) {
        if (this.isRoad(chunkX, chunkZ)) {
            return this.generatorRoad;
        }
        if (this.isBuildable(chunkX, chunkZ)) {
            if (this.isGreenBelt(chunkX, chunkZ)) {
                if (this.isUrban(chunkX, chunkZ)) {
                    if (this.includeUrbanZones) {
                        return this.generatorParks;
                    }
                    return this.generatorForests;
                }
                else if (this.isSuburban(chunkX, chunkZ)) {
                    if (this.includeResidentialZones) {
                        return this.generatorEstates;
                    }
                    return this.generatorForests;
                }
                else if (this.isRural(chunkX, chunkZ)) {
                    return this.generatorForests;
                }
            }
            else if (this.isUrban(chunkX, chunkZ)) {
                if (this.includeUrbanZones) {
                    return this.generatorCities;
                }
                return this.generatorForests;
            }
            else if (this.isSuburban(chunkX, chunkZ)) {
                if (this.includeResidentialZones) {
                    return this.generatorNeighborhoods;
                }
                return this.generatorForests;
            }
            else if (this.isRural(chunkX, chunkZ)) {
                if (this.includeAgricultureZones) {
                    return this.generatorFarms;
                }
                return this.generatorForests;
            }
        }
        return this.generatorAir;
    }
    
    public boolean ifTopPlatGenerator(final int chunkX, final int chunkZ, final PlatGenerator generator) {
        return this.getTopPlatGenerator(chunkX, chunkZ) == generator;
    }
    
    public boolean isCompatibleGenerator(final int chunkX, final int chunkZ, final PlatGenerator generator) {
        return this.getTopPlatGenerator(chunkX, chunkZ).isCompatibleEdgeChunk(generator);
    }
    
    public PlatGenerator getNeighboringPlatGenerator(final int chunkX, final int chunkZ, final int deltaX, final int deltaZ) {
        PlatGenerator generator = this.getTopPlatGenerator(chunkX, chunkZ);
        if (generator != this.generatorRoad) {
            generator = this.getTopPlatGenerator(chunkX + deltaX, chunkZ);
            if (generator == this.generatorRoad || generator == this.generatorAir) {
                generator = this.getTopPlatGenerator(chunkX, chunkZ + deltaZ);
            }
        }
        return generator;
    }
    
    public Material getTopMaterial(final int chunkX, final int chunkZ) {
        return this.getTopPlatGenerator(chunkX, chunkZ).getGroundSurfaceMaterial(chunkX, chunkZ);
    }
    
    public boolean isLake(final int x, final int z) {
        return this.generatorLake.isChunk(x, z);
    }
    
    public boolean isRiver(final int x, final int z) {
        return this.generatorRiver.isChunk(x, z);
    }
    
    public boolean isDelta(final int x, final int z) {
        return this.isRiver(x, z) && (this.isLake(x - 1, z) || this.isLake(x + 1, z) || this.isLake(x, z - 1) || this.isLake(x, z + 1));
    }
    
    public boolean isWater(final int x, final int z) {
        return this.isLake(x, z) || this.isRiver(x, z);
    }
    
    public boolean isBuildable(final int x, final int z) {
        return !this.isWater(x, z) && !this.isRoad(x, z);
    }
    
    public boolean isGreenBelt(final int x, final int z) {
        final double noiseLevel = (this.noiseGreenBelt.noise(x / 30.0, z / 30.0) + 1.0) / 2.0;
        return noiseLevel > 0.4 && noiseLevel < 0.5;
    }
    
    public boolean isUrban(final int x, final int z) {
        final double noiseLevel = (this.noiseUrbanization.noise(x / 30.0, z / 30.0) + 1.0) / 2.0;
        return noiseLevel >= 0.6;
    }
    
    public boolean isSuburban(final int x, final int z) {
        final double noiseLevel = (this.noiseUrbanization.noise(x / 30.0, z / 30.0) + 1.0) / 2.0;
        return noiseLevel >= 0.35 && noiseLevel < 0.6;
    }
    
    public boolean isRural(final int x, final int z) {
        final double noiseLevel = (this.noiseUrbanization.noise(x / 30.0, z / 30.0) + 1.0) / 2.0;
        return noiseLevel < 0.35;
    }
    
    public boolean isUrbanBuilding(final int x, final int z) {
        return this.isUrban(x, z) && this.isBuildable(x, z);
    }
    
    public double getUrbanLevel(final int x, final int z) {
        return ((this.noiseUrbanization.noise(x / 30.0, z / 30.0) + 1.0) / 2.0 - 0.6) / 0.4;
    }
    
    public boolean isRoad(final int x, final int z) {
        return this.generatorRoad.isChunk(x, z);
    }
    
    public long getNextSeed() {
//        if (this.seedInc == 0) {
//            this.seedWorld = this.world.getSeed();
//        }
        ++this.seedInc;
        return this.seedWorld + this.seedInc;
    }
}
