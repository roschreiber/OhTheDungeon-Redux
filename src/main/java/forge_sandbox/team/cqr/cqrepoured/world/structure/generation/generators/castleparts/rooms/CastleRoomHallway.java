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
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.IRoomDecor;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.RoomDecorTypes;
import otd.lib.async.AsyncWorldEditor;

public class CastleRoomHallway extends CastleRoomGenericBase {
	public enum Alignment {
		VERTICAL, HORIZONTAL;

		@SuppressWarnings("unused")
		private boolean canHaveInteriorWall(BlockFace side) {
			if (this == VERTICAL) {
				return (side == BlockFace.WEST || side == BlockFace.EAST);
			} else {
				return (side == BlockFace.NORTH || side == BlockFace.SOUTH);
			}
		}
	}

	@SuppressWarnings("unused")
	private Alignment alignment;
	BlockFace patternStartFacing;

	public CastleRoomHallway(int sideLength, int height, Alignment alignment, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.HALLWAY;
		this.alignment = alignment;
		this.defaultFloor = true;
		this.defaultCeiling = true;
		this.patternStartFacing = EnumFacingConstant.HORIZONTALS[this.random
				.nextInt(EnumFacingConstant.HORIZONTALS.length)];
	}

	@Override
	protected void generateDefaultFloor(BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		for (int z = 0; z < this.getDecorationLengthZ(); z++) {
			for (int x = 0; x < this.getDecorationLengthX(); x++) {
				BlockPos pos = this.getNonWallStartPos().add(x, 0, z);
				BlockData tcBlock = Bukkit.createBlockData(Material.GRAY_GLAZED_TERRACOTTA);
				BlockFace tcFacing;

				// Terracotta patterns are formed in a 2x2 square from the pattern (going
				// clockwise) N E S W
				// So create that pattern here given some starting facing
				if (pos.getZ() % 2 == 0) {
					if (pos.getX() % 2 == 0) {
						tcFacing = this.patternStartFacing;
					} else {
						tcFacing = EnumFacingConstant.rotateY(this.patternStartFacing);
					}
				} else {
					if (pos.getX() % 2 == 0) {
						tcFacing = EnumFacingConstant.rotateYCCW(this.patternStartFacing);
					} else {
						tcFacing = EnumFacingConstant.rotateY(EnumFacingConstant.rotateY(this.patternStartFacing));
					}
				}
				Directional dir = (Directional) tcBlock;
				dir.setFacing(tcFacing);
				tcBlock = dir;
				genArray.addBlockState(pos, tcBlock, BlockStateGenArray.GenerationPhase.MAIN,
						BlockStateGenArray.EnumPriority.MEDIUM);
			}
		}
	}

	@Override
	protected void addMidDecoration(AsyncWorldEditor world, BlockStateGenArray genArray,
			DungeonRandomizedCastle dungeon) {
		IRoomDecor pillar = RoomDecorTypes.PILLAR;
		int halfX = this.getDecorationLengthX() / 2;
		int halfZ = this.getDecorationLengthZ() / 2;

		// Offset by 1 since the pillar is 3x3
		--halfX;
		--halfZ;

		BlockPos pillarStart = this.roomOrigin.add(halfX, 1, halfZ);
		pillar.build(world, genArray, this, dungeon, pillarStart, BlockFace.NORTH, this.usedDecoPositions);
	}

	@Override
	protected Material getFloorBlock(DungeonRandomizedCastle dungeon) {
		return Material.GRAY_GLAZED_TERRACOTTA;
	}

	@Override
	public void copyPropertiesOf(CastleRoomBase room) {
		if (room instanceof CastleRoomHallway) {
			this.patternStartFacing = ((CastleRoomHallway) room).patternStartFacing;
		}
	}
}
