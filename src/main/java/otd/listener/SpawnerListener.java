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
package otd.listener;

import static otd.listener.MobListener.SPAWN_EGGS;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import forge_sandbox.team.cqr.cqrepoured.boss.CastleKing;
import forge_sandbox.twilightforest.structures.lichtower.boss.Lich;
import otd.Main;
import otd.MultiVersion;
import otd.api.MobManager;
import otd.api.SpawnerManager;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
//import otd.integration.BossImpl;
import otd.integration.EcoBossesImpl;
import otd.integration.MythicMobsImpl;
import otd.lib.spawner.SpawnerDecryAPI;
import otd.util.I18n;

/**
 *
 * @author
 */
public class SpawnerListener implements Listener {
	private final NamespacedKey lightupdate_v118;
	private final NamespacedKey vanilla_no_drop;

	public SpawnerListener() {
		super();
		lightupdate_v118 = new NamespacedKey(Main.instance, "lightupdate_v118");
		vanilla_no_drop = new NamespacedKey(Main.instance, "vanilla_no_drop");
	}

	@EventHandler
	public void NoChangeLimit(PlayerInteractEvent e) {
		if (e.getItem() == null) {
			return;
		}
		Block block = e.getClickedBlock();
		if (block == null) {
			return;
		}
		Material type = block.getType();
		if (type != Material.SPAWNER)
			return;

		Material item1 = e.getPlayer().getInventory().getItemInMainHand().getType();
		Material item2 = e.getPlayer().getInventory().getItemInOffHand().getType();

		if ((!SPAWN_EGGS.contains(item1)) && (!SPAWN_EGGS.contains(item2)))
			return;

		Player p = e.getPlayer();
		String world = p.getWorld().getName();
		if (WorldConfig.wc.dict.containsKey(world) && !WorldConfig.wc.dict.get(world).egg_change_spawner) {
			if (p.hasPermission("oh_the_dungeons.admin")) {
				p.sendMessage("Bypass spawner change for OP");
			} else {
				e.setCancelled(true);
				p.sendMessage(I18n.instance.ChangeSpawnerMessage);
			}
		}
	}

