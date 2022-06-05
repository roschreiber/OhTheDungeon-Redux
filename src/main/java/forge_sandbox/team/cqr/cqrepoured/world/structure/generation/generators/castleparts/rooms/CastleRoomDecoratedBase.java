package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import forge_sandbox.BlockPos;
import forge_sandbox.EnumFacingConstant;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.util.GearedMobFactory;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.DecorationSelector;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.IRoomDecor;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects.RoomDecorChest;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects.RoomDecorNone;
import otd.lib.async.AsyncWorldEditor;

public abstract class CastleRoomDecoratedBase extends CastleRoomBase {
	protected static final int MAX_DECO_ATTEMPTS = 3;
	protected static final int LIGHT_LEVEL = 4;
	protected DecorationSelector decoSelector;
	protected Map<BlockPos, BlockFace> possibleChestLocs;

	CastleRoomDecoratedBase(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.decoSelector = new DecorationSelector();
		this.possibleChestLocs = new HashMap<>();
	}

	@Override
	public void decorate(AsyncWorldEditor world, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon,
			GearedMobFactory mobFactory) {
		this.setupDecoration(genArray, dungeon);

		if (this.shouldBuildEdgeDecoration()) {
			this.addEdgeDecoration(world, genArray, dungeon);
		}
		if (this.shouldBuildWallDecoration()) {
			this.addWallDecoration(world, genArray, dungeon);
		}
		if (this.shouldBuildMidDecoration()) {
			this.addMidDecoration(world, genArray, dungeon);
		}
		if (this.shouldAddSpawners()) {
			this.addSpawners(world, genArray, dungeon, mobFactory);
		}
		if (this.shouldAddChests()) {
			this.addChests(world, genArray, dungeon);
		}

		this.fillEmptySpaceWithAir(genArray);
	}

	abstract boolean shouldBuildEdgeDecoration();

	abstract boolean shouldBuildWallDecoration();

	abstract boolean shouldBuildMidDecoration();

	abstract boolean shouldAddSpawners();

	abstract boolean shouldAddChests();

