package forge_sandbox.greymerk.roguelike.dungeon.segment;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.theme.ITheme;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

import java.util.Random;

public interface ISegment {

	public void generate(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord pos);

}
