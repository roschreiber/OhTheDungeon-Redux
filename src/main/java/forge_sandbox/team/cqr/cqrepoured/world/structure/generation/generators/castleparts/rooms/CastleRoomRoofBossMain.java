package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.block.data.type.Stairs;

import forge_sandbox.BlockPos;
import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.util.GearedMobFactory;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import otd.lib.async.AsyncWorldEditor;
import otd.util.BossInfo;
import otd.util.OTDLoottables;

public class CastleRoomRoofBossMain extends CastleRoomBase {
	private Vec3i bossBuildOffset = new Vec3i(0, 0, 0);
	private static final int BOSS_ROOM_STATIC_SIZE = 17;
	private DungeonRandomizedCastle dungeon;

	public CastleRoomRoofBossMain(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.ROOF_BOSS_MAIN;
		this.pathable = false;
	}

	public void setBossBuildOffset(Vec3i bossBuildOffset) {
		this.bossBuildOffset = bossBuildOffset;
	}

	public int getStaticSize() {
		return BOSS_ROOM_STATIC_SIZE;
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		BlockPos nwCorner = this.getBossRoomBuildStartPosition();
		BlockPos pos;
		BlockData blockToBuild;
		this.dungeon = dungeon;

		for (int x = 0; x < BOSS_ROOM_STATIC_SIZE; x++) {
			for (int y = 0; y < 8; y++) {
				for (int z = 0; z < BOSS_ROOM_STATIC_SIZE; z++) {
					blockToBuild = this.getBlockToBuild(x, y, z);
					pos = nwCorner.add(x, y, z);

					genArray.addBlockState(pos, blockToBuild, BlockStateGenArray.GenerationPhase.MAIN,
							BlockStateGenArray.EnumPriority.MEDIUM);
				}
			}
		}
	}

	@Override
	public void decorate(AsyncWorldEditor world, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon,
			GearedMobFactory mobFactory) {
		// Have to add torches last because they won't place unless the wall next to
		// them is already built
//		this.placeTorches(this.getBossRoomBuildStartPosition(), genArray);

		this.placeChests(world, this.getBossRoomBuildStartPosition(), genArray);
	}

	@Override
	public void placeBoss(AsyncWorldEditor world, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon,
			BossInfo bossResourceLocation) {
		BlockPos pos = this.getBossRoomBuildStartPosition().add(BOSS_ROOM_STATIC_SIZE / 2, 1,
				BOSS_ROOM_STATIC_SIZE / 2);
		genArray.addRelativePosSpawner(world, pos, this.floor, true);
		// TODO
//		BlockPos pos = this.getBossRoomBuildStartPosition().add(BOSS_ROOM_STATIC_SIZE / 2, 1,
//				BOSS_ROOM_STATIC_SIZE / 2);
//		genArray.addInternal(BlockStateGenArray.GenerationPhase.POST,
//				new PreparableBossInfo(pos, (NBTTagCompound) null), BlockStateGenArray.EnumPriority.MEDIUM);
	}

	@SuppressWarnings("unused")
	private void placeTorches(BlockPos nwCorner, BlockStateGenArray genArray) {
		BlockData torchBase = Bukkit.createBlockData(Material.WALL_TORCH);
		{
			Directional dir = (Directional) torchBase.clone();
			dir.setFacing(BlockFace.SOUTH);
			genArray.addBlockState(nwCorner.add(10, 3, 2), dir, BlockStateGenArray.GenerationPhase.POST,
					BlockStateGenArray.EnumPriority.MEDIUM);
		}
		{
			Directional dir = (Directional) torchBase.clone();
			dir.setFacing(BlockFace.SOUTH);
			genArray.addBlockState(nwCorner.add(6, 3, 2), dir, BlockStateGenArray.GenerationPhase.POST,
					BlockStateGenArray.EnumPriority.MEDIUM);
		}
		{
			Directional dir = (Directional) torchBase.clone();
			dir.setFacing(BlockFace.NORTH);
			genArray.addBlockState(nwCorner.add(6, 3, 14), dir, BlockStateGenArray.GenerationPhase.POST,
					BlockStateGenArray.EnumPriority.MEDIUM);
		}
		{
			Directional dir = (Directional) torchBase.clone();
			dir.setFacing(BlockFace.NORTH);
			genArray.addBlockState(nwCorner.add(10, 3, 14), dir, BlockStateGenArray.GenerationPhase.POST,
					BlockStateGenArray.EnumPriority.MEDIUM);
		}
		{
			Directional dir = (Directional) torchBase.clone();
			dir.setFacing(BlockFace.EAST);
			genArray.addBlockState(nwCorner.add(2, 3, 6), dir, BlockStateGenArray.GenerationPhase.POST,
					BlockStateGenArray.EnumPriority.MEDIUM);
		}
		{
			Directional dir = (Directional) torchBase.clone();
			dir.setFacing(BlockFace.EAST);
			genArray.addBlockState(nwCorner.add(2, 3, 10), dir, BlockStateGenArray.GenerationPhase.POST,
					BlockStateGenArray.EnumPriority.MEDIUM);
		}
		{
			Directional dir = (Directional) torchBase.clone();
			dir.setFacing(BlockFace.WEST);
			genArray.addBlockState(nwCorner.add(14, 3, 6), dir, BlockStateGenArray.GenerationPhase.POST,
					BlockStateGenArray.EnumPriority.MEDIUM);
		}
		{
			Directional dir = (Directional) torchBase.clone();
			dir.setFacing(BlockFace.WEST);
			genArray.addBlockState(nwCorner.add(14, 3, 10), dir, BlockStateGenArray.GenerationPhase.POST,
					BlockStateGenArray.EnumPriority.MEDIUM);
		}
	}

