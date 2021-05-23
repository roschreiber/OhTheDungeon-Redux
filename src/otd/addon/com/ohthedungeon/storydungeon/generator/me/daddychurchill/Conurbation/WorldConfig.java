// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation;

public class WorldConfig
{
    private String worldname;
    private String worldstyle;
    private int streetLevel;
    private int seabedLevel;
    private int maximumFloors;
    private double decrepitLevel;
    private boolean includeAgricultureZones;
    private boolean includeResidentialZones;
    private boolean includeUrbanZones;
    public static final int defaultStreetLevel = -1;
    public static final int defaultSeabedLevel = -1;
    public static final int defaultMaximumFloors = 20;
    public static final double defaultDecrepitLevel = 0.1;
    public static final boolean defaultIncludeAgricultureZones = true;
    public static final boolean defaultIncludeResidentialZones = true;
    public static final boolean defaultIncludeUrbanZones = true;
    public static final int minimumMaximumFloors = 5;
    
    public WorldConfig() {
        this.worldname = "fake";
        this.worldstyle = "NORMAL";
        int globalStreetLevel = -1;
        int globalSeabedLevel = -1;
        int globalMaximumFloors = 20;
        double globalDecrepitLevel = 0.1;
        boolean globalIncludeAgricultureZones = true;
        boolean globalIncludeResidentialZones = true;
        boolean globalIncludeUrbanZones = true;
        globalStreetLevel = -1;
        globalSeabedLevel = -1;
        globalMaximumFloors = 20;
        globalDecrepitLevel = 0.1;
        globalIncludeAgricultureZones = true;
        globalIncludeResidentialZones = true;
        globalIncludeUrbanZones = true;
        this.streetLevel = globalStreetLevel;
        this.seabedLevel = globalSeabedLevel;
        this.maximumFloors = globalMaximumFloors;
        this.decrepitLevel = globalDecrepitLevel;
        this.includeAgricultureZones = globalIncludeAgricultureZones;
        this.includeResidentialZones = globalIncludeResidentialZones;
        this.includeUrbanZones = globalIncludeUrbanZones;
    }
            
    public String getWorldname() {
        return this.worldname;
    }
    
    public String getWorldstyle() {
        return this.worldstyle;
    }
    
    public int getStreetLevel() {
        return this.streetLevel;
    }
    
    public int getSeabedLevel() {
        return this.seabedLevel;
    }
    
    public int getMaximumFloors() {
        return Math.max(this.maximumFloors, 5);
    }
    
    public double getDecrepitLevel() {
        return this.decrepitLevel;
    }
    
    public boolean getIncludeAgricultureZones() {
        return this.includeAgricultureZones;
    }
    
    public boolean getIncludeResidentialZones() {
        return this.includeResidentialZones;
    }
    
    public boolean getIncludeUrbanZones() {
        return this.includeUrbanZones;
    }
}
