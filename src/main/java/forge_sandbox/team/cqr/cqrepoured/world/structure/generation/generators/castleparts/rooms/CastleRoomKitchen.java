package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.RoomDecorTypes;
import otd.util.OTDLoottables;

public class CastleRoomKitchen extends CastleRoomGenericBase {
	public CastleRoomKitchen(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.KITCHEN;
		this.maxSlotsUsed = 2;
		this.defaultCeiling = true;
		this.defaultFloor = true;

		this.decoSelector.registerEdgeDecor(RoomDecorTypes.NONE, 4);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.SHELF, 3);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.TABLE_1x1, 2);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.CRAFTING_TABLE, 1);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.FURNACE, 1);

		this.decoSelector.registerMidDecor(RoomDecorTypes.NONE, 20);
		this.decoSelector.registerMidDecor(RoomDecorTypes.TABLE_2x2, 1);
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		super.generateRoom(castleOrigin, genArray, dungeon);
	}

	@Override
	public OTDLoottables getChestIDs() {
		return OTDLoottables.CHESTS_FOOD;
	}
}
