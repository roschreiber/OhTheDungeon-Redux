package forge_sandbox.twilightforest.structures.lichtower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.util.WoodType;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFTowerRoofAttachedSlab extends ComponentTFTowerRoofSlab {

	public ComponentTFTowerRoofAttachedSlab() {
		super();
	}

	public ComponentTFTowerRoofAttachedSlab(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(feature, i, wing);
	}

	/**
	 * Makes a flat, pyramid-shaped roof that is connected to the parent tower
	 */
	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		return makeConnectedCap(world, WoodType.BIRCH, sbb);
	}
}
