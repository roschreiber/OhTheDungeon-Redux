/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import java.util.Random;

import forge_sandbox.com.someguyssoftware.dungeons2.generator.strategy.IRoomGenerationStrategy;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Door;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Hallway;
import forge_sandbox.com.someguyssoftware.dungeons2.model.LevelConfig;
import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.StyleSheet;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Theme;
import forge_sandbox.com.someguyssoftware.dungeonsengine.config.ILevelConfig;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Alignment;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import forge_sandbox.BlockPos;
import org.bukkit.Material;
import otd.lib.async.AsyncWorldEditor;

/**
 * @author Mark Gottschling on Aug 28, 2016
 *
 */
public class HallwayGenerator extends AbstractRoomGenerator {

	private IRoomGenerationStrategy roomGenerationStrategy;

	/**
	 * Enforce that the room generator has to have a structure generator.
	 * 
	 * @param generator
	 */
	public HallwayGenerator(IRoomGenerationStrategy generator) {
		setGenerationStrategy(generator);
	}

	@Override
	public void generate(AsyncWorldEditor world, Random random, Room room, Theme theme, StyleSheet styleSheet,
			ILevelConfig config) {

		// generate the room structure
		getGenerationStrategy().generate(world, random, room, theme, styleSheet, config);

		/*
		 * add additional elements
		 */
		Hallway hw = (Hallway) room;
		// build the doors
		for (Door door : hw.getDoors()) {
			if (hw.getAlignment() == Alignment.HORIZONTAL) {
				// test which side the door is on
				if (door.getCoords().getX() == hw.getMinX()) {
					if (door.getCoords().getZ() == door.getRoom().getMinZ()) {
						buildDoorway(world, door.getCoords(), Direction.WEST, Direction.SOUTH);
					} else if (door.getCoords().getZ() == door.getRoom().getMaxZ()) {
						buildDoorway(world, door.getCoords(), Direction.WEST, Direction.NORTH);
					} else {
						buildDoorway(world, door.getCoords(), Direction.WEST);
					}
				}
				if (door.getCoords().getX() == hw.getMaxX()) {
					if (door.getCoords().getZ() == door.getRoom().getMinZ()) {
						buildDoorway(world, door.getCoords(), Direction.EAST, Direction.SOUTH);
					} else if (door.getCoords().getZ() == door.getRoom().getMaxZ()) {
						buildDoorway(world, door.getCoords(), Direction.EAST, Direction.NORTH);
					} else {
						buildDoorway(world, door.getCoords(), Direction.EAST);
					}
				}
			}
			// VERTICAL (NORTH/SOUTH)
			else {

				if (door.getCoords().getZ() == hw.getMinZ()) {
					if (door.getCoords().getX() == door.getRoom().getMinX()) {
						buildDoorway(world, door.getCoords(), Direction.NORTH, Direction.EAST);
					} else if (door.getCoords().getX() == door.getRoom().getMaxX()) {
						buildDoorway(world, door.getCoords(), Direction.NORTH, Direction.WEST);
					} else {
						buildDoorway(world, door.getCoords(), Direction.NORTH);
					}
				}
				if (door.getCoords().getZ() == hw.getMaxZ()) {
					if (door.getCoords().getX() == door.getRoom().getMinX()) {
						buildDoorway(world, door.getCoords(), Direction.SOUTH, Direction.EAST);
					} else if (door.getCoords().getX() == door.getRoom().getMaxX()) {
						buildDoorway(world, door.getCoords(), Direction.SOUTH, Direction.WEST);
					} else {
						buildDoorway(world, door.getCoords(), Direction.SOUTH);
					}
				}
			}
		}
	}