	private void placeChests(AsyncWorldEditor world, BlockPos nwCorner, BlockStateGenArray genArray) {
		int numChestsTotal = DungeonGenUtils.randomBetweenGaussian(4, 8, this.random);
		int numTreasureChests = DungeonGenUtils.randomBetween(2, 4, this.random);
		int treasureChestsPlaced = 0;
		Map<BlockPos, BlockFace> possibleChestLocs = new HashMap<>();
		possibleChestLocs.put(nwCorner.add(1, 5, 7), BlockFace.WEST);
		possibleChestLocs.put(nwCorner.add(1, 5, 9), BlockFace.WEST);
		possibleChestLocs.put(nwCorner.add(15, 5, 7), BlockFace.EAST);
		possibleChestLocs.put(nwCorner.add(15, 5, 9), BlockFace.EAST);
		possibleChestLocs.put(nwCorner.add(7, 5, 1), BlockFace.NORTH);
		possibleChestLocs.put(nwCorner.add(9, 5, 1), BlockFace.NORTH);
		possibleChestLocs.put(nwCorner.add(7, 5, 15), BlockFace.SOUTH);
		possibleChestLocs.put(nwCorner.add(9, 5, 15), BlockFace.SOUTH);
		List<Map.Entry<BlockPos, BlockFace>> locList = new ArrayList<>(possibleChestLocs.entrySet());
		Collections.shuffle(locList, this.random);

		for (int i = 0; i < numChestsTotal; i++) {
			OTDLoottables lootTable;

			if (treasureChestsPlaced < numTreasureChests) {
				lootTable = OTDLoottables.CHESTS_TREASURE;
				++treasureChestsPlaced;
			} else {
				if (DungeonGenUtils.percentageRandom(50, this.random)) {
					lootTable = OTDLoottables.CHESTS_MATERIAL;
				} else {
					lootTable = OTDLoottables.CHESTS_EQUIPMENT;
				}
			}
			genArray.addChestWithLootTable(world, locList.get(i).getKey(), locList.get(i).getValue().getOppositeFace(),
					lootTable, BlockStateGenArray.GenerationPhase.POST);
		}
	}

	private BlockPos getBossRoomBuildStartPosition() {
		return this.getNonWallStartPos().add(this.bossBuildOffset);
	}

	private BlockData getBlockToBuild(int x, int y, int z) {
		BlockData blockToBuild = Bukkit.createBlockData(Material.AIR);
		if (y == 0 || y == 7) {
			if (this.floorDesignBlock(x, z)) {
				blockToBuild = Bukkit.createBlockData(Material.WHITE_CONCRETE);
			} else {
				blockToBuild = Bukkit.createBlockData(this.dungeon.getMainBlockState());
			}
		} else if (x == 0 || z == 0 || x == 16 || z == 16) {
			blockToBuild = this.getOuterEdgeBlock(x, y, z);
		} else if (x == 1 || x == 15 || z == 1 || z == 15) {
			blockToBuild = this.getInnerRing1Block(x, y, z);
		} else if (x == 2 || x == 14 || z == 2 || z == 14) {
			blockToBuild = this.getInnerRing2Block(x, y, z);
		} else if (x == 3 || x == 13 || z == 3 || z == 13) {
			blockToBuild = this.getInnerRing3Block(x, y, z);
		}

		return blockToBuild;
	}

