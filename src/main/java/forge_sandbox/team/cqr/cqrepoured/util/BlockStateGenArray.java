package forge_sandbox.team.cqr.cqrepoured.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.preparable.PreparableBlockInfo;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.preparable.PreparablePosInfo;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.castle.Chest_Later;
import otd.util.OTDLoottables;

public class BlockStateGenArray {

	public enum GenerationPhase {
		MAIN, POST
	}

	public enum EnumPriority {
		LOWEST(0), LOW(1), MEDIUM(2), HIGH(3), HIGHEST(4);

		private final int value;

		EnumPriority(final int valueIn) {
			this.value = valueIn;
		}

		public int getValue() {
			return this.value;
		}
	}

	private class PriorityBlockInfo {
		private PreparablePosInfo blockInfo;
		private EnumPriority priority;

		private PriorityBlockInfo(PreparablePosInfo blockInfo, EnumPriority priority) {
			this.blockInfo = blockInfo;
			this.priority = priority;
		}

		public PreparablePosInfo getBlockInfo() {
			return this.blockInfo;
		}

		public EnumPriority getPriority() {
			return this.priority;
		}
	}

	private final Random random;
	private Map<BlockPos, PriorityBlockInfo> mainMap = new HashMap<>();
	private Map<BlockPos, PriorityBlockInfo> postMap = new HashMap<>();
//	private List<PreparableEntityInfo> entityList = new ArrayList<>();

	public BlockStateGenArray(Random rand) {
		this.random = rand;
	}

	public Random getRandom() {
		return this.random;
	}

	public Map<BlockPos, PreparablePosInfo> getMainMap() {
		Map<BlockPos, PreparablePosInfo> result = new HashMap<>();
		this.mainMap.forEach((key, value) -> result.put(key, value.getBlockInfo()));
		return result;
	}

	public Map<BlockPos, PreparablePosInfo> getPostMap() {
		Map<BlockPos, PreparablePosInfo> result = new HashMap<>();
		this.postMap.forEach((key, value) -> result.put(key, value.getBlockInfo()));
		return result;
	}

//	public List<PreparableEntityInfo> getEntityMap() {
//		return this.entityList;
//	}

	public boolean addChestWithLootTable(AsyncWorldEditor world, BlockPos pos, BlockFace facing,
			OTDLoottables lootTable, GenerationPhase phase) {
		Chest_Later c = Chest_Later.getChest(world, pos, facing, lootTable, random);
		world.addLater(c);
		// TODO
		return true;
	}

	public static class RelativeChest {
		public BlockPos pos;
		public BlockFace facing;
		public OTDLoottables lootTable;

		public RelativeChest(BlockPos pos, BlockFace facing, OTDLoottables lootTable) {
			this.pos = pos;
			this.facing = facing;
			this.lootTable = lootTable;
		}
	}

	public List<RelativeChest> chests = new ArrayList<>();

	public void addRelativePosChest(AsyncWorldEditor world, BlockPos pos, BlockFace facing, OTDLoottables lootTable) {
		chests.add(new RelativeChest(pos, facing, lootTable));
	}

	public static class RelativeSpawner {
		public BlockPos pos;
		public int floor;
		public boolean isBoss;

		public RelativeSpawner(BlockPos pos, int floor, boolean isBoss) {
			this.pos = pos;
			this.floor = floor;
			this.isBoss = isBoss;
		}
	}

	public List<RelativeSpawner> spawners = new ArrayList<>();

	public void addRelativePosSpawner(AsyncWorldEditor world, BlockPos pos, int floor) {
		spawners.add(new RelativeSpawner(pos, floor, false));
	}

	public void addRelativePosSpawner(AsyncWorldEditor world, BlockPos pos, int floor, boolean isBoss) {
		spawners.add(new RelativeSpawner(pos, floor, isBoss));
	}

	public void addBlockStateMap(Map<BlockPos, BlockData> map, GenerationPhase phase, EnumPriority priority) {
		map.entrySet().forEach(entry -> this.addBlockState(entry.getKey(), entry.getValue(), phase, priority));
	}

	public boolean addBlockState(BlockPos pos, BlockData blockState, GenerationPhase phase, EnumPriority priority) {
		return this.addInternal(phase, new PreparableBlockInfo(pos, blockState), priority);
	}

//	public boolean addSpawner(BlockPos pos, BlockData blockState, NBTTagCompound nbt, GenerationPhase phase,
//			EnumPriority priority) {
//		return this.addInternal(phase, new PreparableBlockInfo(pos, blockState, nbt), priority);
//	}
//
//	public boolean addEntity(BlockPos structurePos, Entity entity) {
//		return this.addInternal(new PreparableEntityInfo(structurePos, entity));
//	}

	public boolean addInternal(GenerationPhase phase, PreparablePosInfo blockInfo, EnumPriority priority) {
		boolean added = false;
		Map<BlockPos, PriorityBlockInfo> mapToAdd = this.getMapFromPhase(phase);
		BlockPos p = new BlockPos(blockInfo.getX(), blockInfo.getY(), blockInfo.getZ());
		PriorityBlockInfo old = mapToAdd.get(p);

		if (old == null || (priority.getValue() > old.getPriority().getValue())) {
			mapToAdd.put(p, new PriorityBlockInfo(blockInfo, priority));
			added = true;
		}

		return added;
	}

//	private boolean addInternal(PreparableEntityInfo entityInfo) {
//		this.entityList.add(entityInfo);
//		return true;
//	}

	private Map<BlockPos, PriorityBlockInfo> getMapFromPhase(GenerationPhase phase) {
		switch (phase) {
		case POST:
			return this.postMap;
		case MAIN:
		default:
			return this.mainMap;

		}
	}
}
