package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.generatable;

import org.bukkit.block.data.BlockData;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockPlacingHelper;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.GeneratableDungeon;
import otd.lib.async.AsyncWorldEditor;

public class GeneratableBlockInfo extends GeneratablePosInfo {

	private final BlockData state;

	public GeneratableBlockInfo(int x, int y, int z, BlockData state) {
		super(x, y, z);
		this.state = state;
	}

	public GeneratableBlockInfo(BlockPos pos, BlockData state) {
		this(pos.getX(), pos.getY(), pos.getZ(), state);
	}

	@Override
	protected boolean place(AsyncWorldEditor world, BlockPos pos, GeneratableDungeon dungeon) {
		if (!BlockPlacingHelper.setBlockState(world, pos, this.state)) {
			return false;
		}
		return true;
	}

	public BlockData getState() {
		return this.state;
	}
}
