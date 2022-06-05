package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorJukebox extends RoomDecorBlocksBase {
	public RoomDecorJukebox() {
		super();
	}

	private final static BlockData JUKEBOX = Bukkit.createBlockData(Material.JUKEBOX);

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockBase(0, 0, 0, JUKEBOX.clone(), BlockStateGenArray.GenerationPhase.MAIN));
	}
}
