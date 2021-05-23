package forge_sandbox.twilightforest.structures.darktower;

import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;
import forge_sandbox.twilightforest.structures.lichtower.ComponentTFTowerWing;
import forge_sandbox.StructureBoundingBox;

import java.util.Random;
import org.bukkit.Material;
import otd.lib.async.AsyncWorldEditor;


public class ComponentTFDarkTowerBeard extends StructureTFComponentOld {

    protected int size;
    protected int height;

    public ComponentTFDarkTowerBeard() {
        super();
    }

    public ComponentTFDarkTowerBeard(TFFeature feature, int i, ComponentTFTowerWing wing) {
        super(feature, i);

        this.setCoordBaseMode(wing.getCoordBaseMode());
        this.size = wing.size;
        this.height = size / 2;

        // just hang out at the very bottom of the tower
        this.boundingBox = new StructureBoundingBox(wing.getBoundingBox().minX, wing.getBoundingBox().minY - this.height, wing.getBoundingBox().minZ, wing.getBoundingBox().maxX, wing.getBoundingBox().minY, wing.getBoundingBox().maxZ);

    }

    /**
     * Makes a dark tower type beard
     */
    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        makeDarkBeard(world, sbb, 0, 0, 0, size - 1, height - 1, size - 1);

        return true;
    }


    protected void makeDarkBeard(AsyncWorldEditor world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        Material frameState = Material.CHISELED_STONE_BRICKS;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                if (x == minX || x == maxX || z == minZ || z == maxZ) {
                    int length = Math.min(Math.abs(x - height) - 1, Math.abs(z - height) - 1);

                    if (length == height - 1) {
                        length++;
                    }

                    if (length == -1) {
                        length = 1;
                    }

                    for (int y = maxY; y >= height - length; y--) {
                        // wall
                        this.setBlockState(world, frameState, x, y, z, sbb);
                    }
                }
            }
        }
    }
}
