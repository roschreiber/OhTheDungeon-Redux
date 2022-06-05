package forge_sandbox.greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import forge_sandbox.greymerk.roguelike.dungeon.settings.ISettings;
import forge_sandbox.greymerk.roguelike.dungeon.settings.LevelSettings;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.IDungeonTask;
import forge_sandbox.greymerk.roguelike.dungeon.tasks.IDungeonTaskRegistry;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import otd.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class DungeonGenerator {

	public static int generate(IWorldEditor editor, IDungeon dungeon, ISettings settings, IDungeonTaskRegistry tasks) {
//try {
		Coord start = dungeon.getPosition();
		Random rand = Dungeon.getRandom(editor, start);
		List<IDungeonLevel> levels = dungeon.getLevels();
		int numLevels = settings.getNumLevels();

		// create level objects
		for (int i = 0; i < numLevels; ++i) {
			LevelSettings levelSettings = settings.getLevelSettings(i);
			DungeonLevel level = new DungeonLevel(editor, rand, levelSettings, new Coord(start));
			levels.add(level);
		}

		BukkitRunnable run = new BukkitRunnable() {
			DungeonStage[] stages = DungeonStage.values();
			int index = 0;
			// int div = 0;
			int sub_index = 0;

//                private final static int STEP = 12;
			@Override
			public void run() {
				if (index < stages.length) {
					DungeonStage stage = stages[index];
					List<IDungeonTask> list = tasks.getTasks(stage);
					if (list != null && list.size() > 0) {
						IDungeonTask task = list.get(0);
						if (task.execute(editor, rand, dungeon, settings, sub_index)) {
							sub_index = 0;
							index++;
							return;
						} else {
							sub_index++;
							return;
						}
					} else {
						index++;
						return;
					}
				} else {
					this.cancel();
				}
			}
		};
		run.runTaskTimer(Main.instance, 1, 1);

		return 1;
	}
}
