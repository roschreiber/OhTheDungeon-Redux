// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support;

import java.util.Random;
import org.bukkit.Material;

public class GlassFactoryNS extends MaterialFactory
{
    public GlassFactoryNS(final Random rand) {
        super(rand);
    }
    
    public GlassFactoryNS(final Random rand, final SkipStyles style) {
        super(rand, style);
    }
    
    @Override
    public Material pickMaterial(final Material primaryId, final Material secondaryId, final int x, final int z) {
        switch (this.style) {
            case SINGLE: {
                return (z % 2 == 0) ? primaryId : secondaryId;
            }
            case DOUBLE: {
                return (z % 3 == 0) ? primaryId : secondaryId;
            }
            default: {
                return (this.rand.nextInt(2) == 0) ? primaryId : secondaryId;
            }
        }
    }
}
