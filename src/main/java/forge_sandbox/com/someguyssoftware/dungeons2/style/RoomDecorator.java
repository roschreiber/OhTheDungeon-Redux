/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.style;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;

import forge_sandbox.GroupHelper;
import forge_sandbox.com.someguyssoftware.dungeons2.Dungeons2;
import forge_sandbox.com.someguyssoftware.dungeons2.config.ModConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.generator.Location;
import forge_sandbox.com.someguyssoftware.dungeons2.generator.blockprovider.IDungeonsBlockProvider;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Dungeon;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.rotate.RotatorHelper;
import forge_sandbox.com.someguyssoftware.dungeons2.spawner.SpawnGroup;
import forge_sandbox.com.someguyssoftware.dungeons2.spawner.SpawnSheet;
import forge_sandbox.com.someguyssoftware.dungeons2.spawner.SpawnerPopulator;
import forge_sandbox.com.someguyssoftware.dungeonsengine.chest.ILootLoader;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.ILevelConfig;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Rarity;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import forge_sandbox.com.someguyssoftware.gottschcore.random.RandomHelper;
import forge_sandbox.com.someguyssoftware.gottschcore.random.RandomProbabilityCollection;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.dungeons2.Chest_Later;

/**
 * @author Mark Gottschling on Sep 7, 2016
 *
 */
public class RoomDecorator implements IRoomDecorator {

	private SpawnerPopulator spawnerPopulator;
	private ILootLoader lootLoader;

	/**
	 * 
	 */
	public RoomDecorator() {
	}

	/**
	 * 
	 * @param loader
	 * @param spawnSheet
	 */
	public RoomDecorator(ILootLoader loader, SpawnSheet spawnSheet) {
		this.spawnerPopulator = new SpawnerPopulator(spawnSheet);
		this.setLootLoader(loader);
	}

	@Override
	public void decorate(AsyncWorldEditor world, Random random, Dungeon dungeon, IDungeonsBlockProvider provider,
			Room room, ILevelConfig config) {
//        Dungeons2.log.debug("floorMap in decorate -> {}", room.getFloorMap());
		List<Entry<DesignElement, ICoords>> surfaceAirZone = room.getFloorMap().entries().stream()
				.filter(x -> x.getKey().getFamily() == DesignElement.SURFACE_AIR).collect(Collectors.toList());

//        Dungeons2.log.debug("SurfaceAirZone.size() -> {}", surfaceAirZone.size());

		if (surfaceAirZone == null || surfaceAirZone.isEmpty())
			return;

		List<Entry<DesignElement, ICoords>> wallZone = null;
		List<Entry<DesignElement, ICoords>> floorZone = null;

		if (config.isDecorations() || ModConfig.enableChests) {
			// get the floor only (from the air zone)
			floorZone = surfaceAirZone.stream().filter(f -> f.getKey() == DesignElement.FLOOR_AIR)
					.collect(Collectors.toList());
		}

		// decorate enabled
		if (config.isDecorations()) {
			// TODO these methods could be reduced to more generic methods

			/*
			 * webs
			 */
			addBlock(world, random, provider, room, surfaceAirZone,
					new BlockData[] { Bukkit.createBlockData(Material.COBWEB) }, config.getWebFrequency(),
					config.getNumberOfWebs(), config);

			/*
			 * all-over decorations: moss, lichen, lichen2, mold
			 */
//            addAnywhereDecoration(world, random, provider, room, surfaceAirZone, config);

			// get the walls only (from the air zone)
			wallZone = surfaceAirZone.stream().filter(f -> f.getKey() == DesignElement.WALL_AIR)
					.collect(Collectors.toList());

			/*
			 * vines
			 */
			addVines(world, random, provider, room, wallZone, config);

			/*
			 * wall blood
			 */
//            addBlood(world, random, provider, room, wallZone, config);

			/*
			 * grass
			 */
			addGrass(world, random, provider, room, floorZone, config);

			// TODO change addGrass to addDirtSupportBlock() which will pass into grasses,
			// mushrooms
			/*
			 * floor blood
			 */
//            addBlood(world, random, provider, room, floorZone, config);

			/*
			 * puddles
			 */
//            addPuddles(world, random, provider, room, floorZone, config);

			/*
			 * add water (above the ceiling block so that there are drips coming down...
			 * maybe should be above puddles?
			 */

			// TODO add roots

			// TODO add debris

		}
		/*
		 * chest
		 */
		// TODO this should be part of ILevelConfig
		if (ModConfig.enableChests) {
			addChest(world, random, provider, room, floorZone, config);
		}

		/*
		 * determine if the rroom should get a spawner and what kind (boss, one-time,
		 * vanilla) etc
		 */
		if (ModConfig.enableSpawners) {
			ICoords spawnerCoords = addSpawner(world, random, provider, room, floorZone, config);
			if (spawnerCoords != null) {
				Dungeons2.log.debug("Adding spawner @ " + spawnerCoords.toShortString());
				// get the spawner tile entity
				List<SpawnGroup> groups = new ArrayList<>(spawnerPopulator.getSpawnSheet().getGroups().values());
				RandomProbabilityCollection<SpawnGroup> spawnerProbCol = new RandomProbabilityCollection<>(groups);
				SpawnGroup spawnGroup = (SpawnGroup) spawnerProbCol.next();
				spawnerPopulator.populate(world, spawnerCoords, random, spawnGroup);
			}
		}
	}

