package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;

import forge_sandbox.BlockPos;
import forge_sandbox.EnumFacingConstant;
import forge_sandbox.Vec3i;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.util.GenerationTemplate;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.inhabitants.DungeonInhabitant;
import otd.lib.async.AsyncWorldEditor;

public class CastleRoomJailCell extends CastleRoomDecoratedBase {
	private BlockFace doorSide;
	private List<BlockPos> prisonerSpawnerPositions = new ArrayList<>();

	public CastleRoomJailCell(int sideLength, int height, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.JAIL;
		this.defaultCeiling = true;
		this.defaultFloor = true;
		this.doorSide = EnumFacingConstant.HORIZONTALS[this.random.nextInt(EnumFacingConstant.HORIZONTALS.length)];
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
		return false;
	}

	@Override
	boolean shouldAddChests() {
		return false;
	}

	@Override
	protected void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		int endX = this.getDecorationLengthX() - 1;
		int endZ = this.getDecorationLengthZ() - 1;

		Predicate<Vec3i> northRow = (v -> ((v.getZ() == 1) && ((v.getX() >= 1) && (v.getX() <= endX - 1))));
		Predicate<Vec3i> southRow = (v -> ((v.getZ() == endZ - 1) && ((v.getX() >= 1) && (v.getX() <= endX - 1))));
		Predicate<Vec3i> westRow = (v -> ((v.getX() == 1) && ((v.getZ() >= 1) && (v.getZ() <= endZ - 1))));
		Predicate<Vec3i> eastRow = (v -> ((v.getX() == endX - 1) && ((v.getZ() >= 1) && (v.getZ() <= endZ - 1))));

		GenerationTemplate template = new GenerationTemplate(this.getDecorationLengthX(), this.getDecorationLengthY(),
				this.getDecorationLengthZ());
		// here we take advantage of the fact that rules added to the template earlier
		// will take priority
		// so we add in the order of door -> frame -> cell

