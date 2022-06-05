package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons;

import java.util.Properties;
import java.util.Random;

import org.bukkit.Material;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.CQRWeightedRandom;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.AbstractDungeonGenerator;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.GeneratorRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.RandomCastleConfigOptions;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.EnumRoomType;
import forge_sandbox.util.WoodType;
import otd.lib.async.AsyncWorldEditor;

/**
 * Copyright (c) 20.04.2020 Developed by KalgogSmash GitHub:
 * https://github.com/KalgogSmash
 */
public class DungeonRandomizedCastle extends DungeonBase {

	private int maxSize;
	private int roomSize;
	private int floorHeight;
	private Material mainBlock;
	private Material fancyBlock;
	private Material slabBlock;
	private Material stairBlock;
	private Material roofBlock;
	private Material fenceBlock;
	private Material floorBlock;
	private Material woodStairBlock;
	private Material woodSlabBlock;
	private Material plankBlock;
	private Material doorBlock;

	private CQRWeightedRandom<RandomCastleConfigOptions.RoofType> roofTypeRandomizer;
	private CQRWeightedRandom<RandomCastleConfigOptions.RoofType> towerRoofTypeRandomizer;
	private CQRWeightedRandom<RandomCastleConfigOptions.WindowType> windowTypeRandomizer;
	private CQRWeightedRandom<EnumRoomType> roomRandomizer;

	private int minSpawnerRolls;
	private int maxSpawnerRolls;
	private int spawnerRollChance;

	private int minBridgeLength;
	private int maxBridgeLength;
	private int bridgeChance;

	private int paintingChance;

	public DungeonRandomizedCastle(String name, Properties prop) {
		super(name, prop);

		this.maxSize = 75;
		this.roomSize = 9;
		this.floorHeight = 7;

		WoodType woodType = WoodType.OAK;
		this.mainBlock = Material.STONE_BRICKS;
		this.stairBlock = Material.STONE_BRICK_STAIRS;
		this.slabBlock = Material.STONE_BRICK_SLAB;
		this.fancyBlock = Material.CHISELED_STONE_BRICKS;
		this.floorBlock = WoodType.getPlanks(woodType);
		this.roofBlock = WoodType.getStair(woodType);
		this.fenceBlock = WoodType.getFence(woodType);
		this.woodStairBlock = WoodType.getStair(woodType);
		this.woodSlabBlock = WoodType.getSlab(woodType);
		this.plankBlock = WoodType.getPlanks(woodType);
		this.doorBlock = WoodType.getDoor(woodType);

		this.roomRandomizer = new CQRWeightedRandom<>();
		this.roomRandomizer.add(EnumRoomType.ALCHEMY_LAB, 10);
		this.roomRandomizer.add(EnumRoomType.ARMORY, 10);
		this.roomRandomizer.add(EnumRoomType.BEDROOM_BASIC, 10);
		this.roomRandomizer.add(EnumRoomType.BEDROOM_FANCY, 10);
		this.roomRandomizer.add(EnumRoomType.KITCHEN, 10);
		this.roomRandomizer.add(EnumRoomType.LIBRARY, 10);
		this.roomRandomizer.add(EnumRoomType.POOL, 5);
		this.roomRandomizer.add(EnumRoomType.PORTAL, 1);
		this.roomRandomizer.add(EnumRoomType.JAIL, 1);

		this.roofTypeRandomizer = new CQRWeightedRandom<>();
		this.roofTypeRandomizer.add(RandomCastleConfigOptions.RoofType.TWO_SIDED, 1);
		this.roofTypeRandomizer.add(RandomCastleConfigOptions.RoofType.FOUR_SIDED, 1);
		this.roofTypeRandomizer.add(RandomCastleConfigOptions.RoofType.SPIRE, 0);

		this.towerRoofTypeRandomizer = new CQRWeightedRandom<>();
		this.towerRoofTypeRandomizer.add(RandomCastleConfigOptions.RoofType.TWO_SIDED, 1);
		this.towerRoofTypeRandomizer.add(RandomCastleConfigOptions.RoofType.FOUR_SIDED, 1);
		this.towerRoofTypeRandomizer.add(RandomCastleConfigOptions.RoofType.SPIRE, 2);

		this.windowTypeRandomizer = new CQRWeightedRandom<>();
		this.windowTypeRandomizer.add(RandomCastleConfigOptions.WindowType.BASIC_GLASS, 1);
		this.windowTypeRandomizer.add(RandomCastleConfigOptions.WindowType.CROSS_GLASS, 1);
		this.windowTypeRandomizer.add(RandomCastleConfigOptions.WindowType.SQUARE_BARS, 0);
		this.windowTypeRandomizer.add(RandomCastleConfigOptions.WindowType.OPEN_SLIT, 0);

		this.minSpawnerRolls = 1;
		this.maxSpawnerRolls = 3;
		this.spawnerRollChance = 50;

		this.minBridgeLength = 2;
		this.maxBridgeLength = 4;
		this.bridgeChance = 25;

		this.paintingChance = 8;
	}

