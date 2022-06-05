package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons;

import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.DungeonSpawnPos;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.DungeonGenerationManager;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.AbstractDungeonGenerator;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.inhabitants.DungeonInhabitantManager;
import otd.lib.async.AsyncWorldEditor;

/**
 * Copyright (c) 29.04.2019 Developed by DerToaster98 GitHub:
 * https://github.com/DerToaster98
 */
public abstract class DungeonBase {

	protected String name;
	protected boolean enabled = true;
	protected int iconID = 0;

	protected int weight = 0;
	protected int chance = 0;
	protected int spawnLimit = -1;
	protected int[] allowedDims = new int[0];
	protected boolean allowedDimsAsBlacklist = false;
//	protected ResourceLocation[] allowedBiomes = new ResourceLocation[0];
	protected String[] allowedBiomeTypes = new String[0];
	protected boolean allowedInAllBiomes = false;
//	protected ResourceLocation[] disallowedBiomes = new ResourceLocation[0];
	protected String[] disallowedBiomeTypes = new String[0];
	protected DungeonSpawnPos[] lockedPositions = new DungeonSpawnPos[0];
	protected boolean spawnOnlyBehindWall = false;
	protected String[] modDependencies = new String[0];
	protected String[] dungeonDependencies = new String[0];
	protected String[] structuresPreventingGeneration = new String[0];
	protected int structureCheckRadius = 0;

	protected boolean treatWaterAsAir = false;
	protected int underGroundOffset = 0;
	protected boolean fixedY = false;
	protected int yOffsetMin = 0;
	protected int yOffsetMax = 0;

	protected String dungeonMob = DungeonInhabitantManager.DEFAULT_DUNGEON_INHABITANT.getName();
	protected boolean replaceBanners = true;

	protected boolean buildSupportPlatform = true;
	protected BlockData supportBlock = null;
	protected BlockData supportTopBlock = null;

	protected boolean useCoverBlock;
	protected BlockData coverBlock;

	// Protection system stuff
	protected boolean enableProtectionSystem = false;
	protected boolean preventBlockPlacing = false;
	protected boolean preventBlockBreaking = false;
	protected boolean preventExplosionsTNT = false;
	protected boolean preventExplosionsOther = false;
	protected boolean preventFireSpreading = false;
	protected boolean preventEntitySpawning = false;
	protected boolean ignoreNoBossOrNexus = false;

	protected DungeonBase(String name, Properties prop) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public abstract AbstractDungeonGenerator<?> createDungeonGenerator(AsyncWorldEditor world, int x, int y, int z,
			Random rand);

	public void generate(AsyncWorldEditor world, int x, int z, Random rand, boolean generateImmediately) {
		this.generate(world, x, this.getYForPos(world, x, z, rand), z, rand);
	}

	public int getYForPos(AsyncWorldEditor world, int x, int z, Random rand) {
		int y = 0;
		if (!this.fixedY) {
			// TODOo make this a dungeon config option?
			int r = 16;
			int[] arr = new int[(r * 2 + 1) * (r * 2 + 1)];
			for (int ix = -r; ix <= r; ix++) {
				for (int iz = -r; iz <= r; iz++) {
					arr[(ix + r) * (r * 2 + 1) + (iz + r)] = world.getVirtualGroundHeight();
				}
			}
			Arrays.sort(arr);

			y = arr[(int) (arr.length * 0.6)];
			y += DungeonGenUtils.randomBetween(this.yOffsetMin, this.yOffsetMax, rand);
		} else {
			y = DungeonGenUtils.randomBetween(this.yOffsetMin, this.yOffsetMax, rand);
		}
		y -= this.getUnderGroundOffset();
		return y;
	}

	public void generate(AsyncWorldEditor world, int x, int y, int z, Random rand) {
		AbstractDungeonGenerator<?> generator = this.createDungeonGenerator(world, x, y, z, rand);
		DungeonGenerationManager.generateNow(world, generator.get(), this);
	}

	public void generateWithOffsets(AsyncWorldEditor world, int x, int y, int z, Random rand) {
		if (!this.fixedY) {
			y += DungeonGenUtils.randomBetween(this.yOffsetMin, this.yOffsetMax, rand);
		}
		y -= this.getUnderGroundOffset();
		this.generate(world, x, y, z, rand);
	}

	public boolean canSpawnAt(AsyncWorldEditor world, Biome biome, BlockPos pos) {
		return true;
	}

	public String getDungeonName() {
		return this.name;
	}

	public int getIconID() {
		return this.iconID;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public int getWeight() {
		return this.weight;
	}

	public int getChance() {
		return this.chance;
	}

	public int getSpawnLimit() {
		return this.spawnLimit;
	}

	public int[] getAllowedDims() {
		return this.allowedDims;
	}

	public boolean isAllowedDimsAsBlacklist() {
		return this.allowedDimsAsBlacklist;
	}

	public String[] getAllowedBiomeTypes() {
		return this.allowedBiomeTypes;
	}

	public boolean isAllowedInAllBiomes() {
		return this.allowedInAllBiomes;
	}

	public String[] getDisallowedBiomeTypes() {
		return this.disallowedBiomeTypes;
	}

	public DungeonSpawnPos[] getLockedPositions() {
		return this.lockedPositions;
	}

	public boolean doesSpawnOnlyBehindWall() {
		return this.spawnOnlyBehindWall;
	}

	public String[] getModDependencies() {
		return this.modDependencies;
	}

	public String[] getDungeonDependencies() {
		return this.dungeonDependencies;
	}

	public boolean treatWaterAsAir() {
		return this.treatWaterAsAir;
	}

	public int getUnderGroundOffset() {
		return this.underGroundOffset;
	}

	public int getYOffsetMin() {
		return this.yOffsetMin;
	}

	public int getYOffsetMax() {
		return this.yOffsetMax;
	}

	public String getDungeonMob() {
		return this.dungeonMob;
	}

	public boolean replaceBanners() {
		return this.replaceBanners;
	}

	public boolean doBuildSupportPlatform() {
		return this.buildSupportPlatform;
	}

	public BlockData getSupportBlock() {
		return this.supportBlock;
	}

	public BlockData getSupportTopBlock() {
		return this.supportTopBlock;
	}

	public boolean isCoverBlockEnabled() {
		return this.useCoverBlock;
	}

	public BlockData getCoverBlock() {
		return this.coverBlock;
	}

	public boolean isProtectionSystemEnabled() {
		return this.enableProtectionSystem;
	}

	public boolean preventBlockPlacing() {
		return this.preventBlockPlacing;
	}

	public boolean preventBlockBreaking() {
		return this.preventBlockBreaking;
	}

	public boolean preventExplosionsTNT() {
		return this.preventExplosionsTNT;
	}

	public boolean preventExplosionsOther() {
		return this.preventExplosionsOther;
	}

	public boolean preventFireSpreading() {
		return this.preventFireSpreading;
	}

	public boolean preventEntitySpawning() {
		return this.preventEntitySpawning;
	}

	public boolean ignoreNoBossOrNexus() {
		return this.ignoreNoBossOrNexus;
	}

}
