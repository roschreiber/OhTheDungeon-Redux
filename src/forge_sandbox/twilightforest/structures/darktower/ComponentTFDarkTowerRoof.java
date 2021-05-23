package forge_sandbox.twilightforest.structures.darktower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;
import forge_sandbox.twilightforest.structures.lichtower.ComponentTFTowerRoof;
import forge_sandbox.twilightforest.structures.lichtower.ComponentTFTowerWing;

import java.util.List;
import java.util.Random;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class ComponentTFDarkTowerRoof extends ComponentTFTowerRoof {
	public ComponentTFDarkTowerRoof() {
	}

	public ComponentTFDarkTowerRoof(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(feature, i, wing);

		// same alignment
		this.setCoordBaseMode(wing.getCoordBaseMode());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = 12;

		// just hang out at the very top of the tower
		makeCapBB(wing);

		// spawn list!
		this.spawnListIndex = 1;
	}

	@Override
	public void buildComponent(StructureTFComponent parent, List<StructureTFComponent> list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}
	}

	/**
	 * A fence around the roof!
	 */
	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// fence
		for (int x = 0; x <= size - 1; x++) {
			for (int z = 0; z <= size - 1; z++) {
				if (x == 0 || x == size - 1 || z == 0 || z == size - 1) {
					setBlockState(world, deco.fenceState, x, 1, z, sbb);
				}
			}
		}

		setBlockState(world, deco.accentState, 0, 1, 0, sbb);
		setBlockState(world, deco.accentState, size - 1, 1, 0, sbb);
		setBlockState(world, deco.accentState, 0, 1, size - 1, sbb);
		setBlockState(world, deco.accentState, size - 1, 1, size - 1, sbb);

		return true;
	}

}
