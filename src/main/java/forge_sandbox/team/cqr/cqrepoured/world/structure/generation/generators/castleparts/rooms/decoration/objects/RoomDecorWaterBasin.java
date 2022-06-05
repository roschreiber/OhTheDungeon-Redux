package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorWaterBasin extends RoomDecorBlocksBase {
	public RoomDecorWaterBasin() {
		super();
	}

	@Override
	protected void makeSchematic() {
		final BlockData chiseledStone = Bukkit.createBlockData(Material.CHISELED_STONE_BRICKS);
		final BlockData stairs = Bukkit.createBlockData(Material.STONE_BRICK_STAIRS);

		this.schematic.add(new DecoBlockBase(0, 0, 0, chiseledStone.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockRotating(1, 0, 0, stairs.clone(), BlockFace.SOUTH,
				BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(2, 0, 0, chiseledStone, BlockStateGenArray.GenerationPhase.MAIN));

		this.schematic.add(new DecoBlockRotating(0, 0, 1, stairs.clone(), BlockFace.EAST,
				BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(1, 0, 1, Bukkit.createBlockData(Material.WATER),
				BlockStateGenArray.GenerationPhase.POST));
		this.schematic.add(new DecoBlockRotating(2, 0, 1, stairs.clone(), BlockFace.WEST,
				BlockStateGenArray.GenerationPhase.MAIN));

		this.schematic.add(new DecoBlockBase(0, 0, 2, chiseledStone.clone(), BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic
				.add(new DecoBlockRotating(1, 0, 2, stairs, BlockFace.NORTH, BlockStateGenArray.GenerationPhase.MAIN));
		this.schematic.add(new DecoBlockBase(2, 0, 2, chiseledStone, BlockStateGenArray.GenerationPhase.MAIN));
	}
}
