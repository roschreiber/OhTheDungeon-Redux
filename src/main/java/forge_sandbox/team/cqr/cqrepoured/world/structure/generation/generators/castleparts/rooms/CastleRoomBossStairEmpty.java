package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.block.BlockFace;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomBossStairEmpty extends CastleRoomDecoratedBase {
	@SuppressWarnings("unused")
	private BlockFace doorSide;

	public CastleRoomBossStairEmpty(int sideLength, int height, BlockFace doorSide, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.STAIRCASE_BOSS;
		this.pathable = true;
		this.doorSide = doorSide;
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
		return false;
	}

	@Override
	boolean shouldAddChests() {
		return false;
	}
}
