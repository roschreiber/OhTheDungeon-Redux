package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.preparable;

import org.bukkit.block.data.BlockData;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.DungeonPlacement;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.generatable.GeneratableBlockInfo;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.generatable.GeneratablePosInfo;
import otd.lib.async.AsyncWorldEditor;

public class PreparableBlockInfo extends PreparablePosInfo {

	private final BlockData state;

	public PreparableBlockInfo(BlockPos pos, BlockData state) {
		this(pos.getX(), pos.getY(), pos.getZ(), state);
	}

	public PreparableBlockInfo(int x, int y, int z, BlockData state) {
		super(x, y, z);
		this.state = state;
	}

	@Override
	protected GeneratablePosInfo prepare(AsyncWorldEditor world, DungeonPlacement placement, BlockPos pos) {
		BlockData transformedState = this.state;

		return this.prepare(world, placement, pos, transformedState);
	}

	@Override
	protected GeneratablePosInfo prepareDebug(AsyncWorldEditor world, DungeonPlacement placement, BlockPos pos) {
		BlockData transformedState = this.state;

		return this.prepareDebug(world, placement, pos, transformedState);
	}

	protected GeneratablePosInfo prepare(AsyncWorldEditor world, DungeonPlacement placement, BlockPos pos,
			BlockData state) {
		return new GeneratableBlockInfo(pos, state);
	}

	protected GeneratablePosInfo prepareDebug(AsyncWorldEditor world, DungeonPlacement placement, BlockPos pos,
			BlockData state) {
		return new GeneratableBlockInfo(pos, state);
	}

	public BlockData getState() {
		return this.state;
	}
}