		if (this.doorSide == BlockFace.NORTH) {
			int half = this.getDecorationLengthX() / 2;
			Predicate<Vec3i> doorFrame = (v -> ((v.getY() >= 0) && (v.getY() <= 2) && (v.getZ() == 1)
					&& (v.getX() >= half - 1) && (v.getX() <= half + 2)));
			Predicate<Vec3i> doorLower1 = (v -> ((v.getY() == 0) && (v.getZ() == 1) && (v.getX() == half)));
			Predicate<Vec3i> doorLower2 = (v -> ((v.getY() == 0) && (v.getZ() == 1) && (v.getX() == half + 1)));
			Predicate<Vec3i> doorUpper1 = (v -> ((v.getY() == 1) && (v.getZ() == 1) && (v.getX() == half)));
			Predicate<Vec3i> doorUpper2 = (v -> ((v.getY() == 1) && (v.getZ() == 1) && (v.getX() == half + 1)));
			Predicate<Vec3i> levers = (v -> ((v.getY() == 1) && (v.getZ() == 0)
					&& ((v.getX() == half - 1) || (v.getX() == half + 2))));

			{
				BlockData iron_door = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) iron_door;
				door.setFacing(BlockFace.SOUTH);
				door.setHalf(Half.BOTTOM);
				door.setHinge(Hinge.RIGHT);
				template.addRule(doorLower1, door);
			}
			{
				BlockData iron_door = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) iron_door;
				door.setFacing(BlockFace.SOUTH);
				door.setHalf(Half.BOTTOM);
				door.setHinge(Hinge.LEFT);
				template.addRule(doorLower2, door);
			}
			{
				BlockData iron_door = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) iron_door;
				door.setFacing(BlockFace.SOUTH);
				door.setHalf(Half.TOP);
				door.setHinge(Hinge.RIGHT);
				template.addRule(doorUpper1, door);
			}
			{
				BlockData iron_door = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) iron_door;
				door.setFacing(BlockFace.SOUTH);
				door.setHalf(Half.TOP);
				door.setHinge(Hinge.LEFT);
				template.addRule(doorUpper2, door);
			}
			template.addRule(doorFrame, Bukkit.createBlockData(Material.IRON_BLOCK));
			{
				BlockData bd = Bukkit.createBlockData(Material.LEVER);
				Directional dir = (Directional) bd;
				dir.setFacing(BlockFace.NORTH);
				template.addRule(levers, dir);
			}
		} else if (this.doorSide == BlockFace.SOUTH) {
			int half = this.getDecorationLengthX() / 2;
			Predicate<Vec3i> doorFrame = (v -> ((v.getY() >= 0) && (v.getY() <= 2) && (v.getZ() == endZ - 1)
					&& (v.getX() >= half - 1) && (v.getX() <= half + 2)));
			Predicate<Vec3i> doorLower1 = (v -> ((v.getY() == 0) && (v.getZ() == endZ - 1) && (v.getX() == half)));
			Predicate<Vec3i> doorLower2 = (v -> ((v.getY() == 0) && (v.getZ() == endZ - 1) && (v.getX() == half + 1)));
			Predicate<Vec3i> doorUpper1 = (v -> ((v.getY() == 1) && (v.getZ() == endZ - 1) && (v.getX() == half)));
			Predicate<Vec3i> doorUpper2 = (v -> ((v.getY() == 1) && (v.getZ() == endZ - 1) && (v.getX() == half + 1)));
			Predicate<Vec3i> levers = (v -> ((v.getY() == 1) && (v.getZ() == endZ)
					&& ((v.getX() == half - 1) || (v.getX() == half + 2))));

			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.NORTH);
				door.setHalf(Half.BOTTOM);
				door.setHinge(Hinge.LEFT);
				template.addRule(doorLower1, door);
			}
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.NORTH);
				door.setHalf(Half.BOTTOM);
				door.setHinge(Hinge.RIGHT);
				template.addRule(doorLower2, door);
			}
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.NORTH);
				door.setHalf(Half.TOP);
				door.setHinge(Hinge.LEFT);
				template.addRule(doorUpper1, door);
			}
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.NORTH);
				door.setHalf(Half.TOP);
				door.setHinge(Hinge.RIGHT);
				template.addRule(doorUpper2, door);
			}
			template.addRule(doorFrame, Bukkit.createBlockData(Material.IRON_BLOCK));
			{
				BlockData bd = Bukkit.createBlockData(Material.LEVER);
				Directional dir = (Directional) bd;
				dir.setFacing(BlockFace.SOUTH);
				template.addRule(levers, dir);
			}
		} else if (this.doorSide == BlockFace.WEST) {
			int half = this.getDecorationLengthZ() / 2;
			Predicate<Vec3i> doorFrame = (v -> ((v.getY() >= 0) && (v.getY() <= 2) && (v.getX() == 1)
					&& (v.getZ() >= half - 1) && (v.getZ() <= half + 2)));
			Predicate<Vec3i> doorLower1 = (v -> ((v.getY() == 0) && (v.getX() == 1) && (v.getZ() == half)));
			Predicate<Vec3i> doorLower2 = (v -> ((v.getY() == 0) && (v.getX() == 1) && (v.getZ() == half + 1)));
			Predicate<Vec3i> doorUpper1 = (v -> ((v.getY() == 1) && (v.getX() == 1) && (v.getZ() == half)));
			Predicate<Vec3i> doorUpper2 = (v -> ((v.getY() == 1) && (v.getX() == 1) && (v.getZ() == half + 1)));
			Predicate<Vec3i> levers = (v -> ((v.getY() == 1) && (v.getX() == 0)
					&& ((v.getZ() == half - 1) || (v.getZ() == half + 2))));

			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.EAST);
				door.setHalf(Half.BOTTOM);
				door.setHinge(Hinge.LEFT);
				template.addRule(doorLower1, door);
			}
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.EAST);
				door.setHalf(Half.BOTTOM);
				door.setHinge(Hinge.RIGHT);
				template.addRule(doorLower2, door);
			}
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.EAST);
				door.setHalf(Half.TOP);
				door.setHinge(Hinge.LEFT);
				template.addRule(doorUpper1, door);
			}
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.EAST);
				door.setHalf(Half.TOP);
				door.setHinge(Hinge.RIGHT);
				template.addRule(doorUpper2, door);
			}
			template.addRule(doorFrame, Bukkit.createBlockData(Material.IRON_BLOCK));
			{
				BlockData bd = Bukkit.createBlockData(Material.LEVER);
				Directional dir = (Directional) bd;
				dir.setFacing(BlockFace.WEST);
				template.addRule(levers, dir);
			}
		} else if (this.doorSide == BlockFace.EAST) {
			int half = this.getDecorationLengthZ() / 2;
			Predicate<Vec3i> doorFrame = (v -> ((v.getY() >= 0) && (v.getY() <= 2) && (v.getX() == endX - 1)
					&& (v.getZ() >= half - 1) && (v.getZ() <= half + 2)));
			Predicate<Vec3i> doorLower1 = (v -> ((v.getY() == 0) && (v.getX() == endX - 1) && (v.getZ() == half)));
			Predicate<Vec3i> doorLower2 = (v -> ((v.getY() == 0) && (v.getX() == endX - 1) && (v.getZ() == half + 1)));
			Predicate<Vec3i> doorUpper1 = (v -> ((v.getY() == 1) && (v.getX() == endX - 1) && (v.getZ() == half)));
			Predicate<Vec3i> doorUpper2 = (v -> ((v.getY() == 1) && (v.getX() == endX - 1) && (v.getZ() == half + 1)));
			Predicate<Vec3i> levers = (v -> ((v.getY() == 1) && (v.getX() == endX)
					&& ((v.getZ() == half - 1) || (v.getZ() == half + 2))));
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.WEST);
				door.setHalf(Half.BOTTOM);
				door.setHinge(Hinge.RIGHT);
				template.addRule(doorLower1, door);
			}
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.WEST);
				door.setHalf(Half.BOTTOM);
				door.setHinge(Hinge.LEFT);
				template.addRule(doorLower2, door);
			}
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.WEST);
				door.setHalf(Half.TOP);
				door.setHinge(Hinge.RIGHT);
				template.addRule(doorUpper1, door);
			}
			{
				BlockData bd = Bukkit.createBlockData(Material.IRON_DOOR);
				Door door = (Door) bd;
				door.setFacing(BlockFace.WEST);
				door.setHalf(Half.TOP);
				door.setHinge(Hinge.LEFT);
				template.addRule(doorUpper2, door);
			}
			template.addRule(doorFrame, Bukkit.createBlockData(Material.IRON_BLOCK));
			{
				BlockData bd = Bukkit.createBlockData(Material.LEVER);
				Directional dir = (Directional) bd;
				dir.setFacing(BlockFace.EAST);
				template.addRule(levers, dir);
			}
		}

		BlockData iron_bars = Bukkit.createBlockData(Material.IRON_BARS);
		template.addRule(northRow, iron_bars.clone());
		template.addRule(southRow, iron_bars.clone());
		template.addRule(westRow, iron_bars.clone());
		template.addRule(eastRow, iron_bars.clone());

		Map<BlockPos, BlockData> genMap = template.getGenerationMap(this.getDecorationStartPos(), true);
		genArray.addBlockStateMap(genMap, BlockStateGenArray.GenerationPhase.MAIN,
				BlockStateGenArray.EnumPriority.MEDIUM);
		for (Map.Entry<BlockPos, BlockData> entry : genMap.entrySet()) {
			if (entry.getValue().getMaterial() != Material.AIR) {
				this.usedDecoPositions.add(entry.getKey());
			}
		}

		// Add all spaces inside the cell to the list os possible prisoner spawn
		// locations
		for (int x = 2; x < this.getDecorationLengthX() - 2; x++) {
			for (int z = 2; z < this.getDecorationLengthZ() - 2; z++) {
				this.prisonerSpawnerPositions.add(this.roomOrigin.add(x, 1, z));
			}
		}
	}

	public void addPrisonerSpawners(DungeonInhabitant jailInhabitant, BlockStateGenArray genArray,
			AsyncWorldEditor world) {
		Collections.shuffle(this.prisonerSpawnerPositions, this.random);
		int spawnerCount = DungeonGenUtils.randomBetween(2, 5, this.random);
		for (int i = 0; (i < spawnerCount && !this.prisonerSpawnerPositions.isEmpty()); i++) {
			BlockPos pos = this.prisonerSpawnerPositions.get(i);
			genArray.addRelativePosSpawner(world, pos, this.floor);
			this.usedDecoPositions.add(pos);
		}

		// TODO
//		Collections.shuffle(this.prisonerSpawnerPositions, this.random);
//
//		int spawnerCount = DungeonGenUtils.randomBetween(2, 5, this.random);
//
//		for (int i = 0; (i < spawnerCount && !this.prisonerSpawnerPositions.isEmpty()); i++) {
//			BlockPos pos = this.prisonerSpawnerPositions.get(i);
//
//			Entity mobEntity = EntityList.createEntityByIDFromName(jailInhabitant.getEntityID(), world);
//
//			Block spawnerBlock = CQRBlocks.SPAWNER;
//			IBlockState state = spawnerBlock.getDefaultState();
//			TileEntitySpawner spawner = (TileEntitySpawner) spawnerBlock.createTileEntity(world, state);
//
//			if (spawner != null) {
//				spawner.inventory.setStackInSlot(0, SpawnerFactory.getSoulBottleItemStackForEntity(mobEntity));
//
//				NBTTagCompound spawnerCompound = spawner.writeToNBT(new NBTTagCompound());
//				genArray.addBlockState(pos, state, spawnerCompound, BlockStateGenArray.GenerationPhase.POST, BlockStateGenArray.EnumPriority.HIGH);
//
//				this.usedDecoPositions.add(pos);
//			}
//		}
	}
}
