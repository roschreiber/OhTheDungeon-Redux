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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import io.papermc.lib.PaperLib;
import otd.Main;
import otd.MultiVersion;
import otd.api.event.DungeonGeneratedEvent;
import otd.dungeon.dungeonmaze.populator.ChunkBlockPopulator;
import otd.dungeon.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import otd.dungeon.dungeonmaze.populator.maze.MazeLayerBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeLayerBlockPopulatorArgs;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import otd.dungeon.dungeonmaze.populator.maze.structure.OasisChunkPopulator;
import otd.lib.ZoneWorld;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;
import otd.util.RandomCollection;
import otd.world.DungeonType;

/**
 *
 * @author
 */
public class SmoofyDungeonPopulator {
	private static final int MIN_LAYER = 1;
	private static final int MAX_LAYER = 7;
//    private static final int LAYER_COUNT = 7;

	private static class DungeonChunk {
		public boolean whole_chunk = false;
		public ChunkBlockPopulator chunk = null;
		public int chunkx, chunkz;

		public ChunkBlockPopulator[][] map;

		public DungeonChunk(int layer) {
			map = new ChunkBlockPopulator[layer][4];
		}

		public void addLayer(int layer, MazeLayerBlockPopulator p) {
			if (layer >= map.length)
				return;
			map[layer][1] = null;
			map[layer][2] = null;
			map[layer][3] = null;
			map[layer][0] = p;
		}

		public void addRoom(int layer, int pos, MazeRoomBlockPopulator p) {
			if (layer >= map.length)
				return;
			if (pos >= 4)
				return;
			map[layer][pos] = p;
		}
	}

	public static class SmoofyDungeonInstance {
		DungeonChunk chunks[][];
		final int layerMin = MIN_LAYER;
		final int layerMax = MAX_LAYER;

		public void placePiece(AsyncWorldEditor world, Random rand, int i, int j, Biome b) {
			int ymin = 18 + ((layerMin - 1) * 6);
			int ymax = 18 + ((layerMax - 1 + 1) * 6);

			int chunkx = chunks[i][j].chunkx, chunkz = chunks[i][j].chunkz;
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < 16; y++) {
					for (int z = ymin; z < ymax; z++) {
						if ((z - 18) % 6 == 0) {
							world.setBlockType(chunkx * 16 + x, z, chunkz * 16 + y, Material.COBBLESTONE);
						} else
							world.setBlockType(chunkx * 16 + x, z, chunkz * 16 + y, Material.AIR);
					}
				}
			}

