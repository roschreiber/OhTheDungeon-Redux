package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeRoomFountain extends ComponentTFMazeRoom {

    public ComponentTFMazeRoomFountain() {
        super();
    }


    public ComponentTFMazeRoomFountain(TFFeature feature, int i, Random rand, int x, int y, int z) {
        super(feature, i, rand, x, y, z);
    }


    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        super.addComponentParts(world, rand, sbb);

        this.fillWithBlocks(world, sbb, 5, 1, 5, 10, 1, 10, Blocks.maze_stone, AIR, false);
        this.fillWithBlocks(world, sbb, 6, 1, 6, 9, 1, 9, Bukkit.createBlockData(Material.WATER), AIR, false);

        return true;
    }
}
