package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorNone extends RoomDecorBlocksBase {
	public RoomDecorNone() {
		super();
	}

	private final static BlockData AIR = Bukkit.createBlockData(Material.AIR);

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockBase(0, 0, 0, AIR, BlockStateGenArray.GenerationPhase.MAIN));
	}
}
