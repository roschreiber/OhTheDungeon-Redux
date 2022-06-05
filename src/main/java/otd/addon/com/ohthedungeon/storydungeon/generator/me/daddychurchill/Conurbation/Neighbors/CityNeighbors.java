// 
// Decompiled by Procyon v0.5.36
// 

package otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Neighbors;

import otd.addon.com.ohthedungeon.storydungeon.generator.me.daddychurchill.Conurbation.Plats.CityGenerator;
import org.bukkit.Material;

public class CityNeighbors {
	private int[][] neighboringFloors;
	public int floors;
	public Material wallMaterial;
	public Material floorMaterial;
	public Material roofMaterial;
	public static final int indentNorthSouth = 2;
	public static final int indentWestEast = 2;

	public CityNeighbors(final CityGenerator generator, final int chunkX, final int chunkZ) {
		this.floors = generator.getUrbanHeight(chunkX, chunkZ);
		this.wallMaterial = generator.getWallMaterial(chunkX, chunkZ);
		this.floorMaterial = generator.getFloorMaterial(chunkX, chunkZ);
		this.roofMaterial = generator.getRoofMaterial(chunkX, chunkZ);
		this.neighboringFloors = new int[3][3];
		this.neighboringFloors[1][1] = this.floors;
		for (int x = 0; x < 3; ++x) {
			for (int z = 0; z < 3; ++z) {
				if (x != 1 || z != 1) {
					final int x2 = chunkX + x - 1;
					final int z2 = chunkZ + z - 1;
					if (generator.noise.isUrbanBuilding(x2, z2)
							&& generator.getWallMaterial(x2, z2) == this.wallMaterial
							&& generator.getFloorMaterial(x2, z2) == this.floorMaterial
							&& generator.getRoofMaterial(x2, z2) == this.roofMaterial) {
						this.neighboringFloors[x][z] = generator.getUrbanHeight(x2, z2);
					}
				}
			}
		}
	}

	public void decrement() {
		for (int x = 0; x < 3; ++x) {
			for (int z = 0; z < 3; ++z) {
				final int[] array = this.neighboringFloors[x];
				final int n = z;
				--array[n];
			}
		}
	}

	public int getNeighborCount() {
		int result = 0;
		if (this.neighboringFloors[0][1] > 0) {
			++result;
		}
		if (this.neighboringFloors[1][0] > 0) {
			++result;
		}
		if (this.neighboringFloors[2][1] > 0) {
			++result;
		}
		if (this.neighboringFloors[1][2] > 0) {
			++result;
		}
		return result;
	}

	public boolean isRoundable() {
		if (this.toSouth()) {
			if (this.toWest()) {
				return !this.toNorth() && !this.toEast() && this.floorsSame(this.floorsToSouth(), this.floorsToWest());
			}
			if (this.toEast()) {
				return !this.toNorth() && this.floorsSame(this.floorsToSouth(), this.floorsToEast());
			}
		} else if (this.toNorth()) {
			if (this.toWest()) {
				return !this.toEast() && this.floorsSame(this.floorsToNorth(), this.floorsToWest());
			}
			if (this.toEast()) {
				return this.floorsSame(this.floorsToNorth(), this.floorsToEast());
			}
		}
		return false;
	}

	private boolean floorsSame(final int other1, final int other2) {
		return this.neighboringFloors[1][1] == other1 && other1 == other2;
	}

	private int floorsToWest() {
		return this.neighboringFloors[0][1];
	}

	private int floorsToEast() {
		return this.neighboringFloors[2][1];
	}

	private int floorsToNorth() {
		return this.neighboringFloors[1][0];
	}

	private int floorsToSouth() {
		return this.neighboringFloors[1][2];
	}

	public boolean toNorthEast() {
		return this.neighboringFloors[2][0] > 0 && this.toEast() && this.toNorth();
	}

	public boolean toEast() {
		return this.neighboringFloors[2][1] > 0;
	}

	public boolean toSouthEast() {
		return this.neighboringFloors[2][2] > 0 && this.toEast() && this.toSouth();
	}

	public boolean toNorth() {
		return this.neighboringFloors[1][0] > 0;
	}

	public boolean toCenter() {
		return true;
	}

	public boolean toSouth() {
		return this.neighboringFloors[1][2] > 0;
	}

	public boolean toNorthWest() {
		return this.neighboringFloors[0][0] > 0 && this.toWest() && this.toNorth();
	}

	public boolean toWest() {
		return this.neighboringFloors[0][1] > 0;
	}

	public boolean toSouthWest() {
		return this.neighboringFloors[0][2] > 0 && this.toWest() && this.toSouth();
	}

	public int insetToNorth() {
		return this.toNorth() ? 0 : 2;
	}

	public int insetToSouth() {
		return this.toSouth() ? 0 : 2;
	}

	public int insetToWest() {
		return this.toWest() ? 0 : 2;
	}

	public int insetToEast() {
		return this.toEast() ? 0 : 2;
	}
}
