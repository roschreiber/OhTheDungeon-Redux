package otd.lib.async.later.castle;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;

import forge_sandbox.BlockPos;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.team.cqr.cqrepoured.boss.CastleKing;
import otd.Main;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;
import otd.lib.spawner.SpawnerDecryAPI;
import otd.world.DungeonType;

public class Spawner_Later extends Later {
	public int x, y, z;
	public int floor;
	public boolean isBoss;

	public static boolean generate_later(final AsyncWorldEditor world, final BlockPos pos, int floor) {
		Spawner_Later later = new Spawner_Later(pos.getX(), pos.getY(), pos.getZ(), floor, false);
		world.addLater(later);

		return true;
	}

	public static boolean generate_later(final AsyncWorldEditor world, final BlockPos pos, int floor, boolean isBoss) {
		Spawner_Later later = new Spawner_Later(pos.getX(), pos.getY(), pos.getZ(), floor, isBoss);
		world.addLater(later);

		return true;
	}

	public Spawner_Later(int x, int y, int z, int floor, boolean isBoss) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.floor = floor;
		this.isBoss = isBoss;
	}

	@Override
	public Coord getPos() {
		return new Coord(x, y, z);
	}

	@Override
	public void doSomething() {

	}

	@Override
	public void doSomethingInChunk(Chunk c) {
		int bx = this.x % 16;
		int by = this.y;
		int bz = this.z % 16;

		if (bx < 0)
			bx = bx + 16;
		if (bz < 0)
			bz = bz + 16;

		Block block = c.getBlock(bx, by, bz);
		if (isBoss) {
			CastleKing.createSpawner(block);
		} else {
			block.setType(Material.SPAWNER);
			CreatureSpawner cs = (CreatureSpawner) block.getState();
			cs.setSpawnedType(EntityType.ZOMBIE);
			cs.update();
			NamespacedKey f = new NamespacedKey(Main.instance, "castle_floor");
			cs.getPersistentDataContainer().set(f, PersistentDataType.INTEGER, this.floor);
			cs.update(true, false);

			SpawnerDecryAPI.setSpawnerDecry(block, Main.instance, DungeonType.Castle, true);
		}
	}
}
