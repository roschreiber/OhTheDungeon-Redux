package forge_sandbox.greymerk.roguelike.dungeon.towers;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public interface ITower {

	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin);

}
