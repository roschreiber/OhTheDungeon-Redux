package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorShelf extends RoomDecorBlocksBase {

	private final static BlockData WOODEN_SLAB;
	static {
		BlockData bd = Bukkit.createBlockData(Material.OAK_SLAB);
		Slab slab = (Slab) bd;
		slab.setType(Type.BOTTOM);
		WOODEN_SLAB = slab;
	}

	public RoomDecorShelf() {
		super();
	}

	@Override
	protected void makeSchematic() {
		this.schematic.add(new DecoBlockBase(0, 2, 0, WOODEN_SLAB.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 2, 0, WOODEN_SLAB.clone(), BlockStateGenArray.GenerationPhase.MAIN));

		this.schematic.add(new DecoBlockBase(0, 1, 0, Bukkit.createBlockData(Material.AIR),
				BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 1, 0, Bukkit.createBlockData(Material.AIR),
				BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(0, 0, 0, Bukkit.createBlockData(Material.AIR),
				BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 0, 0, Bukkit.createBlockData(Material.AIR),
				BlockStateGenArray.GenerationPhase.MAIN));

	}
}
