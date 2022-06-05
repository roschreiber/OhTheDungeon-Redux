package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.RoomDecorTypes;

public class CastleRoomAlchemyLab extends CastleRoomGenericBase {
	public CastleRoomAlchemyLab(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.ALCHEMY_LAB;
		this.maxSlotsUsed = 2;
		this.defaultCeiling = true;
		this.defaultFloor = true;

		this.decoSelector.registerEdgeDecor(RoomDecorTypes.NONE, 5);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.SHELF, 2);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.CAULDRON, 1);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.BREW_STAND, 1);

		this.decoSelector.registerMidDecor(RoomDecorTypes.NONE, 15);
		this.decoSelector.registerMidDecor(RoomDecorTypes.WATER_BASIN, 1);
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		super.generateRoom(castleOrigin, genArray, dungeon);
	}
}
