// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support;

import java.util.Random;
import org.bukkit.Material;

public abstract class MaterialFactory {
	public SkipStyles style;
	protected Random rand;

	public MaterialFactory() {
		this.rand = null;
		this.style = SkipStyles.SINGLE;
	}

	public MaterialFactory(final Random rand) {
		this.rand = rand;
		this.style = this.pickSkipStyle();
	}

	public MaterialFactory(final Random rand, final SkipStyles astyle) {
		this.rand = rand;
		this.style = astyle;
	}

	protected SkipStyles pickSkipStyle() {
		switch (this.rand.nextInt(3)) {
		case 1: {
			return SkipStyles.SINGLE;
		}
		case 2: {
			return SkipStyles.DOUBLE;
		}
		default: {
			return SkipStyles.RANDOM;
		}
		}
	}

	public abstract Material pickMaterial(final Material p0, final Material p1, final int p2, final int p3);

	public enum SkipStyles {
		RANDOM("RANDOM", 0), SINGLE("SINGLE", 1), DOUBLE("DOUBLE", 2);

		private SkipStyles(final String name, final int ordinal) {
		}
	}
}
