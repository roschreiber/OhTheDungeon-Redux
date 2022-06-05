package forge_sandbox.jaredbgreat.dldungeons.planner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */

//import forge_sandbox.jaredbgreat.dldungeons.ConfigHandler;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.jaredbgreat.dldungeons.Difficulty;
//import jaredbgreat.dldungeons.DoomlikeDungeons;
import forge_sandbox.jaredbgreat.dldungeons.builder.DBlock;
import forge_sandbox.jaredbgreat.dldungeons.pieces.Spawner;
import forge_sandbox.jaredbgreat.dldungeons.pieces.chests.BasicChest;
import forge_sandbox.jaredbgreat.dldungeons.pieces.chests.LootCategory;
import forge_sandbox.jaredbgreat.dldungeons.pieces.chests.LootHandler;
import forge_sandbox.jaredbgreat.dldungeons.pieces.entrances.SimpleEntrance;
import forge_sandbox.jaredbgreat.dldungeons.pieces.entrances.SpiralStair;
import forge_sandbox.jaredbgreat.dldungeons.pieces.entrances.TopRoom;
import forge_sandbox.jaredbgreat.dldungeons.planner.astar.DoorChecker;
import forge_sandbox.jaredbgreat.dldungeons.planner.mapping.MapMatrix;
import forge_sandbox.jaredbgreat.dldungeons.rooms.Cave;
import forge_sandbox.jaredbgreat.dldungeons.rooms.Room;
import forge_sandbox.jaredbgreat.dldungeons.rooms.RoomList;
import forge_sandbox.jaredbgreat.dldungeons.themes.BiomeSets;
import forge_sandbox.jaredbgreat.dldungeons.themes.Degree;
import forge_sandbox.jaredbgreat.dldungeons.themes.Sizes;
import forge_sandbox.jaredbgreat.dldungeons.themes.Theme;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.doomlike.Chest_Later;

//import net.minecraft.world.World;
//import net.minecraft.world.biome.Biome;
//import net.minecraftforge.common.MinecraftForge;

/**
 * A representation of a dungeon level; as multi-level dungeons are not
 * generated this is, by extension, a dungeon. This class holds all the dungeon
 * wide information as well as the 2D map (MapMatrix) of the dungeon and its
 * list of rooms.
 * 
 * Methods of this class are responsible for determining the dungeon wide info
 * it holds and and for layout the rooms and other features.
 * 
 * @author Jared Blackburn
 *
 */
public class Dungeon {

	public Theme theme;
	public Random random;
	public Biome biome;

	public MapMatrix map; // 2D layout of the dungeon
	public Node[] nodes; // Main rooms (entrances and destination) which other rooms connect
	public int numNodes;
	public int roomCount;
	public int entrancePref;

	public int baseHeight; // Default floor height for the dungeon
	public int numEntrances = 0;

	public RoomList rooms;
	public SpawnerCounter spawners;
	public ArrayList<Room> planter;
	public ArrayList<Room> grower;

	// Planning parameters
	public Sizes size;

	// Not sure if I'll use all of them...
	public Degree outside; // Roofless rooms (also wall-less, but may have fences)
	public Degree liquids; // Quantity of water / lava pools
	public Degree subrooms; // Rooms budding of from the main one
	public Degree islands; // Rooms inside rooms
	public Degree pillars; // Uh, pillars / columns, duh!
	public Degree fences; // Uh, fences, duh!
	public Degree symmetry; // How symmetrical rooms are (technically, chance of axis mirroring)
	public Degree variability; // Inconsistency, that chance of using a different style in some place
	public Degree degeneracy; // Chance of walls / ceilings not spawning over airblocks (idea taken from
								// Greymerk)
	public Degree complexity; // Basically how many shape primitives to add; depth of added place seeds
	public Degree verticle; // How many height change and how much height change
	public Degree entrances; // Ways in and outS
	public Degree bigRooms; // Not currently used, but for oversided rooms between 1 and 2 time the normal
							// max
	public Degree naturals; // Cave like areas created with celluar automata

	// Default blocks
	public int wallBlock1;
	public int floorBlock;
	public int cielingBlock;
	public int stairBlock;
	public int stairSlab;
	public int fenceBlock;
	public int cornerBlock;
	public int liquidBlock;
	public int caveBlock;
	public AsyncWorldEditor aworld;

	public LootCategory lootCat;

	int shiftX;
	int shiftZ;

