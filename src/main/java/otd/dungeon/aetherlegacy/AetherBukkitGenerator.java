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
package otd.dungeon.aetherlegacy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import io.papermc.lib.PaperLib;
import otd.Main;
import otd.api.event.DungeonGeneratedEvent;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.lib.ZoneWorld;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;
import otd.world.DungeonType;

/**
 *
 * @author
 */
public class AetherBukkitGenerator {

	public static void generate(World world, Random random, int x, int z) {
		AsyncWorldEditor w = new AsyncWorldEditor(world);

		int height = 180;
		boolean cloud = true;
		Material cloud_material = Material.PACKED_ICE;

		String world_name = world.getName();

		if (WorldConfig.wc.dict.containsKey(world_name)) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(world_name);
			height = swc.aether_dungeon.height;
			cloud = swc.aether_dungeon.cloud;
			cloud_material = Material.PACKED_ICE;
		}

		final int h = height;
		final boolean c = cloud;
		final Material cm = cloud_material;

		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			asyncGenerate(w, random, x, z, h, c, cm);
		});
	}

	private final static Map<UUID, Integer> POOL = new HashMap<>();

	private static boolean asyncGenerate(AsyncWorldEditor w, Random random, int x, int z, int height, boolean cloud,
			Material cloud_material) {
		w.setDefaultState(Material.AIR);

		int firstStaircaseZ, secondStaircaseZ, finalStaircaseZ;
		int xTendency, zTendency;
		ComponentSilverDungeon dungeon = new ComponentSilverDungeon();
		dungeon.setBaseStructureOffset(x - 55, height, z - 45);
		dungeon.setStructureOffset(0, 0, 0);
		firstStaircaseZ = random.nextInt(3);
		secondStaircaseZ = random.nextInt(3);
		finalStaircaseZ = random.nextInt(3);

		xTendency = random.nextInt(3) - 1;
		zTendency = random.nextInt(3) - 1;

		dungeon.setStaircasePosition(firstStaircaseZ, secondStaircaseZ, finalStaircaseZ);
		dungeon.setCloudTendencies(xTendency, zTendency);

		dungeon.addComponentParts(w, random, cloud, cloud_material);

		Set<int[]> chunks0 = w.getAsyncWorld().getCriticalChunks();

		int delay = 0;

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
							if (node.bd.getMaterial() != Material.GLASS_PANE)
								c.getBlock(pos[0], pos[1], pos[2]).setBlockData(node.bd, false);
						} else {
							if (node.material != Material.GLASS_PANE)
								c.getBlock(pos[0], pos[1], pos[2]).setType(node.material, false);
						}
					}
					if (later != null) {
						for (Later l : later) {
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
							int iy = (w.zone_world.getMaxY() + w.zone_world.getMinY()) / 2;
							int iz = (w.zone_world.getMaxZ() + w.zone_world.getMinZ()) / 2;

							DungeonGeneratedEvent event = new DungeonGeneratedEvent(w.getWorld(), chunks0,
									DungeonType.Aether, ix, iy, iz);
							Bukkit.getServer().getPluginManager().callEvent(event);
						}, 1L);
					}
				});

			}, delay);
		}

		return true;
	}
}
