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

public class CastleRoomTowerSquare extends CastleRoomBase {
	private BlockFace connectedSide;
	private int stairYOffset;
	private Vec3i pillarOffset;
	private BlockFace firstStairSide;

	public CastleRoomTowerSquare(int sideLength, int height, BlockFace connectedSide, int towerSize,
			CastleRoomTowerSquare towerBelow, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.TOWER_SQUARE;
		this.connectedSide = connectedSide;
		this.defaultFloor = false;
		this.defaultCeiling = false;
		this.pathable = false;
		this.isTower = true;

		if (towerBelow != null) {
			this.firstStairSide = EnumFacingConstant.rotateY(towerBelow.getLastStairSide());
			this.stairYOffset = 0; // stairs must continue from room below so start building in the floor
		} else {
			this.firstStairSide = EnumFacingConstant.rotateY(this.connectedSide); // makes stairs face door
			this.stairYOffset = 1; // account for 1 layer of floor
		}

		this.pillarOffset = new Vec3i((this.roomLengthX / 2), this.stairYOffset, (this.roomLengthZ / 2));
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
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
					pos = this.getNonWallStartPos().add(x, y, z);

					if (stairs.isPartOfStairs(pos)) {
						blockToBuild = stairs.getBlock(pos);
					} else if (y == 0) {
						blockToBuild = Bukkit.createBlockData(dungeon.getFloorBlockState());
					} else if (y == this.height - 1) {
						blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());
					}

					if (blockToBuild.getMaterial() != Material.AIR) {
						this.usedDecoPositions.add(pos);
					}

					genArray.addBlockState(pos, blockToBuild, BlockStateGenArray.GenerationPhase.MAIN,
							BlockStateGenArray.EnumPriority.MEDIUM);
				}
			}
		}
	}

	public BlockFace getLastStairSide() {
		BlockFace result = this.firstStairSide;
		for (int i = this.stairYOffset; i < this.height - 1; i++) {
			result = EnumFacingConstant.rotateY(result);
		}
		return result;
	}

	@Override
	public boolean isTower() {
		return true;
	}

}
