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
package otd.dungeon.dungeonmaze.populator.maze.decoration;

//import java.util.ArrayList;
//import java.util.List;
import java.util.Random;

import org.bukkit.Material;

import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import otd.dungeon.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
//import org.bukkit.entity.Player;
import otd.lib.async.AsyncWorldEditor;

public class SkullPopulator extends MazeRoomBlockPopulator {

	/** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 4;
	private static final int ROOM_ITERATIONS = 5;
//	private static final float ROOM_ITERATIONS_CHANCE = .001f;
	private static final float ROOM_ITERATIONS_CHANCE = .01f;

	/** Populator constants. */
	private static final float POLE_GRAVE_CHANCE = .8f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final AsyncWorldEditor world = args.getWorld();
		final Random rand = args.getRandom();
		int chunkx = args.getChunkX(), chunkz = args.getChunkZ();
		world.setChunk(chunkx, chunkz);

		if (rand.nextFloat() > ROOM_ITERATIONS_CHANCE)
			return;

		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();

		// Determine the position of the skull
		int skullX = x + rand.nextInt(6) + 1;
		int skullY = args.getFloorY() + 1;
		int skullZ = z + rand.nextInt(6) + 1;

		// Decide whether it's a grave on a pole
		boolean withPole = false;
		if (rand.nextFloat() < POLE_GRAVE_CHANCE)
			withPole = true;

		// Move the skull one up if it's on a pole
		if (withPole)
			skullY++;

		// Define the blocks

		if (withPole)
			world.setChunkType(skullX, skullY - 1, skullZ, Material.OAK_FENCE);

		// Get and create the skull block
		world.setChunkType(skullX, skullY, skullZ, Material.SKELETON_SKULL);
//        skullBlock.setData((byte) 1);//TODO

//		try {
//			Skull skull = (Skull) skullBlock.getState();
//
//			// Get a random name for the skull, and make sure the name is valid
//			final String skullOwner = getRandomOwner(rand);
//			if(skullOwner.trim().length() == 0)
//				return;
//
//			// Set the skull type, rotation and owner
//			skull.setSkullType(getRandomSkullType(rand));
//			skull.setRotation(getRandomSkullRotation(rand));
//			skull.setOwner(skullOwner);
//
//			// Force update the skull
//			skull.update(true, false);
//
//		} catch(Exception ex) {
//			// Show a proper error message
//			Core.getLogger().error("Failed to configure player skull");
//		}
	}

//	private String getRandomOwner(Random rand) {
//		String name = "timvisee";
//		if(Bukkit.getOnlinePlayers().size() > 0) {
//			List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
//			name = onlinePlayers.get(rand.nextInt(onlinePlayers.size())).getName();
//		}
//		return name;
//	}

//	private SkullType getRandomSkullType(Random rand) {
//		SkullType[] types = SkullType.values();
//		return types[rand.nextInt(types.length)];
//	}
//	
//	private BlockFace getRandomSkullRotation(Random rand) {
//		BlockFace[] faces = new BlockFace[]{
//				BlockFace.EAST,
//				BlockFace.EAST_NORTH_EAST,
//				BlockFace.EAST_SOUTH_EAST,
//				BlockFace.NORTH,
//				BlockFace.NORTH_EAST,
//				BlockFace.NORTH_NORTH_EAST,
//				BlockFace.NORTH_NORTH_WEST,
//				BlockFace.NORTH_WEST,
//				BlockFace.SOUTH,
//				BlockFace.SOUTH_EAST,
//				BlockFace.SOUTH_SOUTH_EAST,
//				BlockFace.SOUTH_SOUTH_WEST,
//				BlockFace.SOUTH_WEST,
//				BlockFace.WEST,
//				BlockFace.WEST_NORTH_WEST,
//				BlockFace.WEST_SOUTH_WEST
//		};
//		return faces[rand.nextInt(faces.length)];
//	}

	@Override
	public int getRoomIterations() {
		return ROOM_ITERATIONS;
	}

	@Override
	public float getRoomIterationsChance() {
		return ROOM_ITERATIONS_CHANCE;
	}

	/**
	 * Get the minimum layer
	 * 
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}

	/**
	 * Get the maximum layer
	 * 
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
}
