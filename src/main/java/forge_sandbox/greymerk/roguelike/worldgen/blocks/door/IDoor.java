package forge_sandbox.greymerk.roguelike.worldgen.blocks.door;

import forge_sandbox.greymerk.roguelike.worldgen.Cardinal;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public interface IDoor {

	public void generate(IWorldEditor editor, Coord pos, Cardinal dir);

	public void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean open);
}
