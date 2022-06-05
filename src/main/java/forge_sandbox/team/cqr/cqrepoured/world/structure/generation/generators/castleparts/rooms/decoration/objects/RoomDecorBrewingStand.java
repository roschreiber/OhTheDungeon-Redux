package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorBrewingStand extends RoomDecorBlocksBase {
	public RoomDecorBrewingStand() {
		super();
	}

	private final static BlockData BREWING_STAND = Bukkit.createBlockData(Material.BREWING_STAND);

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockBase(0, 0, 0, BREWING_STAND, BlockStateGenArray.GenerationPhase.MAIN));
	}
}
