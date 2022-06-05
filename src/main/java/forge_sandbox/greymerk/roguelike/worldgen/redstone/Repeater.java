package forge_sandbox.greymerk.roguelike.worldgen.redstone;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
//import net.minecraft.block.BlockRedstoneRepeater;
//import net.minecraft.init.Blocks;
import org.bukkit.Material;

public class Repeater {

	public static void generate(IWorldEditor editor, Random rand, Cardinal dir, int delay, Coord pos) {
		generate(editor, rand, dir, delay, false, pos);
	}

	public static void generate(IWorldEditor editor, Random rand, Cardinal dir, int delay, boolean powered, Coord pos) {

//		MetaBlock repeater = powered ? new MetaBlock(Blocks.POWERED_REPEATER) : new MetaBlock(Blocks.UNPOWERED_REPEATER);
		MetaBlock repeater = new MetaBlock(Material.REPEATER);
		org.bukkit.block.data.type.Repeater state = (org.bukkit.block.data.type.Repeater) repeater.getState();
		state.setPowered(powered);
		state.setFacing(Cardinal.facing(dir));
		if (delay > 0 && delay <= 4)
			state.setDelay(delay);
		repeater.setState(state);
		repeater.set(editor, pos);
	}

}
