package forge_sandbox.twilightforest.structures.darktower;

import forge_sandbox.twilightforest.TFFeature;

import java.util.List;
import java.util.Random;
import org.bukkit.block.BlockFace;
import otd.util.RotationMirror.Rotation;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class ComponentTFDarkTowerMainBridge extends ComponentTFDarkTowerBridge {

	public ComponentTFDarkTowerMainBridge() {
	}

	protected ComponentTFDarkTowerMainBridge(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight,
			BlockFace direction) {
		super(feature, i, x, y, z, 15, pHeight, direction);
	}

	@Override
	public boolean makeTowerWing(List<StructureTFComponent> list, Random rand, int index, int x, int y, int z,
			int wingSize, int wingHeight, Rotation rotation) {
		// make another size 15 main tower
		BlockFace direction = getStructureRelativeRotation(rotation);
		int[] dx = offsetTowerCoords(x, y, z, 19, direction);

		ComponentTFDarkTowerMain wing = new ComponentTFDarkTowerMain(getFeatureType(), null, rand, index, dx[0], dx[1],
				dx[2], direction);

		list.add(wing);
		wing.buildComponent(this, list, rand);
		addOpening(x, y, z, rotation);
		return true;
	}
}
