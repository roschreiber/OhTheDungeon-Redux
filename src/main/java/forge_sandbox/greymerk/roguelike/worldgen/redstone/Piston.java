package forge_sandbox.greymerk.roguelike.worldgen.redstone;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
//import net.minecraft.block.BlockPistonBase;
//import net.minecraft.init.Blocks;

public class Piston {

	public static void generate(IWorldEditor editor, Coord origin, Cardinal dir, boolean sticky) {

		MetaBlock piston = new MetaBlock(sticky ? Material.STICKY_PISTON : Material.PISTON);
		Directional state = (Directional) piston.getState();
		state.setFacing(Cardinal.facing(Cardinal.reverse(dir)));
		piston.setState(state);
		piston.set(editor, origin);
	}

}
