package forge_sandbox.twilightforest.structures;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.BlockPos;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import otd.util.RotationMirror;
import otd.util.RotationMirror.Mirror;
import otd.util.RotationMirror.Rotation;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.TFFeature;

public abstract class StructureTFComponent {

	protected StructureBoundingBox boundingBox;
	public StructureTFDecorator deco = null;
	public int spawnListIndex = 0;
	protected TFFeature feature = TFFeature.NOTHING;
	protected BlockFace coordBaseMode = null;
	protected Rotation rotation;
	protected Mirror mirror;
	protected int componentType = 0;

	public final static BlockFace[] HORIZONTALS = { BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH };

	public StructureTFComponent() {
		this.rotation = Rotation.NONE;
	}

	public StructureTFComponent(int i) {
		this.componentType = i;
		this.rotation = Rotation.NONE;
	}

	public StructureTFComponent(TFFeature feature, int i) {
		this.componentType = i;
		this.feature = feature;
		this.rotation = Rotation.NONE;
	}

	public TFFeature getFeatureType() {
		return feature;
	}

	protected static boolean shouldDebug() {
		return false;
	}

	/**
	 * Does this component fall under block protection when progression is turned
	 * on, normally true
	 */
	public boolean isComponentProtected() {
		return true;
	}

	public int getComponentType() {
		return this.componentType;
	}

	protected void randomlyPlaceBlock(AsyncWorldEditor worldIn, StructureBoundingBox boundingboxIn, Random rand,
			float chance, int x, int y, int z, BlockData blockstateIn) {
		if (rand.nextFloat() < chance) {
			this.setBlockState(worldIn, blockstateIn, x, y, z, boundingboxIn);
		}
	}

	@Nullable
	public BlockFace getCoordBaseMode() {
		return this.coordBaseMode;
	}

	public StructureBoundingBox getBoundingBox() {
		return this.boundingBox;
	}

	public static StructureTFComponent findIntersecting(List<StructureTFComponent> listIn,
			StructureBoundingBox boundingboxIn) {
		for (StructureTFComponent structurecomponent : listIn) {
			if (structurecomponent.getBoundingBox() != null
					&& structurecomponent.getBoundingBox().intersectsWith(boundingboxIn)) {
				return structurecomponent;
			}
		}

		return null;
	}

	protected void fillWithRandomizedBlocks(AsyncWorldEditor worldIn, StructureBoundingBox boundingboxIn, int minX,
			int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand,
			BlockSelector blockselector) {
		for (int i = minY; i <= maxY; ++i) {
			for (int j = minX; j <= maxX; ++j) {
				for (int k = minZ; k <= maxZ; ++k) {
					if (!alwaysReplace || this.getBlockStateFromPos(worldIn, j, i, k, boundingboxIn) != Material.AIR) {
						blockselector.selectBlocks(rand, j, i, k,
								i == minY || i == maxY || j == minX || j == maxX || k == minZ || k == maxZ);
						this.setBlockState(worldIn, blockselector.getBlockState(), j, i, k, boundingboxIn);
					}
				}
			}
		}
	}

	protected void setBlockState(AsyncWorldEditor worldIn, BlockData blockstateIn, int x, int y, int z,
			StructureBoundingBox boundingboxIn) {
		BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));

		if (boundingboxIn.isVecInside(blockpos)) {
			if (this.mirror != Mirror.NONE) {
				blockstateIn = RotationMirror.withMirror(blockstateIn, this.mirror);
			}

			if (this.rotation != Rotation.NONE) {
				blockstateIn = RotationMirror.withRotation(blockstateIn, this.rotation);
			}

			worldIn.setBlockState(blockpos, blockstateIn, 2);
		}
	}

	protected void setBlockState(AsyncWorldEditor worldIn, Material type, int x, int y, int z,
			StructureBoundingBox boundingboxIn) {
		BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));

		if (boundingboxIn.isVecInside(blockpos)) {
			worldIn.setBlockState(blockpos, type, 2);
		}
	}

	protected Material getBlockStateFromPos(AsyncWorldEditor worldIn, int x, int y, int z,
			StructureBoundingBox boundingboxIn) {
		int i = this.getXWithOffset(x, z);
		int j = this.getYWithOffset(y);
		int k = this.getZWithOffset(x, z);
		BlockPos blockpos = new BlockPos(i, j, k);
		return !boundingboxIn.isVecInside(blockpos) ? Material.AIR : worldIn.getBlockState(blockpos);
	}

	protected BlockData getBlockStateFromPosBD(AsyncWorldEditor worldIn, int x, int y, int z,
			StructureBoundingBox boundingboxIn) {
		int i = this.getXWithOffset(x, z);
		int j = this.getYWithOffset(y);
		int k = this.getZWithOffset(x, z);
		BlockPos blockpos = new BlockPos(i, j, k);
		return !boundingboxIn.isVecInside(blockpos) ? Bukkit.createBlockData(Material.AIR)
				: worldIn.getBlockStateBD(blockpos);
	}

	protected int getXWithOffset(int x, int z) {
		BlockFace enumfacing = this.getCoordBaseMode();

		if (enumfacing == null) {
			return x;
		} else {
			switch (enumfacing) {
			case NORTH:
			case SOUTH:
				return this.boundingBox.minX + x;
			case WEST:
				return this.boundingBox.maxX - z;
			case EAST:
				return this.boundingBox.minX + z;
			default:
				return x;
			}
		}
	}

	public void setCoordBaseMode(@Nullable BlockFace facing) {
		this.coordBaseMode = facing;

		if (facing == null) {
			this.rotation = Rotation.NONE;
			this.mirror = Mirror.NONE;
		} else {
			switch (facing) {
			case SOUTH:
				this.mirror = Mirror.LEFT_RIGHT;
				this.rotation = Rotation.NONE;
				break;
			case WEST:
				this.mirror = Mirror.LEFT_RIGHT;
				this.rotation = Rotation.CLOCKWISE_90;
				break;
			case EAST:
				this.mirror = Mirror.NONE;
				this.rotation = Rotation.CLOCKWISE_90;
				break;
			default:
				this.mirror = Mirror.NONE;
				this.rotation = Rotation.NONE;
			}
		}
	}

	protected int getYWithOffset(int y) {
		return this.getCoordBaseMode() == null ? y : y + this.boundingBox.minY;
	}

	protected int getZWithOffset(int x, int z) {
		BlockFace enumfacing = this.getCoordBaseMode();

		if (enumfacing == null) {
			return z;
		} else {
			switch (enumfacing) {
			case NORTH:
				return this.boundingBox.maxZ - z;
			case SOUTH:
				return this.boundingBox.minZ + z;
			case WEST:
			case EAST:
				return this.boundingBox.minZ + x;
			default:
				return z;
			}
		}
	}

	public abstract static class BlockSelector {
		protected Material blockstate = Material.AIR;

		public abstract void selectBlocks(Random rand, int x, int y, int z, boolean wall);

		public Material getBlockState() {
			return this.blockstate;
		}
	}

	public abstract boolean addComponentParts(AsyncWorldEditor worldIn, Random randomIn,
			StructureBoundingBox structureBoundingBoxIn);

	public void buildComponent(StructureTFComponent parent, List<StructureTFComponent> list, Random rand) {

	}
}