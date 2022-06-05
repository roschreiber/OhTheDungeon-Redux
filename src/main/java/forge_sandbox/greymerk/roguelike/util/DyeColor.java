package forge_sandbox.greymerk.roguelike.util;

import java.util.Random;

//import net.minecraft.item.EnumDyeColor;

public enum DyeColor {

	WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, GRAY, LIGHT_GRAY, CYAN, PURPLE, BLUE, BROWN, GREEN, RED,
	BLACK;

	public static String get(DyeColor color) {
		switch (color) {
		case WHITE:
			return "WHITE";
		case ORANGE:
			return "ORANGE";
		case MAGENTA:
			return "MAGENTA";
		case LIGHT_BLUE:
			return "LIGHT_BLUE";
		case YELLOW:
			return "YELLOW";
		case LIME:
			return "LIME";
		case PINK:
			return "PINK";
		case GRAY:
			return "GRAY";
		case LIGHT_GRAY:
			return "LIGHT_GRAY";
		case CYAN:
			return "CYAN";
		case PURPLE:
			return "PURPLE";
		case BLUE:
			return "BLUE";
		case BROWN:
			return "BROWN";
		case GREEN:
			return "GREEN";
		case RED:
			return "RED";
		case BLACK:
			return "BLACK";
		default:
			return "WHITE";
		}
	}

	public static int getInt(DyeColor color) {
		switch (color) {
		case WHITE:
			return 0;
		case ORANGE:
			return 1;
		case MAGENTA:
			return 2;
		case LIGHT_BLUE:
			return 3;
		case YELLOW:
			return 4;
		case LIME:
			return 5;
		case PINK:
			return 6;
		case GRAY:
			return 7;
		case LIGHT_GRAY:
			return 8;
		case CYAN:
			return 9;
		case PURPLE:
			return 10;
		case BLUE:
			return 11;
		case BROWN:
			return 12;
		case GREEN:
			return 13;
		case RED:
			return 14;
		case BLACK:
			return 15;
		default:
			return 0;
		}
	}

	public static DyeColor get(Random rand) {
		return DyeColor.values()[rand.nextInt(DyeColor.values().length)];
	}

	public static int RGBToColor(int r, int g, int b) {
		return r << 16 | g << 8 | b << 0;
	}

	public static int HSLToColor(float h, float s, float l) {
		float r, g, b;

		if (s == 0f) {
			r = g = b = l;
		} else {
			float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
			float p = 2 * l - q;
			r = hueToRgb(p, q, h + 1f / 3f);
			g = hueToRgb(p, q, h);
			b = hueToRgb(p, q, h - 1f / 3f);
		}
		return RGBToColor((int) (r * 255), (int) (g * 255), (int) (b * 255));
	}

	public static float hueToRgb(float p, float q, float t) {
		if (t < 0f)
			t += 1f;
		if (t > 1f)
			t -= 1f;
		if (t < 1f / 6f)
			return p + (q - p) * 6f * t;
		if (t < 1f / 2f)
			return q;
		if (t < 2f / 3f)
			return p + (q - p) * (2f / 3f - t) * 6f;
		return p;
	}
}
