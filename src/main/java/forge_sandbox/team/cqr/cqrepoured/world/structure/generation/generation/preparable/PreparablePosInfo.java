package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.preparable;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.DungeonPlacement;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.generatable.GeneratablePosInfo;
import otd.lib.async.AsyncWorldEditor;

public abstract class PreparablePosInfo implements IPreparable<GeneratablePosInfo> {

	private final int x;
	private final int y;
	private final int z;

	protected PreparablePosInfo(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public GeneratablePosInfo prepareNormal(AsyncWorldEditor world, DungeonPlacement placement) {
		BlockPos pos = placement.transform(this.x, this.y, this.z);
		return this.prepare(world, placement, pos);
	}

	@Override
	public GeneratablePosInfo prepareDebug(AsyncWorldEditor world, DungeonPlacement placement) {
		BlockPos pos = placement.transform(this.x, this.y, this.z);
		return this.prepareDebug(world, placement, pos);
	}

	protected abstract GeneratablePosInfo prepare(AsyncWorldEditor world, DungeonPlacement placement, BlockPos pos);

	protected abstract GeneratablePosInfo prepareDebug(AsyncWorldEditor world, DungeonPlacement placement,
			BlockPos pos);

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public int getChunkX() {
		return this.x >> 4;
	}

	public int getChunkY() {
		return this.y >> 4;
	}

	public int getChunkZ() {
		return this.z >> 4;
	}
}
