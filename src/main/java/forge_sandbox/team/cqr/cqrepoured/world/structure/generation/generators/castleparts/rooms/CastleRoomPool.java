package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;

import forge_sandbox.BlockPos;
import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.GenerationTemplate;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomPool extends CastleRoomDecoratedBase {
	public CastleRoomPool(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.POOL;
		this.maxSlotsUsed = 1;
		this.defaultCeiling = true;
		this.defaultFloor = true;
	}

	@Override
	protected void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		int endX = this.getDecorationLengthX() - 1;
		int endZ = this.getDecorationLengthZ() - 1;
		Predicate<Vec3i> northRow = (v -> ((v.getY() == 0) && (v.getZ() == 1)
				&& ((v.getX() >= 1) && (v.getX() <= endX - 1))));
		Predicate<Vec3i> southRow = (v -> ((v.getY() == 0) && (v.getZ() == endZ - 1)
				&& ((v.getX() >= 1) && (v.getX() <= endX - 1))));
		Predicate<Vec3i> westRow = (v -> ((v.getY() == 0) && (v.getX() == 1)
				&& ((v.getZ() >= 1) && (v.getZ() <= endZ - 1))));
		Predicate<Vec3i> eastRow = (v -> ((v.getY() == 0) && (v.getX() == endX - 1)
				&& ((v.getZ() >= 1) && (v.getZ() <= endZ - 1))));
		Predicate<Vec3i> water = (v -> ((v.getY() == 0) && (v.getX() > 1) && (v.getX() < endX - 1) && (v.getZ() > 1)
				&& (v.getZ() < endZ - 1)));

		GenerationTemplate poolRoomTemplate = new GenerationTemplate(this.getDecorationLengthX(),
				this.getDecorationLengthY(), this.getDecorationLengthZ());
		{
			Stairs stair = (Stairs) Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);
			stair.setFacing(BlockFace.SOUTH);
			poolRoomTemplate.addRule(northRow, stair);
		}
		{
			Stairs stair = (Stairs) Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);
			stair.setFacing(BlockFace.NORTH);
			poolRoomTemplate.addRule(southRow, stair);
		}
		{
			Stairs stair = (Stairs) Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);
			stair.setFacing(BlockFace.EAST);
			poolRoomTemplate.addRule(westRow, stair);
		}
		{
			Stairs stair = (Stairs) Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);
			stair.setFacing(BlockFace.WEST);
			poolRoomTemplate.addRule(eastRow, stair);
		}
		poolRoomTemplate.addRule(water, Bukkit.createBlockData(Material.WATER));

		Map<BlockPos, BlockData> genMap = poolRoomTemplate.getGenerationMap(this.getDecorationStartPos(), true);
		genArray.addBlockStateMap(genMap, BlockStateGenArray.GenerationPhase.MAIN,
				BlockStateGenArray.EnumPriority.MEDIUM);
		for (Map.Entry<BlockPos, BlockData> entry : genMap.entrySet()) {
			if (entry.getValue().getMaterial() != Material.AIR) {
				this.usedDecoPositions.add(entry.getKey());
			}
		}
	}

	@Override
	protected Material getFloorBlock(DungeonRandomizedCastle dungeon) {
		return dungeon.getMainBlockState();
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