	@Deprecated
	@Override
	public void generate(AsyncWorldEditor world, Random random, Room room, Theme theme, StyleSheet styleSheet,
			LevelConfig config) {

		// generate the room structure
		getGenerationStrategy().generate(world, random, room, theme, styleSheet, config);

		/*
		 * add additional elements
		 */
		Hallway hw = (Hallway) room;
		// build the doors
		for (Door door : hw.getDoors()) {
			if (hw.getAlignment() == Alignment.HORIZONTAL) {
				// test which side the door is on
				if (door.getCoords().getX() == hw.getMinX()) {
					if (door.getCoords().getZ() == door.getRoom().getMinZ()) {
						buildDoorway(world, door.getCoords(), Direction.WEST, Direction.SOUTH);
					} else if (door.getCoords().getZ() == door.getRoom().getMaxZ()) {
						buildDoorway(world, door.getCoords(), Direction.WEST, Direction.NORTH);
					} else {
						buildDoorway(world, door.getCoords(), Direction.WEST);
					}
				}
				if (door.getCoords().getX() == hw.getMaxX()) {
					if (door.getCoords().getZ() == door.getRoom().getMinZ()) {
						buildDoorway(world, door.getCoords(), Direction.EAST, Direction.SOUTH);
					} else if (door.getCoords().getZ() == door.getRoom().getMaxZ()) {
						buildDoorway(world, door.getCoords(), Direction.EAST, Direction.NORTH);
					} else {
						buildDoorway(world, door.getCoords(), Direction.EAST);
					}
				}
			}
			// VERTICAL (NORTH/SOUTH)
			else {

				if (door.getCoords().getZ() == hw.getMinZ()) {
					if (door.getCoords().getX() == door.getRoom().getMinX()) {
						buildDoorway(world, door.getCoords(), Direction.NORTH, Direction.EAST);
					} else if (door.getCoords().getX() == door.getRoom().getMaxX()) {
						buildDoorway(world, door.getCoords(), Direction.NORTH, Direction.WEST);
					} else {
						buildDoorway(world, door.getCoords(), Direction.NORTH);
					}
				}
				if (door.getCoords().getZ() == hw.getMaxZ()) {
					if (door.getCoords().getX() == door.getRoom().getMinX()) {
						buildDoorway(world, door.getCoords(), Direction.SOUTH, Direction.EAST);
					} else if (door.getCoords().getX() == door.getRoom().getMaxX()) {
						buildDoorway(world, door.getCoords(), Direction.SOUTH, Direction.WEST);
					} else {
						buildDoorway(world, door.getCoords(), Direction.SOUTH);
					}
				}
			}
		}
	}

	/**
	 * A special case of doorway that needs to be generated when the wayline/hallway
	 * connects to a room parallel with the room's wall.
	 * 
	 * @param world
	 * @param coords
	 * @param west
	 * @param south
	 */
	protected void buildDoorway(AsyncWorldEditor world, ICoords coords, Direction direction, Direction doubleSide) {
		int touching;
		int x = 0;
		int z = 0;
		int dx = 0;
		int dz = 0;
		int failSafe = 0;

		// setup the side that double side will remove
		switch (doubleSide) {
		case NORTH:
			dz--;
			break;
		case EAST:
			dx++;
			break;
		case SOUTH:
			dz++;
			break;
		case WEST:
			dx--;
			break;
		default:
		}

		do {
			touching = 0;

			// carve "regular" doorway
			world.setBlockState(coords.add(x, 1, z).toPos(), Material.AIR, 3);
			world.setBlockState(coords.add(x, 2, z).toPos(), Material.AIR, 3);
			// carve double-side doorway
			world.setBlockState(coords.add(dx, 1, dz).toPos(), Material.AIR, 3);
			world.setBlockState(coords.add(dx, 2, dz).toPos(), Material.AIR, 3);

			// check in all four directions and add 1 to value
			BlockPos pos = coords.add(dx, 1, dz).toPos();
			if (world.getBlockState(pos.north()) == Material.AIR)
				touching++;
			if (world.getBlockState(pos.south()) == Material.AIR)
				touching++;
			if (world.getBlockState(pos.east()) == Material.AIR)
				touching++;
			if (world.getBlockState(pos.west()) == Material.AIR)
				touching++;

			// move to next block
			switch (direction) {
			case NORTH:
				z--;
				dz--;
				break;
			case EAST:
				x++;
				dx++;
				break;
			case SOUTH:
				z++;
				dz++;
				break;
			case WEST:
				x--;
				dx--;
				break;
			default:
			}
			failSafe++;
		} while (touching < 3 && failSafe < 5);
	}

	/**
	 * @return the roomGenerationStrategy
	 */
	@Override
	public final IRoomGenerationStrategy getGenerationStrategy() {
		return roomGenerationStrategy;
	}

	/**
	 * @param roomGenerationStrategy the roomGenerationStrategy to set
	 */
	public final void setGenerationStrategy(IRoomGenerationStrategy roomGenerationStrategy) {
		this.roomGenerationStrategy = roomGenerationStrategy;
	}

}
