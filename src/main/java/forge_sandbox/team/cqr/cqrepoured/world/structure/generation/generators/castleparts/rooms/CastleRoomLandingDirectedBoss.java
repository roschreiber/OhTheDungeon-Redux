package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.block.BlockFace;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomLandingDirectedBoss extends CastleRoomLandingDirected {
	public CastleRoomLandingDirectedBoss(int sideLength, int height, CastleRoomStaircaseDirected stairsBelow, int floor,
			Random rand) {
		super(sideLength, height, stairsBelow, floor, rand);
	}

	@Override
	public void generate(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		super.generate(castleOrigin, genArray, dungeon);
	}

	@Override
	public boolean canBuildInnerWallOnSide(BlockFace side) {
		return side != this.stairStartSide;
	}
}
