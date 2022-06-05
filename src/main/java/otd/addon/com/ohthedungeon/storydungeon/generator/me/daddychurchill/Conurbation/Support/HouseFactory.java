// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Support;

import java.util.Random;
import org.bukkit.Material;

public class HouseFactory {
	protected static Material byteAir = Material.AIR;
	protected static Material byteGlass = Material.GLASS;

	public static void generateColonial(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ,
			final int baseY, final Material matFloor, final Material matWall, final Material matRoof) {
		generateColonial(chunk, random, chunkX, chunkZ, baseY, matFloor, matWall, matRoof, 1);
	}

	public static void generateColonial(final ByteChunk chunk, final Random random, final int chunkX, final int chunkZ,
			final int baseY, final Material matFloor, final Material matWall, final Material matRoof,
			final int floors) {
		boolean missingRoom = false;
		final Room[][][] rooms = new Room[floors][2][2];
		for (int f = 0; f < floors; ++f) {
			missingRoom = false;
			for (int x = 0; x < 2; ++x) {
				for (int z = 0; z < 2; ++z) {
					rooms[f][x][z] = new Room(random);
					if (floors == 1) {
						if (rooms[f][x][z].Missing) {
							if (!missingRoom) {
								missingRoom = true;
							} else {
								rooms[f][x][z].Missing = false;
							}
						}
					} else {
						if (f == 0) {
							rooms[f][x][z].Missing = false;
						} else if (rooms[f - 1][x][z].Missing) {
							rooms[f][x][z].Missing = true;
						} else if (rooms[f][x][z].Missing) {
							if (!missingRoom) {
								missingRoom = true;
							} else {
								rooms[f][x][z].Missing = false;
							}
						}
						if (f > 0) {
							rooms[f][x][z].SizeX = Math.min(rooms[f][x][z].SizeX, rooms[f - 1][x][z].SizeX);
							rooms[f][x][z].SizeZ = Math.min(rooms[f][x][z].SizeZ, rooms[f - 1][x][z].SizeZ);
						}
					}
				}
			}
		}
		final int roomOffsetX = chunk.width / 2 + random.nextInt(2) - 1;
		final int roomOffsetZ = chunk.width / 2 + random.nextInt(2) - 1;
		for (int f2 = 0; f2 < floors; ++f2) {
			for (int x2 = 0; x2 < 2; ++x2) {
				for (int z2 = 0; z2 < 2; ++z2) {
					drawRoom(chunk, rooms, f2, f2 == floors - 1, x2, z2, roomOffsetX, roomOffsetZ, baseY, matFloor,
							matWall, matRoof);
				}
			}
		}
		final int roofY = baseY + floors * 4 - 1;
		for (int y = 0; y < 3; ++y) {
			for (int x3 = 1; x3 < chunk.width - 1; ++x3) {
				for (int z3 = 1; z3 < chunk.width - 1; ++z3) {
					final int yAt = y + roofY;
					if (chunk.getBlock(x3 - 1, yAt, z3) != HouseFactory.byteAir
							&& chunk.getBlock(x3 + 1, yAt, z3) != HouseFactory.byteAir
							&& chunk.getBlock(x3, yAt, z3 - 1) != HouseFactory.byteAir
							&& chunk.getBlock(x3, yAt, z3 + 1) != HouseFactory.byteAir) {
						chunk.setBlock(x3, yAt + 1, z3, matRoof);
					}
				}
			}
		}
		for (int y = 1; y < 3; ++y) {
			for (int x3 = 1; x3 < chunk.width - 1; ++x3) {
				for (int z3 = 1; z3 < chunk.width - 1; ++z3) {
					final int yAt = y + roofY;
					if (chunk.getBlock(x3, yAt + 1, z3) != HouseFactory.byteAir) {
						chunk.setBlock(x3, yAt, z3, HouseFactory.byteAir);
					}
				}
			}
		}
	}

