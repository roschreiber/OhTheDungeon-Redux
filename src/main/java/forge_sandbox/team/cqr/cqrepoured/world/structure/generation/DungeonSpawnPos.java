package forge_sandbox.team.cqr.cqrepoured.world.structure.generation;

import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import otd.lib.async.AsyncWorldEditor;

public class DungeonSpawnPos {

	private final int x;
	private final int z;
	private final boolean spawnPointRelative;

	public DungeonSpawnPos(int x, int z, boolean spawnPointRelative) {
		this.x = x;
		this.z = z;
		this.spawnPointRelative = spawnPointRelative;
	}

	public int getX() {
		return this.x;
	}

	public int getZ() {
		return this.z;
	}

	public boolean isSpawnPointRelative() {
		return this.spawnPointRelative;
	}

	public boolean isInChunk(AsyncWorldEditor world, int chunkX, int chunkZ) {
		int i = this.spawnPointRelative ? (this.x + DungeonGenUtils.getSpawnX(world)) >> 4 : this.x >> 4;
		int j = this.spawnPointRelative ? (this.z + DungeonGenUtils.getSpawnZ(world)) >> 4 : this.z >> 4;
		return i == chunkX && j == chunkZ;
	}

	public int getX(AsyncWorldEditor world) {
		return this.spawnPointRelative ? this.x + DungeonGenUtils.getSpawnX(world) : this.x;
	}

	public int getZ(AsyncWorldEditor world) {
		return this.spawnPointRelative ? this.z + DungeonGenUtils.getSpawnZ(world) : this.z;
	}

}
