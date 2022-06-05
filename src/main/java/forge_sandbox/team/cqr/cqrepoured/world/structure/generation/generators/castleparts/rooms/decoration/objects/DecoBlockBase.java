package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class DecoBlockBase {
	public Vec3i offset;
	public BlockData blockState;
	public BlockStateGenArray.GenerationPhase genPhase;

	protected DecoBlockBase(int x, int y, int z, BlockData block, BlockStateGenArray.GenerationPhase generationPhase) {
		this.offset = new Vec3i(x, y, z);
		this.blockState = block;
		this.genPhase = generationPhase;
	}

	protected DecoBlockBase(Vec3i offset, BlockData block, BlockStateGenArray.GenerationPhase generationPhase) {
		this.offset = offset;
		this.blockState = block;
		this.genPhase = generationPhase;
	}

	protected BlockData getState(BlockFace side) {
		return this.blockState;
	}

	public BlockStateGenArray.GenerationPhase getGenPhase() {
		return this.genPhase;
	}
}
