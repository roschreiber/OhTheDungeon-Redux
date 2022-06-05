package forge_sandbox.jaredbgreat.dldungeons.planner.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */

//import jaredbgreat.dldungeons.api.DLDEvent;
import forge_sandbox.jaredbgreat.dldungeons.builder.DBlock;
import forge_sandbox.jaredbgreat.dldungeons.planner.Dungeon;
import forge_sandbox.jaredbgreat.dldungeons.planner.astar.Step;
import forge_sandbox.jaredbgreat.dldungeons.rooms.Room;
import forge_sandbox.jaredbgreat.dldungeons.themes.ThemeFlags;
import io.papermc.lib.PaperLib;
import otd.Main;
import otd.MultiVersion;
import otd.api.event.DungeonGeneratedEvent;
import otd.lib.ZoneWorld;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;
import otd.world.DungeonType;

/**
 * A two dimensional map of the dungeon, including heights, blocks, and certain
 * features such as fall and doorways, and pathfinding data.
 * 
 * This map also includes the method for building itself into the actual world,
 * converting the 2 1/2 d mapping into blocks.
 * 
 * @author Jared Blackburn
 *
 */
public class MapMatrix {
	private static final Material lapis = Material.LAPIS_BLOCK;
	private static final Material slab = Material.STONE_SLAB;// Block.getBlockFromName("double_stone_slab");
	private static final Material gold = Material.GOLD_BLOCK;
	private static final Material glass = Material.GLASS;
	private static final BlockData STONE_SLAB = Bukkit.createBlockData("minecraft:stone_slab[type=double]");

	private static boolean drawFlyingMap = false;

	public final AsyncWorldEditor world;
	public final int chunkX, chunkZ, origenX, origenZ;

	// map of heights to build at
	public byte[][] ceilY; // Ceiling height
	public byte[][] floorY; // Floor Height
	public byte[][] nCeilY; // Height of Neighboring Ceiling
	public byte[][] nFloorY; // Height of Neighboring Floor

	// Blocks referenced against the DBlock.registry
	public int[][] ceiling;
	public int[][] wall;
	public int[][] floor;

	// The room id (index of the room in the dungeons main RoomList)
	public int[][] room;

	// Is it a wall?
	public boolean[][] isWall; // Is this coordinate occupied by a wall?
	public boolean[][] isFence; // Is this coordinate occupied by a wall?
	public boolean[][] hasLiquid; // Is floor covered by a liquid block?
	public boolean[][] isDoor; // Is there a door here?

	// The A* scratch pad
	public Step nodedge[][];
	public boolean astared[][];
	private int bottom;

