package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeDeadEndFountainLava extends ComponentTFMazeDeadEndFountain {

	public ComponentTFMazeDeadEndFountainLava() {
		super();
	}

	public ComponentTFMazeDeadEndFountainLava(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// normal fountain
		super.addComponentParts(world, rand, sbb);

		// remove water
		this.setBlockState(world, AIR, 2, 3, 4, sbb);
		this.setBlockState(world, AIR, 3, 3, 4, sbb);

		// lava instead of water
		this.setBlockState(world, Material.LAVA, 2, 3, 4, sbb);
		this.setBlockState(world, Material.LAVA, 3, 3, 4, sbb);

		return true;
	}

}
