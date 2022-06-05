package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import forge_sandbox.greymerk.roguelike.dungeon.DungeonStage;
import otd.util.DungeonDelayTask;

public class DungeonTaskRegistry implements IDungeonTaskRegistry {

	public static DungeonTaskRegistry registry;
	private Map<DungeonStage, List<IDungeonTask>> tasks;

	public DungeonTaskRegistry() {
		this.tasks = new HashMap<>();
		this.addTask(new DungeonTaskLayout(), DungeonStage.LAYOUT);
		this.addTask(new DungeonTaskEncase(), DungeonStage.ENCASE);
		this.addTask(new DungeonTaskTunnels(), DungeonStage.TUNNELS);
		this.addTask(new DungeonTaskRooms(), DungeonStage.ROOMS);
		this.addTask(new DungeonTaskSegments(), DungeonStage.SEGMENTS);
		this.addTask(new DungeonTaskLinks(), DungeonStage.LINKS);
		this.addTask(new DungeonTaskTower(), DungeonStage.TOWER);
		this.addTask(new DungeonTaskFilters(), DungeonStage.FILTERS);
		this.addTask(new DungeonTaskLoot(), DungeonStage.LOOT);
		this.addTask(new DungeonDelayTask(), DungeonStage.AFTER);
	}

	@Override
	public final void addTask(IDungeonTask task, DungeonStage stage) {
		if (!tasks.containsKey(stage)) {
			this.tasks.put(stage, new ArrayList<>());
		}

		tasks.get(stage).add(task);
	}

	@Override
	public List<IDungeonTask> getTasks(DungeonStage stage) {
		if (!this.tasks.containsKey(stage)) {
			return new ArrayList<>();
		}
		return this.tasks.get(stage);
	}

	public static IDungeonTaskRegistry getTaskRegistry() {
		if (registry == null) {
			registry = new DungeonTaskRegistry();
		}

		return registry;
	}
}
