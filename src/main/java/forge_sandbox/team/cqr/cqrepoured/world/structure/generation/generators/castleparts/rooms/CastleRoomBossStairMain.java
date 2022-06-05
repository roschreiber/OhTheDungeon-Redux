package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import forge_sandbox.BlockPos;
import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomBossStairMain extends CastleRoomDecoratedBase {
	private BlockFace doorSide;
	private int numRotations;
	private static final int ROOMS_LONG = 2;
	private static final int ROOMS_SHORT = 1;
	private static final int TOP_LANDING_BUFFER_Z = 3;
	private static final int MAIN_LANDING_Z = 2;
	private static final int MAIN_LANDING_X = 7;
	private static final int UPPER_STAIR_X = 3;
	private static final int LOWER_LANDING_Z = 2;
	private static final int LOWER_STAIRS_Z = 2;
	private static final int LOWER_STAIRS_LEN = 2;
	private static final int FLOOR_HEIGHT = 1;
	private static final int MID_STAIR_LENGTH = 2;

	private int endX;
	private int lenX;
	private int endZ;
	private int lenZ;
	private int maxHeightIdx;
	private int topStairLength;

	private int mainLandingXStartIdx;
	private int mainLandingXEndIdx;
	private int mainLandingZStartIdx;

	private int upperStairXStartIdx;
	private int upperStairXEndIdx;

	private int lowerStair1XStartIdx;
	private int lowerStair1XEndIdx;
	private int lowerStair2XStartIdx;
	private int lowerStair2XEndIdx;

	private int lowerLanding1XStartIdx;
	private int lowerLanding1XEndIdx;
	private int lowerLanding2XStartIdx;
	private int lowerLanding2XEndIdx;
	private int lowerLandingZStartIdx;
	private int midStairsZStartIdx;
	private int mainLandingMaxHeightIdx;
	private int lowerLandingMaxHeightIdx;

	public CastleRoomBossStairMain(int sideLength, int height, BlockFace doorSide, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.STAIRCASE_BOSS;

		this.doorSide = doorSide;
		this.numRotations = DungeonGenUtils.getCWRotationsBetween(BlockFace.NORTH, this.doorSide);

		this.endX = ROOMS_LONG * sideLength; // 1 wall space in between them
		this.lenX = this.endX + 1;
		this.endZ = ROOMS_SHORT * sideLength - 1;
		this.lenZ = this.endZ + 1;
		this.maxHeightIdx = height - 1;

		this.topStairLength = this.lenZ - TOP_LANDING_BUFFER_Z - MAIN_LANDING_Z;
		final int lowerStairLength = height - FLOOR_HEIGHT - MID_STAIR_LENGTH - this.topStairLength;

		this.mainLandingXStartIdx = sideLength - 4;
		this.mainLandingXEndIdx = this.mainLandingXStartIdx + MAIN_LANDING_X - 1;
		this.mainLandingZStartIdx = this.endZ - MAIN_LANDING_Z + 1;

		this.upperStairXStartIdx = sideLength - 2;
		this.upperStairXEndIdx = this.upperStairXStartIdx + UPPER_STAIR_X - 1;

		this.lowerLanding1XStartIdx = this.upperStairXStartIdx - 2;
		this.lowerLanding1XEndIdx = this.lowerLanding1XStartIdx + 1;
		this.lowerLanding2XStartIdx = this.upperStairXEndIdx + 1;
		this.lowerLanding2XEndIdx = this.lowerLanding2XStartIdx + 1;

		this.lowerStair1XStartIdx = this.lowerLanding1XStartIdx - lowerStairLength;
		this.lowerStair1XEndIdx = this.lowerStair1XStartIdx + lowerStairLength - 1;
		this.lowerStair2XStartIdx = this.lowerLanding2XEndIdx + 1;
		this.lowerStair2XEndIdx = this.lowerStair2XStartIdx + lowerStairLength - 1;

		this.midStairsZStartIdx = this.mainLandingZStartIdx - LOWER_STAIRS_Z;
		this.lowerLandingZStartIdx = this.midStairsZStartIdx - LOWER_LANDING_Z;

		this.mainLandingMaxHeightIdx = height - this.topStairLength - 1;
		this.lowerLandingMaxHeightIdx = this.mainLandingMaxHeightIdx - LOWER_STAIRS_LEN;
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		Vec3i offset;

		for (int x = 0; x <= this.endX; x++) {
			for (int y = 0; y < this.height; y++) {
				for (int z = 0; z <= this.endZ; z++) {
					BlockData blockToBuild = this.getBlockToBuild(dungeon, x, y, z);

					offset = DungeonGenUtils.rotateMatrixOffsetCW(new Vec3i(x, y, z), this.lenX, this.lenZ,
							this.numRotations);
					genArray.addBlockState(this.roomOrigin.add(offset), blockToBuild,
							BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);

					if (blockToBuild.getMaterial() != Material.AIR) {
						this.usedDecoPositions.add(this.roomOrigin.add(offset));
					}
				}
			}
		}
	}

	private BlockData getBlockToBuild(DungeonRandomizedCastle dungeon, int x, int y, int z) {
		BlockData blockToBuild = Bukkit.createBlockData(Material.AIR);

		if (y == 0) {
			blockToBuild = Bukkit.createBlockData(this.getFloorBlock(dungeon));
		} else if (y == this.maxHeightIdx) {
			if (x >= this.upperStairXStartIdx && x <= this.upperStairXEndIdx) {
				if (z == TOP_LANDING_BUFFER_Z) {
					BlockFace stairFacing = DungeonGenUtils.rotateFacingNTimesAboutY(BlockFace.NORTH,
							this.numRotations);
					BlockData bd = Bukkit.createBlockData(dungeon.getStairBlockState());
					Directional dir = (Directional) bd;
					dir.setFacing(stairFacing);
					return dir;
				} else if (z < TOP_LANDING_BUFFER_Z) {
					return Bukkit.createBlockData(dungeon.getMainBlockState());
				}
			} else {
				blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());
			}
		} else if ((x >= this.mainLandingXStartIdx && x <= this.mainLandingXEndIdx)
				&& (z >= this.mainLandingZStartIdx)) {
			blockToBuild = this.getMainLandingBlock(x, y, z);
		} else if ((x >= this.upperStairXStartIdx && x <= this.upperStairXEndIdx)
				&& ((z >= TOP_LANDING_BUFFER_Z) && (z <= TOP_LANDING_BUFFER_Z + this.topStairLength - 1))) {
			blockToBuild = this.getUpperStairBlock(x, y, z);
		} else if ((x >= this.lowerLanding1XStartIdx && x <= this.lowerLanding1XEndIdx)
				|| (x >= this.lowerLanding2XStartIdx && x <= this.lowerLanding2XEndIdx)) {
			if (z == this.midStairsZStartIdx || z == this.midStairsZStartIdx + 1) {
				blockToBuild = this.getMidStairBlock(x, y, z);
			} else if (z == this.lowerLandingZStartIdx || z == this.lowerLandingZStartIdx + 1) {
				blockToBuild = this.getLowerLandingBlock(x, y, z);
			}
		} else if ((x >= this.lowerStair1XStartIdx && x <= this.lowerStair1XEndIdx)
				&& (z == this.lowerLandingZStartIdx || z == this.lowerLandingZStartIdx + 1)) {
			blockToBuild = this.getLowerStair1Block(x, y, z);
		} else if ((x >= this.lowerStair2XStartIdx && x <= this.lowerStair2XEndIdx)
				&& (z == this.lowerLandingZStartIdx || z == this.lowerLandingZStartIdx + 1)) {
			blockToBuild = this.getLowerStair2Block(x, y, z);
		}

		return blockToBuild;
	}

	private BlockData getLowerStair1Block(int x, int y, int z) {
		if (y == this.lowerLandingMaxHeightIdx - (this.lowerStair1XEndIdx - x)) {
			BlockFace stairFacing = DungeonGenUtils.rotateFacingNTimesAboutY(BlockFace.EAST, this.numRotations);
			BlockData bd = Bukkit.createBlockData(Material.STONE_BRICKS);
			Directional dir = (Directional) bd;
			dir.setFacing(stairFacing);
			return dir;
		} else if (y <= this.lowerLandingMaxHeightIdx - (this.lowerLanding1XEndIdx - x)) {
			return Bukkit.createBlockData(Material.STONE_BRICKS);
		} else {
			return Bukkit.createBlockData(Material.AIR);
		}
	}

	private BlockData getLowerStair2Block(int x, int y, int z) {
		if (y == this.lowerLandingMaxHeightIdx - (x - this.lowerStair2XStartIdx)) {
			BlockFace stairFacing = DungeonGenUtils.rotateFacingNTimesAboutY(BlockFace.WEST, this.numRotations);
			BlockData bd = Bukkit.createBlockData(Material.STONE_BRICKS);
			Directional dir = (Directional) bd;
			dir.setFacing(stairFacing);
			return dir;
		} else if (y <= this.lowerLandingMaxHeightIdx - (x - this.lowerStair2XStartIdx)) {
			return Bukkit.createBlockData(Material.STONE_BRICKS);
		} else {
			return Bukkit.createBlockData(Material.AIR);
		}
	}

	private BlockData getLowerLandingBlock(int x, int y, int z) {
		if (y >= 1 && y <= this.lowerLandingMaxHeightIdx) {
			return Bukkit.createBlockData(Material.STONE_BRICKS);
		} else {
			return Bukkit.createBlockData(Material.AIR);
		}
	}

	private BlockData getMidStairBlock(int x, int y, int z) {
		if (y == this.mainLandingMaxHeightIdx - (this.endZ - z - MAIN_LANDING_Z)) {
			BlockFace stairFacing = DungeonGenUtils.rotateFacingNTimesAboutY(BlockFace.SOUTH, this.numRotations);
			BlockData bd = Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);
			Directional dir = (Directional) bd;
			dir.setFacing(stairFacing);
			return dir;
		} else if (y < this.mainLandingMaxHeightIdx - (this.endZ - z - MAIN_LANDING_Z)) {
			return Bukkit.createBlockData(Material.STONE_BRICKS);
		} else {
			return Bukkit.createBlockData(Material.AIR);
		}
	}

	private BlockData getUpperStairBlock(int x, int y, int z) {
		if (y == (this.maxHeightIdx - (z - TOP_LANDING_BUFFER_Z))) {
			BlockFace stairFacing = DungeonGenUtils.rotateFacingNTimesAboutY(BlockFace.NORTH, this.numRotations);
			BlockData bd = Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);
			Directional dir = (Directional) bd;
			dir.setFacing(stairFacing);
			return dir;
		} else if ((y < this.maxHeightIdx - (z - TOP_LANDING_BUFFER_Z))) {
			return Bukkit.createBlockData(Material.STONE_BRICKS);
		} else {
			return Bukkit.createBlockData(Material.AIR);
		}
	}

	public BlockData getMainLandingBlock(int x, int y, int z) {
		if (y >= 1 && y <= this.mainLandingMaxHeightIdx) {
			return Bukkit.createBlockData(Material.STONE_BRICKS);
		} else {
			return Bukkit.createBlockData(Material.AIR);
		}
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

	@Override
	public boolean canBuildDoorOnSide(BlockFace side) {
		return true;
	}

	@Override
	public boolean reachableFromSide(BlockFace side) {
		return true;
	}
}