	private boolean floorDesignBlock(int x, int z) {
		final int[][] floorPattern = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

		return this.checkPatternIndex(x, z, floorPattern);
	}

	private boolean checkPatternIndex(int x, int z, int[][] pattern) {
		if (pattern != null && z >= 0 && z <= pattern.length && x >= 0 && x <= pattern[0].length) {
			return pattern[x][z] == 1;
		} else {
			return false;
		}
	}

	private BlockData getOuterEdgeBlock(int x, int y, int z) {
		if (x == 0 || x == 16) {
			if (z == 0 || z == 3 || z == 6 || z == 10 || z == 13 || z == 16) {
				return Bukkit.createBlockData(this.dungeon.getMainBlockState());
			} else if (z >= 7 && z <= 9) {
				if (y >= 1 && y <= 3) {
					return Bukkit.createBlockData(Material.AIR);
				} else if (y == 4) {
					if (z == 7 || z == 9) {
						BlockFace doorFrameFacing = (z == 7) ? BlockFace.NORTH : BlockFace.SOUTH;
						Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
						stair.setHalf(Half.TOP);
						stair.setFacing(doorFrameFacing);
						return stair;
					} else {
						return Bukkit.createBlockData(Material.AIR);
					}
				}
			} else {
				if (y == 6) {
					return Bukkit.createBlockData(this.dungeon.getMainBlockState());
				} else if (y == 2 || y == 3 || y == 4) {
					return Bukkit.createBlockData(Material.RED_STAINED_GLASS);
				} else if (y == 1) {
					BlockFace windowBotFacing = (x == 0) ? BlockFace.WEST : BlockFace.EAST;
					Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
					stair.setHalf(Half.BOTTOM);
					stair.setFacing(windowBotFacing);
					return stair;
				} else if (y == 5) {
					BlockFace windowTopFacing = (x == 0) ? BlockFace.EAST : BlockFace.WEST;
					Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
					stair.setHalf(Half.TOP);
					stair.setFacing(windowTopFacing);
					return stair;
				}
			}
		} else if (z == 0 || z == 16) {
			if (x == 3 || x == 6 || x == 10 || x == 13) {
				return Bukkit.createBlockData(this.dungeon.getMainBlockState());
			} else if (x >= 7 && x <= 9) {
				if (y >= 1 && y <= 3) {
					return Bukkit.createBlockData(Material.AIR);
				} else if (y == 4) {
					if (x == 7 || x == 9) {
						BlockFace doorFrameFacing = (x == 7) ? BlockFace.WEST : BlockFace.EAST;
						Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
						stair.setHalf(Half.TOP);
						stair.setFacing(doorFrameFacing);
						return stair;
					} else {
						return Bukkit.createBlockData(Material.AIR);
					}
				}
			} else {
				if (y == 6) {
					return Bukkit.createBlockData(this.dungeon.getMainBlockState());
				} else if (y == 2 || y == 3 || y == 4) {
					return Bukkit.createBlockData(Material.RED_STAINED_GLASS);
				} else if (y == 1) {
					BlockFace windowBotFacing = (z == 0) ? BlockFace.NORTH : BlockFace.SOUTH;
					Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
					stair.setHalf(Half.BOTTOM);
					stair.setFacing(windowBotFacing);
					return stair;
				} else if (y == 5) {
					BlockFace windowTopFacing = (z == 0) ? BlockFace.SOUTH : BlockFace.NORTH;
					Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
					stair.setHalf(Half.TOP);
					stair.setFacing(windowTopFacing);
					return stair;
				}
			}
		}

		return Bukkit.createBlockData(this.dungeon.getMainBlockState());
	}

