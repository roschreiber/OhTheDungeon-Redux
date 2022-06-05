package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomRoofBossEmpty extends CastleRoomBase {
	public CastleRoomRoofBossEmpty(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.ROOF_BOSS_EMPTY;
		this.pathable = false;
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
	}
}
