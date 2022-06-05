package forge_sandbox.jaredbgreat.dldungeons.planner;

/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */

import forge_sandbox.jaredbgreat.dldungeons.Difficulty;
import forge_sandbox.jaredbgreat.dldungeons.pieces.Spawner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.bukkit.World;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
//import forge_sandbox.jaredbgreat.dldungeons.ConfigHandler;

public class SpawnerCounter {
	final ArrayList<Spawner> list;
	int dungeonSize;

	public SpawnerCounter() {
		list = new ArrayList<>();
		dungeonSize = 0;
	}

	public void addRoom(int size) {
		dungeonSize += size;
	}

	public void addSpawner(Spawner s) {
		list.add(s);
	}

	public void fixSpawners(World world, Dungeon dungeon, Random random) {

		Difficulty difficulty = Difficulty.NORM;
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
			difficulty = swc.doomlike.difficulty;
		}

		int targetNum = dungeonSize / difficulty.blocksPerSpawner;
		targetNum += (targetNum * Math.max(5, Math.min(0, (random.nextGaussian() + 2)))) / 10;
		if (targetNum < 0)
			targetNum = 0;
		int existing = list.size();
		if (existing <= targetNum) {
			return;
		}
		Collections.shuffle(list, random);
		for (int i = targetNum; i < existing; i++) {
			Spawner s = list.get(i);
			dungeon.rooms.get(s.getRoom()).spawners.remove(s);
		}
	}
}