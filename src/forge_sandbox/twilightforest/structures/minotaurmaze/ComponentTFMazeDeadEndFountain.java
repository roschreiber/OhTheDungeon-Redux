package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeDeadEndFountain extends ComponentTFMazeDeadEnd {

    public ComponentTFMazeDeadEndFountain() {
        super();
    }

    public ComponentTFMazeDeadEndFountain(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
        super(feature, i, x, y, z, rotation);
    }

    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        // normal doorway
        super.addComponentParts(world, rand, sbb);

        // back wall brick
        this.fillWithBlocks(world, sbb, 1, 1, 4, 4, 4, 4, Blocks.maze_stone, AIR, false);

        // water
        this.setBlockState(world, Material.WATER, 2, 3, 4, sbb);
        this.setBlockState(world, Material.WATER, 3, 3, 4, sbb);

        // receptacle
        this.setBlockState(world, AIR, 2, 0, 3, sbb);
        this.setBlockState(world, AIR, 3, 0, 3, sbb);

        return true;
    }
}