	@EventHandler
	public void onBlockExplode(BlockExplodeEvent e) {
		if (e.isCancelled())
			return;
		SimpleWorldConfig config = WorldConfig.wc.dict.get(e.getBlock().getWorld().getName());
		if (config == null || !config.prevent_spawner_breaking)
			return;
		SpawnerManager sm = SpawnerManager.instance();
		Iterator<Block> iterator = e.blockList().iterator();
		while (iterator.hasNext()) {
			Block block = iterator.next();
			if (block.getState() instanceof CreatureSpawner) {
				CreatureSpawner cs = (CreatureSpawner) block.getState();
				if (sm.isOtdSpawner(cs)) {
					iterator.remove();
				}
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		if (e.isCancelled())
			return;
		SimpleWorldConfig config = WorldConfig.wc.dict.get(e.getEntity().getWorld().getName());
		if (config == null || !config.prevent_spawner_breaking)
			return;
		SpawnerManager sm = SpawnerManager.instance();
		Iterator<Block> iterator = e.blockList().iterator();
		while (iterator.hasNext()) {
			Block block = iterator.next();
			if (block.getState() instanceof CreatureSpawner) {
				CreatureSpawner cs = (CreatureSpawner) block.getState();
				if (sm.isOtdSpawner(cs)) {
					iterator.remove();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSpawnerMine(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (block.getType() != Material.SPAWNER)
			return;

		World world = block.getWorld();
		Player p = event.getPlayer();
		String world_name = world.getName();

		boolean isDungeon = false;
		if (block.getState() instanceof CreatureSpawner) {
			CreatureSpawner ts = (CreatureSpawner) block.getState();
			if (SpawnerManager.instance().isOtdSpawner(ts)) {
				isDungeon = true;
			}
		}
		if (!isDungeon)
			return;

		if (WorldConfig.wc.dict.containsKey(world_name)) {
			SimpleWorldConfig config = WorldConfig.wc.dict.get(world_name);

			if (config.prevent_spawner_breaking) {
				if (p.hasPermission("oh_the_dungeons.admin")) {
					p.sendMessage("Bypass spawner change for OP");
					return;
				}
				event.setCancelled(true);
				return;
			}

			if (config.prevent_spawner_dropping) {
				event.setCancelled(true);
				block.setType(Material.AIR);
			}
		}
	}

	private final static Random RAND = new Random();

	private void spawnerNormalLogic(SpawnerSpawnEvent event) {
		Block block = event.getSpawner().getBlock();
		World world = block.getWorld();
		float vanilla = 0;
		float dungeon = 0;
		float boss = 0;
		SimpleWorldConfig.BossType type = SimpleWorldConfig.BossType.Vanilla;

		boolean vanilla_drop = true;
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			SimpleWorldConfig config = WorldConfig.wc.dict.get(world.getName());
			vanilla = config.disappearance_rate_vanilla / 100;
			dungeon = config.disappearance_rate_dungeon / 100;
			vanilla_drop = config.mob_drop_in_vanilla_spawner;
			boss = config.chance == 0 ? 0 : ((float) config.chance) / 100;
			type = config.boss;
		}

		boolean isDungeon = false;
		{
			CreatureSpawner ts = (CreatureSpawner) block.getState();
			if (SpawnerManager.instance().isOtdSpawner(ts)) {
				isDungeon = true;
			}
		}

		if (isDungeon) {
			if (RAND.nextFloat() < dungeon) {
				block.setType(Material.AIR, true);
				event.setCancelled(true);
			}
			Entity entity = event.getEntity();
			MobManager.instance().setOtdMobTag(entity);
		} else {
			if (RAND.nextFloat() < vanilla) {
				block.setType(Material.AIR, true);
				event.setCancelled(true);
			}
			if (!vanilla_drop) {
				Entity entity = event.getEntity();
				entity.getPersistentDataContainer().set(vanilla_no_drop, PersistentDataType.BYTE, (byte) 1);
			}
		}

		if (isDungeon) {
			if (boss > 0 && RAND.nextFloat() < boss) {
				if (type == SimpleWorldConfig.BossType.MythicMobs && MythicMobsImpl.isMythicMobsReady()) {
					Entity entity = event.getEntity();
					Location loc = entity.getLocation();
					MythicMobsImpl.spawnSmallBoss(loc, RAND);
				}
				/*if (type == SimpleWorldConfig.BossType.Boss && BossImpl.isBossReady()) {
					Entity entity = event.getEntity();
					Location loc = entity.getLocation();
					BossImpl.spawnSmallBoss(loc, RAND);
				}*/
			}
		}
	}

	public static void spawnLichLogic(Block block, World world) {
		SimpleWorldConfig.BossType type = SimpleWorldConfig.BossType.Vanilla;
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			SimpleWorldConfig config = WorldConfig.wc.dict.get(world.getName());
			type = config.boss;
		}

		Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
			block.setType(Material.AIR, true);
		}, 1L);

		Location loc = block.getLocation();
		loc.setY(loc.getBlockY() + 2);

		if (MythicMobsImpl.isMythicMobsReady() && type == SimpleWorldConfig.BossType.MythicMobs
				&& MythicMobsImpl.lichBossReady()) {
			MythicMobsImpl.spawnLich(loc);
		/*} else if (BossImpl.isBossReady() && type == SimpleWorldConfig.BossType.Boss) {
			BossImpl.spawnLich(loc);*/
		} else if (EcoBossesImpl.isEcoBossesReady() && type == SimpleWorldConfig.BossType.EcoBosses) {
			EcoBossesImpl.spawnLich(loc);
		} else {
			Lich.spawnBoss(loc);
		}
	}

	public static void spawnCastleKingLogic(Block block, World world) {
		SimpleWorldConfig.BossType type = SimpleWorldConfig.BossType.Vanilla;
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			SimpleWorldConfig config = WorldConfig.wc.dict.get(world.getName());
			type = config.boss;
		}

		Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
			block.setType(Material.AIR, true);
		}, 1L);

