package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.segments;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.block.data.type.Stairs;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.RandomCastleConfigOptions;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.RoomGridCell;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.CastleRoomBossLandingMain;

public class CastleMainStructWall {
	public enum WallOrientation {
		HORIZONTAL, VERTICAL
	}

	private final int length;
	private final int height;
	private RandomCastleConfigOptions.WindowType windowType = RandomCastleConfigOptions.WindowType.BASIC_GLASS;
	private boolean enabled = false;
	private boolean isOuterWall = false;
	private boolean isRoofEdge = false;
	private int doorStartOffset = 0;
	private EnumCastleDoorType doorType = EnumCastleDoorType.NONE;
	private BlockPos origin;
	private WallOrientation orientation;
	private Map<BlockFace, RoomGridCell> adjacentCells = new EnumMap<>(BlockFace.class);

	public CastleMainStructWall(BlockPos origin, WallOrientation orientation, int length, int height) {
		this.origin = origin;
		this.orientation = orientation;
		this.length = length;
		this.height = height;
	}

	public void registerAdjacentCell(RoomGridCell cell, BlockFace directionOfCell) {
		this.adjacentCells.put(directionOfCell, cell);
	}

	public Optional<RoomGridCell> getAdjacentCell(BlockFace direction) {
		if (this.adjacentCells.containsKey(direction)) {
			return Optional.of(this.adjacentCells.get(direction));
		} else {
			return Optional.empty();
		}
	}

	public BlockPos getOrigin() {
		return this.origin;
	}

	public void enable() {
		this.enabled = true;
	}

	public void disable() {
		this.enabled = false;
	}

	public void setAsOuterWall() {
		this.isOuterWall = true;
	}

	public void setAsInnerWall() {
		this.isOuterWall = false;
	}

	public void setAsRoofEdge() {
		this.isRoofEdge = true;
	}

	public void setAsNormalWall() {
		this.isRoofEdge = false;
	}

