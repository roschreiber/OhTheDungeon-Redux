package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators;

import java.util.Random;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.DungeonPlacement;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.part.BlockDungeonPart;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.CastleRoomSelector;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.CastleRoomSelector.SupportArea;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.inhabitants.DungeonInhabitant;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.inhabitants.DungeonInhabitantManager;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.castle.Chest_Later;
import otd.lib.async.later.castle.Spawner_Later;

/**
 * Copyright (c) 25.05.2019 Developed by KalgogSmash GitHub:
 * https://github.com/KalgogSmash
 */
public class GeneratorRandomizedCastle extends AbstractDungeonGenerator<DungeonRandomizedCastle> {

	private CastleRoomSelector roomHelper;
	private BlockPos structurePos;

	public GeneratorRandomizedCastle(AsyncWorldEditor world, BlockPos pos, DungeonRandomizedCastle dungeon,
			Random rand) {
		super(world, pos, dungeon, rand);
	}

	@Override
	public void preProcess() {
		this.roomHelper = new CastleRoomSelector(this.dungeon, this.random);
		this.roomHelper.randomizeCastle();

		int minX = Integer.MAX_VALUE;
		int minZ = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxZ = Integer.MIN_VALUE;
		for (SupportArea area : this.roomHelper.getSupportAreas()) {
			minX = Math.min(area.getNwCorner().getX(), minX);
			minZ = Math.min(area.getNwCorner().getZ(), minZ);
			maxX = Math.max(area.getNwCorner().getX() + area.getBlocksX(), maxX);
			maxZ = Math.max(area.getNwCorner().getZ() + area.getBlocksZ(), maxZ);
		}
		this.structurePos = this.pos.add((minX - maxX) / 2, 0, (minZ - maxZ) / 2);

//		if (this.dungeon.doBuildSupportPlatform()) {
//			for (CastleRoomSelector.SupportArea area : this.roomHelper.getSupportAreas()) {
//				// CQRMain.logger.info("{} {} {}", area.getNwCorner(), area.getBlocksX(),
//				// area.getBlocksZ());
//				BlockPos p1 = this.structurePos.add(area.getNwCorner());
//				BlockPos p2 = p1.add(area.getBlocksX(), 0, area.getBlocksZ());
//				PlateauDungeonPart.Builder partBuilder = new PlateauDungeonPart.Builder(p1.getX(), p1.getZ(), p2.getX(),
//						p2.getY(), p2.getZ(), 8); // CQRConfig.general.supportHillWallSize
//				partBuilder.setSupportHillBlock(this.dungeon.getSupportBlock());
//				partBuilder.setSupportHillTopBlock(this.dungeon.getSupportTopBlock());
//				this.dungeonBuilder.add(partBuilder);
//			}
//		}
	}

	@Override
	public void buildStructure() {
		BlockStateGenArray genArray = new BlockStateGenArray(this.random);
		DungeonInhabitant mobType = DungeonInhabitantManager.instance().getInhabitantByDistanceIfDefault(
				this.dungeon.getDungeonMob(), this.world, this.pos.getX(), this.pos.getZ());
		this.roomHelper.generate(this.world, genArray, this.dungeon, this.pos, mobType);

		this.dungeonBuilder.add(new BlockDungeonPart.Builder().addAll(genArray.getMainMap().values()),
				this.structurePos);
		this.dungeonBuilder.add(new BlockDungeonPart.Builder().addAll(genArray.getPostMap().values()),
				this.structurePos);

		DungeonPlacement placement = this.dungeonBuilder.getPlacement(this.structurePos);
		for (BlockStateGenArray.RelativeChest chest : genArray.chests) {
			BlockPos newPos = placement.transform(chest.pos);
			Chest_Later c = Chest_Later.getChest(world, newPos, chest.facing, chest.lootTable, this.random);
			world.addLater(c);
		}
		for (BlockStateGenArray.RelativeSpawner spawner : genArray.spawners) {
			BlockPos newPos = placement.transform(spawner.pos);
			Spawner_Later.generate_later(world, newPos, spawner.floor, spawner.isBoss);
		}
//		this.dungeonBuilder.add(new EntityDungeonPart.Builder().addAll(genArray.getEntityMap()), this.structurePos);
	}

	@Override
	public void postProcess() {
		// Does nothing here
	}

}
