package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorUnlitTorch extends RoomDecorBlocksBase {
	public RoomDecorUnlitTorch() {
		super();
	}

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockRotating(0, 0, 0, Bukkit.createBlockData(Material.REDSTONE_WALL_TORCH),
				BlockFace.SOUTH, BlockStateGenArray.GenerationPhase.POST));
	}
}
