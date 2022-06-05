package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.block.BlockFace;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomBossLandingEmpty extends CastleRoomDecoratedBase {
	@SuppressWarnings("unused")
	private BlockFace doorSide;

	public CastleRoomBossLandingEmpty(int sideLength, int height, BlockFace doorSide, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.LANDING_BOSS;
		this.pathable = false;
		this.doorSide = doorSide;
		this.defaultCeiling = true;
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
	}

	@Override
	boolean shouldBuildEdgeDecoration() {
		return false;
	}

	@Override
	boolean shouldBuildWallDecoration() {
		return true;
	}

	@Override
	boolean shouldBuildMidDecoration() {
		return false;
	}

	@Override
	boolean shouldAddSpawners() {
		return true;
	}

	@Override
	boolean shouldAddChests() {
		return false;
	}
}