	@Override
	public AbstractDungeonGenerator<DungeonRandomizedCastle> createDungeonGenerator(AsyncWorldEditor world, int x,
			int y, int z, Random rand) {
		return new GeneratorRandomizedCastle(world, new BlockPos(x, y, z), this, rand);
	}

	public Material getMainBlockState() {
		return this.mainBlock;
	}

	public Material getFancyBlockState() {
		return this.fancyBlock;
	}

	public Material getSlabBlockState() {
		return this.slabBlock;
	}

	public Material getStairBlockState() {
		return this.stairBlock;
	}

	public Material getFloorBlockState() {
		return this.floorBlock;
	}

	public Material getRoofBlockState() {
		return this.roofBlock;
	}

	public Material getFenceBlockState() {
		return this.fenceBlock;
	}

	public Material getWoodStairBlockState() {
		return this.woodStairBlock;
	}

	public Material getWoodSlabBlockState() {
		return this.woodSlabBlock;
	}

	public Material getPlankBlockState() {
		return this.plankBlock;
	}

	public Material getDoorBlockState() {
		return this.doorBlock;
	}

	public int getMaxSize() {
		return this.maxSize;
	}

	public int getRoomSize() {
		return this.roomSize;
	}

	public int getFloorHeight() {
		return this.floorHeight;
	}

	public EnumRoomType getRandomRoom(Random rand) {
		return this.roomRandomizer.next(rand);
	}

	public RandomCastleConfigOptions.RoofType getRandomRoofType(Random rand) {
		return this.roofTypeRandomizer.next(rand);
	}

	public RandomCastleConfigOptions.RoofType getRandomTowerRoofType(Random rand) {
		return this.towerRoofTypeRandomizer.next(rand);
	}

	public RandomCastleConfigOptions.WindowType getRandomWindowType(Random rand) {
		return this.windowTypeRandomizer.next(rand);
	}

	public int getMinBridgeLength() {
		return this.minBridgeLength;
	}

	public int getMaxBridgeLength() {
		return this.maxBridgeLength;
	}

	public int getBridgeChance() {
		return this.bridgeChance;
	}

	public int getPaintingChance() {
		return this.paintingChance;
	}

	public int randomizeRoomSpawnerCount(Random rand) {
		int numRolls;
		int result = 0;
		if (this.minSpawnerRolls >= this.maxSpawnerRolls) {
			numRolls = this.minSpawnerRolls;
		} else {
			numRolls = DungeonGenUtils.randomBetween(this.minSpawnerRolls, this.maxSpawnerRolls, rand);
		}

		for (int i = 0; i < numRolls; i++) {
			if (DungeonGenUtils.percentageRandom(this.spawnerRollChance, rand)) {
				result++;
			}
		}

		return result;
	}
}
