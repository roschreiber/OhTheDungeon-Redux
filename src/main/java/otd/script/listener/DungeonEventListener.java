package otd.script.listener;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import forge_sandbox.ChunkPos;
import otd.Main;
import otd.api.MobManager;
import otd.api.event.DungeonGeneratedEvent;
import otd.script.JSLoader;
import otd.script.JSLoader.Script;
import otd.script.utils.BlockUtils;
import otd.util.ExceptionReporter;
import otd.world.DungeonType;

public class DungeonEventListener implements Listener {
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent e) {
		if (e.isCancelled())
			return;
		Iterator<Block> iterator = e.blockList().iterator();
		while (iterator.hasNext()) {
			Block block = iterator.next();
			if (block.getState() instanceof CreatureSpawner) {
				CreatureSpawner cs = (CreatureSpawner) block.getState();
				if (cs.getPersistentDataContainer().has(BlockUtils.KEY)) {
					iterator.remove();
				}
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		if (e.isCancelled())
			return;
		Iterator<Block> iterator = e.blockList().iterator();
		while (iterator.hasNext()) {
			Block block = iterator.next();
			if (block.getState() instanceof CreatureSpawner) {
				CreatureSpawner cs = (CreatureSpawner) block.getState();
				if (cs.getPersistentDataContainer().has(BlockUtils.KEY)) {
					iterator.remove();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSpawnerMine(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player p = event.getPlayer();
		if (block.getType() != Material.SPAWNER)
			return;

		if (block.getState() instanceof CreatureSpawner) {
			CreatureSpawner ts = (CreatureSpawner) block.getState();
			if (ts.getPersistentDataContainer().has(BlockUtils.KEY)) {
				if (p.hasPermission("oh_the_dungeons.admin")) {
					p.sendMessage("Bypass spawner change for OP");
					return;
				}
				event.setCancelled(true);
			}
		}
	}

	private final static int MAX_ENTRIES = 100;

	@SuppressWarnings("serial")
	Set<Location> invalid = Collections.newSetFromMap(new LinkedHashMap<Location, Boolean>() {
		protected boolean removeEldestEntry(Map.Entry<Location, Boolean> eldest) {
			return size() > MAX_ENTRIES;
		}
	});

	@EventHandler
	public void onDeath(EntityDeathEvent e) {

		Entity entity = e.getEntity();
		Entity killer = e.getEntity().getKiller();

		if (!(killer instanceof Player))
			return;
		Player p = (Player) killer;

		if (!(entity instanceof LivingEntity))
			return;

		LivingEntity le = (LivingEntity) entity;

		boolean isBoss = MobManager.instance().isCastleKing(entity) || MobManager.instance().isLich(entity);
		boolean isMob = MobManager.instance().isOtdNormalMob(entity);

		if (isBoss || isMob) {
			Map<String, Script> scripts = JSLoader.getScripts("on_dungeon_mob_killed");
			if (scripts != null) {
				for (Map.Entry<String, Script> entry : scripts.entrySet()) {
					try {
						ScriptEngine engine = JSLoader.engine;
						Invocable invocable = (Invocable) engine;
						engine.setContext(entry.getValue().context);
						Location loc = entity.getLocation();
						invocable.invokeFunction("on_dungeon_mob_killed", p, le, loc, isBoss);
					} catch (NoSuchMethodException | ScriptException ex) {
						Bukkit.getLogger().log(Level.SEVERE,
								"Found errors while executing js script : " + entry.getKey());
						Bukkit.getLogger().log(Level.SEVERE, ExceptionReporter.exceptionToString(ex));
					}
				}
			}
		}

	}

	@EventHandler
	public void onDungeonPlaced(DungeonGeneratedEvent e) {
		Map<String, Script> scripts = JSLoader.getScripts("on_dungeon_placed");
		if (scripts != null) {
			int centerx = e.getCenterX();
			int centery = e.getCenterY();
			int centerz = e.getCenterZ();
			Set<ChunkPos> chunks = e.getChunks();
			DungeonType type = e.getType();
			String custom = e.getCustomStructureName();

			for (Map.Entry<String, Script> entry : scripts.entrySet()) {
				try {
					ScriptEngine engine = JSLoader.engine;
					Invocable invocable = (Invocable) engine;
					engine.setContext(entry.getValue().context);
					invocable.invokeFunction("on_dungeon_placed", e.getWorld(), centerx, centery, centerz, chunks, type,
							custom);
				} catch (NoSuchMethodException | ScriptException ex) {
					Bukkit.getLogger().log(Level.SEVERE, "Found errors while executing js script : " + entry.getKey());
					Bukkit.getLogger().log(Level.SEVERE, ExceptionReporter.exceptionToString(ex));
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSpawner(SpawnerSpawnEvent event) {
		if (event.isCancelled())
			return;
		if (event.getSpawner() == null)
			return;
		Block block = event.getSpawner().getBlock();
		TileState ts = (TileState) block.getState();
		if (ts.getPersistentDataContainer().has(BlockUtils.KEY)) {
			Location loc = block.getLocation();
			if (invalid.contains(loc)) {
				event.setCancelled(true);
				return;
			}

			String script = ts.getPersistentDataContainer().get(BlockUtils.KEY, PersistentDataType.STRING);

			Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
				block.setType(Material.AIR, true);
			}, 1L);
			invalid.add(loc);
			event.setCancelled(true);

			if (script != null) {
				Script s = JSLoader.getScript("spawner_scripts", script);
				if (s != null) {
					ScriptEngine engine = JSLoader.engine;
					Invocable invocable = (Invocable) engine;
					engine.setContext(s.context);

					try {
						invocable.invokeFunction("spawner_action", loc);
					} catch (NoSuchMethodException | ScriptException ex) {
						Bukkit.getLogger().log(Level.SEVERE, "Found errors while executing js script : " + script);
						Bukkit.getLogger().log(Level.SEVERE, ExceptionReporter.exceptionToString(ex));
					}
				}
			}
		}
	}
}
