package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeDeadEndTripwireChest extends ComponentTFMazeDeadEndChest {

	public ComponentTFMazeDeadEndTripwireChest() {
		super();
	}

	public ComponentTFMazeDeadEndTripwireChest(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// normal chest room
		super.addComponentParts(world, rand, sbb);

		// add tripwire
		this.placeTripwire(world, 1, 1, 2, 3, BlockFace.EAST, sbb);

		// TNT!
		Material tnt = Material.TNT;
		this.setBlockState(world, tnt, 0, 0, 2, sbb);

		// Air blocks are required underneath to maximize TNT destruction of chest
		this.setBlockState(world, AIR, 0, -1, 2, sbb);
		this.setBlockState(world, AIR, 1, -1, 2, sbb);

		this.setBlockState(world, tnt, 2, 0, 4, sbb);
		this.setBlockState(world, tnt, 3, 0, 4, sbb);
		this.setBlockState(world, tnt, 2, 0, 3, sbb);
		this.setBlockState(world, tnt, 3, 0, 3, sbb);

		return true;
	}
}
