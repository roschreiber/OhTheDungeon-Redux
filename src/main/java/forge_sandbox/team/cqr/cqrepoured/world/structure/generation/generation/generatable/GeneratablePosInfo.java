package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.generatable;

import forge_sandbox.BlockPos;
import forge_sandbox.BlockPos.MutableBlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockPlacingHelper.IBlockInfo;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.GeneratableDungeon;
import otd.lib.async.AsyncWorldEditor;

public abstract class GeneratablePosInfo implements IGeneratable, IBlockInfo {

	private static final MutableBlockPos MUTABLE = new MutableBlockPos();
	private final int x;
	private final int y;
	private final int z;

	protected GeneratablePosInfo(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	protected GeneratablePosInfo(BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void generate(AsyncWorldEditor world, GeneratableDungeon dungeon) {
		this.place(world, dungeon);
	}

	@Override
	public boolean place(AsyncWorldEditor world, GeneratableDungeon dungeon) {
		return this.place(world, MUTABLE.setPos(this.x, this.y, this.z), dungeon);
	}

	protected abstract boolean place(AsyncWorldEditor world, BlockPos pos, GeneratableDungeon dungeon);

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
