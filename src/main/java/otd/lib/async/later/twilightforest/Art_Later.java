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
package otd.lib.async.later.twilightforest;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Art;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;
import org.bukkit.util.BoundingBox;

import forge_sandbox.BlockPos;
import forge_sandbox.StructureBoundingBox;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import otd.lib.async.AsyncWorldEditor;
import otd.lib.async.later.roguelike.Later;

public class Art_Later extends Later {
	private AsyncWorldEditor world;
	private final Random rand;
	private final int howMany;
	private final int floorLevel;
	private final BlockFace direction;
	private final int minSize;
	private final StructureBoundingBox sbb;
	private final StructureBoundingBox boundingBox;

	public Art_Later(AsyncWorldEditor world, Random rand, int howMany, int floorLevel, BlockFace direction, int minSize,
			StructureBoundingBox sbb, StructureBoundingBox boundingBox) {
		this.world = world;
		this.rand = rand;
		this.howMany = howMany;
		this.floorLevel = floorLevel;
		this.direction = direction;
		this.minSize = minSize;
		this.sbb = sbb;
		this.boundingBox = boundingBox;
	}

	/**
	 * At least one of the painting's parameters must be the specified size or
	 * greater
	 */
	public static Art getPaintingOfSize(Random rand, int minSize) {
		ArrayList<Art> valid = new ArrayList<>();

		for (Art art : Art.values()) {
			if (art.getBlockWidth() >= minSize || art.getBlockHeight() >= minSize) {
				valid.add(art);
			}
		}

		if (valid.size() > 0) {
			return valid.get(rand.nextInt(valid.size()));
		} else {
			return null;
		}
	}

	/**
	 * This returns the real-world coordinates of a possible painting or torch spot
	 * on the specified wall of this tower.
	 */
	public static BlockPos getRandomWallSpot(Random rand, int floorLevel, BlockFace direction, StructureBoundingBox sbb,
			StructureBoundingBox boundingBox) {
		int minX = boundingBox.minX + 2;
		int maxX = boundingBox.maxX - 2;

		int minY = boundingBox.minY + floorLevel + 2;
		int maxY = boundingBox.maxY - 2;

		int minZ = boundingBox.minZ + 2;
		int maxZ = boundingBox.maxZ - 2;

		// constrain the paintings to one wall
		// these directions correspond to painting facing directions, not necessarily to
		// the structure orienting directions
		if (direction == BlockFace.SOUTH) {
			minZ = boundingBox.minZ;
			maxZ = boundingBox.minZ;
		} else if (direction == BlockFace.WEST) {
			maxX = boundingBox.maxX;
			minX = boundingBox.maxX;
		} else if (direction == BlockFace.NORTH) {
			maxZ = boundingBox.maxZ;
			minZ = boundingBox.maxZ;
		} else if (direction == BlockFace.EAST) {
			minX = boundingBox.minX;
			maxX = boundingBox.minX;
		}

		// try 30 times to get a proper result
		for (int i = 0; i < 30; i++) {
			int cx = minX + (maxX > minX ? rand.nextInt(maxX - minX) : 0);
			int cy = minY + (maxY > minY ? rand.nextInt(maxY - minY) : 0);
			int cz = minZ + (maxZ > minZ ? rand.nextInt(maxZ - minZ) : 0);

			final BlockPos blockPos = new BlockPos(cx, cy, cz).offset(direction);
			if (sbb.isVecInside(blockPos)) {
				return blockPos;
			}
		}

		// I guess we didn't get one
		// TwilightForestMod.LOGGER.info("getRandomWallSpot - We didn't find a valid
		// random spot on the wall.");
		return null;
	}

	/**
	 * This is similar to EntityPainting.isOnValidSurface, except that it does not
	 * check for a solid wall behind the painting.
	 */
	protected boolean checkPainting(AsyncWorldEditor world, Painting painting) {

		if (painting == null) {
			return false;
		}

		final BoundingBox largerBox = painting.getBoundingBox();
		return !hasOverlay(world.getWorld(), painting, largerBox);
	}

	protected static boolean hasOverlay(World world, Entity entity, BoundingBox box) {
		int x1 = (int) Math.floor((box.getMinX() - 2.0D) / 16.0D);
		int x2 = (int) Math.floor((box.getMaxX() + 2.0D) / 16.0D);
		int y1 = (int) Math.floor((box.getMinY() - 2.0D) / 16.0D);
		int y2 = (int) Math.floor((box.getMaxY() + 2.0D) / 16.0D);

		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if (world.isChunkLoaded(i, j)) {
					Chunk c = world.getChunkAt(i, j);
					Entity[] es = c.getEntities();
					for (Entity e : es) {
						if (e != entity && e.getBoundingBox().overlaps(box) && entity instanceof Painting) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public static boolean generate_later(AsyncWorldEditor world, Random rand, int howMany, int floorLevel,
			BlockFace direction, int minSize, StructureBoundingBox sbb, StructureBoundingBox boundingBox) {
		Art_Later later = new Art_Later(world, rand, howMany, floorLevel, direction, minSize, sbb, boundingBox);
		world.addLater(later);

		return true;
	}

	@Override
	public Coord getPos() {
		return null;
	}

	@Override
	public void doSomething() {
		World w = world.getWorld();
		for (int i = 0; i < howMany; i++) {
			// get some random coordinates on the wall in the chunk
			BlockPos pCoords = getRandomWallSpot(rand, floorLevel, direction, sbb, boundingBox);

			// initialize a painting object
			Art art = getPaintingOfSize(rand, minSize);
			if (art == null)
				continue;
			Location loc = new Location(w, pCoords.x, pCoords.y + this.y, pCoords.z);
//            Painting painting = (Painting) EntityCreator.create(EntityType.PAINTING, loc);
//            
//            painting.setArt(art);
//            painting.setFacingDirection(direction);
//            painting.teleport(loc);

			// check if we can fit a painting there
//            if (checkPainting(world, painting)) {
			// place the painting
//                EntityCreator.spawn(w, painting);
			try {
				Painting p = (Painting) w.spawnEntity(loc, EntityType.PAINTING);
				p.setArt(art);
				p.setFacingDirection(direction);

			} catch (Exception ex) {

			}
//            }
		}
		this.world = null;
	}

	@Override
	public void doSomethingInChunk(Chunk c) {

	}
}