	protected static void drawRoom(final ByteChunk chunk, final Room[][][] rooms, final int floor,
			final boolean topFloor, final int x, final int z, final int roomOffsetX, final int roomOffsetZ,
			final int baseY, final Material matFloor, final Material matWall, final Material matRoof) {
		final Room room = rooms[floor][x][z];
		final boolean northRoom = x != 0;
		final boolean eastRoom = z != 0;
		if (!room.Missing) {
			final int x2 = roomOffsetX - (northRoom ? 0 : room.SizeX);
			final int x3 = roomOffsetX + (northRoom ? room.SizeX : 0);
			final int z2 = roomOffsetZ - (eastRoom ? 0 : room.SizeZ);
			final int z3 = roomOffsetZ + (eastRoom ? room.SizeZ : 0);
			final int y1 = baseY + floor * 4;
			final int y2 = y1 + 4 - 1;
			chunk.setBlocks(x2, x2 + 1, y1, y2, z2, z3 + 1, matWall);
			chunk.setBlocks(x3, x3 + 1, y1, y2, z2, z3 + 1, matWall);
			chunk.setBlocks(x2, x3 + 1, y1, y2, z2, z2 + 1, matWall);
			chunk.setBlocks(x2, x3 + 1, y1, y2, z3, z3 + 1, matWall);
			chunk.setBlocks(x2, x3 + 1, y1 - 1, y1, z2, z3 + 1, matFloor);
			if (!topFloor) {
				chunk.setBlocks(x2, x3 + 1, y2, y2 + 1, z2, z3 + 1, matRoof);
				chunk.setBlocks(x2 + 1, x3 + 1 - 1, y2, y2 + 1, z2 + 1, z3 + 1 - 1, matRoof);
			} else {
				chunk.setBlocks(x2 - 1, x3 + 2, y2, y2 + 1, z2 - 1, z3 + 2, matRoof);
			}
			if (northRoom) {
				if (eastRoom) {
					chunk.setBlocks(x2 + 2, y1, y2 - 1, z2, HouseFactory.byteAir);
					chunk.setBlocks(x2, y1, y2 - 1, z2 + 2, HouseFactory.byteAir);
					chunk.setBlocks(x2 + 2, x3 - 1, y1 + 1, y2 - 1, z3, z3 + 1, HouseFactory.byteGlass);
					chunk.setBlocks(x3, x3 + 1, y1 + 1, y2 - 1, z2 + 2, z3 - 1, HouseFactory.byteGlass);
				} else {
					chunk.setBlocks(x2 + 2, y1, y2 - 1, z3, HouseFactory.byteAir);
					chunk.setBlocks(x2, y1, y2 - 1, z3 - 2, HouseFactory.byteAir);
					chunk.setBlocks(x2 + 2, x3 - 1, y1 + 1, y2 - 1, z2, z2 + 1, HouseFactory.byteGlass);
					chunk.setBlocks(x3, x3 + 1, y1 + 1, y2 - 1, z2 + 2, z3 - 1, HouseFactory.byteGlass);
				}
			} else if (eastRoom) {
				chunk.setBlocks(x3 - 2, y1, y2 - 1, z2, HouseFactory.byteAir);
				chunk.setBlocks(x3, y1, y2 - 1, z2 + 2, HouseFactory.byteAir);
				chunk.setBlocks(x2 + 2, x3 - 1, y1 + 1, y2 - 1, z3, z3 + 1, HouseFactory.byteGlass);
				chunk.setBlocks(x2, x2 + 1, y1 + 1, y2 - 1, z2 + 2, z3 - 1, HouseFactory.byteGlass);
			} else {
				chunk.setBlocks(x3 - 2, y1, y2 - 1, z3, HouseFactory.byteAir);
				chunk.setBlocks(x3, y1, y2 - 1, z3 - 2, HouseFactory.byteAir);
				chunk.setBlocks(x2 + 2, x3 - 1, y1 + 1, y2 - 1, z2, z2 + 1, HouseFactory.byteGlass);
				chunk.setBlocks(x2, x2 + 1, y1 + 1, y2 - 1, z2 + 2, z3 - 1, HouseFactory.byteGlass);
			}
		}
	}

	private static class Room {
//        public static final int MinSize = 5;
//        public static final int MaxSize = 7;
//        public static final int MissingRoomOdds = 12;
		public boolean Missing;
		public int SizeX;
		public int SizeZ;

		public Room(final Random random) {
			this.Missing = (random.nextInt(12) == 0);
			this.SizeX = random.nextInt(2) + 5;
			this.SizeZ = random.nextInt(2) + 5;
		}
	}
}
