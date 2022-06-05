package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generation;

import javax.annotation.Nullable;

import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonBase;
import otd.lib.async.AsyncWorldEditor;

public final class DungeonGenerationManager {
	private DungeonGenerationManager() {
	}

	public static void generateNow(AsyncWorldEditor world, GeneratableDungeon generatableDungeon,
			@Nullable DungeonBase dungeon) {
		generatableDungeon.generate(world);
	}
}