/**
 * 
 */
package forge_sandbox.com.someguyssoftware.gottschcore.cube;

import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;
import forge_sandbox.BlockPos;
import org.bukkit.Material;
import otd.lib.async.AsyncWorldEditor;

/**
 * For Forge 1.8+
 * Wrapper class for Block/BlockState. Immutable.
 * TODO remove all the redundant getBlockState() calls and use this.getState()
 * @author Mark Gottschling on May 6, 2017
 *
 */
public class Cube {

	private final ICoords coords;
	private final Material state;
	
	/**
	 * 
	 * @param world
	 * @param coords
	 */
	public Cube(AsyncWorldEditor world, ICoords coords) {
		this.coords = coords;
                BlockPos pos = coords.toPos();
                this.state = world.zone_world.getType(pos.x, pos.y, pos.z);
	}
	
	/**
	 * 
	 * @param coords
	 * @param state
	 */
	public Cube(ICoords coords, Material state) {
		this.coords = coords;
		this.state = state;
	}
	
	/**
	 * 
	 * @return
	 */
	public Material getState() {
		return state;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasState() {
		if (state == null) return false;
		return true;
	}
	
	/**
	 * 
	 * @param block
	 * @return
	 */
	public boolean equalsBlock(Material block) {
		if (state == block) return true;
		return false;
	}

	/**
	 * 
	 * @param material
	 * @return
	 */
	public boolean equalsMaterial(Material material) {		
		if (state == material) return true;
		return false;	
	}
	
	/**
	 * Wrapper for Block.isAir()
	 * @return
	 */
	public boolean isAir() {
		return state == Material.AIR;
	}
	
	/**
	 * Wrapper for Material.isReplaceable();
	 * @return
	 */
	public boolean isReplaceable() {
		return true;
	}
	
	/**
	 * Wrapper for Material.isSolid()
	 * @return
	 */
	public boolean isSolid() {
		return getState().isSolid();
	}

        /**
	 * Wrapper for IBlockState.isTopSolid
	 * @return
	 */
	public boolean isTopSolid() {
		return getState().isSolid();
	}
	
	public boolean isLiquid() {
		return getState() == Material.WATER || getState() == Material.LAVA;
	}
	
	/**
	 * @return the coords
	 */
	public ICoords getCoords() {
		return coords;
	}
	
	@Override
	public String toString() {
		return "Cube [coords=" + coords.toShortString() + ", state=" + state + "]";
	}
}
