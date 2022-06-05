package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms.decoration.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;

public class RoomDecorBed extends RoomDecorBlocksBase {
	public RoomDecorBed() {
		super();
	}

	@Override
	protected void makeSchematic() {

//		BlockData head = Bukkit.createBlockData(Material.RED_BED);
//		Bed bed_head = (Bed) head;
//		bed_head.setPart(Part.HEAD);
		this.schematic.add(new DecoBlockBase(0, 0, 0, Bukkit.createBlockData(Material.RED_CARPET),
				BlockStateGenArray.GenerationPhase.MAIN));

//		this.schematic.add(
//				new DecoBlockRotating(0, 0, 0, bed_head, BlockFace.NORTH, BlockStateGenArray.GenerationPhase.MAIN));
//		BlockData foot = Bukkit.createBlockData(Material.RED_BED);
//		Bed bed_foot = (Bed) foot;
//		bed_foot.setPart(Part.HEAD);
		this.schematic.add(new DecoBlockBase(0, 0, 1, Bukkit.createBlockData(Material.RED_CARPET),
				BlockStateGenArray.GenerationPhase.MAIN));

//		this.schematic.add(
//				new DecoBlockRotating(0, 0, 1, bed_foot, BlockFace.NORTH, BlockStateGenArray.GenerationPhase.MAIN));
	}
}
