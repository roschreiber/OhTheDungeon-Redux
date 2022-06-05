package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import java.util.Arrays;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
//import net.minecraft.block.BlockEnderChest;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.EnumFacing;

public class EnderChest {
	public static void set(IWorldEditor editor, Cardinal dir, Coord pos) {

		BlockFace facing = Arrays.asList(Cardinal.directions).contains(dir) ? Cardinal.facing(Cardinal.reverse(dir))
				: Cardinal.facing(Cardinal.SOUTH);

		MetaBlock chest = new MetaBlock(Material.ENDER_CHEST);
		Directional state = (Directional) chest.getState();
		state.setFacing(facing);
		chest.setState(state);
		chest.set(editor, pos);
	}
}