	/**
	 * De-links all referenced objects as a safety measure against memory leaks,
	 * which the complexity creates a risk for. This might not be needed, as
	 * circular have been checked for.
	 */
	public void preFinalize() {
		if (theme != null) {
			for (int i = 0; i < nodes.length; i++)
				nodes[i] = null;
			for (Room room : rooms) {
				room.preFinalize();
				room = null;
			}
			rooms.clear();
		}
		rooms = null;
		planter = grower = null;
		nodes = null;
		theme = null;
		random = null;
		biome = null;
		map = null;
		size = null;
		outside = null;
		liquids = null;
		subrooms = null;
		islands = null;
		pillars = null;
		symmetry = null;
		variability = null;
		degeneracy = null;
		complexity = null;
		verticle = null;
		entrances = null;
		bigRooms = null;
		naturals = null;
	}

	public Dungeon(Random rnd, Biome biome, World world, int chunkX, int chunkZ, AsyncWorldEditor aworld)
			throws Throwable {
//		DoomlikeDungeons.profiler.startTask("Planning Dungeon");
//		DoomlikeDungeons.profiler.startTask("Layout dungeon (rough draft)");
		this.aworld = aworld;
		random = rnd;
		this.biome = biome;
		theme = BiomeSets.getTheme(biome, random);
		if (theme == null) {
//                    Bukkit.getLogger().log(Level.SEVERE, "ERROR2");
			return;
		}

		applyTheme();
		entrancePref = random.nextInt(3);

		wallBlock1 = theme.walls[random.nextInt(theme.walls.length)];
		floorBlock = theme.floors[random.nextInt(theme.floors.length)];
		cielingBlock = theme.ceilings[random.nextInt(theme.ceilings.length)];
		fenceBlock = theme.fencing[random.nextInt(theme.fencing.length)];
		cornerBlock = theme.pillarBlock[random.nextInt(theme.pillarBlock.length)];
		liquidBlock = theme.liquid[random.nextInt(theme.liquid.length)];
		caveBlock = theme.caveWalls[random.nextInt(theme.caveWalls.length)];

		rooms = new RoomList(size.maxRooms + 1);
		planter = new ArrayList<>();
		map = new MapMatrix(size.width, aworld, chunkX, chunkZ);

		numNodes = random.nextInt(size.maxNodes - size.minNodes + 1) + size.minNodes + 1;
		nodes = new Node[numNodes];
		spawners = new SpawnerCounter();

		shiftX = (map.chunkX * 16) - (map.room.length / 2) + 8;
		shiftZ = (map.chunkZ * 16) - (map.room.length / 2) + 8;

		makeNodes();

		boolean easyFind = true;
		boolean singleEntrance = true;
		boolean thinSpawners = true;
		Difficulty difficulty = Difficulty.NORM;
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
			easyFind = swc.doomlike.easyFind;
			singleEntrance = swc.doomlike.singleEntrance;
			thinSpawners = swc.doomlike.thinSpawners;
			difficulty = swc.doomlike.difficulty;
		}