	public boolean hasDoor() {
		return (this.doorType != EnumCastleDoorType.NONE);
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isVertical() {
		return this.orientation == WallOrientation.VERTICAL;
	}

	public boolean isHorizontal() {
		return this.orientation == WallOrientation.HORIZONTAL;
	}

	public int getGenerationPriority() {
		if (this.enabled) {
			if (this.isRoofEdge) {
				// Roof edges have low priority so they don't replace regular walls with air
				return 3;
			} else if (this.isOuterWall) {
				// Outer walls should go first since we want them all to be uniform
				return 1;
			} else {
				// Everything else (inner walls)
				return 2;
			}
		}
		return Integer.MAX_VALUE;
	}

	public void determineIfEnabled(Random rand) {
		BlockFace checkSide1;
		BlockFace checkSide2;

		if (this.orientation == WallOrientation.HORIZONTAL) {
			checkSide1 = BlockFace.NORTH;
			checkSide2 = BlockFace.SOUTH;
		} else {
			checkSide1 = BlockFace.WEST;
			checkSide2 = BlockFace.EAST;
		}

		Optional<RoomGridCell> neighbor1 = this.getAdjacentCell(checkSide1);
		Optional<RoomGridCell> neighbor2 = this.getAdjacentCell(checkSide2);

		boolean neighbor1Populated = false;
		boolean neighbor1IsWalkableRoof = false;
		boolean neighbor1IsNormalRoof = false;
		boolean neighbor1IsPreBoss = false;
		boolean neighbor1IsBoss = false;

		boolean neighbor2Populated = false;
		boolean neighbor2IsWalkableRoof = false;
		boolean neighbor2IsNormalRoof = false;
		boolean neighbor2IsPreBoss = false;
		boolean neighbor2IsBoss = false;

		if (neighbor1.isPresent()) {
			neighbor1Populated = neighbor1.get().isPopulated();
			if (neighbor1Populated) {
				neighbor1IsWalkableRoof = neighbor1.get().getRoom().isWalkableRoof();
				neighbor1IsNormalRoof = neighbor1.get().getRoom().isReplacedRoof();
				neighbor1IsBoss = neighbor1.get().isBossArea();
				neighbor1IsPreBoss = neighbor1.get().getRoom().isBossLanding();
			}
		}

		if (neighbor2.isPresent()) {
			neighbor2Populated = neighbor2.get().isPopulated();
			if (neighbor2Populated) {
				neighbor2IsWalkableRoof = neighbor2.get().getRoom().isWalkableRoof();
				neighbor2IsNormalRoof = neighbor2.get().getRoom().isReplacedRoof();
				neighbor2IsBoss = neighbor2.get().isBossArea();
				neighbor2IsPreBoss = neighbor2.get().getRoom().isBossLanding();
			}
		}

		if (neighbor1IsNormalRoof
				&& (neighbor2IsWalkableRoof || neighbor2IsBoss || !neighbor2Populated || neighbor2IsNormalRoof)) {
			this.disable();
		} else if (neighbor2IsNormalRoof
				&& (neighbor1IsWalkableRoof || neighbor1IsBoss || !neighbor1Populated || neighbor1IsNormalRoof)) {
			this.disable();
		} else if (neighbor1IsBoss || neighbor2IsBoss) {
			if (neighbor1IsBoss && neighbor2IsPreBoss) {
				this.enable();
				this.setAsInnerWall();
				if (neighbor2.get().getRoom() instanceof CastleRoomBossLandingMain) {
					this.addDoorCentered(EnumCastleDoorType.BOSS_HALF_1, new Random());
				} else {
					this.addDoorCentered(EnumCastleDoorType.BOSS_HALF_2, new Random());
				}
			} else if (neighbor2IsBoss && neighbor1IsPreBoss) {
				this.enable();
				this.setAsInnerWall();
				if (neighbor1.get().getRoom() instanceof CastleRoomBossLandingMain) {
					this.addDoorCentered(EnumCastleDoorType.BOSS_HALF_1, new Random());
				} else {
					this.addDoorCentered(EnumCastleDoorType.BOSS_HALF_2, new Random());
				}
			} else {
				this.disable();
			}
		} else if (neighbor1Populated && neighbor2Populated) {
			if (neighbor1.get().isConnectedToCell(neighbor2.get())) {
				// if rooms are connected then there should be no wall between them
				this.disable();
			} else if (neighbor1IsWalkableRoof && neighbor2IsWalkableRoof) {
				// no walls between roof tiles either
				this.disable();
			} else {
				this.enable();
				this.setAsInnerWall();
			}
		} else if (neighbor1Populated || neighbor2Populated) {
			this.enable();
			this.setAsOuterWall();
			if (neighbor1IsWalkableRoof || neighbor2IsWalkableRoof) {
				this.setAsRoofEdge();
			}
		} else {
			this.disable();
		}
	}

	public void generate(BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		BlockPos pos;
		BlockData blockToBuild;

		BlockFace iterDirection;
		this.windowType = dungeon.getRandomWindowType(genArray.getRandom());

		if (this.orientation == WallOrientation.VERTICAL) {
			iterDirection = BlockFace.SOUTH;
		} else {
			iterDirection = BlockFace.EAST;
		}

		for (int i = 0; i < this.length; i++) {
			for (int y = 0; y < this.height; y++) {
				pos = this.origin.offset(iterDirection, i).offset(BlockFace.UP, y);
				blockToBuild = this.getBlockToBuild(pos, dungeon);
				BlockStateGenArray.EnumPriority priority = BlockStateGenArray.EnumPriority.MEDIUM;

				if (blockToBuild.getMaterial() == Material.AIR) {
					priority = BlockStateGenArray.EnumPriority.LOWEST;
				}
				genArray.addBlockState(pos, blockToBuild, BlockStateGenArray.GenerationPhase.MAIN, priority);
			}
		}
	}

	protected BlockData getBlockToBuild(BlockPos pos, DungeonRandomizedCastle dungeon) {
		if (this.isRoofEdge) {
			return this.getRoofEdgeBlock(pos, dungeon);
		} else if (this.hasDoor()) {
			return this.getDoorBlock(pos, dungeon);
		} else if (this.isOuterWall) {
			return this.getWindowBlock(pos, dungeon);
		} else {
			return Bukkit.createBlockData(dungeon.getMainBlockState());
		}
	}

	public boolean offsetIsDoorOrWindow(int distAlongWall, int heightOnWall, DungeonRandomizedCastle dungeon) {
		// Determine the relative offset within the wall given the distance and height
		int xDist = (this.orientation == WallOrientation.HORIZONTAL) ? distAlongWall : 0;
		int zDist = (this.orientation == WallOrientation.VERTICAL) ? distAlongWall : 0;
		BlockPos wallPosition = this.origin.add(xDist, heightOnWall, zDist);

		// Consider this a door/window block if it is anything other then a regular
		// castle block
		return (this.getBlockToBuild(wallPosition, dungeon)).getMaterial() != dungeon.getMainBlockState();
	}

	private BlockData getRoofEdgeBlock(BlockPos pos, DungeonRandomizedCastle dungeon) {
		int y = pos.getY() - this.origin.getY();
		int dist = this.getLengthPoint(pos);

		if ((y == 0) || ((y == 1) && ((dist == this.length - 1) || (dist % 2 == 0)))) {
			return Bukkit.createBlockData(dungeon.getMainBlockState());
		} else {
			return Bukkit.createBlockData(Material.AIR);
		}
	}

	private BlockData getBlockDoorBossHalf1(BlockPos pos, DungeonRandomizedCastle dungeon) {
		Material blockToBuild = dungeon.getMainBlockState();
		int y = pos.getY() - this.origin.getY();
		int dist = this.getLengthPoint(pos);

		if (dist > 2) {
			if (y == 0) {
				blockToBuild = dungeon.getMainBlockState();
			} else if (y < this.height - 1) {
				blockToBuild = Material.AIR;
			}
		}

		return Bukkit.createBlockData(blockToBuild);
	}

	private BlockData getBlockDoorBossHalf2(BlockPos pos, DungeonRandomizedCastle dungeon) {
		Material blockToBuild = dungeon.getMainBlockState();
		int y = pos.getY() - this.origin.getY();
		int dist = this.getLengthPoint(pos);

		if (dist < (this.length - 3)) {
			if (y == 0) {
				blockToBuild = dungeon.getMainBlockState();
			} else if (y < this.height - 1) {
				blockToBuild = Material.AIR;
			}
		}

		return Bukkit.createBlockData(blockToBuild);
	}

	protected BlockData getDoorBlock(BlockPos pos, DungeonRandomizedCastle dungeon) {
		switch (this.doorType) {
		case AIR:
			return getBlockDoorAir(pos, dungeon);

		case BOSS_HALF_1:
			return this.getBlockDoorBossHalf1(pos, dungeon);

		case BOSS_HALF_2:
			return this.getBlockDoorBossHalf2(pos, dungeon);

		case STANDARD:
			return this.getBlockDoorStandard(pos, dungeon);

		case FENCE_BORDER:
			return this.getBlockDoorFenceBorder(pos, dungeon);

		case STAIR_BORDER:
			return this.getBlockDoorStairBorder(pos, dungeon);

		case GRAND_ENTRY:
			return this.getBlockGrandEntry(pos, dungeon);

		default:
			return Bukkit.createBlockData(dungeon.getMainBlockState());
		}
	}

	private BlockData getBlockDoorAir(BlockPos pos, DungeonRandomizedCastle dungeon) {
		Material blockToBuild = dungeon.getMainBlockState();
		int y = pos.getY() - this.origin.getY();
		int dist = this.getLengthPoint(pos);

		if (this.withinDoorWidth(dist)) {
			if (y == 0) {
				blockToBuild = dungeon.getMainBlockState();
			} else if (y < this.doorType.getHeight()) {
				blockToBuild = Material.AIR;
			}
		}

		return Bukkit.createBlockData(blockToBuild);
	}

	private BlockData getBlockDoorStairBorder(BlockPos pos, DungeonRandomizedCastle dungeon) {
		BlockData blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());
		final int y = pos.getY() - this.origin.getY();
		final int dist = this.getLengthPoint(pos);
		final int halfPoint = this.doorStartOffset + (this.doorType.getWidth() / 2);

		if (this.withinDoorWidth(dist)) {
			if (y == 0) {
				blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());
			} else if (dist == halfPoint || dist == halfPoint - 1) {
				if (y >= 1 && y <= 3) {
					blockToBuild = Bukkit.createBlockData(Material.AIR);
				} else if (y == 4) {
					Slab slab = (Slab) Bukkit.createBlockData(dungeon.getSlabBlockState());
					slab.setType(Type.TOP);
					blockToBuild = slab;
				}
			} else if (dist == halfPoint + 1 || dist == halfPoint - 2) {
				BlockFace stairFacing;

				if (this.orientation == WallOrientation.HORIZONTAL) {
					stairFacing = (dist == halfPoint - 2) ? BlockFace.WEST : BlockFace.EAST;
				} else {
					stairFacing = (dist == halfPoint - 2) ? BlockFace.NORTH : BlockFace.SOUTH;
				}

				Directional dir = (Directional) Bukkit.createBlockData(dungeon.getStairBlockState());
				dir.setFacing(stairFacing);
				BlockData stairBase = dir;

				if (y == 1) {
					blockToBuild = stairBase;
				} else if (y == 2 || y == 3) {
					blockToBuild = Bukkit.createBlockData(Material.AIR);
				} else if (y == 4) {
					Stairs stair = (Stairs) stairBase.clone();
					stair.setHalf(Half.TOP);
					blockToBuild = stair;
				}
			}
		}

