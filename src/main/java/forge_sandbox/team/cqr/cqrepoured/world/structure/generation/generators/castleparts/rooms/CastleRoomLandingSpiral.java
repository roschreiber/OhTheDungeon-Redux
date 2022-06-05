package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

import forge_sandbox.BlockPos;
import forge_sandbox.EnumFacingConstant;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.SpiralStaircaseBuilder;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomLandingSpiral extends CastleRoomDecoratedBase {
	private CastleRoomStaircaseSpiral stairsBelow;

	public CastleRoomLandingSpiral(int sideLength, int height, CastleRoomStaircaseSpiral stairsBelow, int floor,
			Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.LANDING_SPIRAL;
		this.stairsBelow = stairsBelow;
		this.defaultCeiling = true;
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		BlockPos pos;
		BlockData blockToBuild;
		BlockPos pillarStart = this.roomOrigin.add(this.stairsBelow.getStairCenterOffsetX(), 0,
				this.stairsBelow.getStairCenterOffsetZ());
		BlockFace firstStairSide = EnumFacingConstant.rotateY(this.stairsBelow.getLastStairSide());

		SpiralStaircaseBuilder stairs = new SpiralStaircaseBuilder(pillarStart, firstStairSide,
				dungeon.getMainBlockState(), dungeon.getStairBlockState());

		for (int x = 0; x < this.getDecorationLengthX(); x++) {
			for (int z = 0; z < this.getDecorationLengthZ(); z++) {
				for (int y = 0; y < this.getDecorationLengthY(); y++) {
					blockToBuild = Bukkit.createBlockData(Material.AIR);
					pos = this.getInteriorBuildStart().add(x, y, z);

					// continue stairs for 1 layer through floor
					if (y == 0) {
						if (stairs.isPartOfStairs(pos)) {
							blockToBuild = stairs.getBlock(pos);
							this.usedDecoPositions.add(pos);
						} else {
							blockToBuild = Bukkit.createBlockData(dungeon.getFloorBlockState());
						}
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
}
