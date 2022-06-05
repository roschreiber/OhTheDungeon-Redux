package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;

public class DecoBlockRotating extends DecoBlockBase {
	protected final BlockFace DEFAULT_SIDE = BlockFace.NORTH;
	protected BlockFace initialFacing;

	protected DecoBlockRotating(int x, int y, int z, BlockData initialState, BlockFace initialFacing,
			BlockStateGenArray.GenerationPhase generationPhase) {
		super(x, y, z, initialState, generationPhase);
		this.initialFacing = initialFacing;
	}

	protected DecoBlockRotating(Vec3i offset, BlockData initialState, BlockFace initialFacing,
			BlockStateGenArray.GenerationPhase generationPhase) {
		super(offset, initialState, generationPhase);
		this.initialFacing = initialFacing;
	}

	@Override
	protected BlockData getState(BlockFace side) {
		int rotations = DungeonGenUtils.getCWRotationsBetween(this.DEFAULT_SIDE, side);
		Directional dir = (Directional) this.blockState;
		dir.setFacing(DungeonGenUtils.rotateFacingNTimesAboutY(this.initialFacing, rotations));
		return dir;
	}
}