		if ((numEntrances < 1) && (easyFind || singleEntrance))
			addAnEntrance();
		connectNodes();
		growthCycle();
		if (thinSpawners && (difficulty != Difficulty.NONE)) {
			spawners.fixSpawners(world, this, rnd);
			for (Room room : rooms) {
				room.addChests(this);
			}
		}
	}

	/**
	 * Set all the dungeon wide theme derived variables that are of type Degree.
	 */
	private void applyTheme() {
		size = theme.sizes.select(random);
		outside = theme.outside.select(random);
		liquids = theme.liquids.select(random);
		subrooms = theme.subrooms.select(random);
		islands = theme.islands.select(random);
		pillars = theme.pillars.select(random);
		symmetry = theme.symmetry.select(random);
		variability = theme.variability.select(random);
		degeneracy = theme.degeneracy.select(random);
		complexity = theme.complexity.select(random);
		verticle = theme.verticle.select(random);
		entrances = theme.entrances.select(random);
		fences = theme.fences.select(random);
		naturals = theme.naturals.select(random);
		int[] range = this.aworld.getUpdatedRange(theme.getMinY(), theme.getMaxY());
		baseHeight = random.nextInt(range[1] - range[0]) + range[0];
		lootCat = LootHandler.getLootHandler().getCategory(theme.lootCat);
		if (lootCat == null) {
			lootCat = LootHandler.getLootHandler().getCategory("chest.cfg");
		}
	}

	/**
	 * Creates all the nodes and store along with a list of node rooms.
	 */
	private void makeNodes() {
		// DoomlikeDungeons.profiler.startTask("Creating Node Rooms");
		int i = 0;
		while (i < numNodes) {
			nodes[i] = new Node(random.nextInt(size.width), baseHeight, random.nextInt(size.width), random, this);
			if (nodes[i].hubRoom != null)
				++i;
		}
		// DoomlikeDungeons.profiler.endTask("Creating Node Rooms");
	}

	/**
	 * This will connect all the nodes with series of intermediate rooms by callaing
	 * either connectNodesDensely or connectNodesSparcely, with a 50% chance of
	 * each.
	 * 
	 * @throws Throwable
	 */
	private void connectNodes() {
		if (random.nextBoolean()) {
			connectNodesDensely();
		} else {
			connectNodesSparcely();
		}
	}

	/**
	 * This will attempt to connect all nodes based on the logic that if B can be
	 * reached from A, and C can be reached from B, then C can be reached from A (by
	 * going through B if no other route exists).
	 * 
	 * Specifically, it will connect the first node to one random other node, and
	 * then connect a random node already connected to the first with one that has
	 * not been connected, until all nodes have attempted a connects. Note that this
	 * does not guarantee connections as the attempt to place a route between any
	 * two nodes may fail.
	 * 
	 * @throws Throwable
	 */
	private void connectNodesSparcely() {
		// DoomlikeDungeons.profiler.startTask("Connecting Nodes");
		Node first, other;
		ArrayList<Node> connected = new ArrayList<>(nodes.length), disconnected = new ArrayList<>(nodes.length);
		connected.add(nodes[0]);
		for (int i = 1; i < nodes.length; i++) {
			disconnected.add(nodes[i]);
		}
		while (!disconnected.isEmpty()) {
			if (rooms.realSize() >= size.maxRooms) {
				// DoomlikeDungeons.profiler.endTask("Connecting Nodes");
				return;
			}
			first = connected.get(random.nextInt(connected.size()));
			other = disconnected.get(random.nextInt(disconnected.size()));
			new Route(first, other).drawConnections(this);
			connected.add(other);
			disconnected.remove(other);
		}
		// DoomlikeDungeons.profiler.endTask("Connecting Nodes");
	}

	/**
	 * This will attempt to make one connects between every two pairs of nodes by
	 * first connecting the first node to all others directly, then each successive
	 * node to every node with a higher index. As nodes with a lower index will
	 * already have attempted a connects this is not repeated. Note that this does
	 * not guarantee connections as the attempt to place a route between any two
	 * nodes may fail.
	 * 
	 * @throws Throwable
	 */
	private void connectNodesDensely() {
		// DoomlikeDungeons.profiler.startTask("Connecting Nodes");
		Node first, other;
		for (int i = 0; i < nodes.length; i++) {
			first = nodes[i];
			for (int j = i + 1; j < nodes.length; j++) {
				other = nodes[j];
				if (rooms.realSize() >= size.maxRooms) {
					// DoomlikeDungeons.profiler.endTask("Connecting Nodes");
					return;
				}
				if (other != first) {
					new Route(first, other).drawConnections(this);
				}
			}
		}
		// DoomlikeDungeons.profiler.endTask("Connecting Nodes");
	}

	/**
	 * This will add side rooms not directly connecting nodes (though new connection
	 * are often added by luck); these are the extra rooms whose only real purpose
	 * it to give the player more areas to explore, mobs to fights, and chests to
	 * loot.
	 * 
	 * The basic system try to sprout side room from all rooms that are eligible to
	 * produce side rooms in random order, but trying to grow rooms their doors. If
	 * a room fails to grow a new room it is no longer considered eligible for
	 * future attempt, while all new rooms start as eligible by default. The cycle
	 * will repeat until either the maximum number of rooms for the dungeons size
	 * have been generated or there are no more eligible rooms to spread from.
	 */
	private void growthCycle() {
		// DoomlikeDungeons.profiler.startTask("Adding Rooms (growthCycle)");
		boolean doMore = true;
		do {
			doMore = false;
			grower = planter;
			Collections.shuffle(grower, random);
			planter = new ArrayList<Room>();
			for (Room room : grower) {
				if (rooms.realSize() >= size.maxRooms) {
					return;
				}
				if (room.plantChildren(this)) {
					doMore = true;
				}
			}
		} while (doMore);
		// DoomlikeDungeons.profiler.endTask("Adding Rooms (growthCycle)");
	}

	/**
	 * This is the master method for error corrections and improvements in the
	 * dungeon. While it does none of these things directly, it calls methods for
	 * removing doors-to-nowhere, determining which doors to treat as the main
	 * connection between rooms, check room passibility, and check dungeon
	 * connectivity, ensure all these methods are called (and in the correct order).
	 */
	public void fixRoomContents() {
		for (Room room : rooms) {
			addChestBlocks(room);
			DoorChecker.processDoors1(this, room);
		}
		for (Room room : rooms) {
			DoorChecker.processDoors2(this, room);
		}
		for (Room room : rooms) {
			DoorChecker.processDoors3(this, room);
			if (room instanceof Cave)
				DoorChecker.caveConnector(this, room);
		}
		DoorChecker.checkConnectivity(this);
	}

	public void fixRoomContentsAsync(AsyncWorldEditor hworld) {
		for (Room room : rooms) {
			addChestBlocksAsync(room, hworld);
			DoorChecker.processDoors1(this, room);
		}
		for (Room room : rooms) {
			DoorChecker.processDoors2(this, room);
		}
		for (Room room : rooms) {
			DoorChecker.processDoors3(this, room);
			if (room instanceof Cave)
				DoorChecker.caveConnector(this, room);
		}
		DoorChecker.checkConnectivity(this);
	}

	/**
	 * Places the chest blocks; this is done in advance to decrease the chance of
	 * absent chests resulting from concurrent optimization in the main game.
	 * 
	 * @param room
	 */
	public void addChestBlocks(Room room) {
//		if(MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.AddChestBlocksToRoom(this, room))) return;
		for (BasicChest chest : room.chests) {
			DBlock.placeChest(map.world, shiftX + chest.mx, chest.my, shiftZ + chest.mz);
		}
	}

	public void addChestBlocksAsync(Room room, AsyncWorldEditor hworld) {
//		if(MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.AddChestBlocksToRoom(this, room))) return;
		for (BasicChest chest : room.chests) {
//			DBlock.placeChest(map.world, shiftX + chest.mx, chest.my, shiftZ + chest.mz);
			hworld.setBlockType(shiftX + chest.mx, chest.my, shiftZ + chest.mz, DBlock.chest);
		}
	}

	/**
	 * This cycles through all the rooms and add chests and spawners by calling
	 * addTileEntitiesToRoom on each.
	 */
	public void addTileEntities() {
//            Bukkit.getLogger().info("count" + rooms.size());
		for (Room room : rooms) {
			addTileEntitesToRoom(room);
		}
	}

	/**
	 * This add all the chest and spawners to the room.
	 * 
	 * @param room
	 */
	private void addTileEntitesToRoom(Room room) {
		for (Spawner spawner : room.spawners) {

			EntityType et;
			try {
				et = EntityType.valueOf(spawner.getMob());
			} catch (IllegalArgumentException ex) {
				et = EntityType.ZOMBIE;
			}

			DBlock.placeSpawner(map.world, shiftX + spawner.getX(), spawner.getY(), shiftZ + spawner.getZ(), et);
		}

		boolean enable = true;
		String world_name = map.world.getWorldName();
		if (WorldConfig.wc.dict.containsKey(world_name))
			enable = WorldConfig.wc.dict.get(world_name).doomlike.builtinLoot;
		for (BasicChest chest : room.chests) {
			Chest_Later.generate_later(map.world, random, new Coord(shiftX + chest.mx, chest.my, shiftZ + chest.mz),
					enable, chest);
		}
	}

	/**
	 * This cycles through all nodes and calls addEntrance on each; entrances will
	 * only be added to entrance nodes, but that is checked by addEntrance, not
	 * here.
	 */
	public void addEntrances(World world) {
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].hubRoom != null)
				addEntrance(world, nodes[i].hubRoom);
		}
	}

	/**
	 * This will added a physical entrance to all entrance nodes.
	 * 
	 * @param room
	 */
	private void addEntrance(World world, Room room) {
		if (!room.hasEntrance)
			return;
		// DoomlikeDungeons.profiler.startTask("Adding Entrances");
		int entrance;
		if (variability.use(random))
			entrance = random.nextInt(3);
		else
			entrance = entrancePref;

		boolean easyFind = false;
		if (WorldConfig.wc.dict.containsKey(world.getName())) {
			SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
			easyFind = swc.doomlike.easyFind;
		}

		if (easyFind)
			entrance = 1;

		switch (entrance) {
		case 0:
			new SpiralStair((int) room.realX, (int) room.realZ).build(this, world);
			break;
		case 1:
			new TopRoom((int) room.realX, (int) room.realZ).build(this, world);
			break;
		case 2:
		default:
			new SimpleEntrance((int) room.realX, (int) room.realZ).build(this, world);
			break;
		}
	}

	/**
	 * This will convert one non-entrance node into an entrance node if and only if
	 * the number of entrances has not been set to a degree of NONE by the dungeons
	 * theme.
	 */
	private void addAnEntrance() {
		if (theme.entrances.never())
			return;
		int which = random.nextInt(nodes.length);
		Room it = nodes[which].hubRoom;
		it.chests.clear();
		it.spawners.clear();
		it.hasEntrance = true;
		it.hasSpawners = false;
		numEntrances = 1;
		for (int i = (int) it.realX - 2; i < ((int) it.realX + 2); i++)
			for (int j = (int) it.realZ - 2; j < ((int) it.realZ + 2); j++) {
				map.floorY[i][j] = (byte) it.floorY;
				map.hasLiquid[i][j] = false;
				map.isWall[i][j] = false;
			}
	}

}
