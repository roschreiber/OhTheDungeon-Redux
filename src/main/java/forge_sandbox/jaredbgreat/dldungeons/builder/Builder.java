package forge_sandbox.jaredbgreat.dldungeons.builder;

import java.util.Random;

import org.bukkit.World;

/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */

import forge_sandbox.jaredbgreat.dldungeons.planner.Dungeon;
import otd.MultiVersion;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.lib.async.AsyncWorldEditor;

public class Builder {

	@SuppressWarnings("unused")
	private static boolean debugPole = false;

	/**
	 * This will place a dungeon into the world, and is called to create the dungeon
	 * object (which plans the dungeon) when a dungeon is cheated in by command or
	 * generated through the API and have the dungeon built. Basically, this is used
	 * any time and dungeons is generated without the use of IChunkGenerator and
	 * IChunkProvider objects, that is, not through an IWorldGenerator.
	 * 
	 * @param random
	 * @param chunkX
	 * @param chunkZ
	 * @param world
	 * @throws Throwable
	 */
	public static boolean placeDungeonAsync(Random random, int chunkX, int chunkZ, World world) throws Throwable {
		AsyncWorldEditor aworld = new AsyncWorldEditor(world);
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
			aworld.setSeaLevel(swc.worldParameter.sealevel);
			aworld.setBottom(swc.worldParameter.bottom);
		}
		Dungeon dungeon = new Dungeon(random, MultiVersion.getBiome(world, chunkX * 16, chunkZ * 16), world, chunkX,
				chunkZ, aworld);
		boolean res = buildDungeonAsync(dungeon);
		return res;
	}

	public static boolean commandPlaceDungeon(Random random, int chunkX, int chunkZ, World world) throws Throwable {
		AsyncWorldEditor aworld = new AsyncWorldEditor(world);
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
			aworld.setSeaLevel(swc.worldParameter.sealevel);
			aworld.setBottom(swc.worldParameter.bottom);
		}
		Dungeon dungeon = new Dungeon(random, MultiVersion.getBiome(world, chunkX * 16, chunkZ * 16), world, chunkX,
				chunkZ, aworld);
		boolean res = (dungeon.theme != null);

		buildDungeonAsync(dungeon);
		return res;
	}

	public static boolean buildDungeonAsync(Dungeon dungeon /* TODO: Parameters */) {
		if (dungeon.theme != null)
			dungeon.map.buildAsync(dungeon);
		return dungeon.theme != null;
	}

	/**
	 * Set the debugPole variable. Setting this to true will cause a quartz pillar
	 * to appear in the center of the dungeon from y=16 to y=240, and a lapis lazuli
	 * boarder to appear around the area allotted for the dungeon at y=80.
	 * 
	 * @param val
	 */
	public static void setDebugPole(boolean val) {
		debugPole = val;
	}
}