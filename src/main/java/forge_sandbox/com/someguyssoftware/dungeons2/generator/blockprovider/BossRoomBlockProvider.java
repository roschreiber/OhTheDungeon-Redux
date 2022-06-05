/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator.blockprovider;

import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Layout;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;

/**
 * @author Mark Gottschling on Aug 30, 2016
 *
 */
public class BossRoomBlockProvider implements IDungeonsBlockProvider {
	/*
	 * TODO is there anything special in a boss room? NO pillars YES CARPET/RUGS -
	 * should rug be a design element.... i guess so.
	 */
	@Override
	public boolean isPillarElement(ICoords coords, Room room, Layout layout) {
		return false;
	}
}
