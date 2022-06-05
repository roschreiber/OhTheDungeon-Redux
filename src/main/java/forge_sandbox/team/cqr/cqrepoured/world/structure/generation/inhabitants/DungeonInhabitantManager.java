package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.inhabitants;

import otd.lib.async.AsyncWorldEditor;

public class DungeonInhabitantManager {
	public final static DungeonInhabitant DEFAULT_DUNGEON_INHABITANT = new DungeonInhabitant();

	private DungeonInhabitantManager() {

	}

	private static DungeonInhabitantManager instance = new DungeonInhabitantManager();

	public static DungeonInhabitantManager instance() {
		return instance;
	}

	public DungeonInhabitant getInhabitantByDistanceIfDefault(String name, AsyncWorldEditor world, int blockX,
			int blockZ) {
		return DEFAULT_DUNGEON_INHABITANT;
	}

	public DungeonInhabitant getRandomInhabitant() {
		return DEFAULT_DUNGEON_INHABITANT;
	}
}
