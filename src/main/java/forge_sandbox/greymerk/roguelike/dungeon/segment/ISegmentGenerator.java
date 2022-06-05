package forge_sandbox.greymerk.roguelike.dungeon.segment;

import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.IDungeonLevel;
import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public interface ISegmentGenerator {

	public List<ISegment> genSegment(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, Coord pos);

}