	// TODO load the chest sheet into categories ???
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.someguyssoftware.dungeons2.style.IRoomDecorator#decorate(net.minecraft.
	 * world.World, java.util.Random,
	 * com.someguyssoftware.dungeons2.generator.blockprovider.
	 * IDungeonsBlockProvider, com.someguyssoftware.dungeons2.model.Room,
	 * com.someguyssoftware.dungeons2.model.LevelConfig)
	 */
	@Deprecated
	@Override
	public void decorate(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			LevelConfig config) {
		List<Entry<DesignElement, ICoords>> surfaceAirZone = room.getFloorMap().entries().stream()
				.filter(x -> x.getKey().getFamily() == DesignElement.SURFACE_AIR).collect(Collectors.toList());
		if (surfaceAirZone == null || surfaceAirZone.isEmpty())
			return;

		List<Entry<DesignElement, ICoords>> wallZone;
		List<Entry<DesignElement, ICoords>> floorZone;

//        if (config.isDecorationsOn() || ModConfig.enableChests) {
		// get the floor only (from the air zone)
		floorZone = surfaceAirZone.stream().filter(f -> f.getKey() == DesignElement.FLOOR_AIR)
				.collect(Collectors.toList());
//        }

		// decorate enabled
		if (config.isDecorationsOn()) {
			// TODO these methods could be reduced to more generic methods

			/*
			 * webs
			 */
//            addWebs(world, random, provider, room, surfaceAirZone, config);
			addBlock(world, random, provider, room, surfaceAirZone,
					new BlockData[] { Bukkit.createBlockData(Material.COBWEB) }, config.getWebFrequency(),
					config.getNumberOfWebs(), config);

			/*
			 * all-over decorations: moss, lichen, lichen2, mold
			 */
//            addAnywhereDecoration(world, random, provider, room, surfaceAirZone, config);

			// get the walls only (from the air zone)
			wallZone = surfaceAirZone.stream().filter(f -> f.getKey() == DesignElement.WALL_AIR)
					.collect(Collectors.toList());

			/*
			 * vines
			 */
			addVines(world, random, provider, room, wallZone, config);

			/*
			 * wall blood
			 */
//            addBlood(world, random, provider, room, wallZone, config);

			/*
			 * grass
			 */
			addGrass(world, random, provider, room, floorZone, config);

			// TODO change addGrass to addDirtSupportBlock() which will pass into grasses,
			// mushrooms
			/*
			 * floor blood
			 */
//            addBlood(world, random, provider, room, floorZone, config);

			/*
			 * puddles
			 */
			addPuddles(world, random, provider, room, floorZone, config);

			/*
			 * add water (above the ceiling block so that there are drips coming down...
			 * maybe should be above puddles?
			 */

			// TODO add roots

			// TODO add debris

		}

		/*
		 * chest
		 */
		if (ModConfig.enableChests) {
			addChest(world, random, provider, room, floorZone, config);
		}

		/*
		 * determine if the rroom should get a spawner and what kind (boss, one-time,
		 * vanilla) etc
		 */
		if (ModConfig.enableSpawners) {
			ICoords spawnerCoords = addSpawner(world, random, provider, room, floorZone, config);
			if (spawnerCoords != null) {
				Dungeons2.log.debug("Adding spawner @ " + spawnerCoords.toShortString());
				// get the spawner tile entity
				List<SpawnGroup> groups = new ArrayList<>(spawnerPopulator.getSpawnSheet().getGroups().values());
				RandomProbabilityCollection<SpawnGroup> spawnerProbCol = new RandomProbabilityCollection<>(groups);
				SpawnGroup spawnGroup = (SpawnGroup) spawnerProbCol.next();
				spawnerPopulator.populate(world, spawnerCoords, random, spawnGroup);
			}
		}

	}

	/**
	 * 
	 * @param world
	 * @param random
	 * @param provider
	 * @param room
	 * @param airZone
	 * @param config
	 */
	@SuppressWarnings({ "incomplete-switch", "deprecation" })
	protected void addAnywhereDecoration(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider,
			Room room, List<Entry<DesignElement, ICoords>> airZone, LevelConfig config) {

		// for the number of webs to attempt to generate
		double freq = RandomHelper.randomDouble(random, config.getAnywhereDecorationFrequency().getMin(),
				config.getAnywhereDecorationFrequency().getMax());
		for (int i = 0; i < scaleNumForSizeOfRoom(room,
				RandomHelper.randomInt(random, config.getNumberOfAnywhereDecorations().getMinInt(),
						config.getNumberOfAnywhereDecorations().getMaxInt()),
				config); i++) {
			double n = random.nextDouble() * 100;
			// Material block = Material.VINE;

			if (n < freq && airZone.size() > 0) {
				// select ANY surface air spot
				int airZoneIndex = random.nextInt(airZone.size());
				Entry<DesignElement, ICoords> entry = airZone.get(airZoneIndex);
				DesignElement elem = airZone.get(airZoneIndex).getKey();
				ICoords coords = entry.getValue();
				Location location = provider.getLocation(coords, room, room.getLayout());
				MultipleFacing blockState = (MultipleFacing) Bukkit.createBlockData(Material.VINE);
				// check if the adjoining block exists
				if (hasSupport(world, coords, elem, location)) {
					// orient vines
					switch (elem) {
					case FLOOR_AIR:
						blockState.setFace(BlockFace.UP, true);
						break;
					case WALL_AIR:
						switch (location) {
						case NORTH_SIDE:
							blockState.setFace(BlockFace.SOUTH, true);
							break;
						case EAST_SIDE:
							blockState.setFace(BlockFace.WEST, true);
							break;
						case SOUTH_SIDE:
							blockState.setFace(BlockFace.NORTH, true);
							break;
						case WEST_SIDE:
							blockState.setFace(BlockFace.EAST, true);
							break;
						}
						break;
					case CEILING_AIR:
						blockState.setFace(BlockFace.DOWN, true);
						break;
					}
					// update the world
					world.setBlockState(coords.toPos(), blockState, 3);
					// remove location from airZone
					airZone.remove(entry);
				}
			}
		}
	}

	protected void addBlood(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> zone, LevelConfig config) {
		// TODO Do Something
//        // for the number of blood items to attempt to generate
//        double freq = RandomHelper.randomDouble(random, config.getBloodFrequency().getMin(), config.getBloodFrequency().getMax());
//        for (int i = 0; i < scaleNumForSizeOfRoom(room, RandomHelper.randomInt(random, config.getNumberOfBlood().getMinInt(), config.getNumberOfBlood().getMaxInt()), config); i++) {
//            double n = random.nextDouble() * 100;
////            Block block = Dungeons2.blood;
//            Block block = null;
//
//            if (n < freq && zone.size() > 0) {
//                // select ANY surface air spot
//                int airZoneIndex = random.nextInt(zone.size());
//                Entry<DesignElement, ICoords> entry = zone.get(airZoneIndex);
//                DesignElement elem = zone.get(airZoneIndex).getKey();
//                ICoords coords = entry.getValue();
//                Location location = provider.getLocation(coords, room, room.getLayout());
//                IBlockState blockState = null;
//                // check if the adjoining block exists
//                if (hasSupport(world, coords, elem, location)) {
//                    // orient vines
//                    switch(elem) {
//                    case FLOOR_AIR:
//                        blockState = block.getDefaultState().withProperty(DecorationBlock.BASE, EnumFacing.UP);
//                        break;
//                    case WALL_AIR:
//                        switch(location) {
//                        case NORTH_SIDE:
//                            blockState = block.getDefaultState().withProperty(DecorationBlock.BASE, EnumFacing.SOUTH);
//                            break;
//                        case EAST_SIDE:
//                            blockState = block.getDefaultState().withProperty(DecorationBlock.BASE, EnumFacing.WEST);
//                            break;
//                        case SOUTH_SIDE:
//                            blockState = block.getDefaultState().withProperty(DecorationBlock.BASE, EnumFacing.NORTH);
//                            break;
//                        case WEST_SIDE:
//                            blockState = block.getDefaultState().withProperty(DecorationBlock.BASE, EnumFacing.EAST);
//                            break;
//                        }
//                        break;
//                    }
//                    // update the world
//                    if (blockState != null) {
//                        world.setBlockState(coords.toPos(), blockState, 3);    
//                        // remove location from airZone
//                        zone.remove(entry);
//                    }
//                }
//            }
//        }            
	}

	/**
	 * 
	 * @param world
	 * @param random
	 * @param provider
	 * @param room
	 * @param zone
	 * @param config
	 */
	@SuppressWarnings("deprecation")
	protected void addWebs(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> zone, LevelConfig config) {

		// for the number of webs to attempt to generate
		double freq = RandomHelper.randomDouble(random, config.getWebFrequency().getMin(),
				config.getWebFrequency().getMax());
		for (int i = 0; i < scaleNumForSizeOfRoom(room, RandomHelper.randomInt(random,
				config.getNumberOfWebs().getMinInt(), config.getNumberOfWebs().getMaxInt()), config); i++) {
			double n = random.nextDouble() * 100;
			if (n < freq && zone.size() > 0) {
				// select ANY surface air spot
				int zoneIndex = random.nextInt(zone.size());
				Entry<DesignElement, ICoords> entry = zone.get(zoneIndex);
				DesignElement elem = zone.get(zoneIndex).getKey();
				ICoords webCoords = entry.getValue();
				// check if the adjoining block exists
				if (hasSupport(world, webCoords, elem, provider.getLocation(webCoords, room, room.getLayout()))) {
					// update the world
					world.setBlockState(webCoords.toPos(), Material.COBWEB, 3);
					// remove location from airZone
					zone.remove(entry);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	protected void addVines(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> zone, ILevelConfig config) {

		double freq = RandomHelper.randomDouble(random, config.getVineFrequency().getMin(),
				config.getVineFrequency().getMax());
		// Dungeons2.log.debug("Vine Freq:" + freq);
		for (int i = 0; i < scaleNumForSizeOfRoom(room, RandomHelper.randomInt(random,
				config.getNumberOfVines().getMinInt(), config.getNumberOfVines().getMaxInt()), config); i++) {
			double n = random.nextDouble() * 100;
			// Dungeons2.log.debug("Vine n:" + n);
			if (n < freq && zone.size() > 0) {
				int wallZoneIndex = random.nextInt(zone.size());
				DesignElement elem = zone.get(wallZoneIndex).getKey();
				ICoords vineCoords = zone.get(wallZoneIndex).getValue();
				if (hasSupport(world, vineCoords, elem, provider.getLocation(vineCoords, room, room.getLayout()))) {
					// orient vines
					Location location = provider.getLocation(vineCoords, room, room.getLayout());
					Direction d = provider.getDirection(vineCoords, room, DesignElement.WALL_AIR, location);
					// rotate vines
					BlockData blockState = RotatorHelper.rotateBlock(Bukkit.createBlockData(Material.VINE), d);
					// update the world
					world.setBlockState(vineCoords.toPos(), blockState, 3);
					// remove location from wallZone
					zone.remove(wallZoneIndex);
				}
			}
		}
	}

	/**
	 * 
	 * @param world
	 * @param random
	 * @param provider
	 * @param room
	 * @param zone
	 * @param config
	 */
	@Deprecated
	protected void addVines(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> zone, LevelConfig config) {

		double freq = RandomHelper.randomDouble(random, config.getVineFrequency().getMin(),
				config.getVineFrequency().getMax());
		// Dungeons2.log.debug("Vine Freq:" + freq);
		for (int i = 0; i < scaleNumForSizeOfRoom(room, RandomHelper.randomInt(random,
				config.getNumberOfVines().getMinInt(), config.getNumberOfVines().getMaxInt()), config); i++) {
			double n = random.nextDouble() * 100;
			// Dungeons2.log.debug("Vine n:" + n);
			if (n < freq && zone.size() > 0) {
				int wallZoneIndex = random.nextInt(zone.size());
				DesignElement elem = zone.get(wallZoneIndex).getKey();
				ICoords vineCoords = zone.get(wallZoneIndex).getValue();
				if (hasSupport(world, vineCoords, elem, provider.getLocation(vineCoords, room, room.getLayout()))) {
					// orient vines
					Location location = provider.getLocation(vineCoords, room, room.getLayout());
					Direction d = provider.getDirection(vineCoords, room, DesignElement.WALL_AIR, location);
					// rotate vines
					BlockData blockState = RotatorHelper.rotateBlock(Bukkit.createBlockData(Material.VINE), d);
					// update the world
					world.setBlockState(vineCoords.toPos(), blockState, 3);
					// remove location from wallZone
					zone.remove(wallZoneIndex);
				}
			}
		}
	}

	@SuppressWarnings({ "deprecation", "unlikely-arg-type" })
	protected void addGrass(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> floorZone, ILevelConfig config) {

		double freq = RandomHelper.randomDouble(random, config.getWebFrequency().getMin(),
				config.getWebFrequency().getMax());
		// Dungeons2.log.debug("Grass Freq:" + freq);
		for (int i = 0; i < scaleNumForSizeOfRoom(room, RandomHelper.randomInt(random,
				config.getNumberOfWebs().getMinInt(), config.getNumberOfWebs().getMaxInt()), config); i++) {
			double n = random.nextDouble() * 100;
			// Dungeons2.log.debug("Grass n:" + n);
			if (n < freq && floorZone.size() > 0) {

				// select a grass/mushroom
				int b = random.nextInt(5);
				Material plantBlockState = null;
				Material groundBlockState = null;
				switch (b) {
				case 0:
					plantBlockState = Material.SHORT_GRASS;
					break;
				case 1:
					plantBlockState = Material.DEAD_BUSH;
					break;
				case 2:
					plantBlockState = Material.FERN;
					break;
				case 3:
					plantBlockState = Material.BROWN_MUSHROOM;
					break;
				case 4:
					plantBlockState = Material.RED_MUSHROOM;
					break;
				default:
					plantBlockState = Material.SHORT_GRASS;
				}
				if (b < 3)
					groundBlockState = Material.DIRT;
				else
					groundBlockState = Material.PODZOL;

				// select ANY surface air spot
				int floorZoneIndex = random.nextInt(floorZone.size());
				DesignElement elem = floorZone.get(floorZoneIndex).getKey();
				ICoords grassCoords = floorZone.get(floorZoneIndex).getValue();
				// Dungeons2.log.debug("Grass Coords:" + grassCoords.toShortString());
				if (hasSupport(world, grassCoords, elem, provider.getLocation(grassCoords, room, room.getLayout()))) {
					// update the block below with dirt
					world.setBlockState(grassCoords.toPos().add(0, -1, 0), groundBlockState, 3);
					// update the world
					world.setBlockState(grassCoords.toPos(), plantBlockState, 3);
					// remove location from airZone
					floorZone.remove(elem);
				}
			}
		}
	}

	/**
	 * 
	 * @param world
	 * @param random
	 * @param provider
	 * @param room
	 * @param floorZone
	 * @param config
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Deprecated
	protected void addGrass(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> floorZone, LevelConfig config) {

		double freq = RandomHelper.randomDouble(random, config.getWebFrequency().getMin(),
				config.getWebFrequency().getMax());
		// Dungeons2.log.debug("Grass Freq:" + freq);
		for (int i = 0; i < scaleNumForSizeOfRoom(room, RandomHelper.randomInt(random,
				config.getNumberOfWebs().getMinInt(), config.getNumberOfWebs().getMaxInt()), config); i++) {
			double n = random.nextDouble() * 100;
			// Dungeons2.log.debug("Grass n:" + n);
			if (n < freq && floorZone.size() > 0) {

				// select a grass/mushroom
				int b = random.nextInt(5);
				Material plantBlockState = null;
				Material groundBlockState = null;
				switch (b) {
				case 0:
					plantBlockState = Material.SHORT_GRASS;
					break;
				case 1:
					plantBlockState = Material.DEAD_BUSH;
					break;
				case 2:
					plantBlockState = Material.FERN;
					break;
				case 3:
					plantBlockState = Material.BROWN_MUSHROOM;
					break;
				case 4:
					plantBlockState = Material.RED_MUSHROOM;
					break;
				default:
					plantBlockState = Material.SHORT_GRASS;
				}
				if (b < 3)
					groundBlockState = Material.DIRT;
				else
					groundBlockState = Material.PODZOL;

				// select ANY surface air spot
				int floorZoneIndex = random.nextInt(floorZone.size());
				DesignElement elem = floorZone.get(floorZoneIndex).getKey();
				ICoords grassCoords = floorZone.get(floorZoneIndex).getValue();
				// Dungeons2.log.debug("Grass Coords:" + grassCoords.toShortString());
				if (hasSupport(world, grassCoords, elem, provider.getLocation(grassCoords, room, room.getLayout()))) {
					// update the block below with dirt
					world.setBlockState(grassCoords.toPos().add(0, -1, 0), groundBlockState, 3);
					// update the world
					world.setBlockState(grassCoords.toPos(), plantBlockState, 3);
					// remove location from airZone
					floorZone.remove(elem);
				}
			}
		}
	}

	/**
	 * 
	 * @param world
	 * @param random
	 * @param provider
	 * @param room
	 * @param zone
	 * @param config
	 */
	@SuppressWarnings({ "deprecation", "unlikely-arg-type" })
	protected void addPuddles(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> zone, LevelConfig config) {

		double freq = RandomHelper.randomDouble(random, config.getPuddleFrequency().getMin(),
				config.getPuddleFrequency().getMax());
		// Dungeons2.log.debug("Grass Freq:" + freq);
		for (int i = 0; i < scaleNumForSizeOfRoom(room, RandomHelper.randomInt(random,
				config.getNumberOfPuddles().getMinInt(), config.getNumberOfPuddles().getMaxInt()), config); i++) {
			double n = random.nextDouble() * 100;
			if (n < freq && zone.size() > 0) {
				// select ANY floor air spot
				int floorZoneIndex = random.nextInt(zone.size());
				DesignElement elem = zone.get(floorZoneIndex).getKey();
				ICoords coords = zone.get(floorZoneIndex).getValue();
				// Dungeons2.log.debug("Grass Coords:" + grassCoords.toShortString());
				if (hasSupport(world, coords, elem, provider.getLocation(coords, room, room.getLayout()))) {
					// update the world
					world.setBlockState(coords.toPos(), Material.CHISELED_STONE_BRICKS, 3);
					// remove location from airZone
					zone.remove(elem);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	protected ICoords addChest(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> floorZone, ILevelConfig config) {
		ICoords chestCoords = null;
		// determine if room should get a chest
		double freq = RandomHelper.randomDouble(random, config.getChestFrequency().getMin(),
				config.getChestFrequency().getMax());
		if (RandomHelper.checkProbability(random, freq) && floorZone.size() > 0) {
			int floorIndex = random.nextInt(floorZone.size());
			DesignElement elem = floorZone.get(floorIndex).getKey();
			chestCoords = floorZone.get(floorIndex).getValue();
			// determine location in room
			Location location = provider.getLocation(chestCoords, room, room.getLayout());
			if (hasSupport(world, chestCoords, elem, location)) {
				BlockFace facing = orientChest(location);
				// place a chest
				Chest_Later.generate_later(world, random, chestCoords, Rarity.RARE, GroupHelper.CHEST.get(facing));
				// remove from list
				floorZone.remove(floorIndex);
			} else {
				chestCoords = null;
			}
		}
		// return coords
		return chestCoords;
	}

	/**
	 * 
	 * @param world
	 * @param random
	 * @param provider
	 * @param room
	 * @param floorZone
	 * @param config
	 */
	@Deprecated
	protected ICoords addChest(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> floorZone, LevelConfig config) {
		ICoords chestCoords = null;
		// determine if room should get a chest
		double freq = RandomHelper.randomDouble(random, config.getChestFrequency().getMin(),
				config.getChestFrequency().getMax());
		if (random.nextDouble() * 100 < freq && floorZone.size() > 0) {
			int floorIndex = random.nextInt(floorZone.size());
			DesignElement elem = floorZone.get(floorIndex).getKey();
			chestCoords = floorZone.get(floorIndex).getValue();
			// determine location in room
			Location location = provider.getLocation(chestCoords, room, room.getLayout());
			if (hasSupport(world, chestCoords, elem, location)) {
				BlockFace facing = orientChest(location);
				// place a chest
				Chest_Later.generate_later(world, random, chestCoords, Rarity.RARE, GroupHelper.CHEST.get(facing));
				// remove from list
				floorZone.remove(floorIndex);
			} else {
				chestCoords = null;
			}
		}
		// return coords
		return chestCoords;
	}

	@SuppressWarnings("deprecation")
	protected ICoords addSpawner(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> floorZone, ILevelConfig config) {
		ICoords spawnerCoords = null;

		// determine if room should get a chest
		double freq = RandomHelper.randomDouble(random, config.getSpawnerFrequency().getMin(),
				config.getSpawnerFrequency().getMax());
		if (random.nextDouble() * 100 < freq && floorZone.size() > 0) {
			int floorIndex = random.nextInt(floorZone.size());
			DesignElement elem = floorZone.get(floorIndex).getKey();
			spawnerCoords = floorZone.get(floorIndex).getValue();
			// determine location in room
			Location location = provider.getLocation(spawnerCoords, room, room.getLayout());
			if (hasSupport(world, spawnerCoords, elem, location)) {
				floorZone.remove(floorIndex);
			}
		}
		return spawnerCoords;
	}

	/**
	 * 
	 * @param world
	 * @param random
	 * @param provider
	 * @param room
	 * @param floorZone
	 * @param config
	 * @return
	 */
	@Deprecated
	protected ICoords addSpawner(AsyncWorldEditor world, Random random, IDungeonsBlockProvider provider, Room room,
			List<Entry<DesignElement, ICoords>> floorZone, LevelConfig config) {
		ICoords spawnerCoords = null;

		// determine if room should get a chest
		double freq = RandomHelper.randomDouble(random, config.getSpawnerFrequency().getMin(),
				config.getSpawnerFrequency().getMax());
		if (random.nextDouble() * 100 < freq && floorZone.size() > 0) {
			int floorIndex = random.nextInt(floorZone.size());
			DesignElement elem = floorZone.get(floorIndex).getKey();
			spawnerCoords = floorZone.get(floorIndex).getValue();
			// determine location in room
			Location location = provider.getLocation(spawnerCoords, room, room.getLayout());
			if (hasSupport(world, spawnerCoords, elem, location)) {
				floorZone.remove(floorIndex);
			}
		}
		return spawnerCoords;
	}

	public ILootLoader getLootLoader() {
		return lootLoader;
	}

	public void setLootLoader(ILootLoader lootLoader) {
		this.lootLoader = lootLoader;
	}
}
