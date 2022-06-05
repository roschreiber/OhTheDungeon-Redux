package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorTableSmall extends RoomDecorBlocksBase {
	public RoomDecorTableSmall() {
		super();
	}

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockBase(0, 0, 0, Bukkit.createBlockData(Material.SPRUCE_PLANKS),
				BlockStateGenArray.GenerationPhase.MAIN));
	}

}
