package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorTableMedium extends RoomDecorBlocksBase {

	public RoomDecorTableMedium() {
		super();
	}

	@Override
	protected void makeSchematic() {
		BlockData bd = Bukkit.createBlockData(Material.SPRUCE_PLANKS);
		this.schematic.add(new DecoBlockBase(0, 0, 0, bd.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 0, 0, bd.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(0, 0, 1, bd.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 0, 1, bd, BlockStateGenArray.GenerationPhase.MAIN));
	}

}
