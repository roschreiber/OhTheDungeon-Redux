package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorCraftingTable extends RoomDecorBlocksBase {
	public RoomDecorCraftingTable() {
		super();
	}

	private final static BlockData CRAFTING_TABLE = Bukkit.createBlockData(Material.CRAFTING_TABLE);

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockBase(0, 0, 0, CRAFTING_TABLE.clone(), BlockStateGenArray.GenerationPhase.MAIN));
	}
}
