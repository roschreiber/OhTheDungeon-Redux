package forge_sandbox.twilightforest.structures.darktower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.lichtower.ComponentTFTowerWing;

import java.util.Random;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFDarkTowerRoofCactus extends ComponentTFDarkTowerRoof {

	public ComponentTFDarkTowerRoofCactus() {
	}

	public ComponentTFDarkTowerRoofCactus(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(feature, i, wing);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		super.addComponentParts(world, rand, sbb);

		// antenna
		for (int y = 1; y < 10; y++) {
			setBlockState(world, deco.blockState, size / 2, y, size / 2, sbb);
		}
		setBlockState(world, deco.accentState, size / 2, 10, size / 2, sbb);

		setBlockState(world, deco.accentState, size / 2 - 1, 1, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 1, 1, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2, 1, size / 2 - 1, sbb);
		setBlockState(world, deco.accentState, size / 2, 1, size / 2 + 1, sbb);

		// cactus things
		setBlockState(world, deco.accentState, size / 2 + 1, 7, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 2, 7, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 2, 8, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 2, 9, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 3, 9, size / 2, sbb);

		setBlockState(world, deco.accentState, size / 2, 6, size / 2 + 1, sbb);
		setBlockState(world, deco.accentState, size / 2, 6, size / 2 + 2, sbb);
		setBlockState(world, deco.accentState, size / 2, 7, size / 2 + 2, sbb);
		setBlockState(world, deco.accentState, size / 2, 8, size / 2 + 2, sbb);
		setBlockState(world, deco.accentState, size / 2, 8, size / 2 + 3, sbb);

		setBlockState(world, deco.accentState, size / 2 - 1, 5, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 - 2, 5, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 - 2, 6, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 - 2, 7, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 - 3, 7, size / 2, sbb);

		setBlockState(world, deco.accentState, size / 2, 4, size / 2 - 1, sbb);
		setBlockState(world, deco.accentState, size / 2, 4, size / 2 - 2, sbb);
		setBlockState(world, deco.accentState, size / 2, 5, size / 2 - 2, sbb);
		setBlockState(world, deco.accentState, size / 2, 6, size / 2 - 2, sbb);
		setBlockState(world, deco.accentState, size / 2, 6, size / 2 - 3, sbb);

		return true;
	}
}
