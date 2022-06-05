package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomWalkableRoof extends CastleRoomBase {
	public CastleRoomWalkableRoof(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.WALKABLE_ROOF;
		this.pathable = false;
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		for (BlockPos pos : this.getDecorationArea()) {
			genArray.addBlockState(pos, Bukkit.createBlockData(Material.AIR), BlockStateGenArray.GenerationPhase.MAIN,
					BlockStateGenArray.EnumPriority.LOWEST);
		}
	}

	@Override
	protected boolean hasFloor() {
		return false;
	}
}
