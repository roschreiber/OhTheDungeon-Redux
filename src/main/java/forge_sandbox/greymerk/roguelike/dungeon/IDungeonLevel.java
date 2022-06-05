package forge_sandbox.greymerk.roguelike.dungeon;

import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.filter.IFilter;

public interface IDungeonLevel {

	public LevelSettings getSettings();

	boolean hasNearbyNode(Coord pos);

	public ILevelLayout getLayout();

	public void encase(IWorldEditor editor, Random rand);

	public void generate(ILevelGenerator generator, Coord start);

	void applyFilters(IWorldEditor editor, Random rand);

	void filter(IWorldEditor editor, Random rand, IFilter filter);
}
