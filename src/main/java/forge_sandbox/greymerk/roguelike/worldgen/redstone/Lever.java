package forge_sandbox.greymerk.roguelike.worldgen.redstone;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
import org.bukkit.block.data.type.Switch;
//import net.minecraft.block.BlockLever;
//import net.minecraft.block.BlockLever.EnumOrientation;
//import net.minecraft.init.Blocks;

public class Lever {

	@SuppressWarnings("deprecation")
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos, boolean active) {

		MetaBlock lever = new MetaBlock(Material.LEVER);
		Switch state = (Switch) lever.getState();
		state.setPowered(active);
		if (dir == Cardinal.UP) {
//			lever.withProperty(BlockLever.FACING, EnumOrientation.UP_X);
			state.setFace(Switch.Face.FLOOR);
		} else if (dir == Cardinal.DOWN) {
//			lever.withProperty(BlockLever.FACING, EnumOrientation.DOWN_X);
			state.setFace(Switch.Face.CEILING);
		} else {
//			lever.withProperty(BlockLever.FACING, Cardinal.orientation(Cardinal.reverse(dir)));
			state.setFace(Switch.Face.WALL);
			state.setFacing(Cardinal.facing(Cardinal.reverse(dir)));
		}
		lever.set(editor, pos);
	}

}
