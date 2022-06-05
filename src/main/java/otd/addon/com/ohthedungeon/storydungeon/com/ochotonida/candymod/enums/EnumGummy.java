package otd.addon.com.ohthedungeon.storydungeon.com.ochotonida.candymod.enums;

import java.util.Random;
import org.bukkit.Material;

public enum EnumGummy {

	RED(0, "red", 0xff4530), ORANGE(1, "orange", 0xff9b4f), YELLOW(2, "yellow", 0xffe563), WHITE(3, "white", 0xfffeb0),
	GREEN(4, "green", 0x80e22b);

	public Material getMaterial() {
		if ("red".equals(this.name))
			return Material.RED_TERRACOTTA;
		if ("orange".equals(this.name))
			return Material.ORANGE_TERRACOTTA;
		if ("yellow".equals(this.name))
			return Material.YELLOW_TERRACOTTA;
		if ("white".equals(this.name))
			return Material.WHITE_TERRACOTTA;
		return Material.GREEN_TERRACOTTA;
	}

	public static final EnumGummy[] META_LOOKUP = new EnumGummy[values().length];
	public static final EnumGummy[] WORLDGEN_SEQUENCE = { RED, ORANGE, YELLOW, GREEN, GREEN, YELLOW, WHITE, YELLOW,
			ORANGE, RED };

	static {
		for (EnumGummy enumgummy : values()) {
			META_LOOKUP[enumgummy.getMetadata()] = enumgummy;
		}
	}

	private final int meta;
	private final String name;
	private final int color;
	private final float[] colorComponentValues;

	EnumGummy(int meta, String name, int color) {
		this.meta = meta;
		this.name = name;
		this.color = color;
		int i = (color & 16711680) >> 16;
		int j = (color & 65280) >> 8;
		int k = (color & 255);
		this.colorComponentValues = new float[] { (float) i / 255.0F, (float) j / 255.0F, (float) k / 255.0F };
	}

	public static EnumGummy byMetadata(int meta) {
		if (meta < 0 || meta >= META_LOOKUP.length) {
			meta = 0;
		}

		return META_LOOKUP[meta];
	}

	public static EnumGummy getGummyForGeneration(double noise) {
		int i = (int) (noise * 1.6) % WORLDGEN_SEQUENCE.length;
		if (i < 0)
			i += WORLDGEN_SEQUENCE.length;
		return WORLDGEN_SEQUENCE[i];
	}

	public static EnumGummy random(Random rand) {
		return byMetadata(rand.nextInt(META_LOOKUP.length));
	}

	public int getMetadata() {
		return this.meta;
	}

	public int getColor() {
		return this.color;
	}

	public float[] getColorComponentValues() {
		return this.colorComponentValues;
	}

	public String getName() {
		return this.name;
	}
}