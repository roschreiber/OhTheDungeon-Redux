/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest.structures.lichtower;

import java.util.Random;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureStartTFAbstract;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class StructureStartLichTower extends StructureStartTFAbstract {
	public StructureStartLichTower() {
		super();
	}

	public StructureStartLichTower(AsyncWorldEditor world, TFFeature feature, Random rand, int chunkX, int chunkZ) {
		super(world, feature, rand, chunkX, chunkZ);
	}

	@Override
	protected StructureTFComponent makeFirstComponent(AsyncWorldEditor world, TFFeature feature, Random rand, int x,
			int y, int z) {
		return new ComponentTFTowerMain(TFFeature.LICH_TOWER, world, rand, 0, x, y, z);
	}
}
