package forge_sandbox.greymerk.roguelike.theme;

import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import forge_sandbox.greymerk.roguelike.worldgen.MetaStair;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Log;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.StairType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.Wood;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.WoodBlock;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.Door;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.DoorType;
import forge_sandbox.greymerk.roguelike.worldgen.blocks.door.IDoor;

public class ThemeDarkOak extends ThemeBase {

	public ThemeDarkOak() {

		MetaBlock walls = Wood.get(Wood.DARKOAK, WoodBlock.PLANK);
		MetaStair stair = new MetaStair(StairType.DARKOAK);
		MetaBlock pillar = Log.getLog(Wood.DARKOAK);

		IDoor door = new Door(DoorType.DARKOAK);

		this.primary = new BlockSet(walls, walls, stair, pillar, door);
		MetaBlock secondaryWalls = Wood.get(Wood.DARKOAK, WoodBlock.PLANK);
		this.secondary = new BlockSet(secondaryWalls, secondaryWalls, stair, pillar, door);

	}
}
