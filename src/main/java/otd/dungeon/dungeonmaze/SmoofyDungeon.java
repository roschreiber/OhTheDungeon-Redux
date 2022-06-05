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
package otd.dungeon.dungeonmaze;

import otd.dungeon.dungeonmaze.populator.maze.structure.OasisChunkPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.LibraryRoomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.GravelPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.EmptyRoom;
import otd.dungeon.dungeonmaze.populator.maze.structure.MassiveRoomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.SanctuaryPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.GreatFurnaceRoomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.WaterWellRoomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.ArmoryRoomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.StructPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.AbandonedDefenceCastleRoomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.TopTurveRoomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.structure.EntrancePopulator;
import otd.dungeon.dungeonmaze.populator.maze.spawner.BossRoomInsanePopulator;
import otd.dungeon.dungeonmaze.populator.maze.spawner.BossRoomHardPopulator;
import otd.dungeon.dungeonmaze.populator.maze.spawner.BossRoomEasyPopulator;
import otd.dungeon.dungeonmaze.populator.maze.spawner.CreeperSpawnerRoomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.spawner.BlazeSpawnerRoomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.BrokenWallsPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.WebPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.SoulsandPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.MushroomPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.TorchPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.VinePopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.LavaOutOfWallPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.MossPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.IronBarPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.WaterOutOfWallPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.ChestPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.NetherrackPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.SlabPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.PumpkinPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.LadderPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.SkullPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.CoalOrePopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.CrackedStoneBrickPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.CobblestonePopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.PoolPopulator;
import otd.dungeon.dungeonmaze.populator.maze.decoration.LanternPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeLayerBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.util.RandomCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class SmoofyDungeon {
	public static List<MazeRoomBlockPopulator> decoration;
	static {
		decoration = new ArrayList<>();
		{
			BrokenWallsPopulator p = new BrokenWallsPopulator();
			decoration.add(p);
		}
		{
			ChestPopulator p = new ChestPopulator();
			decoration.add(p);
		}
		{
			CoalOrePopulator p = new CoalOrePopulator();
			decoration.add(p);
		}
		{
			CobblestonePopulator p = new CobblestonePopulator();
			decoration.add(p);
		}
		{
			CrackedStoneBrickPopulator p = new CrackedStoneBrickPopulator();
			decoration.add(p);
		}
		{
//            ExplosionPopulator p = new ExplosionPopulator();
//            decoration.add(0.1, p);
		}
//        {
//            GravePopulator p = new GravePopulator();
//            decoration.add(p);
//        }
		{
			IronBarPopulator p = new IronBarPopulator();
			decoration.add(p);
		}
		{
			LadderPopulator p = new LadderPopulator();
			decoration.add(p);
		}
		{
			LanternPopulator p = new LanternPopulator();
			decoration.add(p);
		}
		{
			LavaOutOfWallPopulator p = new LavaOutOfWallPopulator();
			decoration.add(p);
		}
		{
			MossPopulator p = new MossPopulator();
			decoration.add(p);
		}
		{
			MushroomPopulator p = new MushroomPopulator();
			decoration.add(p);
		}
		{
			NetherrackPopulator p = new NetherrackPopulator();
			decoration.add(p);
		}
		{
//            OresInGroundPopulator p = new OresInGroundPopulator();
//            decoration.add(0.1, p);
		}
		{
			PoolPopulator p = new PoolPopulator();
			decoration.add(p);
		}
		{
			PumpkinPopulator p = new PumpkinPopulator();
			decoration.add(p);
		}
		{
			SkullPopulator p = new SkullPopulator();
			decoration.add(p);
		}
		{
			SlabPopulator p = new SlabPopulator();
			decoration.add(p);
		}
		{
			SoulsandPopulator p = new SoulsandPopulator();
			decoration.add(p);
		}
		{
			TorchPopulator p = new TorchPopulator();
			decoration.add(p);
		}
		{
			VinePopulator p = new VinePopulator();
			decoration.add(p);
		}
		{
			WaterOutOfWallPopulator p = new WaterOutOfWallPopulator();
			decoration.add(p);
		}
		{
			WebPopulator p = new WebPopulator();
			decoration.add(p);
		}
	}
	public static Map<Integer, RandomCollection<MazeRoomBlockPopulator>> ROOMS;
	static {
		ROOMS = new HashMap<>();
		{
			BlazeSpawnerRoomPopulator p = new BlazeSpawnerRoomPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
		{
			BossRoomEasyPopulator p = new BossRoomEasyPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
		{
			CreeperSpawnerRoomPopulator p = new CreeperSpawnerRoomPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
//        {
//            SilverfishBlockPopulator p = new SilverfishBlockPopulator();
//            int min = p.getMinimumLayer();
//            int max = p.getMaximumLayer();
//            for(int i = min; i <= max; i++) {
//                if(!ROOMS.containsKey(i)) ROOMS.put(i, new RandomCollection<>());
//                RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
//                c.add(0.1, p);
//            }
//        }
//        {
//            SpawnerPopulator p = new SpawnerPopulator();
//            int min = p.getMinimumLayer();
//            int max = p.getMaximumLayer();
//            for(int i = min; i <= max; i++) {
//                if(!ROOMS.containsKey(i)) ROOMS.put(i, new RandomCollection<>());
//                RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
//                c.add(0.1, p);
//            }
//        }
		{
			AbandonedDefenceCastleRoomPopulator p = new AbandonedDefenceCastleRoomPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
		{
			ArmoryRoomPopulator p = new ArmoryRoomPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
		{
//            FloodedRoomPopulator p = new FloodedRoomPopulator();
//            int min = p.getMinimumLayer();
//            int max = p.getMaximumLayer();
//            for(int i = min; i <= max; i++) {
//                if(!ROOMS.containsKey(i)) ROOMS.put(i, new RandomCollection<>());
//                RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
//                c.add(0.1, p);
//            }
		}
		{
			GravelPopulator p = new GravelPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
		{
			GreatFurnaceRoomPopulator p = new GreatFurnaceRoomPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
		{
//            HighRoomPopulator p = new HighRoomPopulator();
//            int min = p.getMinimumLayer();
//            int max = p.getMaximumLayer();
//            for(int i = min; i <= max; i++) {
//                if(!ROOMS.containsKey(i)) ROOMS.put(i, new RandomCollection<>());
//                RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
//                c.add(0.1, p);
//            }
		}
		{
			LibraryRoomPopulator p = new LibraryRoomPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
		{
			MassiveRoomPopulator p = new MassiveRoomPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
//        {
//            RailPopulator p = new RailPopulator();
//            int min = p.getMinimumLayer();
//            int max = p.getMaximumLayer();
//            for(int i = min; i <= max; i++) {
//                if(!ROOMS.containsKey(i)) ROOMS.put(i, new RandomCollection<>());
//                RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
//                c.add(0.1, p);
//            }
//        }
//        {
//            RuinsPopulator p = new RuinsPopulator();
//            int min = p.getMinimumLayer();
//            int max = p.getMaximumLayer();
//            for(int i = min; i <= max; i++) {
//                if(!ROOMS.containsKey(i)) ROOMS.put(i, new RandomCollection<>());
//                RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
//                c.add(0.1, p);
//            }
//        }
		{
			SanctuaryPopulator p = new SanctuaryPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
//        {
//            SandPopulator p = new SandPopulator();
//            int min = p.getMinimumLayer();
//            int max = p.getMaximumLayer();
//            for(int i = min; i <= max; i++) {
//                if(!ROOMS.containsKey(i)) ROOMS.put(i, new RandomCollection<>());
//                RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
//                c.add(0.1, p);
//            }
//        }
//        {
//            SpawnChamberPopulator p = new SpawnChamberPopulator();
//            int min = p.getMinimumLayer();
//            int max = p.getMaximumLayer();
//            for(int i = min; i <= max; i++) {
//                if(!ROOMS.containsKey(i)) ROOMS.put(i, new RandomCollection<>());
//                RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
//                c.add(0.1, p);
//            }
//        }
//        {
//            StairsPopulator p = new StairsPopulator();
//            int min = p.getMinimumLayer();
//            int max = p.getMaximumLayer();
//            for(int i = min; i <= max; i++) {
//                if(!ROOMS.containsKey(i)) ROOMS.put(i, new RandomCollection<>());
//                RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
//                c.add(0.1, p);
//            }
//        }
		{
			StructPopulator p = new StructPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
		{
			TopTurveRoomPopulator p = new TopTurveRoomPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
		{
			WaterWellRoomPopulator p = new WaterWellRoomPopulator();
			int min = p.getMinimumLayer();
			int max = p.getMaximumLayer();
			for (int i = min; i <= max; i++) {
				if (!ROOMS.containsKey(i))
					ROOMS.put(i, new RandomCollection<>());
				RandomCollection<MazeRoomBlockPopulator> c = ROOMS.get(i);
				c.add(0.1, p);
			}
		}
	}
	public static RandomCollection<MazeLayerBlockPopulator> layer;
	static {
		layer = new RandomCollection<>();
		{
			BossRoomHardPopulator p = new BossRoomHardPopulator();
			layer.add(p.getLayerChance(), p);
		}
		{
			BossRoomInsanePopulator p = new BossRoomInsanePopulator();
			layer.add(p.getLayerChance(), p);
		}
	}
	public static EntrancePopulator entry = new EntrancePopulator();
	public static OasisChunkPopulator oasis = new OasisChunkPopulator();
	public static EmptyRoom empty = new EmptyRoom();
}
