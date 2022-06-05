package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation;

import forge_sandbox.BlockPos;
import forge_sandbox.BlockPos.MutableBlockPos;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.inhabitants.DungeonInhabitant;
import forge_sandbox.Vec3d;

public class DungeonPlacement {

	public static class MutableVec3d {

		public double x;
		public double y;
		public double z;

		public MutableVec3d() {

		}

		public MutableVec3d(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public MutableVec3d set(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
			return this;
		}

	}

	private static final ThreadLocal<MutableBlockPos> LOCAL_MUTABLE_BLOCKPOS = ThreadLocal
			.withInitial(MutableBlockPos::new);
	private static final ThreadLocal<MutableVec3d> LOCAL_MUTABLE_VEC3D = ThreadLocal.withInitial(MutableVec3d::new);
	private final BlockPos pos;
	private final BlockPos partPos;

	public DungeonPlacement(BlockPos pos, BlockPos partPos, DungeonInhabitant inhabitant) {
		this.pos = pos;
		this.partPos = partPos;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public BlockPos getPartPos() {
		return this.partPos;
	}

	public MutableBlockPos transform(BlockPos pos) {
		return transform(pos.getX(), pos.getY(), pos.getZ(), this.partPos);
	}

	public MutableBlockPos transform(int x, int y, int z) {
		return transform(x, y, z, this.partPos);
	}

	public static MutableBlockPos transform(int x, int y, int z, BlockPos origin) {
		return LOCAL_MUTABLE_BLOCKPOS.get().setPos(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
	}

	public MutableVec3d transform(Vec3d vec) {
		return transform(vec.x, vec.y, vec.z, this.partPos);
	}

	public MutableVec3d transform(double x, double y, double z) {
		return transform(x, y, z, this.partPos);
	}

	public static MutableVec3d transform(double x, double y, double z, BlockPos origin) {

		return LOCAL_MUTABLE_VEC3D.get().set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
	}
}
