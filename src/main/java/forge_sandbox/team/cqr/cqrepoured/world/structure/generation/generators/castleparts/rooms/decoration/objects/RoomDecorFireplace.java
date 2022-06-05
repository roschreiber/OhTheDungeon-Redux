package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorFireplace extends RoomDecorBlocksBase {
	public RoomDecorFireplace() {
		super();
	}

	@Override
	protected void makeSchematic() {
		BlockData BRICK_BLOCK = Bukkit.createBlockData(Material.BRICKS);

		this.schematic.add(new DecoBlockBase(0, 0, 0, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 0, 0, Bukkit.createBlockData(Material.NETHER_BRICKS),
				BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(2, 0, 0, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));

		this.schematic.add(new DecoBlockBase(0, 0, 1, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 0, 1, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(2, 0, 1, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));

		this.schematic.add(new DecoBlockBase(0, 1, 0, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 1, 0, Bukkit.createBlockData(Material.FIRE),
				BlockStateGenArray.GenerationPhase.POST));
		this.schematic.add(new DecoBlockBase(2, 1, 0, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));

		this.schematic.add(new DecoBlockBase(0, 2, 0, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 2, 0, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(2, 2, 0, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));

		this.schematic.add(new DecoBlockBase(1, 3, 0, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 4, 0, BRICK_BLOCK.clone(), BlockStateGenArray.GenerationPhase.MAIN));
	}
}
