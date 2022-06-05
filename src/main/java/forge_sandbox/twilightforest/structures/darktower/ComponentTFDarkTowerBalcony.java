package forge_sandbox.twilightforest.structures.darktower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;
import forge_sandbox.twilightforest.structures.lichtower.ComponentTFTowerWing;

import java.util.List;
import java.util.Random;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class ComponentTFDarkTowerBalcony extends ComponentTFTowerWing {

	public ComponentTFDarkTowerBalcony() {
	}

	protected ComponentTFDarkTowerBalcony(TFFeature feature, int i, int x, int y, int z, BlockFace direction) {
		super(feature, i, x, y, z, 5, 5, direction);
	}

	@Override
	public void buildComponent(StructureTFComponent parent, List<StructureTFComponent> list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {

		// make floor
		fillWithBlocks(world, sbb, 0, 0, 0, 2, 0, 4, deco.accentState, AIR, false);
		fillWithBlocks(world, sbb, 0, 0, 1, 1, 0, 3, deco.blockState, AIR, false);

		fillWithBlocks(world, sbb, 0, 1, 0, 2, 1, 4, deco.fenceState, AIR, false);

		this.setBlockState(world, deco.accentState, 2, 1, 0, sbb);
		this.setBlockState(world, deco.accentState, 2, 1, 4, sbb);

		// clear inside
		fillWithAir(world, sbb, 0, 1, 1, 1, 1, 3);

		return true;
	}

}
