package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.util.DyeColor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockColored;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.EnumDyeColor;

public enum ColorBlock {

	CLAY, WOOL, CARPET, GLASS, PANE, CONCRETE, POWDER;

	public static String get(ColorBlock type) {
		if (null != type)
			switch (type) {
			case CLAY:
				return "TERRACOTTA";
			case WOOL:
				return "WOOL";
			case CARPET:
				return "CARPET";
			case GLASS:
				return "STAINED_GLASS";
			case PANE:
				return "STAINED_GLASS_PANE";
			case CONCRETE:
				return "CONCRETE";
			default:
				return "CONCRETE_POWDER";
			}
		return "CONCRETE_POWDER";
	}

	public static MetaBlock get(ColorBlock type, DyeColor color) {
		String c = DyeColor.get(color);
		String base = get(type);
		// TODO
		Material b;
		try {
			b = Material.valueOf(c + "_" + base);
		} catch (Exception ex) {
			b = Material.WHITE_WOOL;
			Bukkit.getLogger().log(Level.SEVERE, ex.toString());
		}
		MetaBlock block = new MetaBlock(b);
		return block;
	}

	public static MetaBlock get(ColorBlock type, Random rand) {
		DyeColor[] colors = DyeColor.values();
		DyeColor choice = colors[rand.nextInt(colors.length)];
		return get(type, choice);
	}

	public static Material getBlock(ColorBlock type) {
		switch (type) {
		case CLAY:
			return Material.WHITE_TERRACOTTA;
		case WOOL:
			return Material.WHITE_WOOL;
		case CARPET:
			return Material.WHITE_CARPET;
		case GLASS:
			return Material.WHITE_STAINED_GLASS;
		case PANE:
			return Material.WHITE_STAINED_GLASS_PANE;
		case CONCRETE:
			return Material.WHITE_CONCRETE;
		case POWDER:
			return Material.WHITE_CONCRETE_POWDER;
		default:
			return Material.WHITE_WOOL;
		}
	}

}