			if (chunks[i][j].whole_chunk) {
				ChunkBlockPopulatorArgs args = new ChunkBlockPopulatorArgs(world, rand, new HashSet<>(),
						chunks[i][j].chunkx, chunks[i][j].chunkz);
				chunks[i][j].chunk.populateChunk(args);
				if (chunks[i][j].chunk instanceof OasisChunkPopulator) {
					OasisChunkPopulator t = (OasisChunkPopulator) chunks[i][j].chunk;
					int x = chunks[i][j].chunkx * 16 + 8;
					int z = chunks[i][j].chunkz * 16 + 8;
					t.apply_glass(ymax, world, x, z, b);
				}
				return;
			}
			world.setChunk(chunks[i][j].chunkx, chunks[i][j].chunkz);
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					world.setChunkType(x, ymax, z, Material.GLASS);
				}
			}
			for (int l = layerMin - 1; l < layerMax; l++) {
				// Calculate the Y coordinate based on the current layer
				int y = 18 + ((l - 1) * 6);
				if (chunks[i][j].map[l][1] == null && chunks[i][j].map[l][2] == null
						&& chunks[i][j].map[l][3] == null) {
					MazeLayerBlockPopulatorArgs newArgs = new MazeLayerBlockPopulatorArgs(world, rand,
							chunks[i][j].chunkx, chunks[i][j].chunkz, new HashSet<>(), l, y);
					MazeLayerBlockPopulator p = (MazeLayerBlockPopulator) chunks[i][j].map[l][0];
					p.populateLayer(newArgs);
				} else {
					world.setChunk(chunks[i][j].chunkx, chunks[i][j].chunkz);
					for (int chunkX = 0; chunkX < 2; chunkX++) {
						for (int chunkZ = 0; chunkZ < 2; chunkZ++) {
							// Calculate the global X and Y coordinates
							int x = (chunks[i][j].chunkx * 16) + chunkX * 8;
							int z = (chunks[i][j].chunkz * 16) + chunkZ * 8;

							int index = chunkX * 2 + chunkZ;
							MazeRoomBlockPopulator p = (MazeRoomBlockPopulator) chunks[i][j].map[l][index];
							int floorOffset = p.getFloorOffset(chunkX * 8, y, chunkZ * 8, world);
							int ceilingOffset = p.getCeilingOffset(chunkX * 8, y, chunkZ * 8, world);

							MazeRoomBlockPopulatorArgs newArgs = new MazeRoomBlockPopulatorArgs(world, rand,
									chunks[i][j].chunkx, chunks[i][j].chunkz, new HashSet<>(), l, x, y, z, floorOffset,
									ceilingOffset);
							p.populateRoom(newArgs);

							if (!p.getConstRoom()) {
								for (MazeRoomBlockPopulator pp : SmoofyDungeon.decoration)
									pp.populateRoom(newArgs);
							}
						}
					}
				}
			}
			for (int m = ymin; m < ymax; m++) {
				world.setChunkType(0, m, 7, Material.STONE_BRICKS);
				world.setChunkType(0, m, 8, Material.STONE_BRICKS);
				world.setChunkType(15, m, 7, Material.STONE_BRICKS);
				world.setChunkType(15, m, 8, Material.STONE_BRICKS);
				world.setChunkType(7, m, 0, Material.STONE_BRICKS);
				world.setChunkType(8, m, 0, Material.STONE_BRICKS);
				world.setChunkType(7, m, 15, Material.STONE_BRICKS);
				world.setChunkType(8, m, 15, Material.STONE_BRICKS);
			}

		}

		public void placeDungeon(AsyncWorldEditor world, Random rand, int chunkx, int chunkz, int w, int h, float oasis,
				float entry) {

			chunks = new DungeonChunk[w][h];
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					chunks[i][j] = new DungeonChunk(MAX_LAYER);
				}
			}

			if (rand.nextFloat() < oasis) {
				int sizew = w - 2;
				int sizeh = h - 2;
				if (sizew > 0 && sizeh > 0) {
					int a = rand.nextInt(sizew) + 1;
					int b = rand.nextInt(sizeh) + 1;
					chunks[a][b].whole_chunk = true;
					chunks[a][b].chunk = SmoofyDungeon.oasis;
				}
			}

			{
				int count = rand.nextInt(2) + 1;
				for (int i = 0; i < count; i++) {
					int sizew = w - 2;
					int sizeh = h - 2;
					if (sizew > 0 && sizeh > 0) {
						int a = rand.nextInt(sizew) + 1;
						int b = rand.nextInt(sizeh) + 1;
						MazeLayerBlockPopulator p = SmoofyDungeon.layer.next();
						int max = p.getMaximumLayer();
						int min = p.getMinimumLayer();
						int layer = rand.nextInt(max - min) + min;
						chunks[a][b].addLayer(layer, p);
					}
				}
			}

			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					if (chunks[i][j].whole_chunk)
						continue;
					for (int k = layerMin - 1; k < layerMax; k++) {
						RandomCollection<MazeRoomBlockPopulator> r;
						if (chunks[i][j].map[k][0] != null)
							continue;
						r = SmoofyDungeon.ROOMS.get(k + 1);
						chunks[i][j].addRoom(k, 0, r.next());
						if ((i + k) % 2 == 0 && (j + k) % 2 == 0) {
							chunks[i][j].addRoom(k, 1, SmoofyDungeon.empty);
							chunks[i][j].addRoom(k, 2, SmoofyDungeon.empty);
							chunks[i][j].addRoom(k, 3, SmoofyDungeon.empty);
						} else {
							chunks[i][j].addRoom(k, 1, r.next());
							chunks[i][j].addRoom(k, 2, r.next());
							chunks[i][j].addRoom(k, 3, r.next());
						}
					}
				}
			}

			int midx = w / 2;
			int midz = h / 2;
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					chunks[i][j].chunkx = i - midx + chunkx;
					chunks[i][j].chunkz = j - midz + chunkz;
				}
			}

			if (rand.nextFloat() < entry) {
				// add entry
				int sizew = w;
				int sizeh = h;
				if (sizew > 0 && sizeh > 0) {
					int a = rand.nextInt(sizew);
					int b = rand.nextInt(sizeh);
					int c = rand.nextInt(4);
					ChunkSnapshot snapshot = world.getWorld().getChunkAt(chunks[a][b].chunkx, chunks[a][b].chunkz)
							.getChunkSnapshot(true, false, false);
//					Bukkit.getLogger().info(chunks[a][b].chunkx + " " + chunks[a][b].chunkz);
					SmoofyDungeon.entry.setChunkSnapshot(snapshot);
					chunks[a][b].addRoom(MAX_LAYER - 1, c, SmoofyDungeon.entry);
				}
			}

			Biome b = MultiVersion.getBiome(world.getWorld(), chunkx * 16 + 8, chunkz * 16 + 8);

			Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
				for (int i = 0; i < w; i++)
					for (int j = 0; j < h; j++)
						placePiece(world, rand, i, j, b);
				commitDungeon(world, chunks[w / 2][h / 2].chunkx * 16 + 8, chunks[w / 2][h / 2].chunkz * 16 + 8);
			});
		}

		private final static Map<UUID, Integer> POOL = new HashMap<>();

		private void commitDungeon(AsyncWorldEditor w, int x, int z) {
			Set<int[]> chunks0 = w.getAsyncWorld().getCriticalChunks();

			int delay = 0;

			int offsetY = w.getSeaLevel() - AsyncWorldEditor.DEFAULT_SEALEVEL;

			UUID id;
			do {
				id = UUID.randomUUID();
			} while (POOL.containsKey(id));
			UUID uuid = id;
			synchronized (POOL) {
				POOL.put(uuid, chunks0.size());
			}

			for (int[] chunk : chunks0) {
				int chunkX = chunk[0];
				int chunkZ = chunk[1];

				List<ZoneWorld.CriticalNode> cn = w.getAsyncWorld().getCriticalBlock(chunkX, chunkZ);
				List<Later> later = w.getAsyncWorld().getCriticalLater(chunkX, chunkZ);

				delay++;

				Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
					PaperLib.getChunkAtAsync(w.getWorld(), chunkX, chunkZ, true).thenAccept((Chunk c) -> {
						for (ZoneWorld.CriticalNode node : cn) {
							int[] pos = node.pos;
							if (node.bd != null) {
								if (node.bd.getMaterial() != Material.IRON_BARS)
									c.getBlock(pos[0], pos[1] + offsetY, pos[2]).setBlockData(node.bd, false);
								else
									c.getBlock(pos[0], pos[1] + offsetY, pos[2]).setType(node.bd.getMaterial(), true);
							} else {
								if (node.material != Material.IRON_BARS)
									c.getBlock(pos[0], pos[1] + offsetY, pos[2]).setType(node.material, false);
								else
									c.getBlock(pos[0], pos[1] + offsetY, pos[2]).setType(node.material, true);
							}
						}
						if (later != null) {
							for (Later l : later) {
								l.setOffset(x, offsetY, z);
								l.doSomethingInChunk(c);
							}
						}

						boolean isFinish = false;
						synchronized (POOL) {
							if (POOL.containsKey(uuid)) {
								int count = POOL.get(uuid);
								count--;
								POOL.put(uuid, count);

								if (count == 0) {
									isFinish = true;
									POOL.remove(uuid);
								}
							}
						}

						if (isFinish) {
							Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
								int ix = (w.zone_world.getMaxX() + w.zone_world.getMinX()) / 2;
								int iy = (w.zone_world.getMaxY() + w.zone_world.getMinY()) / 2 + offsetY;
								int iz = (w.zone_world.getMaxZ() + w.zone_world.getMinZ()) / 2;

								DungeonGeneratedEvent event = new DungeonGeneratedEvent(w.getWorld(), chunks0,
										DungeonType.DungeonMaze, ix, iy, iz);
								Bukkit.getServer().getPluginManager().callEvent(event);
							}, 1L);
						}

					});
				}, delay);
			}
		}
	}
}
