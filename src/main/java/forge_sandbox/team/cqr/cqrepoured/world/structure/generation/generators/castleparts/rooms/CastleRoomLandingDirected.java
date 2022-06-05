package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import forge_sandbox.BlockPos;
import forge_sandbox.EnumFacingConstant;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomLandingDirected extends CastleRoomBase {
	protected int openingWidth;
	protected int openingSeparation;
	protected int stairZ;
	protected BlockFace stairStartSide;

	public CastleRoomLandingDirected(int sideLength, int height, CastleRoomStaircaseDirected stairsBelow, int floor,
			Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.LANDING_DIRECTED;
		this.openingWidth = stairsBelow.getUpperStairWidth();
		this.stairZ = stairsBelow.getUpperStairEndZ() + 1;
		this.openingSeparation = stairsBelow.getCenterStairWidth();
		this.stairStartSide = stairsBelow.getDoorSide();
		this.defaultCeiling = true;
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		BlockData blockToBuild;

		// If stairs are facing to the east or west, need to flip the build lengths
		// since we are essentially
		// generating a room facing south and then rotating it
		int lenX = EnumFacingConstant.getAxis(this.stairStartSide) == EnumFacingConstant.Axis.Z
				? this.getDecorationLengthX()
				: this.getDecorationLengthZ();
		int lenZ = EnumFacingConstant.getAxis(this.stairStartSide) == EnumFacingConstant.Axis.Z
				? this.getDecorationLengthZ()
				: this.getDecorationLengthX();

		for (int x = 0; x < lenX - 1; x++) {
			for (int z = 0; z < lenZ - 1; z++) {
				for (int y = 0; y < this.height - 1; y++) {
					blockToBuild = Bukkit.createBlockData(Material.AIR);
					if (y == 0) {
						if (z > this.stairZ) {
							blockToBuild = Bukkit.createBlockData(dungeon.getFloorBlockState());
						} else if (x < this.openingWidth || ((x >= this.openingSeparation + this.openingWidth)
								&& (x < this.openingSeparation + this.openingWidth * 2))) {
							if (z == this.stairZ) {
								BlockFace stairFacing = DungeonGenUtils.rotateFacingNTimesAboutY(BlockFace.SOUTH,
										DungeonGenUtils.getCWRotationsBetween(BlockFace.SOUTH, this.stairStartSide));
								BlockData bd = Bukkit.createBlockData(dungeon.getWoodStairBlockState());
								Directional dir = (Directional) bd;
								dir.setFacing(stairFacing);
								blockToBuild = dir;
							}
						} else {
							blockToBuild = Bukkit.createBlockData(dungeon.getFloorBlockState());
						}
					}

					genArray.addBlockState(this.getRotatedPlacement(x, y, z, this.stairStartSide), blockToBuild,
							BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
				}
			}
		}
	}

	@Override
	public boolean canBuildDoorOnSide(BlockFace side) {
		// Really only works on this side, could add logic to align the doors for other
		// sides later
		return (side == this.stairStartSide);
	}

	@Override
	public boolean reachableFromSide(BlockFace side) {
		return (side == this.stairStartSide || side == this.stairStartSide.getOppositeFace());
	}
}
