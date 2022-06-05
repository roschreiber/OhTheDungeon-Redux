package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeDeadEndRoots extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndRoots() {
		super();
	}

	public ComponentTFMazeDeadEndRoots(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// no door
		for (int x = 1; x < 5; x++) {
			for (int z = 0; z < 5; z++) {
				// as we go +z, there are more vines
				if (rand.nextInt(z + 2) > 0) {
					// int length = rand.nextInt(6);

					// place dirt above ceiling
					this.setBlockState(world, Blocks.dirt, x, 6, z, sbb);

//                    // roots
//                    for (int y = 6 - length; y < 6; y++) {
//                        this.setBlockState(world, TFBlocks.twilight_plant.getDefaultState().withProperty(BlockTFPlant.VARIANT, PlantVariant.ROOT_STRAND), x, y, z, sbb);
//                    }

					// occasional gravel
					if (rand.nextInt(z + 1) > 1) {
						this.setBlockState(world, Blocks.gravel, x, 1, z, sbb);

						if (rand.nextInt(z + 1) > 1) {
							this.setBlockState(world, Blocks.gravel, x, 2, z, sbb);
						}
					}
				}
			}
		}

		return true;
	}
}
