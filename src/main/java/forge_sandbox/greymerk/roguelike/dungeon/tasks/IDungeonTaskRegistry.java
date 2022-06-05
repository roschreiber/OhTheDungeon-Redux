package forge_sandbox.greymerk.roguelike.dungeon.tasks;

import java.util.List;

import forge_sandbox.greymerk.roguelike.dungeon.DungeonStage;

public interface IDungeonTaskRegistry {

	public void addTask(IDungeonTask task, DungeonStage stage);

	public List<IDungeonTask> getTasks(DungeonStage stage);

}
