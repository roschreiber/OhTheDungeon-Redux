package forge_sandbox.twilightforest.structures.minotaurmaze;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;
import forge_sandbox.twilightforest.structures.StructureTFComponentOld;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import otd.lib.async.AsyncWorldEditor;

public class ComponentTFMazeDeadEnd extends StructureTFComponentOld {

    public ComponentTFMazeDeadEnd() {
        super();
    }

    public ComponentTFMazeDeadEnd(TFFeature feature, int i, int x, int y, int z, BlockFace rotation) {
        super(feature, i);
        this.setCoordBaseMode(rotation);
        this.boundingBox = new StructureBoundingBox(x, y, z, x + 5, y + 5, z + 5);

    }

    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        this.fillWithBlocks(world, sbb, 1, 1, 0, 4, 4, 0, Bukkit.createBlockData(Material.OAK_FENCE), AIR, false);
        this.fillWithAir(world, sbb, 2, 1, 0, 3, 3, 0);
        return true;
    }
}
