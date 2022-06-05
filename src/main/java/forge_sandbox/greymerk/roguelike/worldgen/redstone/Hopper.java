package forge_sandbox.greymerk.roguelike.worldgen.redstone;

import java.util.Arrays;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
//import net.minecraft.block.BlockHopper;
//import net.minecraft.init.Blocks;

public class Hopper {

	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos) {
		MetaBlock hopper = new MetaBlock(Material.HOPPER);
		if (Arrays.asList(Cardinal.directions).contains(dir)) {
			Directional state = (Directional) hopper.getState();
			state.setFacing(Cardinal.facing(Cardinal.reverse(dir)));
			hopper.setState(state);
		}
		hopper.set(editor, pos);
	}
}