		Location loc = block.getLocation();
		loc.setY(loc.getBlockY() + 2);

		if (MythicMobsImpl.isMythicMobsReady() && type == SimpleWorldConfig.BossType.MythicMobs
				&& MythicMobsImpl.kingCastleBossReady()) {
			MythicMobsImpl.spawnCastleKing(loc);
		/*} else if (BossImpl.isBossReady() && type == SimpleWorldConfig.BossType.Boss) {
			BossImpl.spawnCastleKing(loc);*/
		} else if (EcoBossesImpl.isEcoBossesReady() && type == SimpleWorldConfig.BossType.EcoBosses) {
			EcoBossesImpl.spawnCastleKing(loc);
		} else {
			CastleKing.spawnBoss(loc);
		}
	}

	public final static int MAX_ENTRIES = 100;

	@SuppressWarnings("serial")
	Set<Location> invalid = Collections.newSetFromMap(new LinkedHashMap<Location, Boolean>() {
		protected boolean removeEldestEntry(Map.Entry<Location, Boolean> eldest) {
			return size() > MAX_ENTRIES;
		}
	});

	@EventHandler
	public void onSpawner(SpawnerSpawnEvent event) {
		if (event.isCancelled())
			return;
		if (event.getSpawner() == null)
			return;
		Block block = event.getSpawner().getBlock();
		Location loc = block.getLocation();
		TileState ts = (TileState) block.getState();
		boolean lich = Lich.isLichSpawner(ts);
		boolean king = CastleKing.isCastleKingSpawner(ts);

		if (lich) {
			if (invalid.contains(loc)) {
				event.setCancelled(true);
				return;
			}
			spawnLichLogic(event.getSpawner().getBlock(), event.getLocation().getWorld());
			invalid.add(loc);
//			Lich.removeLichSpawner(ts);
			event.setCancelled(true);
		} else if (king) {
			if (invalid.contains(loc)) {
				event.setCancelled(true);
				return;
			}
			spawnCastleKingLogic(event.getSpawner().getBlock(), event.getLocation().getWorld());
			invalid.add(loc);
//			CastleKing.removeCastleKingSpawner(ts);
			event.setCancelled(true);
		} else {
			spawnerNormalLogic(event);
		}
	}

	@EventHandler
	public void onMobDrop(EntityDeathEvent event) {
		Entity e = event.getEntity();
		if (e == null)
			return;

		if (!MobManager.instance().isOtdNormalMob(e)) {
			if (e.getPersistentDataContainer().has(vanilla_no_drop, PersistentDataType.BYTE)) {
				event.getDrops().clear();
				event.setDroppedExp(0);
			}
		} else {
			boolean dungeon_drop = false;
			String world = e.getWorld().getName();
			if (WorldConfig.wc.dict.containsKey(world)) {
				SimpleWorldConfig config = WorldConfig.wc.dict.get(world);
				dungeon_drop = config.mob_drop_in_dungeon_spawner;
			}

			if (!dungeon_drop) {
				event.getDrops().clear();
				event.setDroppedExp(0);
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	void onChunkLoad(ChunkLoadEvent event) {
		if (!MultiVersion.spawnerNeedLightUpdate()) {
			return;
		}
		Chunk chunk = event.getChunk();
		PersistentDataContainer container = chunk.getPersistentDataContainer();
		if (event.isNewChunk()) {
			container.set(lightupdate_v118, PersistentDataType.BYTE, (byte) 15);
			return;
		}
		if (container.has(lightupdate_v118, PersistentDataType.BYTE)) {
			return;
		}

		for (BlockState state : chunk.getTileEntities()) {
			if (state instanceof CreatureSpawner) {
				if (!SpawnerDecryAPI.hasLightRuleUpdate((TileState) state, Main.instance)) {
					SpawnerDecryAPI.updateSpawnerLightRule(state.getBlock(), Main.instance);
				}
			}
		}
		container.set(lightupdate_v118, PersistentDataType.BYTE, (byte) 15);
	}

}
