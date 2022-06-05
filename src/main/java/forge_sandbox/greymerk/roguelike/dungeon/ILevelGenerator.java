package forge_sandbox.greymerk.roguelike.dungeon;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;

public interface ILevelGenerator {

	public void generate(Coord start);

	public ILevelLayout getLayout();

}
