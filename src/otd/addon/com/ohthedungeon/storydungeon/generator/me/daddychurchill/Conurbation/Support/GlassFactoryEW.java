// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support;

import java.util.Random;
import org.bukkit.Material;

public class GlassFactoryEW extends MaterialFactory
{
    public GlassFactoryEW(final Random rand) {
        super(rand);
    }
    
    public GlassFactoryEW(final Random rand, final SkipStyles style) {
        super(rand, style);
    }
    
    @Override
    public Material pickMaterial(final Material primaryId, final Material secondaryId, final int x, final int z) {
        switch (this.style) {
            case SINGLE: {
                return (x % 2 == 0) ? primaryId : secondaryId;
            }
            case DOUBLE: {
                return (x % 3 == 0) ? primaryId : secondaryId;
            }
            default: {
                return (this.rand.nextInt(2) == 0) ? primaryId : secondaryId;
            }
        }
    }
}
