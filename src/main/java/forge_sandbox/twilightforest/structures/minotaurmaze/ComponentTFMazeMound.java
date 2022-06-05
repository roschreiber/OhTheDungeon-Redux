package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class ComponentTFMazeMound extends StructureTFComponentOld {

	public ComponentTFMazeMound() {
		super();
	}

	public static final int DIAMETER = 35;
	private int averageGroundLevel = -1;

	private ComponentTFMazeUpperEntrance mazeAbove;

	public ComponentTFMazeMound(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(feature, i);
		this.setCoordBaseMode(HORIZONTALS[rand.nextInt(4)]);

		this.boundingBox = new StructureBoundingBox(x, y, z, x + DIAMETER, y + 8, z + DIAMETER);
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current
	 * Location of StructGen
	 */
	@Override
	public void buildComponent(StructureTFComponent structurecomponent, List<StructureTFComponent> list,
			Random random) {
		super.buildComponent(structurecomponent, list, random);

		// add aboveground maze entrance building
		mazeAbove = new ComponentTFMazeUpperEntrance(getFeatureType(), 3, random, boundingBox.minX + 10,
				boundingBox.minY + 0, boundingBox.minZ + 10);
		list.add(mazeAbove);
		mazeAbove.buildComponent(this, list, random);
	}

	@Override
	public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {

		if (this.averageGroundLevel < 0) {
			this.averageGroundLevel = 63;

			if (this.averageGroundLevel < 0) {
				return true;
			}

			int offset = this.averageGroundLevel - this.boundingBox.maxY + 8 - 1;

			this.boundingBox.offset(0, offset, 0);

			if (this.mazeAbove != null) {
				mazeAbove.getBoundingBox().offset(0, offset, 0);
			}
		}

		// this.fillWithBlocks(world, sbb, 0, 0, 0, 25, 8, 25, Blocks.DIRT, 0, false);

		for (int x = 0; x < DIAMETER; x++) {
			for (int z = 0; z < DIAMETER; z++) {
				int cx = x - DIAMETER / 2;
				int cz = z - DIAMETER / 2;

				int dist = (int) Math.sqrt(cx * cx + cz * cz);
				int hheight = (int) (Math.cos((double) dist / DIAMETER * Math.PI) * (DIAMETER / 3));

				// leave a hole in the middle
				if (!(cx <= 2 && cx >= -1 && cz <= 2 && cz >= -1)
						&& ((!(cx <= 2 && cx >= -1) && !(cz <= 2 && cz >= -1)) || hheight > 6)) {
					this.setBlockState(world, Material.GRASS_BLOCK, x, hheight, z, sbb);

					// only fill to the bottom when we're not in the entrances
					if (!(cx <= 2 && cx >= -1) && !(cz <= 2 && cz >= -1)) {
						this.setBlockState(world, Material.DIRT, x, hheight - 1, z, sbb);
					} else if (hheight > 6) {
						this.fillWithBlocks(world, sbb, x, 6, z, x, hheight - 1, z, Blocks.dirt, AIR, false);
					}
				}
			}
		}

		return true;
	}
}
