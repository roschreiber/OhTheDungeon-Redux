package forge_sandbox.greymerk.roguelike.worldgen.blocks;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;

public class Stair {

	public static MetaStair get(StairType type) {
		return get(type, Cardinal.EAST, false);
	}

	public static MetaStair get(StairType type, Cardinal dir, boolean upsideDown) {
		MetaStair stair = new MetaStair(type);
		stair.setOrientation(dir, upsideDown);
		return stair;
	}

}
