/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest.structures.darktower;

import java.util.Random;

import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureStartTFAbstract;
import forge_sandbox.twilightforest.structures.StructureTFComponent;
import otd.lib.async.AsyncWorldEditor;

public class StructureStartDarkTower extends StructureStartTFAbstract { // NO_UCD (unused code)
	public StructureStartDarkTower() {
		super();
	}

	public StructureStartDarkTower(AsyncWorldEditor world, TFFeature feature, Random rand, int chunkX, int chunkZ) {
		super(world, feature, rand, chunkX, chunkZ);
	}

	@Override
	protected StructureTFComponent makeFirstComponent(AsyncWorldEditor world, TFFeature feature, Random rand, int x,
			int y, int z) {
		return new ComponentTFDarkTowerMain(TFFeature.DARK_TOWER, world, rand, 0, x, y - 1, z);
	}
}
