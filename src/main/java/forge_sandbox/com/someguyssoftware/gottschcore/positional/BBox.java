/**
 * 
 */
package forge_sandbox.com.someguyssoftware.gottschcore.positional;

import forge_sandbox.AxisAlignedBB;
import javax.annotation.concurrent.Immutable;

/**
 * Wrapper for AxisAlignedBB
 * 
 * @author Mark Gottschling on Dec 14, 2018
 *
 */
@Immutable
public class BBox {

	private ICoords minCoords;
	private ICoords maxCoords;

	/**
	 * 
	 */
	public BBox(final ICoords c1, final ICoords c2) {
		int minX = Math.min(c1.getX(), c2.getX());
		int minY = Math.min(c1.getY(), c2.getY());
		int minZ = Math.min(c1.getZ(), c2.getZ());
		int maxX = Math.max(c1.getX(), c2.getX());
		int maxY = Math.max(c1.getY(), c2.getY());
		int maxZ = Math.max(c1.getZ(), c2.getZ());

		setMinCoords(new Coords(minX, minY, minZ));
		setMaxCoords(new Coords(maxX, maxY, maxZ));
	}

	/**
	 * 
	 * @param c
	 */
	public BBox(final ICoords c) {
		setMinCoords(new Coords(c));
		setMaxCoords(c.add(1, 1, 1));
	}

	/*
	 * Constructor from AxisAlignedBB.
	 */
	public BBox(final AxisAlignedBB aabb) {
		setMinCoords(new Coords((int) aabb.minX, (int) aabb.minY, (int) aabb.minZ));
		setMaxCoords(new Coords((int) aabb.maxX, (int) aabb.maxY, (int) aabb.maxZ));
	}

	public AxisAlignedBB toAABB() {
		return new AxisAlignedBB(this.minCoords.toPos(), this.maxCoords.toPos());
	}

	/**
	 * 
	 * @param other
	 * @return
	 */
	public boolean intersects(BBox other) {
		return this.toAABB().intersects(other.toAABB());
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public BBox grow(int value) {
		return new BBox(this.toAABB().grow(value));
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public BBox grow(int x, int y, int z) {
		return new BBox(this.toAABB().grow(x, y, z));
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public BBox expand(double x, double y, double z) {
		return new BBox(this.toAABB().expand(x, y, z));
	}

	/**
	 * @return the minCoords
	 */
	public ICoords getMinCoords() {
		return minCoords;
	}

	/**
	 * @param minCoords the minCoords to set
	 */
	public void setMinCoords(ICoords minCoords) {
		this.minCoords = minCoords;
	}

	/**
	 * @return the maxCoords
	 */
	public ICoords getMaxCoords() {
		return maxCoords;
	}

	/**
	 * @param maxCoords the maxCoords to set
	 */
	public void setMaxCoords(ICoords maxCoords) {
		this.maxCoords = maxCoords;
	}
}