		return blockToBuild;
	}

	private BlockData getBlockDoorFenceBorder(BlockPos pos, DungeonRandomizedCastle dungeon) {
		Material blockToBuild = dungeon.getMainBlockState();
		final int y = pos.getY() - this.origin.getY();
		final int dist = this.getLengthPoint(pos);
		final int halfPoint = this.doorStartOffset + (this.doorType.getWidth() / 2);

		if (this.withinDoorWidth(dist)) {
			if (y == 0) {
				blockToBuild = dungeon.getMainBlockState();
			} else if (dist == halfPoint || dist == halfPoint - 1) {
				if (y == 1 || y == 2) {
					blockToBuild = Material.AIR;
				} else if (y == 3) {
					blockToBuild = dungeon.getFenceBlockState();
				}
			} else if (((dist == halfPoint + 1) || (dist == halfPoint - 2)) && (y < this.doorType.getHeight())) {
				blockToBuild = dungeon.getFenceBlockState();
			}
		}

		return Bukkit.createBlockData(blockToBuild);
	}

	private BlockData getBlockDoorStandard(BlockPos pos, DungeonRandomizedCastle dungeon) {
		BlockData blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());
		final int y = pos.getY() - this.origin.getY();
		final int dist = this.getLengthPoint(pos);
		final int halfPoint = this.doorStartOffset + (this.doorType.getWidth() / 2);

		if (this.withinDoorWidth(dist)) {
			if (y == 0) {
				blockToBuild = Bukkit.createBlockData(dungeon.getFloorBlockState());
			} else if ((dist == halfPoint || dist == halfPoint - 1)) {
				if (y == 1 || y == 2) {
					Bisected.Half half = (y == 1) ? Bisected.Half.BOTTOM : Bisected.Half.TOP;

					Door.Hinge hinge;
					if (this.orientation == WallOrientation.HORIZONTAL) {
						hinge = (dist == halfPoint) ? Door.Hinge.RIGHT : Door.Hinge.LEFT;
					} else {
						hinge = (dist == halfPoint) ? Door.Hinge.LEFT : Door.Hinge.RIGHT;
					}
					BlockFace facing = (this.orientation == WallOrientation.HORIZONTAL) ? BlockFace.NORTH
							: BlockFace.WEST;

					BlockData bd = Bukkit.createBlockData(dungeon.getDoorBlockState());
					Door door = (Door) bd;
					door.setHalf(half);
					door.setHinge(hinge);
					door.setFacing(facing);

					blockToBuild = door;
				} else if (y == 3) {
					blockToBuild = Bukkit.createBlockData(dungeon.getPlankBlockState());
				}

			} else if (((dist == halfPoint + 1) || (dist == halfPoint - 2)) && (y < this.doorType.getHeight())) {
				blockToBuild = Bukkit.createBlockData(dungeon.getPlankBlockState());
			}
		}

		return blockToBuild;
	}

	private BlockData getBlockGrandEntry(BlockPos pos, DungeonRandomizedCastle dungeon) {
		BlockData blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());

		final int y = pos.getY() - this.origin.getY();
		final int dist = this.getLengthPoint(pos);
		final int halfPoint = this.doorStartOffset + (this.doorType.getWidth() / 2);
		final int distFromHalf = Math.abs(dist - halfPoint);

		final BlockData outlineBlock = Bukkit.createBlockData(dungeon.getFancyBlockState());

		if (this.withinDoorWidth(dist)) {
			if (y == 0) {
				blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());
			} else if (distFromHalf == 0) {
				if (y <= 3) {
					return Bukkit.createBlockData(Material.AIR);
				} else if (y == 4) {
					return Bukkit.createBlockData(dungeon.getFenceBlockState());
				} else if (y == 5) {
					return outlineBlock;
				}
			} else if (distFromHalf == 1) {
				if (y <= 2) {
					return Bukkit.createBlockData(Material.AIR);
				} else if (y == 3 || y == 4) {
					return Bukkit.createBlockData(dungeon.getFenceBlockState());
				} else if (y == 5) {
					return outlineBlock;
				}
			} else if (Math.abs(dist - halfPoint) == 2) {
				if (y <= 3) {
					return Bukkit.createBlockData(dungeon.getFenceBlockState());
				} else if (y == 4 || y == 5) {
					return outlineBlock;
				}
			} else if (Math.abs(dist - halfPoint) == 3) {
				if (y <= 4) {
					return outlineBlock;
				}
			}
		}

		return blockToBuild;
	}

	protected BlockData getWindowBlock(BlockPos pos, DungeonRandomizedCastle dungeon) {
		switch (this.windowType) {
		case BASIC_GLASS:
			return this.getBlockWindowBasicGlass(pos, dungeon);
		case CROSS_GLASS:
			return this.getBlockWindowCrossGlass(pos, dungeon);
		case SQUARE_BARS:
			return this.getBlockWindowSquareBars(pos, dungeon);
		case OPEN_SLIT:
		default:
			return this.getBlockWindowOpenSlit(pos, dungeon);
		}
	}

	private BlockData getBlockWindowBasicGlass(BlockPos pos, DungeonRandomizedCastle dungeon) {
		int y = pos.getY() - this.origin.getY();
		int dist = this.getLengthPoint(pos);

		if ((y == 2 || y == 3) && (dist == this.length / 2)) {
			return Bukkit.createBlockData(Material.GLASS);
		} else {
			return Bukkit.createBlockData(dungeon.getMainBlockState());
		}
	}

	private BlockData getBlockWindowCrossGlass(BlockPos pos, DungeonRandomizedCastle dungeon) {
		int y = pos.getY() - this.origin.getY();
		int dist = this.getLengthPoint(pos);
		int halfDist = this.length / 2;

		if ((dist == halfDist - 1 && y == 3) || (dist == halfDist && y >= 2 && y <= 4)
				|| (dist == halfDist + 1 && y == 3)) {
			return Bukkit.createBlockData(Material.GLASS);
		} else {
			return Bukkit.createBlockData(dungeon.getMainBlockState());
		}
	}

	private BlockData getBlockWindowSquareBars(BlockPos pos, DungeonRandomizedCastle dungeon) {
		int y = pos.getY() - this.origin.getY();
		int dist = this.getLengthPoint(pos);
		int halfDist = this.length / 2;

		if (((y == 2) || (y == 3)) && ((dist == halfDist) || (dist == halfDist + 1))) {
			return Bukkit.createBlockData(Material.IRON_BARS);
		} else {
			return Bukkit.createBlockData(dungeon.getMainBlockState());
		}
	}

	private final static BlockData AIR = Bukkit.createBlockData(Material.AIR);

	private BlockData getBlockWindowOpenSlit(BlockPos pos, DungeonRandomizedCastle dungeon) {
		int y = pos.getY() - this.origin.getY();
		int dist = this.getLengthPoint(pos);
		int halfDist = this.length / 2;

		if ((y == 2) && (dist >= halfDist - 1) && (dist <= halfDist + 1)) {
			return AIR;
		} else {
			return Bukkit.createBlockData(dungeon.getMainBlockState());
		}
	}

	public void addDoorCentered(EnumCastleDoorType type, Random random) {
		if (type == EnumCastleDoorType.RANDOM) {
			type = EnumCastleDoorType.getRandomRegularType(random);
		}
		this.doorType = type;
		this.doorStartOffset = (this.length - type.getWidth()) / 2;
	}

	public void addDoorRandomOffset(EnumCastleDoorType type, Random random) {
		if (type == EnumCastleDoorType.RANDOM) {
			type = EnumCastleDoorType.getRandomRegularType(random);
		}
		this.doorType = type;
		this.doorStartOffset = 1 + random.nextInt(this.length - type.getWidth() - 1);
	}

	/*
	 * Whether to build a door or window is usually determined by how far along the
	 * wall we are. This function gets the relevant length along the wall based on
	 * if we are a horizontal wall or a vertical wall.
	 */
	protected int getLengthPoint(BlockPos pos) {
		if (this.orientation == WallOrientation.VERTICAL) {
			return pos.getZ() - this.origin.getZ();
		} else {
			return pos.getX() - this.origin.getX();
		}
	}

	protected boolean withinDoorWidth(int value) {
		int relativeToDoor = value - this.doorStartOffset;
		return (relativeToDoor >= 0 && relativeToDoor < this.doorType.getWidth());
	}
}
