/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import otd.config.DungeonWorldConfig;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.util.RandomCollection;
import otd.world.task.DungeonChunkTask;
import otd.world.task.DungeonPlaceTask;
import otd.world.task.DungeonWorldTask;

/**
 *
 * @author shadow
 */
public class ChunkList {
	public static Map<String, DungeonType> chunks = new HashMap<>();
	public static Set<String> chunk_set = new HashSet<>();
	public static Map<int[], List<int[]>> groups = new HashMap<>();

	public static List<int[]> chunk_table;

	public static void rebuildChunkMap() {
		chunk_table = new ArrayList<>();
		zonePos = new ZonePos();
		for (int i = 0; i < 30; i++) {
			int[] next = getNextZone();
			chunk_table.add(next);
		}
	}

	public static void clear() {
		ChunkList.chunks.clear();
		ChunkList.groups.clear();
		ChunkList.task_pool.clear();
		ChunkList.chunk_set.clear();
	}

	public final static Map<DungeonType, Integer> dict;
	static {
		dict = new HashMap<>();
		dict.put(DungeonType.BattleTower, 2);
		dict.put(DungeonType.Roguelike, 6);
		dict.put(DungeonType.Doomlike, 4);
		dict.put(DungeonType.DungeonMaze, 2);
		dict.put(DungeonType.Draylar, 2);
		dict.put(DungeonType.Aether, 2);
		dict.put(DungeonType.Lich, 2);
		dict.put(DungeonType.AntMan, 6);
		dict.put(DungeonType.Castle, 3);
	}
	public static List<DungeonWorldTask> task_pool = new ArrayList<>();

	public static void initChunksMap(DungeonWorldConfig dwc) {
		chunk_set.clear();
		chunks.clear();
		groups.clear();
		task_pool.clear();
		RandomCollection<DungeonType> r = new RandomCollection<>();
		SimpleWorldConfig swc = WorldConfig.wc.dict.get(WorldDefine.WORLD_NAME);
		if (swc.roguelike.doNaturalSpawn)
			r.add(swc.roguelike_weight, DungeonType.Roguelike);
		if (swc.doomlike.doNaturalSpawn)
			r.add(swc.doomlike_weight, DungeonType.Doomlike);
		if (swc.battletower.doNaturalSpawn)
			r.add(swc.battle_tower_weight, DungeonType.BattleTower);
		if (swc.smoofydungeon.doNaturalSpawn)
			r.add(swc.smoofy_weight, DungeonType.DungeonMaze);
		if (swc.draylar_battletower.doNaturalSpawn)
			r.add(swc.draylar_weight, DungeonType.Draylar);
		if (swc.ant_man_dungeon.doNaturalSpawn)
			r.add(swc.antman_weight, DungeonType.AntMan);
		if (swc.aether_dungeon.doNaturalSpawn)
			r.add(swc.aether_weight, DungeonType.Aether);
		if (swc.lich_tower.doNaturalSpawn)
			r.add(swc.lich_weight, DungeonType.Lich);
		if (swc.castle.doNaturalSpawn)
			r.add(swc.castle_weight, DungeonType.Castle);
//		if (WorldEdit.isReady() && !swc.custom_dungeons.isEmpty())
//			r.add(swc.custom_dungeon_weight, DungeonType.CustomDungeon);

		if (r.isEmpty())
			return;
		zonePos = new ZonePos();
		int count = dwc.dungeon_count;

		for (int i = 0; i < count; i++) {
			int[] next = getNextZone();
			DungeonType dungeon = r.next();
			int d = dict.get(dungeon);
			List<int[]> sub = new ArrayList<>();
			for (int x = next[0] - d; x <= next[0] + d; x++) {
				for (int z = next[1] - d; z <= next[1] + d; z++) {
					chunks.put(x + "," + z, dungeon);
					chunk_set.add(x + "," + z);
					sub.add(new int[] { x, z });

					task_pool.add(new DungeonChunkTask(x, z));
				}
			}
			groups.put(next, sub);
			task_pool.add(new DungeonPlaceTask(next[0], next[1], dungeon));
		}
	}

	public static class ZonePos {
		int cx, cy;
		int r;

		public ZonePos() {
			cx = 48;
			cy = 48;
			r = 48;
		}
	}

//	public static void main(String[] args) {
//		zonePos = new ZonePos();
//		for (int i = 0; i < 60; i++)
//			System.out.println(Arrays.toString(getNextZone()));
//	}

	public static ZonePos zonePos;

	public static int[] getNextZone() {
		int x = zonePos.cx;
		int y = zonePos.cy;
		int r = zonePos.r;
		if (x > 0 && x == y) {
			x = x - 32;
			zonePos.cx = x;
			zonePos.cy = y;
			zonePos.r = r;
			return new int[] { x, y };
		}
		if (x < 0 && (-x) == y) {
			y = y - 32;
			zonePos.cx = x;
			zonePos.cy = y;
			zonePos.r = r;
			return new int[] { x, y };
		}
		if (x < 0 && x == y) {
			x = x + 32;
			zonePos.cx = x;
			zonePos.cy = y;
			zonePos.r = r;
			return new int[] { x, y };
		}
		if (x > 0 && (-y) == x) {
			y = y + 32;
			zonePos.cx = x;
			zonePos.cy = y;
			zonePos.r = r;
			return new int[] { x, y };
		}
		if (y == r) {
			x = x - 32;
			zonePos.cx = x;
			zonePos.cy = y;
			zonePos.r = r;
			return new int[] { x, y };
		}
		if (x == -r) {
			y = y - 32;
			zonePos.cx = x;
			zonePos.cy = y;
			zonePos.r = r;
			return new int[] { x, y };
		}
		if (y == -r) {
			x = x + 32;
			zonePos.cx = x;
			zonePos.cy = y;
			zonePos.r = r;
			return new int[] { x, y };
		}
		if (x == r) {
			y = y + 32;
			if (y == r) {
				x = x + 32;
				y = y + 32;
				r = r + 32;
			}
			zonePos.cx = x;
			zonePos.cy = y;
			zonePos.r = r;
			return new int[] { x, y };
		}
		return null;
	}
}
