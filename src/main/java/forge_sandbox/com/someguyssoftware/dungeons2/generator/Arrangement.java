/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator;

import forge_sandbox.com.someguyssoftware.dungeons2.style.DesignElement;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;

/**
 * @author Mark Gottschling on Aug 4, 2016
 *
 */
public class Arrangement {
	private DesignElement element;
	private Direction direction;
	private Location location;

	/**
	 * 
	 */
	public Arrangement() {

	}

	/**
	 * 
	 * @param element
	 * @param location
	 * @param direction
	 */
	public Arrangement(DesignElement element, Location location, Direction direction) {
		setElement(element);
		setLocation(location);
		setDirection(direction);
	}

	/**
	 * @return the element
	 */
	public DesignElement getElement() {
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public final void setElement(DesignElement element) {
		this.element = element;
	}

	/**
	 * @return the direction
	 */
	public final Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public final void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public final void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Arrangement [element=" + element + ", direction=" + direction + ", location=" + location + "]";
	}
}
