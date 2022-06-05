package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Material;
import org.bukkit.block.data.Bisected;
//import net.minecraft.block.BlockDoublePlant;
//import net.minecraft.init.Blocks;

public enum TallPlant {

	SUNFLOWER, LILAC, TALLGRASS, FERN, ROSE, PEONY;

	public static void generate(IWorldEditor editor, TallPlant type, Coord pos) {
		Coord cursor;

		MetaBlock upper = new MetaBlock(getType(type));
		MetaBlock lower = new MetaBlock(getType(type));

		{
			Bisected state = (Bisected) upper.getState();
			state.setHalf(Bisected.Half.TOP);
			upper.setState(state);
		}
		{
			Bisected state = (Bisected) lower.getState();
			state.setHalf(Bisected.Half.BOTTOM);
			lower.setState(state);
		}

		cursor = new Coord(pos);
		lower.set(editor, cursor);
		cursor.add(Cardinal.UP);
		upper.set(editor, cursor);
	}

	public static Material getType(TallPlant type) {
		switch (type) {
		case SUNFLOWER:
			return Material.SUNFLOWER;
		case LILAC:
			return Material.LILAC;
		case TALLGRASS:
			return Material.TALL_GRASS;
		case FERN:
			return Material.LARGE_FERN;
		case ROSE:
			return Material.ROSE_BUSH;
		case PEONY:
			return Material.PEONY;
		default:
			return Material.TALL_GRASS;
		}
	}

}
