package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorCauldron extends RoomDecorBlocksBase {
	public RoomDecorCauldron() {
		super();
	}

	private final static BlockData CAULDRON = Bukkit.createBlockData(Material.CAULDRON);

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockBase(0, 0, 0, CAULDRON.clone(), BlockStateGenArray.GenerationPhase.MAIN));
	}
}
