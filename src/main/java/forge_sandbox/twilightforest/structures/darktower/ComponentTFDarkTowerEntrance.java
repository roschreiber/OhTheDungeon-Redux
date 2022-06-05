package forge_sandbox.twilightforest.structures.darktower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.List;
import java.util.Random;
import org.bukkit.block.BlockFace;
import otd.util.RotationMirror.Rotation;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class ComponentTFDarkTowerEntrance extends ComponentTFDarkTowerWing {

	public ComponentTFDarkTowerEntrance() {
	}

	protected ComponentTFDarkTowerEntrance(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight,
			BlockFace direction) {
		super(feature, i, x, y, z, pSize, pHeight, direction);
	}

	@Override
	public void buildComponent(StructureTFComponent parent, List<StructureTFComponent> list, Random rand) {
		super.buildComponent(parent, list, rand);

		// a few more openings
		addOpening(size / 2, 1, 0, Rotation.CLOCKWISE_90, EnumDarkTowerDoor.REAPPEARING);
		addOpening(size / 2, 1, size - 1, Rotation.COUNTERCLOCKWISE_90, EnumDarkTowerDoor.REAPPEARING);
	}

	@Override
	public void makeABeard(StructureTFComponent parent, List<StructureTFComponent> list, Random rand) {
	}

	@Override
	public void makeARoof(StructureTFComponent parent, List<StructureTFComponent> list, Random rand) {
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
		// make walls
		makeEncasedWalls(world, rand, sbb, 0, 0, 0, size - 1, height - 1, size - 1);

		// deco to ground
		for (int x = 0; x < this.size; x++) {
			for (int z = 0; z < this.size; z++) {
				this.setBlockState(world, deco.accentState, x, -1, z, sbb);
			}
		}

		// clear inside
		fillWithAir(world, sbb, 1, 1, 1, size - 2, height - 2, size - 2);

		// sky light
		nullifySkyLightForBoundingBox(world);

		// openings
		makeOpenings(world, sbb);

		return true;
	}

}
