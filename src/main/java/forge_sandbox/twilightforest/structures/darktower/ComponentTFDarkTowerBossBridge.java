package forge_sandbox.twilightforest.structures.darktower;

import forge_sandbox.twilightforest.TFFeature;

import java.util.List;
import java.util.Random;
import org.bukkit.block.BlockFace;
import otd.util.RotationMirror.Rotation;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class ComponentTFDarkTowerBossBridge extends ComponentTFDarkTowerBridge {

	public ComponentTFDarkTowerBossBridge() {
	}

	protected ComponentTFDarkTowerBossBridge(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight,
			BlockFace direction) {
		super(feature, i, x, y, z, pSize, pHeight, direction);
	}

	@Override
	public boolean makeTowerWing(List<StructureTFComponent> list, Random rand, int index, int x, int y, int z,
			int wingSize, int wingHeight, Rotation rotation) {
		// make another size 15 main tower

		BlockFace direction = getStructureRelativeRotation(rotation);
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);

		ComponentTFDarkTowerBossTrap wing = new ComponentTFDarkTowerBossTrap(getFeatureType(), index, dx[0], dx[1],
				dx[2], wingSize, wingHeight, direction);
		list.add(wing);
		wing.buildComponent(this, list, rand);
		addOpening(x, y, z, rotation);
		return true;
	}
}
