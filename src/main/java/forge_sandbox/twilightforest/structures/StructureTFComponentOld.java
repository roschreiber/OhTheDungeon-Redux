package forge_sandbox.twilightforest.structures;

import forge_sandbox.BlockPos;
import forge_sandbox.StructureBoundingBox;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.twilightforest.TFFeature;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TripwireHook;
import org.bukkit.entity.EntityType;
import otd.util.RotationMirror.Mirror;
import otd.util.RotationMirror.Rotation;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.twilightforest.Chest_Later;
import otd.lib.async.later.twilightforest.SpawnerBoss_Later;
import otd.lib.async.later.twilightforest.Spawner_Later;
import forge_sandbox.twilightforest.TFBoss;
import forge_sandbox.twilightforest.TFTreasure;

public abstract class StructureTFComponentOld extends StructureTFComponent {

	protected static final BlockData AIR = Bukkit.createBlockData(Material.AIR);
	private static final StructureTFStrongholdStones strongholdStones = new StructureTFStrongholdStones();

	public StructureTFComponentOld() {
	}

	public StructureTFComponentOld(TFFeature feature, int i) {
		super(i);
		this.feature = feature;
	}

	@Override
	public TFFeature getFeatureType() {
		return feature;
	}

	// Let's not use vanilla's weird rotation+mirror thing...
	@Override
	public void setCoordBaseMode(@Nullable BlockFace facing) {
		this.coordBaseMode = facing;
		this.mirror = Mirror.NONE;

		if (facing == null) {
			this.rotation = Rotation.NONE;
		} else {
			switch (facing) {
			case SOUTH:
				this.rotation = Rotation.CLOCKWISE_180;
				break;
			case WEST:
				this.rotation = Rotation.COUNTERCLOCKWISE_90;
				break;
			case EAST:
				this.rotation = Rotation.CLOCKWISE_90;
				break;
			default:
				this.rotation = Rotation.NONE;
			}
		}
	}

	/**
	 * Places a tripwire.
	 *
	 * Tries to delay notifying tripwire blocks of placement so they won't scan
	 * unloaded chunks looking for connections.
	 *
	 * See
	 * {@link net.minecraftforge.common.ForgeHooks#onPlaceItemIntoWorld(ItemStack, EntityPlayer, World, BlockPos, EnumFacing, float, float, float, EnumHand)}
	 * for block snapshot handling code.
	 */
	protected void placeTripwire(AsyncWorldEditor world, int x, int y, int z, int size, BlockFace facing,
			StructureBoundingBox sbb) {

		int dx = facing.getModX();
		int dz = facing.getModZ();

		// add tripwire hooks
		{
			TripwireHook tripwireHook = (TripwireHook) Bukkit.createBlockData(Material.TRIPWIRE_HOOK);
			tripwireHook.setFacing(facing.getOppositeFace());
			setBlockState(world, tripwireHook, x, y, z, sbb);
		}
		{
			TripwireHook tripwireHook = (TripwireHook) Bukkit.createBlockData(Material.TRIPWIRE_HOOK);
			tripwireHook.setFacing(facing);
			setBlockState(world, tripwireHook, x + dx * size, y, z + dz * size, sbb);
		}

		// add string
		for (int i = 1; i < size; i++) {
			setBlockState(world, Material.TRIPWIRE, x + dx * i, y, z + dz * i, sbb);
		}
	}

	/* BlockState Helpers */
	protected static Stairs getStairState(BlockData stairState, BlockFace direction, Rotation rotation,
			boolean isTopHalf) {
		Stairs stair = (Stairs) stairState.clone();
		stair.setFacing(direction);
		Bisected.Half half = isTopHalf ? Bisected.Half.TOP : Bisected.Half.BOTTOM;
		stair.setHalf(half);
		return stair;
	}

	/* BlockState Helpers */
	protected static Slab getSlabState(BlockData slabState, Slab.Type type) {
		Slab slab = (Slab) slabState.clone();
		slab.setType(type);
		return slab;
	}

	public static StructureBoundingBox getComponentToAddBoundingBox(int x, int y, int z, int minX, int minY, int minZ,
			int maxX, int maxY, int maxZ, @Nullable BlockFace dir) {
		switch (dir) {
		default:
			return new StructureBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY,
					z + maxZ + minZ);

		case SOUTH: // '\0'
			return new StructureBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY,
					z + maxZ + minZ);

