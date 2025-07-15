package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.shapes.RectSolid;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
//import net.minecraft.block.BlockVine;

public class Vine {

	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end) {
		for (Coord cursor : new RectSolid(start, end)) {
			set(editor, cursor);
		}
	}

	public static void set(IWorldEditor editor, Coord origin) {
		if (!editor.isAirBlock(origin))
			return;
		MetaBlock vine = BlockType.get(BlockType.VINE);
		MultipleFacing state = (MultipleFacing) vine.getState(); // BlockVine.FACING -> MultipleFacing
		boolean placed = false;
		for (Cardinal dir : Cardinal.directions) {
			Coord c = new Coord(origin);
			c.add(dir);
			if (editor.getMaterial(c).isSolid()) {
				BlockFace face;
				switch(dir) {
					case NORTH: face = BlockFace.NORTH; break;
					case EAST: face = BlockFace.EAST; break;
					case SOUTH: face = BlockFace.SOUTH; break;
					case WEST: face = BlockFace.WEST; break;
					default: continue;
				}
				state.setFace(face, true);
				placed = true;
			}
		}
		if (placed) {
			vine.setState(state);
			vine.set(editor, origin);
		}
	}

	public static MetaBlock setOrientation(MetaBlock vine, Cardinal dir) {
		MultipleFacing state = (MultipleFacing) vine.getState();
		state.setFace(BlockFace.NORTH, dir == Cardinal.NORTH);
		state.setFace(BlockFace.EAST, dir == Cardinal.EAST);
		state.setFace(BlockFace.SOUTH, dir == Cardinal.SOUTH);
		state.setFace(BlockFace.WEST, dir == Cardinal.WEST);
		vine.setState(state);
		return vine;
	}
}
