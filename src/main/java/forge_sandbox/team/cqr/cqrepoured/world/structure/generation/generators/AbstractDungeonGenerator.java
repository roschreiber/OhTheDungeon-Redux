package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators;

import java.util.Random;
import java.util.function.Supplier;

import forge_sandbox.BlockPos;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonBase;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation.GeneratableDungeon;
import otd.lib.async.AsyncWorldEditor;

public abstract class AbstractDungeonGenerator<T extends DungeonBase> implements Supplier<GeneratableDungeon> {

	protected final AsyncWorldEditor world;
	protected final Random random;
	protected final BlockPos pos;
	protected final T dungeon;
	protected final GeneratableDungeon.Builder dungeonBuilder;

	protected AbstractDungeonGenerator(AsyncWorldEditor world, BlockPos pos, T dungeon, Random random) {
		this.world = world;
		this.pos = pos;
		this.dungeon = dungeon;
		this.random = random;
		this.dungeonBuilder = new GeneratableDungeon.Builder(this.world, this.pos, this.dungeon);
	}

	@Override
	public GeneratableDungeon get() {
		try {
			this.preProcess();
			this.buildStructure();
			this.postProcess();
			return this.dungeonBuilder.build(this.world);
		} catch (Throwable e) {
			// TODOo handle this elsewhere, DungeonPreparationHelper maybe?
//			Bukkit.getLogger().log(Level.SEVERE, "Failed to prepare dungeon {} for generation at {}", this.dungeon,
//					this.pos, e.toString());
			throw new RuntimeException(e);
		}
	}

	// Actually having 3 methods here is useless as they are just called one after
	// another

	protected abstract void preProcess();

	protected abstract void buildStructure();

	protected abstract void postProcess();

	public AsyncWorldEditor getWorld() {
		return this.world;
	}

	public T getDungeon() {
		return this.dungeon;
	}

}