		case WEST: // '\001'
			return new StructureBoundingBox(x - maxZ + minZ, y + minY, z + minX, x + minZ, y + maxY + minY,
					z + maxX + minX);

		case NORTH: // '\002'
			return new StructureBoundingBox(x - maxX - minX, y + minY, z - maxZ - minZ, x - minX, y + maxY + minY,
					z - minZ);

		case EAST: // '\003'
			return new StructureBoundingBox(x + minZ, y + minY, z - maxX, x + maxZ + minZ, y + maxY + minY, z + minX);
		}
	}

	/**
	 * Fixed a bug with direction 1 and -z values, but I'm not sure if it'll break
	 * other things
	 */
	public static StructureBoundingBox getComponentToAddBoundingBox2(int x, int y, int z, int minX, int minY, int minZ,
			int maxX, int maxY, int maxZ, BlockFace dir) {
		switch (dir) {
		default:
			return new StructureBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY,
					z + maxZ + minZ);

		case SOUTH: // '\0'
			return new StructureBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY,
					z + maxZ + minZ);

		case WEST: // '\001'
			return new StructureBoundingBox(x - maxZ - minZ, y + minY, z + minX, x - minZ, y + maxY + minY,
					z + maxX + minX);

		case NORTH: // '\002'
			return new StructureBoundingBox(x - maxX - minX, y + minY, z - maxZ - minZ, x - minX, y + maxY + minY,
					z - minZ);

		case EAST: // '\003'
			return new StructureBoundingBox(x + minZ, y + minY, z - maxX, x + maxZ + minZ, y + maxY + minY, z - minX);
		}
	}

	// [VanillaCopy] Keep pinned to signature of setBlockState (no state arg)
	protected void setSpawner(AsyncWorldEditor world, int x, int y, int z, StructureBoundingBox sbb,
			EntityType monsterID) {
		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);
		BlockPos pos = new BlockPos(dx, dy, dz);
		if (sbb.isVecInside(pos) && world.getBlockState(pos) != Material.SPAWNER) {
			Spawner_Later.generate_later(world, new Coord(dx, dy, dz), monsterID);
		}
	}

	protected void spawnBossLater(AsyncWorldEditor world, int x, int y, int z, TFBoss boss) {
		SpawnerBoss_Later.generate_later(world, new Coord(x, y, z), boss);
	}

	protected void surroundBlockCardinal(AsyncWorldEditor world, BlockData block, int x, int y, int z,
			StructureBoundingBox sbb) {
		setBlockState(world, block, x + 0, y, z - 1, sbb);
		setBlockState(world, block, x + 0, y, z + 1, sbb);
		setBlockState(world, block, x - 1, y, z + 0, sbb);
		setBlockState(world, block, x + 1, y, z + 0, sbb);
	}

	protected void surroundBlockCardinalRotated(AsyncWorldEditor world, BlockData block, int x, int y, int z,
			StructureBoundingBox sbb) {
		{
			Stairs stair = (Stairs) block.clone();
			stair.setFacing(BlockFace.NORTH);
			setBlockState(world, stair, x + 0, y, z - 1, sbb);
		}
		{
			Stairs stair = (Stairs) block.clone();
			stair.setFacing(BlockFace.SOUTH);
			setBlockState(world, stair, x + 0, y, z + 1, sbb);
		}
		{
			Stairs stair = (Stairs) block.clone();
			stair.setFacing(BlockFace.WEST);
			setBlockState(world, stair, x - 1, y, z + 0, sbb);
		}
		{
			Stairs stair = (Stairs) block.clone();
			stair.setFacing(BlockFace.EAST);
			setBlockState(world, stair, x + 1, y, z + 0, sbb);
		}
	}

	protected void surroundBlockCorners(AsyncWorldEditor world, BlockData block, int x, int y, int z,
			StructureBoundingBox sbb) {
		setBlockState(world, block, x - 1, y, z - 1, sbb);
		setBlockState(world, block, x - 1, y, z + 1, sbb);
		setBlockState(world, block, x + 1, y, z - 1, sbb);
		setBlockState(world, block, x + 1, y, z + 1, sbb);
	}

	/**
	 * Place a treasure chest at the specified coordinates
	 *
	 * @param treasureType
	 */
	protected void placeTreasureAtCurrentPosition(AsyncWorldEditor world, Random rand, int x, int y, int z,
			TFTreasure treasureType, StructureBoundingBox sbb) {
		this.placeTreasureAtCurrentPosition(world, rand, x, y, z, treasureType, false, sbb);
	}

	// TODO: make it work with async world editor
	/**
	 * Place a treasure chest at the specified coordinates
	 *
	 * @param treasureType
	 */
	protected void placeTreasureAtCurrentPosition(AsyncWorldEditor world, Random rand, int x, int y, int z,
			TFTreasure treasureType, boolean trapped, StructureBoundingBox sbb) {
		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);
		BlockPos pos = new BlockPos(dx, dy, dz);
		if (sbb.isVecInside(pos) && world.getBlockState(pos) != (trapped ? Material.TRAPPED_CHEST : Material.CHEST)) {
			Chest_Later.generate_later(world, new Coord(dx, dy, dz), rand, treasureType,
					trapped ? Material.TRAPPED_CHEST : Material.CHEST);
		}
	}

	/**
	 * Place a treasure chest at the specified coordinates
	 *
	 * @param treasureType
	 */
	protected void placeTreasureRotated(AsyncWorldEditor world, Random rand, int x, int y, int z, Rotation rotation,
			TFTreasure treasureType, StructureBoundingBox sbb) {
		this.placeTreasureRotated(world, rand, x, y, z, rotation, treasureType, false, sbb);
	}

	// TODO: make it work with async world editor
	/**
	 * Place a treasure chest at the specified coordinates
	 *
	 * @param treasureType
	 */
	protected void placeTreasureRotated(AsyncWorldEditor world, Random rand, int x, int y, int z, Rotation rotation,
			TFTreasure treasureType, boolean trapped, StructureBoundingBox sbb) {
		int dx = getXWithOffsetRotated(x, z, rotation);
		int dy = getYWithOffset(y);
		int dz = getZWithOffsetRotated(x, z, rotation);
		BlockPos pos = new BlockPos(dx, dy, dz);
		if (sbb.isVecInside(pos) && world.getBlockState(pos) != (trapped ? Material.TRAPPED_CHEST : Material.CHEST)) {
			Chest_Later.generate_later(world, new Coord(dx, dy, dz), rand, treasureType,
					trapped ? Material.TRAPPED_CHEST : Material.CHEST);
		}
	}

	/**
	 * Provides coordinates to make a tower such that it will open into the parent
	 * tower at the provided coordinates.
	 */
	protected int[] offsetTowerCoords(int x, int y, int z, int towerSize, BlockFace direction) {

		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);

		if (direction == BlockFace.SOUTH) {
			return new int[] { dx + 1, dy - 1, dz - towerSize / 2 };
		} else if (direction == BlockFace.WEST) {
			return new int[] { dx + towerSize / 2, dy - 1, dz + 1 };
		} else if (direction == BlockFace.NORTH) {
			return new int[] { dx - 1, dy - 1, dz + towerSize / 2 };
		} else if (direction == BlockFace.EAST) {
			return new int[] { dx - towerSize / 2, dy - 1, dz - 1 };
		}

		// ugh?
		return new int[] { x, y, z };
	}

	/**
	 * Provides coordinates to make a tower such that it will open into the parent
	 * tower at the provided coordinates.
	 */
	protected BlockPos offsetTowerCCoords(int x, int y, int z, int towerSize, BlockFace direction) {

		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);

		switch (direction) {
		case SOUTH:
			return new BlockPos(dx + 1, dy - 1, dz - towerSize / 2);
		case WEST:
			return new BlockPos(dx + towerSize / 2, dy - 1, dz + 1);
		case NORTH:
			return new BlockPos(dx - 1, dy - 1, dz + towerSize / 2);
		case EAST:
			return new BlockPos(dx - towerSize / 2, dy - 1, dz - 1);
		default:
			break;
		}

		// ugh?
		return new BlockPos(x, y, z);
	}

	@Override
	protected int getXWithOffset(int x, int z) {
		// return super.getXWithOffset(x, z);
		// [VanillaCopy] of super, edits noted.
		BlockFace enumfacing = this.getCoordBaseMode();

		if (enumfacing == null) {
			return x;
		} else {
			switch (enumfacing) {
			case SOUTH:
				return this.boundingBox.minX + x;
			case WEST:
				return this.boundingBox.maxX - z;
			case NORTH:
				return this.boundingBox.maxX - x; // TF - Add case for NORTH todo 1.9 is this correct?
			case EAST:
				return this.boundingBox.minX + z;
			default:
				return x;
			}
		}
	}

	@Override
	protected int getZWithOffset(int x, int z) {
		// return super.getZWithOffset(x, z);
		// [VanillaCopy] of super, edits noted.
		BlockFace enumfacing = this.getCoordBaseMode();

		if (enumfacing == null) {
			return z;
		} else {
			switch (enumfacing) {
			case SOUTH:
				return this.boundingBox.minZ + z;
			case WEST:
				return this.boundingBox.minZ + x;
			case NORTH:
				return this.boundingBox.maxZ - z;
			case EAST:
				return this.boundingBox.maxZ - x;
			default:
				return z;
			}
		}
	}

	private BlockFace fakeBaseMode(Rotation rotationsCW) {
		final BlockFace oldBaseMode = getCoordBaseMode();

		if (oldBaseMode != null) {
			BlockFace pretendBaseMode = oldBaseMode;
			pretendBaseMode = rotationsCW.rotate(pretendBaseMode);

			setCoordBaseMode(pretendBaseMode);
		}

		return oldBaseMode;
	}

	// [VanillaCopy] Keep pinned to the signature of getXWithOffset
	protected int getXWithOffsetRotated(int x, int z, Rotation rotationsCW) {
		BlockFace oldMode = fakeBaseMode(rotationsCW);
		int ret = getXWithOffset(x, z);
		setCoordBaseMode(oldMode);
		return ret;
	}

	// [VanillaCopy] Keep pinned to the signature of getZWithOffset
	protected int getZWithOffsetRotated(int x, int z, Rotation rotationsCW) {
		BlockFace oldMode = fakeBaseMode(rotationsCW);
		int ret = getZWithOffset(x, z);
		setCoordBaseMode(oldMode);
		return ret;
	}

	protected void setBlockStateRotated(AsyncWorldEditor world, BlockData state, int x, int y, int z,
			Rotation rotationsCW, StructureBoundingBox sbb) {
		BlockFace oldMode = fakeBaseMode(rotationsCW);
		setBlockState(world, state, x, y, z, sbb);
		setCoordBaseMode(oldMode);
	}

	@Override
	protected void setBlockState(AsyncWorldEditor worldIn, BlockData blockstateIn, int x, int y, int z,
			StructureBoundingBox sbb) {
		// Making public
		super.setBlockState(worldIn, blockstateIn, x, y, z, sbb);
	}

	// [VanillaCopy] Keep pinned to the signature of getBlockStateFromPos
	public BlockData getBlockStateFromPosRotated(AsyncWorldEditor world, int x, int y, int z, StructureBoundingBox sbb,
			Rotation rotationsCW) {
		BlockFace oldMode = fakeBaseMode(rotationsCW);
		BlockData ret = getBlockStateFromPosBD(world, x, y, z, sbb);
		setCoordBaseMode(oldMode);
		return ret;
	}

	protected void fillBlocksRotated(AsyncWorldEditor world, StructureBoundingBox sbb, int minX, int minY, int minZ,
			int maxX, int maxY, int maxZ, BlockData state, Rotation rotation) {
		BlockFace oldBase = fakeBaseMode(rotation);
		fillWithBlocks(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, state);
		setCoordBaseMode(oldBase);
	}

	protected void fillAirRotated(AsyncWorldEditor world, StructureBoundingBox sbb, int minX, int minY, int minZ,
			int maxX, int maxY, int maxZ, Rotation rotation) {
		BlockFace oldBaseMode = fakeBaseMode(rotation);
		fillWithAir(world, sbb, minX, minY, minZ, maxX, maxY, maxZ);
		setCoordBaseMode(oldBaseMode);
	}

	protected void fillWithAir(AsyncWorldEditor world, StructureBoundingBox boundingBox, int xMin, int yMin, int zMin,
			int xMax, int yMax, int zMax, Predicate<BlockData> predicate) {
		fillWithBlocks(world, boundingBox, xMin, yMin, zMin, xMax, yMax, zMax, AIR, predicate);
	}

	protected void fillWithAir(AsyncWorldEditor world, StructureBoundingBox boundingBox, int xMin, int yMin, int zMin,
			int xMax, int yMax, int zMax) {
		fillWithBlocks(world, boundingBox, xMin, yMin, zMin, xMax, yMax, zMax, AIR);
	}

	protected void fillWithBlocks(AsyncWorldEditor world, StructureBoundingBox boundingBox, int xMin, int yMin,
			int zMin, int xMax, int yMax, int zMax, BlockData state, Predicate<BlockData> predicate) {
		fillWithBlocks(world, boundingBox, xMin, yMin, zMin, xMax, yMax, zMax, state, state, predicate);
	}

	protected void fillWithBlocks(AsyncWorldEditor world, StructureBoundingBox boundingBox, int xMin, int yMin,
			int zMin, int xMax, int yMax, int zMax, BlockData borderState) {
		for (int y = yMin; y <= yMax; ++y) {
			for (int x = xMin; x <= xMax; ++x) {
				for (int z = zMin; z <= zMax; ++z) {

					if (true) {
						this.setBlockState(world, borderState, x, y, z, boundingBox);
					}
				}
			}
		}
	}

	protected void fillWithBlocks(AsyncWorldEditor worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin,
			int zMin, int xMax, int yMax, int zMax, BlockData boundaryBlockState, BlockData insideBlockState,
			boolean existingOnly) {
		for (int i = yMin; i <= yMax; ++i) {
			for (int j = xMin; j <= xMax; ++j) {
				for (int k = zMin; k <= zMax; ++k) {
					if (!existingOnly || this.getBlockStateFromPos(worldIn, j, i, k, boundingboxIn) != Material.AIR) {
						if (i != yMin && i != yMax && j != xMin && j != xMax && k != zMin && k != zMax) {
							this.setBlockState(worldIn, insideBlockState, j, i, k, boundingboxIn);
						} else {
							this.setBlockState(worldIn, boundaryBlockState, j, i, k, boundingboxIn);
						}
					}
				}
			}
		}
	}

	protected void fillWithBlocks(AsyncWorldEditor world, StructureBoundingBox boundingBox, int xMin, int yMin,
			int zMin, int xMax, int yMax, int zMax, BlockData borderState, BlockData interiorState,
			Predicate<BlockData> predicate) {
		for (int y = yMin; y <= yMax; ++y) {
			for (int x = xMin; x <= xMax; ++x) {
				for (int z = zMin; z <= zMax; ++z) {

					if (predicate.test(this.getBlockStateFromPosBD(world, x, y, z, boundingBox))) {

						boolean isBorder = yMin != yMax && (y == yMin || y == yMax)
								|| xMin != xMax && (x == xMin || x == xMax) || zMin != zMax && (z == zMin || z == zMax);

						this.setBlockState(world, isBorder ? borderState : interiorState, x, y, z, boundingBox);
					}
				}
			}
		}
	}

	protected static StructureTFComponent.BlockSelector getStrongholdStones() {
		return strongholdStones;
	}

	protected BlockFace getStructureRelativeRotation(Rotation rotationsCW) {
		return rotationsCW.rotate(getCoordBaseMode());
	}

	/**
	 * Nullify all the sky light in this component bounding box
	 */
	public void nullifySkyLightForBoundingBox(AsyncWorldEditor world) {
		this.nullifySkyLight(world, boundingBox.minX - 1, boundingBox.minY - 1, boundingBox.minZ - 1,
				boundingBox.maxX + 1, boundingBox.maxY + 1, boundingBox.maxZ + 1);
	}

	/**
	 * Nullify all the sky light at the specified positions, using local coordinates
	 */
	protected void nullifySkyLightAtCurrentPosition(AsyncWorldEditor world, int sx, int sy, int sz, int dx, int dy,
			int dz) {
		// resolve all variables to their actual in-world positions
		nullifySkyLight(world, getXWithOffset(sx, sz), getYWithOffset(sy), getZWithOffset(sx, sz),
				getXWithOffset(dx, dz), getYWithOffset(dy), getZWithOffset(dx, dz));
	}

	/**
	 * Nullify all the sky light at the specified positions, using world coordinates
	 */
	protected void nullifySkyLight(AsyncWorldEditor world, int sx, int sy, int sz, int dx, int dy, int dz) {
	}

	/**
	 * Discover if bounding box can fit within the current bounding box object.
	 */
	@Nullable
	public static StructureTFComponent findIntersectingExcluding(List<StructureTFComponent> list,
			StructureBoundingBox toCheck, StructureTFComponent exclude) {
		Iterator<StructureTFComponent> iterator = list.iterator();
		StructureTFComponent structurecomponent;

		do {
			if (!iterator.hasNext()) {
				return null;
			}

			structurecomponent = (StructureTFComponent) iterator.next();
		} while (structurecomponent == exclude || structurecomponent.getBoundingBox() == null
				|| !structurecomponent.getBoundingBox().intersectsWith(toCheck));

		return structurecomponent;
	}

	public BlockPos getBlockPosWithOffset(int x, int y, int z) {
		return new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
	}
}
