package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonBase;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.part.IDungeonPart;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.part.IDungeonPartBuilder;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.inhabitants.DungeonInhabitant;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.inhabitants.DungeonInhabitantManager;
import otd.lib.async.AsyncWorldEditor;

public class GeneratableDungeon {

	private final String dungeonName;
	private final BlockPos pos;
	private final List<IDungeonPart> parts;

	protected GeneratableDungeon(String dungeonName, BlockPos pos, Collection<IDungeonPart> parts) {
		this.dungeonName = dungeonName;
		this.pos = pos;
		this.parts = new ArrayList<>(parts);
	}

	public void mark(int chunkX, int chunkY, int chunkZ) {
	}

	public void markRemovedLight(int x, int y, int z, int light) {
	}

	public void markRemovedLight(BlockPos pos, int light) {
	}

	public void generate(AsyncWorldEditor world) {
		this.tryGeneratePart(world);
		this.tryCheckBlockLight(world);
		this.tryGenerateSkylightMap(world);
		this.tryCheckSkyLight(world);
		this.tryCheckRemovedBlockLight(world);
		this.tryMarkBlockForUpdate(world);
		this.tryNotifyNeighboursRespectDebug(world);

//		CQRMain.logger.info("Generated dungeon {} at {}", this.dungeonName, this.pos);
//		CQRMain.logger.debug("Total: {} secs {} millis", this.generationTimes[0] / 1_000_000_000, this.generationTimes[0] / 1_000_000 % 1_000);
//		CQRMain.logger.debug("Parts: {} secs {} millis", this.generationTimes[1] / 1_000_000_000, this.generationTimes[1] / 1_000_000 % 1_000);
//		CQRMain.logger.debug("Blocklight: {} secs {} millis", this.generationTimes[2] / 1_000_000_000, this.generationTimes[2] / 1_000_000 % 1_000);
//		CQRMain.logger.debug("SkylightMap: {} secs {} millis", this.generationTimes[3] / 1_000_000_000, this.generationTimes[3] / 1_000_000 % 1_000);
//		CQRMain.logger.debug("Skylight: {} secs {} millis", this.generationTimes[4] / 1_000_000_000, this.generationTimes[4] / 1_000_000 % 1_000);
//		CQRMain.logger.debug("RemovedBlocklight: {} secs {} millis", this.generationTimes[5] / 1_000_000_000, this.generationTimes[5] / 1_000_000 % 1_000);
//		CQRMain.logger.debug("Sync: {} secs {} millis", this.generationTimes[6] / 1_000_000_000, this.generationTimes[6] / 1_000_000 % 1_000);
//		CQRMain.logger.debug("Updates: {} secs {} millis", this.generationTimes[7] / 1_000_000_000, this.generationTimes[7] / 1_000_000 % 1_000);
	}

	private void tryGeneratePart(AsyncWorldEditor world) {
		for (IDungeonPart part : this.parts) {
			part.generate(world, this);
		}
	}

	private void tryCheckBlockLight(AsyncWorldEditor world) {
	}

	private void tryGenerateSkylightMap(AsyncWorldEditor world) {
	}

	private void tryCheckSkyLight(AsyncWorldEditor world) {
	}

	private void tryCheckRemovedBlockLight(AsyncWorldEditor world) {
	}

	private void tryMarkBlockForUpdate(AsyncWorldEditor world) {
	}

	private void tryNotifyNeighboursRespectDebug(AsyncWorldEditor world) {
	}

	public String getDungeonName() {
		return dungeonName;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public static class Builder {

		private final String dungeonName;
		private final BlockPos pos;
		private final DungeonInhabitant defaultInhabitant;
		private final List<Function<AsyncWorldEditor, IDungeonPart>> partBuilders = new ArrayList<>();

		public Builder(AsyncWorldEditor world, BlockPos pos, DungeonBase dungeonConfig) {
			this.dungeonName = dungeonConfig.getDungeonName();
			this.pos = pos;
			this.defaultInhabitant = DungeonInhabitantManager.instance()
					.getInhabitantByDistanceIfDefault(dungeonConfig.getDungeonMob(), world, pos.getX(), pos.getZ());
		}

		public Builder(AsyncWorldEditor world, BlockPos pos, String dungeonName, String defaultnhabitant) {
			this.dungeonName = dungeonName;
			this.pos = pos;
			this.defaultInhabitant = DungeonInhabitantManager.instance()
					.getInhabitantByDistanceIfDefault(defaultnhabitant, world, pos.getX(), pos.getZ());
		}

		public GeneratableDungeon build(AsyncWorldEditor world) {
			List<IDungeonPart> parts = this.partBuilders.stream().map(builder -> builder.apply(world))
					.filter(Objects::nonNull).collect(Collectors.toList());
			return new GeneratableDungeon(this.dungeonName, this.pos, parts);
		}

		public void add(IDungeonPartBuilder partBuilder) {
			this.add(partBuilder, this.getPlacement(this.pos, this.defaultInhabitant));
		}

		public void add(IDungeonPartBuilder partBuilder, BlockPos partPos) {
			this.add(partBuilder, this.getPlacement(partPos));
		}

		public void add(IDungeonPartBuilder partBuilder, BlockPos partPos, DungeonInhabitant inhabitant) {
			this.add(partBuilder, this.getPlacement(partPos, inhabitant));
		}

		public void add(IDungeonPartBuilder partBuilder, DungeonPlacement placement) {
			this.partBuilders.add(world1 -> partBuilder.build(world1, placement));
		}

		public void addAll(Collection<IDungeonPartBuilder> partBuilders) {
			partBuilders.forEach(this::add);
		}

		public DungeonPlacement getPlacement(BlockPos partPos) {
			return this.getPlacement(partPos, this.defaultInhabitant);
		}

		public DungeonPlacement getPlacement(BlockPos partPos, DungeonInhabitant inhabitant) {
			return new DungeonPlacement(this.pos, partPos, inhabitant);
		}

	}

}