	public MapMatrix(int width, AsyncWorldEditor world, int chunkX, int chunkZ) {
		this.world = world;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		origenX = (chunkX * 16) - (width / 2) + 8;
		origenZ = (chunkZ * 16) - (width / 2) + 8;
		ceilY = new byte[width][width];
		floorY = new byte[width][width];
		nCeilY = new byte[width][width];
		nFloorY = new byte[width][width];
		bottom = 0;
		if (MultiVersion.hasExtendedPos()) {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < width; j++) {
					ceilY[i][j] = -64;
					floorY[i][j] = -64;
					nCeilY[i][j] = -64;
					nFloorY[i][j] = -64;
					bottom = -64;
				}
			}
		}
		room = new int[width][width];
		ceiling = new int[width][width];
		wall = new int[width][width];
		floor = new int[width][width];
		isWall = new boolean[width][width];
		isFence = new boolean[width][width];
		hasLiquid = new boolean[width][width];
		isDoor = new boolean[width][width];
		nodedge = new Step[width][width];
		astared = new boolean[width][width];
	}

	public void buildAsync(Dungeon dungeon) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			buildAsync_actual(dungeon);
		});
	}

	private void buildAsync_actual(Dungeon dungeon) {
		AsyncWorldEditor aworld = world;
		world.setDefaultState(Material.STONE);
		dungeon.fixRoomContentsAsync(aworld);
		int shiftX = (chunkX * 16) - (room.length / 2) + 8;
		int shiftZ = (chunkZ * 16) - (room.length / 2) + 8;
		int below;
		boolean flooded = dungeon.theme.flags.contains(ThemeFlags.WATER);

		for (int i = 0; i < room.length; i++)
			for (int j = 0; j < room.length; j++) {
				if (room[i][j] != 0) {
					Room theRoom = dungeon.rooms.get(room[i][j]);

					// Debugging code; should not normally run
					if (drawFlyingMap) {
						if (astared[i][j]) {
							DBlock.placeBlockAsync(aworld, shiftX + i, 96, shiftZ + j, lapis);
						} else if (isDoor[i][j]) {
							DBlock.placeBlockAsync(aworld, shiftX + i, 96, shiftZ + j, slab);
						} else if (isWall[i][j]) {
							DBlock.placeBlockAsync(aworld, shiftX + i, 96, shiftZ + j, gold);
						} else {
							DBlock.placeBlockAsync(aworld, shiftX + i, 96, shiftZ + j, glass);
						}
					}

					// Lower parts of the room
					if (nFloorY[i][j] < floorY[i][j] && nFloorY[i][j] > bottom)
						for (int k = nFloorY[i][j]; k < floorY[i][j]; k++)
							if (noLowDegenerateAsync(aworld, theRoom, shiftX + i, k, shiftZ + j, i, j))
								DBlock.placeAsync(aworld, shiftX + i, k, shiftZ + j, wall[i][j]);
					if (nFloorY[i][j] > floorY[i][j] && floorY[i][j] > bottom)
						for (int k = floorY[i][j]; k < nFloorY[i][j]; k++)
							if (noLowDegenerateAsync(aworld, theRoom, shiftX + i, k, shiftZ + j, i, j))
								DBlock.placeAsync(aworld, shiftX + i, k, shiftZ + j, wall[i][j]);

					if (noLowDegenerateAsync(aworld, theRoom, shiftX + i, floorY[i][j] - 1, shiftZ + j, i, j)) {
						DBlock.placeAsync(aworld, shiftX + i, floorY[i][j] - 1, shiftZ + j, floor[i][j]);
						if (dungeon.theme.buildFoundation) {
							below = nFloorY[i][j] < floorY[i][j] ? nFloorY[i][j] - 1 : floorY[i][j] - 2;
							while (!DBlock.isGroundBlockAsync(aworld, shiftX + i, below, shiftZ + j)) {
								DBlock.placeAsync(aworld, shiftX + i, below, shiftZ + j, dungeon.floorBlock);
								below--;
								if (below < aworld.getBottom())
									break;
							}
						}
					}

					// Upper parts of the room
					if (!theRoom.sky && noHighDegenerateAsync(aworld, theRoom, shiftX + i, ceilY[i][j] + 1, shiftZ + j))
						DBlock.placeAsync(aworld, shiftX + i, ceilY[i][j] + 1, shiftZ + j, ceiling[i][j]);

					for (int k = roomBottom(i, j); k <= ceilY[i][j]; k++)
						if (!isWall[i][j])
							DBlock.deleteBlock(world, shiftX + i, k, shiftZ + j, flooded);
						else if (noHighDegenerateAsync(aworld, theRoom, shiftX + i, k, shiftZ + j))
							DBlock.placeAsync(aworld, shiftX + i, k, shiftZ + j, wall[i][j]);
					for (int k = nCeilY[i][j]; k < ceilY[i][j]; k++)
						if (noHighDegenerateAsync(aworld, theRoom, shiftX + i, k, shiftZ + j))
							DBlock.placeAsync(aworld, shiftX + i, k, shiftZ + j, wall[i][j]);
					if (isFence[i][j])
						DBlock.placeAsync(aworld, shiftX + i, floorY[i][j], shiftZ + j, dungeon.fenceBlock);

					if (isDoor[i][j]) {
						DBlock.deleteBlockAsync(aworld, shiftX + i, floorY[i][j], shiftZ + j, flooded);
						DBlock.deleteBlockAsync(aworld, shiftX + i, floorY[i][j] + 1, shiftZ + j, flooded);
						DBlock.deleteBlockAsync(aworld, shiftX + i, floorY[i][j] + 2, shiftZ + j, flooded);
					}

					// Liquids
					if (hasLiquid[i][j] && (!isWall[i][j] && !isDoor[i][j])
							&& aworld.getBlockState(shiftX + i, floorY[i][j] - 1, shiftZ + j) != Material.AIR)
						DBlock.placeAsync(aworld, shiftX + i, floorY[i][j], shiftZ + j, theRoom.liquidBlock);
				}
			}
		dungeon.addTileEntities();

		startBuild(dungeon, aworld);
	}

	private final static Map<UUID, Integer> POOL = new HashMap<>();

	private void startBuild(Dungeon dungeon, AsyncWorldEditor w) {
		Set<int[]> chunks0 = w.getAsyncWorld().getCriticalChunks();

		int delay = 0;

		UUID id;
		do {
			id = UUID.randomUUID();
		} while (POOL.containsKey(id));
		UUID uuid = id;
		POOL.put(uuid, chunks0.size());

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
							if (node.bd.getMaterial() != Material.GLASS_PANE
									&& node.bd.getMaterial() != Material.STONE_SLAB)
								c.getBlock(pos[0], pos[1], pos[2]).setBlockData(node.bd, false);
							if (node.bd.getMaterial() == Material.STONE_SLAB) {
								c.getBlock(pos[0], pos[1], pos[2]).setBlockData(STONE_SLAB, false);
							}
						} else {
							if (node.material != Material.GLASS_PANE && node.material != Material.STONE_SLAB)
								c.getBlock(pos[0], pos[1], pos[2]).setType(node.material, false);
							if (node.material == Material.STONE_SLAB)
								c.getBlock(pos[0], pos[1], pos[2]).setBlockData(STONE_SLAB, false);
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
							dungeon.addEntrances(w.getWorld());
						}, 1L);

						int x = (w.zone_world.getMaxX() + w.zone_world.getMinX()) / 2;
						int y = (w.zone_world.getMaxY() + w.zone_world.getMinY()) / 2;
						int z = (w.zone_world.getMaxZ() + w.zone_world.getMinZ()) / 2;

						Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
							DungeonGeneratedEvent event = new DungeonGeneratedEvent(w.getWorld(), chunks0,
									DungeonType.Doomlike, x, y, z);
							Bukkit.getServer().getPluginManager().callEvent(event);
						}, 2L);
					}
				});
			}, delay);
		}
	}

	/**
	 * Returns true if a block should be placed in those coordinates; that is the
	 * block is not air or the room is not degenerate.
	 * 
	 * This is for use with wall and ceiling blocks; for floor blocks use
	 * noLowDegenerate.
	 * 
	 * @param theRoom
	 * @param x       world x coordinate
	 * @param y       world y coordinate
	 * @param z       world z coordinate
	 * @return if the block should be placed here.
	 */
	@SuppressWarnings("unused")
	private boolean noHighDegenerate(Room theRoom, int x, int y, int z) {
		return !(theRoom.degenerate && world.getBlockState(x, y, z) == Material.AIR);
	}

	private boolean noHighDegenerateAsync(AsyncWorldEditor hworld, Room theRoom, int x, int y, int z) {
		return !(theRoom.degenerate && hworld.getBlockState(x, y, z) == Material.AIR);
	}

	/**
	 * Returns true if a floor block should be placed here. This will be true if the
	 * block is not air, if the room does not have degenerate floors, or is part of
	 * a main path through the room.
	 * 
	 * @param theRoom
	 * @param x       world x coordinate
	 * @param y       world y coordinate
	 * @param z       world z coordinate
	 * @param i       dungeon x coordinate
	 * @param j       dungeon z coordinate
	 * @return
	 * @return if the block should be placed here.
	 */
	@SuppressWarnings("unused")
	private boolean noLowDegenerate(Room theRoom, int x, int y, int z, int i, int j) {
		return !(theRoom.degenerateFloors && world.getBlockState(x, y, z) == Material.AIR && !astared[i][j]);
	}

	private boolean noLowDegenerateAsync(AsyncWorldEditor world, Room theRoom, int x, int y, int z, int i, int j) {
		return !(theRoom.degenerateFloors && world.getBlockState(x, y, z) == Material.AIR && !astared[i][j]);
	}

	/**
	 * The lowest height to place air or wall; walls may go one block lower.
	 * 
	 * @param i dungeon x coordinate
	 * @param j dungeon z coordinate
	 * @return lowest height to place a wall or air/water block.
	 */
	private int roomBottom(int i, int j) {
		int b = floorY[i][j];
		if (isWall[i][j] && !isDoor[i][j])
			b--;
		return b;
	}

	/**
	 * Sets whether the flying debug map should be drawn.
	 * 
	 * @param value
	 */
	public static void setDrawFlyingMap(boolean value) {
		drawFlyingMap = value;
	}
}
