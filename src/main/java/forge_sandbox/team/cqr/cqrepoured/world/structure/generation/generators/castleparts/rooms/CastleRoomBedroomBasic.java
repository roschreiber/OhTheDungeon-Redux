package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.Material;

import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.RoomDecorTypes;

public class CastleRoomBedroomBasic extends CastleRoomGenericBase {
//	private EnumDyeColor carpetColor;

	public CastleRoomBedroomBasic(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.BEDROOM_BASIC;
		this.maxSlotsUsed = 2;
		this.defaultCeiling = true;
		this.defaultFloor = true;

		this.decoSelector.registerEdgeDecor(RoomDecorTypes.NONE, 10);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.SHELF, 2);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.PLANKS, 2);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.TABLE_1x1, 2);
		this.decoSelector.registerEdgeDecor(RoomDecorTypes.BED, 3);
	}

	@Override
	protected Material getFloorBlock(DungeonRandomizedCastle dungeon) {
		return dungeon.getMainBlockState();
	}
}
