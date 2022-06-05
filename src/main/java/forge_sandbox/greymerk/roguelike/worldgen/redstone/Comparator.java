package forge_sandbox.greymerk.roguelike.worldgen.redstone;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
//import net.minecraft.block.BlockRedstoneComparator;
//import net.minecraft.init.Blocks;

public class Comparator {

	private static void setDirection(MetaBlock block, BlockFace dir) {
		Directional state = (Directional) block.getState();
		state.setFacing(dir);
		block.setState(state);
	}

	private static void setMode(MetaBlock block, org.bukkit.block.data.type.Comparator.Mode mode) {
		org.bukkit.block.data.type.Comparator state = (org.bukkit.block.data.type.Comparator) block.getState();
		state.setMode(mode);
		block.setState(state);
	}

	public static void generate(IWorldEditor world, Random rand, Cardinal dir, boolean subtraction, Coord pos) {

		MetaBlock comparator = new MetaBlock(Material.COMPARATOR);
		setDirection(comparator, Cardinal.facing(dir));
		if (subtraction) {
			setMode(comparator, org.bukkit.block.data.type.Comparator.Mode.SUBTRACT);
		} else {
			setMode(comparator, org.bukkit.block.data.type.Comparator.Mode.COMPARE);
		}
		comparator.set(world, pos);
	}

}
