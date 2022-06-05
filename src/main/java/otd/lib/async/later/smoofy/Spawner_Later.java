package otd.lib.async.later.smoofy;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import otd.Main;
import otd.lib.async.later.roguelike.Later;
import otd.lib.spawner.SpawnerDecryAPI;
import otd.world.DungeonType;

public class Spawner_Later extends Later {
	private int bx, by, bz;
	private EntityType type;

	public Spawner_Later(int x, int y, int z, EntityType type) {
		this.bx = x;
		this.by = y;
		this.bz = z;
		this.type = type;
	}

	@Override
	public Coord getPos() {
		return new Coord(bx, by, bz);
	}

	@Override
	public void doSomething() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSomethingInChunk(Chunk c) {
		int bx = this.bx % 16;
		int by = this.by + this.y;
		int bz = this.bz % 16;

		if (bx < 0)
			bx = bx + 16;
		if (bz < 0)
			bz = bz + 16;

		Block spawnerBlock = c.getBlock(bx, by, bz);
		spawnerBlock.setType(Material.SPAWNER, true);
		BlockState blockState = spawnerBlock.getState();
		CreatureSpawner spawner = ((CreatureSpawner) blockState);
		spawner.setSpawnedType(type);
		blockState.update();
		SpawnerDecryAPI.setSpawnerDecry(spawnerBlock, Main.instance, DungeonType.DungeonMaze, true);
	}

}
