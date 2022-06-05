package otd.nms;

import java.util.Random;

import org.bukkit.Chunk;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.spawners.Spawnable;

public interface GenerateLaterOrigin {
	public void generate(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level, Spawnable s);

	public void generate_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level,
			Spawnable s);
}
