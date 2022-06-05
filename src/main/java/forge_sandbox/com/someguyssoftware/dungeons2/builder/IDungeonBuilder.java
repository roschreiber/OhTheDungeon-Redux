/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.builder;

import java.util.Random;

import org.bukkit.ChunkSnapshot;

import forge_sandbox.AxisAlignedBB;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Dungeon;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.IDungeonConfig;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Aug 18, 2016
 *
 */
public interface IDungeonBuilder {

	public Dungeon EMPTY_DUNGEON = new Dungeon();

	/**
	 * 
	 * @param world
	 * @param rand
	 * @param startPoint
	 * @param config
	 * @return
	 */
	// @Override
	// Dungeon build(World world, Random rand, ICoords startPoint, DungeonConfig
	// config);

	/**
	 * @return the levelBuilder
	 */
	LevelBuilder getLevelBuilder();

	/**
	 * @param levelBuilder the levelBuilder to set
	 */
	void setLevelBuilder(LevelBuilder levelBuilder);

	/**
	 * @param world
	 * @param rand
	 * @param startPoint
	 * @return
	 */
	Room buildEntranceRoom(AsyncWorldEditor world, Random rand, ICoords startPoint);

	DungeonBuilderTopDown withBoundary(AxisAlignedBB field);

	AxisAlignedBB getBoundary();

	IDungeonConfig getConfig();

	Dungeon build(AsyncWorldEditor world, ChunkSnapshot chunk, Random rand, ICoords spawnCoords, IDungeonConfig config);

}