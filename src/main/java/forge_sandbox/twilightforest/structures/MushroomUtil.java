/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest.structures;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;

/**
 * Utility class for Huge Mushroom blocks. Contains presets
 */
public class MushroomUtil {

	public static BlockData getState(Type type, BlockData base) {
		MultipleFacing mf = (MultipleFacing) base.clone();
		mf.setFace(BlockFace.UP, type.top);
		mf.setFace(BlockFace.DOWN, type.bottom);
		mf.setFace(BlockFace.NORTH, type.north);
		mf.setFace(BlockFace.SOUTH, type.south);
		mf.setFace(BlockFace.EAST, type.east);
		mf.setFace(BlockFace.WEST, type.west);

		return mf;
	}

	public enum Type {

		CENTER(true, false, false, false, false, false), NORTH(true, false, true, false, false, false),
		SOUTH(true, false, false, true, false, false), EAST(true, false, false, false, true, false),
		WEST(true, false, false, false, false, true), NORTH_WEST(true, false, true, false, false, true),
		NORTH_EAST(true, false, true, false, true, false), SOUTH_WEST(true, false, false, true, false, true),
		SOUTH_EAST(true, false, false, true, true, false);

		private boolean top;
		private boolean bottom;
		private boolean north;
		private boolean south;
		private boolean east;
		private boolean west;

		Type(boolean t, boolean b, boolean n, boolean s, boolean e, boolean w) {
			top = t;
			bottom = b;
			north = n;
			south = s;
			east = e;
			west = w;
		}
	}
}