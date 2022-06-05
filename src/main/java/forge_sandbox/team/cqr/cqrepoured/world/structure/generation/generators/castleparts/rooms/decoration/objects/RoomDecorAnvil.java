package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorAnvil extends RoomDecorBlocksBase {
	public RoomDecorAnvil() {
		super();
	}

	private final static BlockData ANVIL = Bukkit.createBlockData("minecraft:anvil[facing=west]");

	@Override
	protected void makeSchematic() {
		this.schematic.add(
				new DecoBlockRotating(0, 0, 0, ANVIL.clone(), BlockFace.WEST, BlockStateGenArray.GenerationPhase.MAIN));
	}
}
