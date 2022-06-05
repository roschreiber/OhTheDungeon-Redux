package forge_sandbox.greymerk.roguelike.worldgen.shapes;

import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IBlockFactory;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;

public interface IShape extends Iterable<Coord> {

	public void fill(IWorldEditor editor, Random rand, IBlockFactory block);

	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid);

	public List<Coord> get();

}
