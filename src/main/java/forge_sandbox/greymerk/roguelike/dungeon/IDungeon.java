package forge_sandbox.greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.treasure.ITreasureChest;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;

public interface IDungeon {

	public int generate(ISettings setting, Coord pos);

	public void spawnInChunk(Random rand, int chunkX, int chunkZ);

	public Coord getPosition();

	public Coord getTaskPosition();

	public void setTaskPosition(Coord pos);

	public List<IDungeonLevel> getLevels();

	public List<ITreasureChest> getChests();

}
