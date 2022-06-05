package forge_sandbox.greymerk.roguelike.worldgen.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import forge_sandbox.greymerk.roguelike.worldgen.MetaBlock;
import otd.MultiVersion;
import otd.lib.ZoneWorld;
import otd.lib.async.later.roguelike.Spawnable_Later;
import otd.lib.spawner.SpawnerApi;

public class Spawnable {

	public Spawner type;
	public final List<SpawnPotential> potentials;

	public static SpawnerApi.SpawnerHandler handler = null;

	static {
		ZoneWorld.registerSpecialBlock(Material.SPAWNER);
	}

	public Spawnable(Spawner type) {
		this.potentials = new ArrayList<>();
		this.type = type;
	}

	public Spawnable(JsonElement data) throws Exception {
		this.potentials = new ArrayList<>();

		JsonArray arr = data.getAsJsonArray();
		for (JsonElement e : arr) {
			SpawnPotential potential = new SpawnPotential(e.getAsJsonObject());
			this.potentials.add(potential);
		}
	}

	public void generate_later_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor,
			int level) {
		if (handler != null) {
			handler.generate(this, pos, editor, rand, cursor, level);
		} else {
			this.generate_later_orign_chunk(chunk, pos, editor, rand, cursor, level);
		}
	}

	public void generate_later(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level) {
		if (handler != null) {
			handler.generate(this, pos, editor, rand, cursor, level);
		} else {
			this.generate_later_orign(pos, editor, rand, cursor, level);
		}
	}

	public void generate_later_orign(Coord pos, IWorldEditor editor, Random rand, Coord cursor, int level) {
		if (MultiVersion.generateLaterOrigin != null)
			MultiVersion.generateLaterOrigin.generate(pos, editor, rand, cursor, level, this);
	}

	public void generate_later_orign_chunk(Chunk chunk, Coord pos, IWorldEditor editor, Random rand, Coord cursor,
			int level) {
		if (MultiVersion.generateLaterOrigin != null)
			MultiVersion.generateLaterOrigin.generate_chunk(chunk, pos, editor, rand, cursor, level, this);
	}

	public void generate(IWorldEditor editor, Random rand, Coord cursor, int level) {
		Coord pos = new Coord(cursor);
		editor.setBlock(pos, new MetaBlock(Material.SPAWNER), true, true);

		if (!editor.isFakeWorld())
			generate_later(pos, editor, rand, cursor, level);
		else
			editor.addLater(new Spawnable_Later(this, pos, editor, rand, cursor, level));
	}

	public Object getSpawnPotentials(Random rand, int level) {
		return MultiVersion.getSpawnPotentials == null ? null : MultiVersion.getSpawnPotentials.get(rand, level, this);
	}
}