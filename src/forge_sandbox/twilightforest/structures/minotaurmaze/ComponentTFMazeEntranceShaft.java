package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.structures.StructureTFComponent;

public class ComponentTFMazeEntranceShaft extends StructureTFComponentOld {

    public ComponentTFMazeEntranceShaft() {
        super();
    }


//    private int averageGroundLevel = -1;

    public ComponentTFMazeEntranceShaft(TFFeature feature, int i, Random rand, int x, int y, int z) {
        super(feature, i);
        this.setCoordBaseMode(HORIZONTALS[rand.nextInt(4)]);

        this.boundingBox = new StructureBoundingBox(x, y, z, x + 6 - 1, y + 14, z + 6 - 1);
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    @Override
    public void buildComponent(StructureTFComponent structurecomponent, List<StructureTFComponent> list, Random random) {
        ;
    }

    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
//        if (this.averageGroundLevel < 0) {
//            this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);
//
//            if (this.averageGroundLevel < 0) {
//                return true;
//            }
//
//            this.boundingBox.maxY = this.averageGroundLevel;
//            this.boundingBox.minY = 53;
//        }

        this.boundingBox.maxY = 63;
        this.boundingBox.minY = 53;


        this.fillWithBlocks(world, sbb, 0, 0, 0, 5, this.boundingBox.getYSize(), 5, Blocks.maze_stone, AIR, true);
        this.fillWithAir(world, sbb, 1, 0, 1, 4, this.boundingBox.getYSize(), 4);

        return true;
    }
}
