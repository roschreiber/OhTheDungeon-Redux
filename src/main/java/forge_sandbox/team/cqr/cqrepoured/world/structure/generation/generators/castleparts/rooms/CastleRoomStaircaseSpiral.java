package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

import forge_sandbox.BlockPos;
import forge_sandbox.EnumFacingConstant;
import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.SpiralStaircaseBuilder;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomStaircaseSpiral extends CastleRoomDecoratedBase {
	private BlockFace firstStairSide;
	private Vec3i pillarOffset;

	public CastleRoomStaircaseSpiral(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.STAIRCASE_SPIRAL;
		this.defaultCeiling = false;
		this.defaultFloor = false;

		this.firstStairSide = BlockFace.NORTH;
		this.recalcPillarOffset();
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		this.recalcPillarOffset();
		BlockPos stairCenter = this.roomOrigin.add(this.pillarOffset);
		SpiralStaircaseBuilder stairs = new SpiralStaircaseBuilder(stairCenter, this.firstStairSide,
				Bukkit.createBlockData(dungeon.getMainBlockState()),
				Bukkit.createBlockData(dungeon.getStairBlockState()));

		BlockPos pos;
		BlockData blockToBuild;

		for (int x = 0; x < this.getDecorationLengthX(); x++) {
			for (int z = 0; z < this.getDecorationLengthZ(); z++) {
				for (int y = 0; y < this.height; y++) {
					blockToBuild = Bukkit.createBlockData(Material.AIR);
					pos = this.getInteriorBuildStart().add(x, y, z);

					if (y == 0) {
						blockToBuild = Bukkit.createBlockData(dungeon.getFloorBlockState());
					} else if (stairs.isPartOfStairs(pos)) {
						blockToBuild = stairs.getBlock(pos);
						this.usedDecoPositions.add(pos);
					} else if (y == this.height - 1) {
						blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());
					}

					genArray.addBlockState(pos, blockToBuild, BlockStateGenArray.GenerationPhase.MAIN,
							BlockStateGenArray.EnumPriority.MEDIUM);
				}
			}
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

	public BlockFace getLastStairSide() {
		BlockFace result = BlockFace.NORTH;
		for (int i = 0; i < this.height - 1; i++) {
			result = EnumFacingConstant.rotateY(result);
		}
		return result;
	}

	public int getStairCenterOffsetX() {
		return this.pillarOffset.getX();
	}

	public int getStairCenterOffsetZ() {
		return this.pillarOffset.getZ();
	}

	private void recalcPillarOffset() {
		int centerX = (this.roomLengthX - 1) / 2;
		int centerZ = (this.roomLengthZ - 1) / 2;
		this.pillarOffset = new Vec3i(centerX, 0, centerZ);
	}
}
