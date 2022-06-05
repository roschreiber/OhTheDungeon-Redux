package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorPlanks extends RoomDecorBlocksBase {
	public RoomDecorPlanks() {
		super();
	}

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockBase(0, 0, 0, Bukkit.createBlockData(Material.OAK_PLANKS),
				BlockStateGenArray.GenerationPhase.MAIN));
	}
}
