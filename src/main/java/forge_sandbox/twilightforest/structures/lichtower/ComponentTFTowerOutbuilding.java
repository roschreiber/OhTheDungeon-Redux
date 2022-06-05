package forge_sandbox.twilightforest.structures.lichtower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.List;
import java.util.Random;
import org.bukkit.block.BlockFace;
import otd.util.RotationMirror.Rotation;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class ComponentTFTowerOutbuilding extends ComponentTFTowerWing {

	public ComponentTFTowerOutbuilding() {
		super();
	}

	protected ComponentTFTowerOutbuilding(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight,
			BlockFace direction) {
		super(feature, i, x, y, z, pSize, pHeight, direction);
	}

	/**
	 * NO BEARDS!
	 */
	@Override
	public void makeABeard(StructureTFComponent parent, List<StructureTFComponent> list, Random rand) {
		return;
	}

	/**
	 * Outbuildings should not make new wings close to the ground.
	 */
	@Override
	public boolean makeTowerWing(List<StructureTFComponent> list, Random rand, int index, int x, int y, int z,
			int wingSize, int wingHeight, Rotation direction) {
		if (y > 7) {
			return super.makeTowerWing(list, rand, index, x, y, z, wingSize, wingHeight, direction);
		} else {
			return false;
		}
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
//        final IBlockState cobblestone = Blocks.COBBLESTONE.getDefaultState();
//        for (int x = 0; x < this.size; x++) {
//            for (int z = 0; z < this.size; z++) {
//                this.replaceAirAndLiquidDownwards(world, cobblestone, x, -1, z, sbb);
//            }
//        }
		return super.addComponentParts(world, rand, sbb);
	}

}