	protected void addEdgeDecoration(AsyncWorldEditor world, BlockStateGenArray genArray,
			DungeonRandomizedCastle dungeon) {
		if (this.decoSelector.edgeDecorRegistered()) {
			for (BlockFace side : EnumFacingConstant.HORIZONTALS) {
				if (this.walls.containsKey(side) && this.walls.get(side).isEnabled()) {
					List<BlockPos> edge = this.getDecorationEdge(side);
					for (BlockPos pos : edge) {
						if (this.usedDecoPositions.contains(pos)) {
							// This position is already decorated, so keep going
							continue;
						}

						int attempts = 0;

						while (attempts < MAX_DECO_ATTEMPTS) {
							IRoomDecor decor = this.decoSelector.randomEdgeDecor(genArray.getRandom());
							if (decor.wouldFit(pos, side, this.possibleDecoPositions, this.usedDecoPositions, this)) {
								decor.build(world, genArray, this, dungeon, pos, side, this.usedDecoPositions);

								// If we added air here then this is a candidate spot for a chest
								if (decor instanceof RoomDecorNone) {
									this.usedDecoPositions.add(pos);
									this.possibleChestLocs.put(pos, side);
								}
								break;
							}
							++attempts;
						}
						if (attempts >= MAX_DECO_ATTEMPTS) {
							genArray.addBlockState(pos, Bukkit.createBlockData(Material.AIR),
									BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
							this.usedDecoPositions.add(pos);
							this.possibleChestLocs.put(pos, side);
						}
					}
				}
			}
		}
	}

	protected void addMidDecoration(AsyncWorldEditor world, BlockStateGenArray genArray,
			DungeonRandomizedCastle dungeon) {
		if (this.decoSelector.midDecorRegistered()) {
			List<BlockPos> area = this.getDecorationMiddle();
			for (BlockPos pos : area) {
				if (this.usedDecoPositions.contains(pos)) {
					// This position is already decorated, so keep going
					continue;
				}

				int attempts = 0;

				while (attempts < MAX_DECO_ATTEMPTS) {
					IRoomDecor decor = this.decoSelector.randomMidDecor(genArray.getRandom());
					BlockFace side = BlockFace.NORTH; // EnumFacing.HORIZONTALS[random.nextInt(EnumFacing.HORIZONTALS.length)];
					if (decor.wouldFit(pos, side, this.possibleDecoPositions, this.usedDecoPositions, this)) {
						decor.build(world, genArray, this, dungeon, pos, side, this.usedDecoPositions);

						break;
					}
					++attempts;
				}
				if (attempts >= MAX_DECO_ATTEMPTS) {
					genArray.addBlockState(pos, Bukkit.createBlockData(Material.AIR),
							BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
					this.usedDecoPositions.add(pos);
				}
			}
		}
	}

	protected void addSpawners(AsyncWorldEditor world, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon,
			GearedMobFactory mobFactory) {
		List<BlockPos> spawnPositions = this.getDecorationLayer(0);
		spawnPositions.removeAll(this.usedDecoPositions);

		int spawnerCount = dungeon.randomizeRoomSpawnerCount(this.random);

		for (int i = 0; (i < spawnerCount && !spawnPositions.isEmpty()); i++) {
			BlockPos pos = spawnPositions.get(this.random.nextInt(spawnPositions.size()));
			genArray.addRelativePosSpawner(world, pos, this.floor);
			this.usedDecoPositions.add(pos);
			spawnPositions.remove(pos);
		}
		// TODO
//		List<BlockPos> spawnPositions = this.getDecorationLayer(0);
//		spawnPositions.removeAll(this.usedDecoPositions);
//
//		int spawnerCount = dungeon.randomizeRoomSpawnerCount(this.random);
//
//		for (int i = 0; (i < spawnerCount && !spawnPositions.isEmpty()); i++) {
//			BlockPos pos = spawnPositions.get(this.random.nextInt(spawnPositions.size()));
//
//			Entity mobEntity = mobFactory.getGearedEntityByFloor(this.floor, world);
//
//			Block spawnerBlock = CQRBlocks.SPAWNER;
//			IBlockState state = spawnerBlock.getDefaultState();
//			TileEntitySpawner spawner = (TileEntitySpawner) spawnerBlock.createTileEntity(world, state);
//
//			if (spawner != null) {
//				spawner.inventory.setStackInSlot(0, SpawnerFactory.getSoulBottleItemStackForEntity(mobEntity));
//
//				NBTTagCompound spawnerCompound = spawner.writeToNBT(new NBTTagCompound());
//				genArray.addBlockState(pos, state, spawnerCompound, BlockStateGenArray.GenerationPhase.POST,
//						BlockStateGenArray.EnumPriority.MEDIUM);
//
//				this.usedDecoPositions.add(pos);
//				spawnPositions.remove(pos);
//			}
//		}
	}

	protected void addWallDecoration(AsyncWorldEditor world, BlockStateGenArray genArray,
			DungeonRandomizedCastle dungeon) {
//		int torchPercent = LIGHT_LEVEL * 3;
//
//		for (BlockFace side : EnumFacingConstant.HORIZONTALS) {
//			if (this.walls.containsKey(side) && this.walls.get(side).isEnabled()) {
//				List<BlockPos> edge = this.getWallDecorationEdge(side);
//				for (BlockPos pos : edge) {
//					if (this.usedDecoPositions.contains(pos)) {
//						// This position is already decorated, so keep going
//						continue;
//					}
//
//					switch (this.random.nextInt(1)) {
//					case 0:
//						if ((RoomDecorTypes.TORCH.wouldFit(pos, side, this.possibleDecoPositions,
//								this.usedDecoPositions, this))
//								&& (DungeonGenUtils.percentageRandom(torchPercent, this.random))) {
//							RoomDecorTypes.TORCH.build(world, genArray, this, dungeon, pos, side,
//									this.usedDecoPositions);
//						}
//						break;
////					case 1:
////						if ((RoomDecorTypes.PAINTING.wouldFit(pos, side, this.possibleDecoPositions,
////								this.usedDecoPositions))
////								&& (DungeonGenUtils.percentageRandom(dungeon.getPaintingChance(), this.random))) {
////							RoomDecorTypes.PAINTING.buildRandom(world, pos, genArray, side, this.possibleDecoPositions,
////									this.usedDecoPositions);
////						}
////						break;
//					/*
//					 * case 2: if ((RoomDecorTypes.UNLIT_TORCH.wouldFit(pos, side,
//					 * this.possibleDecoPositions, this.usedDecoPositions, this)) &&
//					 * (DungeonGenUtils.percentageRandom(5, this.random))) {
//					 * RoomDecorTypes.UNLIT_TORCH.build(world, genArray, this, dungeon, pos, side,
//					 * this.usedDecoPositions); } break;
//					 */
//					default:
//						break;
//					}
//				}
//			}
//		}
	}

	protected void addChests(AsyncWorldEditor world, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		if (this.getChestIDs() != null && !this.possibleChestLocs.isEmpty()) {
			if (DungeonGenUtils.percentageRandom(50, this.random)) {
				IRoomDecor chest = new RoomDecorChest();
				BlockPos pos = (BlockPos) this.possibleChestLocs.keySet().toArray()[this.random
						.nextInt(this.possibleChestLocs.size())];
				chest.build(world, genArray, this, dungeon, pos, this.possibleChestLocs.get(pos),
						this.usedDecoPositions);
			}
		}
	}
}
