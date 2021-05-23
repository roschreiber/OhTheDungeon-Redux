package forge_sandbox.twilightforest.structures.lichtower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;

import java.util.Random;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.TFFeature;


public class ComponentTFTowerBeard extends StructureTFComponentOld {

    int size;
    int height;

    public ComponentTFTowerBeard() {
        super();
    }

    public ComponentTFTowerBeard(TFFeature feature, int i, ComponentTFTowerWing wing) {
        super(feature, i);

        this.setCoordBaseMode(wing.getCoordBaseMode());
        this.size = wing.size - 2;
        this.height = size / 2;

        // just hang out at the very bottom of the tower
        this.boundingBox = new StructureBoundingBox(wing.getBoundingBox().minX + 1, wing.getBoundingBox().minY - this.height - 1, wing.getBoundingBox().minZ + 1, wing.getBoundingBox().maxX - 1, wing.getBoundingBox().minY - 1, wing.getBoundingBox().maxZ - 1);

    }

    /**
     * Makes a pyramid-shaped beard
     */
    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        return makePyramidBeard(world, rand, sbb);
    }

    private boolean makePyramidBeard(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        for (int y = 0; y <= height; y++) {
            int min = y;
            int max = size - y - 1;

            fillWithRandomizedBlocks(world, sbb, min, height - y, min, max, height - y, max, false, rand, StructureTFComponentOld.getStrongholdStones());
        }
        return true;
    }


}
