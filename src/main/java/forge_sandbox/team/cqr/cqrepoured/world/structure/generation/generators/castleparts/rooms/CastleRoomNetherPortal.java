package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import forge_sandbox.BlockPos;
import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.GenerationTemplate;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomNetherPortal extends CastleRoomDecoratedBase {
	private enum Alignment {
		HORIZONTAL, VERTICAL
	}

	@SuppressWarnings("unused")
	private Alignment portalAlignment;

	public CastleRoomNetherPortal(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.PORTAL;
		this.maxSlotsUsed = 1;
		this.defaultCeiling = true;
		this.defaultFloor = true;
		this.portalAlignment = this.random.nextBoolean() ? Alignment.HORIZONTAL : Alignment.VERTICAL;
	}

	@Override
	protected void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		int endX = this.getDecorationLengthX() - 1;
		int endZ = this.getDecorationLengthZ() - 1;
		int halfX = endX / 2;
		int halfZ = endZ / 2;

		int xStart = halfX - 2;
		int xEnd = halfX + 3;
		int zStart = halfZ - 2;
		int zEnd = halfZ + 2;

		Predicate<Vec3i> firstLayer = (v -> (v.getY() == 0));
		Predicate<Vec3i> northEdge = firstLayer
				.and(v -> (v.getX() >= xStart) && (v.getX() <= xEnd) && (v.getZ() == zStart));
		Predicate<Vec3i> southEdge = firstLayer
				.and(v -> (v.getX() >= xStart) && (v.getX() <= xEnd) && (v.getZ() == zEnd));
		Predicate<Vec3i> westEdge = firstLayer
				.and(v -> (v.getZ() >= zStart) && (v.getZ() <= zEnd) && (v.getX() == xStart));
		Predicate<Vec3i> eastEdge = firstLayer
				.and(v -> (v.getZ() >= zStart) && (v.getZ() <= zEnd) && (v.getX() == xEnd));
		Predicate<Vec3i> portalBot = (v -> (v.getY() == 0) && (v.getZ() == halfZ) && (v.getX() >= xStart + 1)
				&& (v.getX() <= xEnd - 1));
		Predicate<Vec3i> portalTop = (v -> (v.getY() == 4) && (v.getZ() == halfZ) && (v.getX() >= xStart + 1)
				&& (v.getX() <= xEnd - 1));
		Predicate<Vec3i> portalSides = (v -> (v.getY() > 0) && (v.getY() < 4) && (v.getZ() == halfZ)
				&& ((v.getX() == xStart + 1) || (v.getX() == xEnd - 1)));
		Predicate<Vec3i> portalMid = (v -> (v.getY() > 0) && (v.getY() < 4) && (v.getZ() == halfZ)
				&& ((v.getX() > xStart + 1) && (v.getX() < xEnd - 1)));
		Predicate<Vec3i> portal = portalBot.or(portalTop).or(portalSides);
		Predicate<Vec3i> platform = portal.negate().and(firstLayer.and(v -> (v.getX() >= xStart + 1)
				&& (v.getX() <= xEnd - 1) && (v.getZ() >= zStart + 1) && (v.getZ() <= zEnd - 1)));

		GenerationTemplate portalRoomTemplate = new GenerationTemplate(this.getDecorationLengthX(),
				this.getDecorationLengthY(), this.getDecorationLengthZ());
		{
			BlockData bd = Bukkit.createBlockData(dungeon.getWoodStairBlockState());
			Directional dir = (Directional) bd;
			dir.setFacing(BlockFace.SOUTH);
			portalRoomTemplate.addRule(northEdge, dir);
		}
		{
			BlockData bd = Bukkit.createBlockData(dungeon.getWoodStairBlockState());
			Directional dir = (Directional) bd;
			dir.setFacing(BlockFace.NORTH);
			portalRoomTemplate.addRule(southEdge, dir);
		}
		{
			BlockData bd = Bukkit.createBlockData(dungeon.getWoodStairBlockState());
			Directional dir = (Directional) bd;
			dir.setFacing(BlockFace.EAST);
			portalRoomTemplate.addRule(westEdge, dir);
		}
		{
			BlockData bd = Bukkit.createBlockData(dungeon.getWoodStairBlockState());
			Directional dir = (Directional) bd;
			dir.setFacing(BlockFace.WEST);
			portalRoomTemplate.addRule(eastEdge, dir);
		}
		portalRoomTemplate.addRule(platform, Bukkit.createBlockData(dungeon.getMainBlockState()));
		portalRoomTemplate.addRule(portal, Bukkit.createBlockData(Material.OBSIDIAN));
		portalRoomTemplate.addRule(portalMid, Bukkit.createBlockData(Material.NETHER_PORTAL));

		Map<BlockPos, BlockData> genMap = portalRoomTemplate.getGenerationMap(this.getDecorationStartPos(), true);
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