	private BlockData getInnerRing1Block(int x, int y, int z) {
		final BlockData detailBlock = Bukkit.createBlockData(this.dungeon.getFancyBlockState());

		if (x == 1 || x == 15) {
			if (z == 3 || z == 6 || z == 10 || z == 13) {
				return detailBlock;
			} else if ((z == 1 || z == 2 || z == 14 || z == 15) && y == 1) {
				return Bukkit.createBlockData(Material.LAVA);
			} else if (z >= 7 && z <= 9) {
				if (y == 3 && (z == 7 || z == 9)) {
					Slab slab = (Slab) Bukkit.createBlockData(this.dungeon.getSlabBlockState());
					slab.setType(Type.TOP);
					return slab;
				} else if (y == 4) {
					return detailBlock;
				} else if (y == 5 && z == 8) {
					BlockFace frameTopStairFacing = (x == 1) ? BlockFace.WEST : BlockFace.EAST;
					Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
					stair.setHalf(Half.BOTTOM);
					stair.setFacing(frameTopStairFacing);
					return stair;
				}
			}
		} else if (z == 1 || z == 15) {
			if (x == 3 || x == 6 || x == 10 || x == 13) {
				return detailBlock;
			} else if ((x == 2 || x == 14) && y == 1) {
				return Bukkit.createBlockData(Material.LAVA);
			} else if (x >= 7 && x <= 9) {
				if (y == 3 && (x == 7 || x == 9)) {
					Slab slab = (Slab) Bukkit.createBlockData(this.dungeon.getSlabBlockState());
					slab.setType(Type.BOTTOM);
					return slab;
				} else if (y == 4) {
					return detailBlock;
				} else if (y == 5 && x == 8) {
					BlockFace frameTopStairFacing = (z == 1) ? BlockFace.NORTH : BlockFace.SOUTH;
					Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
					stair.setHalf(Half.BOTTOM);
					stair.setFacing(frameTopStairFacing);
					return stair;
				}
			}
		}

		return Bukkit.createBlockData(Material.AIR);
	}

	private BlockData getInnerRing2Block(int x, int y, int z) {
		if (x == 2 || x == 14) {
			if ((z == 2 || z == 14) && y == 1) {
				return Bukkit.createBlockData(Material.LAVA);
			} else if (z == 3 || z == 13) {
				if (y == 1 || y == 6) {
					BlockFace stairFacing = (z == 3) ? BlockFace.NORTH : BlockFace.SOUTH;
					Bisected.Half stairHalf = (y == 1) ? Bisected.Half.TOP : Bisected.Half.BOTTOM;
					Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
					stair.setHalf(stairHalf);
					stair.setFacing(stairFacing);
					return stair;
				} else if (y >= 2 && y <= 5) {
					return Bukkit.createBlockData(Material.IRON_BARS);
				}
			}
		} else if (z == 2 || z == 14) {
			// Lava case covered by previous conditionals

			if (x == 3 || x == 13) {
				if (y == 1 || y == 6) {
					BlockFace stairFacing = (x == 3) ? BlockFace.WEST : BlockFace.EAST;
					Bisected.Half stairHalf = (y == 1) ? Bisected.Half.TOP : Bisected.Half.BOTTOM;
					Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
					stair.setHalf(stairHalf);
					stair.setFacing(stairFacing);
					return stair;
				} else if (y >= 2 && y <= 5) {
					return Bukkit.createBlockData(Material.IRON_BARS);
				}
			}
		}

		return Bukkit.createBlockData(Material.AIR);
	}

	private BlockData getInnerRing3Block(int x, int y, int z) {
		if ((x == 3 || x == 13) && z == 3) {
			if (y >= 2 && y <= 5) {
				return Bukkit.createBlockData(Material.IRON_BARS);
			} else if (y == 1 || y == 6) {
				Bisected.Half stairHalf = (y == 1) ? Bisected.Half.TOP : Bisected.Half.BOTTOM;
				BlockFace stairFacing = (x == 3) ? BlockFace.WEST : BlockFace.NORTH;
				Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
				stair.setHalf(stairHalf);
				stair.setFacing(stairFacing);
				return stair;
			}
		} else if ((x == 3 || x == 13) && z == 13) {
			if (y >= 2 && y <= 5) {
				return Bukkit.createBlockData(Material.IRON_BARS);
			} else if (y == 1 || y == 6) {
				Bisected.Half stairHalf = (y == 1) ? Bisected.Half.TOP : Bisected.Half.BOTTOM;
				BlockFace stairFacing = (x == 3) ? BlockFace.WEST : BlockFace.SOUTH;
				Stairs stair = (Stairs) Bukkit.createBlockData(this.dungeon.getStairBlockState());
				stair.setHalf(stairHalf);
				stair.setFacing(stairFacing);
				return stair;
			}
		}

		return Bukkit.createBlockData(Material.AIR);
	}
}
